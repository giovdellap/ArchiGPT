from flask import request, jsonify

projects_dataset = ["OneSport", "NFFH", "EFarmers", "RentYourExpert", "CDC", "EventTicket", "Teamify", "RecipeCove"]

def generateProjects():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        if 'project_name' not in projects_dataset:
            return jsonify({"message": "project_name not matches an available project"}), 400
        project_name = request.form['project_name']
        if 'num_projects' not in request.form:
            return jsonify({"message": "num_projects missing"}), 400
        if 'num_projects' > 0 and 'num_projects' <= 10:
            return jsonify({"message": "num_projects is not valid"}), 400
        num_projects = request.form['num_projects']


        #ASSISTANT INTERROGATION
        # result = assistant_call( getAssistantName(assistant_name), content )
        
        # #UPDATE DB STATUS
        # dbhandler.updateSystemStatus(project_name, assistant_name, 'OK')
        
        # #SAVE ON DB
        # dbhandler.updateSystem(project_name, assistant_name, result)
        
        return jsonify({'content': ""}), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500