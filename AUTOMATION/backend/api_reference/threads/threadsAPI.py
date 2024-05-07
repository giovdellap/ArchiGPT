from flask import current_app


def threadCreationHandler():
    try:
        thread = current_app.config['CLIENT'].beta.threads.create()
        print("Thread created successfully:", thread.id)
        return thread
    except Exception as e:
        print("Thread creation failed:", e)
        return None
    
    
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