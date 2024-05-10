from flask import current_app

class AssistantFactory:

    def __init__(self) :
        self.resources_path = current_app.config['basedir'] + '/resources'

    def set_assets(self, name, model):
        self.name = name
        self.model = model

        if name == 'Unified Endpoints Generator':
            self.set_assets_UEG()
        elif name == 'Containers List Generator':
            self.set_assets_CLG()
        elif name == 'Endpoints Solo Generator':
            self.set_assets_ESG()
        elif name == 'DataStructures Solo Generator':
            self.set_assets_DSG()
        else:
            raise ValueError("Invalid assistant name")

    def set_assets_UEG(self):
        UEG_path = self.resources_path + "/UEG"
        RAG_path = UEG_path + "/RAG"
        RAG_files_paths = [RAG_path + '/EXAMPLE 1 - source file.py', RAG_path + '/EXAMPLE 2 - source files.zip']
        vector_store_paths = [RAG_path + '/EXAMPLE 1 - result.txt', RAG_path + '/EXAMPLE 2 - result.txt']

        self.instructions_path = UEG_path +"/instructions.txt"
        self.tools = [{"type": "code_interpreter"},{"type": "file_search"}]
        self.tool_resources_path = { "code_interpreter": { "file_paths": RAG_files_paths }, "file_search": { "vector_store_paths": vector_store_paths , "vector_store_name" : "UEG_VS" }
    }
        
    def set_assets_CLG(self):
        CLG_path = self.resources_path + "/CLG"
        RAG_path = CLG_path + "/RAG"
        #RAG_files_paths = [RAG_path + '/EXAMPLE - docker-compose.yml']
        #vector_store_paths = [RAG_path + '/EXAMPLE - DOCUMENT_B.txt', RAG_path + '/Docker.txt']
        vector_store_paths = [RAG_path + '/Docker.txt']


        self.instructions_path = CLG_path +"/instructions.txt"
        #self.tools = [{"type": "code_interpreter"},{"type": "file_search"}]
        #self.tool_resources_path = { "code_interpreter": { "file_paths": RAG_files_paths }, "file_search": { "vector_store_paths": vector_store_paths , "vector_store_name" : "CLG_VS" }
        self.tools = [{"type": "code_interpreter"}]
        self.tool_resources_path = {}
        
    def set_assets_ESG(self):
        ESG_path = self.resources_path + "/ESG"
        RAG_path = ESG_path + "/RAG"
        RAG_files_paths = [RAG_path + '/EXAMPLE 1 - source file.py', RAG_path + '/EXAMPLE 2 - source files.zip']
        vector_store_paths = [RAG_path + '/EXAMPLE 1 - result.txt', RAG_path + '/EXAMPLE 2 - result.txt']

        self.instructions_path = ESG_path +"/instructions.txt"
        self.tools = [{"type": "code_interpreter"},{"type": "file_search"}]
        self.tool_resources_path = { "code_interpreter": { "file_paths": RAG_files_paths }, "file_search": { "vector_store_paths": vector_store_paths , "vector_store_name" : "ESG_VS" }
    }
        
    def set_assets_DSG(self):
        DSG_path = self.resources_path + "/DSG"
        RAG_path = DSG_path + "/RAG"
        RAG_files_paths = [RAG_path + '/EXAMPLE 1 - source file.py', RAG_path + '/EXAMPLE 2 - source files.zip']
        vector_store_paths = [RAG_path + '/EXAMPLE 1 - endpoints.txt', RAG_path + '/EXAMPLE 2 - endpoints.txt', RAG_path + '/EXAMPLE 1 - result.txt', RAG_path + '/EXAMPLE 2 - result.txt']

        self.instructions_path = DSG_path +"/instructions.txt"
        self.tools = [{"type": "code_interpreter"},{"type": "file_search"}]
        self.tool_resources_path = { "code_interpreter": { "file_paths": RAG_files_paths }, "file_search": { "vector_store_paths": vector_store_paths , "vector_store_name" : "DSG_VS" }
    }
    
