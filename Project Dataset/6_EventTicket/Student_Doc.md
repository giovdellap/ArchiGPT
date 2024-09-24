# SYSTEM DESCRIPTION:

The purpose of our project is to build a web application providing a set of services aimed at the management and proposals of events and distribution of tickets. The goal is provide to the users a way to discover events and buy or preorder tickets directly online. At the same time the application allows also the events’ managers to publish their events in order to promote them and eventually to give the possibility to sell tickets of the event through the website.

# USER STORIES:
1) As a client, I want to visit the web application, so that I can explore the functionalities.
2) As a client, I want to register to the service, so that I can be able to use the user's features.
3) As a client, I want to login to the service, so that I become an user, a manager or an admin.
4) As a client, I want to explore the list of events, so that I can see any event.
5) As an user, I want to access my personal page, so that I can see my data.
6) As an user, I want to be able to add a favourite category, so that I can have email notifications about related events.
7) As an user, I want to see the booked pre-sales, so that I can see them.
8) As an user, I want to see the bought tickets, so that I can see them.
9) As an user, I want to book a pre-sale, so that I obtain the pre-sale to access the event.
10) As an user, I want to buy a ticket, so that I obtain the ticket to access the event.
11) As a user, I want to pay for a ticket online so that I can get the ticket code.
12) As an user, I want to logout, so that I become a client.
13) As a manager, I want to see all events I have proposed.
14) As a manager, I want to insert a new event, so that I can add it into the web application.
15) As an manager, I want to access my personal page, so that I can see my data.
16) As a manager, I want to logout, so that I become a client.
17) As an admin, I want to add a new manager, so that he can publish events.
18) As an admin, I want to logout, so that I become a client.
19) As an admin, I want to access my personal page, so that I can see my data.
20) As an user, I want to be able to regenerate my password in case i forgot it.


# CONTAINERS:

## CONTAINER NAME: EventTicket

### DESCRIPTION: 
Manages and displays user account, events, tickets and pre-sales.

### USER STORIES: 
1) As a client, I want to visit the web application, so that I can explore the functionalities.
2) As a client, I want to register to the service, so that I can be able to use the user's features.
3) As a client, I want to login to the service, so that I become an user, a manager or an admin.
4) As a client, I want to explore the list of events, so that I can see any event.
5) As an user, I want to access my personal page, so that I can see my data.
6) As an user, I want to be able to add a favourite category, so that I can have email notifications about related events.
7) As an user, I want to see the booked pre-sales, so that I can see them.
8) As an user, I want to see the bought tickets, so that I can see them.
9) As an user, I want to book a pre-sale, so that I obtain the pre-sale to access the event.
10) As an user, I want to buy a ticket, so that I obtain the ticket to access the event.
11) As a user, I want to pay for a ticket online so that I can get the ticket code.
12) As an user, I want to logout, so that I become a client.
13) As a manager, I want to see all events I have proposed.
14) As a manager, I want to insert a new event, so that I can add it into the web application.
15) As an manager, I want to access my personal page, so that I can see my data.
16) As a manager, I want to logout, so that I become a client.
17) As an admin, I want to add a new manager, so that he can publish events.
18) As an admin, I want to logout, so that I become a client.
19) As an admin, I want to access my personal page, so that I can see my data.
20) As an user, I want to be able to regenerate my password in case i forgot it.

### PORTS: 
84:8081,85:8082,2717:27017,2718:27017,82:3007,81:3006,8080:80,2716:27017,4242:4242

### DESCRIPTION:
This only EventTicket container is designed to handle all core functionalities related to user accounts (user,admin,manager),events, tickets and pre-sales features within the EventTicket platform.

### PERSISTANCE EVALUATION
The container handles persistent and accessible storage for user, events, tickets and pre-sales data. 

### EXTERNAL SERVICES CONNECTIONS
The container handles connection to external services like:
- SendGrid: A cloud-based email delivery service that helps businesses send transactional emails, marketing campaigns, and notifications. It provides email templates, tracking, and analytics features.
- CloudAMQP: A hosted RabbitMQ service that facilitates messaging between different parts of an application or microservices, enabling asynchronous communication, queuing, and message distribution across systems.
- Stripe: A payment processing platform that allows businesses to accept online payments, manage subscriptions, handle billing, and secure transactions for e-commerce and mobile applications.

### MICROSERVICES:

#### MICROSERVICE: rabbit_producer
- TYPE: middleware
- DESCRIPTION: The rabbit_producer microservice hosts RabbitMQ, an open-source message broker software that implements the Advanced Message Queuing Protocol (AMQP). It is used to facilitate communication between different parts of a distributed system by sending and receiving messages, in this case between producers and consumers.
- PORTS: 84:8081
- TECHNOLOGICAL SPECIFICATION: The rabbitmq microservice is responsible for hosting RabbitMQ for producers.
- SERVICE ARCHITECTURE: There is no architecture.

#### MICROSERVICE: rabbit_consumer
- TYPE: middleware
- DESCRIPTION: The rabbit_consumer microservice hosts RabbitMQ, an open-source message broker software that implements the Advanced Message Queuing Protocol (AMQP). It is used to facilitate communication between different parts of a distributed system by sending and receiving messages, in this case between producers and consumers.
- PORTS: 85:8082
- TECHNOLOGICAL SPECIFICATION: The rabbitmq microservice is responsible for hosting RabbitMQ for consumers.
- SERVICE ARCHITECTURE: There is no architecture.

#### MICROSERVICE: mongo
- TYPE: database
- DESCRIPTION: The mongo microservice is responsible for storing: all user-related data including registration information, login credentials, and profile settings for admin, users and managers. This microservice ensures data integrity and swift access to data in a secure and persistent storage environment.
- PORTS: 2717:27017
- TECHNOLOGICAL SPECIFICATION: The mongo microservice will use MongoDB as its database management system, empowering the developer to adapt quickly, scale efficiently, and handle complex data structures effortlessly.
- SERVICE ARCHITECTURE: There is no architecture.

#### MICROSERVICE: mongo_event
- TYPE: database
- DESCRIPTION: The mongo_event microservice is responsible for storing data regarding events. This microservice ensures data integrity and swift access to data in a secure and persistent storage environment.
- PORTS: 2718:27017
- TECHNOLOGICAL SPECIFICATION: The mongo microservice will use MongoDB as its database management system, empowering the developer to adapt quickly, scale efficiently, and handle complex data structures effortlessly.
- SERVICE ARCHITECTURE: There is no architecture.

#### MICROSERVICE: mongodb
- TYPE: database
- DESCRIPTION: The mongodb microservice is responsible for storing data regarding tickets. This microservice ensures data integrity and swift access to data in a secure and persistent storage environment.
- PORTS: 2716:27017
- TECHNOLOGICAL SPECIFICATION: The mongo microservice will use MongoDB as its database management system, empowering the developer to adapt quickly, scale efficiently, and handle complex data structures effortlessly.
- SERVICE ARCHITECTURE: There is no architecture.

#### MICROSERVICE: event
- TYPE: backend
- DESCRIPTION: The event microservice is responsible for managing all aspects related to the display and personalization of events content for users and managers of the EventTicket platform. This includes displaying events, offering functionalities to manage them by managers.
- PORTS: 82:3007
- TECHNOLOGICAL SPECIFICATION: The event microservice uses JavaScript as its primary programming language. For building a JavaScript runtime server-side applications, the microservice leverages the **NodeJS** runtime system. In order to achieve his aims, it uses the following libraries and technologies:
	- **express**: Minimal, flexible web framework for Node.js, used to build APIs and web apps.
	- **ejs**: Templating engine for embedding JavaScript in HTML to render dynamic content.
	- **mongoose**: ODM (Object Data Modeling) library for MongoDB, simplifying schema definitions and data validation.
	- **passportJS**: Authentication middleware for Node.js, supporting various authentication strategies.
	- **jsonwebtoken**: Secure, compact token for authentication and data exchange.
	- **nodemailer**: Module for sending emails in Node.js apps.
- SERVICE ARCHITECTURE: The event microservice adopts a layered architecture pattern, which helps in separating concerns and providing maintainability and scalability features:
	- **Controllers Layer**: The controllers within this layer handle the business logic associated with events management and display. They interact with the models and services to process the requests and generate appropriate responses.
	- **Models Layer**: Defines models for events. These models define the schema and represent how data will be stored in MongoDB using Mongoose.
	- **Routes Layer**: This layer defines the endpoints and the component used for the frontend. It handles incoming requests and forwards them to the appropriate controllers.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| GET | /events | Return all the events | 4 |
	| POST | /createEvent | Handle event creation process | 14 |
	| GET | /single_event | Return an event with event_id |  |
	| GET | /eventi_manager | Return all the events belong to a manager | 13 |

- DB STRUCTURE: 

	**_Event_** :	 | **_id_** | name | descrizione | img | date | city | locale | manager | categoria | num_bigl | prezzo | tipo | bigl_rimanenti |

#### MICROSERVICE: auth1
- TYPE: backend
- DESCRIPTION: The auth1 microservice is responsible for managing all aspects related to the display and personalization of users including users, managers and admins of the EventTicket platform. This includes showing their informations and allowing them to do their business.
- PORTS: 81:3006
- TECHNOLOGICAL SPECIFICATION: The auth1 microservice uses JavaScript as its primary programming language. For building a JavaScript runtime server-side applications, the microservice leverages the **NodeJS** runtime system. In order to achieve his aims, it uses the following libraries and technologies:
	- **express**: Minimal, flexible web framework for Node.js, used to build APIs and web apps.
	- **ejs**: Templating engine for embedding JavaScript in HTML to render dynamic content.
	- **mongoose**: ODM (Object Data Modeling) library for MongoDB, simplifying schema definitions and data validation.
	- **passportJS**: Authentication middleware for Node.js, supporting various authentication strategies.
	- **jsonwebtoken**: Secure, compact token for authentication and data exchange.
	- **nodemailer**: Module for sending emails in Node.js apps.
- SERVICE ARCHITECTURE: The auth1 microservice adopts a layered architecture pattern, which helps in separating concerns and providing maintainability and scalability features:
	- **Controllers Layer**: The controllers within this layer handle the business logic associated with users management and display. They interact with the models and services to process the requests and generate appropriate responses.
	- **Models Layer**: Defines models for users. These models define the schema and represent how data will be stored in MongoDB using Mongoose.
	- **Routes Layer**: This layer defines the endpoints and the component used for the frontend. It handles incoming requests and forwards them to the appropriate controllers.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| POST | /register | Handle registration process for an user | 2 |
	| POST | /registerManager | Handle registration process for a manager | 17 |
	| GET | /activate/:token | Handle email activation |  |
	| POST | /forgot | Handle password regeneration | 20 |
	| POST | /reset/:id | Handle password reset | 20 |
	| GET | /forgot/:token | Handle new token after password reset |  |
	| POST | /login | Handle login process for an user | 3 |
	| GET | /logout | Handle logout process for an user | 12,16,18 |
	| POST | /setPreferenza | Handle user preference | 6 |

- DB STRUCTURE: 

	**_Users_** :	 | **_id_** | name | email | password | verified | resetLink | role | preferenza |

#### MICROSERVICE: nginx
- TYPE: frontend
- DESCRIPTION: The nginx microservice serves as the user interface for the EventTicket platform. It is designed to provide a seamless and interactive experience, allowing users to access all the business logic within the applicatioon. It is the entry point for carrying out all user interactions described in the user stories and ensures that they are intuitive and efficient, enhancing the overall user experience on the platform.
- PORTS: 8080:80
- TECHNOLOGICAL SPECIFICATION: The frontend microservice utilizes Nginx, an HTTP server and load balancer.
- SERVICE ARCHITECTURE: There is no architecture.
- PAGES:

	| Name | Description | Related Microservice | User Stories |
	| ---- | ----------- | -------------------- | ------------ |
	| Home.js | Displays and manages all the application features |  | 1,5,15,19 |

#### MICROSERVICE: payments
- TYPE: backend
- DESCRIPTION: The payments microservice is responsible for managing all aspects related to the display and personalization of tickets and pre-sales content. This includes purchasing tickets and perform checkouts.
- PORTS: 4242:4242
- TECHNOLOGICAL SPECIFICATION: The event microservice uses JavaScript as its primary programming language. For building a JavaScript runtime server-side applications, the microservice leverages the **NodeJS** runtime system. In order to achieve his aims, it uses the following libraries and technologies:
	- **express**: Minimal, flexible web framework for Node.js, used to build APIs and web apps.
	- **ejs**: Templating engine for embedding JavaScript in HTML to render dynamic content.
	- **mongoose**: ODM (Object Data Modeling) library for MongoDB, simplifying schema definitions and data validation.
	- **stripe**: Set of tools and API for simplifing payments on the platforms at global level.
- SERVICE ARCHITECTURE: The payments microservice has no architecture.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| GET | /checkout | Allow the user to checkout a ticket of an event | 10 |
	| POST | /create-checkout-session | Handle checkout session with stripe | 9,11 |
	| GET | /return_tickets | Return all the tickets of an user | 8 |
	| GET | /return_prevendite | Return all the pre-sales | 7 |
	| GET | /prenotazioni | Return all the booked pre-sales |  |

- DB STRUCTURE: 

	**_Event_** :	 | **_id_** | name | descrizione | img | date | city | locale | manager | categoria | num_bigl | prezzo | tipo | bigl_rimanenti |