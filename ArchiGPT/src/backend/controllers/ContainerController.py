import json
from flask import current_app, request, jsonify
import requests
import os

from utils.content_factory import ContentFactory
from handlers.container_handler import ContainerHandler
from utils.assistant_name_matcher import getAssistantName, getNextContainerAssistant
from handlers.db_handler import DBHandler


def generateContainer():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        project_name = request.form['project_name']
        if 'assistant' not in request.form:
            return jsonify({"message": "assistant missing"}), 400
        assistant_name = request.form['assistant']
        if 'container' not in request.form:
            return jsonify({"message": "container missing"}), 400
        container_name = request.form['container']
        
        content = ""
        handler = DBHandler()
        handler.database_setup()      
        
        #CONTENT CREATION
        print('CONTENT CREATION')
        contentFactory = ContentFactory(handler, project_name)
        content = contentFactory.getContainerContent(getAssistantName(assistant_name), {"container": container_name})


        #ASSISTANT INTERROGATION
        print('ASSISTANT INTERROGATION')
        message = requests.post(
            current_app.config['API_HANDLER'] + '/interrogation/interrogate',
            data={
                'ass_name': getAssistantName(assistant_name),
                'ass_model': 'gpt-3.5-turbo',
                #'ass_model': 'gpt-4-turbo-2024-04-09',
                'content': content
            }
        )
        result = message.json()['content']
        #result = "provaprova"
        print('Message: ', result)
        
        #UPDATE DB STATUS
        handler.updateContainerStatus(project_name, assistant_name, 'OK', container_name)
        
        #SAVE ON DB
        handler.updateContainer(project_name, container_name, assistant_name, result)
        
        # NEXT ASSISTANT MANAGEMENT
        nextAssistant = getNextContainerAssistant(assistant_name)
        if nextAssistant != "SERVICE":
            handler.updateContainerStatus(project_name, getNextContainerAssistant(assistant_name), 'NEXT', container_name)
        else:
            print('AO')
        
        #print(message['content'])
        return jsonify({'content': result}), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500

def getContainer():
    try:
        project_name = request.args.get('project_name')
        if not project_name:
            return jsonify({"message": "project_name missing"}), 400
        container_name = request.args.get('container_name')
        if not container_name:
            return jsonify({"message": "container_name missing"}), 400
        
        handler = DBHandler()
        handler.database_setup()      
        result = handler.getContainer(project_name, container_name)
        
        return json.loads(result)['data'], 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500