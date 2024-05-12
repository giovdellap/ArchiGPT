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


@server.route('/checkout')
def checkout():

    args = request.args
    if "user_id" not  in args:
        return "Bad Request", 400

    db = connectDb()
    cursor = db.cursor()

    #GET CART
    query = """	SELECT p.id , c.quantity 
	FROM cart c JOIN users u ON c.user_id = u.id JOIN products p ON p.id = c.product_id
	WHERE u.id = {id};""".format(id=args['user_id'])

    cursor.execute(query)
    rv = cursor.fetchall()

    for result in rv:
        #FOR EACH PRODUCT
        query = """Place an order
        """
        #Delete it from the cart?
        

    cursor.close()
    db.close()
    return "OK"



if __name__ == '__main__':
    server.run()