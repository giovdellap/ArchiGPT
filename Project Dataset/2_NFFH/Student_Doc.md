# SYSTEM DESCRIPTION:

Not Far(m) From Home is a platform that allows a direct interaction between local farmers and consumers, with the main purpose of being “km 0”.
The Farmers will be able to post their fresh produce in the site, and the consumers to reserve the produce and select a day for the pickup at the Agricoltural Company.

# USER STORIES:

1) As a Client , I want to Register in the site so that I can use the site
2) As a Client , I want to login in the site so that I can use the site
3) As a Client , I want to not put my Credentials in the site every time a reload the site, so that I can use the site
4) As a Client , I want to logout, so that no one else use my account
5) As a Client , I want to see my personal information
6) As a Client , I want to See Hot products, so that i can discover the product in the season
7) As a Client , I want to See Agricultural companies in my area, so that i can choose where to buy products
8) As a Client , I want to See products for each Agricultural company, so that I can buy From them
9) As a Client , I want to Add products to my cart, so that i can buy them
10) As a Client, I want to Remove products from my cart, so that i can decide what to buy
11) As a Client , I want to see the products in my cart, so that i can see want I am going to buy
12) As a Client , I want to Complete an order, so that I can choose a date to go and pick up the products
13) As a Client , I want to Open in google Maps the Location of the Agricultural company, so that i can find directions to it easly
14) As an Agricultural Company, I want to Add products in inventory, so that I can show my clients the new produce
15) As an Agricultural Company, I want to Remove products in inventory, so that My clients don't try to buy an item that I don't have anymore
16) As an Agricultural Company, I want to Modify products in inventory, so that I can change price and quantities on an item
17) As an Agricultural Company, I want to Registrer in the site, so that i can be visible and start doing business in the site
18) As an Agricultural Company, I want to Login, so that i can work on the site
19) As an Agricultural Company, I want to not put my Credentials in the site every time a reload the site, so that I can use the site easily
20) As an Agricultural Company, I want to logout from the site
21) As an Agricultural Company, I want to see my personal information
22) As the Administrator of the site, I want to login in the site
23) As the Administrator of the site, I want to not put my Credentials in the site every time a reload the site
24) As the Administrator of the site, I want to logout from the site
25) As the Administrator of the site, I want to delete malevolus user
26) As the Administrator of the site, I want to see all orders, so that I can better analyze how the platform is doing
27) As the Administrator of the site, I want to see all users, so that I can better analyze how the platform is doing
28) As the Administrator of the site, I want to get user details, so that I can have some clear view over the user
29) As the Administrator of the site, I want to modify users, so that I can do site maintenance
30) As the Administrator of the site, I want to see all areas, so that I can do site maintenance
31) As the Administrator of the site, I want to get all Agricultural company by areas, so that I can analyze how they are distributed
32) As the Administrator of the site, I want to get all Agricultural company details, so that I can do platform maintenance
33) As the Administrator of the site, I want to get product by seller, so that I can do site maintenance
34) As the Administrator of the site, I want to get product by id, so that I can do site maintenance
35) As the Administrator of the site, I want to add an area, so that if there is some request for that area to be added, I can
36) As the Administrator of the site, I want to add product, so that I can do site maintenance
37) As the Administrator of the site, I want to modify product, so that I can do site maintenance
38) As the Administrator of the site, I want to delete product, so that I can do site maintenance
39) As the Administrator of the site, I want to delete an area, so that I can do site maintenance
40) As a client, i want to see all my orders
41) As a farmer, i want to see all my orders



# CONTAINERS:

## CONTAINER_NAME: Authentication

### DESCRIPTION: 
Manages all functionalities related to registration, login, session persistence, and logout for clients, agricultural companies, and the site administrator.

### USER STORIES:
1) As a Client , I want to Register in the site so that I can use the site

2) As a Client , I want to login in the site so that I can use the site

3) As a Client , I want to not put my Credentials in the site every time a reload the site, so that I can use the site

17) As an Agricultural Company, I want to Registrer in the site, so that i can be visible and start doing business in the site

18) As an Agricultural Company, I want to Login

19) As an Agricultural Company, I want to not put my Credentials in the site every time a reload the site, so that I can use the site easily

4) As a Client , I want to logout, so that no one else use my account

20) As an Agricultural Company, I want to logout from the site

22) As the Administrator of the site, I want to login in the site
    
23) As the Administrator of the site, I want to not put my Credentials in the site every time a reload the site
    
24) As the Administrator of the site, I want to logout from the site



### PORTS: 
9701:9701

### DESCRIPTION:
The Authentication container is responsible for managing all the security-related functionalities that involve registration, login, session persistence clients and agricultural companies. 

### PERSISTANCE EVALUATION
The Authentication container does not require data persistence to manage token creation and validation.

### EXTERNAL SERVICES CONNECTIONS
The Authentication container does not connect to external services.

### MICROSERVICES:

#### MICROSERVICE: auth
- TYPE: backend
- DESCRIPTION: Manages the creation and verification of tokens.
- PORTS: 9701
- TECHNOLOGICAL SPECIFICATION:
The microservice is developed in Python and uses Flask, a lightweight Python web framework.
It uses the followinf libraries and technologies:
    - Gunicorn: The microservice uses Gunicorn as a WSGI server to serve the Flask application in a production environment.
    - JWT (pyjwt): The microservice handles JSON Web Tokens (JWT), commonly used for secure token-based authentication.
    - Cryptography: The cryptography==41.0.4 and cffi==1.16.0 packages are used for secure encryption and cryptographic operations.
    - Requests: The library requests==2.31.0 is used for making HTTP requests
- SERVICE ARCHITECTURE: 
The service uses a single file to manage login and signup, with functions to create and evaluate JWT tokens.

- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
    | POST | /{role}/login | Verifies encrypted password and creates and sends out a JWT token | 2, 18, 22 |
    | POST | /{role}/signup | Creates encrypted password and creates and sends out a JWT token | 1, 17 |
    | POST | /{role}/logout | Deletes the token | 4, 20, 24 |
    | POST | /verifyToken | Verifies the validity of a JWT token | 3, 19, 23 |


## CONTAINER_NAME: Client-BE

### DESCRIPTION: 
Handles operations related to user and company profiles, viewing personal information, and storing profile related data.

### USER STORIES:
5) As a Client , I want to see my personal information

27) As the Administrator of the site, I want to see all users, so that I can better analyze how the platform is doing

28) As the Administrator of the site, I want to get user details, so that I can have some clear view over the user

25) As the Administrator of the site, I want to delete malevolus user

1) As a Client , I want to Register in the site so that I can use the site

2) As a Client , I want to login in the site so that I can use the site


### PORTS:
9702:9702

### DESCRIPTION:
The Client-BE container handles operations related to the management of user and company profiles on the "Not Far(m) From Home" platform. Its main responsibilities include displaying personal information for clients and the site administrator. This container serves as a central point for storing and managing profile-related data.

### PERSISTANCE EVALUATION
The Client-BE container requires persistent storage to maintain details about the users and company profiles. It needs to store user-specific information, such as personal data and any associated preferences or settings attributed to that user. This data includes profile contact details like email, username and password.

### EXTERNAL SERVICES CONNECTIONS
The Client-BE container does not connect to external services.

### MICROSERVICES:

#### MICROSERVICE: CLIENT-BE
- TYPE: backend
- DESCRIPTION: Handles the creation, viewing, and updating of personal information for clients. It also enables administrators to view and retrieve detailed user profiles to analyze platform activity and manage user data.
- PORTS: 9702
- TECHNOLOGICAL SPECIFICATION:
The microservice utilizes the Java programming language, specifically targeting Java 17. The service is built using the Spring Boot framework, version 2.7.4.
Key components of the stack:
Spring Boot:
Several Spring Boot starter dependencies are included:
spring-boot-starter-data-jpa: the service interacts with a relational database using JPA (Java Persistence API).
spring-boot-starter-web: This is used for building web applications, including RESTful APIs.
spring-boot-starter-test: This is included for unit and integration testing purposes.
MySQL:
The dependency mysql-connector-java indicates that the microservice is configured to connect to a MySQL database.
Maven:
The build process is managed by Apache Maven, with plugins such as spring-boot-maven-plugin for packaging the application and maven-compiler-plugin for compiling the code with Java 17.
- SERVICE ARCHITECTURE:
The service is realized with:
    - a controller to manage routes, request parameters and responses. It handles the business logic of the microservice.
    - model directory with all the classes related to requests'bodies and responses.
    - repositories and services to interact with the database and make queries.
    - an entity class to respresent the database table.

- ENDPOINTS:

    | HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
    | POST | /client | Inserts a new client in the database and interacts with the auth container | 2 |
    | GET | /clients | Returns all the clients in the database | 27 |
    | GET | /client/{id} | Returns the client informations | 5, 28 |
    | DELETE | /client/{id} | Deletes the client | 25 |
    | POST | /client/login | Manages the client login and interacts with the auth container | 1 |

- DB STRUCTURE: 

	**_Client_** :	| **_id_** | name | email | password |


#### MICROSERVICE: mysql-client
- TYPE: database
- DESCRIPTION: Manages persistent storage of user data.
- PORTS: 3306


## CONTAINER_NAME: Farmer-BE

### DESCRIPTION: 
Manages operations regarding products, including adding, modifying, and removing products. This service caters to agricultural companies and the administrator.

### USER STORIES:

6) As a Client , I want to See Hot products, so that i can discover the product in the season
7) As a Client , I want to See Agricultural company in my area, so that i can choose where to buy products
8) As a Client , I want to See The products for each Agricultural company, so that I can buy From them
14) As an Agricultural Company, I want to Add products in inventory, so that I can show my clients the new produce
15) As an Agricultural Company, I want to Remove products in inventory, so that My clients don't try to buy an item that I don't have anymore
16) As an Agricultural Company, I want to Modify products in inventory, so that I can change price and quantities on the same item
17) As an Agricultural Company, I want to Registrer in the site, so that i can be visible and start doing business in the site
18) As an Agricultural Company, I want to Login, so that i can work on the site
30) As the Administrator of the site, I want to see all areas, so that I can do site maintenance
31) As the Administrator of the site, I want to get all Agricultural company by areas, so that I can analyze how they are distributed
32) As the Administrator of the site, I want to get all Agricultural company details, so that I can do platform maintenance
33) As the Administrator of the site, I want to get product by seller, so that I can do site maintenance
34) As the Administrator of the site, I want to get product by id, so that I can do site maintenance
35) As the Administrator of the site, I want to add an area, so that if there is some request for that area to be added, I can
36) As the Administrator of the site, I want to add product, so that I can do site maintenance
37) As the Administrator of the site, I want to modify product, so that I can do site maintenance
38) As the Administrator of the site, I want to delete product, so that I can do site maintenance
39) As the Administrator of the site, I want to delete an area, so that I can do site maintenance
21) As an Agricultural Company, I want to see my personal information



### PORTS: 
9703:9703

### DESCRIPTION:
The  container is designed to manage all operations related to products on the "Not Far(m) From Home" platform. Its primary responsibilities include adding new products to the inventory, modifying details of existing products, and removing products that are no longer available. 
The container also provides essential capabilities for the site administrator that include adding, modifying, or deleting products, as well as retrieving product details either by seller or by product ID. 
It also shows products to clients and manages Agricultural companies profile data.

### PERSISTANCE EVALUATION
The Product_Management container requires persistent storage to manage the product information effectively. This container maintains a database of products that includes details like product name, quantity, price, agricultural company ID, and product descriptions. It also manages agricultural companies informations and areas-related informations.

### EXTERNAL SERVICES CONNECTIONS
The Farmer-BE container does not connect to external services.


### MICROSERVICES:

#### MICROSERVICE: farmer-be
- TYPE: backend
- DESCRIPTION: Manages all backend functionalities such as adding, modifying, and removing products from the inventory, manges agricultural companies datas and areas. 
- PORTS: 9703
- TECHNOLOGICAL SPECIFICATION:
The microservice utilizes the Java programming language, specifically targeting Java 17. The service is built using the Spring Boot framework, version 2.7.4.
Key components of the stack:
Spring Boot:
Several Spring Boot starter dependencies are included:
spring-boot-starter-data-jpa: the service interacts with a relational database using JPA (Java Persistence API).
spring-boot-starter-web: This is used for building web applications, including RESTful APIs.
spring-boot-starter-test: This is included for unit and integration testing purposes.
MySQL:
The dependency mysql-connector-java indicates that the microservice is configured to connect to a MySQL database.
Maven:
The build process is managed by Apache Maven, with plugins such as spring-boot-maven-plugin for packaging the application and maven-compiler-plugin for compiling the code with Java 17.
- SERVICE ARCHITECTURE:
The service is realized with:
    - a controller to manage areas endpoint, one to mange products and one to manage farmers
    - model directory with all the classes related to requests'bodies and responses.
    - repositories and services to interact with the database and make queries.
    - Three Entity classes, one for each table of the Database.

- ENDPOINTS:

    | HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
    | POST | /farmer | Inserts a new farmer in the database and interacts with the auth container | 17 |
    | GET | /farmers | Returns all the clients in the database | 27 |
    | GET | /farmer/{id} | Returns the farmer informations | 8, 21 |
    | DELETE | /farmer/{id} | Deletes the farmer | 25 |
    | POST | /farmer/login | Manages the client login and interacts with the auth container | 18 |
    | GET | /farmer/areas | Returns the farmers divided by Area | 31, 7, 32 |
    | POST | /area | Inserts a new area | 35 |
    | GET | /area | Returns all the areas | 30 |
    | DELETE | /area/{id} | Deletes the area | 39 |
    | POST | /product/add | Inserts a new product | 14, 36 |
    | DELETE | /product/{id} | Deletes the area | 15, 38 |
    | GET | /product/findBySeller | Returns all the products of an agricultural company | 33, 8 |
    | POST | /product/modify/{id} | Modifies a product | 16, 37 |
    | GET | /product/{id} | Returns the product with the specified id | 34, 6 |

- DB STRUCTURE: 

	**_Product_** :	| **_id_** | title | seller | image | description | price | weight | availability |
    **_Farmer_** :	| **_id_** | username | email | password | image | area | address |
    **_Farmer_** :	| **_id_** | name |

#### MICROSERVICE: mysql-farmer
- TYPE: database
- DESCRIPTION: Stores all relevant data concerning the products, agricultural companies and areas.
- PORTS: 3306

## CONTAINER_NAME: Order-BE

### DESCRIPTION: 
Manages order placements, including completing orders and arranging pickup dates, as well as viewing all orders for site administration.

### USER_STORIES:
12) As a Client , I want to Complete an order, so that I can choose a date to go and pick up the products
26) As the Administrator of the site, I want to see all orders, so that I can better analyze how the platform is doing
40) As a client, i want to see all my orders
41) As a farmer, i want to see all my orders

### PORTS: 
9704:9704

### DESCRIPTION:
The Order-BE container is dedicated to managing all aspects of order processing within the "Not Far(m) From Home" platform. This includes facilitating the completion of orders by clients—allowing them to select dates for product pickup directly from local farmers. Additionally, it provides capabilities for the site administrator to view all processed orders, offering insights and oversight to ensure smooth operation and fulfillment. 

### PERSISTANCE EVALUATION
The Order-BE container requires persistent storage to maintain and manage the details of each order. This includes storing information such as the order IDs, consumer details, products ordered, quantities, prices, and the selected pickup dates. 

### EXTERNAL SERVICES CONNECTIONS
The Order-BE container does not connect to external services.


### MICROSERVICES:

#### MICROSERVICE: order_be
- TYPE: backend
- DESCRIPTION: This microservice is responsible for handling the logic and operations related to order placement by clients. It allows clients to complete orders, select pickup dates, and ensures all necessary details are preserved for each transaction. It also communicates with the Product_Management container to check product availability. Provides to the site's administrator the capability to view and manage all orders.
- PORTS: 9704
- TECHNOLOGICAL SPECIFICATION:
The microservice utilizes the Java programming language, specifically targeting Java 17. The service is built using the Spring Boot framework, version 2.7.4.
Key components of the stack:
Spring Boot:
Several Spring Boot starter dependencies are included:
spring-boot-starter-data-jpa: the service interacts with a relational database using JPA (Java Persistence API).
spring-boot-starter-web: This is used for building web applications, including RESTful APIs.
spring-boot-starter-test: This is included for unit and integration testing purposes.
MySQL:
The dependency mysql-connector-java indicates that the microservice is configured to connect to a MySQL database.
Maven:
The build process is managed by Apache Maven, with plugins such as spring-boot-maven-plugin for packaging the application and maven-compiler-plugin for compiling the code with Java 17.
- SERVICE ARCHITECTURE:
The service is realized with:
    - a controller to manage routes, request parameters and responses. It handles the business logic of the microservice.
    - model directory with all the classes related to requests'bodies and responses.
    - repositories and services to interact with the database and make queries.
    - an entity class to respresent the database table.

- ENDPOINTS:

    | HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
    | POST | /order | Inserts a new order | 12 |
    | GET | /order/<client>/myorder | Returns all the client's orders | 26, 40 |
    | GET | /order/<farmer>/myorder | Returns all the farmer's orders | 26, 41 |


- DB STRUCTURE: 

	**_Order_** :	| **_id_** | client | commission | pickup | accepted |

#### MICROSERVICE: mysql-order
- TYPE: database
- DESCRIPTION: Manages the persistent storage and retrieval of order data, including order IDs, consumer details, products ordered, quantities, prices, and pickup dates. 
- PORTS: 3306


## CONTAINER_NAME: Client-FE

### USER STORIES:
13) As a Client , I want to Open in google Maps the Location of the Agricultural company, so that i can find directions to it easly
8) As a Client , I want to Add to cart the products, so that i can buy them
9) As a Client, I want to Remove products to the cart, so that i can decide what to buy
10) As a Client , I want to see product in the cart, so that i can see want I am going to buy
11) As a Client , I want to see the products in my cart, so that i can see want I am going to buy


### PORTS: 
4201:4201

### DESCRIPTION:
The Client-FE container is primarily responsible for managing the front-end user interactions related to product showcasing and purchase on the Not Far(m) From Home platform. This includes displaying products from various agricultural companies and seasonal or hot products, managing the shopping cart functionalities like adding and removing products, and providing a comprehensive view of products in the shopping cart. Designed to enhance user experience, it ensures clients can easily navigate through different product offerings, make informed purchase decisions, and handle their transactions efficiently within the platform.

### PERSISTANCE EVALEVALUATION
The Client-FE container does not include a database.

### EXTERNAL SERVICES SERVICES CONNECTIONS
The Client-FE container connects to Google Maps API to show the Agricultural companies locations.

### MICROSERVICES:

#### MICROSERVICE: client-fe
- TYPE: frontend
- DESCRIPTION: This microservice serves the main user interface for the Customer.
- PORTS: 4201
- - PAGES:

	| Name | Description | Related Microservice | User Stories |
	| ---- | ----------- | -------------------- | ------------ |
	| Home.js | Displays and manages yhe client SPA | client-be, farmer-be | 8, 9, 10, 11, 13 |


## CONTAINER_NAME: Admin-FE

### DESCRIPTION: 
Provides administrative control features including user and product modification, and deletion of malicious users.

### USER-STORIES:

### PORTS: 
4203:4203

### DESCRIPTION:
The Admin-FE container serves a frontend for admin user stories.

### PERSISTANCE EVALIGATION
The Admin-FE container does not include a database.

### EXTERNAL SERVICES CONNECTIONS
The Admin-FE container does not connect to external services.

### MICROSERVICES:

#### MICROSERVICE: admin-fe
- TYPE: frontend
- DESCRIPTION: This microservice serves the main user interface for the Administrator.
- PORTS: 4203


## CONTAINER_NAME: Farmer-FE

### DESCRIPTION: 
Provides the User Interface for the Agricutural companies.

### USER-STORIES:

### PORTS: 
4202:4202

### DESCRIPTION:
The Farmer-FE container serves a frontend for Agricultural Companies User stories.

### PERSISTANCE EVALUATION
The Farmer-FE container does not include a database.

### EXTERNAL SERVICES CONNECTIONS
The Farmer-FE container does not connect to external services.

### MICROSERVICES:

#### MICROSERVICE: admin-fe
- TYPE: frontend
- DESCRIPTION: This microservice serves the main user interface for the Agricultural companies.
- PORTS: 4202


## CONTAINER_NAME: Image-Server

### DESCRIPTION: 
Provides storing and retrieval of images for all the users of the site.

### USER-STORIES:

### PORTS: 
9705:9705

### DESCRIPTION:
The Image-Server container Provides storing and retrieval of images for all the users of the site.

### PERSISTANCE EVALIGATION
The Image-Server container does not include a database, stores images directly in the VM memory.

### EXTERNAL SERVICES CONNECTIONS
The Farmer-FE container does not connect to external services.

#### MICROSERVICE: image-server
- TYPE: backend
- DESCRIPTION: This microservice Provides storing and retrieval of images for all the users of the site.
- PORTS: 9703

## CONTAINER_NAME: APIGateway

### DESCRIPTION: 
Provides a single Point of Access for all the incoming requests

### USER-STORIES:

### PORTS: 
8080:8080

### DESCRIPTION:
Provides a single Point of Access for all the incoming requests

### PERSISTANCE EVALUATION
The APIGateway container does not include a database.

### EXTERNAL SERVICES CONNECTIONS
The APIGateway container does not connect to external services.

#### MICROSERVICE: api-getaway
- TYPE: middleware
- DESCRIPTION: This microservice Provides storing and retrieval of images for all the users of the site.
- PORTS: 9703
- DESCRIPTION: Is the only available endpoint of the system, redirects the requests to the correct container
