from flask import current_app
from openai import OpenAI

from assistants.assistantAPI import creationHandler, fileCreationHandler
from assistants.assistantFactory import AssistantFactory


class AssistantOrchestrator:

	def __init__(self) :
		self.client = OpenAI(api_key = current_app.config['OPENAI_API_KEY'])

	def assistantCreation(self,name,model,user_files):

		# Factory Method pattern 
		assistantObj = AssistantFactory()
		assistantObj.set_assets(name,model)

		assistant_id = creationHandler(assistantObj,self.client)

		return assistant_id


