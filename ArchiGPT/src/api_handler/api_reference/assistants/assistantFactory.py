from flask import current_app

class AssistantFactory:

    def __init__(self) :
        self.resources_path = current_app.config['basedir'] + '/resources'


    def set_assets(self, name, model):
        self.name = name
        self.model = model
        path = self.resources_path + "/" + name
        self.instructions_path = path +"/instructions.txt"
        self.tools = []
        self.tool_resources_path = {}
       
    def set_assets_Archi_1(self):
        CLG_path = self.resources_path + "/Archi_1"
        self.instructions_path = CLG_path +"/instructions.txt"
        self.tools = []
        self.tool_resources_path = {}
        
    def set_assets_Archi_2(self):
        CLG_path = self.resources_path + "/Archi_2"
        self.instructions_path = CLG_path +"/instructions.txt"
        self.tools = []
        self.tool_resources_path = {}
    
        
    def set_assets_DSG(self):
        DSG_path = self.resources_path + "/DSG"
        RAG_path = DSG_path + "/RAG"
        RAG_files_paths = [RAG_path + '/EXAMPLE 1 - source file.py', RAG_path + '/EXAMPLE 2 - source files.zip']
        vector_store_paths = [RAG_path + '/EXAMPLE 1 - endpoints.txt', RAG_path + '/EXAMPLE 2 - endpoints.txt', RAG_path + '/EXAMPLE 1 - result.txt', RAG_path + '/EXAMPLE 2 - result.txt']

        self.instructions_path = DSG_path +"/instructions.txt"
        self.tools = [{"type": "code_interpreter"},{"type": "file_search"}]
        self.tool_resources_path = { "code_interpreter": { "file_paths": RAG_files_paths }, "file_search": { "vector_store_paths": vector_store_paths , "vector_store_name" : "DSG_VS" }
    }
    
