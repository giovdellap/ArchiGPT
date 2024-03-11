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


#to see every question done to that <id> worker
@app.route('/catalogue/<id>', methods=['GET'])
def getQeAs(id):
    #connecting to the database
    db = dbConnect()

    query = """SELECT questionanswer.id,  worker_id, customer_id, username, image_url, question, answer FROM customer, questionanswer 
            WHERE worker_id = {id} AND customer.id = customer_id""".format(id = id)
    

    cursor = db.cursor()
    cursor.execute(query)

    #jsonifying 
    row_headers = [x[0] for x in cursor.description] #this will extract row headers
    rv = cursor.fetchall()
    json_data = []
    for result in rv:
        json_data.append(dict(zip(row_headers,result)))

    #closing the connection to the database
    cursor.close()
    db.close()

    return json.dumps(json_data)

@app.route('/catalogue/<id>', methods = ['POST'])
def addQeA(id):
    token = request.headers.get('Authorization').split(";")
    customer_id = token[0]
    worker_id = id
    question = request.form['question'] 

    #connecting to the database
    db = dbConnect()

    query = """INSERT INTO questionanswer (customer_id, worker_id, question, answer)
            VALUES ("{customer_id}", "{worker_id}","{question}", NULL)""".format(
                customer_id = customer_id,
                worker_id = worker_id,
                question = question,
            )

    cursor = db.cursor()
    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(id),200


@app.route('/worker_profile/<id>/qea/<id2>', methods=['POST'])
def worker_addAnswer(id,id2):
    token = request.headers.get('Authorization').split(";")
    worker_id = id
    request_id = id2
    answer = request.form['answer']

    db = dbConnect()

    query = """UPDATE questionanswer SET answer = '{answer}' WHERE id = {id2}""".format(
        answer = answer,
        id2 = id2
    )

    cursor = db.cursor()
    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(id),200  

@app.route('/question/<id>', methods=['DELETE'])
def delQuestion(id):
    
    #connecting to the database
    db = dbConnect()

    query = """DELETE FROM questionanswer WHERE id = {id}""".format(id=id)
    
    #executing the query
    cursor = db.cursor()
    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(id),200

@app.route('/worker_profile/<id>/qea/<id2>', methods=['PUT'])
def editAnswer(id,id2):
    answer = request.form['answer']

    #connecting to the database
    db = dbConnect()

    query = """UPDATE questionanswer SET answer = '{answer}' WHERE id = {id2}""".format(
        answer = answer,
        id2 = id2
    )

    cursor = db.cursor()
    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return  str(id),200  