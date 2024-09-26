from flask import request, jsonify

from utils.api_handler_bridge import assistant_call
from handlers.pre_processing_handler import PreProcessingHandler
from utils.content_factory import ContentFactory
from handlers.container_handler import ContainerHandler
from utils.assistant_name_matcher import getAssistantName, getNextSystemAssistant
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
        dbhandler = DBHandler()
        dbhandler.database_setup()      
        
        #CONTENT CREATION
        print('CONTENT CREATION')
        if assistant_name == 'Container Design':
            if 'userstories' not in request.files:
               return jsonify({"message": "userstories missing"}), 400
            content = request.files['userstories'].read()
            preprocessinghandler = PreProcessingHandler()
            preprocessinghandler.userStories(content.decode("utf-8"))
            dbhandler.updateSystem(
                project_name, 
                'userstories', 
                preprocessinghandler.getUserStories()
            )
            dbhandler.updateSystem(
                project_name,
                'description',
                preprocessinghandler.getDescription()
            )

        else:
            contentFactory = ContentFactory(dbhandler, project_name)
            content = contentFactory.getSystemContent(getAssistantName(assistant_name))


        #ASSISTANT INTERROGATION
        result = assistant_call( getAssistantName(assistant_name), content )
        
        #UPDATE DB STATUS
        dbhandler.updateSystemStatus(project_name, assistant_name, 'OK')
        
        #SAVE ON DB
        dbhandler.updateSystem(project_name, assistant_name, result)
        
        # NEXT ASSISTANT MANAGEMENT
        nextAssistant = getNextSystemAssistant(assistant_name)
        if nextAssistant != "CONTAINER":
            dbhandler.updateSystemStatus(project_name, nextAssistant, 'NEXT')
        else:
            container_handler = ContainerHandler(result, project_name)
            container_handler.getContainersList(dbhandler)
        
        return jsonify({'content': result}), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    

def regenerateSystem():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        project_name = request.form['project_name']
        if 'assistant' not in request.form:
            return jsonify({"message": "assistant missing"}), 400
        assistant_name = request.form['assistant']
        
        content = ""
        dbhandler = DBHandler()
        dbhandler.database_setup()      
        
        #CONTENT CREATION
        print('CONTENT CREATION')

        contentFactory = ContentFactory(dbhandler, project_name)
        content = contentFactory.getSystemContent(getAssistantName(assistant_name))

        #ASSISTANT INTERROGATION
        result = assistant_call( getAssistantName(assistant_name), content )
        
        #SAVE ON DB
        dbhandler.updateSystem(project_name, assistant_name, result)
        
        # NEXT ASSISTANT MANAGEMENT
        nextAssistant = getNextSystemAssistant(assistant_name)
        if nextAssistant == "CONTAINER":
            container_handler = ContainerHandler(result, project_name)
            container_handler.getContainersList(dbhandler)
        
        return jsonify({'content': result}), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500

