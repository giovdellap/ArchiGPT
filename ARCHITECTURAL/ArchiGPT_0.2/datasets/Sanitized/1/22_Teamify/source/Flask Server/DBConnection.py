import psycopg2
import random

user="postgres"
psw="postgres"

hosts = ["db", "db-backup"]

def get_connection():
    try:
        conn = psycopg2.connect(host=hosts[0], port=5432, dbname="postgres", user=user, password=psw, connect_timeout=3)
        return conn
    except:
        try:
            conn = psycopg2.connect(host=hosts[1], port=5432, dbname="postgres", user=user, password=psw, connect_timeout=3)
            return conn
        except:
            print("Error during db connection\n")
            return None