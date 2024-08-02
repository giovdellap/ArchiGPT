# SYSTEM DESCRIPTION:

A system ideally commissioned by the European Union. It empowers citizens from all EU member
nations to actively participate and to vote in future referendums.
SYSTEM PROPERTIES
1. European Referendum proposal
2. European-level Consensus
3. Submission of a Referendum to Citizens
4. National-level Consensus
5. National-level Voting
6. Result of Referendum


# USER STORIES:

1. As a user, I want to select the citizen or the national representative role, so that I can enter and navigate in the page hosted by that nation.
2. As a citizen, I want to register myself in the page hosted by the nation related to my legal citizenship, so that I can authenticate myself in the future
3. As a citizen, I want to authenticate myself in the the page hosted by the nation related to my legal citizenship, so that I can access the national service
4. As a nation representative, I want to authenticate myself in the context of my nation, so that I can manage the national service
5. As a citizen, I want to have a personal area displaying the possible services, so that I can choose a service 
6. As a nation representative, I want to keep track of the number of supporting citizens in the context of a certain referendum idea, so that I can decide if declare or not the referendum, on the base of a certain threshold 
7. As a citizen, I want to see the number of supporting citizens in the context of a certain referendum idea, so that I can be informed about the evolution of the referendum idea support
8. As a citizen, I want to receive a notification when a supported national referendum idea has reached the 1% threshold, so that I can know it has been accepted
9. As a nation representative, I want to declare a new national referendum, constituted by one question with "Yes" or "No" answer, so that I can obtain votes from citizens of my nation
10. As a nation representative, I want to propose an idea for an European referendum to other European nations, so that it can be proposed to all European citizens
11. As a nation representative, I want to declare a new accepted European referendum, constituted by one question with "Yes" or "No" answer, so that I can obtain votes from citizens of my nation
12. As a citizen, I want to see the list of all referendums declared by my nation, distinguished between national ones and European ones, so that I can vote
13. As a nation representative, I want to notify the citizens of my nation about a new declared referendum, so that I can inform them
14. As a citizen, I want to be notified by my nation about a new declared referendum, so that I can be up to date
15. As a citizen, I want to vote in a referendum declared by my nation, so that I can express my opinion
16. As a nation representative, I want to see the results of a declared referendum, so that I can take relative measures
17. As a citizen, I want to see the results of a referendum declared by my nation, so that I can know the outcome
18. As a nation representative, I want to see all current European referendum ideas, so that I can know other nations opinions
19. As a nation representative, I want to support or reject an idea of European referendum, so that the idea can be effectively proposed or not to European citizens
20. As a nation representative, I want to see the final result of the supporting process related to an European referendum idea, so that I can know it has been accepted
21. As a nation representative, I want to see the final result of the voting process related to an European referendum, so that I can apply it if it has been approved by European citizens

# CONTAINERS:

## CONTAINER NAME: Authentication

### DESCRIPTION: 
Manages registration, authentication, and security protocols for both citizens and national representatives.

### USER STORIES:
- 2) As a citizen, I want to register myself in the page hosted by the nation related to my legal citizenship, so that I can authenticate myself in the future
- 3) As a citizen, I want to authenticate myself on the page hosted by the nation related to my legal citizenship, so that I can access the national service
- 4) As a nation representative, I want to authenticate myself in the context of my nation, so that I can manage the national service

### PORTS: 5000:5100

### DESCRIPTION:
The Authentication container is essential for maintaining the security and integrity of the voting and engagement processes within the European Union referendum system. Its primary functions include managing user registration, enabling login mechanisms, and ensuring the security protocols are adhered to for both citizens and national representatives. For citizens, it facilitates the registration and authentication based on their legal citizenship, allowing personalized and secure access to referendum-related services. For national representatives, it provides authentication capabilities to manage and administer national services pertinent to the referendum process.

### PERSISTANCE EVALUATION
The Authentication container requires data persistence to manage user credentials and session information effectively. This data includes:
- User credentials (e.g., usernames, password hashes) to support registration and authentication processes for citizens and national representatives.
- Session details or authentication tokens which enables the container to manage login sessions, ensuring that users remain authenticated while interacting with the system until they decide to log out.

### EXTERNAL SERVICES CONNECTIONS
The Authentication container, based on its core functions of registration, login, and security management, does not require direct connections to external services for its primary tasks. However, it might have to integrate with national databases or identity verification services provided by each EU nation to validate citizenship and personal details during registration and authentication. This would ensure that the authentication process aligns with legal and security standards specific to each nation within the EU.

## CONTAINER NAME: User_Interface

### DESCRIPTION: 
Provides the interaction interface for choosing roles and displaying personalized areas.

### USER STORIES:
- 1) As a user, I want to select the citizen or the national representative role, so that I can enter and navigate in the page hosted by that nation.
- 5) As a citizen, I want to have a personal area displaying the possible services, so that I can choose a service

### PORTS: 5200:5300

### DESCRIPTION:
The User_Interface container is designed to provide the primary interaction interface for users of the system, enabling them to choose their roles—either as citizens or national representatives—and access personalized areas within the system. This container facilitates the entry into and navigation of the respective national pages, ensuring a tailored experience where users can select from various available services meant for their specific roles. This setup aims to streamline user interactions, making the system intuitive and effective in displaying context-relevant features and options based on the user's role and permissions.

### PERSISTANCE EVALUATION
The User_Interface container is primarily focused on interaction and user engagement rather than data storage or processing. Its function to facilitate role selection and display personalized areas suggests that it is stateless and mainly serves content dynamically, fetching data from other services rather than persisting it. However, some minimal session data, such as user role and state, might be temporarily held in local storage or in-memory for the continuity of the user session across different interactions.

### EXTERNAL SERVICES CONNECTIONS
The User_Interface container may need to connect with external services to fetch user-specific data, such as available services and permissions. This could be facilitated through interactions with internal containers such as Authentication for user identity validation, Referendum_Management for accessing available referendum options, or Notification_System for displaying relevant notifications. There is no indication that this container directly connects to external third-party services outside of the described system architecture.

## CONTAINER NAME: Referendum_Management

### DESCRIPTION: 
Handles the creation, management, and tracking of national and European referendums.

### USER STORIES:
- 6) As a nation representative, I want to keep track of the number of supporting citizens in the context of a certain referendum idea, so that I can decide if declare or not the referendum, based on a certain threshold
- 7) As a citizen, I want to see the number of supporting citizens in the context of a certain referendum idea, so that I can be informed about the evolution of the referendum idea support
- 9) As a nation representative, I want to declare a new national referendum, constituted by one question with "Yes" or "No" answer, so that I can obtain votes from citizens of my nation
- 10) As a nation representative, I want to propose an idea for an European referendum to other European nations, so that it can be proposed to all European citizens
- 11) As a nation representative, I want to declare a new accepted European referendum, constituted by one question with "Yes" or "No" answer, so that I can obtain votes from citizens of my nation

### PORTS: 5400:5500

### DESCRIPTION:
The Referendum_Management container is central to the operation of managing both national and European referendums. It oversees the entire lifecycle of a referendum from idea propagation to execution. This includes tracking the support for different referendum ideas, declaring new referendums, and coordinating with other European nations to propose and declare European-wide referendums. This container serves nation representatives by providing tools to monitor the buildup of support for different initiatives and make informed decisions about when to move forward with a referendum based on predefined thresholds. Additionally, it allows for a high level of transparency and engagement by enabling citizens to check the progress of referendum ideas. This central role in facilitating active participation and democratic processes is crucial for the operational success of the system in delivering an effective and participative referendum mechanism.

### PERSISTANCE EVALUATION
The Referendum_Management container needs to persist data to handle the lifecycle of referendums effectively. It must store various types of data including:
- Details and statuses of proposal ideas for national and European referendums.
- Counts of supporting citizens for each referendum idea to monitor thresholds and determine when an idea is viable to be declared as a referendum.
- Records of declared referendums, both at the national and European level.
These data sets are essential to track the progress of referendum ideas, support counts, and the status of current referendums. This persistent storage supports the administrative workflow, allowing nation representatives to make decisions based on concrete data about citizen engagement and support.

### EXTERNAL SERVICES CONNECTIONS
The Referendum_Management container might need to connect to external services for functions such as:
- Syncing data regarding European-level referendum ideas with other EU nations to ensure consistent and updated information across all member states.
This would typically involve making use of APIs provided by each EU nation or a centralized database designed to maintain the integrity and consistency of referendum data across the European Union. This ensures that the data about the progress of referendum ideas and the declaration of new referendums are harmonized, supporting the system's goal of European-level consensus and participation.

## CONTAINER NAME: Notification_System

### DESCRIPTION: 
Manages notifications and alerts regarding threshold achievements and new referendums.

### USER STORIES:
- 8) As a citizen, I want to receive a notification when a supported national referendum idea has reached the 1% threshold, so that I can know it has been accepted
- 13) As a nation representative, I want to notify the citizens of my nation about a new declared referendum, so that I can inform them
- 14) As a citizen, I want to be notified by my nation about a new declared referendum, so that I can be up to date

### PORTS: 5600:5700

## CONTAINER NAME: Voting_System

### DESCRIPTION: Handles the voting processes for both national and European referendums.

### USER STORIES:
- 12) As a citizen, I want to see the list of all referendums declared by my nation, distinguished between national ones and European ones, so that I can vote
- 15) As a citizen, I want to vote in a referendum declared by my nation, so that I can express my opinion

### PORTS: 5800:5900

## CONTAINER NAME: Result_Processing

### DESCRIPTION: 
Processes and displays the results of referendums to both citizens and national representatives.

### USER STORIES:
- 16) As a nation representative, I want to see the results of a declared referendum, so that I can take relative measures
- 17) As a citizen, I want to see the results of a referendum declared by my nation, so that I can know the outcome
- 20) As a nation representative, I want to see the final result of the supporting process related to an European referendum idea, so that I can know it has been accepted
- 21) As a nation representative, I want to see the final result of the voting process related to an European referendum, so that I can apply it if it has been approved by European citizens
### PORTS: 6000:6100

## CONTAINER NAME: European_Consensus

### DESCRIPTION: Manages the compilation and review of European referendum ideas and supports or rejects them based on consensus.

### USER STORIES:
- 18) As a nation representative, I want to see all current European referendum ideas, so that I can know other nations opinions
- 19) As a nation representative, I want to support or reject an idea of European referendum, so that the idea can be effectively proposed or not to European citizens

### PORTS: 6200:6300

