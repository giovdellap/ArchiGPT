import json
from flask import request, jsonify

from utils.object_factory import getBlankProjectStatus, getBlankProjectSystem
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
        
        name = request.form['project_name']

        # Collection creation
        handler = DBHandler()
        handler.database_setup()
        handler.create_collection(name)
   
        system = getBlankProjectSystem()
        status = getBlankProjectStatus()
        
        handler.addDocument(name, system)
        handler.addDocument(name, status)
        
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
    

def getStatus():
    try:
        project_name = request.args.get('project_name')
        if not project_name:
            return jsonify({"message": "project_name missing"}), 400

        handler = DBHandler()
        handler.database_setup()
        db_status = handler.getStatus(project_name)
        status = json.loads(db_status)

        return jsonify(status['data']), 200

    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    

def getSystem():
    try:
        project_name = request.args.get('project_name')
        if not project_name:
            return jsonify({"message": "project_name missing"}), 400

        handler = DBHandler()
        handler.database_setup()
        db_system = handler.getSystem(project_name)
        system = json.loads(db_system)

        return jsonify(system['data']), 200

    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500