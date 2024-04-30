#Assistant Handler in order to use GPTs throw API Endpoint

import os
from dotenv import load_dotenv
from openai import OpenAI
from AUTOMATION.utils.EventHandler import EventHandler

load_dotenv()
api_key = os.getenv("OPENAI_API_KEY")
client = OpenAI(api_key=api_key)

#Unified Endpoints Generator

assistant = client.beta.assistants.create(
    instructions=""" You are a Software Engineer. 
	You have to analyze a docker service and provide informations about its endpoints and exposed data structures.
	You will be provided with:
    - a description of the system's purpose and its user stories
    - the source files related to endpoints and data structures within the service""",
    name="Unified Endpoints Generator",
    tools=[{"type": "code_interpreter"}],
    model="gpt-4-turbo",
)
print(assistant)


thread = client.beta.threads.create()

message = client.beta.threads.messages.create(
  thread_id=thread.id,
  role="user",
  content="""Generate a txt file named "endpoints-datastructures" containing:
	1) a table with the following columns:
		- Endpoint URL (example: /animals/cats)
		- Request object
		- Response Object
		- HTTP Method

	2) a table for each of the Request or Response objects declared in the previous table, with the following columns:
		- variable name
		- variable type

	Your knowledge presents two examples with the source files and the related generated document
  
  
	Input:
	- User Stories
	- Single Source files

	Output:
	- Document D
		- Endpoints
		- Data Structures

	"""
)	
 
# Create the Run and stream the response.
 
with client.beta.threads.runs.stream(
  thread_id=thread.id,
  assistant_id=assistant.id,
  instructions="Please address the user as Eugenio. The user has a premium account.",
  event_handler=EventHandler(),
) as stream:
  stream.until_done()