system_assistants = [
    'ContainerDesigner',
    'MissingUserStoriesMatcher',
    'ArchitecturalPatternProposer'
]

def getAssistantName(name):
    if name == 'ContainerDesigner':
        return 'Archi_1'
    elif name == 'MissingUserStoriesMatcher':
        return 'Archi_2'
    elif name == 'ArchitecturalPatternProposer':
        return 'Archi_3'
    elif name == 'ContainerPurposeGenerator':
        return 'Archi_4'
    else:
        return 'Wrong assistant name'
    
def getNextAssistant(name):
    index = system_assistants.index(name)
    return system_assistants[index+1]

    