import os
#from dotenv import load_dotenv
from openai import OpenAI

from EventHandler import EventHandler

#load_dotenv()

# Global Variables
api_key = os.getenv("OPENAI_API_KEY", "sk-proj-qTNW2Bsgu02jS5sSuBXHT3BlbkFJ3r32uljTh68oZtY1OTu8")
#api_key = os.getenv("OPENAI_API_KEY")
client = OpenAI(api_key=api_key)
vector_store_name = "UEG_VS"
rag_folder_name = "RAG"
user_folder_name = "USER"
#rag_path = rag_folder_name
#user_path = user_folder_name
current_path = os.current_path = os.path.dirname(os.path.realpath(__file__))
rag_path = os.path.join(current_path, rag_folder_name)
user_path = os.path.join(current_path, user_folder_name)

# ASSISTANT 

# Code Interpreter files
rag_code_1_path = os.path.join(rag_path, 'EXAMPLE 1 - source file.py')
rag_code_2_path = os.path.join(rag_path, 'EXAMPLE 2 - source files.zip')

# Debug
print("CODE PATHS:", rag_code_1_path, rag_code_2_path)

rag_code_1_file = client.files.create(file=open(rag_code_1_path, "rb"), purpose='assistants')
rag_code_2_file = client.files.create(file=open(rag_code_2_path, "rb"), purpose='assistants')

# File Search files
rag_search_1_path = os.path.join(rag_path, 'EXAMPLE 1 - result.txt')
rag_search_2_path = os.path.join(rag_path, 'EXAMPLE 2 - result.txt')

# Debug
print("SEARCH PATHS:", rag_search_1_path, rag_search_2_path)

# Vector Store Creation
vector_store = client.beta.vector_stores.create(name=vector_store_name)
file_paths = [rag_search_1_path, rag_search_2_path]
file_streams = [open(path, "rb") for path in file_paths]
file_batch = client.beta.vector_stores.file_batches.upload_and_poll(
  vector_store_id=vector_store.id, files=file_streams
)
# You can print the status and the file counts of the batch to see the result of this operation.
print(file_batch.status)
print(file_batch.file_counts)


# Assistant Creation
assistant = client.beta.assistants.create(
    instructions="""
    You are a Software Engineer.
	You have to analyze a docker service and provide informations about its endpoints and exposed data structures.
	You will be provided with:
    	- a description of the system's purpose and its user stories
    	- the source files related to endpoints and data structures within the service

	Generate a text containing:
	1) a table with the following columns:
    	- Endpoint URL (example: /animals/cats)
    	- Request object
    	- Response Object
    	- HTTP Method

	2) a table for each of the Request or Response objects declared in the previous table, with the following columns:
    	- variable name
    	- variable type

	You have 2 examples of the text to be generated:
		- assistant's Code interpreter contains source files of the examples
		- assistant's File search contains two output examples 
	""",
    name="Unified Endpoints Generator",
    tools=[{"type": "code_interpreter"},{"type": "file_search"}],
    model="gpt-4-turbo-2024-04-09",
    tool_resources={
		"code_interpreter": {
		"file_ids": [rag_code_1_file.id,rag_code_2_file.id]
		},
		"file_search": {
			"vector_store_ids": [vector_store.id]
		}
    }
)
print(assistant)


# THREAD

# Thread files
user_service_path = os.path.join(user_path, 'service.zip')
user_userstories_path = os.path.join(user_path, 'User Stories.txt')
user_service_file = client.files.create(file=open(user_service_path, "rb"), purpose='assistants')
user_userstories_file = client.files.create(file=open(user_userstories_path, "rb"), purpose="assistants")

# Debug
print("USER PATHS", user_service_path, user_userstories_path)

# Thread creation
thread = client.beta.threads.create()
message = client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content=""" 
    """,
    attachments=[
        {
            "file_id": user_service_file.id,
            "tools": [{"type": "code_interpreter"}]
        },
        {
			"file_id": user_userstories_file.id,
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