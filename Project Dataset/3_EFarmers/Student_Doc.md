# SYSTEM DESCRIPTION:

E-Farmers is an e-commerce website that aims to connect local farmers with customers who are interested in
buying fresh and locally sourced products. Our platform allows farmers to showcase their products by publishing
insertions, while customers can easily browse and purchase these goods. Whether you are a farmer looking
to sell your products or a customers seeking high-quality ”farm-to-table” items, E-Farmers has something for
everyone. Moreover, if you cannot reach the farmer’s warehouse yourself, you can rely on riders who can ship
the products to your home

# USER STORIES:

1) As a visitor, I want to register to the website with an email
2) As a user, I want to logout from my account
3) As a user, I want to reach a login page so that I can login into my  account
4) As a farmer, I want to publish a new insertion
5) As a user, I want to access the home page
6) As a user, I want to access my personal page, so that I can see my informations
7) As a customer, I can check on calendar all seasonal foodstuff 
8) As a user or visitor, I can search for farmer's insertions 
9) As a the owner of an insertion, I want to delete one of my insertions
10) As a farmer, I want to modify my insertions
11) As a user, I want to see the expiring boxes
12) As a customer, I want to update my status to Farmer
13) As a user, I want to access a farmer page, so I can see his information
14) As a customer, I want to add a product in my cart, so that I buy this product 
15) As a customer, I want to delete the boxes added to the shopping cart
16) As a customer, I want to buy boxes added to the shopping cart
17) As a customer, I want to be able to checkout and pay for my order using a secure payment gateway. 
18) As a customer, I want to book a product from the calendar
19) As a customer, I can subscribe to a farmer, so I will be notify for his every new insertion
20) As a customer, I want to have a list of the farmers that I am subscribed to 
21) As a farmer, I want to accept incoming requests for booked boxes 
22) As a customer, I want to check my order history, so I can remember past purchases 
23) As a user that bought a box, I want to leave a comment to the farmer
24) As a customer, I want to update my status to Rider, so that I can deliver stuffs. 
25) As a rider, I want to have a personal delivery page 
26) As a rider, I want to change my availability status to available so that I can receive delivery requests 
27) As a customer, I want to select a delivery option, so that I can receive the products at home 
28) As a farmer, I want to achieve a badge 
29) As a visitor, I want to register to the website with OAuth
30) As a user, I want to be able to modify my informations



# CONTAINERS:

## CONTAINER_NAME: EFarmers

### DESCRIPTION: 
Manages and displays user account, insertions, payments orders, shopping carts and subscriptions features.

### USER STORIES:
1) As a visitor, I want to register to the website with an email
2) As a user, I want to logout from my account
3) As a user, I want to reach a login page so that I can login into my  account
4) As a farmer, I want to publish a new insertion
5) As a user, I want to access the home page
6) As a user, I want to access my personal page, so that I can see my informations
7) As a customer, I can check on calendar all seasonal foodstuff 
8) As a user or visitor, I can search for farmer's insertions 
9) As a the owner of an insertion, I want to delete one of my insertions
10) As a farmer, I want to modify my insertions
11) As a user, I want to see the expiring boxes
12) As a customer, I want to update my status to Farmer
13) As a user, I want to access a farmer page, so I can see his information
14) As a customer, I want to add a product in my cart, so that I buy this product 
15) As a customer, I want to delete the boxes added to the shopping cart
16) As a customer, I want to buy boxes added to the shopping cart
17) As a customer, I want to be able to checkout and pay for my order using a secure payment gateway. 
18) As a customer, I want to book a product from the calendar
19) As a customer, I can subscribe to a farmer, so I will be notify for his every new insertion
20) As a customer, I want to have a list of the farmers that I am subscribed to 
21) As a farmer, I want to accept incoming requests for booked boxes 
22) As a customer, I want to check my order history, so I can remember past purchases 
23) As a user that bought a box, I want to leave a comment to the farmer
24) As a customer, I want to update my status to Rider, so that I can deliver stuffs. 
25) As a rider, I want to have a personal delivery page 
26) As a rider, I want to change my availability status to available so that I can receive delivery requests 
27) As a customer, I want to select a delivery option, so that I can receive the products at home 
28) As a farmer, I want to achieve a badge 
29) As a visitor, I want to register to the website with OAuth
30) As a user, I want to be able to modify my informations

### PORTS: 
3000:8083

### DESCRIPTION:
This only EFarmers container is designed to handle all core functionalities related to user account, insertions, payments orders, shopping carts and subscriptions features within the EFarmers platform.

### PERSISTANCE EVALUATION:
The container handles persistent and accessible storage for user, insertions, payments orders, shopping carts and subscriptions data. 

### EXTERNAL SERVICES CONNECTIONS:
The container connects to external services for processing secure payments. In particular it uses Stripe library in Python, a popular payment processing platform that provides a suite of APIs and tools for handling online payments.

### MICROSERVICES:

#### MICROSERVICE: frontend
- TYPE: frontend
- DESCRIPTION: Manages user-specific pages and calendar features, including individual user account and farmer profiles, user subscriptions to farmers, and user feedback mechanisms like comments and reviews.
- PORTS: 3000
- TECHNOLOGICAL SPECIFICATION: The frontend microservice is built using ReactJS, which serves as the core framework for developing user interfaces. By utilizing its component-based architecture, the application is both responsive and dynamic. For user authentication, it integrates GoogleOAuthProvider, enabling secure sign-ins through Google Accounts without requiring users to create separate accounts. Additionally, the microservice uses Axios to handle asynchronous HTTP requests, ensuring efficient communication with backend services, along with managing responses and errors seamlessly.
- SERVICE ARCHITECTURE: The frontend microservice architecture is built on a component-based design pattern using hooks, promoting the creation of reusable, modular, and isolated UI components. It communicates with other microservices by providing functions that make API calls using Axios, efficiently managing both requests and responses.
- PAGES:

	| Name | Description | Related Microservice | User Stories |
	| ---- | ----------- | -------------------- | ------------ |
	| AddBoxes.js |  |  |  |
	| BookedProducts.js |  |  |  |
	| Delivery.js |  |  |  |
	| EditInsertion.js |  |  |  |
	| FarmerProfile.js | Displays farmer informations | user_service | 13 |
	| Home.js | Display the main feature of the application | insertions_service | 5 |
	| Inbox.js |  |  |  |
	| InsertionDetails.js |  |  |  |
	| Insertions.js | Diplay insertions | insertions_service | 8 |
	| Login.js | Manages user authentication and login process | user_service | 3 |
	| NotFound.js |  |  |  |
	| OrdersMainPage.js |  |  |  |
	| PaymentPage.js |  |  |  |
	| PublishInsertion.js |  |  |  |
	| Registration.js |  |  |  |
	| RiderProfile.js |  |  |  |
	| ShoppingCart.js |  |  |  |
	| UserChanges.js | Manages user infromations | user_service |  |
	| UserProfile.js | Displays user informations | user_service | 6 |
	| SeasonCalendar.js | Display seasonal calendar for foodstuff |  | 7 |

#### MICROSERVICE: user_service
- TYPE: backend
- DESCRIPTION: Manages user accounts, including registration (both email and OAuth), login, logout, session management, reviews, and updating user roles (such as customer to farmer, or customer to rider).
- PORTS: 8080
- TECHNOLOGICAL SPECIFICATION: The user_service microservice uses Python as its primary programming language. For building a web application with RESTful APIs, the microservice leverages the **Django** web framework. In order to achieve his aims, it uses the following libraries and technologies:
	- **django-cors-headers**: Extension for handling Cross-Origin Resource Sharing (CORS) in Django.
	- **djangorestframework-simplejwt**: A JSON Web Token (JWT) authentication package for Django REST framework.
	- **psycopg2-binary**: PostgreSQL adapter for Python, enabling database interaction.
- SERVICE ARCHITECTURE: The user_service microservice adopts a simple architecture that separate configurations from the API:
	- **Users API**: A set of models and APIs for managing users (both riders and farmers), reviews, and token authentication using JWT.
	- **Users Service**: A collection of every configurations of ASGI/WSGI and Django.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	`Token`
	| POST | login/ | Generates the access and the refresh tokens associated to user with those email and password | 3 |
	| POST | token/refresh/ | It takes as input a refresh token and outputs the two new access and refresh tokens. It also returns a new refresh token since the first one is backlisted |  |
	| POST | token/verify/ | In this case the token is passed in the Authorization header. This API checks whether the user id passed as input is the one associated with the token. If it is not the case, the access is denied  |  |
	| POST | oauth/token/ | The access token in input is the token generated by the Google OAuth service. The API then returns the usual access and refresh JWT tokens of our system | 29 |
	| POST | logout/blacklist/ | The refresh token is blacklisted. This will make sure that the refresh token cannot be used again to generate a new token | 2 |
	`User`
	| POST | login/ | It checks if there exist a user with that email and password in the DB. If it is not the case an exception is thrown | 3 |
	| GET | users/ | Return all users that are stored in the database |  |
	| POST | users/ | This creates a new instance of the user only when you satisfied all the requirements needed in order to register to this application. If you don't respect some of this requirements: like unique email and so on, you can't able to register to the site | 1 |
	| GET | users/{user_id}/ | Get the specific user which match the user_id passed through the request. If the user has some special extra information, that comes from special kind of account, the request retrieve either the user information and the user extra info | 6,13 |
	| GET | users/{user_id}/name/ | Get the name of the user with that id |  |
	| PATCH | users/{user_id}/ | This request modify the field of the user in the db by updating the type of the users account. This is a special operation that is handled if cause some error by administrator | 12,24 |
	| PATCH | users/{user_id}/changes/ | Updates the user's account information, such as name, billing, shipping address and so other usefully information. It doesn't require that all field are filled up with some context | 30 |
	| POST | users/{user_id}/{type} | Insert user extra information based on type, by adding some new field in the database. In this case gives two additional information for the specific case of rider and farmer | 12,24 |
	`User.Farmer`
	| GET | farmers/{user_id}/ | Get the instance of farmer which has the sames user_id, then retrieve all the extra information about it |  |
	| PATCH | farmers/{user_id}/id | Once a farmer publish his insertion, this request is called in order to increase the global counter of published insertion for the specific farmer |  |
	`User.Rider`
	| GET | riders/ | Search through the database to find the first raider available in order to assign him to a new delivery |  |
	| GET | riders/{user_id}/ | Get the instance of rider which has the same user_id, then retrieve all the extra information about it |  |
	| PATCH | riders/{user_id}/ | Special operation for the rider. It change only the status of the rider by putting him available or not available, which means being able to handle a delivery and not being able to handle a delivery, respectively | 26 |
	`Review`
	| GET | review/{user_id}/ | Return the last review written for the specific farmer and display it when u visit the Farmer profile page | 13 |
	| POST | review/{user_id}/ | User can create a review about the order. This review is applied both to the shipping and to the quality of the product selled by the farmer. Once the user create a review it can be available on the specific farmer's page | 23 |

- DB STRUCTURE: 

	**_User_** :	 | **_id_** | email | password | username | name | insertions | carts | account_type | phone_numer | billing_address | shipping_address |

	**_Rider_** :	 | **_id_** | available | bio | **_ext_user_** |

	**_Farmer_** :	 | **_id_** | farm_location | bio | number_insertions | since | **_ext_user_** |

	**_Review_** :	 | **_id_** | rating | comment | farmer_user | **_writer_user_** |

#### MICROSERVICE: insertions_service
- TYPE: backend
- DESCRIPTION: Handles the creation, modification, and deletion of insertions, managing boxes booking (send boxes booking request, retrieve boxes list and booking request), and maintaining details of expiring insertions.
- PORTS: 8081
- TECHNOLOGICAL SPECIFICATION: The insertions_service microservice uses Python as its primary programming language. For building a web application with RESTful APIs, the microservice leverages the **Django** web framework. In order to achieve his aims, it uses the following libraries and technologies:
	- **django-cors-headers**: Extension for handling Cross-Origin Resource Sharing (CORS) in Django.
	- **djangorestframework-simplejwt**: A JSON Web Token (JWT) authentication package for Django REST framework.
	- **psycopg2-binary**: PostgreSQL adapter for Python, enabling database interaction.
	- **Pillow**: A python library for opening, manipulating, and saving images.
- SERVICE ARCHITECTURE: The insertions_service microservice adopts a simple architecture that separate configurations from the API:
	- **Insertions API**: A set of models and APIs for managing insertions, boxes and booking requests.
	- **Insertions Service**: A collection of every configurations of ASGI/WSGI and Django.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	`Insertion`
	| GET | insertions/ | List all or a subset of the insertions | 8 |
	| POST | insertions/ | Creates a new insertion |  |
	| GET | insertions/{id}/ | Retrieve a specific insertion. If the insertion has expired, it is deleted from the database |  |
	| GET | insertions/{id}/image/ | Retrieve the image of the specified insertion |  |
	| PUT | insertions/{id}/ | Modify an existing insertion | 10 |
	| DELETE | insertions/{id}/ | Delete the specified insertion | 9 |
	`Box`
	| GET | insertions/{id}/boxes/ | Retrieve all the boxes related to an insertion | 11 |
	| POST | insertions/{id}/boxes/ | Create a new box for an insertion |  |
	| PATCH | insertions/{box_id}/decrease | Decrease the number of boxes of a given type |  |
	`Booking`
	| GET | booking/{request_id}/ | Returns the specified request |  |
	| PUT | booking/{request_id}/ | As a farmer, accept a request by publishing a private insertion |  |
	| GET | booking/requests/{user_id}/ | Returns the list of a user's requests (booked products) |  |
	| GET | booking/inbox/{farmer_id}/ | Returns the list of a farmer's requests received by users |  |
	| POST | booking/ | Creates a new request |  |
	| DELETE | booking/{id}/ | Deletes the specified request |  |

- DB STRUCTURE: 

	**_Insertion_** :	 | **_id_** | title | description | expiration_date | gathering_location | image | reported | farmer | private | ***_request_***

	**_Box_** :	 | **_id_** | insertion | weight | size | price | number_of_available_boxes |

	**_Request_** :	 | **_id_** | user | farmer | title | comment | weight | deadline | ***_insertion_*** |

#### MICROSERVICE: shoppingcart_service
- TYPE: backend
- DESCRIPTION: Manages shopping cart functions, including adding, modifying, and removing items.
- PORTS: 8082

#### MICROSERVICE: payments_order_service
- TYPE: backend
- DESCRIPTION: Processes secure payments with Stripe library, handling delivery options and functionalities specific to riders, such as setting availability status.
- PORTS: 8083

#### MICROSERVICE: subscription_service
- TYPE: backend
- DESCRIPTION: This service implements the subscription funcionality: a customer (user) can subscribe to a farmer in order to be notified when that farmer publishes new insertions.
- PORTS: 5000

#### MICROSERVICE: db
- TYPE: database
- DESCRIPTION: The db microservice is responsible for storing: all user-related data including registration information, login credentials, and profile settings for admin, customers and riders; details of insertions, boxes and order requests lists; data regarding available riders and carts; persisting user interactions data such as reviews and subscription. This microservice ensures data integrity and swift access to data in a secure and persistent storage environment.
- PORTS: 5432

#### MICROSERVICE: rabbitmq
- TYPE: middleware
- DESCRIPTION: The rabbitmq microservice hosts RabbitMQ, an open-source message broker software that implements the Advanced Message Queuing Protocol (AMQP). It is used to facilitate communication between different parts of a distributed system by sending and receiving messages, in this case between customers and farmers.
- PORTS: 5672