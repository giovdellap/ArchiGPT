# ArchiGPT

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


## Progress Chart

| Phase | Assistant | api_handler | be content | be db | be logic | fe |
| ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| System_1 | OK | OK | OK | OK | OK | OK |
| System_2 | OK | OK | OK | OK | OK | OK |
| System_3 | NO | OK | OK | NO | NO | OK |
| System_4 | NO | OK | OK | OK | OK | OK |
| Container_1 | OK | OK | OK | OK | OK | OK |
| Container_2 | IN PROGRESS | OK | OK | OK | OK | OK |
| Container_3 | IN PROGRESS | OK | OK | NO | NO | NO |




## Assistants

### System_1
#### Container Designer 

Propone i container con una breve descrizione per ognuno

### System_2
#### User Interaction Analyzer

Propone un metodo di interazione con l'utente e eventualmente ritorna un container in più

### System_3
#### Missing User Stories Matcher

Controlla le User Stories orfane e le assegna ai container esistenti o ne propone di nuovi

### System_4
#### Architectural Pattern Proposer

Propone pattern architetturali da applicare

### Container_4
#### Container Purpose Generator

Descrive ogni container dal punto di vista