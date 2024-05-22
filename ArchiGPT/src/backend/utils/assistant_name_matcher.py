system_assistants = [
    'ContainerDesigner',
    'UserInteractionAnalyzer',
    'MissingUserStoriesMatcher',
    'ArchitecturalPatternProposer'
]

def getAssistantName(name):
    if name == 'ContainerDesigner':
        return 'System_1'
    elif name == 'UserInteractionAnalyzer':
        return 'System_2'
    elif name == 'MissingUserStoriesMatcher':
        return 'System_3'
    elif name == 'ArchitecturalPatternProposer':
        return 'System_4'
    elif name == 'ContainerDescriptionGenerator':
        return 'Container_1'
    else:
        return 'Wrong assistant name'
    
def getNextAssistant(name):
    index = system_assistants.index(name)
    #if index == len(system_assistants):
    if name == "UserInteractionAnalyzer":
        return 'CONTAINER'
    else:
        return system_assistants[index+1]

    