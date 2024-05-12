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
    containersList: OK
    containers: [
        {
            container_name: "CONTAINER A",
            status: OK,
            specifications: OK,
            description: OK,
            services: [
                {
                    name: Service 1_A,
                    status: OK,
                    type: Backend,
                    endpoints: OK,
                    datastructures: OK,
                    technologies: OK,
                    behaviour: OK
                },
                {
                    name: Service 2_A,
                    status: OK,
                    type: Frontend,
                    technologies: OK,
                    behaviour: OK
                }
            ],
        },
                {
            container_name: "CONTAINER B",
            status: PROGRESS,
            specifications: NO,
            description: NO,
            services: [
                {
                    name: Service 1_B,
                    status: PROGRESS,
                    type: Backend,
                    endpoints: OK,
                    datastructures: OK,
                    technologies: NO,
                    behaviour: NO
                },
                {
                    name: Service 2_A,
                    type: Database,
                    technologies: no
                }
            ],
        }
    ]
}

Attenzione: i frontend hanno solo technologies e behaviour, i database solo technologies


### PULSANTE GENERATE
 Fa la richiesta specifica al backend, passando i file che servono
 Fare riferimento a postman per queste richieste