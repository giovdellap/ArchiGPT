from flask import current_app
import re
import json


def userstoriesFormatter( user_stories_string ):

	# Using regular expression to extract all numbers before ')'
	user_stories_numbers = re.findall(r'(\d+)\)', user_stories_string)

	# Converting the list of strings to a list of integers
	user_stories_numbers = [int(num) for num in user_stories_numbers]
		
	return user_stories_numbers

def endpointsFormatter( endpoints_string ):

	# Remove the "ENDPOINTS:" part to isolate the JSON-like structure
	json_string = re.sub(r"ENDPOINTS:\s+", "", endpoints_string)

	# Convert the string to a valid Python list (JSON-like structure)
	endpoints_data = json.loads(json_string)

	# Transforming the structure
	transformed_endpoints = []
	for endpoint in endpoints_data:
		transformed_endpoints.append({
			"url": endpoint["URL"],
			"method": endpoint["Method"],
			"userStoryIndex": endpoint["UserStories"]
		})
		
	return transformed_endpoints