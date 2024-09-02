from flask import current_app
import requests


def assistant_call( ass_name, content ):

	print('ASSISTANT INTERROGATION: ', ass_name)
	message = requests.post(
		current_app.config['API_HANDLER'] + '/interrogation/interrogate',
		data={
			'ass_name': ass_name,
			'ass_model': 'gpt-4o-mini',
			#'ass_model': 'gpt-4-turbo-2024-04-09',
			'content': content
		}
	)

	result = message.json()['content']
	#print('RECEIVED MESSAGE: ', result)
		
	return result