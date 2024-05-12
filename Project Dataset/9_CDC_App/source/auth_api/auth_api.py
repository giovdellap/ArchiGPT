from flask import Flask, jsonify,request
from flask_cors import CORS
import json
import mysql.connector

server = Flask(__name__)
CORS(server)

def connectDb():
    config = {
        'user': 'root',
        'password': 'root',
        'host': 'db',
        'port': '3306',
        'database': 'CDC'
    }
    db = mysql.connector.connect(**config)
    return db


@server.route('/register',methods=['POST'])
def register():

    data = request.get_json(force=True,silent=True)
    if "name" not  in data:
        return "Bad Request", 400

    if "password" not  in data:
        return "Bad Request", 400
    
    if "phone" not  in data:
        return "Bad Request", 400

    if "email" not  in data:
        return "Bad Request", 400
    

    db = connectDb()
    cursor = db.cursor()

    query = """SELECT * FROM users WHERE email='{email}'""".format(
        email = data['email'], 
    )

    cursor.execute(query)
    rv = cursor.fetchall()

    if len(rv) == 0: #Utente non esiste
        query = """INSERT INTO users (name,email,phone,password,role) 
        VALUES ('{name}', '{email}', '{phone}','{password}','u')""".format(
            name = data['name'], 
            email = data['email'],
            phone = data['phone'],
            password = data['password']
        )


        cursor.execute(query)

        db.commit()
        
        cursor.close()
        db.close()
        return "OK",200
    else:
        
        cursor.close()
        db.close()
        return "USER ALREADY EXISTS", 500
    
@server.route('/login',methods=['POST'])
def login():

    data = request.get_json(force=True,silent=True)
    if "email" not  in data:
        return "Bad Request", 400

    if "password" not  in data:
        return "Bad Request", 400
    
    db = connectDb()
    cursor = db.cursor()

    query = """SELECT * FROM users WHERE email='{email}' AND password='{password}'""".format(
        email = data['email'], 
        password = data['password']
    )

    cursor.execute(query)
    rv = cursor.fetchall()

    if len(rv) == 0: #Wrong login

        cursor.close()
        db.close()
        return "Email or Password Not Correct", 500
    else:
        
        token = str(rv[0])
        
        
        cursor.close()
        db.close()
        return token,200

    

if __name__ == '__main__':
    server.run()