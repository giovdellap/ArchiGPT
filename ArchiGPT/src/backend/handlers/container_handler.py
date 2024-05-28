from flask import current_app
import requests
import ast

from utils.content_factory import ContentFactory

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
                    'MicroServices': "NO",
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
                    'ContainerSpecificationGenerator': "",
                    'MicroServices': "",
                    'description': '',
                    'ports': '',
                    'userstories': '',
                    'services': []
                }
            })
        print('CONTAINER HANDLER 3')
        db_handler.insertContainersDocuments(documents, self.project_name)
        print('CONTAINER HANDLER 4')
        
    def getServicesList(self, dbhandler, container):
        
        #CONTENT CREATION UTIL 2
        print('CONTAINER HANDLER - CONTENT CREATION UTIL 2')
        contentFactory = ContentFactory(dbhandler, self.project_name)
        content = contentFactory.getUtilContent('Util_2', {"container": container})
        print('CONTAINER HANDLER - CONTENT UTIL 2: ', content)


        #ASSISTANT INTERROGATION UTIL 2
        print('CONTAINER HANDLER - ASSISTANT INTERROGATION UTIL 2')
        message = requests.post(
            current_app.config['API_HANDLER'] + '/interrogation/interrogate',
            data={
                'ass_name': 'Util_2',
                'ass_model': 'gpt-3.5-turbo',
                #'ass_model': 'gpt-4-turbo-2024-04-09',
                'content': content
            }
        )
        message_content = message.json()['content']
        print('CONTAINER HANDLER UTIL 2- RECEIVED MESSAGE: ', message_content)
        obj = ast.literal_eval(message_content)

        
        #SAVE ON DB
        dbhandler.updateContainer(self.project_name, container, 'description', obj['description'])
        dbhandler.updateContainer(self.project_name, container, 'ports', obj['ports'])
        dbhandler.updateContainer(self.project_name, container, 'userstories', obj['userstories'])
        
        #CONTENT CREATION UTIL 3
        print('CONTAINER HANDLER - CONTENT CREATION UTIL 3')
        content = contentFactory.getUtilContent('Util_3', {"container": container})
        print('CONTAINER HANDLER - CONTENT UTIL 3: ', content)

        #ASSISTANT INTERROGATION UTIL 3
        print('CONTAINER HANDLER - ASSISTANT INTERROGATION UTIL 3')
        message = requests.post(
            current_app.config['API_HANDLER'] + '/interrogation/interrogate',
            data={
                'ass_name': 'Util_3',
                'ass_model': 'gpt-3.5-turbo',
                #'ass_model': 'gpt-4-turbo-2024-04-09',
                'content': content
            }
        )
        message_content = message.json()['content']
        print('CONTAINER HANDLER UTIL 3 - RECEIVED MESSAGE: ', message_content)
        obj = ast.literal_eval(message_content)
        
        #SAVE ON DB
        dbhandler.updateContainer(self.project_name, container, 'services', obj)
        
        
