import ast

from utils.api_handler_bridge import assistant_call
from utils.content_factory import ContentFactory
from utils.output_cleaner import cleanOutput

class ContainerHandler:
    
    def __init__(self, document, project_name) :
        self.document = document
        self.project_name = project_name
        
    def getContainersList(self, db_handler):

        #ASSISTANT INTERROGATION
        result = assistant_call( 'Util_1', self.document )

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
        
        db_handler.insertContainersinStatus(status_objs, self.project_name)
        print('CONTAINER HANDLER: STATUS UPDATED')
        
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
                    'ports': '',
                    'userstories': '',
                    'services': []
                }
            })

        db_handler.insertContainersDocuments(documents, self.project_name)
        print('CONTAINER HANDLER: CONTAINER DOCUMENTS CREATED')
        
    def getServicesList(self, dbhandler, container):
        
        #CONTENT CREATION UTIL 2
        print('CONTAINER HANDLER - CONTENT CREATION UTIL 2')
        contentFactory = ContentFactory(dbhandler, self.project_name)
        content = contentFactory.getUtilContent('Util_2', {"container": container})
        print('CONTAINER HANDLER - CONTENT UTIL 2: ', content)

        #ASSISTANT INTERROGATION UTIL 2
        message_content = assistant_call( 'Util_2', content )
        message = cleanOutput(message_content, "UTIL2")
        obj = ast.literal_eval(message)
        print('CONTAINER HANDLER UTIL 2 - OBJ: ', obj)
        
        #SAVE ON DB
        dbhandler.updateContainer(self.project_name, container, 'ports', obj['ports'])
        dbhandler.updateContainer(self.project_name, container, 'userstories', obj['userstories'])
        
        #CONTENT CREATION UTIL 3
        print('CONTAINER HANDLER - CONTENT CREATION UTIL 3')
        content = contentFactory.getUtilContent('Util_3', {"container": container})
        print('CONTAINER HANDLER - CONTENT UTIL 3: ', content)

        #ASSISTANT INTERROGATION UTIL 3
        message_content = assistant_call( 'Util_3', content )
        print('CONTAINER HANDLER UTIL 3 - OBJ: ', message_content)
        message = cleanOutput(message_content, "UTIL3")
        list = ast.literal_eval(message)

        #OBJECT CONSTRUCTION
        for microservice in list:
            microservice['ServiceSpecificationGenerator'] = ""
            if microservice['type'] == "backend":
                microservice['ServiceEndpointGenerator'] = ""
            if microservice['type'] == "frontend":
                microservice['ServicePageGenerator'] = ""
        
        print('CONTAINER HANDLER UTIL 3 - LIST: ', list)

        #SAVE ON DB
        dbhandler.updateContainer(self.project_name, container, 'services', list)
        
        #STATUS OBJECT
        status_list = []
        for microservice in list:
            service = {}
            service['name'] = microservice['name']
            service['description'] = 'OK'
            service['ServiceSpecificationGenerator'] = 'NEXT'
            if microservice['type'] == 'backend':
                service['ServiceEndpointGenerator'] = 'NO'
            if microservice['type'] == 'frontend':
                service['ServicePageGenerator'] = 'NO'
            status_list.append(service)
        
        print('CONTAINER HANDLER UTILS 3 - STATUS LIST: ', status_list)

        #SAVE STATUS ON DB
        dbhandler.updateContainerStatus(self.project_name, 'services', status_list, container)
        
