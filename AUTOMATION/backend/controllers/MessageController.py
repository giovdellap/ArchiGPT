import json
from flask import request, jsonify

from api_reference.threads.threadsAPI import fileCreationHandler
from api_reference.threads.messagesAPI import messageCreationHandler


def create_message(thread_id):
    try:
        if 'content' not in request.form :
            return jsonify({"message": "Content is required in the form-data"}), 400
        content = request.form['content']
        
        # if not request.files:
        #     return jsonify({"message": "No files were uploaded."}), 400
        # user_files = request.files

        attachments = []
        tool = json.loads(request.form['tools'])
        if request.files:
            for file in request.files.items():
                #print(file,file[1].filename)
                fileObj = fileCreationHandler(file)
                attachments.append({ "file_id": fileObj.id, "tools": [{"type": tool[file[0]]}] })
        
        print("attachments: ",attachments)

        message = messageCreationHandler(thread_id,content,attachments)

        return jsonify({"message_id": message.id}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500