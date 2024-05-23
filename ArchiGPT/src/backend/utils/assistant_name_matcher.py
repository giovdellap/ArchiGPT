system_assistants = [
    'ContainerDesigner',
    'UserInteractionAnalyzer',
    'MissingUserStoriesMatcher',
    'ArchitecturalPatternProposer'
]

container_assistants = [
    'ContainerDescriptionGenerator',
    'ContainerSpecificationGenerator',
    'ServiceListGenerator'
]

def getAssistantName(name):
    
    if name in system_assistants:
        return 'System_' + str(system_assistants.index(name) + 1)
    elif name in container_assistants:
        return 'Container_' + str(container_assistants.index(name) + 1)
    else:
        return 'Wrong assistant name'
    
def getNextSystemAssistant(name):
    index = system_assistants.index(name)
    #if index == len(system_assistants):
    if name == "UserInteractionAnalyzer":
        return 'CONTAINER'
    else:
        return system_assistants[index+1]

def getNextContainerAssistant(name):
    index = container_assistants.index(name)
    #if index == len(system_assistants):
    if name == "ContainerDescriptionGenerator":
        return 'SERVICE'
    else:
        return container_assistants[index+1]
    