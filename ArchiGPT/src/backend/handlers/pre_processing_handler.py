class PreProcessingHandler:
    
    def __init__(self):
        self.description = ""
        self.userstories = ""
    
    def userStories(self, userstories):
        cleaned = userstories.split('DESCRIPTION:')
        print('CLEANED: ', cleaned)
        splitted = cleaned[1].split('USER STORIES:')
        print('SPLITTED: ', splitted)
        self.description = splitted[0]
        self.userstories = splitted[1]
        
    def getDescription(self):
        return self.description
    
    def getUserStories(self):
        return self.userstories