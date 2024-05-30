import time
from flask import request, jsonify, current_app

from utils.sleepManager import getSleepTime
from api_reference.threads.messagesAPI import messageCreationHandler, messageListRetriever
from api_reference.threads.contentFactory import ContentFactory
from api_reference.threads.threadsAPI import threadCreationHandler
from api_reference.assistants.assistantsOrchestrator import AssistantOrchestrator
from api_reference.threads.runsAPI import runCreationHandler
from utils.demo import demoResponse


def interrogate():
    try:
        if 'ass_name' not in request.form:
            return jsonify({"message": "ass_name missing"}), 400
        name = request.form['ass_name']
        if 'ass_model' not in request.form:
            return jsonify({"message": "ass_model missing"}), 400
        model = request.form['ass_model']
        if 'content' not in request.form:
            return jsonify({"message": "content missing"}), 400
        content = request.form['content']
        
        print('NEW REQUEST')
        print('NAME: ', name)
        print('MODEL: ', model)
        print('CONTENT: ', content)

        demo = current_app.config['DEMO']
        if demo:
            return jsonify({"content": demoResponse(name)}), 200

        #ASSISTANT CLEANUP
        assistant = AssistantOrchestrator()
        assistants_list = assistant.assistantListProvider()
        for old_assistant in assistants_list:
            if old_assistant['name'] == name:
                assistant.assistantDelete(old_assistant['id'])

        
        #ASSISTANT CREATION
        assistant_id = AssistantOrchestrator.assistantCreation(assistant, name, model, [], [])
        print("Assistant correctly created: ", name)
        #THREAD CREATION
        thread = threadCreationHandler()
        #print("THREAD: ", thread)

        #MESSAGE CREATION
        contentFactory = ContentFactory()
        contentFactory.set_assets(name, content)
        message = messageCreationHandler(thread.id, contentFactory.content, [])
        #print('message created')
        
        #RUN CREATION
        run = runCreationHandler(thread.id, assistant_id)
        print('run created')
        print('Waiting for message response from OpenAI ...')
        sleep = getSleepTime(name)
        time.sleep(sleep)
        
        
        #MESSAGE RETRIEVAL
        thread_messages = []
        while(len(thread_messages) < 2):
            thread_messages = messageListRetriever(thread.id)


        print("messages", thread_messages)
        message = thread_messages[0]['content']
        

        return jsonify({"content": message}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500