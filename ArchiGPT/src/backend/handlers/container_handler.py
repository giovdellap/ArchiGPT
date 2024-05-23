from flask import current_app, request, jsonify
import requests
import ast

class ContainerHandler:
    
    def __init__(self, document, project_name) :
        self.document = document
        self.project_name = project_name
        
    def getContainersList(self, db_handler):
        message = requests.post(
            current_app.config['API_HANDLER'] + '/interrogation/interrogate',
            data={
                'ass_name': 'Util_1',
                'ass_model': 'gpt-3.5-turbo',
                #'ass_model': 'gpt-4-turbo-2024-04-09',
                'content': self.document
            }
        )
        result = message.json()['content']
        list = ast.literal_eval(result)
        
        # STATUS UPDATE
        status_objs = []
        for elem in list:
            status_objs.append(
                {
                    'name': elem,
                    'ContainerDescriptionGenerator': "NEXT",
                    'ContainerSpecificationGenerator': "NO",
                    'ServiceListGenerator': "NO",
                    'services': []
                }
            )
        print('CONTAINER HANDLER 1')
        db_handler.insertContainersinStatus(status_objs, self.project_name)
        print('CONTAINER HANDLER 2')
        #DOCUMENTS INSERTION
        documents = []
        for elem in list:
            documents.append({
                'type': 'container',
                'data': {
                    'name': elem,
                    'ContainerDescriptionGenerator': "",
                    'ContainerSpecificationGeneration': "",
                    'ServiceListGenerator': "",
                    'services': []
                }
            })
        print('CONTAINER HANDLER 3')
        db_handler.insertContainersDocuments(documents, self.project_name)
        print('CONTAINER HANDLER 4')
        
        
    