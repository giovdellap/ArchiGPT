def getTestProjectSystem():
    return {
        'type': 'system',
        'data': [
            {
                'name': "UserStoriesAnalyzer",
                'message': "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec finibus pellentesque turpis, quis venenatis ligula tincidunt a. Sed eleifend diam dolor, ac suscipit lectus vulputate eget. Proin rhoncus rutrum ligula et sagittis. Donec vehicula turpis ipsum, et pharetra nunc convallis quis. Nam ultrices lectus et justo laoreet, at vehicula enim facilisis. Sed rutrum nulla in dui eleifend, sit amet tempor mi porta. Nullam commodo sapien nisi, quis venenatis eros eleifend sit amet. Pellentesque ut diam at mi accumsan luctus. Curabitur bibendum condimentum justo, in tincidunt nunc luctus in. Fusce pharetra lectus quis turpis blandit lobortis. Aenean porta velit elit, in finibus nulla auctor eget. Nunc non rhoncus tellus, vel molestie purus. Cras molestie porta augue scelerisque malesuada. In pretium quis enim vel fringilla. Nulla a quam urna. Curabitur eu nunc lacinia, venenatis ante nec, dignissim ante. ",
            },
            {
                'name': "ContainerDesign",
                'message': "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec finibus pellentesque turpis, quis venenatis ligula tincidunt a. Sed eleifend diam dolor, ac suscipit lectus vulputate eget. Proin rhoncus rutrum ligula et sagittis. Donec vehicula turpis ipsum, et pharetra nunc convallis quis. Nam ultrices lectus et justo laoreet, at vehicula enim facilisis. Sed rutrum nulla in dui eleifend, sit amet tempor mi porta. Nullam commodo sapien nisi, quis venenatis eros eleifend sit amet. Pellentesque ut diam at mi accumsan luctus. Curabitur bibendum condimentum justo, in tincidunt nunc luctus in. Fusce pharetra lectus quis turpis blandit lobortis. Aenean porta velit elit, in finibus nulla auctor eget. Nunc non rhoncus tellus, vel molestie purus. Cras molestie porta augue scelerisque malesuada. In pretium quis enim vel fringilla. Nulla a quam urna. Curabitur eu nunc lacinia, venenatis ante nec, dignissim ante. ",
            },
            {
                'name': "UserStoriesClassifier",
                'message': "",
            },
            {
                'name': "MissingUserStoriesMatcher",
                'message': "",
            },
            {
                'name': "ContainerProposer",
                'message': "",
            },
        ],
    }


def getTestProjectStatus():
    return {
        'type': 'status',
        'data': {

                'system': [
                    {
                        'name': "UserStoriesAnalyzer",
                        'status': "OK",
                    },
                    {
                        'name': "ContainerDesign",
                        'status': "OK",
                    },
                    {
                        'name': "UserStoriesClassifier",
                        'status': "NEXT",
                    },
                    {
                        'name': "MissingUserStoriesMatcher",
                        'status': "NO",
                    },
                    {
                        'name': "Container Proposer",
                        'status': "NO",
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