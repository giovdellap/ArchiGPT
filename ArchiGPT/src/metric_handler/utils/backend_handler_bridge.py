from flask import current_app
import requests


def generationRequest( api_name, data ):

	print('API INTERROGATION: ', api_name)

	message = requests.post(
		current_app.config['BACKEND_HANDLER'] + f'/{api_name}',
		data = data
	)

	result = message.json()
	print('RECEIVED MESSAGE: ', result)
		
	return result

def generationRequestWithFile( api_name, data, file ):

	print('API INTERROGATION: ', api_name)

	message = requests.post(
		current_app.config['BACKEND_HANDLER'] + f'/{api_name}',
		data = data,
		files = file
	)

	result = message.json()
	print('RECEIVED MESSAGE: ', result)
		
	return result

def getDataRequest( api_name, params ):

	print('API INTERROGATION: ', api_name)

	message = requests.get(
		current_app.config['BACKEND_HANDLER'] + f'/{api_name}',
		params = params
	)

	result = message.json()
	print('RECEIVED MESSAGE: ', result)
		
	return result