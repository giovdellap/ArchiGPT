from flask import current_app


class ContentFactory:

    def __init__(self) :
        self.resources_path = current_app.config['basedir'] + '/resources'

    def set_assets(self, name, content):
        self.name = name
        self.content = content
            
    def getContent(self, folder):
        content_path = self.resources_path + '/' + folder + '/content.txt'
        with open(content_path, 'r') as file:
            self.content = file.read()
