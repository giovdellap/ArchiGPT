def buildPreMessage(assistant, message):
    premessage = ""
    if assistant == 'Container_1':
        premessage = "DESCRIPTION: \n"
    elif assistant == 'Container_3':
        premessage = "MICROSERVICES: \n"
    elif assistant == 'Service_2':
        premessage = "ENDPOINTS: \n"
    elif assistant == 'Service_3':
        premessage = "PAGES: \n"
    
    return premessage + message