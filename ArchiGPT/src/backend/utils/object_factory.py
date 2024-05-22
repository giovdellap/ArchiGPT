def getTestProjectSystem():
    return {
        'type': 'system',
        'data': [
            {
                'name': "ContainerDesigner",
                'message': "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec finibus pellentesque turpis, quis venenatis ligula tincidunt a. Sed eleifend diam dolor, ac suscipit lectus vulputate eget. Proin rhoncus rutrum ligula et sagittis. Donec vehicula turpis ipsum, et pharetra nunc convallis quis. Nam ultrices lectus et justo laoreet, at vehicula enim facilisis. Sed rutrum nulla in dui eleifend, sit amet tempor mi porta. Nullam commodo sapien nisi, quis venenatis eros eleifend sit amet. Pellentesque ut diam at mi accumsan luctus. Curabitur bibendum condimentum justo, in tincidunt nunc luctus in. Fusce pharetra lectus quis turpis blandit lobortis. Aenean porta velit elit, in finibus nulla auctor eget. Nunc non rhoncus tellus, vel molestie purus. Cras molestie porta augue scelerisque malesuada. In pretium quis enim vel fringilla. Nulla a quam urna. Curabitur eu nunc lacinia, venenatis ante nec, dignissim ante. ",
            },
            {
                'name': "UserInteractionAnalyzer",
                'message': "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec finibus pellentesque turpis, quis venenatis ligula tincidunt a. Sed eleifend diam dolor, ac suscipit lectus vulputate eget. Proin rhoncus rutrum ligula et sagittis. Donec vehicula turpis ipsum, et pharetra nunc convallis quis. Nam ultrices lectus et justo laoreet, at vehicula enim facilisis. Sed rutrum nulla in dui eleifend, sit amet tempor mi porta. Nullam commodo sapien nisi, quis venenatis eros eleifend sit amet. Pellentesque ut diam at mi accumsan luctus. Curabitur bibendum condimentum justo, in tincidunt nunc luctus in. Fusce pharetra lectus quis turpis blandit lobortis. Aenean porta velit elit, in finibus nulla auctor eget. Nunc non rhoncus tellus, vel molestie purus. Cras molestie porta augue scelerisque malesuada. In pretium quis enim vel fringilla. Nulla a quam urna. Curabitur eu nunc lacinia, venenatis ante nec, dignissim ante. ",

            },
            {
                'name': "MissingUserStoriesMatcher",
                'message': "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec finibus pellentesque turpis, quis venenatis ligula tincidunt a. Sed eleifend diam dolor, ac suscipit lectus vulputate eget. Proin rhoncus rutrum ligula et sagittis. Donec vehicula turpis ipsum, et pharetra nunc convallis quis. Nam ultrices lectus et justo laoreet, at vehicula enim facilisis. Sed rutrum nulla in dui eleifend, sit amet tempor mi porta. Nullam commodo sapien nisi, quis venenatis eros eleifend sit amet. Pellentesque ut diam at mi accumsan luctus. Curabitur bibendum condimentum justo, in tincidunt nunc luctus in. Fusce pharetra lectus quis turpis blandit lobortis. Aenean porta velit elit, in finibus nulla auctor eget. Nunc non rhoncus tellus, vel molestie purus. Cras molestie porta augue scelerisque malesuada. In pretium quis enim vel fringilla. Nulla a quam urna. Curabitur eu nunc lacinia, venenatis ante nec, dignissim ante. ",
            },
            {
                'name': "ArchitecturalPatternProposer",
                'message': "",
            },
        ],
    }


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

def getTestProjectStatus():
    return {
        'type': 'status',
        'data': {

                'system': [
                    {
                        'name': "ContainerDesigner",
                        'status': "OK",
                    },
                    {
                    'name': "UserInteractionAnalyzer",
                    'status': "OK",
                    },
                    {
                        'name': "MissingUserStoriesMatcher",
                        'status': "OK",
                    },
                    {
                        'name': "ArchitecturalPatternProposer",
                        'status': "NEXT",
                    },
                ],
                'containers': [
                    {
                        'name': "CONTAINER_A",
                        'id_doc': "",
                        'containerDescription': "NO",
                        'containerTechnologies': "NO",
                        'services': [
                            {
                                'name': "Service_1",
                                'endpoints': "NO",
                                'datastructures': "NO"
                            },
                            {
                                'name': "Service_2",
                                'endpoints': "NO",
                                'datastructures': "NO"
                            },
                        ]
                    },
                    {
                        'name': "CONTAINER_B",
                        'id_doc': "",
                        'containerDescription': "NO",
                        'containerTechnologies': "NO",
                        'services': []
                    },
                ],
            
        },
    }