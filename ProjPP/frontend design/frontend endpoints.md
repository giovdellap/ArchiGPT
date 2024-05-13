## HOMEPAGE

### Prendere lista sinistra

GET /projectList
Response:
{
    projects: [
        {
            name: string,
            id: string
        }
    ]
}

### Creare progetto

POST /project
Request: {
    name: string,
    editor: string
}

Response: 200, 500

## PROJECT PAGE

### Prende lista sinistra

GET /status/Project_id

Response (esempio del mockup):
{
    containersList: "OK",
    containers: [
        {
            name: "CONTAINER A",
            status: "OK",
            specifications: "OK",
            description: "OK",
            services: [
                {
                    name: "Service 1_A",
                    status: "OK",
                    features: [
                        {
                            name: "Type:Backend",
                            status: "OK"
                        },
                        {
                            name: "endpoints",
                            status: "OK"
                        },
                        {
                            name: "datastructures",
                            status: "OK"
                        },
                        {
                            name: "technologies",
                            status: "OK"
                        },
                        {
                            name: "behaviour",
                            status: "OK"
                        },
                    ]
                },
                {
                    name: "Service 2_A",
                    status: "OK",
                    features: [
                        {
                            name: "Type:Frontend",
                            status: "OK"
                        },
                        {
                            name: "behaviour",
                            status: "OK"
                        },
                    ]
                }
            ],
        },
        {
            name: "CONTAINER B",
            status: "PROGRESS",
            specifications: "NO",
            description: "NO",
            services: [
                {
                    name: "Service 1_B",
                    status: "PROGRESS",
                    features: [
                        {
                            name: "Type:Backend",
                            status: "OK"
                        },
                        {
                            name: "endpoints",
                            status: "OK"
                        },
                        {
                            name: "datastructures",
                            status: "OK"
                        },
                        {
                            name: "technologies",
                            status: "NO"
                        },
                        {
                            name: "behaviour",
                            status: "NO"
                        },
                    ]
                },
                {
                    name: "Service 2_A",
                    status: "PROGRESS",
                    features: [
                        {
                            name: "Type:Database",
                            status: "OK"
                        },
                        {
                            name: "behaviour",
                            status: "NO"
                        },
                    ]
                }
            ],
        }
    ]
}

Attenzione: i frontend hanno solo technologies e behaviour, i database solo technologies


### PULSANTE GENERATE
 Fa la richiesta specifica al backend, passando i file che servono
 Fare riferimento a postman per queste richieste