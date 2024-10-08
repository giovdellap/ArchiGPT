from flask import request, jsonify
from utils.backend_handler_bridge import generationRequest, generationRequestWithFile, getDataRequest
from utils.projectJsonFormatter import userstoriesFormatter, endpointsFormatter

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
        
        archiProjects = []
        
        for indexProject in range(1, num_projects + 1):
            
            # PROJECT APIs INTERROGATION

            project_id = project_name + str(indexProject)
            data = {"project_name": project_id}
            generationRequest("project/", data)
            archiProjects.append({ "name": project_id, "containers": [] })

            # SYSTEM GENERATION APIs INTERROGATION

            file = { 'userstories' : open(f'./utils/{project_name}/userStories.txt', 'rb') }
            data = {'project_name': project_id, 'assistant': "Container Design"}
            generationRequestWithFile("generation/generateSystem", data, file)

            data = {'project_name': project_id, 'assistant': "User Interaction Analysis"}
            generationRequest("generation/generateSystem", data)

            # CONTAINER GENERATION APIs INTERROGATION

            params = {"project_name": project_id}
            statusData = getDataRequest("project/status", params)

            indexContainer = 0
            for container in statusData['containers']:

                data = {'project_name': project_id, 'assistant': "ContainerDescriptionGenerator", "container": container['name']}
                generationRequest("generation/generateContainer", data)

                data = {'project_name': project_id, 'assistant': "ContainerSpecificationGenerator", "container": container['name']}
                generationRequest("generation/generateContainer", data)

                data = {'project_name': project_id, 'assistant': "MicroServices", "container": container['name']}
                generationRequest("generation/generateContainer", data)

                params = {"project_name": project_id, "container_name": container['name']}
                containerData = getDataRequest("generation/getContainer", params)

                archiProjects[indexProject-1]["containers"].append({
                    "name": containerData['name'],
                    "userStories": userstoriesFormatter(containerData['userstories']),
                    "services": []
                })


                # SERVICE GENERATION APIs INTERROGATION

                for service in containerData['services']:

                    if service['type'] == "backend" or service['type'] == "frontend":
                        
                        data = {'project_name': project_id, 'assistant': "ServiceSpecificationGenerator", "container": container['name'], "service": service['name']}
                        generationRequest("generation/generateService", data)

                        if service['type'] == "backend":

                            data = {'project_name': project_id, 'assistant': "ServiceEndpointGenerator", "container": container['name'], "service": service['name']}
                            temp_endpoint =generationRequest("generation/generateService", data)
                            print('DIO MAIALE', temp_endpoint)
                            archiProjects[indexProject-1]["containers"][indexContainer]["services"].append({
                                "name": service['name'],
                                "type": service['type'],
                                "endpoints": endpointsFormatter(temp_endpoint['content'])
                            })
                    
                        if service['type'] == "frontend" :

                            archiProjects[indexProject-1]["containers"][indexContainer]["services"].append({
                                "name": service['name'],
                                "type": service['type'],
                                "pages": []
                            })

                    else :

                        archiProjects[indexProject-1]["containers"][indexContainer]["services"].append({
                            "name": service['name'],
                            "type": service['type']
                        })
                
                indexContainer = indexContainer + 1

        archiJson = {
            "modelName": "ArchiGPT",
            "projectModel": project_name,
            "projects": archiProjects
        }
        
        return jsonify(archiJson), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500