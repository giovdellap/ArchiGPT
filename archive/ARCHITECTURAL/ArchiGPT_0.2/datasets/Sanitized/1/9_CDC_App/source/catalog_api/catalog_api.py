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

@server.route('/cart',methods=['GET'])
def cart():

    args = request.args
    if "user_id" not  in args:
        return "Bad Request", 400
    
    
    query = """	SELECT p.id ,p.name, p.description,p.category,p.price, p.image_url, c.quantity 
    FROM cart c JOIN users u ON c.user_id = u.id JOIN products p ON p.id = c.product_id
    WHERE u.id = {id};""".format(id=args['user_id'])
    
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

@server.route('/cart',methods=['POST'])
def addProductToCart():

    data = request.get_json(force=True,silent=True)
    if "user_id" not  in data:
        return "Bad Request", 400

    if "product_id" not  in data:
        return "Bad Request", 400
    
    if "quantity" not  in data:
        return "Bad Request", 400
    

    db = connectDb()
    cursor = db.cursor()

    query = """SELECT * FROM cart WHERE user_id={user_id} AND product_id={product_id}""".format(
        user_id = data['user_id'], 
        product_id = data['product_id']
    )

    cursor.execute(query)
    rv = cursor.fetchall()

    if len(rv) == 0:
        query = """INSERT INTO cart (user_id,product_id,quantity) 
        VALUES ('{user_id}', '{product_id}', '{quantity}')""".format(
            user_id = data['user_id'], 
            product_id = data['product_id'],
            quantity = data['quantity']
        )


        cursor.execute(query)

        db.commit()
        
        cursor.close()
        db.close()
        return "OK",200
    else:
        query = """UPDATE cart SET quantity = {quantity} WHERE user_id = {user_id}  AND product_id = {product_id}""".format(
            user_id = data['user_id'], 
            product_id = data['product_id'],
            quantity = data['quantity']
        )


        cursor.execute(query)

        db.commit()
        
        cursor.close()
        db.close()
        return "OK",200

@server.route('/cart',methods=['DELETE'])
def DeleteProductToCart():

    args = request.args
    if "user_id" not  in args:
        return "Bad Request", 400

    if "product_id" not  in args:
        return "Bad Request", 400
    
    

    db = connectDb()
    cursor = db.cursor()

    query = """DELETE FROM cart WHERE user_id={user_id} AND product_id={product_id}""".format(
        user_id = args['user_id'], 
        product_id = args['product_id']
    )
    db = connectDb()
    cursor = db.cursor()

    cursor.execute(query)

    db.commit()
    cursor.close()
    db.close()
    return "OK", 200
    

if __name__ == '__main__':
    server.run()