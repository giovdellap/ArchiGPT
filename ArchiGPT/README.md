# ArchiGPT

TO START THE SYSTEM:

1) Add a .env file in the following directory: ArchiGPT/src/api_handler
    content of the .env file: OPENAI_API_KEY=<YOUR KEY>>

2)from the repo directory execute:
cd ArchiGPT/src
docker compose up

3)If the frontend service doesn't start:
    1) delete containers and images
    2) from the repo directory:
        cd ArchiGPT/src/frontend
        npm i
        cd ..
        docker compose up

4) frontend is available at localhost:3000
    mongo-express is available at localhost:8081
    mongo express credentials:
    user: admin
    password: pass

5) User Stories to test the system are available in /Test Dataset


ASSISTANTS INSTRUCTIONS:
Assistant instructions are located here:
ArchiGPT/src/api_handler/resources


## Assistant Chart

| Type | Phase | Assistant | Assistant ID |
| ------- | ------- | ------- | ------- |
| System | Container Design | ContainerDesigner | System_1 | 
| System | User Interaction Analysis | UserInteractionAnalyzer | System_2 |
| System | Match Missing User Stories | Missing User Stories Matcher | System_3 |
| System | Architectural Patterns | ArchitecturalPatternProposer | System_4 |
| Container | Container Description | ContainerDescriptionGenerator | Container_1 |
| Container | Container Specifications | ContainerSpecificationGenerator | Container_2 |
| Container | MicroServices | ServiceListGenerator | Container_3 |
| Service | Service Specifications | ServiceSpecificationGenerator | Service_1 |
| Service | Service Endpoints | ServiceEndpointGenerator | Service_2 |


## Progress Chart

| Phase | Assistant | api_handler | be content | be db | be logic | fe |
| ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| System_1 | OK | OK | OK | OK | OK | OK |
| System_2 | OK | OK | OK | OK | OK | OK |
| System_3 | OK | OK | OK | OK | OK | OK |
| Container_1 | OK | OK | OK | OK | OK | OK |
| Container_2 | OK | OK | OK | OK | OK | OK |
| Container_3 | OK | OK | OK | OK | OK | OK |
| Util_1 | OK | OK | OK | OK | OK | OK |
| Util_2 | OK | OK | OK | OK | OK | OK |
| Util_3 | OK | OK | OK | OK | OK | OK |
| Service_1 | OK | OK | OK | OK | OK | OK |
| Service_2 | OK | OK | OK | OK | OK | OK |




## Assistants

### System_1
#### Container Designer 

Propone i container con una breve descrizione per ognuno

### System_2
#### User Interaction Analyzer

Propone un metodo di interazione con l'utente e eventualmente ritorna un container in più

### System_3
#### Architectural Pattern Proposer

Propone pattern architetturali da applicare

### Container_1
#### Container Purpose Generator

Descrive ogni container dal punto di vista

### Container_2
#### Container Purpose Generator

Descrive ogni container dal punto di vista

### Container_3
#### Container Purpose Generator

Descrive ogni container dal punto di vista

### Service_1
#### Container Purpose Generator

Descrive ogni container dal punto di vista

### Service_2
#### Container Purpose Generator

Descrive ogni container dal punto di vista
