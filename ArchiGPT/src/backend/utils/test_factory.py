def getTestProjectSystem():
    return {
        'type': 'system',
        'data': [
            {
                'name': "UserStoriesAnalyzer",
                'message': "Test",
            },
            {
                'name': "ContainerDesign",
                'message': "Test",
            },
            {
                'name': "UserStoriesClassifier",
                'message': "Test",
            },
            {
                'name': "MissingUserStoriesMatcher",
                'message': "Test",
            },
            {
                'name': "Container Proposer",
                'message': "Test",
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
                        'status': "NO",
                    },
                    {
                        'name': "ContainerDesign",
                        'status': "NO",
                    },
                    {
                        'name': "UserStoriesClassifier",
                        'status': "NO",
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