from flask import current_app
import requests


def api_call( api_name, content ):

	print('API INTERROGATION: ', api_name)
	message = requests.post(
		current_app.config['BACKEND_HANDLER'] + '/{api_name}',
		data={
		}
	)

	result = message.json()['content']
	#print('RECEIVED MESSAGE: ', result)
		
	return result