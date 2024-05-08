from flask import current_app

from api_reference.assistants.assistantAPI import creationHandler, getAssistantList
from api_reference.assistants.assistantFactory import AssistantFactory


class AssistantOrchestrator:

	def __init__(self) :
		self.client = current_app.config['CLIENT']

	def assistantCreation(self,name,model):

		# Factory Method pattern 
		assistantObj = AssistantFactory()
		assistantObj.set_assets(name,model)

		assistant_id = creationHandler(assistantObj,self.client)

		return assistant_id
	
	def assistantListProvider(self):
				
		list_assistants = getAssistantList(self.client)

		return list_assistants

