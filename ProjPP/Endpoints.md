# ENDPOINTS

## CREATE PROJECT
URL: /createProject
Request parameters:
- project name
Request files:
- User Stories
Description:
- Creates a new collection named "project name"
- Saves user stories inside it as a document

## DOCUMENT B GENERATION
URL: /generateDocumentB
Assistant: Containers List Generator
Request parameters:
- project name
Request files:
- source.zip
Description:
- Calls the assistant passing:
  - source.zip (code_interpreter)
  - User Stories (from db) (file_serach)
- Saves the result as DOCUMENT_B inside the collection "project name"
- For each container generates a new document named "container" inside the collection "project name"
- Return DOCUMENT_B to user 

## ENDPOINTS GENERATION
URL: /generateEndpoints
Assistant: Endpoints Solo Generator
Request parameters:
- project name
- container name
- service name
Request files:
- service.zip
Description:
- Calls the assistant passing:
  - service.zip
- Saves the result as a string named "endpoints_servicename" inside the document "container name" in the collection "project name"

## DATASTRUCTURES GENERATION
URL: /generateDataStructures
Assistant: DataStructures Solo Generator
Request parameters:
- project name
- container name
- service name
Request files:
- service.zip
Description:
- Gets "endpoints_servicename" from the DB (/projectname/containername)
- Calls the assistant passing:
  - service.zip
  - endpoints_servicename
- Saves the result as a string named "datastructures_servicename" inside the document "container name" in the collection "project name"

## SERVICE TECHNOLOGIES GENERATION
URL: /generateServiceTechnologies
Assistant: Service Technologies Generator
Request parameters:
- project name
- container name
- service name
Request files:
- service.zip
Description:
- Gets from the DB (/projectname/containername):
  - "endpoints_servicename"
  - "datastructures_servicename" 
- Calls the assistant passing:
  - service.zip
  - endpoints_servicename
  - datastructures_servicename
- Saves the result as a string named "technologies_servicename" inside the document "container name" in the collection "project name"

## SERVICE BEHAVIOUR GENERATOR
URL: /generateServiceBehaviour
Assistant: Service Behaviour Generator
Request parameters:
- project name
- container name
- service name
Description:
- Gets from the DB (/projectname/containername):
  - "endpoints_servicename"
  - "datastructures_servicename"
  - "technologies_servicename
- Gets from the DB (/projectname):
  - User Stories
- Calls the assistant passing:
  - endpoints_servicename
  - datastructures_servicename
  - technologies_servicename
  - User Stories
- Saves the result as a string named "behaviour_servicename" inside the document "container name" in the collection "project name"
- Assemblies the following documents into one document:
  - endpoints_servicename
  - datastructures_servicename
  - technologies_servicename
  - behaviour_servicename
- Saves it as "D_servicename" into the DB (/projectname/containername)

## CONTAINER SERVICES FULL DOCUMENT
URL: /assemblyServicesDocument
Request parameters:
- project name
- container name
Description:
- Gets from the DB (/projectname/containername) all the files "D_servicename"
- Assemblies all the texts into one text
- Saves the result as a string named "services" inside the document "container name" in the collection "project name"

## CONTAINER SPECIFICATIONS GENERATION
URL: /generateContainerSpecs
Assistant: Container Specifications Generator
Request parameters:
- project name
- container name
Request files:
- source.zip
Description:
- Gets from the DB (/projectname/containername) the file "services"
- Calls the assistant passing:
  - User Stories
  - services
  - source.zip
- Saves the result as a string named "specs" inside the document "container name" in the collection "project name"

## CONTAINER DESCRIPTION GENERATOR
URL: /generateContainerDescription
Assistant: Container Description Generator
Request parameters:
- project name
- container name
Description:
- Gets from the DB (/projectname/containername) the files:
  - services
  - specs
- Calls the assistant passing:
  - User Stories
  - services
  - specs
- Saves the result as a string named "description" inside the document "container name" in the collection "project name"

## CONTAINER SERVICES FULL DOCUMENT
URL: /assemblyServicesDocument
Request parameters:
- project name
- container name
Description:
- Gets from the DB (/projectname/containername) the files:
  - services
  - specs
  - documentation
- Assemblies all the texts into one text
- Saves the result as a string named "container" inside the document "container name" in the collection "project name"