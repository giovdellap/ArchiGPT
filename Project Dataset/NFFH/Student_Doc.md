# SYSTEM DESCRIPTION:

Not Far(m) From Home is a platform that allows a direct interaction between local farmers and consumers, with the main purpose of being “km 0”.
The Farmers will be able to post their fresh produce in the site, and the consumers to reserve the produce and select a day for the pickup at the Agricoltural Company.

# USER STORIES:

1) As a Client , I want to be able to Register in the site so that I can use the site
2) As a Client , I want to be able to login in the site so that I can use the site
3) As a Client , I want to be able to not put my Credentials in the site every time a reload the site, so that I can use the site
4) As a Client , I want to be able to logout, so that no one else use my account
5) As a Client , I want to be able to see my personal information
6) As a Client , I want to be able to See Hot products, so that i can discover the product in the season
7) As a Client , I want to be able to See Agricultural company in my area, so that i can choose where to buy products
8) As a Client , I want to be able to See The products for each Agricultural company, so that I can buy From them
9) As a Client , I want to be able to Add to cart the products, so that i can buy them
10) As a Client, I want to be able to Remove products to the cart, so that i can decide what to buy
11) As a Client , I want to be able to see product in the cart, so that i can see want I am going to buy
12) As a Client , I want to be able to Complete an order, so that I can choose a date to go and pick up the products
13) As a Client , I want to be able to Open in google Maps the Location of the Agricultural company, so that i can find directions to it easly
14) As an Agricultural Company, I want to be able to Add products in inventory, so that I can show my clients the new produce
15) As an Agricultural Company, I want to be able to Remove products in inventory, so that My clients don't try to buy an item that I don't have anymore
16) As an Agricultural Company, I want to be able to Modify products in inventory, so that I can change price and quantities on the same item
17) As an Agricultural Company, I want to be able to Registrer in the site, so that i can be visible and start doing business in the site
18) As an Agricultural Company, I want to be able to Login, so that i can work on the site
19) As an Agricultural Company, I want to be able to ot put my Credentials in the site every time a reload the site, so that I can use the site easily
20) As an Agricultural Company, I want to be able to logout from the site
21) As an Agricultural Company, I want to be able to see my personal information
22) As the Administrator of the site, I want to be able to login in the site, so that i can work on it
23) As the Administrator of the site, I want to be able to not put my Credentials in the site every time a reload the site, so that I can use the site easily
24) As the Administrator of the site, I want to be able to logout from the site
25) As the Administrator of the site, I want to be able to delete malevolus user, so that the platform is reliable
26) As the Administrator of the site, I want to be able to see all orders, so that I can better analyze how the platform is doing
27) As the Administrator of the site, I want to be able to see all users, so that I can better analyze how the platform is doing
28) As the Administrator of the site, I want to be able to get user details, so that I can have some clear view over the user
29) As the Administrator of the site, I want to be able to modify users, so that I can do site maintenance
30) As the Administrator of the site, I want to be able to see all areas, so that I can do site maintenance
31) As the Administrator of the site, I want to be able to get all Agricultural company by areas, so that I can analyze how they are distributed
32) As the Administrator of the site, I want to be able to get all Agricultural company details, so that I can do platform maintenance
33) As the Administrator of the site, I want to be able to get product by seller, so that I can do site maintenance
34) As the Administrator of the site, I want to be able to get product by id, so that I can do site maintenance
35) As the Administrator of the site, I want to be able to add an area, so that if there is some request for that area to be added, I can
36) As the Administrator of the site, I want to be able to add product, so that I can do site maintenance
37) As the Administrator of the site, I want to be able to modify product, so that I can do site maintenance
38) As the Administrator of the site, I want to be able to delete product, so that I can do site maintenance


# CONTAINERS:

## CONTAINER_NAME: Authentication

### DESCRIPTION: 
Manages all functionalities related to registration, login, session persistence, and logout for clients, agricultural companies, and the site administrator.

### USER STORIES:
1) As a Client , I want to be able to Register in the site so that I can use the site

2) As a Client , I want to be able to login in the site so that I can use the site

3) As a Client , I want to be able to not put my Credentials in the site every time a reload the site, so that I can use the site

17) As an Agricultural Company, I want to be able to Registrer in the site, so that i can be visible and start doing business in the site

18) As an Agricultural Company, I want to be able to Login, so that i can work on the site

19) As an Agricultural Company, I want to be able to ot put my Credentials in the site every time a reload the site, so that I can use the site easily

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


## CONTAINER_NAME: Client-BE

### DESCRIPTION: 
Handles operations related to user and company profiles, viewing personal information, and storing profile related data.

### USER STORIES:
5) As a Client , I want to be able to see my personal information

27) As the Administrator of the site, I want to be able to see all users, so that I can better analyze how the platform is doing

28) As the Administrator of the site, I want to be able to get user details, so that I can have some clear view over the user

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

#### MICROSERVICE: mysql-client
- TYPE: database
- DESCRIPTION: Manages persistent storage of user data.
- PORTS: 3306


## CONTAINER_NAME: Farmer-BE

### DESCRIPTION: 
Manages operations regarding products, including adding, modifying, and removing products. This service caters to agricultural companies and the administrator.

### USER STORIES:
7) As a Client , I want to be able to See Agricultural company in my area, so that i can choose where to buy products

8) As a Client , I want to be able to See The products for each Agricultural company, so that I can buy From them
6) As a Client , I want to be able to See Hot products, so that i can discover the product in the season
7) As a Client , I want to be able to See Agricultural company in my area, so that i can choose where to buy products
8) As a Client , I want to be able to See The products for each Agricultural company, so that I can buy From them
14) As an Agricultural Company, I want to be able to Add products in inventory, so that I can show my clients the new produce
15) As an Agricultural Company, I want to be able to Remove products in inventory, so that My clients don't try to buy an item that I don't have anymore
16) As an Agricultural Company, I want to be able to Modify products in inventory, so that I can change price and quantities on the same item
17) As an Agricultural Company, I want to be able to Registrer in the site, so that i can be visible and start doing business in the site
18) As an Agricultural Company, I want to be able to Login, so that i can work on the site
30) As the Administrator of the site, I want to be able to see all areas, so that I can do site maintenance
31) As the Administrator of the site, I want to be able to get all Agricultural company by areas, so that I can analyze how they are distributed
32) As the Administrator of the site, I want to be able to get all Agricultural company details, so that I can do platform maintenance
33) As the Administrator of the site, I want to be able to get product by seller, so that I can do site maintenance
34) As the Administrator of the site, I want to be able to get product by id, so that I can do site maintenance
35) As the Administrator of the site, I want to be able to add an area, so that if there is some request for that area to be added, I can
36) As the Administrator of the site, I want to be able to add product, so that I can do site maintenance
37) As the Administrator of the site, I want to be able to modify product, so that I can do site maintenance
38) As the Administrator of the site, I want to be able to delete product, so that I can do site maintenance

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

#### MICROSERVICE: mysql-farmer
- TYPE: database
- DESCRIPTION: Stores all relevant data concerning the products, agricultural companies and areas.
- PORTS: 3306

## CONTAINER_NAME: Order-BE

### DESCRIPTION: 
Manages order placements, including completing orders and arranging pickup dates, as well as viewing all orders for site administration.

### USER_STORIES:
12) As a Client , I want to be able to Complete an order, so that I can choose a date to go and pick up the products
26) As the Administrator of the site, I want to be able to see all orders, so that I can better analyze how the platform is doing

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

#### MICROSERVICE: mysql-order
- TYPE: database
- DESCRIPTION: Manages the persistent storage and retrieval of order data, including order IDs, consumer details, products ordered, quantities, prices, and pickup dates. 
- PORTS: 3306


## CONTAINER_NAME: Client-FE

### USER STORIES:
13) As a Client , I want to be able to Open in google Maps the Location of the Agricultural company, so that i can find directions to it easly
8) As a Client , I want to be able to Add to cart the products, so that i can buy them
9) As a Client, I want to be able to Remove products to the cart, so that i can decide what to buy
10) As a Client , I want to be able to see product in the cart, so that i can see want I am going to buy

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
- TYPE: frontend
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
