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
            }
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
            ],
            'containers': [],
        },
    }
