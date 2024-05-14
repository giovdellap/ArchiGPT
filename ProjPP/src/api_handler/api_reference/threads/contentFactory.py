from flask import current_app


class ContentFactory:

    def __init__(self) :
        self.resources_path = current_app.config['basedir'] + '/resources'

    def set_assets(self, name):
        self.name = name
        if name == 'Unified Endpoints Generator':
            self.set_content_UEG()
        elif name == 'Containers List Generator':
            self.getContent('CLG')
        elif name == 'Endpoints Solo Generator':
            self.getContent('ESG')
        elif name == 'DataStructures Solo Generator':
            self.set_content_DSG()
        else:
            raise ValueError("Invalid assistant name")
            
    def getContent(self, folder):
        content_path = self.resources_path + '/' + folder + '/content.txt'
        with open(content_path, 'r') as file:
            self.content = file.read()
