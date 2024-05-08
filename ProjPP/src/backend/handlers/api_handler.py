from flask import current_app, request, jsonify
import requests

def getAssistantId(name):
    
    res = requests.get(current_app.config['DEFAULT_PATH'] + "/assistant")
    res_json = res.json()
    print(res_json)
    for assistant in res_json['list_assistants']:
        #print(assistant.json())
        if assistant['name'] == name:
            return assistant['id']
    res = requests.post(
        current_app.config['DEFAULT_PATH'] + "/assistant",
        {
            "name": name,
            "model": "gpt-3.5-turbo"
        }
    )
    return res.json()['assistant_id']

def getThread():
    thread = requests.post(current_app.config['DEFAULT_PATH'] + "/thread")
    return thread.json()['thread_id']
