from flask import request, jsonify
from flask import jsonify, request

from foundation.db_handler import DBHandler



def create_database():
    try:
        if 'name' not in request.form:
            return jsonify({"message": "Name and model are required in the form-data"}), 400
        name = request.form['name']
        handler = DBHandler()
        response = handler.create_database(name=name)
        handler.shutdown_db_client()
        return jsonify({"DB_response": response}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": e}), 500
    
def testDB():
    try:
        handler = DBHandler()
        response = handler.testDB()
        return response, 200
    except Exception as e:
        return jsonify({"message": e}), 500
