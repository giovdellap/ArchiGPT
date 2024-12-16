# SYSTEM DESCRIPTION:

Teamify is a versatile software solution designed to simplify personal task management and promoved group collaboration.
With intuitive task management, shared events, real-time chat, survey creation, and more.
Teamify empowers both individuals and teams to work efficiently and effectively.

Main Goals:
- Task Management
    Create and manage personal and shared tasks
- Team Management
    Create teams and invite members to join
- Real-time notifications
    Save time with real-time notifications
- Real-time chat
    Chat in real-time with your colleagues

# USER STORIES:

1) As a user, I want to create an account, so that I can use the application.
2) As a user, I want to login into the application, so that I can have access to my personal account within the application.
3) As a user, I want to logout from the application, so that I can leave the system without keeping my access credentials into the system.
4) As a user, I want to visualize my personal information, so that I can check whether they are correct or not.
5) As a user, I want to modify my personal information, so that I can change my password or whatever other information that is not correct at any time.
6) As a user, I want to reset my password, so that I can login into the system even if I forget it.
7) As a user, I want to delete my account, so that my personal data will no longer be saved into the system.
8) As a user, I want to have access to the notification section, so that I can see all the notifications that have been sent to me.
9) As a user, I want to visualize my personal agenda, so that I can check at any time which are my personal tasks.
10) As a team member, I want to visualize the agenda that is shared between me and a specific team which I belong to, so that I can check at any time which are the events shared with that team.
11) As a user, I want to create a personal task, so that I can better organize my personal life.
12) As a user, I want to modify a personal task, so that I can adjust some incorrect things that I have specified before.
13) As a user, I want to delete a personal task, so that I can empty a time slot of my personal agenda.
14) As a user, I want to check a personal task for completion, so that I can see which are the remaining personal tasks to do and which are those that I have already completed.
15) As a user, I want to create a team, so that I can have a shared agenda with my colleagues.
16) As a user, I want to join an existing team, so that I can receive notifications about events from my colleagues.
17) As a user, I want to visualize the list of all the teams I have joined so far, so that I can see all their details.
18) As a team member, I want to leave a team which I belong to, so that I will receive no more notifications from people that are no longer my colleagues.
19) As a team administrator, I want to select other team members to be administrators as well, so that I can more easily manage the team.
20) As a team administrator, I want to select other team administrators not to be administrators anymore, so that I can more easily manage the team.
21) As a team member, I want to send messages in a group chat, so that I can easily communicate with all the other team members.
22) As a team administrator, I want to create a poll, so that I can know the opinions of the other members related to a specific topic.
23) As a team member, I want to see the results of a previously created poll, so that I can know the opinions of the other members related to a specific topic.
24) As a team member, I want to vote for a poll, so that I can express my opinion with respect to a specific topic.
25) As a team administrator, I want to edit team's information, so that I can change whatever information that is not correct at any time.
26) As a team administrator, I want to delete a team, so that I can remove a team that is no longer necessary.
27) As a team administrator, I want to create an event, so that I can share a task with the colleagues belonging to a specific team which I am managing.
28) As a user, I want to accept or refuse the invitation to participate at an event, so that I can communicate my decision to the team administrator.
29) As a team administrator, I want to modify an event, so that I can adjust some incorrect things that I have specified before.
30) As a team administrator, I want to delete an event, so that I can empty a time slot of the entire team’s shared agenda.
31) As a team administrator, I want to check an event for completion, so that I can see which are the remaining shared tasks to do and which are those that we have already completed.


# CONTAINERS:

## CONTAINER NAME: User_Accounts

### DESCRIPTION: 
Manages user account creation, authentication, session management, and personal information updates.

### USER STORIES:
1) As a user, I want to create an account, so that I can use the application.
2) As a user, I want to login into the application, so that I can have access to my personal account within the application.
3) As a user, I want to logout from the application, so that I can leave the system without keeping my access credentials into the system.
4) As a user, I want to visualize my personal information, so that I can check whether they are correct or not.
5) As a user, I want to modify my personal information, so that I can change my password or whatever other information that is not correct at any time.
6) As a user, I want to reset my password, so that I can login into the system even if I forget it.
7) As a user, I want to delete my account, so that my personal data will no longer be saved into the system.

### PORTS: 
10000:10099

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER NAME: Notifications

### DESCRIPTION: 
Handles sending and managing real-time notifications for users.

### USER STORIES:
8) As a user, I want to have access to the notification section, so that I can see all the notifications that have been sent to me.

### PORTS: 
10100:10149

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER NAME: Personal_Tasks

### DESCRIPTION: 
Manages creation, modification, deletion, and completion status tracking of personal tasks.

### USER STORIES:
9) As a user, I want to visualize my personal agenda, so that I can check at any time which are my personal tasks.
11) As a user, I want to create a personal task, so that I can better organize my personal life.
12) As a user, I want to modify a personal task, so that I can adjust some incorrect things that I have specified before.
13) As a user, I want to delete a personal task, so that I can empty a time slot of my personal agenda.
14) As a user, I want to check a personal task for completion, so that I can see which are the remaining personal tasks to do and which are those that I have already completed.

### PORTS: 
10150:10199

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER NAME: Teams_Management

### DESCRIPTION: 
Manages team-related functionalities including creating teams, managing team memberships, designating administrators, and editing team information.

### USER STORIES:
15) As a user, I want to create a team, so that I can have a shared agenda with my colleagues.
16) As a user, I want to join an existing team, so that I can receive notifications about events from my colleagues.
17) As a user, I want to visualize the list of all the teams I have joined so far, so that I can see all their details.
18) As a team member, I want to leave a team which I belong to, so that I will receive no more notifications from people that are no longer my colleagues.
19) As a team administrator, I want to select other team members to be administrators as well, so that I can more easily manage the team.
20) As a team administrator, I want to select other team administrators not to be administrators anymore, so that I can more easily manage the team.
25) As a team administrator, I want to edit team's information, so that I can change whatever information that is not correct at any time.
26) As a team administrator, I want to delete a team, so that I can remove a team that is no longer necessary.

### PORTS: 
10200:10299

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER_NAME: Teamify_Chat

### DESCRIPTION: 
Handles real-time communication in group chats for collaborative discussions within teams.

### USER STORIES:
21) As a team member, I want to send messages in a group chat, so that I can easily communicate with all the other team members.

### PORTS: 10300:10349

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER_NAME: Team_Events

### DESCRIPTION: 
Manages team events, including creation, modification, confirmation of attendance, deletion, completion checking, and shared task management for events.

### USER STORIES:
10) As a team member, I want to visualize the agenda that is shared between me and a specific team which I belong to, so that I can check at any time which are the events shared with that team.
27) As a team administrator, I want to create an event, so that I can share a task with the colleagues belonging to a specific team which I am managing.
28) As a user, I want to accept or refuse the invitation to participate at an event, so that I can communicate my decision to the team administrator.
29) As a team administrator, I want to modify an event, so that I can adjust some incorrect things that I have specified before.
30) As a team administrator, I want to delete an event, so that I can empty a time slot of the entire team's shared agenda.
31) As a team administrator, I want to check an event for completion, so that I can see which are the remaining shared tasks to do and which are those that we have already completed.

### PORTS: 
10350:10399

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER_NAME: Surveys_Polls

### DESCRIPTION: 
Manages creation, voting, and result sharing of polls to gather opinions from team members on specific topics.

### USER STORIES:
22) As a team administrator, I want to create a poll, so that I can know the opinions of the other members related to a specific topic.
23) As a team member, I want to see the results of a previously created poll, so that I can know the opinions of the other members related to a specific topic.
24) As a team member, I want to vote for a poll, so that I can express my opinion with respect to a specific topic.

### PORTS: 
10400:10449

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER NAME: Teamify_Frontend

### DESCRIPTION: 
Hosts the web interface for user interactions, serving the frontend of the Teamify application.

### PORTS: 
10500:10549

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 