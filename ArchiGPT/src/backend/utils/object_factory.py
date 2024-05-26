def getBlankProjectSystem():
    return {
        'type': 'system',
        'data': [
            {
                'name': "userstories",
                'message': "",
            },
            {
                'name': "description",
                'message': "",
            },
            {
                'name': "Container Design",
                'message': "",
            },
            {
                'name': "User Interaction Analysis",
                'message': "",
            },
            {
                'name': "Match Missing User Stories",
                'message': "",
            },
            {
                'name': "Architectural Patterns",
                'message': "",
            },
        ],
    }
    
def getBlankProjectStatus():
    return {
        'type': 'status',
        'data': {
            'system': [
                {
                    'name': "Container Design",
                    'status': "NEXT",
                },
                {
                    'name': "User Interaction Analysis",
                    'status': "NO",
                },
                {
                    'name': "Match Missing User Stories",
                    'status': "NO",
                },
                {
                    'name': "Architectural Patterns ",
                    'status': "NO",
                },
            ],
            'containers': [],
        },
    }
