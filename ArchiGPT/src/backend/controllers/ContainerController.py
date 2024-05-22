import json
from flask import current_app, request, jsonify
import requests
import os

from utils.content_factory import ContentFactory
from handlers.container_handler import ContainerHandler
from utils.assistant_name_matcher import getAssistantName, getNextAssistant
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
        content = contentFactory.getSystemContent(getAssistantName(assistant_name))


        #ASSISTANT INTERROGATION
        print('ASSISTANT INTERROGATION')
        message = requests.post(
            current_app.config['API_HANDLER'] + '/interrogation/interrogate',
            data={
                'ass_name': getAssistantName(assistant_name),
                #'ass_model': 'gpt-3.5-turbO',
                'ass_model': 'gpt-4-turbo-2024-04-09',
                'content': content
            }
        )
        result = message.json()['content']
        print('Message: ', result)
        
        #UPDATE DB STATUS
        handler.updateSystemStatus(project_name, assistant_name, 'OK')
        
        #SAVE ON DB
        handler.updateSystem(project_name, assistant_name, result)
        
        # NEXT ASSISTANT MANAGEMENT
        nextAssistant = getNextAssistant(assistant_name)
        if nextAssistant != "CONTAINER":
            handler.updateSystemStatus(project_name, getNextAssistant(assistant_name), 'NEXT')
        else:
            container_handler = ContainerHandler(result, project_name)
            container_handler.getContainersList(handler)
        

        #DB HANDLER SHUTDOWN
        #handler.shutdown_db_client()
        
        #print(message['content'])
        return jsonify({'content': result}), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500

