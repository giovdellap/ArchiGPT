#Assistant Handler in order to use GPTs throw API Endpoint

import os
from dotenv import load_dotenv
from openai import OpenAI

from EventHandler import EventHandler

load_dotenv()

# Global Variables
api_key = os.getenv("OPENAI_API_KEY")
client = OpenAI(api_key=api_key)
vector_store = "UEG_VS"
rag_folder_name = "RAG"
user_folder_name = "USER"
#rag_path = rag_folder_name
#user_path = user_folder_name
current_path = os.getcwd()
rag_path = os.path.join(current_path, rag_folder_name)
user_path = os.path.join(current_path, user_folder_name)

# ASSISTANT 

# Code Interpreter files
code_1 = os.path.join(rag_path, 'EXAMPLE 1 - source file.py')
code_2 = os.path.join(rag_path, 'EXAMPLE 2 - source files.zip')

#windows only


# Debug
print("paths", code_1, code_2)

file_code_1 = client.files.create(file=open(code_1, "rb"), purpose='assistants')
file_code_2 = client.files.create(file=open(code_2, "rb"), purpose='assistants')

# File Search files
search_1 = os.path.join(rag_path, 'EXAMPLE 1 - result.txt')
search_2 = os.path.join(rag_path, 'EXAMPLE 2 - result.txt')

# Debug
print("paths", code_1, code_2, search_1, search_2)

# Vector Store Creation
vector_store = client.beta.vector_stores.create(name=vector_store)
file_paths = [search_1, search_2]
file_streams = [open(path, "rb") for path in file_paths]
file_batch = client.beta.vector_stores.file_batches.upload_and_poll(
  vector_store_id=vector_store.id, files=file_streams
)
# You can print the status and the file counts of the batch to see the result of this operation.
print(file_batch.status)
print(file_batch.file_counts)


# Assistant Creation
assistant = client.beta.assistants.create(
    instructions="""You are a Software Engineer.
	You have to analyze a docker service and provide informations about its endpoints and exposed data structures.
	You will be provided with:
		- a description of the system's purpose and its user stories
		- the source files related to endpoints and data structures within the service

	Generate a txt file named "endpoints-datastructures" containing:
	1) a table with the following columns:
		- Endpoint URL (example: /animals/cats)
		- Request object
		- Response Object
		- HTTP Method

	2) a table for each of the Request or Response objects declared in the previous table, with the following columns:
		- variable name
		- variable type

	Your knowledge presents two examples with the source files and the related generated document.
	""",
    name="Unified Endpoints Generator",
    tools=[{"type": "code_interpreter"},{"type": "file_search"}],
    model="gpt-3.5-turbo",
    tool_resources={
		"code_interpreter": {
		"file_ids": [code_1.id,code_2.id]
		},
		"file_search": {
			"vector_store_ids": [vector_store.id]
		}
    }
)
print(assistant)


# THREAD

# Thread files
service_path = os.path.join(user_path, 'EXAMPLE 1 - source file.py')
user_stories_path = os.path.join(user_path, 'EXAMPLE 2 - source files.zip')
service_file = client.files.create(file=open(service_path, "rb"), purpose='assistants')
user_stories_file = client.files.create(file=open(user_stories_path, "rb"), purpose="assistants")

# Debug
print("paths", service_path, user_stories_path)

# Thread creation
thread = client.beta.threads.create()
message = client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content=""" 
    """,
    attachments=[
        {
            "file_id": service_file.id,
            "tools": [{"type": "code_interpreter"}]
        },
        {
			"file_id": user_stories_file.id,
			"tools": [{"type": "file_search"}]
		}
    ]
)


# Create the Run and stream the response.
 
with client.beta.threads.runs.stream(
  thread_id=thread.id,
  assistant_id=assistant.id,
  #instructions="Please address the user as Eugenio. The user has a premium account.",
  event_handler=EventHandler(),
) as stream:
  stream.until_done()