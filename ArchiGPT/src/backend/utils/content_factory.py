import json


class ContentFactory:
    def __init__(self, db_handler, project):
        self.project = project
        self.content = ""
        self.db_handler = db_handler
       

        
    def getSystemContent(self, assistant):
        if assistant == "System_2":
            system = json.loads(self.db_handler.getSystem(self.project))
            print('SYSTEM: ', type(system))
            
            self.content = getSystemDocument('userstories', system) + '/n' + getSystemDocument('ContainerDesigner', system)
            print('CONTENT: ', self.content)
            return self.content
        
    def getContainerContent(self, assistant):
        if assistant == "Container_1":
            system = json.loads(self.db_handler.getSystem(self.project))
            return getSystemDocument('UserInteractionAnalyzer', system)
        
def getSystemDocument(name, system):
    print('ppp')
    for item in system['data']:
        print('eeee')
        if item['name'] == name:
            print('oooo')
            return item['message']
            
            