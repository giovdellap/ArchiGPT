
class PostProcessingHandler:
    
    def processMessage(assistant, message):
        if assistant == 'Container_1':
            premessage = "DESCRIPTION: \n"
            return premessage + message
        else:
            return message