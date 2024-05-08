

def creationHandler(assistantObj, client):
    try:
        with open(assistantObj.instructions_path, 'r') as file:
            instructions_text = file.read()
        tool_resources = {}
        
        if 'code_interpreter' in assistantObj.tool_resources_path:
            tool_resources['code_interpreter'] = {}
            fileObjIds = []
            for filePath in assistantObj.tool_resources_path['code_interpreter']['file_paths']:
                fileObj = fileCreationHandler(filePath, client)
                fileObjIds.append(fileObj.id)
            tool_resources['code_interpreter']['file_ids'] = fileObjIds
        
        if 'file_search' in assistantObj.tool_resources_path:
            tool_resources['file_search'] = {}
            file_streams = [open(path, "rb") for path in assistantObj.tool_resources_path['file_search']['vector_store_paths']]
            vector_store_name = assistantObj.tool_resources_path['file_search']['vector_store_name']

            vector_store = vectoreStoreCreationHandler(vector_store_name, client)
            file_batch_assistant = uploadFileBatchesVectorStore(vector_store, file_streams, client)
            tool_resources['file_search']['vector_store_ids'] = [vector_store.id]

        assistant = client.beta.assistants.create(
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


def fileCreationHandler(filePath,client):
    try:
        fileObj = client.files.create(
            file=open(filePath, "rb"),
            purpose='assistants'
        )
        print("File uploaded successfully:", fileObj.id)
        return fileObj
    except Exception as e:
        print("File upload failed:", e)
        return None
    

def vectoreStoreCreationHandler(name, client):
    try:
        vector_store = client.beta.vector_stores.create(name=name)
        print("Vector store created successfully:", vector_store.id)
        return vector_store
    except Exception as e:
        print("Vector store creation failed:", e)
        return None


def uploadFileBatchesVectorStore(vector_store, file_streams, client):
    try:
        file_batch = client.beta.vector_stores.file_batches.upload_and_poll(
            vector_store_id=vector_store.id, files=file_streams
        )
        print("File batch upload status:",file_batch.file_counts,file_batch.status)
    except Exception as e:
        print("File batch upload failed:", e)


def getAssistantList(client):
    try:
        list_assistants = client.beta.assistants.list(
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
                #"tool_resources": assistant.tool_resources
            }
            serialized_assistants.append(serialized_assistant)

        print("Assistants List correctly recevived")
        return serialized_assistants
    
    except Exception as e:
        print("Assistants List GET failed:", e)


def assistantUpdate(assistantObj, client):
    try:
        # with open(assistantObj.instructions_path, 'r') as file:
        #     instructions_text = file.read()
        # tool_resources = {}
        
        # if 'code_interpreter' in assistantObj.tool_resources_path:
        #     tool_resources['code_interpreter'] = {}
        #     fileObjIds = []
        #     for filePath in assistantObj.tool_resources_path['code_interpreter']['file_paths']:
        #         fileObj = fileCreationHandler(filePath, client)
        #         fileObjIds.append(fileObj.id)
        #     tool_resources['code_interpreter']['file_ids'] = fileObjIds
        
        # if 'file_search' in assistantObj.tool_resources_path:
        #     tool_resources['file_search'] = {}
        #     file_streams = [open(path, "rb") for path in assistantObj.tool_resources_path['file_search']['vector_store_paths']]
        #     vector_store_name = assistantObj.tool_resources_path['file_search']['vector_store_name']

        #     vector_store = vectoreStoreCreationHandler(vector_store_name, client)
        #     file_batch_assistant = uploadFileBatchesVectorStore(vector_store, file_streams, client)
        #     tool_resources['file_search']['vector_store_ids'] = [vector_store.id]

        # assistant = client.beta.assistants.create(
        #     instructions=instructions_text,
        #     name=assistantObj.name,
        #     tools=assistantObj.tools,
        #     model=assistantObj.model,
        #     tool_resources=tool_resources,
        # )
        print("Assistant updated successfully")

    except Exception as e:
        print("Assistant update failed:", e)
    

def assistantDelete(assistant_id, client):
    try:
        response = client.beta.assistants.delete(assistant_id)
        if response.deleted: print("Assistant deleted successfully")

    except Exception as e:
        print("Assistant delete failed:", e)