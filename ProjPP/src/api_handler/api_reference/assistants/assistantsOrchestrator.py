from flask import current_app

from api_reference.assistants.assistantAPI import assistantDelete, assistantUpdate, creationHandler, getAssistant, getAssistantList
from api_reference.assistants.assistantFactory import AssistantFactory


class AssistantOrchestrator:

	def assistantCreation(self,name,model, req_ci, req_vs):

		# Factory Method pattern 
		assistantObj = AssistantFactory()
		assistantObj.set_assets(name,model)

		assistant_id = creationHandler(assistantObj, req_ci, req_vs)

		return assistant_id
	
	def assistantProvider(self,assistant_id):
				
		assistantObj = getAssistant(assistant_id)

		return assistantObj
	
	def assistantListProvider(self):
				
		list_assistants = getAssistantList()

		return list_assistants
	
	def assistantUpdate(self,assistantObj):

		assistantUpdate(assistantObj)
	
	def assistantDelete(self,assistant_id):	

		assistantDelete(assistant_id)
