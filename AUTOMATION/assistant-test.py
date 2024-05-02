#Assistant Handler in order to use GPTs throw API Endpoint

import os
from dotenv import load_dotenv
from openai import OpenAI

from utils.EventHandler import EventHandler

load_dotenv()
api_key = os.getenv("OPENAI_API_KEY")
client = OpenAI(api_key=api_key)

# Specify the folder name where your PDF file is located on your desktop
folder_name = "RAG"
# Assuming your PDF file is named "MACC.pdf" and is located in the specified folder on your desktop
desktop_path = os.path.join(os.path.expanduser('~'), 'Desktop/Thesis/thesis/AUTOMATION/UEG')
folder_path = os.path.join(desktop_path, folder_name)
example1py_path = os.path.join(folder_path, 'EXAMPLE 1 - source file.py')
example2zip_path = os.path.join(folder_path, 'EXAMPLE 2 - source files.zip')
news_path = os.path.join(folder_path, 'news.zip')

print(example1py_path,example2zip_path)

# Upload a file with an "assistants" purpose
example1py = client.files.create(
    file=open(example1py_path, "rb"),
    purpose='assistants'
)

example2zip = client.files.create(
    file=open(example2zip_path, "rb"),
    purpose='assistants'
)

#Unified Endpoints Generator

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
	
	EXAMPLE 1

		### Endpoints:

		Endpoint URL | Request Object | Response Object | HTTP Method
		-------------------------------------------------------------
		/<role>/login | Login Request | Login Response | POST
		/<role>/signup | Signup Request | Signup Response | POST
		/verifyToken | Token Verification Request | Token Verification Response | POST


		### Data Structures:

		#### Login Request:
		Variable Name | Variable Type
		----------------------------
		email | string
		password | string

		#### Login Response:
		Variable Name | Variable Type
		----------------------------
		token | string
		success | boolean

		#### Signup Request:
		Variable Name | Variable Type
		----------------------------
		email | string
		password | string
		name | string
		address | string

		#### Signup Response:
		Variable Name | Variable Type
		----------------------------
		user_id | string
		success | boolean

		#### Token Verification Request:
		Variable Name | Variable Type
		----------------------------
		token | string

		#### Token Verification Response:
		Variable Name | Variable Type
		----------------------------
		valid | boolean
		role | string
    
    EXAMPLE 2
    
		### Endpoints:

		Endpoint URL | Request Object | Response Object | HTTP Method
		-------------------------------------------------------------
		/customer/login | LoginInput | LoginResponse | POST
		/customer/signup | SignupInput | SignupResponse | POST
		/customer/get | None | GetClientByIDResponse | GET


		### Data Structures: 

		#### LoginInput:
		Variable Name | Variable Type
		-----------------------------
		username | String
		password | String

		#### LoginResponse:
		Variable Name | Variable Type
		-----------------------------
		userId | String
		token | String

		#### SignupInput:
		Variable Name | Variable Type
		-----------------------------
		username | String
		email | String
		password | String

		#### SignupResponse:
		Variable Name | Variable Type
		-----------------------------
		success | boolean
		userId | String

		#### GetClientByIDResponse:
		Variable Name | Variable Type
		-----------------------------
		clientId | String
		firstName | String
		lastName | String
		email | String

	""",
    name="Unified Endpoints Generator",
    tools=[{"type": "code_interpreter"},{"type": "file_search"}],
    model="gpt-3.5-turbo",
    tool_resources={
		"code_interpreter": {
		"file_ids": [example1py.id,example2zip.id]
		}
    }
)
print(assistant)


thread = client.beta.threads.create()

# Upload a file with an "assistants" purpose
newszip = client.files.create(
    file=open(news_path, "rb"),
    purpose='assistants'
)

message = client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content=""" 
    """,
    attachments=[
        {
            "file_id": newszip.id,
            "tools": [{"type": "code_interpreter"}]
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