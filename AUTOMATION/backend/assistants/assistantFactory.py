from flask import current_app

from assistants.assistantsOrchestrator import AssistantOrchestrator

class AssistantFactory:

    def __init__(self) :
        self.resources_path = current_app.config['basedir'] + '/resources'

    def set_assets(self, name, model):
        self.name = name
        self.model = model

        if name == 'Unified Endpoints Generator':
            self.set_assets_UEG()
        else:
            raise ValueError("Invalid input variable")

    def set_assets_UEG(self):
        UEG_path = self.resources_path + "/UEG"

        self.instructions_path = UEG_path +"/instructions.txt"
        self.tools = [{"type": "code_interpreter"},{"type": "file_search"}]
        self.tool_resources = "tool_resources"
    
