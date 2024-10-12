import re
import json


def userstoriesFormatter( user_stories_string ):

	try:

		# Using regular expression to extract all numbers before ')'
		user_stories_numbers = re.findall(r'(\d+)\)', user_stories_string)

		# Converting the list of strings to a list of integers
		user_stories_numbers = [int(num) for num in user_stories_numbers]
		
		return user_stories_numbers

	except Exception as e:
		print(f"Error occurred: {e} in ", user_stories_string)
		return []

def endpointsFormatter( endpoints_string ):

	try:
		# Remove the "ENDPOINTS:" part to isolate the JSON-like structure
		json_string_temp = re.sub(r"ENDPOINTS:\s+", "", endpoints_string)

		# Handle possible generation errors
		if json_string_temp[0] == "`":
			ts = json_string_temp
			json_string_temp = ts.replace("```json", "")
		json_string = json_string_temp.replace("\n", " \n")

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
	
	except Exception as e:
		print(f"Error occurred: {e} in ", endpoints_string)
		return []

def pagesFormatter( pages_string ):

	try:
		# Remove the "PAGES:" part to isolate the JSON-like structure
		json_string_temp = re.sub(r"PAGES:\s+", "", pages_string)

		# Handle possible generation errors
		if json_string_temp[0] == "'":
			ts = json_string_temp
			json_string_temp = ts.replace("```json", "")
		json_string = json_string_temp.replace("\n", " \n")

		# Convert the string to a valid Python list (JSON-like structure)
		pages_data = json.loads(json_string)

		# Transforming the structure
		transformed_pages = []
		for page in pages_data:
			transformed_pages.append({
				"name": page["PageName"],
				"userStoryIndex": page["UserStories"]
			})
			
		return transformed_pages
	
	except Exception as e:
		print(f"Error occurred: {e} in ", pages_string)
		return []