# ProjPP Documentation Generation CustomGPT

## Custom GPTs

### Document A

#### User Stories Analyzer

Version 1

### Document B

#### Containers List Generator

Version: 1

Input: 
- Source code

Output:
- Document B

### Document C

#### Container Description Generator

Version: 1

Prompt: 
CONTAINER NAME: container  

Input: 
- User Stories
- Document B
- Document D unified
- Document C (Specifications)

Output:
- Document C
    - Description

#### Container Specification Generator

Version: 1

Input: 
- Project source code
- User Stories
- Document B
- Document D for all the services within the container

Output:
- Document C
   - Specifications


### DOCUMENT D (Service description)

#### Service Technologies Generator

Version: 2

User Prompt:  
CONTAINER NAME: container  
SERVICE NAME: service  

Input:
- Service Source Code
- User Stories
- Document B
- Document D (endpoints/datastructures)

Output:
- Document C
    - Technologies

#### Service Behaviour Generator

Version: 2

User Prompt:  
CONTAINER NAME: container  
SERVICE NAME: service  

Input:
- User Stories
- Document B
- Document D (endpoints/datastructures)
- Document D (technologies)

Output:
- Document C
    - Behaviour


#### Unified Endpoints Generator

Version: 2
 
Input:
- User Stories
- Single Source files

Output:
- Document D
    - Endpoints
    - Data Structures


### Endpoints Solo Generator

Version: 2

Input:
- User Stories
- Single Source files

Output:
- Document D
    - Endpoints


### DataStructures Solo Generator

Version: 2

Input:
- User Stories
- Single Source files
- Document D (endpoints)

Output:
- Document D
    - Data Structures