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
19) As a User, I want to find another user by email, in order to see his details
20) As a User, I want to see my collection of saved news 
21) As a User, I want to see my collection of saved tickets 
22) As a User, I want to see my friend list, in order to see their details
23) As a User, I want to be able to accept a friend request, in order to add the sender as a friend
24) As a User, I want to be able to delete a friend request, in order to refuse the friend request

# CONTAINERS:

## CONTAINER NAME: User_Management

### DESCRIPTION: 
Manages user account registration, login, logout, profile settings, account deletion, and updating user credentials.

### USER STORIES: 
1) As a User, I want to be able to signup in order to have an account
2) As a User, I want to be able to login
3) As a User, I want to logout from the account by clicking on the Navbar
4) As a User, I want to be able to have access to my profile settings
5) As a User, I want to be able to delete my account by clicking on a button from the profile settings page
6) As a User, I want to be able to modify my email and password in order to change my credentials
     
### PORTS: 
10000:10099

### DESCRIPTION: 
The User_Management container is designed to handle all core functionalities related to user accounts within the OneSport platform. This includes the processes of registering for a new account, logging into an existing account, and securely logging out. Additionally, it manages user profile settings, allowing users to access and modify their personal information such as updating email addresses and passwords. It also provides the functionality to delete a user account altogether. This container ensures that user account data is handled securely and efficiently, contributing to a seamless user experience on the OneSport platform.

### PERSISTANCE EVALUATION
The User_Management container is integral for handling user-specific data, which necessitates persistent storage. It must store data relevant to user registration, login credentials (e.g., usernames, passwords), profile settings, and any changes or deletions made to user accounts. This data must be securely handled and readily accessible for user authentication, account updates, or deletions, ensuring consistent and reliable user experiences.

### EXTERNAL SERVICES CONNECTIONS
The User_Management container, given its function related strictly to user account management, does not inherently require connecting to any external services for its core operations of account management and authentication. However, integrations with external email service providers for purposes such as confirmation emails or password resets may be applicable if such functionality is desired to enhance user communication and security measures.

### MICROSERVICES:

#### MICROSERVICE: account_management
- TYPE: backend
- DESCRIPTION: Handles all aspects of user interaction with their accounts such as signup, login, logout, modify email, modify password, and delete account. It interfaces with the database to store and retrieve user information securely and manages sessions to keep users logged in.
- PORTS: 10000

#### MICROSERVICE: profile_management
- TYPE: backend
- DESCRIPTION: Manages user profile settings allowing users to access and update their personal details such as email address and password. Includes functionality to handle account deletion requests and securely updates the database upon user-initiated changes.
- PORTS: 10010

#### MICROSERVICE: user_data_storage
- TYPE: database
- DESCRIPTION: Maintains all user-related data including registration information, login credentials, and profile settings in a secure and persistent storage environment. This microservice ensures data integrity and swift access to user data as needed for authentication and account management.
- PORTS: 10020

## CONTAINER NAME: News_Service

### DESCRIPTION:
Manages displaying of news, filtering by region, managing favorite news lists, and batch deletion of news items.

### USER STORIES: 
7) As a User, I want to see all recent news
8) As a User, I want to filter Italian news by selecting from the menu of available countries
9) As a User, I want to add to my favourite news all the news I'm interested in, in order to read them later
10) As a User, I want to delete from my favourite news all the news that I'm no longer interested
15) As a User, I want to be able to delete all the news and tickets in my favourite list

### PORTS: 
10100:10199

### DESCRIPTION: 
The News_Service container is responsible for managing all aspects related to the display and personalization of news content for users of the OneSport platform. This includes presenting recent news updates, offering functionalities to filter news by specific regions such as Italian news, and allowing users to manage a personalized list of favorite news items that they can add to and delete from. The container also supports the function to batch delete news items from a user's favorites list, ensuring a tailored and interactive user experience for consuming sports news.

### PERSISTANCE EVALUATION
The News_Service container requires persistent storage to manage and tailor the user's experience effectively. It needs to store details of news items and user-specific data such as favorite lists and filtered selections. Storing news involves retaining titles, content, metadata like publication date and region-specific tagging, while user-specific data includes the list of favorite news items to facilitate adding and removing operations as described in the user stories.

### EXTERNAL SERVICES CONNECTIONS
This container needs to connect to external news provider services to fetch up-to-date and region-specific news content. As it offers regional filtering (e.g., Italian news), it likely requires access to an API that provides comprehensive news data, categorized by regions or other relevant criteria, to keep the news content current and pertinent as per user demands and system description.

### MICROSERVICES:

#### MICROSERVICE: news_feed
- TYPE: backend
- DESCRIPTION: Handles the retrieval and display of all recent and regional specific news. This microservice interacts with external news provider APIs to fetch news articles, and it also handles the filtering logic to segregate news by region as per user preferences.
- PORTS: 10100

#### MICROSERVICE: favorites_manager
- TYPE: backend
- DESCRIPTION: Manages the user-specific favorite news list, including adding new items to the favorites as per user interest, and removing items from the list when the user no longer finds them relevant.
- PORTS: 10110

#### MICROSERVICE: batch_delete
- TYPE: backend
- DESCRIPTION: Provides the functionality to batch delete news items from a user's favorites list, supporting user stories that involve managing large-scale deletions to ease user management of stored content.
- PORTS: 10120

#### MICROSERVICE: news_storage
- TYPE: database
- DESCRIPTION: Stores the details of news items and user-specific data such as favorite lists and filtered selections. This includes saving titles, content, publication dates, region-specific tagging, and maintaining a list of favorite news items.
- PORTS: 10130

## CONTAINER NAME: Ticket_Service

### DESCRIPTION:
Manages ticket display, filtering by region, managing the collection of tickets, and deleting tickets from a user’s collection.

### USER STORIES:
11) As a User, I want to see available tickets for upcoming sporting events
12) As a User, I want to filter Italian tickets by selecting from the menu of available countries
13) As a User, I want to add to my collection all the tickets I'm interested in, in order to purchase them later
14) As a User, I want to delete from my collection all the tickets that I'm no longer interested

### PORTS: 
10200:10299

### DESCRIPTION: 
The Ticket_Service container is responsible for managing all aspects related to event tickets within the OneSport platform. It facilitates the display of available tickets for various sporting events, allowing users to browse and filter tickets based on region-specific criteria. The container enables users to add tickets to a personal collection for future purchase, and to manage (add or remove) these tickets as desired. This service is pivotal for users who plan to attend sports events and need an efficient way to handle their ticketing options, ensuring a smooth transaction from selection to purchase.

### PERSISTANCE EVALUATION
The Ticket_Service container needs to persistently store data regarding both the available tickets and user-specific collections of tickets. For the available tickets, data might include details such as event information, pricing, dates, and regional categorizations, which are essential for filtering and display based on user queries. Regarding user-specific collections, the container must store individual user preferences about the tickets they are interested in or plan to purchase. This data is crucial for allowing users to manage their collections, adding or deleting tickets as they revise their plans.

### EXTERNAL SERVICES CONNECTIONS
The Ticket_Service container may need to connect to external services that provide real-time access to ticketing information, especially if the sporting event tickets are managed through various external ticket providers or event management platforms. Integrating APIs from these services will enable the container to update and synchronize its ticket offerings with the latest availability and pricing updates. Furthermore, connections to payment gateways for handling transactions might also be necessary once tickets move from collection to the actual purchase phase.

### MICROSERVICES:

#### MICROSERVICE: ticket_display
- TYPE: backend
- DESCRIPTION: Manages the display and filter actions for sports event tickets. It provides endpoints for users to view and filter tickets by region and other criteria. This service interacts with the database to fetch the current available tickets based on the filters applied by users.
- PORTS: 10200

#### MICROSERVICE: ticket_management
- TYPE: backend
- DESCRIPTION: Handles the addition and removal of tickets from a user's personal collection. It provides functionality for users to manage their ticket collections, facilitating the future purchase of selected tickets. This service interacts with the database to update user-specific collections with actions like add or delete.
- PORTS: 10210

##### MICROSERVICE: ticket_storage
- TYPE: database
- DESCRIPTION: Responsible for storing data regarding available tickets and user-specific collections. It maintains details including event information, pricing, dates, regional categorizations for available tickets, and user-specific preference data for collecting and managing tickets.
- PORTS: 10220

## CONTAINER NAME: Social_Features

### DESCRIPTION:
Manages social interactions including sending friend requests, managing incoming requests, and managing friend lists.

### USER STORIES:
16) As a User, I want to send a friend request to another user, in order to add him to my friend list
17) As a User, I want to check if someone has sent me a friend request, in order to accept or delete it
18) As a User, I want to delete a friend from my friend list

### PORTS: 
10300:10399

### DESCRIPTION: 
The Social_Features container is designed to manage various aspects of social interaction within the OneSport platform. It allows users to send friend requests, manage incoming friend requests, and control their friend lists. This functionality not only enhances the user's social experience on the platform but also enables them to engage more personally with other sports enthusiasts. The container handles the addition and removal of friends, helping users curate their social circle within the OneSport environment.

### PERSISTANCE EVALUATION
The Social_Features container requires persistent storage to manage friend relationships effectively. It needs to store information about sent and received friend requests and the current status of these requests (e.g., pending, accepted, declined). Additionally, the container must maintain a list of friends for each user, which would include data such as user identifiers and relationship status. This data is essential for the container to fulfill its user stories related to managing social interactions and friend lists.

### EXTERNAL SERVICES CONNECTIONS
The Social_Features container does not inherently need to connect to any external services to fulfill its primary functionalities, related to managing friend requests and friend lists. Its operation is largely dependent on internal data and interactions with other internal services, such as User_Management, to retrieve user-specific information when necessary. No external APIs or third-party services are necessary for the primary operations of sending, accepting, or removing friends within the OneSport platform.

### MICROSERVICES: 

#### MICROSERVICE: friend_management
- TYPE: backend
- DESCRIPTION: Handles the core functionality of managing social interactions on the OneSport platform. This includes sending friend requests, managing incoming friend requests, and handling friend lists. The service provides endpoints to add a friend, accept or reject incoming friend requests, and delete friends from a user's list, ensuring that all user interactions align with the social features specified.
- PORTS: 10300

#### MICROSERVICE: social_storage
- TYPE: database
- DESCRIPTION: Responsible for persisting social interaction data such as sent and received friend requests and their statuses (pending, accepted, declined), and maintaining a curated list of friends for each user. This microservice ensures data integrity and handles queries to fetch friend lists and friendship statuses efficiently.
- PORTS: 10320

## CONTAINER NAME:  Frontend

### DESCRIPTION:
Provides the UI to access OneSport services and interact with the platform for seamless user experience and functionalities as described in the system. It is the entry point where the User Stories and overall system interaction comes to life.

### PORTS:  
10400:10499

### DESCRIPTION: 
The Frontend container serves as the user interface for the OneSport platform. It is designed to provide a seamless and interactive experience, allowing users to access services like viewing sports news, buying tickets, and managing their user accounts. This container integrates the platform's functionalities into a cohesive graphical interface that users interact with. It is the entry point for carrying out all user interactions described in the user stories and ensures that they are intuitive and efficient, enhancing the overall user experience on the platform.

### PERSISTANCE EVALUATION
The Frontend container does not require storage of persistent data, as its primary function is to serve as the user interface layer. This container presents data processed and managed by backend services and maintains no state that needs persistence beyond user session management through cache or temporary local/session storage for improved user experience.

### EXTERNAL SERVICES CONNECTIONS
The Frontend container requires connections to other internal microservices such as User_Management, News_Service, Ticket_Service, and Social_Features to request and retrieve data accordingly. These services provide APIs that the Frontend container calls to allow users to perform actions like login, view news, manage tickets, and social interactions. Although primarily dependent on internal services, it might also incorporate connections to external APIs for additional functionalities like maps, social media sharing, or to fetch external content which enhances the information provided within the platform.

### MICROSERVICES:

#### MICROSERVICE: web_ui
- TYPE: frontend
- DESCRIPTION: This microservice serves the graphical user interface of the OneSport platform. It provides the entry point where users interact with various services such as viewing sports news, buying tickets, managing user accounts, and engaging in social features. The web_ui integrates all user stories into a cohesive experience, ensuring accessibility and effective engagement with the content, fulfilling the role of making the platform intuitive and easy to use.
- PORTS: 10400

#### MICROSERVICE: session_management
- TYPE: other
- DESCRIPTION: This microservice handles temporary local and session storage for the Frontend container to manage user sessions efficiently. It ensures that user preferences and session data are maintained while the user is active on OneSport, enhancing the responsiveness and user experience without the need for persistent backend storage.
- PORTS: 10410