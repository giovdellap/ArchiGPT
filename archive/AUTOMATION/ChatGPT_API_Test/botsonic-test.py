#Botsonic GPTs handler script

import os
from dotenv import load_dotenv
import uuid
import requests

load_dotenv()
token = os.getenv("BOTSONIC_API_KEY")

endpoint = 'https://api-azure.botsonic.ai/v1/botsonic/generate'
payload = {
    "input_text": "How to develop these skills? please answer.",
    "chat_id": str(uuid.uuid4()),
    #"chat_history": [{ "sent": True }],
}
headers = {
    "accept": "application/json",
    "content-type": "application/json",
    "token": token
}
response = requests.post(endpoint, json=payload, headers=headers)

print(response,response.text, str(uuid.uuid4()))
