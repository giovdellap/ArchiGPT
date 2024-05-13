from flask import request, jsonify

from api_reference.assistants.assistantsOrchestrator import AssistantOrchestrator


def create_assistant():
    try:
        if 'name' not in request.form or 'model' not in request.form:
            return jsonify({"message": "Name and model are required in the form-data"}), 400
        name = request.form['name']
        model = request.form['model']
        req_ci = []
        req_vs = []
        if 'ci_files' in request.files:
            req_ci.append(request.files['ci_files'])
        if 'vs_files' in request.files: 
            req_vs. append(request.files['vs_files'])
        assistant = AssistantOrchestrator()
        assistant_id = AssistantOrchestrator.assistantCreation(assistant,name,model, req_ci, req_vs)
        return jsonify({"assistant_id": assistant_id}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    
    
def get_assistant(assistant_id):
    try:      
        assistant = AssistantOrchestrator()
        assistantObj = AssistantOrchestrator.assistantProvider(assistant,assistant_id)

        return jsonify({"assistant": assistantObj}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    

def get_list_assistants():
    try:      
        assistant = AssistantOrchestrator()
        list_assistants = AssistantOrchestrator.assistantListProvider(assistant)

        return jsonify({"list_assistants": list_assistants}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    

def update_assistant(assistant_id):
    try:
        # if 'name' not in request.form or 'model' not in request.form:
        #     return jsonify({"message": "Name and model are required in the form-data"}), 400
        # name = request.form['name']
        # model = request.form['model']
        
        # assistant = AssistantOrchestrator()
        # assistant_id = AssistantOrchestrator.assistantUpdate(assistant,name,model)

        return jsonify({"message": f"Assistant {assistant_id} correctly updated"}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    

def delete_assistant(assistant_id):
    try:
        
        assistant = AssistantOrchestrator()
        AssistantOrchestrator.assistantDelete(assistant,assistant_id)

        return jsonify({"message": f"Assistant {assistant_id} and related files correctly deleted"}), 200
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500