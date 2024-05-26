import json


class ContentFactory:
    def __init__(self, db_handler, project):
        self.project = project
        self.content = ""
        self.db_handler = db_handler
        
    def getSystemContent(self, assistant):
        if assistant == "System_2":
            print('test 1')
            system = json.loads(self.db_handler.getSystem(self.project))
            print('test 2')
            self.content = getSystemDocument('userstories', system) + '/n' + getSystemDocument('Container Design', system)
    
        print('CONTENT FACTORY - CONTENT: ', self.content)
        return self.content
            
    def getContainerContent(self, assistant, attachments):
        system = json.loads(self.db_handler.getSystem(self.project))
        container_name = attachments['container']
        container_prompt = "ANALYZE CONTAINER: " + container_name + "/n"
        container = {}
        if assistant in load_container_data:
            print('AAAAA')
            container = json.loads(self.db_handler.getContainer(self.project, container_name))
            print('CONTAINER DATA: ', container)

        if assistant == "Container_1":
            content = self.container1(system)
            self.content = container_prompt + content
        
        if assistant == "Container_2":
            content = self.container2(system, container)
            self.content = container_prompt + content
                        
        print('CONTENT FACTORY - CONTENT: ', self.content)
        return self.content
    
    def container1(self, system):
        userstories = getSystemDocument('userstories', system) + '/n'
        containerDesign = getSystemDocument('Container Design', system) + '/n'
        userInteraction = getSystemDocument('User Interaction Analysis', system)
        return userstories + containerDesign + userInteraction
    
    def container2(self, system, container):
        userstories = getSystemDocument('userstories', system) + '/n'
        containerDesign = getSystemDocument('Container Design', system) + '/n'
        userInteraction = getSystemDocument('User Interaction Analysis', system) + '/n'
        
        containerTitle = 'CONTAINER: ' + getContainerDocument('name', container) + '/n'
        description = getContainerDocument('ContainerDescriptionGenerator', container) + '/n'
        
        return userstories + containerDesign + userInteraction + containerTitle + description
        
load_container_data = ['Container_2']        

def getSystemDocument(name, system):
    for item in system['data']:
        if item['name'] == name:
            return item['message']
        
def getContainerDocument(name, container):
    return container['data'][name]
    
            
            