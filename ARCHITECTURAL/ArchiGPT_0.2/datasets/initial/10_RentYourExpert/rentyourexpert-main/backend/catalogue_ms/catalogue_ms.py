from flask import Flask 
from flask import request 
from flask import jsonify
import mysql.connector
import json
from flask_cors import CORS
from flask import request

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

@app.route('/catalogue', methods=['GET'])
def getAllWorkers():
    #connecting to the database
    db = dbConnect()    

    filter = request.args.get('filter')
    query = "SELECT * FROM worker "

    if (filter):
        query += """ WHERE name LIKE '%{filter}%' OR
                    surname LIKE '%{filter}%' OR
                    profession LIKE '%{filter}%' OR
                    location LIKE '%{filter}%' OR
                    description LIKE '%{filter}%';
                """.format(filter = filter)

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

@app.route('/catalogue/<id>', methods=['GET'])
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


if __name__ == "__main__":
    app.run(debug = True, host = '0.0.0.0', port = 5000)