import re
from flask import Flask, render_template, request, redirect, url_for, session 
from flask import jsonify
import mysql.connector
from flask_cors import CORS
import json


app = Flask(__name__, template_folder='templates')
app.secret_key = "super secret key"
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


#### CUSTOMER REGISTER 
@app.route('/register_customer/', methods=['GET', 'POST'])
def register_customer():
    #MySQL connection config
    config = {
        'user' : 'root',
        'password' : 'root',
        'host' : 'db',
        'port' : '3306',
        'database' : 'rentYourExpert',
    }
    db = mysql.connector.connect(**config)
    cursor = db.cursor()
    response = {'message': ''}
    # Check if "email", "password" and "email" POST requests exist (user submitted form)
    if request.method == 'POST' and 'email' in request.form and 'password' in request.form:
        # Create variables for easy access
        email = request.form['email']
        password = request.form['password']
        name = request.form['name']
        surname = request.form['surname']
        username = request.form['username']
        image_url = 'https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png'
        
        
        # Check if account exists using MySQL
        cursor.execute('SELECT * FROM customer WHERE email = %s', (email,))
        account = cursor.fetchone()
        # If account exists show error and validation checks
        if account:
            response['message'] = 'Account already exists!'
        elif not re.match(r'[^@]+@[^@]+\.[^@]+', email):
            response['message'] = 'Invalid email address!'
        elif not re.match(r'[A-Za-z0-9]+', email):
            response['message'] = 'email must contain only characters and numbers!'
        elif not email or not password or not email:
            response['message'] = 'Please fill out the form!'
        else:
            # Account doesnt exists and the form data is valid, now insert new account into user table
            cursor.execute('INSERT INTO customer (username, name, surname, email, password, isAdmin, image_url) VALUES (%s, %s, %s, %s, %s, 0, %s)', (username, name, surname, email, password, image_url))
            db.commit()
            response['message'] = 'You have successfully registered!'
        
    elif request.method == 'POST':
        # Form is empty... (no POST data)
         response['message'] = 'Please fill out the form!'
    # Show registration form with message (if any)
    return jsonify(response)


#### CUSTOMER LOGIN 
@app.route('/login_customer/', methods=['GET', 'POST'])
def login_customer():

    #MySQL connection config
    config = {
        'user' : 'root',
        'password' : 'root',
        'host' : 'db',
        'port' : '3306',
        'database' : 'rentYourExpert',
    }
    db = mysql.connector.connect(**config)
    cursor = db.cursor()
    response = {'message': ''}
    
    if request.method == 'POST':
        # Create variables for easy access
        email = request.form.get('email')
        password = request.form.get('password')
    
        cursor.execute('SELECT * FROM customer WHERE email = %s AND password = %s ', (email, password))

        # Fetch one record and return result
        user = cursor.fetchone()
        # If account exists in user table in out database
        if user:
            # Create session data, we can access this data in other routes
            session['loggedin'] = True
            session['id'] = user[0]
            session['email'] = user[6]
            # Redirect to home page
            response['message'] = 'success'
            response['auth_token'] = str(user[0])+";"+str(email)+";"+str(password)+";"+"C"

        else:
            # Account doesnt exist or email/password incorrect
            response['message'] = 'failure'
            
    # Output message if something goes wrong...
    return jsonify(response)

@app.route('/customer_profile/<id>', methods=['GET'])
def getCustomerProfile(id):
    #MySQL connection config
    config = {
        'user' : 'root',
        'password' : 'root',
        'host' : 'db',
        'port' : '3306',
        'database' : 'rentYourExpert',
    }
    db = mysql.connector.connect(**config)
    cursor = db.cursor()


    #QUERY TO RETRIVE INFORMATION ABOUT THE CUSTOMER
    query_info = "SELECT id, username, name, surname, email, image_url, password FROM customer WHERE id = {id}".format(id = id)
    cursor.execute(query_info)

    #jsonifying 
    row_headers = [x[0] for x in cursor.description] #this will extract row headers
    rv = cursor.fetchall()
    json_data_info = []
    for result in rv:
        json_data_info.append(dict(zip(row_headers,result)))
    return json.dumps(json_data_info)


@app.route('/customer_profile/<id>', methods=['PUT'])
def updateCustomerProfile(id):
    #MySQL connection config
    config = {
        'user' : 'root',
        'password' : 'root',
        'host' : 'db',
        'port' : '3306',
        'database' : 'rentYourExpert',
    }
    db = mysql.connector.connect(**config)
    cursor = db.cursor()

    email = request.form['email']
    name = request.form['name']
    surname = request.form['surname']
    username = request.form['username']

    query = """ UPDATE customer 
                SET name = '{name}', surname = '{surname}', email = '{email}',
                username = '{username}'
                WHERE id = {id}
            """.format(
                name = name, 
                surname = surname, 
                email = email, 
                username = username,
                id = id 
            )
    cursor.execute(query)
    db.commit()

    return "Profile updated succesfully", 200   


@app.route('/customer_profile/<id>/pending_requests', methods=['GET'])
def getCustomerPendingRequests(id):
    #MySQL connection config
    config = {
        'user' : 'root',
        'password' : 'root',
        'host' : 'db',
        'port' : '3306',
        'database' : 'rentYourExpert',
    }
    db = mysql.connector.connect(**config)
    cursor = db.cursor()
    
    #QUERY TO RETRIEVE REQUESTS MADE BY THE CUSTOMER 
    query_requests = "SELECT request.id, request.customer_id, request.worker_id,  name, surname, profession, image_url, accepted FROM request, worker WHERE request.customer_id={id} AND request.worker_id = worker.id AND accepted = '0'".format(id=id)
    cursor.execute(query_requests)

    #jsonifying 
    row_headers = [x[0] for x in cursor.description] #this will extract row headers
    rv = cursor.fetchall()
    json_data_req = []
    for result in rv:
        json_data_req.append(dict(zip(row_headers,result)))

    #closing the connection to the database
    cursor.close()
    db.close()

    return json.dumps(json_data_req, indent=4, sort_keys=True, default=str)


@app.route('/customer_profile/<id>/accepted_requests', methods=['GET'])
def getCustomerAcceptedRequests(id):
    #MySQL connection config
    config = {
        'user' : 'root',
        'password' : 'root',
        'host' : 'db',
        'port' : '3306',
        'database' : 'rentYourExpert',
    }
    db = mysql.connector.connect(**config)
    cursor = db.cursor()
    
    #QUERY TO RETRIEVE REQUESTS MADE BY THE CUSTOMER 
    query_requests = "SELECT request.id, request.customer_id, request.worker_id, name, surname, profession, image_url, accepted FROM request, worker WHERE request.customer_id={id} AND request.worker_id = worker.id AND accepted = '1'".format(id=id)
    cursor.execute(query_requests)

    #jsonifying 
    row_headers = [x[0] for x in cursor.description] #this will extract row headers
    rv = cursor.fetchall()
    json_data_req = []
    for result in rv:
        json_data_req.append(dict(zip(row_headers,result)))

    #closing the connection to the database
    cursor.close()
    db.close()

    return json.dumps(json_data_req, indent=4, sort_keys=True, default=str)


   






