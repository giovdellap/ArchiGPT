from flask import Flask 
from flask import jsonify
from flask import request
from flask_cors import CORS
import requests
import mysql.connector
import json

app = Flask(__name__)
CORS(app)

def dbConnect():
    #MySQL connection config
    config = {
        'user' : 'root',
        'password' : 'root',
        'host' : 'db',
        'port' : '3306',
        'database' : 'rentYourExpert',
    }
    db = mysql.connector.connect(**config)
    return db

@app.route('/catalogue/<id>', methods=['POST'])
def sendRequest(id):

    db = dbConnect()
    cursor = db.cursor()
    token = request.headers.get('Authorization').split(";")
    customer_id = token[0]
    worker_id = id
    
    
    query = """SELECT * FROM request WHERE customer_id='{customer_id}' AND worker_id='{worker_id}'""".format(
        customer_id=customer_id,
        worker_id = worker_id
        )
    cursor.execute(query)
    if(cursor.fetchone()):
        cursor.close()
        db.close()
        return "Request already sent, cannot send again", 400

   
   
    query = """INSERT INTO request (customer_id, worker_id, accepted)
    VALUES ('{customer_id}', '{worker_id}',0);""".format(
        worker_id = worker_id, 
        customer_id = customer_id)
    
    
    
    cursor = db.cursor()
    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(id),200    

@app.route('/worker_profile/<id>/request/<id2>', methods=['GET','POST'])
def worker_manageRequest(id,id2):
    token = request.headers.get('Authorization').split(";")
    worker_id = id
    request_id = id2

    db = dbConnect()

    accepted = request.form['accepted']
    if (accepted == '0'):
        query = """DELETE FROM request WHERE id = {request_id}""".format(request_id = request_id)
    else:
        query = """UPDATE request SET accepted = {accepted}
                WHERE id = '{request_id}'""".format(accepted = accepted, request_id = request_id)
    
    cursor = db.cursor()
    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(id),200  


#DELETE REQUEST
@app.route('/customer_profile/<id>/request/<id2>', methods=['DELETE'])
def customer_deleteRequest(id, id2):
    token = request.headers.get('Authorization').split(";")
    customer_id = token[0]
    request_id = id2

    db = dbConnect()
    cursor = db.cursor()
    query = """DELETE FROM request WHERE id = {request_id}""".format(request_id = request_id)
    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return ("Richiesta eliminata con successo id: "+str(id)), 200  












