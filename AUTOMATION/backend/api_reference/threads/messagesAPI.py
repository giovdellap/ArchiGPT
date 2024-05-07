import json
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
    

def messageListRetriever(thread_id):
    try:
        thread_messages = current_app.config['CLIENT'].beta.threads.messages.list(
            thread_id=thread_id,
        )
        # Extract and serialize necessary data from each message
        serialized_messages = []
        for message in thread_messages.data:
            if message.content:
                content_text = message.content[0].text.value
            else:
                content_text = "No content available"
            serialized_message = {
                "id": message.id,
                "content": content_text
            }
            serialized_messages.append(serialized_message)

        # Convert list of dictionaries to JSON
        serialized_messages_json = json.dumps(serialized_messages, ensure_ascii=False)

        return serialized_messages_json
    except Exception as e:
        print(f"Message data failed:", e)
        return None