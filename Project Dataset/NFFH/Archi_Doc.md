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
4) As a Client , I want to be able to logout, so that no one else use my account
17) As an Agricultural Company, I want to be able to Registrer in the site, so that i can be visible and start doing business in the site
18) As an Agricultural Company, I want to be able to Login, so that i can work on the site
19) As an Agricultural Company, I want to be able to ot put my Credentials in the site every time a reload the site, so that I can use the site easily
20) As an Agricultural Company, I want to be able to logout from the site
22) As the Administrator of the site, I want to be able to login in the site, so that i can work on it
23) As the Administrator of the site, I want to be able to not put my Credentials in the site every time a reload the site, so that I can use the site easily
24) As the Administrator of the site, I want to be able to logout from the site

### PORTS: 
10000:10100

### DESCRIPTION:
The Authentication container is responsible for managing all the security-related functionalities that involve registration, login, session persistence, and logout processes for various types of users, including clients, agricultural companies, and the site administrator. This includes handling user authentication to ensure that each user can securely access and interact with the site according to their privileges and ensuring that user sessions are maintained properly across site visits. Additionally, it provides mechanisms for these users to securely log out, thus helping prevent unauthorized access to the accounts.

### PERSISTANCE EVALUATION:
The Authentication container crucially requires data persistence primarily to manage user credentials and session information for different types of stakeholders such as clients, agricultural companies, and the site administrator. Specifically, it must securely store credentials such as usernames and passwords, session tokens, and possibly security-related audit logs to track login attempts and modifications. This persistent data storage ensures that users do not need to reauthenticate every time they access the system but instead rely on session persistence mechanisms, enhancing user experience and system security.

### EXTERNAL SERVICES CONNECTIONS:
Based on the provided user stories and the container's purpose, there is no apparent requirement for this container to connect to external services directly. Its primary role is focused on authentication, session management, and security, which involves internal data handling and interactions with other internal system components rather than external entities. The functionalities described do not suggest a dependency on external data sources or services; hence, external connections for this container are likely unnecessary.

### MICROSERVICES:

#### MICROSERVICE: auth_service
- TYPE: backend
- DESCRIPTION: Manages the core authentication processes, including user registration, login, session maintenance, and logouts for clients, agricultural companies, and the site administrator. This service ensures that credentials are correctly verified during login and that sessions are maintained securely.
- PORTS: 10010

#### MICROSERVICE: session_manager
- TYPE: backend
- DESCRIPTION: Handles session persistence and state management across site visits, utilizing tokens to ensure users remain logged in without re-entering credentials continually. It interacts with the user database to validate session tokens and manage session data.
- PORTS: 10020

#### MICROSERVICE: credentials_db
- TYPE: database
- DESCRIPTION: Stores and manages user credentials, including usernames and passwords, along with session tokens. This database is crucial for the authentication process and session management, providing quick data access for verification and security checks.
- PORTS: 10030

## CONTAINER_NAME: User_Profile

### DESCRIPTION: 
Handles operations related to user and company profiles, viewing personal information, and storing profile related data.

### USER STORIES:
5) As a Client , I want to be able to see my personal information
21) As an Agricultural Company, I want to be able to see my personal information
27) As the Administrator of the site, I want to be able to see all users, so that I can better analyze how the platform is doing
28) As the Administrator of the site, I want to be able to get user details, so that I can have some clear view over the user

### PORTS:
10200:10300

### DESCRIPTION:
The User_Profile container handles operations related to the management of user and company profiles on the "Not Far(m) From Home" platform. Its main responsibilities include displaying personal information for clients, agricultural companies, and the site administrator. It also facilitates the retrieval of user details for administrative oversight and analytics. This container serves as a central point for storing and managing profile-related data, ensuring that user information is accessible and up-to-date for smooth platform operation.

### PERSISTANCE EVALUATION
The User_Profile container requires persistent storage to maintain details about the users and company profiles. It needs to store user-specific information, such as personal data and any associated preferences or settings attributed to that user. This data includes names, contact details, and possibly preferences regarding the interaction with the platform. Additionally, for administrative purposes, the ability to retrieve and analyze user data suggests that information needs to be stored in a secure and retrievable format, likely in a database. This persistence ensures consistency and provides the necessary information when performing profile-related operations such as viewing or updating personal information.

### EXTERNAL SERVICES CONNECTIONS
The User_Profile container does not clearly indicate a requirement for connections to external services based on the user stories and responsibilities described. All functionalities—viewing personal information, retrieving user details for administrative purposes—are internal to the platform and deal with data handled and stored within the container’s own database or connected internal services. No external data sources or services like social media integration or third-party data validation tools are mentioned in the context given. Therefore, it likely does not require integration with external services for its primary functionality.

### MICROSERVICES:

#### MICROSERVICE: profile_management
- TYPE: backend
- DESCRIPTION: Handles the creation, viewing, and updating of personal information for users, including clients and agricultural companies. It also enables administrators to view and retrieve detailed user profiles to analyze platform activity and manage user data.
- PORTS: 10200

#### MICROSERVICE: profile_storage
- TYPE: database
- DESCRIPTION: Manages persistent storage of user data, including names, contact details, preferences, and administrative oversight data. This database is crucial for ensuring that profiles are consistent, up-to-date, and accessible for viewing or editing.
- PORTS: 10220


## CONTAINER_NAME: Product_Management

### DESCRIPTION: 
Manages operations regarding products, including adding, modifying, and removing products. This service caters to agricultural companies and the administrator.

### USER STORIES:
14) As an Agricultural Company, I want to be able to Add products in inventory, so that I can show my clients the new produce
15) As an Agricultural Company, I want to be able to Remove products in inventory, so that My clients don't try to buy an item that I don't have anymore
16) As an Agricultural Company, I want to be able to Modify products in inventory, so that I can change price and quantities on the same item
36) As the Administrator of the site, I want to be able to add product, so that I can do site maintenance
37) As the Administrator of the site, I want to be able to modify product, so that I can do site maintenance
38) As the Administrator of the site, I want to be able to delete product, so that I can do site maintenance
33) As the Administrator of the site, I want to be able to get product by seller, so that I can do site maintenance
34) As the Administrator of the site, I want to be able to get product by id, so that I can do site maintenance

### PORTS: 
10400:10500

### DESCRIPTION:
The Product_Management container is designed to manage all operations related to products on the "Not Far(m) From Home" platform. Its primary responsibilities include adding new products to the inventory, modifying details of existing products, and removing products that are no longer available. This functionality is crucial for agricultural companies using the platform, allowing them to update their product offerings in real time, adjust prices, change quantities, or completely remove offerings as needed.
Additionally, the container also provides essential capabilities for the site administrator to maintain the site's integrity and up-to-dateness. Administrative functions include adding, modifying, or deleting products as part of broader site maintenance tasks, as well as retrieving product details either by seller or by product ID. This ensures that the administrator can efficiently manage product listings on the platform and perform necessary updates or corrections.
Through these features, the Product_Management container supports the dynamic and variable nature of agricultural products and their availability, making the platform a reliable and current marketplace for both farmers and consumers.

### PERSISTANCE EVALUATION
The Product_Management container requires persistent storage to manage the product information effectively. To implement the user stories such as adding, modifying, and removing products, this container should maintain a database of products that includes details like product name, quantity, price, agricultural company ID, and product descriptions. As the site needs current and accurate information about product availability and details, perpetual storage becomes essential to avoid loss of data and to serve up-to-date information to the clients and administrators.

### EXTERNAL SERVICES CONNECTIONS
Based on the provided user stories and the container's description, there is no apparent requirement for the Product_Management container to connect to external services. All functionalities (adding, modifying, deleting products, and retrieving product details) are managed internally within the platform and rely on the internal database without the need for external data inputs or third-party integrations. This maintains operational simplicity and minimizes dependencies on external service providers for this particular container.

### MICROSERVICES:

#### MICROSERVICE: product_manager
- TYPE: backend
- DESCRIPTION: Manages all backend functionalities such as adding, modifying, and removing products from the inventory. It ensures the operations are reflected in real-time on the database, supporting direct updates by agricultural companies and administrative functionalities for the site administrator.
- PORTS: 10400

#### MICROSERVICE: product_database
- TYPE: database
- DESCRIPTION: Stores all relevant data concerning the products, including details like product names, quantities, prices, descriptions, and agricultural company IDs. This database ensures that product information is kept up-to-date and is persistent to support accurate display and management of inventory.
- PORTS: 10410


## CONTAINER_NAME: Storefront
  
### DESCRIPTION: 
Handles the end-user interface for showing products by agricultural companies and the hot products, managing shopping cart operations.

### USER STORIES:
6) As a Client , I want to be able to Hot products, so that i can discover the product in the season
7) As a Client , I want to be able to See The products for each Agricultural company, so that I can buy From them
8) As a Client , I want to be able to Add to cart the products, so that i can buy them
9) As a Client, I want to be able to Remove products to the cart, so that i can decide what to buy
10) As a Client , I want to be able to see product in the cart, so that i can see want I am going to buy

### PORTS: 
10600:10700

### DESCRIPTION:
The Storefront container is primarily responsible for managing the front-end user interactions related to product showcasing and purchase on the Not Far(m) From Home platform. This includes displaying products from various agricultural companies and seasonal or hot products, managing the shopping cart functionalities like adding and removing products, and providing a comprehensive view of products in the shopping cart. Designed to enhance user experience, it ensures clients can easily navigate through different product offerings, make informed purchase decisions, and handle their transactions efficiently within the platform.

### PERSISTANCE EVALEVALUATION
The Storefront container needs to manage transient data related to users' interactions with the shopping cart, including items currently being considered for purchase. Although this can be temporarily stored in session storage for the duration of the user's activity, it would require a connection to a more persistent storage system if details of the shopping cart need to be retrieved after session inactivity or maintained across sessions. This persistent storage would likely store users’ cart contents and states, ensuring they can return to a pre-populated cart even after leaving the site.

### EXTERNAL SERVICES SERVICES CONNECTIONS
The Storefront container requires data about products, which includes descriptions, prices, and availability, necessitating connections to internal microservices, specifically the Product Management container. However, depending entirely on the container’s purpose and user stories, it does not need connections to external APIs or services beyond those provided internally by the described system architecture. All interactions appear to be managed via intra-system communications with other containers like Product Management for retrieving product details and Order Management for initializing the checkout processes.

### MICROSERVICES:

#### MICROSERVICE: frontend_ui
- TYPE: frontend
- DESCRIPTION: This microservice serves the main user interface of the Storefront container. It handles displaying available products, hot products, and product details from different agricultural companies. It integrates with the Product Management container to fetch product data. It allows clients to navigate through these products, add items to their shopping cart, view the cart, and initiate checkout processes.
- PORTS: 10600

#### MICROSENVICE: cart_management
- TYPE: backend
- DESCRIPTION: Manages all backend operations related to the shopping cart functionalities. This includes adding items to the cart, removing items from the cart, and maintaining the state of the shopping cart across sessions. Interacts with session storage or a persistent store to save and retrieve the cart's state, ensuring that users can return to their previous cart state even after a session has expired or they leave and return to the site.
- PORTS: 10610

#### MICROSERVICE: checkout_initiation
- TYPE: backend
- DESCRIPTION: Handles the initiation of the checkout process. This microservice provides the functionality to transition from a populated shopping cart to the checkout phase. It interacts with the Order Management container to pass along the necessary information for completing the order placement, including selection of pick-up dates and finalizing transaction details.
- PORTS: 10620


## CONTAINER_NAME: Order_Management

### DESCRIPTION: 
Manages order placements, including completing orders and arranging pickup dates, as well as viewing all orders for site administration.

### USER_STORIES:
12) As a Client , I want to be able to Complete an order, so that I can choose a date to go and pick up the products
26) As the Administrator of the site, I want to be able to see all orders, so that I can better analyze how the platform is doing

### PORTS: 
10800:10900

### DESCRIPTION:
The Order_Management container is dedicated to managing all aspects of order processing within the "Not Far(m) From Home" platform. This includes facilitating the completion of orders by clients—allowing them to select dates for product pickup directly from local farmers. Additionally, it provides capabilities for the site administrator to view all processed orders, offering insights and oversight to ensure smooth operation and fulfillment. Thus, this container is pivotal in bridging the transactional activities between consumers and agricultural producers, ensuring that orders are placed, managed, and monitored effectively.

### PERSISTANCE EVALUATION
The Order_Management container requires persistent storage to maintain and manage the details of each order. This includes storing information such as the order IDs, consumer details, products ordered, quantities, prices, and the selected pickup dates. Tracking and recording this data is essential for facilitating order fulfillment and providing visibility to administrators for monitoring and analytics purposes.

### EXTERNAL SERVICES CONNECTIONS
The Order_Management container does not need to connect to any external services if all necessary data for processing orders (like product details and availability) are provided internally from other containers such as Product_Management. However, if integration with payment gateways or external accounting services is required to facilitate transactions or financial reporting, external service connections might be necessary.

### MICROSERVICES:

#### MICROSERVICE: order_processor
- TYPE: backend
- DESCRIPTION: This microservice is responsible for handling the logic and operations related to order placement by clients. It allows clients to complete orders, select pickup dates, and ensures all necessary details are preserved for each transaction. It also communicates with the Product_Management container to check product availability.
- PORTS: 10800

#### MICROSERVICE: order_viewer
- TYPE: backend
- DESCRIPTION: This microservice is designed for the site's administrator, providing the capability to view and manage all orders. It helps in analyzing order patterns, monitoring order status, and ensuring order fulfillment across the platform. It fetches and displays order details as per administrative queries.
- PORTS: 10810

#### MICROSERVICE: order_storage
- TYPE: database
- DESCRIPTION: Manages the persistent storage and retrieval of order data, including order IDs, consumer details, products ordered, quantities, prices, and pickup dates. It ensures data integrity and provides support for transactional operations handled by other microservices within the same container.
- PORTS: 10820


## CONTAINER_NAME: Geographic_Services

### DESCRIPTION: 
Manages location-based services, such as finding nearby agricultural companies, opening their locations in Google Maps, and administrative functionality for geographic data management.

### USER_STORIES:
7) As a Client , I want to be able to See Agricultural company in my area, so that i can choose where to buy products
13) As a Client , I want to be able to Open in google Maps the Location of the Agricultural company, so that i can find directions to it easily
30) As the Administrator of the site, I want to be able to see all areas, so that I can do site maintenance
31) As the Administrator of the site, I want to be able to get all Agricultural company by areas, so that I can analyze how they are distributed
32) As the Administrator of the site, I want to be able to get all Agricultural company details, so that I can do platform maintenance
35) As the Administrator of the site, I want to be able to add an area, so that if there is some request for that area to be added, I can

### PORTS:
11000:11100

### DESCRIPTION:
The Geographic_Services container is responsible for managing location-based services essential for the platform "Not Far(m) From Home". Its primary role is to facilitate the interaction between clients and agricultural companies based on geographical proximity. This includes enabling clients to identify nearby agricultural companies and providing easy access to navigation via integration with Google Maps. For administrators, it offers functionalities to oversee and manage the geographical data to ensure accurate and efficient service delivery, such as adding new areas or analyzing the distribution of agricultural companies within different regions. This container ensures that geographic information is handled effectively, enhancing the user experience by simplifying the process of finding, navigating to, and buying from local agricultural producers.

### PERSISTANCE EVALUATION
The Geographic_Services container requires the storage of geographic data and locations to effectively manage location-based services. This includes storing details about each agricultural company's location, geographic areas of operation, and possibly caching map-related data to improve performance. Storing this data ensures that the system can quickly retrieve and display relevant agricultural companies near the client's location and manage administrative tasks such as analyzing the distribution of companies in different areas.

### EXTERNAL SERVICES CONNECTIONS
The Geographic_Services container needs to connect to external services to fulfill its user stories. Specifically, it integrates with Google Maps API for functionalities such as opening the location of agricultural companies in Google Maps to help clients find directions. This external connection is crucial for providing dynamic map-based interactions and real-time navigational assistance to the users. Additionally, the capability to interact with such external mapping services is fundamental for maintaining the usability and relevance of the geographical information displayed to clients and administrators

### MICROSERVICES:

#### MICROSERVICE: location_finder
- TYPE: backend
- DESCRIPTION: This microservice enables clients to find agricultural companies in their vicinity. It accesses stored geographic data and employs algorithms to calculate distances between client locations and agricultural companies, recommending the closest options.
- PORTS: 11010

#### MICROSERVICE: map_integration
- TYPE: backend
- DESCRIPTION: Integrates with external Google Maps API to provide mapping and navigation functionalities. It handles requests from clients to open the location of agricultural companies in Google Maps, facilitating directions.
- PORTS: 11020

#### MICROSERVICE: geo_data_management
- TYPE: backend
- DESCRIPTION: Manages all administrative tasks related to geographic data. This includes adding new geographical areas into the system, editing data about agricultural companies’ locations, and analyzing geographical distribution of agricultural companies for administrative purposes.
- PORTS: 11030

#### MICROSERVICE: geo_cache
- TYPE: other
- DESCRIPTION: Handles caching of frequently accessed geographical and map data to enhance performance during user interactions. Ensures faster retrieval of data, reducing latency and improving user experience.
- PORTS: 11040


## CONTAINER_NAME: Administration

### DESCRIPTION: Provides administrative control features including user and product modification, and deletion of malicious users.

### USER-STORIES:
25) As the Administrator of the site, I want to be able to delete malevolus user, so that the platform is reliable
29) As the Administrator of the site, I want to be able to modify users, so that I can do site maintenance

### PORTS: 
11200:11300

### DESCRIPTION:
The Administration container serves as a control hub for managing and maintaining the integrity and data quality of the platform "Not Far(m) From Home". It allows the site administrator to handle critical administrative tasks such as modifying user profiles and deleting malicious users. This ensures that the platform remains secure, trustworthy, and efficient in its operation, facilitating a healthy environment for both clients and agricultural companies involved.

### PERSISTANCE EVALIGATION
Given the container "Administration" is designed to handle administrative tasks like managing user profiles and deleting malicious users, it indeed requires persistent storage to maintain records that track user status and modifications. This data might include logs of user activities, timestamps of modifications, details of the actions taken by the administrator, and the status of user profiles before and after modifications.

### EXTERNAL SERVICES CONNECTIONS
The Administration container, owing to its role, does not inherently require connections to external services for its primary administrative functionalities. However, for enhanced security features like IP address tracking or the use of third-party audit logging services, it might optionally integrate with external security or monitoring services to further solidify platform integrity and track malicious activities more effectively.

### MICROSERVICES:

#### MICROSERVICE: admin_management
- TYPE: backend
- DESCRIPTION: Handles crucial administrative tasks such as modifying user profiles and deleting malicious users. It provides a secure interface for the site administrator to perform various functions to maintain the integrity of the platform by ensuring data quality and security.
- PORTS: 11200

#### MICROSERVICE: user_audit_logging
- TYPE: backend
- DESCRIPTION: Manages the recording of all administrative actions including profile modifications and deletions. This microservice is responsible for creating a secure audit trail that logs timestamps, user activities, and admin actions for compliance and security analysis.
- PORTS: 11210

#### MICROSERVICE: external_security_integration
- TYPE: other
- DESCRIPTION: Optionally integrates with external security services for enhanced security measures such as IP address tracking or third-party audit logs. This microservice would act as a bridge to external APIs to enhance the security features of the Administration container.
- PORTS: 11220
