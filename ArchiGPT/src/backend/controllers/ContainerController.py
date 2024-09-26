import json
from flask import request, jsonify

from utils.api_handler_bridge import assistant_call
from handlers.post_processing_handler import buildPreMessage
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
        dbhandler = DBHandler()
        dbhandler.database_setup()      
        
        #CONTENT CREATION
        print('CONTENT CREATION')
        contentFactory = ContentFactory(dbhandler, project_name)
        content = contentFactory.getContainerContent(getAssistantName(assistant_name), {"container": container_name})

        #ASSISTANT INTERROGATION
        message_content = assistant_call( getAssistantName(assistant_name), content )

        result = buildPreMessage(getAssistantName(assistant_name), message_content)
        
        print('AFTER PROCESSING: ', result)
        
        #UPDATE DB STATUS
        dbhandler.updateContainerStatus(project_name, assistant_name, 'OK', container_name)
        
        #SAVE ON DB
        dbhandler.updateContainer(project_name, container_name, assistant_name, result)
        
        # NEXT ASSISTANT MANAGEMENT
        nextAssistant = getNextContainerAssistant(assistant_name)
        if nextAssistant != "SERVICE":
            dbhandler.updateContainerStatus(project_name, getNextContainerAssistant(assistant_name), 'NEXT', container_name)
        else:
            container_handler = ContainerHandler(result, project_name)
            container_handler.getServicesList(dbhandler, container_name)
        
        return jsonify({'content': result}), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    

def regenerateContainer():
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
        dbhandler = DBHandler()
        dbhandler.database_setup()      
        
        #CONTENT CREATION
        print('CONTENT CREATION')
        contentFactory = ContentFactory(dbhandler, project_name)
        content = contentFactory.getContainerContent(getAssistantName(assistant_name), {"container": container_name})

        #ASSISTANT INTERROGATION
        message_content = assistant_call( getAssistantName(assistant_name), content )

        result = buildPreMessage(getAssistantName(assistant_name), message_content)
        
        print('AFTER PROCESSING: ', result)
        
        #SAVE ON DB
        dbhandler.updateContainer(project_name, container_name, assistant_name, result)
        
        # NEXT ASSISTANT MANAGEMENT
        nextAssistant = getNextContainerAssistant(assistant_name)
        if nextAssistant == "SERVICE":
            container_handler = ContainerHandler(result, project_name)
            container_handler.getServicesList(dbhandler, container_name)
        
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
        
        dbhandler = DBHandler()
        dbhandler.database_setup()      
        result = dbhandler.getContainer(project_name, container_name)
        
        return json.loads(result)['data'], 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500