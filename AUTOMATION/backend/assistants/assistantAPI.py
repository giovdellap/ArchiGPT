


def creationHandler(assistantObj,client):

	print(assistantObj.instructions_path,assistantObj.name,assistantObj.tools,assistantObj.model,assistantObj.tool_resources)

	# assistant = client.beta.assistants.create(
	# 	instructions= assistantObj.instructions,
	# 	name= assistantObj.name,
	# 	tools= assistantObj.tools,
	# 	model= assistantObj.model,
	# 	tool_resources= assistantObj.tool_resources,
	# ) 
	# return assistant.id
	
	return ""

def fileCreationHandler(filePath,client):

	print(filePath)

	fileObj = client.files.create(
		file=open(filePath, "rb"),
		purpose='assistants'
	)

	return fileObj