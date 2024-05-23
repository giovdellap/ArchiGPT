import json


class ContentFactory:
    def __init__(self, db_handler, project):
        self.project = project
        self.content = ""
        self.db_handler = db_handler
       

        
    def getSystemContent(self, assistant):
        if assistant == "System_2":
            system = json.loads(self.db_handler.getSystem(self.project))
            self.content = getSystemDocument('userstories', system) + '/n' + getSystemDocument('ContainerDesigner', system)
    
        print('CONTENT FACTORY - CONTENT: ', self.content)
        return self.content
            
        
    def getContainerContent(self, assistant, attachments):
        if assistant == "Container_1":
            system = json.loads(self.db_handler.getSystem(self.project))
            container_content = "ANALYZE CONTAINER: " + attachments['container'] + "/n"
            db_content = getSystemDocument('userstories', system) + '/n' + getSystemDocument('UserInteractionAnalyzer', system)
            self.content = container_content + db_content
        
        print('CONTENT FACTORY - CONTENT: ', self.content)
        return self.content
        
def getSystemDocument(name, system):
    print('ppp')
    for item in system['data']:
        print('eeee')
        if item['name'] == name:
            print('oooo')
            return item['message']
            
            