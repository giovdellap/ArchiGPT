# SYSTEM DESCRIPTION:

CDC shop is a distributed system that handles the management, the payment and the shipment of products: in particular for materials that you can usually find in an hardware store.

# USER STORIES:

1. As a Customer, I want to be able to register in order to have an account 
2. As a Customer, I want to be able to login
3. As a Customer, I want to be able to have access to my profile
4. As a Customer, I want to see my current shopping cart
5. As a Customer, I want to order the products for ascending/descending price
6. As a Customer, I want to retrieve a product by giving its name in a search bar
7. As a Customer, I want to pay for the current shopping cart
8. As a Customer, I want to logout from the profile by clicking on a button
9. As a Customer, I want to check my order to see informations about my package
10. As a Customer, I want to access the about us page
11. As a Customer, I want to see the list of products
12. As a Customer, I want to see the details of a product
13. As a Merchant, I want to logout from the profile by clicking on a button
14. As an Merchant, I want to be able to login to the Merchant page
15. As an Merchant, I want to change the price of a product
16. As an Merchant, I want to insert/remove products
17. As an Merchant, I want to order the products for ascending/descending price
18. As an Merchant, I want to retrieve a product by giving its name in a search bar
19. As an Merchant, I want to access to my profile
20. As an Merchant, I want to access the about us page
21. As an Merchant, I want to see the list of products
22. As an Merchant, I want to see the details of a product
23. As a Customer, I want to contact the shop for any doubt


# CONTAINERS:

## CONTAINER_NAME: CDC

### DESCRIPTION: 
Manages Customer and Merchant Authentication, products and orders

### USER STORIES:
1. As a Customer, I want to be able to register in order to have an account 
2. As a Customer, I want to be able to login
3. As a Customer, I want to be able to have access to my profile
4. As a Customer, I want to see my current shopping cart
5. As a Customer, I want to order the products for ascending/descending price
6. As a Customer, I want to retrieve a product by giving its name in a search bar
7. As a Customer, I want to pay for the current shopping cart
8. As a Customer, I want to logout from the profile by clicking on a button
9. As a Customer, I want to check my order to see informations about my package
10. As a Customer, I want to access the about us page
11. As a Customer, I want to see the list of products
12. As a Customer, I want to see the details of a product
13. As a Merchant, I want to logout from the profile by clicking on a button
14. As an Merchant, I want to be able to login to the Merchant page
15. As an Merchant, I want to change the price of a product
16. As an Merchant, I want to insert/remove products
17. As an Merchant, I want to order the products for ascending/descending price
18. As an Merchant, I want to retrieve a product by giving its name in a search bar
19. As an Merchant, I want to access to my profile
20. As an Merchant, I want to access the about us page
21. As an Merchant, I want to see the list of products
22. As an Merchant, I want to see the details of a product
23. As a Customer, I want to contact the shop for any doubt

### PORTS: 8000:8003 - 32000 - 3000

### DESCRIPTION:
This container is designed to handle all core functionalities related to user accounts, products and order features within the OneSport platform.

### PERSISTANCE EVALUATION:
The container handles persistent and accessible storage for users, products and order features data. 

### EXTERNAL SERVICES CONNECTIONS:
The container does not connect to external services.

### MICROSERVICES:

#### MICROSERVICE: warehouse_api
- TYPE: backend
- DESCRIPTION: Manages product related features like insertion, modification, deletion and queries.
- PORTS: 8000

#### MICROSERVICE: catalog_api
- TYPE: backend
- DESCRIPTION: Manages cart-related features like product insertion in the cart and product deletion from the cart.
- PORTS: 8001

#### MICROSERVICE: purchase_refund_api
- TYPE: backend
- DESCRIPTION: Handles the order checkout.
- PORTS: 8002

#### MICROSERVICE: auth_api
- TYPE: backend
- DESCRIPTION: Handles authentication for customers and merchants.
- PORTS: 8003

#### MICROSERVICE: db
- TYPE: database
- DESCRIPTION: Stores data for users, products, carts and orders features.
- PORTS: 32000

#### MICROSERVICE: frontend
- TYPE: frontend
- DESCRIPTION: Serves the web app to interact with the system.
- PORTS: 3000