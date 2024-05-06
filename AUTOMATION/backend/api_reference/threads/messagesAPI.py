from flask import current_app


def messageCreationHandler(thread_id,content,attachments):
    try:
        message = current_app.config['CLIENT'].beta.threads.messages.create(
            thread_id=thread_id,
            role="user",
            content=content,
            attachments=attachments
        )
        print("Message created successfully:", message.id)
        return message
    except Exception as e:
        print("Message creation failed:", e)
        return None