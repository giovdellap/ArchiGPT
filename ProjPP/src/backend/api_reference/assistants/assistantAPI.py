from flask import current_app
from api_reference.fileHandlerAPI import filePathCreationHandler, uploadFileBatchesVectorStore, vectoreStoreCreationHandler


def creationHandler(assistantObj):
    try:
        with open(assistantObj.instructions_path, 'r') as file:
            instructions_text = file.read()
        tool_resources = {}
        
        if 'code_interpreter' in assistantObj.tool_resources_path:
            tool_resources['code_interpreter'] = {}
            fileObjIds = []
            for filePath in assistantObj.tool_resources_path['code_interpreter']['file_paths']:
                fileObj = filePathCreationHandler(filePath)
                fileObjIds.append(fileObj.id)
            tool_resources['code_interpreter']['file_ids'] = fileObjIds
        
        if 'file_search' in assistantObj.tool_resources_path:
            tool_resources['file_search'] = {}
            file_streams = [open(path, "rb") for path in assistantObj.tool_resources_path['file_search']['vector_store_paths']]
            vector_store_name = assistantObj.tool_resources_path['file_search']['vector_store_name']

            vector_store = vectoreStoreCreationHandler(vector_store_name)
            file_batch_assistant = uploadFileBatchesVectorStore(vector_store, file_streams)
            tool_resources['file_search']['vector_store_ids'] = [vector_store.id]

        assistant = current_app.config['CLIENT'].beta.assistants.create(
            instructions=instructions_text,
            name=assistantObj.name,
            tools=assistantObj.tools,
            model=assistantObj.model,
            tool_resources=tool_resources,
        )
        print("Assistant created successfully:", assistant.id)
        return assistant.id
    except Exception as e:
        print("Assistant creation failed:", e)
        return "Assistant creation failed"


def getAssistant(assistant_id):
    try:
        assistantObj = current_app.config['CLIENT'].beta.assistants.retrieve(assistant_id)

        serialized_assistant = {
            "id": assistantObj.id,
            "name": assistantObj.name,
            "model": assistantObj.model,
            "instructions": assistantObj.instructions,
            #"tools": assistantObj.tools,
            "tool_resources": assistantObj.tool_resources.to_dict()
        }

        print("Assistant correctly recevived")
        return serialized_assistant
    
    except Exception as e:
        print("Assistant GET failed:", e)


def getAssistantList():
    try:
        list_assistants = current_app.config['CLIENT'].beta.assistants.list(
            order="desc",
            limit="20",
        )

        # Extract and serialize necessary data from each assistant
        serialized_assistants = []
        for assistant in list_assistants.data:
            serialized_assistant = {
                "id": assistant.id,
                "name": assistant.name,
                "model": assistant.model,
                "instructions": assistant.instructions,
                #"tools": assistant.tools,
                "tool_resources": assistant.tool_resources.to_dict()
            }
            serialized_assistants.append(serialized_assistant)

        print("Assistants List correctly recevived")
        return serialized_assistants
    
    except Exception as e:
        print("Assistants List GET failed:", e)


def assistantUpdate(assistantObj):
    try:
        # with open(assistantObj.instructions_path, 'r') as file:
        #     instructions_text = file.read()
        # tool_resources = {}
        
        # if 'code_interpreter' in assistantObj.tool_resources_path:
        #     tool_resources['code_interpreter'] = {}
        #     fileObjIds = []
        #     for filePath in assistantObj.tool_resources_path['code_interpreter']['file_paths']:
        #         fileObj = fileCreationHandler(filePath)
        #         fileObjIds.append(fileObj.id)
        #     tool_resources['code_interpreter']['file_ids'] = fileObjIds
        
        # if 'file_search' in assistantObj.tool_resources_path:
        #     tool_resources['file_search'] = {}
        #     file_streams = [open(path, "rb") for path in assistantObj.tool_resources_path['file_search']['vector_store_paths']]
        #     vector_store_name = assistantObj.tool_resources_path['file_search']['vector_store_name']

        #     vector_store = vectoreStoreCreationHandler(vector_store_name)
        #     file_batch_assistant = uploadFileBatchesVectorStore(vector_store, file_streams)
        #     tool_resources['file_search']['vector_store_ids'] = [vector_store.id]

        # assistant = current_app.config['CLIENT'].beta.assistants.create(
        #     instructions=instructions_text,
        #     name=assistantObj.name,
        #     tools=assistantObj.tools,
        #     model=assistantObj.model,
        #     tool_resources=tool_resources,
        # )
        print("Assistant updated successfully")

    except Exception as e:
        print("Assistant update failed:", e)
    

def assistantDelete(assistant_id):
    try:
        assistant = getAssistant(assistant_id)
        file_ids = assistant["tool_resources"]["code_interpreter"]["file_ids"]
        vector_store_ids = assistant["tool_resources"]["file_search"]["vector_store_ids"]

        print(file_ids,vector_store_ids)
        #response = current_app.config['CLIENT'].beta.assistants.delete(assistant_id)
        #if response.deleted: print("Assistant deleted successfully and related files")

    except Exception as e:
        print("Assistant delete failed:", e)