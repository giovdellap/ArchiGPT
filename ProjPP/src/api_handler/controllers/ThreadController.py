from flask import request, jsonify

from api_reference.threads.threadsAPI import threadCreationHandler


def create_thread():
    try:
        
        thread = threadCreationHandler()

        return jsonify({"thread_id": thread.id}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500