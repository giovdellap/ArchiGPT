from flask import request, jsonify

from api_reference.assistants.assistantsOrchestrator import AssistantOrchestrator


def create_assistant():
    try:
        if 'name' not in request.form or 'model' not in request.form:
            return jsonify({"message": "Name and model are required in the form-data"}), 400
        name = request.form['name']
        model = request.form['model']
        
        assistant = AssistantOrchestrator()
        assistant_id = AssistantOrchestrator.assistantCreation(assistant,name,model)
        return jsonify({"assistant_id": assistant_id}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500