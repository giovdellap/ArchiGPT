import json
from flask import current_app, request, jsonify
import requests

from handlers.db_handler import DBHandler

def getAllProject():
    try:
        handler = DBHandler()
        list_projects = handler.getAllProjects()
        
        return jsonify({"list_projects": list_projects}), 200
        
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    

def createProject():
    try:
        print(request.form)
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        # if 'user_stories' not in request.files:
        #     return jsonify({"message": "user_stories missing"}), 400
        name = request.form['project_name']
        # user_stories = request.files['user_stories']
        # content = user_stories.readlines();
        # print(content)

        # Collection creation and User Stories document added
        handler = DBHandler()
        handler.create_collection(name)
        # handler.addDocument(name, {'user_stories': content})
        
        return jsonify({"message": "project created"}), 200
        
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    
    
def deleteProject():
    try:
        if not request.json['project_name']:
            return jsonify({"message": "project_name missing"}), 400
        name = request.json['project_name']

        handler = DBHandler()
        handler.delete_collection(name)
        
        return jsonify({"message": "project deleted"}), 200
        
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