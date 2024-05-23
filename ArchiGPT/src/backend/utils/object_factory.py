def getBlankProjectSystem():
    return {
        'type': 'system',
        'data': [
            {
                'name': "userstories",
                'message': "",
            },
            {
                'name': "ContainerDesigner",
                'message': "",
            },
            {
                'name': "UserInteractionAnalyzer",
                'message': "",
            },
            {
                'name': "MissingUserStoriesMatcher",
                'message': "",
            },
            {
                'name': "ArchitecturalPatternProposer",
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
                    'name': "ContainerDesigner",
                    'status': "NEXT",
                },
                {
                    'name': "UserInteractionAnalyzer",
                    'status': "NO",
                },
                {
                    'name': "MissingUserStoriesMatcher",
                    'status': "NO",
                },
                {
                    'name': "ArchitecturalPatternProposer",
                    'status': "NO",
                },
            ],
            'containers': [],
        },
    }
