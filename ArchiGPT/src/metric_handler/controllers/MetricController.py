from flask import request, jsonify
from utils.backend_handler_bridge import generationRequest, generationRequestWithFile, getDataRequest
from utils.projectJsonFormatter import userstoriesFormatter, endpointsFormatter, pagesFormatter

projects_dataset = ["OneSport", "NFFH", "EFarmers", "RentYourExpert", "CDC", "EventTicket", "Teamify", "RecipeCove"]

def generateProject():
    try:
        if 'project_name' not in request.form:
            return jsonify({"message": "project_name missing"}), 400
        project_name = request.form['project_name']
        if project_name not in projects_dataset:
            return jsonify({"message": f"{project_name} not matches an available project"}), 400
        
        archiProject = { "name": project_name, "containers": [] }

        archiProject, response = generationProcess(archiProject)

        if response == 500 : return jsonify({"message": "An error occurred during the generation process"}, 500)
        
        return jsonify(archiProject), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    


def generateAllProjects():
    try:
        
        archiProjects = []
        
        for indexProject in range(0, 8):

            project_name = projects_dataset[indexProject]

            archiProject = { "name": project_name, "containers": [] }
            
            archiProject, response = generationProcess(archiProject)

            archiProjects.append(archiProject)

        archiJson = {
            "modelName": "ArchiGPT",
            "projects": archiProjects
        }
        
        return jsonify(archiJson), 200
    
    except Exception as e:
        print("Exception: %s", e)
        return jsonify({"message": "An error occurred"}), 500
    


def generationProcess(archiProject):
    try:

        project_name = archiProject['name']
            
        # PROJECT APIs INTERROGATION

        data = {"project_name": project_name}
        generationRequest("project/", data)

        # SYSTEM GENERATION APIs INTERROGATION

        file = { 'userstories' : open(f'./utils/{project_name}/userStories.txt', 'rb') }
        data = {'project_name': project_name, 'assistant': "Container Design"}
        generationRequestWithFile("generation/generateSystem", data, file)

        data = {'project_name': project_name, 'assistant': "User Interaction Analysis"}
        generationRequest("generation/generateSystem", data)

        # CONTAINER GENERATION APIs INTERROGATION

        params = {"project_name": project_name}
        statusData = getDataRequest("project/status", params)

        indexContainer = 0
        for container in statusData['containers']:

            data = {'project_name': project_name, 'assistant': "ContainerDescriptionGenerator", "container": container['name']}
            #generationRequest("generation/generateContainer", data)

            data = {'project_name': project_name, 'assistant': "ContainerSpecificationGenerator", "container": container['name']}
            #generationRequest("generation/generateContainer", data)

            data = {'project_name': project_name, 'assistant': "MicroServices", "container": container['name']}
            #generationRequest("generation/generateContainer", data)

            params = {"project_name": project_name, "container_name": container['name']}
            containerData = getDataRequest("generation/getContainer", params)

            archiProject["containers"].append({
                "name": containerData['name'],
                "userStories": userstoriesFormatter(containerData['userstories']),
                "services": []
            })


            # SERVICE GENERATION APIs INTERROGATION

            for service in containerData['services']:

                if service['type'] == "backend" or service['type'] == "frontend":
                    
                    data = {'project_name': project_name, 'assistant': "ServiceSpecificationGenerator", "container": container['name'], "service": service['name']}
                    #generationRequest("generation/generateService", data)

                    if service['type'] == "backend":

                        data = {'project_name': project_name, 'assistant': "ServiceEndpointGenerator", "container": container['name'], "service": service['name']}
                        temp_endpoints = []
                        #temp_endpoints = generationRequest("generation/generateService", data)

                        archiProject["containers"][indexContainer]["services"].append({
                            "name": service['name'],
                            "type": service['type'],
                            "endpoints": endpointsFormatter(temp_endpoints['content'])
                        })
                
                    if service['type'] == "frontend" :

                        data = {'project_name': project_name, 'assistant': "ServicePageGenerator", "container": container['name'], "service": service['name']}
                        temp_pages = []
                        #temp_pages = generationRequest("generation/generateService", data)

                        archiProject["containers"][indexContainer]["services"].append({
                            "name": service['name'],
                            "type": service['type'],
                            "pages": pagesFormatter(temp_pages['content'])
                        })

                else :

                    archiProject["containers"][indexContainer]["services"].append({
                        "name": service['name'],
                        "type": service['type']
                    })
            
            indexContainer = indexContainer + 1

        print("Project correctly fully generated : ", project_name)
        
        return archiProject, 200
    
    except Exception as e:
        print("Exception: %s", e)
        return {}, 500