def buildPreMessage(assistant, message):
    premessage = ""
    if assistant == 'Container_1':
        premessage = "DESCRIPTION: \n"
    elif assistant == 'Container_3':
        premessage = "MICROSERVICES: \n"
    
    return premessage + message