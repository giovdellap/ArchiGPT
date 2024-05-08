from flask import current_app


def fileCreationHandler(file):
    try:
        fileObj = current_app.config['CLIENT'].files.create(
            file=file,
            purpose='assistants'
        )
        print("File uploaded successfully:", fileObj.id)
        return fileObj
    except Exception as e:
        print("File upload failed:", e)
        return None
    

def filePathCreationHandler(filePath):
    try:
        fileObj = current_app.config['CLIENT'].files.create(
            file=open(filePath, "rb"),
            purpose='assistants'
        )
        print("File uploaded successfully:", fileObj.id)
        return fileObj
    except Exception as e:
        print("File upload failed:", e)
        return None
    

def vectoreStoreCreationHandler(name):
    try:
        vector_store = current_app.config['CLIENT'].beta.vector_stores.create(name=name)
        print("Vector store created successfully:", vector_store.id)
        return vector_store
    except Exception as e:
        print("Vector store creation failed:", e)
        return None


def uploadFileBatchesVectorStore(vector_store, file_streams):
    try:
        file_batch = current_app.config['CLIENT'].beta.vector_stores.file_batches.upload_and_poll(
            vector_store_id=vector_store.id, files=file_streams
        )
        print("File batch upload status:",file_batch.file_counts,file_batch.status)
    except Exception as e:
        print("File batch upload failed:", e)