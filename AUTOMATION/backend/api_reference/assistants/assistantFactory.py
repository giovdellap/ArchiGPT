from flask import current_app

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
        RAG_path = UEG_path + "/RAG"
        RAG_files_paths = [RAG_path + '/EXAMPLE 1 - source file.py', RAG_path + '/EXAMPLE 2 - source files.zip']
        vector_store_paths = [RAG_path + '/EXAMPLE 1 - result.txt', RAG_path + '/EXAMPLE 2 - result.txt']

        self.instructions_path = UEG_path +"/instructions.txt"
        self.tools = [{"type": "code_interpreter"},{"type": "file_search"}]
        self.tool_resources_path = { "code_interpreter": { "file_paths": RAG_files_paths }, "file_search": { "vector_store_paths": vector_store_paths , "vector_store_name" : "UEG_VS" }
    }
    
