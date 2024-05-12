from datetime import datetime, timedelta
from DBConnection import get_connection
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
import base64


def get_current_week_range():
    today = datetime.now()
    current_day_of_week = today.weekday()

    start_of_week = today - timedelta(days=current_day_of_week)
    end_of_week = start_of_week + timedelta(days=6)

    return start_of_week, end_of_week


def convert_date(end_date, duration):
    end_datetime = datetime.strptime(end_date, "%Y-%m-%d %H:%M:%S")

    # Convert amount_of_time to integer (minutes)
    minutes = int(duration)

    # Add the specified amount of time to end_datetime
    new_end_datetime = end_datetime + timedelta(minutes=minutes)

    # Format new_end_datetime back to the desired format
    new_end_date = new_end_datetime.strftime("%Y-%m-%d %H:%M:%S")

    return new_end_date


def convert_one_date(date):
    end_datetime = datetime.strptime(date, "%Y-%m-%d")
    return end_datetime


def get_teams():
    teams = []

    # DB setup
    conn = get_connection()
    conn.set_session(autocommit=True)
    if conn is None:
        print("[ERROR] DB Connection failed.")
        exit()

    curr = conn.cursor()

    query_retrieve_teams = "SELECT id, name FROM team"
    curr.execute(query_retrieve_teams)

    retrieved_teams = curr.fetchall()
    if len(retrieved_teams) != 0:
        query_retrieve_members = "SELECT user FROM joinTeam WHERE team = %s"

        for team in retrieved_teams:
            params = (team[0],)
            curr.execute(query_retrieve_members, params)

            retrieved_users = curr.fetchall()
            if len(retrieved_users) != 0:
                teams.append([team[1], retrieved_users])
            else:
                teams.append([team[1], []])

    return teams


def encrypt_username(username):
    # Input string to be encrypted (padding to adjust length)
    input_string = username.rjust(32)
    # Secret key (pw)
    key = b"em45E0z!UA56MOw19Og4EkBUnW35qYB%"
    # Encrypt the string
    cipher = AES.new(key, AES.MODE_ECB)
    encrypted_string = cipher.encrypt(pad(input_string.encode(), AES.block_size))
    return base64.b64encode(encrypted_string).decode()


def decrypt_username(encryptedUsername):
    key = b"em45E0z!UA56MOw19Og4EkBUnW35qYB%"
    if encryptedUsername:
        encryptedUsername = encryptedUsername.replace(" ", "+")
        cipher = AES.new(key, AES.MODE_ECB)
        # Decrypt the encrypted string
        decrypted_string = unpad(
            cipher.decrypt(base64.b64decode(encryptedUsername)), AES.block_size
        )
        # Print the original and decrypted strings
        return decrypted_string.decode().strip()

    return None


def get_teams_of(username):
    teams = []
    
    conn = get_connection()
    conn.set_session(autocommit=True)
    if conn is None:
        print("[ERROR] DB Connection failed.")
        exit()
    curr = conn.cursor()
    
    query = "select name from team, joinTeam WHERE id=team and username = %s"
    params = (username,)
    
    curr.execute(query, params)
    teams = curr.fetchall()
    
    if len(teams) > 0:
        return teams
    
    return []


def get_team_name(id):
    conn = get_connection()
    conn.set_session(autocommit=True)
    if conn is None:
        print("[ERROR] DB Connection failed.")
        exit()
    curr = conn.cursor()
    
    query = "select name from team where id = %s"
    params = (id,)
    
    curr.execute(query, params)
    (name,) = curr.fetchone()
    
    return name