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
19) As a User, I want to find another user by email, in order to see his details
20) As a User, I want to see my collection of saved news 
21) As a User, I want to see my collection of saved tickets 
22) As a User, I want to see my friend list, in order to see their details
23) As a User, I want to be able to accept a friend request, in order to add the sender as a friend
24) As a User, I want to be able to delete a friend request, in order to refuse the friend request

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
- TECHNOLOGICAL SPECIFICATION: The frontend microservice utilizes ReactJS as its primary framework for building user interfaces, leveraging its component-based architecture to create a responsive and dynamic web application.
- SERVICE ARCHITECTURE: The architecture of the frontends microservice follows a component-based design pattern, allowing for reusable UI components that are modular and isolated. To interact with other microservices, it offers several functions that call microservice APIs, handling both requests and responses.
- PAGES:

	| Name | Description | Related Microservice |
	| ---- | ----------- | -------------------- |
	| Home.js | Displays and manages news and user collection news | news |
	| Login.js | Manages user authentication and login process | authentication |
	| Profile.js | Displays and manages user profile information | authentication |
	| Signup.js | Handles new user registration | authentication |
	| Tickets.js | Displays and manages tickets and user collection tickets | tickets |
	| Friends.js | Displays and manages friends and friend requests | friends |

#### MICROSERVICE: news
- TYPE: backend
- DESCRIPTION: The news microservice is responsible for managing all aspects related to the display and personalization of news content for users of the OneSport platform. This includes presenting recent news updates, offering functionalities to filter news by specific regions such as Italian news, and allowing users to manage a personalized list of favorite news items that they can add to and delete from. Also supports the function to delete every news items from a user's favorites list.
- PORTS: 5000
- TECHNOLOGICAL SPECIFICATION: The news microservice uses Python as its primary programming language. For building a web application with RESTful APIs, the microservice leverages the **Flask** micro-framework. In order to achieve his aims, it uses the following libraries and technologies:
	- **flask_cors**: Extension for handling Cross-Origin Resource Sharing (CORS) in Flask.
	- **flask_sqlalchemy**: Provides ORM support and database integration.
	- **psycopg2**: PostgreSQL adapter for Python, enabling database interaction.
- SERVICE ARCHITECTURE: The news microservice adopts a layered architecture pattern, which helps in separating concerns and providing maintainability and scalability features:
	- **Controllers Layer**: The controllers within this layer handle the business logic associated with news management. They interact with the models and services to process the requests and generate appropriate responses.
	- **Models Layer**: Defines models for news. These models define the schema and represent how data will be stored in PostgreSQL.
	- **Routes Layer**: This layer defines the HTTP endpoints. It handles incoming HTTP requests and forwards them to the appropriate controllers.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| GET | /news | Returns all latest sports news, using an external API, also filters can be applied | 7,8 |
	| GET | /news/{user_id} | Returns all news saved by user with id = {user_id} | 20 |
	| POST | /news/create | Save a news for a user, adding also the linked information and return the json of the news created | 9 |
	| DELETE | /news/{news_id} | Deletes the news with id = {news_id} | 10 |
	| DELETE | /news/{user_id}/all | Deletes all the news saved by user with id = {user_id} | 15 |

- DB STRUCTURE: 

	**_news_**:	 | **_id_** | title | author | urlToImage | published_at | user_id | url |

#### MICROSERVICE: authentication
- TYPE: backend
- DESCRIPTION: The authentication microservice is designed to handle all core functionalities related to user accounts within the OneSport platform. This includes the processes of registering for a new account, logging into an existing account, and securely logging out. Additionally, it manages user profile settings, allowing users to access and modify their personal information such as updating email addresses and passwords. It also provides the functionality to delete a user account altogether. It is responsible for managing the user session through jwt tokens.
- PORTS: 5001
- TECHNOLOGICAL SPECIFICATION: The authentication microservice uses Python as its primary programming language. For building a web application with RESTful APIs, the microservice leverages the **Flask** micro-framework. In order to achieve his aims, it uses the following libraries and technologies:
	- **flask_cors**: Extension for handling Cross-Origin Resource Sharing (CORS) in Flask.
	- **flask_sqlalchemy**: Provides ORM support and database integration.
	- **flask_login**: Manages user sessions and authentication in Flask applications.
	- **psycopg2**: PostgreSQL adapter for Python, enabling database interaction.
	- **flask_jwt_extended**: For generating and validating JSON Web Token (JWT).
- SERVICE ARCHITECTURE: The authentication microservice adopts a layered architecture pattern, which helps in separating concerns and providing maintainability and scalability features:
	- **Controllers Layer**: The controllers within this layer handle the business logic associated with user authentication. They interact with the models and services to process the requests and generate appropriate responses.
	- **Models Layer**: Defines models for user. These models define the schema and represent how data will be stored in PostgreSQL.
	- **Routes Layer**: This layer defines the HTTP endpoints. It handles incoming HTTP requests and forwards them to the appropriate controllers.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| GET | /users | Returns all the users of the application |  |
	| GET | /users/{user_id} | Returns the user with id = {user_id} | 4 |
	| GET | /users/get_by_email/{email} | Return the user with the email = {email} | 19 |
	| GET | /users/checkToken | Check if the current user has a valid token, or it is expired | 2 |
	| POST | /users/login | Handle the login for the current user, generating as response a token | 2 |
	| POST | /users/signup | Create a new user using the inserted information, return a message that confirm the action | 1 |
	| POST | /users/logout | Remove the token for the logged user, and execute the logout from current session, return a confirmation message | 3 |
	| PUT | /users/{user_id}/edit | Update the information of user with id = {user_id}, and return the json of the updated user | 6 |
	| DELETE | /users/{user_id} | Delete the user with id = {user_id} | 5 |

- DB STRUCTURE: 

	**_user_** :	| **_id_** | name | email | role | password |

#### MICROSERVICE: tickets
- TYPE: backend
- DESCRIPTION: The tickets microservice is responsible for managing all aspects related to event tickets within the OneSport platform. It facilitates the display of available tickets for various sporting events, allowing users to browse and filter tickets based on region-specific criteria. The container enables users to add tickets to a personal collection for future purchase, and to manage (add or remove) these tickets as desired. This service is pivotal for users who plan to attend sports events and need an efficient way to handle their ticketing options.
- PORTS: 5002
- TECHNOLOGICAL SPECIFICATION: The tickets microservice uses Python as its primary programming language. For building a web application with RESTful APIs, the microservice leverages the **Flask** micro-framework. In order to achieve his aims, it uses the following libraries and technologies:
	- **flask_cors**: Extension for handling Cross-Origin Resource Sharing (CORS) in Flask.
	- **flask_sqlalchemy**: Provides ORM support and database integration.
	- **psycopg2**: PostgreSQL adapter for Python, enabling database interaction.
- SERVICE ARCHITECTURE: The tickets microservice adopts a layered architecture pattern, which helps in separating concerns and providing maintainability and scalability features:
	- **Controllers Layer**: The controllers within this layer handle the business logic associated with tickets management. They interact with the models and services to process the requests and generate appropriate responses.
	- **Models Layer**: Defines models for tickets. These models define the schema and represent how data will be stored in PostgreSQL.
	- **Routes Layer**: This layer defines the HTTP endpoints. It handles incoming HTTP requests and forwards them to the appropriate controllers.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| GET | /tickets | Returns all tickets for upcoming events, using an external API, also filters can be applied | 11,12 |
	| GET | /tickets/{user_id} | Returns all tickets collected by user with id = {user_id} | 21 |
	| POST | /tickets/collect | Generate the collection of a ticket for a user, adding also the linked information and return the json of the ticket created | 13 |
	| DELETE | /tickets/{ticket_id} | Deletes the tickets with id = {ticket_id} | 14 |
	| DELETE | /tickets/{user_id}/all | Deletes all the tickets saved by user with id = {user_id} | 15 |

- DB STRUCTURE: 

	**_tickets_**:	| **_id_** | name | info | promoter | urlToImage | localDate | user_id | url |

#### MICROSERVICE: friends
- TYPE: backend
- DESCRIPTION: The friends microservice is designed to manage various aspects of social interaction within the OneSport platform. It allows users to send friend requests, manage incoming friend requests, and control their friend lists. The container handles the addition and removal of friends, helping users curate their social circle within the OneSport environment.
- PORTS: 5003
- TECHNOLOGICAL SPECIFICATION: The friends microservice uses Python as its primary programming language. For building a web application with RESTful APIs, the microservice leverages the **Flask** micro-framework. In order to achieve his aims, it uses the following libraries and technologies:
	- **flask_cors**: Extension for handling Cross-Origin Resource Sharing (CORS) in Flask.
	- **flask_sqlalchemy**: Provides ORM support and database integration.
	- **psycopg2**: PostgreSQL adapter for Python, enabling database interaction.
- SERVICE ARCHITECTURE: The friends microservice adopts a layered architecture pattern, which helps in separating concerns and providing maintainability and scalability features:
	- **Controllers Layer**: The controllers within this layer handle the business logic associated with friends management. They interact with the models and services to process the requests and generate appropriate responses.
	- **Models Layer**: Defines models for friends. These models define the schema and represent how data will be stored in PostgreSQL.
	- **Routes Layer**: This layer defines the HTTP endpoints. It handles incoming HTTP requests and forwards them to the appropriate controllers.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| GET | /friends/{user_id} | Returns all the friends of the user associeted to {user_id} | 22 |
	| GET | /friends/requests/{friend_id} |  Returns all requests recieved by the user associeted to {friend_id} | 17 |
	| POST | /friends/create_request | create a request of friendship by user {user_id} for another one {friend_id}, and return the json of the friendhip created | 16 |
	| PUT | /friends/{request_id}/accept_request | Update the request with id = {request_id}, changing status to 'accepted' | 23 |
	| DELETE | /friends/{friend_id}/cancel_friend | Deletes friendship with id = {friend_id} | 18 |
	| DELETE | /friends/{request_id}/delete_request | Deletes request of friendship with id = {request_id} | 24 |

- DB STRUCTURE: 

	**_friends_**:	| **_id_** | user_id | friend_id | status |

#### MICROSERVICE: postgres
- TYPE: database
- DESCRIPTION: The postgres microservice is responsible for storing: all user-related data including registration information, login credentials, and profile settings; details of news items and favorite lists; data regarding available tickets and user-specific collections; persisting social interaction data such as sent and received friend requests and their statuses (pending, accepted, declined), and maintaining a list of friends for each user. This microservice ensures data integrity and swift access to data in a secure and persistent storage environment.
- PORTS: 5432
- TECHNOLOGICAL SPECIFICATION: The postgres microservice will use PostgreSQL as its database management system due to its robustness, support for relational data storage, and advanced security features.
- SERVICE ARCHITECTURE: There is no architecture.

