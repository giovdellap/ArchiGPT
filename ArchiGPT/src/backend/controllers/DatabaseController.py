from flask import request, jsonify
from flask import jsonify, request

from handlers.db_handler import DBHandler


def create_database():
    try:
        if 'db_name' not in request.form or 'collection_name' not in request.form:
            return jsonify({"message": "form-data missing"}), 400
        handler = DBHandler()
        response = handler.create_database(
            db_name=request.form['db_name'], 
            collection_name=request.form['collection_name'])

        return jsonify({"DB_response": response}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": str(e)}), 500


def testDB():
    print('prova')
    try:
        handler = DBHandler()
        response = handler.testDB()
        print('res', response)
        return jsonify({'number': str(response)}), 200
    except Exception as e:
        print(str(e))
        return jsonify({'error': str(e)}), 500
