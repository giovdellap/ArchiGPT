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


@server.route('/products',methods=['POST'])
def addProduct():

    data = request.get_json(force=True,silent=True)
    if data == None:
        return "Bad Request",400


    if 'name' not in data or 'description' not in data or 'category' not in data or 'price' not in data or 'stock' not in data or 'image_url' not in data:
        return "Bad Request",400


    db = connectDb()
    cursor = db.cursor()

    query = """INSERT INTO products (name, description, category, price, stock, image_url) 
    VALUES ('{name}', '{description}', '{category}', {price}, {stock}, '{image_url}')""".format(
        name = data['name'], 
        description = data['description'],
        category = data['category'],
        price = data['price'],
        stock = data['stock'],
        image_url = data['image_url']
    )
    cursor.execute(query)

    db.commit()

    id = cursor.lastrowid
    cursor.close()
    db.close()
    return str(id),200


@server.route('/products',methods=['GET'])
def getAllProducts():

    args = request.args
    query = """SELECT * FROM products"""


    if "name" in args:
        query += """ WHERE name LIKE '%{name}%'""".format(name = args['name'])

    if "orderby" in args:
        query += " ORDER BY price {order}".format(order=args['orderby'])

    if "limit" in args:
        query += """ LIMIT {l}""".format(l=args["limit"])
    

    db = connectDb()
    cursor = db.cursor()

    cursor.execute(query)
    row_headers=[x[0] for x in cursor.description] #this will extract row headers
    rv = cursor.fetchall()

    json_data=[]
    for result in rv:
        json_data.append(dict(zip(row_headers,result)))

    cursor.close()
    db.close()
    return json.dumps(json_data)

@server.route('/products/<int:id>',methods=['GET'])
def getProduct(id):

    query = """SELECT * FROM products WHERE id={id}""".format(id=id)
    db = connectDb()
    cursor = db.cursor()

    cursor.execute(query)
    row_headers=[x[0] for x in cursor.description] #this will extract row headers
    rv = cursor.fetchall()

    json_data=[]
    for result in rv:
        json_data.append(dict(zip(row_headers,result)))

    cursor.close()
    db.close()
    return json.dumps(json_data)

@server.route('/products/<int:id>',methods=['DELETE'])
def deleteProduct(id):

    query = """DELETE FROM products WHERE id={id}""".format(id=id)
    db = connectDb()
    cursor = db.cursor()

    cursor.execute(query)

    db.commit()
    cursor.close()
    db.close()
    return "OK", 200

if __name__ == '__main__':
    server.run()