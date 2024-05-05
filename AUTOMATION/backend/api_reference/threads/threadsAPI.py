from flask import current_app


def threadCreationHandler():
    try:
        thread = current_app.config['CLIENT'].beta.threads.create()
        print("Thread created successfully:", thread.id)
        return thread
    except Exception as e:
        print("Thread creation failed:", e)
        return None