import re
from flask import Flask, render_template, request, redirect, url_for, session 
from flask import jsonify
import mysql.connector
from flask_cors import CORS
import json


app = Flask(__name__, template_folder='templates')
app.secret_key = "super secret key"
CORS(app)


#### WORKER LOGIN 
@app.route('/worker_login/', methods=['GET', 'POST'])
def login():

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
        
        cursor.execute('SELECT * FROM worker WHERE email = %s AND password = %s ', (email, password))

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
            response['auth_token'] = str(user[0])+";"+str(email)+";"+str(password)+";"+"W"

        else:
            # Account doesnt exist or email/password incorrect
            response['message'] = 'failure'
            
    # Output message if something goes wrong...
    return jsonify(response)



#### WORKER REGISTER 
@app.route('/worker_register/', methods=['GET', 'POST'])
def register():
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
        profession = request.form['profession']
        location = request.form['location']
        description = request.form['description']
        phone = request.form['phone']
        address = request.form['address']
        image_url = 'https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png'
        
        
        # Check if account exists using MySQL
        cursor.execute('SELECT * FROM worker WHERE email = %s', (email,))
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
            cursor.execute('INSERT INTO worker (name, surname, profession, location, description, email, phone, address, available, password, image_url) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, 1, %s, %s)', (name, surname, profession, location, description, email, phone, address, password, image_url))
            db.commit()
            response['message'] = 'You have successfully registered!'
        
    elif request.method == 'POST':
        # Form is empty... (no POST data)
         response['message'] = 'Please fill out the form!'
    # Show registration form with message (if any)
    return jsonify(response)


@app.route('/worker_profile/<id>', methods=['GET'])
def getWorkerProfile(id):
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


    #QUERY TO RETRIVE INFORMATION ABOUT THE WORKER
    query_info = "SELECT * FROM worker WHERE id = {id}".format(id = id)
    cursor.execute(query_info)

    #jsonifying 
    row_headers = [x[0] for x in cursor.description] #this will extract row headers
    rv = cursor.fetchall()
    json_data_info = []
    for result in rv:
        json_data_info.append(dict(zip(row_headers,result)))

    return json.dumps(json_data_info)


@app.route('/worker_profile/<id>', methods=['PUT'])
def updateWorkerProfile(id):
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
    profession = request.form['profession']
    location = request.form['location']
    description = request.form['description']
    phone = request.form['phone']
    address = request.form['address']
    available = request.form['available']

    query = """ UPDATE worker 
                SET name = '{name}', surname = '{surname}', profession = '{profession}',
                location = '{location}', description = '{description}', email = '{email}',
                phone = {phone}, address = '{address}', available = {available}
                WHERE id = {id}
            """.format(
                name = name, 
                surname = surname, 
                profession   = profession, 
                location = location,
                description = description, 
                email = email, 
                phone = phone, 
                address = address, 
                available = available,
                
                id = id 
            )
    cursor.execute(query)
    db.commit()

    return "Profile updated succesfully", 200   
        



@app.route('/worker_profile/<id>/pending_requests', methods=['GET'])
def getWorkerPendingRequests(id):
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
    
    #QUERY TO RETRIEVE REQUESTS SENT TO THE WORKER 
    query_requests = "SELECT request.id, name, surname, username, image_url, accepted FROM request, customer WHERE request.worker_id={id} AND request.customer_id = customer.id AND accepted = '0'".format(id=id)
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

    return  json.dumps(json_data_req, indent=4, sort_keys=True, default=str)


@app.route('/worker_profile/<id>/accepted_requests', methods=['GET'])
def getWorkerAcceptedRequests(id):
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
    
    #QUERY TO RETRIEVE REQUESTS SENT TO THE WORKER 
    query_requests = "SELECT request.id, name, surname, username, image_url, accepted FROM request, customer WHERE request.worker_id={id} AND request.customer_id = customer.id AND accepted = '1'".format(id=id)
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

    return  json.dumps(json_data_req, indent=4, sort_keys=True, default=str)


if __name__ == "__main__":
    app.run(debug = True, host = '0.0.0.0', port = 5001)
    
