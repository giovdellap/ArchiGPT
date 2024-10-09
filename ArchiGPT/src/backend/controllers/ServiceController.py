import json
from flask import request, jsonify

from utils.api_handler_bridge import assistant_call
from handlers.post_processing_handler import buildPreMessage
from utils.content_factory import ContentFactory
from utils.assistant_name_matcher import getAssistantName, getNextServiceAssistant
from handlers.db_handler import DBHandler


def generateService():
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
        if 'service' not in request.form:
            return jsonify({"message": "service missing"}), 400
        service_name = request.form['service']
        
        content = ""
        dbhandler = DBHandler()
        dbhandler.database_setup()   
        
        
        #CONTENT CREATION
        print('CONTENT CREATION')
        contentFactory = ContentFactory(dbhandler, project_name)
        content = contentFactory.getServiceContent(getAssistantName(assistant_name), {"container": container_name, "service": service_name})

        #ASSISTANT INTERROGATION
        message_content = assistant_call( getAssistantName(assistant_name), content )

        result = buildPreMessage(getAssistantName(assistant_name), message_content)
        
        print('AFTER PROCESSING: ', result)
        
        #UPDATE DB STATUS
        dbhandler.updateServiceStatus(project_name, assistant_name, 'OK', container_name, service_name)
        
        #SAVE ON DB
        dbhandler.updateService(project_name, container_name, service_name, assistant_name, result)
        
        # NEXT ASSISTANT MANAGEMENT
        if assistant_name == "ServiceSpecificationGenerator":
            nextAssistant = getNextServiceAssistant(assistant_name)
            dbhandler.updateServiceStatus(project_name, nextAssistant, 'NEXT', container_name, service_name)                            #NEXT on ServiceEndpointGenerator
            dbhandler.updateServiceStatus(project_name, getNextServiceAssistant(nextAssistant), 'NEXT', container_name, service_name)   #NEXT on ServicePageGenerator
        
        return jsonify({'content': result}), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    

def regenerateService():
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
        if 'service' not in request.form:
            return jsonify({"message": "service missing"}), 400
        service_name = request.form['service']
        
        content = ""
        dbhandler = DBHandler()
        dbhandler.database_setup()   
        
        
        #CONTENT CREATION
        print('CONTENT CREATION')
        contentFactory = ContentFactory(dbhandler, project_name)
        content = contentFactory.getServiceContent(getAssistantName(assistant_name), {"container": container_name, "service": service_name})

        #ASSISTANT INTERROGATION
        message_content = assistant_call( getAssistantName(assistant_name), content )

        result = buildPreMessage(getAssistantName(assistant_name), message_content)
        
        print('AFTER PROCESSING: ', result)
        
        #SAVE ON DB
        dbhandler.updateService(project_name, container_name, service_name, assistant_name, result)
        
        return jsonify({'content': result}), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500


def getService():
    try:
        project_name = request.args.get('project_name')
        if not project_name:
            return jsonify({"message": "project_name missing"}), 400
        container_name = request.args.get('container_name')
        if not container_name:
            return jsonify({"message": "container_name missing"}), 400
        service_name = request.args.get('service_name')
        if not service_name:
            return jsonify({"message": "service_name missing"}), 400
        
        dbhandler = DBHandler()
        dbhandler.database_setup()    
        #TODO: add dbhandler.getService
        result = dbhandler.getContainer(project_name, container_name)
        
        return json.loads(result)['data'], 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500