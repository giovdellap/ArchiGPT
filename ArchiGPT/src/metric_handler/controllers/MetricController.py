from flask import request, jsonify
from utils.backend_handler_bridge import backend_call, backend_callwithFile

projects_dataset = ["OneSport", "NFFH", "EFarmers", "RentYourExpert", "CDC", "EventTicket", "Teamify", "RecipeCove"]

def generateProjects():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        project_name = request.form['project_name']
        if project_name not in projects_dataset:
            return jsonify({"message": f"{project_name} not matches an available project"}), 400
        if 'num_projects' not in request.form:
            return jsonify({"message": "num_projects missing"}), 400
        num_projects = int(request.form['num_projects'])
        if num_projects < 1 or num_projects > 10:
            return jsonify({"message": "num_projects is not valid"}), 400
        
        for indexProject in range(1, num_projects + 1):
            
            # PROJECT APIs INTERROGATION

            data = {"project_name": project_name + str(indexProject)}
            #backend_call("project/", data)

            # SYSTEM GENERATION APIs INTERROGATION

            file = { 'userstories' : open(f'./utils/{project_name}/userStories.txt', 'rb') }
            data = {'project_name': project_name + str(indexProject), 'assistant': "Container Design"}
            #backend_callwithFile("generation/generateSystem", data, file)

            data = {'project_name': project_name + str(indexProject), 'assistant': "User Interaction Analysis"}
            backend_call("generation/generateSystem", data)

            # CONTAINER GENERATION APIs INTERROGATION

            #data = {"project_name": project_name + str(indexProject)}
            #backend_call("project/", data)
        
        return jsonify(), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500