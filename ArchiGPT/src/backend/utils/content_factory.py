import json


class ContentFactory:
    def __init__(self, db_handler, project):
        self.project = project
        self.content = ""
        self.db_handler = db_handler
        
    def getSystemContent(self, assistant):
        if assistant == "System_2":
            system = json.loads(self.db_handler.getSystem(self.project))
            description = self.getDescription(system)
            self.content = description + '/n' + getSystemDocument('Container Design', system)
    
        print('CONTENT FACTORY - CONTENT: ', self.content)
        return self.content
            
    def getContainerContent(self, assistant, attachments):
        system = json.loads(self.db_handler.getSystem(self.project))
        container_name = attachments['container']
        container_prompt = "ANALYZE CONTAINER: " + container_name + "/n"
        container = {}
        if assistant in load_container_data:
            container = json.loads(self.db_handler.getContainer(self.project, container_name))
            print('CONTAINER DATA: ', container)

        if assistant == "Container_1":
            content = self.container1(system)
            self.content = container_prompt + content
        
        if assistant == "Container_2":
            content = self.container2(system, container)
            self.content = container_prompt + content
            
        if assistant == "Container_3":
            content = self.container3(system, container)
            self.content = container_prompt + content
                        
        print('CONTENT FACTORY - CONTENT: ', self.content)
        return self.content
    
    def getUtilContent(self, assistant, attachments):
        system = json.loads(self.db_handler.getSystem(self.project))
        container_name = attachments['container']
        container_prompt = "ANALYZE CONTAINER: " + container_name + "/n"
        if assistant == "Util_2":
            userInteraction = getSystemDocument('User Interaction Analysis', system)
            self.content = container_prompt + userInteraction
        if assistant == "Util_3":
            container = json.loads(self.db_handler.getContainer(self.project, container_name))
            self.content = getContainerDocument('MicroServices', container)
        return self.content

    def getServiceContent(self, assistant, attachments):
        container_name = attachments['container']
        service_name = attachments['service']
        service_prompt = "ANALYZE SERVICE: " + service_name + "/n"
 
        container = json.loads(self.db_handler.getContainer(self.project, container_name))
        print('CONTAINER DATA: ', container)

        if assistant == "Service_1":
            content = self.service1(container)
            self.content = service_prompt + content
        
        if assistant == "Service_2":
            content = self.service2(container, service_name)
            self.content = service_prompt + content

        if assistant == "Service_3":
            content = self.service3(container, service_name)
            self.content = service_prompt + content
                        
        print('CONTENT FACTORY - CONTENT: ', self.content)
        return self.content
    

    
    def getDescription(self, system):
        return 'SYSTEM DESCRIPTION: \n' + getSystemDocument('description', system) + '\n'

    
    def container1(self, system):
        description = self.getDescription(system)
        containerDesign = getSystemDocument('Container Design', system) + '/n'
        userInteraction = getSystemDocument('User Interaction Analysis', system)
        return description + containerDesign + userInteraction
    
    def container2(self, system, container):
        description = self.getDescription(system)
        containerDesign = getSystemDocument('Container Design', system) + '/n'
        userInteraction = getSystemDocument('User Interaction Analysis', system) + '/n'
        
        containerTitle = 'CONTAINER: ' + getContainerDocument('name', container) + '/n'
        container_description = getContainerDocument('ContainerDescriptionGenerator', container) + '/n'
        
        return description + containerDesign + userInteraction + containerTitle + container_description
        
    def container3(self, system, container):
        description = self.getDescription(system)
        containerDesign = getSystemDocument('Container Design', system) + '/n'
        userInteraction = getSystemDocument('User Interaction Analysis', system) + '/n'
        system_content = description + containerDesign + userInteraction
        
        containerTitle = 'CONTAINER: ' + getContainerDocument('name', container) + '/n'
        container_description = getContainerDocument('ContainerDescriptionGenerator', container) + '/n'
        container_specification = getContainerDocument('ContainerSpecificationGenerator', container) + '/n'
        container_content = containerTitle + container_description + container_specification

        return system_content + container_content
    
    def service1(self, container):
        containerTitle = 'CONTAINER: ' + getContainerDocument('name', container) + '/n'
        container_description = getContainerDocument('ContainerDescriptionGenerator', container) + '/n'
        container_specification = getContainerDocument('ContainerSpecificationGenerator', container) + '/n'
        container_userstories = 'USERSTORIES: ' + getContainerDocument('userstories', container) + '/n'
        container_microservices = getContainerDocument('MicroServices', container) + '/n'

        container_content = containerTitle + container_description + container_specification + container_userstories + container_microservices

        return container_content
    
    def service2(self, container, service_name):
        containerTitle = 'CONTAINER: ' + getContainerDocument('name', container) + '/n'
        container_description = getContainerDocument('ContainerDescriptionGenerator', container) + '/n'
        container_specification = getContainerDocument('ContainerSpecificationGenerator', container) + '/n'
        container_userstories = 'USERSTORIES: ' + getContainerDocument('userstories', container) + '/n'
        container_microservices = getContainerDocument('MicroServices', container) + '/n'

        service_specification = getServiceDocument('ServiceSpecificationGenerator', container, service_name)
        
        container_content = containerTitle + container_description + container_specification + container_userstories + container_microservices + service_specification

        return container_content
    
    def service3(self, container, service_name):
        containerTitle = 'CONTAINER: ' + getContainerDocument('name', container) + '/n'
        container_description = getContainerDocument('ContainerDescriptionGenerator', container) + '/n'
        container_specification = getContainerDocument('ContainerSpecificationGenerator', container) + '/n'
        container_userstories = 'USERSTORIES: ' + getContainerDocument('userstories', container) + '/n'
        container_microservices = getContainerDocument('MicroServices', container) + '/n'

        service_specification = getServiceDocument('ServiceSpecificationGenerator', container, service_name)
        
        container_content = containerTitle + container_description + container_specification + container_userstories + container_microservices + service_specification

        return container_content
        
load_container_data = ['Container_2', 'Container_3']        

def getSystemDocument(name, system):
    for item in system['data']:
        if item['name'] == name:
            return item['message']
        
def getContainerDocument(name, container):
    return container['data'][name]

def getServiceDocument(name, container, service_name):
    for item in container['data']['services']:
        if item['name'] == service_name:
            return item[name]
    
            
            