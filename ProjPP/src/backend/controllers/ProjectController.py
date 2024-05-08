from flask import current_app, request, jsonify
import requests

def createProject():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        name = request.form['project_name']
        user_stories = request.files.items()
        
        
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    

def generateDocumentB():
    try:

        response = requests.post(current_app.config['DEFAULT_PATH'] + "/thread")

        return response.json(), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500