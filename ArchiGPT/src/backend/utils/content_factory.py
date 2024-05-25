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
        if assistant == "Container_1":
            system = json.loads(self.db_handler.getSystem(self.project))
            container_content = "ANALYZE CONTAINER: " + attachments['container'] + "/n"
            db_content = getSystemDocument('userstories', system) + '/n' + getSystemDocument('User Interaction Analysis', system)
            self.content = container_content + db_content
        
        print('CONTENT FACTORY - CONTENT: ', self.content)
        return self.content
        
def getSystemDocument(name, system):
    for item in system['data']:
        if item['name'] == name:
            return item['message']
            
            