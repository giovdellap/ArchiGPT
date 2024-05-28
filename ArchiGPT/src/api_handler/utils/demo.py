def demoResponse(assistant):
    if assistant == "Util_1":
        return "['container_1', 'container_2']"
    elif assistant == "Util_2":
        return "{ 'description': 'Description container test', 'userstories': 'userstories container test', 'ports' : 'ports test'}"
    elif assistant == "Util_3":
        return "[{'name' : 'service_1', 'type': 'backend', 'description' : 'description service_1 test', 'port' : '10010'},{'name' : 'service_1','type': 'backend','description': 'description service_1 test', 'port' : '10010'}]"
    else:
        return "DEMO MESSAGE: " + assistant