import json
from flask import current_app, request, jsonify
import requests

from handlers.db_handler import DBHandler


def genArchi_1():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        
        #ASSISTANT INTERROGATION
        message = requests.post(
            current_app.config['API_HANDLER'] + '/interrogation/interrogate',
            data={
                'ass_name': "User Stories Analyzer",
                #'ass_model': 'gpt-3.5-turbO',
                'ass_model': 'gpt-4o'
                
            }
        )
    
        #SAVE ON DB
        handler = DBHandler()
        handler.database_setup()
        handler.updateDocument('project_name', 'general', 'User Stories Analyzer', message)
    
        #print(message['content'])
        return message, 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500

def generateDocumentB():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        if 'source' not in request.files:
            return jsonify({"message": "source"}), 400
        print("AAAAAA")
        message = requests.post(
            current_app.config['API_HANDLER'] + '/interrogation/interrogate',
            data={
                'ass_name': "Containers List Generator",
                #'ass_model': 'gpt-3.5-turbO',
                'ass_model': 'gpt-4-turbo-2024-04-09'
                
            },
            files={'ass_ci': request.files['source']}
        )
    
        
        #print(message['content'])
        return message, 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    
def generateEndpoints():
    print('inside function')
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        if 'container_name' not in request.form:
            return jsonify({"message": "container_name missing"}), 400
        if 'service_name' not in request.form:
            return jsonify({"message": "service_name missing"}), 400 
        if 'source' not in request.files:
            return jsonify({"message": "source"}), 400
        
        message = requests.post(
            current_app.config['API_HANDLER'] + '/interrogation/interrogate',
            data={
                'ass_name': "Endpoints Solo Generator",
                #'ass_model': 'gpt-3.5-turbo',
                'ass_model': 'gpt-4-turbo-2024-04-09'                
            },
            files={'ass_ci': request.files['source']}
        )
    
        
        #print(message)
        return message, 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500