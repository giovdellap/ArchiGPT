import json
from flask import current_app, request, jsonify
import requests

from handlers.db_handler import DBHandler

def createProject():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        if 'user_stories' not in request.files:
            return jsonify({"message": "user_stories missing"}), 400
        name = request.form['project_name']
        user_stories = request.files['user_stories']
        content = user_stories.readlines();
        print(content)

        # Collection creation and User Stories document added
        handler = DBHandler()
        handler.create_collection(name)
        handler.addDocument(name, {'user_stories': content})
        
        return jsonify({"message": "project created"}), 200
        
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    

def generateDocumentB():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        if 'source' not in request.files:
            return jsonify({"message": "source"}), 400
        
        message = requests.post(
            current_app.config['API_HANDLER'] + '/interrogation',
            {
                'ass_name': "Containers List Generator",
                'ass_model': 'gpt-3.5-turbo-0125',
                #'ass_model': 'gpt-4-turbo-2024-04-09',
                'ass_ci': request.files['source']
            }
        )
        
        print(message)
        return jsonify(message), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    
