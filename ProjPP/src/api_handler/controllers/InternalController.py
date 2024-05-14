import time
from flask import request, jsonify

from api_reference.threads.messagesAPI import messageCreationHandler, messageListRetriever
from api_reference.threads.contentFactory import ContentFactory
from api_reference.threads.threadsAPI import threadCreationHandler
from api_reference.assistants.assistantsOrchestrator import AssistantOrchestrator
from api_reference.threads.runsAPI import runCreationHandler


def interrogate():
    try:
        if 'ass_name' not in request.form:
            return jsonify({"message": "ass_name missing"}), 400
        name = request.form['ass_name']
        if 'ass_model' not in request.form:
            return jsonify({"message": "ass_model"}), 400
        model = request.form['ass_model']


        #ASSISTANT CLEANUP
        assistant = AssistantOrchestrator()
        assistants_list = assistant.assistantListProvider()
        for old_assistant in assistants_list:
            if old_assistant['name'] == name:
                assistant.assistantDelete(old_assistant['id'])

        
        #ASSISTANT CREATION
        req_ci = []
        req_vs = []
        print(request.files['ass_ci'].content_type)
        if 'ass_ci' in request.files:
            req_ci.append(request.files['ass_ci'])
        if 'ass_vs' in request.files: 
            req_vs.append(request.files['ass_vs'])
        assistant_id = AssistantOrchestrator.assistantCreation(assistant, name, model, req_ci, req_vs)
        
        #THREAD CREATION
        thread = threadCreationHandler()
        print("THREAD: ", thread)

        #MESSAGE CREATION
        contentFactory = ContentFactory()
        contentFactory.set_assets(name)
        message = messageCreationHandler(thread.id, contentFactory.content, [])
        #print('message created')
        
        #RUN CREATION
        run = runCreationHandler(thread.id, assistant_id)
        print('run created')
        time.sleep(120)
        
        
        #MESSAGE RETRIEVAL
        thread_messages = []
        while(len(thread_messages) < 2):
            thread_messages = messageListRetriever(thread.id)


        print("messages", thread_messages)
        message = thread_messages[len(thread_messages)-1]
        

        return jsonify({"content": message['content']}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500