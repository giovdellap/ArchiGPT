import json
from flask import current_app, request, jsonify
import requests
import os

from handlers.container_handler import ContainerHandler
from utils.assistant_name_matcher import getAssistantName, getNextAssistant
from handlers.db_handler import DBHandler


def generateSystem():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        project_name = request.form['project_name']
        if 'assistant' not in request.form:
            return jsonify({"message": "assistant missing"}), 400
        assistant_name = request.form['assistant']
        content = ""        
        #print('NAME ', project_name)
        
        #CONTENT CREATION
        if assistant_name == 'ContainerDesigner':
            if 'userstories' not in request.files:
               return jsonify({"message": "userstories missing"}), 400
            content = request.files['userstories'].read()
            # current_path = os.getcwd()
            # path = current_path + ('/utils/UserStories.txt')
            # print(current_path)
            # file = open(path,'r')
            # content = file.read()


        
        #ASSISTANT INTERROGATION
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
        handler = DBHandler()
        handler.database_setup()
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

