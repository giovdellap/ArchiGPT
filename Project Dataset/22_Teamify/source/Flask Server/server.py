# Import statements
from hashlib import sha256
from flask import Flask, jsonify, request
from flask_cors import CORS
from DBConnection import get_connection
from support import (
    get_current_week_range,
    convert_date,
    get_teams,
    encrypt_username,
    decrypt_username,
    get_teams_of,
    get_team_name,
)
from flask_socketio import SocketIO, emit, join_room, leave_room
import datetime
from flask_mail import Mail, Message
import time
import requests
from datetime import date, timedelta
import random


# Server setup
app = Flask(__name__)
CORS(app)
socketio = SocketIO(
    app, cors_allowed_origins="http://localhost:3000", async_mode="eventlet"
)


# Middleware per gestire le richieste preflight OPTIONS
@app.before_request
def before_request():
    if request.method == "OPTIONS":
        headers = {
            "Access-Control-Allow-Origin": "http://localhost:3000",
            "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE",
            "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept",
            "Access-Control-Allow-Credentials": "true",
        }
        return ("", 200, headers)


connected_clients = []
teams = (
    []
)  # List of teams; each team is of the following format: [name, [member1, member2, ..., member10]]

# DB setup
conn = get_connection()
conn.set_session(autocommit=True)
if conn is None:
    print("[ERROR] DB Connection failed.")
    exit()
conn.close()


############################ WEBSOCKET ROUTES ############################
@socketio.on("connect")
def handle_connect():
    global teams

    # If it is the initial state, then retrieve all the database information about teams
    if len(connected_clients) == 0:
        teams = get_teams()
        print("[INFO] Teams retrieved successfully.")


@socketio.on("initial_data")
def handle_initial_data(username):
    username = decrypt_username(username)
    insert = True  # Check whether the request has been sent multiple times

    new_client = (username, request.sid)
    for client in connected_clients:
        (user, req_id) = client
        if user == username:
            insert = False

    if insert == True:
        connected_clients.append(new_client)
        join_room(username)
        socketio.emit('message', 'Joined Room: '+username, room=username)
        
        teams = get_teams_of(username)
        if(len(teams) > 0):
            for team in teams:
                join_room(team[0])
                socketio.emit('message', 'Joined Room: '+team[0], room=username)
                
        print("[INFO] Client connected: " + str(username))


@socketio.on("message")
def handle_message(message):
    print(f"[INFO] Recieved message: {message}")

    # Now send the message to all the connected clients
    emit("message", message, broadcast=True)


@socketio.on("disconnect")
def handle_disconnect():
    disconnected_client = (
        None,
        None,
    )

    for client in connected_clients:
        if client[1] == request.sid:
            disconnected_client = client
            break

    username = disconnected_client[0]
    if username is not None:
        print("[INFO] Client disconnected: " + str(username))
        connected_clients.remove(disconnected_client)
        leave_room(username)


############################ REST APIs ##################################


# Login API
@app.route("/", methods=["POST", "GET"])
def fetch():
    return jsonify({"message": "fetch succesfull!", "status": 200})


@app.route("/login", methods=["POST"])
def login():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()

    username = data["username"]
    encryptedUsername = encrypt_username(username)
    password = data["password"]
    encoded_password = sha256(str(password).encode("utf-8")).hexdigest()

    query = "SELECT password FROM member WHERE username = %s"
    params = (username,)
    curr.execute(query, params)
    try:
        (retrieved_password,) = curr.fetchone()
        retrieved_password = (
            retrieved_password.strip()
        )  # There were some \n without any sense

        if encoded_password != retrieved_password:
            print("[ERROR] /login: Wrong password.")
            conn.close()
            return jsonify("ko"), 400

    except Exception:
        print("[ERROR] /login: User not found.")
        conn.close()
        return jsonify("not found"), 404

    print("[INFO] /login: Login performed.")
    conn.close()
    return jsonify({"encryptedUsername": encryptedUsername}), 200


# Signup API
@app.route("/signup", methods=["POST"])
def signup():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()

    name = data["name"]
    surname = data["surname"]
    birth_date = data["birth"]
    email = data["email"]
    username = data["username"]
    password = data["password"]

    encoded_password = sha256(str(password).encode("utf-8")).hexdigest()

    query = "INSERT INTO member VALUES (%s, %s, %s, %s, %s, %s)"
    params = (name, surname, birth_date, email, username, encoded_password)
    try:
        curr.execute(query, params)
    except Exception as err:
        print("[ERROR] /signup: ", err)
        conn.close()
        return jsonify("ko"), 400

    print("[INFO] /signup: New user created.")
    conn.close()
    return jsonify("ok"), 200


# Reset request API
@app.route("/resetRequest", methods=["POST"])
def reset_request():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()

    username = data["username"]
    encryptedUsername = encrypt_username(username)

    # Does that user exist?
    query_exists = "SELECT email FROM member WHERE username = %s"
    param_exists = (username,)
    curr.execute(query_exists, param_exists)
    email = curr.fetchone()
    if str(email) == "":
        print("[ERROR] /reset: User not found.")
        conn.close()
        return jsonify("ko"), 404
    email = email[0]
    # The user exists; therefore, I can update his/her credentials.

    mail = Mail(app)

    app.config["MAIL_SERVER"] = "smtp.libero.it"
    app.config["MAIL_PORT"] = 465
    app.config["MAIL_USERNAME"] = "teamify@libero.it"
    app.config["MAIL_PASSWORD"] = "Ciaociao1@"
    app.config["MAIL_USE_TLS"] = False
    app.config["MAIL_USE_SSL"] = True
    mail = Mail(app)

    msg = Message("Password Reset", sender="teamify@libero.it", recipients=[email])
    msg.body = (
        "You have requested to recover your password, here you can proceed with the recovery: http://localhost:3000/reset?encryptedUsername="
        + encryptedUsername
    )
    mail.send(msg)
    conn.close()
    return jsonify("ok"), 200


# Reset password API
@app.route("/reset", methods=["POST"])
def reset():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()

    encryptedUsername = data["encryptedUsername"]
    encryptedUsername = encryptedUsername.replace(" ", "+")
    print(encryptedUsername)
    username = decrypt_username(encryptedUsername)
    new_password = data["password"]
    new_encoded_password = sha256(str(new_password).encode("utf-8")).hexdigest()

    # Does that user exist?
    query_exists = "SELECT username FROM member WHERE username = %s"
    param_exists = (username,)
    curr.execute(query_exists, param_exists)
    retrieved_username = str(curr.fetchone()).strip()
    if retrieved_username == "":
        print("[ERROR] /reset: User not found.")
        conn.close()
        return jsonify("ko"), 404

    # The user exists; therefore, I can update his/her credentials.
    query_update = "UPDATE member SET password = %s WHERE username = %s"
    params_update = (new_encoded_password, username)
    try:
        curr.execute(query_update, params_update)
    except Exception as err:
        print("[ERROR] /reset: ", err)
        conn.close()
        return jsonify("ko"), 400

    print("[INFO] /reset: Reset password was successful.")
    conn.close()
    return jsonify("ok"), 200


# Delete account
@app.route("/home/delete-account", methods=["POST"])
def delete_account():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()
    user = data["username"]
    username = decrypt_username(user)

    #remove all the teams where the user is the only participant
    query_teams = "delete from team where id in (select id FROM Team t, jointeam a where a.username = %s and a.team = t.id and not exists ( select * from jointeam b where b.username <> a.username and b.team = t.id))"
    param_teams = (username,)
    try:
        curr.execute(query_teams, param_teams)
    except Exception as err:
        print("[ERROR] /home/profile (tasks): ", err)
        conn.close()
        return jsonify("ko"), 400   
    
    # re assing ownership to the teams where the user is the only owner
    # gather all the teams where the user is the only admin but not the only user
    query_teams_2 = "select id from team t, jointeam j, manage m, manage mm where m.admin = %s and m.team = t.id and j.team = t.id and not (mm.admin <> m.admin and mm.team = t.id) group by t.id having count(*) > 1"
    
    query_teams_3 = "select username from jointeam where username <> %s and team = %s"
    query_fix_manage = "insert into manage values (%s, %s)"
    try:
        curr.execute(query_teams_2, param_teams)
        res = curr.fetchall()
        for(id) in res:
            curr.execute(query_teams_3, (username, id,))
            newUsername = curr.fetchone()
            curr.execute(query_fix_manage, (newUsername,id, ))

    except Exception as err:
        print("[ERROR] /home/profile (tasks): ", err)
        conn.close()
        return jsonify("ko"), 400     
      
    # removal the task connected to the user
    query_task = "DELETE FROM task WHERE member = %s"
    param_task = (username,)
    try:
        curr.execute(query_task, param_task)
    except Exception as err:
        print("[ERROR] /home/profile (tasks): ", err)
        conn.close()
        return jsonify("ko"), 400

    # remove the user
    query_member = "DELETE FROM member WHERE username = %s"
    param_member = (username,)
    try:
        curr.execute(query_member, param_member)
    except Exception as err:
        print("[ERROR] /home/profile: (member:)", err)
        conn.close()
        return jsonify("ko"), 400

    print(f"[INFO] /home/profile: user {username} succesfully deleted")
    conn.close()
    return jsonify("ok"), 200


############################ TASK APIs ##################################


# New Task API
@app.route("/home/newtask", methods=["POST"])
def create_new_task():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()

    # Fetch the ID of the last inserted task
    curr.execute("SELECT id FROM task ORDER BY id DESC LIMIT 1")
    last_id = curr.fetchone()

    if last_id:
        new_id = last_id[0] + 1
    else:
        new_id = 1

    # Extract other data from the request
    title = request.json["title"]
    date = request.json["date"]
    time = request.json["time"]
    description = request.json["description"]
    member = request.json["user"]
    member = decrypt_username(member)
    duration = request.json["duration"]
    type_task = request.json["type"]

    # if is a pernsonal task
    if type_task == "personal":
        query = "INSERT INTO task (id, title, date, time, description, member,duration) VALUES (%s, %s, %s, %s, %s, %s, %s)"
        values = (new_id, title, date, time, description, member, duration)

        try:
            curr.execute(query, values)
        except Exception as err:
            print("[ERROR] /new_task: ", err)
            conn.close()
            return jsonify("ko"), 400

        conn.close()
        return jsonify({"message": "Task created successfully", "id": new_id}), 200

    else:  # Shared task
        team_id = request.json["teamId"]
        event_members = request.json["members"]

        # Team name given the id (it is useful for the notifications)
        query_team_name = "SELECT name FROM team WHERE id = %s"
        params_team_name = (team_id,)
        curr.execute(query_team_name, params_team_name)
        (team_name,) = curr.fetchone()
        team_name = str(team_name).strip()

        curr.execute(
            "SELECT username FROM member where username= %s ORDER BY username DESC LIMIT 1",
            (member,),
        )
        member_db = curr.fetchone()

        if not member_db:
            conn.close()
            return jsonify("Not auth"), 401

        query = "INSERT INTO task (id, title, date, time, description, member, duration,type) VALUES (%s, %s, %s, %s, %s, %s, %s,%s)"
        values = (new_id, title, date, time, description, member, duration, type_task)

        try:
            curr.execute(query, values)
        except Exception as err:
            print("[ERROR] /new_task: ", err)
            conn.close()
            return jsonify("ko"), 400

        # here i include the user that creates the event
        query_inclues = (
            "INSERT INTO includes (event,team,username,state) VALUES (%s,%s,%s,%s)"
        )
        params_includes = (new_id, team_id, member, "accepted")

        try:
            curr.execute(query_inclues, params_includes)
        except Exception as err:
            print("[ERROR] /new_event: ", err)
            conn.close()
            return jsonify("ko"), 400

        for person in event_members:
            query_inclues_member = (
                "INSERT INTO includes (event,team,username,state) VALUES (%s,%s,%s,%s)"
            )
            params_includes_member = (new_id, team_id, person, "pending")
            try:
                curr.execute(query_inclues_member, params_includes_member)
            except Exception as err:
                print("[ERROR] /new_event (event member): ", err)
                conn.close()
                return jsonify("ko"), 400

            if person != member:
                query_notification = "INSERT INTO notification (date, content, type, read, username) VALUES (%s,%s,%s,%s,%s)"
                params_notification = (
                    datetime.datetime.now(),
                    f"{member} of team {team_name} has invited you to join the event '{title}'.",
                    "event",
                    False,
                    person,
                )
                try:
                    curr.execute(query_notification, params_notification)
                    socketio.emit("event_notification", "", room=person)
                except Exception as err:
                    print("[ERROR] /home/newtask: " + err)
                    conn.close()
                    return jsonify("ko"), 400

        conn.close()
        return jsonify({"message": "Task created successfully", "id": new_id}), 200


# Modify a task API
@app.route("/home/updatetask", methods=["POST"])
def update_task():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()
    username = data["local_user"]
    task_id = data["task_id"]
    username = decrypt_username(username)

    title = data.get("title")
    date = data.get("date")
    time = data.get("time")
    description = data.get("description")
    duration = data.get("duration")

    curr.execute("SELECT type FROM task WHERE id = %s", (task_id,))
    existing_task = curr.fetchone()

    if not existing_task:
        conn.close()
        return jsonify({"message": "Task not found"}), 404

    update_query = "UPDATE task SET title = %s, date = %s, time = %s, description = %s, member = %s, duration = %s WHERE id = %s"
    update_values = (title, date, time, description, username, duration, task_id)

    try:
        curr.execute(update_query, update_values)

        if existing_task[0] == "event":
            curr.execute("SELECT username FROM includes WHERE event = %s", (task_id,))
            members = curr.fetchall()
            # send the notification when the event is updated

            for member in members:
                if member[0] != username:
                    query_notification = "INSERT INTO notification (date, content, type, read, username) VALUES (%s,%s,%s,%s,%s)"
                    params_notification = (
                        datetime.datetime.now(),
                        f"{username} modify event {title} with id {task_id}.",
                        "modifyevent",
                        False,
                        member[0],
                    )
                    try:
                        curr.execute(query_notification, params_notification)
                        socketio.emit("event_notification", "", room=member[0])
                    except Exception as err:
                        print("[ERROR] /home/edittask (notification): " + err)
                        conn.close()
                        return jsonify("ko"), 400

    except Exception as err:
        print("[ERROR] /updatetask: An error occurred:", err)
        conn.close()
        return jsonify({"message": "Update failed"}), 400

    conn.close()
    return jsonify({"message": "Task updated successfully"}), 200


# Complete a task API
@app.route("/home/completetask/<int:task_id>", methods=["PUT"])
def complete_task(task_id):
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()

    curr.execute("SELECT * FROM task WHERE id = %s", (task_id,))
    existing_task = curr.fetchone()
    if not existing_task:
        conn.close()
        return jsonify({"message": "Task not found"}), 404

    status = existing_task[5]
    if status == "completed":
        change = "not_completed"
    else:
        change = "completed"

    update_query = "UPDATE task SET status = %s WHERE id = %s"
    update_values = (change, task_id)

    try:
        curr.execute(update_query, update_values)
        conn.commit()
    except Exception as err:
        print("[ERROR] /updatetask: ", err)
        conn.close()
        return jsonify({"message": "Update failed"}), 400

    conn.close()
    return jsonify({"message": "Task change successfully"}, change), 200


# Get all tasks API
@app.route("/tasks", methods=["GET"])
def get_tasks():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    # Fetch the ID of the last inserted task
    local_user = request.args.get("user")  # get back the params from the request
    local_user = decrypt_username(local_user)
    curr.execute(
        "SELECT title, description, date, time, duration, id, status, type, member FROM task WHERE member = %s AND type=%s",
        (
            local_user,
            "personal",
        ),
    )
    tasks = curr.fetchall()

    tasks_list = []
    for task in tasks:
        start_date = task[2].strftime("%Y-%m-%d") + " " + task[3].strftime("%H:%M:%S")
        end_date = task[2].strftime("%Y-%m-%d") + " " + task[3].strftime("%H:%M:%S")

        new_end_date = convert_date(end_date, task[4])

        tasks_list.append(
            {
                "title": task[0],
                "start": start_date,
                "end": new_end_date,
                "description": task[1],
                "duration": task[4],
                "id": task[5],
                "status": task[6],
                "type": task[7],
                "member": task[8],
            }
        )

    curr.execute(
        "SELECT event from includes where state=%s and username=%s",
        (
            "accepted",
            local_user,
        ),
    )
    set_e = curr.fetchall()

    events = []
    for event in set_e:
        curr.execute(
            "SELECT title, description, date, time, duration, id, status, type, member FROM task WHERE type=%s AND id= %s",
            (
                "event",
                event[0],
            ),
        )
        events.append(curr.fetchone())

    for event in events:
        start_date = event[2].strftime("%Y-%m-%d") + " " + event[3].strftime("%H:%M:%S")
        end_date = event[2].strftime("%Y-%m-%d") + " " + event[3].strftime("%H:%M:%S")

        new_end_date = convert_date(end_date, event[4])

        tasks_list.append(
            {
                "title": event[0],
                "start": start_date,
                "end": new_end_date,
                "description": event[1],
                "duration": event[4],
                "id": event[5],
                "status": event[6],
                "type": event[7],
                "member": event[8],
            }
        )

    conn.close()
    return jsonify(tasks_list), 200


# delete a task API
@app.route("/home/deletetask", methods=["DELETE"])
def delete_task():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()

    task_id = request.args.get("taskId")

    # remove all the task connected to the user
    query_delete = "DELETE FROM task WHERE id = %s"
    param_delete = (task_id,)
    print("PROBLEM")
    try:
        curr.execute(query_delete, param_delete)
        conn.close()
        return jsonify(
            {"message": f"[INFO] /home/deletetask: id {task_id} successfully deleted"}
        )
    except Exception as err:
        print("[ERROR] /home/deletetask : ", err)
        conn.close()
        return jsonify("ko"), 400


# Get events/tasks for shared agenda API
@app.route("/teamview", methods=["GET"])
def get_tasks_events():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    # Fetch the ID of the last inserted task
    local_user = request.args.get("user")  # get back the params from the request
    local_user = decrypt_username(local_user)
    local_team = request.args.get("team")  # da gestire dopo l'implementazione dei team

    curr.execute(
        "SELECT title, description, date, time, duration, id, member, type,status FROM task WHERE member = %s",
        (local_user,),
    )
    tasks = curr.fetchall()

    tasks_list = []
    for task in tasks:
        start_date = task[2].strftime("%Y-%m-%d") + " " + task[3].strftime("%H:%M:%S")
        end_date = task[2].strftime("%Y-%m-%d") + " " + task[3].strftime("%H:%M:%S")

        new_end_date = convert_date(end_date, task[4])

        tasks_list.append(
            {
                "title": task[0],
                "start": start_date,
                "end": new_end_date,
                "id": task[5],
                "description": task[1],
                "member": task[6],
                "status": task[8],
                "type": task[7],
                "duration": task[4],
            }
        )

    if local_team != 0:
        curr.execute(
            "SELECT event FROM includes WHERE username=%s AND state= %s",
            (
                local_user,
                "accepted",
            ),
        )
        events_ids = curr.fetchall()

        events = []
        for eventid in events_ids:
            print("ID\n", eventid[0])
            curr.execute(
                "SELECT title, description, date, time, duration, id, member, type,status FROM task WHERE id = %s",
                (eventid[0],),
            )
            events.append(curr.fetchone())
        # here i get also the id of event i need another query to retrieve event
        for event in events:
            start_date = (
                event[2].strftime("%Y-%m-%d") + " " + event[3].strftime("%H:%M:%S")
            )
            end_date = (
                event[2].strftime("%Y-%m-%d") + " " + event[3].strftime("%H:%M:%S")
            )

            new_end_date = convert_date(end_date, event[4])

            tasks_list.append(
                {
                    "title": event[0],
                    "description": event[1],
                    "start": start_date,
                    "end": new_end_date,
                    "duration": event[4],
                    "id": event[5],
                    "member": event[6],
                    "type": event[7],
                    "status": event[8],
                }
            )
    conn.close()
    return jsonify(tasks_list), 200


# get color API
@app.route("/getColor", methods=["GET"])
def getColor():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()

    event_ids = request.args.get("event")
    event_id_list = event_ids.split(",")
    list_color = {}

    for event in event_id_list:
        if event != "":
            curr.execute("SELECT team FROM includes WHERE event=%s", (event,))
            data = curr.fetchone()
            if data != None:
                curr.execute("SELECT color FROM team WHERE id=%s", (data[0],))
                color = curr.fetchone()
                list_color[event] = color[0]

    conn.close()
    return jsonify(list_color), 200


############################ END TASK APIs ##################################


# Get the list of teams releted to a certain user
@app.route("/home/teams", methods=["GET"])
def team_list():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    # Fetch the ID of the last inserted task
    user = request.args.get("user")
    user = decrypt_username(user)
    curr.execute(
        "SELECT team id, name, description FROM joinTeam JOIN team ON team.id=joinTeam.team WHERE username = %s ORDER BY name",
        (user,),
    )
    entries = curr.fetchall()
    teams = []
    for entry in entries:
        curr.execute(
            "SELECT admin from manage where team= %s and admin= %s",
            (
                entry[0],
                user,
            ),
        )
        admin = curr.fetchall()
        isAdmin = len(admin)
        if isAdmin == 0:
            isAdmin = "Member"
        else:
            isAdmin = "Administrator"
        teams.append(
            {"id": entry[0], "name": entry[1], "description": entry[2], "role": isAdmin}
        )

    conn.close()
    return jsonify(teams), 200


# Get joined teams from username
@app.route("/getJoinedTeams", methods=["POST"])
def get_joined_teams():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()
    tmp = []
    teams = []
    ids = []

    username = data["username"]
    username = decrypt_username(username)
    query = "SELECT team.id, team.name FROM joinTeam JOIN team ON joinTeam.team = team.id WHERE username = %s"
    params = (username,)

    curr.execute(query, params)

    tmp = curr.fetchall()
    if not tmp:
        print("[INFO] No joined team.")
        conn.close()
        return jsonify({"message": "No joined team.", "status": 201})

    for team in tmp:
        ids.append(team[0])
        teams.append(team[1])

    conn.close()
    return jsonify({"teams": teams, "ids": ids, "status": 200})


# team details given team id
@app.route("/teamDetails", methods=["GET"])
def team_details():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    # Fetch the ID of the last inserted task
    team_id = request.args.get("id")
    curr.execute(
        "SELECT name, description FROM team WHERE id = %s",
        (team_id,),
    )
    teamData = curr.fetchone()
    curr.execute(
        "SELECT username FROM joinTeam WHERE team = %s",
        (team_id,),
    )
    members = curr.fetchall()
    members2 = []
    for member in members:
        members2.append(member[0])

    curr.execute(
        "SELECT admin FROM manage WHERE team = %s",
        (team_id,),
    )
    admins = curr.fetchall()
    admins2 = []
    for admin in admins:
        admins2.append(admin[0])

    diff = []
    for element in members2:
        if element not in admins2:
            diff.append(element)

    result = []
    result.append(
        {
            "teamName": teamData[0],
            "description": teamData[1],
            "members": diff,
            "admins": admins2,
        }
    )

    conn.close()
    return jsonify(result), 200


pastel_colors = [
    "#FFB6C1",  # Light Pink
    "#FFC0CB",  # Pink
    "#FFD700",  # Gold
    "#FF69B4",  # Hot Pink
    "#FFE4E1",  # Misty Rose
    "#FFA07A",  # Light Salmon
    "#FFFFE0",  # Light Yellow
    "#ADD8E6",  # Light Blue
    "#E0FFFF",  # Light Cyan
    "#DDA0DD",  # Plum
    "#FFE4B5",  # Moccasin
    "#F0E68C",  # Khaki
    "#D8BFD8",  # Thistle
    "#B0E0E6",  # Powder Blue
    "#FFDEAD",  # Navajo White
]


# Create a team
@app.route("/home/newteam", methods=["POST"])
def team_create():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()

    data = request.get_json()
    username = data["username"]
    username = decrypt_username(username)
    name = data["name"]
    description = data["description"]
    random_pastel_color = random.choice(pastel_colors)

    # Fetch the ID of the last inserted task
    curr.execute(
        "INSERT INTO team (name, description,color) VALUES (%s,%s,%s)",
        (
            name,
            description,
            random_pastel_color,
        ),
    )
    curr.execute(
        "SELECT id FROM team WHERE name=%s AND description=%s",
        (
            name,
            description,
        ),
    )
    id = curr.fetchall()[0]
    curr.execute(
        "INSERT INTO joinTeam (team, username) VALUES (%s,%s)",
        (
            id,
            username,
        ),
    )
    curr.execute(
        "INSERT INTO manage (team, admin) VALUES (%s,%s)",
        (
            id,
            username,
        ),
    )
    
    try:
        join_room(name)
    except Exception as err:
        pass
    socketio.emit('message', 'Joined Room: '+name, room=username)

    conn.close()
    return jsonify("ok"), 200


# ottenere la lista degli admin dato un team id
@app.route("/adminGivenTeam", methods=["GET"])
def admin_given_team():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    # Fetch the ID of the last inserted task
    teamId = request.args.get("teamId")  # get back the params from the request
    curr.execute(
        "SELECT admin FROM manage WHERE team = %s",
        (teamId,),
    )
    admins = curr.fetchall()

    admin_list = []
    for admin in admins:
        admin_list.append({"admin": admin[0]})

    if not admin_list:
        conn.close()
        return jsonify([])  # Return a 404 Not Found status

    conn.close()
    return jsonify(admin_list), 200


# remove admin API
@app.route("/home/teams/team/removeadmin", methods=["DELETE"])
def remove_admin():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    team_id = request.args.get("teamId")
    admin = request.args.get("admin_to_remove")
    try:
        curr.execute(
            "DELETE FROM manage WHERE admin=%s AND team= %s",
            (
                admin,
                team_id,
            ),
        )
        conn.close()
        return jsonify("Admin correctly removed"), 200

    except Exception as error:
        print("[ERROR] error in removing admin")
        conn.close()
        return (
            jsonify({"error": "An error occurred"}),
            500,
        )  # Return a valid response


# add a new admin API
@app.route("/home/teams/team/newadmin", methods=["POST"])
def new_admin():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.json
    team_id = data.get("teamId")
    admin = data.get("admin")
    print(team_id, admin)
    try:
        curr.execute(
            "INSERT INTO manage (admin, team) VALUES (%s,%s) ",
            (
                admin,
                team_id,
            ),
        )
        conn.close()
        return jsonify("member addes"), 200

    except Exception as err:
        print("[ERROR] /home/deleteteam : ", err)
        conn.close()
        return jsonify("ko"), 400


# ottenere la lista dei membri dato un team id
@app.route("/membersGivenTeam", methods=["POST"])
def members_given_team():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()

    teamId = data["teamId"]
    curr.execute(
        "SELECT username FROM joinTeam WHERE team = %s",
        (teamId,),
    )
    members = curr.fetchall()
    if not members:
        print("[ERROR] There is no member for this team!")
        conn.close()
        return jsonify({"message": "There is no member for this team!", "status": 400})

    member_list = []
    for member in members:
        member_list.append(member[0])

    conn.close()
    return jsonify({"members": member_list, "status": 200})


# leave a team API
@app.route("/home/teams/leaveteam", methods=["DELETE"])
def exit_from_team():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    team_id = request.args.get("teamId")
    username = request.args.get("username")

    # Fetch admins for the team
    curr.execute(
        "SELECT admin FROM manage WHERE team = %s",
        (team_id,),
    )
    admins = curr.fetchall()
    # otteniamo la lista degli admin del gruppo
    # eliminiamo l'utente dagli admin
    print("ADMINS\n\n", admins)
    admin_list = []

    for admin in admins:
        if username == admin[0]:
            print("your user")
            try:
                curr.execute("DELETE FROM manage WHERE admin=%s", (username,))
            except Exception as error:
                print("[ERROR] error in deleting admin from manage")
                conn.close()
                return (
                    jsonify({"error": "An error occurred"}),
                    500,
                )  # Return a valid response
        else:
            admin_list.append({"admin": admin[0]})

    # se non ci sono admin, cancelliamo in gruppo
    if len(admin_list) < 1:
        # Find a new admin
        curr.execute(
            "SELECT username FROM joinTeam WHERE team = %s AND username != %s ",
            (
                team_id,
                username,
            ),
        )
        new_admin = curr.fetchone()
        if new_admin != None:
            query_new_admin = "INSERT INTO manage (admin, team) VALUES (%s,%s)"
            query_new_params = (
                new_admin[0],
                team_id,
            )
            try:
                curr.execute(query_new_admin, query_new_params)
                try:
                    curr.execute(
                        "UPDATE task SET member = %s WHERE member = %s AND type=%s",
                        (new_admin[0], username, "event"),
                    )
                    curr.execute("DELETE FROM manage WHERE admin = %s", (username,))
                except Exception as error:
                    print("[ERROR] error in deleting admin ")
                    conn.close()
                    return (
                        jsonify({"error": "An error occurred"}),
                        500,
                    )  # Return a valid response
            except Exception as error:
                print("[ERROR] error in adding the new admin")
                conn.close()
                return (
                    jsonify({"error": "An error occurred"}),
                    500,
                )  # Return a valid response

        else:
            print("NO ADM")
            # Delete the team if there is no new admin

            query_delete_team = "DELETE FROM team WHERE id=%s"
            delete_team_params = (team_id,)
            try:
                # se cancelliamo il team, dobbiamo cancellare tutti gli eventi collegati ad esso
                curr.execute("SELECT event FROM includes WHERE team=%s", (team_id,))
                events = curr.fetchall()
                for event in events:
                    try:
                        curr.execute("DELETE FROM task WHERE id=%s", (event[0],))
                    except Exception as err:
                        print(
                            "[ERROR] impossible to delete the events correleted to the team  : ",
                            err,
                        )
                        conn.close()
                        return (
                            jsonify({"error": "An error occurred"}),
                            500,
                        )  # Return a valid response

                curr.execute(query_delete_team, delete_team_params)
                conn.close()
                return (
                    jsonify(
                        {
                            "message": f"[INFO] /home/deleteteam: id {team_id} successfully deleted because no admin"
                        }
                    ),
                    200,
                )
            except Exception as err:
                print("[ERROR] can't delete the team without admin : ", err)
                conn.close()
                return (
                    jsonify({"error": "An error occurred"}),
                    500,
                )  # Return a valid response

    try:
        curr.execute(
            "DELETE FROM includes WHERE team=%s AND username=%s",
            (
                team_id,
                username,
            ),
        )
        query_delete = "DELETE FROM joinTeam WHERE username = %s and team=%s"
        param_delete = (username, team_id)

        curr.execute(query_delete, param_delete)
        conn.close()
        return jsonify(
            {"message": f"[INFO] /home/exitfromteam: id {team_id} successfully left"}
        )

    except Exception as err:
        print("[ERROR] /home/leaveteam : ", err)
        conn.close()
        return jsonify({"error": "An error occurred"}), 500  # Return a valid response


# delete a team API
@app.route("/home/teams/deleteteam", methods=["DELETE"])
def delete_team():
    conn = get_connection()
    conn.set_session(autocommit=True)
    team_id = request.args.get("teamId")

    curr = conn.cursor()

    query_delete = "DELETE FROM team WHERE id = %s "
    param_delete = (team_id,)

    try:
        curr.execute("SELECT event FROM includes WHERE team=%s", (team_id,))
        events = curr.fetchall()
        for event in events:
            try:
                curr.execute("DELETE FROM task WHERE id=%s", (event[0],))
            except Exception as err:
                print(
                    "[ERROR] impossible to delete the events correleted to the team  : ",
                    err,
                )
                conn.close()
                return (
                    jsonify({"error": "An error occurred"}),
                    500,
                )
        curr.execute(query_delete, param_delete)
        conn.close()
        return jsonify(
            {"message": f"[INFO] /home/deleteteam: id {team_id} successfully delete"}
        )
    except Exception as err:
        print("[ERROR] /home/deleteteam : ", err)
        conn.close()
        return jsonify("ko"), 400


# ottenere la lista dei membri dato un team id
@app.route("/teamGivenID", methods=["GET"])
def team_given_id():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    # Fetch the ID of the last inserted task
    teamId = request.args.get("id")  # get back the params from the request
    curr.execute(
        "SELECT name FROM team WHERE id = %s",
        (teamId,),
    )
    (name,) = curr.fetchone()
    name = str(name).strip()

    conn.close()
    return jsonify({"name": name, "status": 200})


# get the list of members of a certain team given an event id
@app.route("/home/team/member", methods=["GET"])
def team_members_given_event_id():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    # Fetch the ID of the last inserted task
    eventID = request.args.get("id")  # get back the params from the request
    curr.execute(
        "SELECT team FROM includes WHERE event = %s",
        (eventID,),
    )
    (team_id,) = curr.fetchone()
    team_id = str(team_id).strip()
    curr.execute(
        "SELECT username FROM joinTeam WHERE team = %s",
        (team_id,),
    )
    members = curr.fetchall()

    member_list = []
    for member in members:
        member_list.append({"member": member[0]})

    print(member_list)
    conn.close()
    return jsonify({"member_list": member_list, "status": 200})


# edit member API
@app.route("/home/team/event/editmember", methods=["POST"])
def edit_member_event():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()  # Fetch the ID of the last inserted task
    event_id = data["id"]  # get back the params from the request
    new_members = data["members"]
    admin = data["admin"]
    new_set = []
    for member in new_members:
        m = member["member"]
        new_set.append(m)
    curr.execute(
        "SELECT username, team FROM includes WHERE event = %s",
        (event_id,),
    )

    members = curr.fetchall()
    member_list = []
    for member in members:
        member_list.append(member[0])
        team_id = member[1]

    curr.execute("SELECT title FROM task WHERE id=%s", (event_id,))
    title = curr.fetchone()
    curr.execute("SELECT name from team WHERE id=%s", (team_id,))
    team_name = curr.fetchone()

    for member in member_list:
        if member not in new_set and member != admin:
            query_member = "DELETE FROM includes WHERE username = %s"
            param_member = (member,)
            try:
                curr.execute(query_member, param_member)
            except Exception as err:
                print("[ERROR] /home/team/event/editmember: (delete)", err)
                conn.close()
                return jsonify("ko"), 400

    for member in new_members:
        if member["member"] not in member_list:
            query_member = (
                "INSERT INTO includes (event,team,username,state) VALUES(%s,%s,%s,%s)"
            )
            param_member = (event_id, team_id, member["member"], "pending")
            try:
                curr.execute(query_member, param_member)
                query_notification = "INSERT INTO notification (date, content, type, read, username) VALUES (%s,%s,%s,%s,%s)"
                params_notification = (
                    datetime.datetime.now(),
                    f"{admin} of team {team_name[0]} has invited you to join the event '{title[0]}'.",
                    "event",
                    False,
                    member["member"],
                )
                try:
                    curr.execute(query_notification, params_notification)
                    socketio.emit("event_notification", "", room=member["member"])
                except Exception as err:
                    print("[ERROR] /home/newtask: " + err)
                    conn.close()
                    return jsonify("ko"), 400
            except Exception as err:
                print("[ERROR] /home/team/event/editmemeber: (insert:)", err)
                conn.close()
                return jsonify("ko"), 400

    conn.close()
    return jsonify({"member_list": member_list, "status": 200})


@app.route("/team/editName", methods=["GET"])
def editTeamName():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    teamId = request.args.get("teamId")
    newName = request.args.get("teamName")

    curr.execute(
        "UPDATE team SET name = %s WHERE id = %s",
        (
            newName,
            teamId,
        ),
    )

    conn.close()
    return jsonify({"message": "Success", "status": 200})


@app.route("/team/editDescription", methods=["GET"])
def editTeamDescription():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    teamId = request.args.get("teamId")
    newDescription = request.args.get("teamDescription")

    curr.execute(
        "UPDATE team SET description = %s WHERE id = %s",
        (
            newDescription,
            teamId,
        ),
    )

    conn.close()
    return jsonify({"message": "Success", "status": 200})


# Get list of members included in a certain event the one that accepted
@app.route("/home/event/members", methods=["GET"])
def members_given_event():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    # Fetch the ID of the last inserted task
    event_id = request.args.get("eventId")  # get back the params from the request
    curr.execute(
        "SELECT username FROM includes WHERE event = %s AND state = %s",
        (
            event_id,
            "accepted",
        ),
    )
    members = curr.fetchall()

    member_list = []
    for member in members:
        member_list.append({"member": member[0]})

    conn.close()
    return jsonify(member_list), 200


# Display user information
@app.route("/home/profile", methods=["POST"])
def show_personal_info():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()

    username = data["username"]
    username = decrypt_username(username)
    if username != "":
        query = "SELECT name, surname, birth_date, email, password FROM member WHERE username = %s"
        params = (username,)

        curr.execute(query, params)
        (name, surname, birth, email, password) = curr.fetchone()
        name = str(name).strip()
        surname = str(surname).strip()
        birth = str(birth).strip()
        email = str(email).strip()
        password = str(password).strip()

        print("[INFO] /home/profile: User data have been successfully displayed.")
        conn.close()
        return (
            jsonify(
                {
                    "name": name,
                    "surname": surname,
                    "birth": birth,
                    "email": email,
                    "password": password,
                }
            ),
            200,
        )

    conn.close()
    return jsonify("ko"), 404


# Modify user information
# Notice that here we are not modifying the password
@app.route("/home/modify-info", methods=["POST"])
def modify_personal_info():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()

    username = data["username"]
    username = decrypt_username(username)
    name = data["new_name"]
    surname = data["new_surname"]
    birth_date = data["new_birth"]
    email = data["new_email"]

    if username != "":
        query = "UPDATE member SET name = %s, surname = %s, birth_date = %s, email = %s WHERE username = %s"
        params = (
            name,
            surname,
            birth_date,
            email,
            username,
        )

        try:
            curr.execute(query, params)
            print("[INFO] /home/modify-info: Update was successful.")
            conn.close()
            return jsonify({"message": "User data has been updated!", "status": 200})

        except Exception as err:
            print("[ERROR] /home/modify-info: ", err)
            conn.close()
            return jsonify({"message": "Error during data update.", "status": 500})

    print("[ERROR] /home/modify-info: Username not found.")
    conn.close()
    return jsonify({"message": "User not found.", "status": 404})


# Modify user password
# Notice that here we modify the password of a logged user
@app.route("/home/modify-password", methods=["POST"])
def modify_password():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()

    username = data["username"]
    username = decrypt_username(username)
    old_password = data["old_password"]
    new_password = data["new_password"]

    encoded_old_password = sha256(str(old_password).encode("utf-8")).hexdigest()

    if username != "":
        # Does the user have inserted the correct password?
        query_verify = "SELECT username FROM member WHERE password = %s"
        params_verify = (encoded_old_password,)

        curr.execute(query_verify, params_verify)

        # There can be more than one username with the same password
        retrieved_username = ""
        retrieved_usernames = curr.fetchall()
        for (user,) in retrieved_usernames:
            if user == username:
                retrieved_username = username

        if retrieved_username == "":
            print(
                "[ERROR] /home/modify-password: The original password was not correct!"
            )
            conn.close()
            return jsonify(
                {"message": "The original password was not correct!", "status": 400}
            )

        if retrieved_username == username:
            encoded_new_password = sha256(str(new_password).encode("utf-8")).hexdigest()
            query = "UPDATE member SET password = %s WHERE username = %s"
            params = (
                encoded_new_password,
                username,
            )

            try:
                curr.execute(query, params)
                print("[INFO] /home/modify-password: Update was successful.")
                conn.close()
                return jsonify({"message": "Password has been updated!", "status": 200})

            except Exception as err:
                print("[ERROR] /home/modify-password: ", err)
                conn.close()
                return jsonify(
                    {"message": "Error during password update.", "status": 500}
                )

    print("[ERROR] /home/modify-password: Username not found.")
    conn.close()
    return jsonify({"message": "User not found.", "status": 404})


# Display user notifications
@app.route("/home/notifications", methods=["POST"])
def get_notifications():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()
    records = []

    username = data["username"]
    username = decrypt_username(username)
    if username != "":
        query = (
            "SELECT id, date, content, type, read FROM notification WHERE username = %s"
        )
        params = (username,)
        curr.execute(query, params)

        records = curr.fetchall()
        if len(records) == 0:
            print("[INFO] /home/notifications: There is no notification for this user.")
            conn.close()
            return jsonify({"message": "No notification available.", "status": 201})

        print("[INFO] /home/notifications: Display notifications was successful.")
        conn.close()
        return jsonify({"notifications": records, "status": 200})

    print("[ERROR] /home/notifications: Username not found.")
    conn.close()
    return jsonify({"message": "Username not found.", "status": 404})


# Read the notification
@app.route("/readNotification", methods=["POST"])
def read_notification():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()

    id = data["notification_id"]

    query_exists = "SELECT read FROM notification WHERE id = %s"
    params = (id,)

    curr.execute(query_exists, params)
    try:
        (read,) = curr.fetchone()
        if read == False:
            query_read = "UPDATE notification SET read = true WHERE id = %s"
            try:
                curr.execute(query_read, params)
                print(
                    "[INFO] /readNotification: The notification has been read successfully."
                )
            except Exception as err:
                print(
                    "[ERROR] /readNotification: The notification has not been read successfully. Err: "
                    + str(err)
                )
                conn.close()
                return (
                    jsonify(
                        {
                            "message": "The notification has not been read successfully.",
                            "status": 400,
                        }
                    ),
                    400,
                )
    except Exception as err:
        print(
            "[ERROR] /readNotification: The notification does not exist. Err: "
            + str(err)
        )
        conn.close()
        return (
            jsonify({"message": "The notification does not exist.", "status": 500}),
            500,
        )

    conn.close()
    return (
        jsonify(
            {"message": "The notification has been read successfully.", "status": 200}
        ),
        200,
    )


# given event id return team id
@app.route("/evevnt/teamview", methods=["GET"])
def give_team_id():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()

    event_id = data["event"]
    curr.execute("SELECT team FROM includes WHERE event=%s", (event_id))

    team_id = curr.fetchone()

    conn.close()
    return jsonify(team_id), 200


# given team id and username, invites the user to the team
@app.route("/invite", methods=["POST"])
def invite():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()

    username = data["username"]
    id = data["id"]
    admin = data["admin"]
    admin = decrypt_username(admin)
    if username == admin:
        conn.close()
        return jsonify("ko"), 400

    # Check if the user is already present in the team
    curr.execute(
        "SELECT * FROM jointeam WHERE username = %s AND team = %s", (username, id)
    )
    if curr.fetchone() is not None:
        # User is already present in the team
        conn.close()
        return jsonify("ko"), 400

    query_invite = "INSERT INTO invite (username, admin, team) VALUES (%s,%s,%s)"
    params_invite = (
        username,
        admin,
        id,
    )

    curr.execute("SELECT name FROM team WHERE id = %s", (id,))
    (name,) = curr.fetchone()
    name = str(name).strip()

    query_notification = "INSERT INTO notification (date, content, type, read, username) VALUES (%s,%s,%s,%s,%s)"
    params_notification = (
        datetime.datetime.now(),
        f"{admin} has invited you to join team {name}.",
        "invite",
        False,
        username,
    )

    try:
        curr.execute(query_invite, params_invite)
        print("[INFO] /invite: User invited successfully.")
        try:
            curr.execute(query_notification, params_notification)
            print("[INFO] /invite: Invite notification registered.")
            socketio.emit("invite_notification", "", room=username)

            conn.close()
            return jsonify("ok"), 200
        except Exception as err:
            print("[ERROR] Invite notification not registered. Err = " + str(err))
            conn.close()
            return jsonify("ko"), 400
    except Exception as err:
        print("[ERROR] User not invited. Err = " + str(err))
        conn.close()
        return jsonify("ko"), 400


# given username, check invites of user
@app.route("/checkInvites", methods=["POST"])
def check_invites():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()

    username = data["username"]
    username = decrypt_username(username)
    curr.execute(
        "SELECT admin, team id, name, description FROM invite JOIN team on team.id=invite.team WHERE username = %s ORDER BY name",
        (username,),
    )
    invites = curr.fetchall()
    invitesJson = []
    for invite in invites:
        invitesJson.append(
            {
                "admin": invite[0],
                "id": invite[1],
                "team_name": invite[2],
                "team_description": invite[3],
            }
        )
    conn.close()
    return jsonify({"invites": invitesJson, "status": 200})


# Given username, check event invites of the user
@app.route("/checkEventInvites", methods=["POST"])
def check_event_invites():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()

    username = data["username"]
    username = decrypt_username(username)
    curr.execute(
        """
        SELECT includes.event, includes.team, includes.username, includes.state, task.title, task.description, task.member
        FROM includes JOIN task ON task.id=includes.event 
        WHERE username = %s
        AND includes.state = 'pending'
        """,
        (username,),
    )
    invites = curr.fetchall()
    invitesJson = []
    for invite in invites:
        invitesJson.append(
            {
                "event_id": invite[0],
                "team_id": invite[1],
                "member": invite[2],
                "state": invite[3],
                "event_title": invite[4],
                "event_description": invite[5],
                "admin": invite[6],
            }
        )
    conn.close()
    return jsonify({"invites": invitesJson, "status": 200})


# given team id and username, user accepts invite and therefore joins team
@app.route("/acceptInvite", methods=["POST"])
def accept_invite():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()
    username = data["username"]
    username = decrypt_username(username)
    id = data["teamId"]
    curr.execute(
        "SELECT * from invite WHERE username=%s AND team=%s",
        (
            username,
            id,
        ),
    )
    if curr.fetchone():
        curr.execute(
            "DELETE FROM invite WHERE username=%s AND team=%s",
            (
                username,
                id,
            ),
        )
        curr.execute(
            "INSERT INTO joinTeam (username,team) values (%s,%s)",
            (
                username,
                id,
            ),
        )
        name = get_team_name(id)
        try:
            join_room(name)
        except Exception as err:
            pass
        socketio.emit('message', 'Joined Room: '+name, room=username)
        
        conn.close()
        return jsonify({"message": "ok", "status": 200})
    else:
        conn.close()
        return jsonify("ko"), 400


@app.route("/acceptEventInvite", methods=["POST"])
def accept_event_invite():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()

    username = data["username"]
    username = decrypt_username(username)
    event_id = data["event_id"]
    team_id = data["teamId"]

    query_update_event_invite = """
    UPDATE includes 
    SET state = 'accepted' 
    WHERE username = %s 
    AND event = %s
    AND team = %s
    """
    params_update_event_invite = (username, event_id, team_id)

    try:
        curr.execute(query_update_event_invite, params_update_event_invite)
        print("[INFO] Event acceptance successfully updated!")
    except Exception as err:
        print("[ERROR] /acceptEventInvite: " + err)
        conn.close()
        return jsonify({"message": "Update of event acceptance failed.", "status": 500})

    conn.close()
    return jsonify({"message": "Event successfully joined!", "status": 200})


@app.route("/rejectInvite", methods=["POST"])
def reject_invite():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()

    username = data["username"]
    username = decrypt_username(username)
    id = data["teamId"]
    admin = data["admin"]

    curr.execute(
        "SELECT * from invite WHERE username=%s AND team=%s",
        (
            username,
            id,
        ),
    )
    if curr.fetchone():
        curr.execute(
            "DELETE FROM invite WHERE username=%s AND team=%s",
            (
                username,
                id,
            ),
        )

        # If the user refuses my message, I get notified
        query_notification = "INSERT INTO notification (date, content, type, read, username) VALUES (%s,%s,%s,%s,%s)"
        params_notification = (
            datetime.datetime.now(),
            f"{username} has not accepted your invite.",
            "message",
            False,
            admin,
        )
        curr.execute(query_notification, params_notification)
        socketio.emit("message_notification", "", room=admin)

        conn.close()
        return jsonify({"message": "ok", "status": 200})
    else:
        conn.close()
        return jsonify("ko"), 400


@app.route("/rejectEventInvite", methods=["POST"])
def reject_event_invite():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    data = request.get_json()

    username = data["username"]
    username = decrypt_username(username)
    event_id = data["event_id"]
    team_id = data["teamId"]
    admin = data["admin"]
    event_name = data["event_title"]

    query_update_event_invite = """
    UPDATE includes 
    SET state = 'rejected' 
    WHERE username = %s 
    AND event = %s
    AND team = %s
    """
    params_update_event_invite = (username, event_id, team_id)

    try:
        curr.execute(query_update_event_invite, params_update_event_invite)
        print("[INFO] Event acceptance successfully updated!")

        # If the user refuses my message, I get notified
        query_notification = "INSERT INTO notification (date, content, type, read, username) VALUES (%s,%s,%s,%s,%s)"
        params_notification = (
            datetime.datetime.now(),
            f"{username} will not join the event {event_name}.",
            "message",
            False,
            admin,
        )
        curr.execute(query_notification, params_notification)
        socketio.emit("message_notification", "", room=admin)
    except Exception as err:
        print("[ERROR] /acceptEventInvite: " + err)
        conn.close()
        return jsonify({"message": "Update of event acceptance failed.", "status": 500})

    conn.close()
    return jsonify({"message": "Event successfully rejected.", "status": 200})


# Send message to team
@app.route("/sendMessageToTeam", methods=["POST"])
def send_chat_message():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()

    text = data["text"]
    sender = data["username"]
    sender = decrypt_username(sender)
    team = data["teamId"]
    members = data["members"]

    message = []
    date = str(datetime.datetime.now().date())
    time_ = str(datetime.datetime.now().time())[:5]
    message.append(sender)
    message.append(text)
    message.append(date)
    message.append(time_)

    # message: [sender, text, date, time]

    query_message = (
        "INSERT INTO message (datetime, content, sender, team) VALUES (%s,%s,%s,%s)"
    )
    params_message = (datetime.datetime.now(), text, sender, team)

    try:
        curr.execute(query_message, params_message)
        name = get_team_name(team)
        
        socketio.emit('message', 'MESSAGE TEAM', room=name)
        socketio.emit("chat_message", message, room=name)
        
        # for member in members:
        #     print('[INFO] Message sent to '+str(member))
        #     socketio.emit("chat_message", message, room=member)
    except Exception as err:
        print(
            "[ERROR] The message has not been registered into the DB. Err: " + str(err)
        )
        conn.close()
        return jsonify(
            {
                "message": "The message has not been registered into the DB.",
                "status": 500,
            }
        )

    print("[INFO] Message sent successfully!")
    conn.close()
    return jsonify({"message": "ok", "status": 200})


# Get messages
@app.route("/getMessages", methods=["POST"])
def get_chat_messages():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    curr = conn.cursor()
    records = []
    messages = []  # [sender, text, date, time]

    teamId = data["teamId"]

    query_retrieve_messages = "SELECT * FROM message WHERE team = %s"
    params_retrieve_messages = (teamId,)

    curr.execute(query_retrieve_messages, params_retrieve_messages)

    records = curr.fetchall()
    if not records:
        print("[INFO] There is no message available.")
        conn.close()
        return jsonify({"message": "No message available.", "status": 201})

    for message in records:
        msg = []
        date = str(message[1])[:10]
        time_ = str(message[1])[11:16]
        msg.append(message[3])  # sender
        msg.append(message[2])  # text
        msg.append(date)
        msg.append(time_)

        messages.append(msg)

    print("[INFO] Messages retrieved successfully.")
    conn.close()
    return jsonify({"messages": messages, "status": 200})


# INIZIO POLLS
@app.route("/vote", methods=["POST"])
def add_vote():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()
    pool_id = data.get("pool_id")
    username = data.get("username")
    username = decrypt_username(username)
    option_id = data.get("option_id")

    curr = conn.cursor()

    # check if the pool exists
    curr.execute(
        "SELECT * FROM survey WHERE id = %s",
        (pool_id,),
    )
    pool = curr.fetchone()
    if not pool:
        conn.close()
        return jsonify({"message": "Pool not found"}), 400

    # check if the user has already voted
    curr.execute(
        "select * from vote, option where username = %s and option.id = vote.option and option.survey = %s",
        (
            username,
            pool_id,
        ),
    )
    vote = curr.fetchone()

    # add or update the vote
    if vote:
        old_option_id = vote[0]
        curr.execute(
            "UPDATE vote SET option = %s WHERE option = (select option from vote, option where username = %s and option = option.id and survey = %s)",
            (
                option_id,
                username,
                pool_id,
            ),
        )
        curr.execute(
            "UPDATE option SET counter = counter - 1 WHERE id = %s",
            (old_option_id,),
        )
    else:
        curr.execute(
            "INSERT INTO vote (username, option) VALUES (%s, %s)",
            (
                username,
                option_id,
            ),
        )

    # update the counter
    curr.execute(
        "UPDATE option SET counter = counter + 1 WHERE id = %s",
        (option_id,),
    )

    conn.close()
    return jsonify({"message": "Success", "status": 200})


@app.route("/createPool", methods=["POST"])
def create_pool():
    conn = get_connection()
    conn.set_session(autocommit=True)
    data = request.get_json()

    text = data.get("text")
    due_date = data.get("due_date")
    admin = data.get("admin")
    admin = decrypt_username(admin)
    team = data.get("team")
    options = data.get("options")
    members = []

    # parse the due date
    due_date = datetime.datetime.strptime(due_date, "%Y-%m-%d").date()

    # create the pool
    cur = conn.cursor()
    cur.execute(
        "INSERT INTO survey (text, due_date) VALUES (%s, %s) RETURNING id",
        [text, due_date],
    )
    survey_id = cur.fetchone()[0]

    # update the sended_by table
    cur.execute(
        "INSERT INTO sended_by (admin, team, survey) VALUES (%s, %s, %s)",
        [admin, team, survey_id],
    )

    # insert the options
    for option_text in options:
        cur.execute(
            "INSERT INTO option (survey, text) VALUES (%s, %s)",
            [survey_id, option_text],
        )

    # Register and send notification
    query_get_team = "SELECT name FROM team WHERE id = %s"
    params_get_team = (team,)
    cur.execute(query_get_team, params_get_team)
    (team_name,) = cur.fetchone()
    team_name = str(team_name).strip()

    query_get_members = "SELECT username FROM joinTeam WHERE team = %s"
    params_get_members = (team,)
    cur.execute(query_get_members, params_get_members)
    members = cur.fetchall()

    for member in members:
        (user,) = member

        if user != admin:
            query_notification = "INSERT INTO notification (date, content, type, read, username) VALUES (%s,%s,%s,%s,%s)"
            params_notification = (
                datetime.datetime.now(),
                f"{admin} of team {team_name} created a new survey!",
                "survey",
                False,
                user,
            )
            cur.execute(query_notification, params_notification)

            socketio.emit("survey_notification", "", room=user)

    conn.close()
    return jsonify({"message": "Success", "status": 200})


# check if already voted in a survey
@app.route("/home/teams/team/survey", methods=["GET"])
def has_voted():
    conn = get_connection()
    conn.set_session(autocommit=True)
    curr = conn.cursor()
    survey = request.args.get("survey")
    username = request.args.get("username")

    print("SURVEY ID", survey, "\n\n")

    curr.execute(
        "SELECT option FROM vote WHERE username = %s",
        (username,),
    )
    options = curr.fetchall()
    for option in options:
        curr.execute("SELECT survey FROM option WHERE id=%s", (option[0],))
        survey_res = curr.fetchone()
        print(survey, survey_res[0])
        if int(survey) == int(survey_res[0]):
            conn.close()
            return jsonify("true"), 200

    conn.close()
    return jsonify("false"), 200


@app.route("/getSurveys", methods=["GET"])
def get_surveys():
    conn = get_connection()
    conn.set_session(autocommit=True)
    team_id = request.args.get("team_id")
    username = request.args.get("username")
    username = username.replace(" ", "+")
    username = decrypt_username(username)

    # get the surveys
    cur = conn.cursor()
    cur.execute(
        "SELECT survey.id, survey.text, survey.due_date, sended_by.admin FROM survey JOIN sended_by ON survey.id = sended_by.survey WHERE sended_by.team = %s AND survey.due_date >= %s ORDER BY id ASC ",
        [team_id, date.today()],
    )
    surveys = cur.fetchall()

    result = []
    for survey in surveys:
        survey_id, survey_text, due_date, survey_author = survey
        # get the options
        cur.execute(
            "SELECT id, text, counter FROM option WHERE survey = %s ORDER BY option.id ASC",
            (survey_id,),
        )
        options = cur.fetchall()

        # get the user's vote
        cur.execute(
            "SELECT option FROM vote WHERE username = %s AND option IN (SELECT id FROM option WHERE survey = %s)",
            [username, survey_id],
        )
        vote = cur.fetchone()
        vote_option_id = vote[0] if vote else None

        result.append(
            {
                "survey_id": survey_id,
                "survey_text": survey_text,
                "due_date": due_date.strftime("%Y-%m-%d"),
                "options": [
                    {
                        "option_id": option[0],
                        "option_text": option[1],
                        "option_votes": option[2],
                    }
                    for option in options
                ],
                "user_voted_this_option_id": vote_option_id,
                "survey_author": survey_author,
            }
        )

    conn.close()
    return jsonify(result)


############################ END REST APIs ####################################

if __name__ == "__main__":
    # app.run(debug=True, host="localhost", port=5000)
    local = "localhost"
    docker = "0.0.0.0"
    socketio.run(app, host=docker, port=5000, debug=True)
