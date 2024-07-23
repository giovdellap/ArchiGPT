# SYSTEM DESCRIPTION:

OneSport is dedicated to offering up-to-date and pertinent information on a wide range of sports and athletic events. 
Our primary aim is to keep fans and enthusiasts well-informed and engaged. 
The project strives to serve as a one-stop destination for comprehensive sports news, event updates, and a convenient platform for purchasing tickets to various sporting events.

# USER STORIES:

1) As a User, I want to be able to signup in order to have an account 
2) As a User, I want to be able to login
3) As a User, I want to logout from the account by clicking on the Navbar
4) As a User, I want to be able to have access to my profile settings
5) As a User, I want to be able to delete my account by clicking on a button from the profile settings page
6) As a User, I want to be able to modify my email and password in order to change my credentials
7) As a User, I want to see all recent news
8) As a User, I want to filter Italian news by selecting from the menu of available countries 
9) As a User, I want to add to my favourite news all the news I'm interested in, in order to read them later
10) As a User, I want to delete from my favourite news all the news that I'm no longer interested 
11) As a User, I want to see available tickets for upcoming sporting events
12) As a User, I want to filter Italian tickets by selecting from the menu of available countries 
13) As a User, I want to add to my collection all the tickets I'm interested in, in order to purchase them later
14) As a User, I want to delete from my collection all the tickets that I'm no longer interested 
15) As a User, I want to be able to delete all the news and tickets in my favourite list
16) As a User, I want to send a friend request to another user, in order to add him to my friend list
17) As a User, I want to check if someone has sent me a friend request, in order to accept or delete it
18) As a User, I want to delete a friend from my friend list


# CONTAINERS:

## CONTAINER_NAME: OneSport

### DESCRIPTION: 
Manages and displays user account, news, tickets and social features.

### USER STORIES:
1) As a User, I want to be able to signup in order to have an account 
2) As a User, I want to be able to login
3) As a User, I want to logout from the account by clicking on the Navbar
4) As a User, I want to be able to have access to my profile settings
5) As a User, I want to be able to delete my account by clicking on a button from the profile settings page
6) As a User, I want to be able to modify my email and password in order to change my credentials
7) As a User, I want to see all recent news
8) As a User, I want to filter Italian news by selecting from the menu of available countries 
9) As a User, I want to add to my favourite news all the news I'm interested in, in order to read them later
10) As a User, I want to delete from my favourite news all the news that I'm no longer interested 
11) As a User, I want to see available tickets for upcoming sporting events
12) As a User, I want to filter Italian tickets by selecting from the menu of available countries 
13) As a User, I want to add to my collection all the tickets I'm interested in, in order to purchase them later
14) As a User, I want to delete from my collection all the tickets that I'm no longer interested 
15) As a User, I want to be able to delete all the news and tickets in my favourite list
16) As a User, I want to send a friend request to another user, in order to add him to my friend list
17) As a User, I want to check if someone has sent me a friend request, in order to accept or delete it
18) As a User, I want to delete a friend from my friend list

### PORTS: 
3000:5432

### DESCRIPTION:
This only OneSport container is designed to handle all core functionalities related to user accounts, news, tickets and social features within the OneSport platform.

### PERSISTANCE EVALUATION
The container handles persistent and accessible storage for user, news, tickets and social features data. 

### EXTERNAL SERVICES CONNECTIONS
The container handles connection to external services that provide real-time access to news and ticket information.

### MICROSERVICES:

#### MICROSERVICE: frontend
- TYPE: frontend
- DESCRIPTION: The frontend microservice serves as the user interface for the OneSport platform. It is designed to provide a seamless and interactive experience, allowing users to access services like viewing sports news, buying tickets, and managing their user accounts. This container integrates the platform's functionalities into a cohesive graphical interface that users interact with. It is the entry point for carrying out all user interactions described in the user stories and ensures that they are intuitive and efficient, enhancing the overall user experience on the platform.
- PORTS: 3000

#### MICROSERVICE: news
- TYPE: backend
- DESCRIPTION: The news microservice is responsible for managing all aspects related to the display and personalization of news content for users of the OneSport platform. This includes presenting recent news updates, offering functionalities to filter news by specific regions such as Italian news, and allowing users to manage a personalized list of favorite news items that they can add to and delete from. Also supports the function to delete every news items from a user's favorites list.
- PORTS: 5000

#### MICROSERVICE: authentication
- TYPE: backend
- DESCRIPTION: The authentication microservice is designed to handle all core functionalities related to user accounts within the OneSport platform. This includes the processes of registering for a new account, logging into an existing account, and securely logging out. Additionally, it manages user profile settings, allowing users to access and modify their personal information such as updating email addresses and passwords. It also provides the functionality to delete a user account altogether. It is responsible for managing the user session through jwt tokens.
- PORTS: 5001

#### MICROSERVICE: tickets
- TYPE: backend
- DESCRIPTION: The tickets microservice is responsible for managing all aspects related to event tickets within the OneSport platform. It facilitates the display of available tickets for various sporting events, allowing users to browse and filter tickets based on region-specific criteria. The container enables users to add tickets to a personal collection for future purchase, and to manage (add or remove) these tickets as desired. This service is pivotal for users who plan to attend sports events and need an efficient way to handle their ticketing options.
- PORTS: 5002

#### MICROSERVICE: friends
- TYPE: backend
- DESCRIPTION: The friends microservice is designed to manage various aspects of social interaction within the OneSport platform. It allows users to send friend requests, manage incoming friend requests, and control their friend lists. The container handles the addition and removal of friends, helping users curate their social circle within the OneSport environment.
- PORTS: 5003

#### MICROSERVICE: postgres
- TYPE: database
- DESCRIPTION: The postgres microservice is responsible for storing: all user-related data including registration information, login credentials, and profile settings; details of news items and favorite lists; data regarding available tickets and user-specific collections; persisting social interaction data such as sent and received friend requests and their statuses (pending, accepted, declined), and maintaining a list of friends for each user. This microservice ensures data integrity and swift access to data in a secure and persistent storage environment.
- PORTS: 5432


