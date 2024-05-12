import psycopg2
from DBConnection import get_connection, user, psw
from hashlib import sha256

# Create connection
host = "localhost"
docker = "db"
conn = psycopg2.connect(host="localhost", port=5432, user=user, password=psw)
conn.set_session(autocommit=True)
if not conn:
    print("Error during db connection")
    exit()


# Create database
dropDB = "DROP DATABASE IF EXISTS teamify"
createDB = "CREATE DATABASE teamify"

cur = conn.cursor()
cur.execute(dropDB)

try:
    cur.execute(createDB)
    print("[INFO] DB successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()

conn.close()


# Create tables
conn = get_connection()
if conn is None:
    print("Error in db connection")
    exit()
cur = conn.cursor()


# Table 'member'
dropMember = "DROP TABLE IF EXISTS member"
member = """CREATE TABLE member (
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(100) PRIMARY KEY,
    password CHAR(256) NOT NULL
)"""
try:
    cur.execute(dropMember)
    cur.execute(member)
    conn.commit()

    password = sha256(str("ciaociao").encode("utf-8")).hexdigest()
    query = "INSERT INTO member (name, surname, birth_date, email, username, password) VALUES (%s, %s, %s, %s, %s, %s)"
    member_data = (
        "Admin",
        "Admin",
        "2000-01-01",
        "admin@example.com",
        "admin",
        password,
    )

    cur.execute(query, member_data)
    conn.commit()

    print("[INFO] Table 'member' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Create task table
dropTask = "DROP TABLE IF EXISTS task"
task = """CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description TEXT,
    date DATE NOT NULL,
    time TIME NOT NULL,
    status VARCHAR(50) DEFAULT 'not_completed' CHECK (status IN ('completed','not_completed')),
    member VARCHAR REFERENCES member(username),
    type VARCHAR(10) DEFAULT 'personal' CHECK (type IN ('personal', 'event')),
    duration INTEGER DEFAULT 60 CHECK (duration > 0)
)"""

try:
    cur.execute(dropTask)
    cur.execute(task)
    conn.commit()
    print("[INFO] Table 'task' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'notification'
dropNotification = "DROP TABLE IF EXISTS notification CASCADE"
notification = """CREATE TABLE notification (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    content VARCHAR(500),
    type VARCHAR(50) NOT NULL DEFAULT 'message',
    read boolean NOT NULL DEFAULT FALSE,
    username VARCHAR(100),
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE,
   CHECK (
    type IN ('invite', 'message', 'survey', 'event', 'admin', 'modifyevent')
)

)"""
try:
    cur.execute(dropNotification)
    cur.execute(notification)
    conn.commit()

    print("[INFO] Table 'notification' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'team'
dropTeam = "DROP TABLE IF EXISTS team"
team = """CREATE TABLE team (
    id SERIAL UNIQUE,
    name VARCHAR(100) NOT NULL DEFAULT 'unnamed_team',
    description VARCHAR(500),
    color VARCHAR(20),
    CONSTRAINT joinn_pkey
        PRIMARY KEY(name, description)
)"""
try:
    cur.execute(dropTeam)
    cur.execute(team)
    conn.commit()

    print("[INFO] Table 'team' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'join'
dropJoin = "DROP TABLE IF EXISTS joinTeam CASCADE"
join = """CREATE TABLE joinTeam (
    username VARCHAR(100),
    team INT,
    CONSTRAINT join_pkey
        PRIMARY KEY(username, team),
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CONSTRAINT fk_team
        FOREIGN KEY(team)
            REFERENCES team(id)
            ON DELETE CASCADE
)"""
try:
    cur.execute(dropJoin)
    cur.execute(join)
    conn.commit()

    print("[INFO] Table 'join' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'manage'
dropManage = "DROP TABLE IF EXISTS manage CASCADE"
manage = """CREATE TABLE manage (
    admin VARCHAR(100),
    team INT,
    CONSTRAINT manage_pkey
        PRIMARY KEY(admin, team),
    CONSTRAINT fk_admin
        FOREIGN KEY(admin)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CONSTRAINT fk_team
        FOREIGN KEY(team)
            REFERENCES team(id)
            ON DELETE CASCADE
)"""
try:
    cur.execute(dropManage)
    cur.execute(manage)
    conn.commit()

    print("[INFO] Table 'manage' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'invite'
dropInvite = "DROP TABLE IF EXISTS invite CASCADE"
invite = """CREATE TABLE invite (
    username VARCHAR(100),
    admin VARCHAR(100) NOT NULL,
    team INT,
    CONSTRAINT invite_pkey
        PRIMARY KEY(username, team),
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CONSTRAINT fk_admin_team
        FOREIGN KEY(admin, team)
            REFERENCES manage(admin, team)
            ON DELETE CASCADE
)"""
try:
    cur.execute(dropInvite)
    cur.execute(invite)
    conn.commit()

    print("[INFO] Table 'invite' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'includes'
dropIncludes = "DROP TABLE IF EXISTS includes CASCADE"
includes = """CREATE TABLE includes (
    event INT,
    team INT,
    username VARCHAR(100),
    state VARCHAR(50) NOT NULL DEFAULT 'pending',
    CONSTRAINT includes_pkey
        PRIMARY KEY(event, team, username),
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CONSTRAINT fk_team
        FOREIGN KEY(team)
            REFERENCES team(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_event
        FOREIGN KEY(event)
            REFERENCES task(id)
            ON DELETE CASCADE,
    CHECK (
        state = 'accepted'
        OR state = 'rejected'
        OR state = 'pending'
    )
)"""
try:
    cur.execute(dropIncludes)
    cur.execute(includes)
    conn.commit()

    print("[INFO] Table 'includes' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'survey'
dropSurvey = "DROP TABLE IF EXISTS survey CASCADE"
survey = """CREATE TABLE survey (
    id SERIAL PRIMARY KEY,
    text VARCHAR(500) NOT NULL,
    due_date DATE NOT NULL
)"""
try:
    cur.execute(dropSurvey)
    cur.execute(survey)
    conn.commit()

    print("[INFO] Table 'survey' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'sended_by'
dropSendedBy = "DROP TABLE IF EXISTS sended_by CASCADE"
sendedBy = """CREATE TABLE sended_by (
    admin VARCHAR(100) NOT NULL,
    team INT NOT NULL,
    survey INT PRIMARY KEY,
    CONSTRAINT fk_survey
        FOREIGN KEY(survey)
            REFERENCES survey(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_admin_team
        FOREIGN KEY(admin, team)
            REFERENCES manage(admin, team)
            ON DELETE CASCADE
)"""
try:
    cur.execute(dropSendedBy)
    cur.execute(sendedBy)
    conn.commit()

    print("[INFO] Table 'sended_by' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'option'
dropOption = "DROP TABLE IF EXISTS option CASCADE"
option = """CREATE TABLE option (
    survey INT NOT NULL,
    id SERIAL PRIMARY KEY,
    text VARCHAR(500) NOT NULL,
    counter INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_survey
        FOREIGN KEY(survey)
            REFERENCES survey(id)
            ON DELETE CASCADE
)"""
try:
    cur.execute(dropOption)
    cur.execute(option)
    conn.commit()

    print("[INFO] Table 'option' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'vote'
dropVote = "DROP TABLE IF EXISTS vote CASCADE"
vote = """CREATE TABLE vote (
    option INT,
    username VARCHAR(100),
    CONSTRAINT pk_vote
        PRIMARY KEY(option, username),
    CONSTRAINT fk_option
        FOREIGN KEY(option)
            REFERENCES option(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE
)"""
try:
    cur.execute(dropVote)
    cur.execute(vote)
    conn.commit()

    print("[INFO] Table 'vote' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'message'
dropMessage = "DROP TABLE IF EXISTS message CASCADE"
message = """CREATE TABLE message (
    id SERIAL PRIMARY KEY,
    datetime TIMESTAMP NOT NULL,
    content TEXT NOT NULL,
    sender VARCHAR(100) NOT NULL,
    team INT NOT NULL,
    CONSTRAINT fk_team
        FOREIGN KEY(team)
            REFERENCES team(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_sender
        FOREIGN KEY(sender)
            REFERENCES member(username)
            ON DELETE CASCADE
)"""
try:
    cur.execute(dropMessage)
    cur.execute(message)
    conn.commit()

    print("[INFO] Table 'message' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


# Table 'add'
dropAdd = "DROP TABLE IF EXISTS add CASCADE"
add = """CREATE TABLE add (
    personal_task INT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CONSTRAINT fk_task
        FOREIGN KEY(personal_task)
            REFERENCES task(id)
            ON DELETE CASCADE
)"""
try:
    cur.execute(dropAdd)
    cur.execute(add)
    conn.commit()

    print("[INFO] Table 'add' successfully created.")
except Exception as err:
    print("Error: ", err)
    exit()


conn.close()
