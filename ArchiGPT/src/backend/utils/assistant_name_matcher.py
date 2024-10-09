system_phases = [
    'Container Design',
    'User Interaction Analysis',
    'Match Missing User Stories',
    'Architectural Patterns'
]

container_phases = [
    'ContainerDescriptionGenerator',
    'ContainerSpecificationGenerator',
    'MicroServices'
]

service_phases = [
    'ServiceSpecificationGenerator',
    'ServiceEndpointGenerator',
    'ServicePageGenerator'
]

def getAssistantName(name):
    
    if name in system_phases:
        return 'System_' + str(system_phases.index(name) + 1)
    elif name in container_phases:
        return 'Container_' + str(container_phases.index(name) + 1)
    elif name in service_phases:
        return 'Service_' + str(service_phases.index(name) + 1)
    else:
        return 'Wrong assistant name'
    
def getNextSystemAssistant(name):
    index = system_phases.index(name)
    #if index == len(system_assistants):
    if name == "User Interaction Analysis":
        return 'CONTAINER'
    else:
        return system_phases[index+1]

def getNextContainerAssistant(name):
    index = container_phases.index(name)
    #if index == len(system_assistants):
    if name == "MicroServices":
        return 'SERVICE'
    else:
        return container_phases[index+1]
    
def getNextServiceAssistant(name):
    index = service_phases.index(name)
    #if index == len(system_assistants):
    if name == "ServicePageGenerator":
        return ''
    else:
        return service_phases[index+1]
    