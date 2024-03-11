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

@app.route('/customers', methods=['GET'])
def getAllCustomers():
    #connecting to the database
    db = dbConnect()

    query = "SELECT * FROM customer"
    #executing the query
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


@app.route('/customers', methods=['POST'])
def addCustomer():

    #connecting to the database
    db = dbConnect()
    email = request.form['email']
    password = request.form['password']
    name = request.form['name']
    surname = request.form['surname']
    username = request.form['username']
    image_url = 'https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png'

    #executing the query
    cursor = db.cursor()
    cursor.execute('INSERT INTO customer (username, name, surname, email, password, isAdmin, image_url) VALUES (%s, %s, %s, %s, %s, 0, %s)', (username, name, surname, email, password, image_url))

    db.commit()
    
    lastId = cursor.lastrowid

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(lastId),200

@app.route('/customers/<id>', methods=['GET'])
def getCustomer(id):
    #connecting to the database
    db = dbConnect()

    query = "SELECT * FROM customer WHERE id={id}".format(id=id)
    #executing the query
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


@app.route('/customers/<id>', methods=['DELETE'])
def delCustomer(id):
    
    #connecting to the database
    db = dbConnect()

    query = """DELETE FROM customer WHERE id = {id}""".format(id=id)
    #executing the query
    cursor = db.cursor()
    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(id),200

@app.route('/workers', methods=['GET'])
def getAllWorkers():
    #connecting to the database
    db = dbConnect()

    query = "SELECT * FROM worker "
    #executing the query
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

@app.route('/workers/<id>', methods=['GET'])
def getWorker(id):
    #connecting to the database
    db = dbConnect()

    query = "SELECT * FROM worker WHERE id={id}".format(id=id)
    #executing the query
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

@app.route('/workers/<id>', methods=['DELETE'])
def delWorker(id):
    
    #connecting to the database
    db = dbConnect()

    query = """DELETE FROM worker WHERE id = {id}""".format(id=id)
    #executing the query
    cursor = db.cursor()
    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(id),200


@app.route('/workers', methods=['POST'])
def addWorker():

    #connecting to the database
    db = dbConnect()
    email = request.form['email']
    password = request.form['password']
    name = request.form['name']
    surname = request.form['surname']
    profession = request.form['profession']
    location = request.form['location']
    description = request.form['description']
    phone = request.form['phone']
    address = request.form['address']
    image_url = 'https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png'

    

    #executing the query
    cursor = db.cursor()
    cursor.execute('INSERT INTO worker (name, surname, profession, location, description, email, phone, address, available, password, image_url) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, 1, %s, %s)', (name, surname, profession, location, description, email, phone, address, password, image_url))
    db.commit()
    
    lastId = cursor.lastrowid

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(lastId),200


@app.route('/requests', methods=['GET'])
def getAllRequests():
    #connecting to the database
    db = dbConnect()

    query = "SELECT id, customer_id, worker_id, created_at FROM request"
    #executing the query
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

    return json.dumps(json_data, indent=4, sort_keys=True, default=str)

@app.route('/requests/<id>', methods=['GET'])
def getRequest(id):
    #connecting to the database
    db = dbConnect()

    query = "SELECT id, customer_id, worker_id, created_at FROM request WHERE id = {id}".format(id=id)
    #executing the query
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

    return json.dumps(json_data, indent=4, sort_keys=True, default=str)

@app.route('/requests', methods=['POST'])
def addRequest():
    #connecting to the database
    db = dbConnect()
    customer_id = request.form.get('customer_id')
    worker_id = request.form.get('worker_id')
    accepted =  request.form.get('accepted')

    cursor = db.cursor()


    query = """SELECT * from customer WHERE id = '{customer_id}'""".format(customer_id = customer_id)
    cursor.execute(query)
    if (not cursor.fetchone()):
        cursor.close()
        db.close()
        return "The customer does not exist", 400


    query = """SELECT * from worker WHERE id = '{worker_id}'""".format(worker_id = worker_id)
    cursor.execute(query)
    if (not cursor.fetchone()):
        cursor.close()
        db.close()
        return "The worker does not exist", 400


    query = """SELECT * FROM request WHERE customer_id='{customer_id}' AND worker_id='{worker_id}'""".format(
        customer_id=customer_id,
        worker_id = worker_id
        )
    cursor.execute(query)

    if(cursor.fetchone()):
        cursor.close()
        db.close()
        return "Request already sent, cannot send again", 400


    query = """INSERT INTO request(customer_id, worker_id, accepted) 
                VALUES ('{customer_id}','{worker_id}',0)""".format(
                    customer_id = customer_id,
                    worker_id = worker_id,
                )

    #executing the query
    cursor.close()
    cursor = db.cursor()
    cursor.execute(query)
    db.commit()
    
    lastId = cursor.lastrowid

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(lastId),200

@app.route('/requests/<id>', methods=['DELETE'])
def delRequest(id):
    
    #connecting to the database
    db = dbConnect()

    query = """DELETE FROM request WHERE id = {id}""".format(id=id)
    
    #executing the query
    cursor = db.cursor()
    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return str(id),200

@app.route('/login_admin/', methods=['GET', 'POST'])
def login_admin():
    #connecting to the database
    db = dbConnect()
    cursor = db.cursor()

    response = {'message': ''}
    
    if request.method == 'POST':
        # Create variables for easy access
        email = request.form.get('email')
        password = request.form.get('password')
    
        cursor.execute('SELECT * FROM customer WHERE email = %s AND password = %s AND isAdmin = 1', (email, password))

        # Fetch one record and return result
        user = cursor.fetchone()
        # If account exists in user table in out database
        if user:
            # Redirect to home page
            response['message'] = 'success'
            response['auth_token'] = str(user[0])+";"+str(email)+";"+str(password)+";"+"A"

        else:
            # Account doesnt exist or email/password incorrect
            response['message'] = 'failure'
            
    # Output message if something goes wrong...
    return jsonify(response)


@app.route('/catalogue/<worker_id>/<id>', methods=['DELETE'])
def deleteReview(worker_id, id):
    db = dbConnect()
    cursor = db.cursor()

    token = request.headers.get('Authorization').split(";")
    

    query = """ DELETE FROM review 
            WHERE id = '{id}'  AND worker_id = '{worker_id}' """.format(
        id = id,
        worker_id = worker_id
    )

    cursor.execute(query)
    db.commit()

    #closing the connection to the database
    cursor.close()
    db.close()

    return "Recensione eliminata con successo",200
