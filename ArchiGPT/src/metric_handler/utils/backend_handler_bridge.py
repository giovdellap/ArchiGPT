from flask import current_app
import requests


def backend_call( api_name, data ):

	print('API INTERROGATION: ', api_name)

	message = requests.post(
		current_app.config['BACKEND_HANDLER'] + f'/{api_name}',
		data = data
	)

	#result = message.json()['content']
	print('RECEIVED MESSAGE: ', message)
		
	return message

def backend_callwithFile( api_name, data, file ):

	print('API INTERROGATION: ', api_name)

	message = requests.post(
		current_app.config['BACKEND_HANDLER'] + f'/{api_name}',
		data = data,
		files = file
	)

	result = message.json()
	print('RECEIVED MESSAGE: ', result)
		
	return result