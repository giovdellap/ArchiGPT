from flask import current_app


def runCreationHandler(thread_id,assistant_id):
    try:
        run = current_app.config['CLIENT'].beta.threads.runs.create_and_poll(
            thread_id= thread_id,
            assistant_id= assistant_id
        )

        print("Run created successfully:", run.id)
        return run
    except Exception as e:
        print("Run creation failed:", e)
        return None