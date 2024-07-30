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

### PORTS: 
3000:8083

### DESCRIPTION:
This only EFarmers container is designed to handle all core functionalities related to user account, insertions, payments orders, shopping carts and subscriptions features within the EFarmers platform.

### PERSISTANCE EVALUATION
The container handles persistent and accessible storage for user, insertions, payments orders, shopping carts and subscriptions data. 

### EXTERNAL SERVICES CONNECTIONS
The container connects to external services for processing secure payments. In particular it uses Stripe library in Python, a popular payment processing platform that provides a suite of APIs and tools for handling online payments.

### MICROSERVICES:

#### MICROSERVICE: frontend
- TYPE: frontend
- DESCRIPTION: Manages user-specific pages and calendar features, including individual user account and farmer profiles, user subscriptions to farmers, and user feedback mechanisms like comments and reviews.
- PORTS: 3000

#### MICROSERVICE: user_service
- TYPE: backend
- DESCRIPTION: Manages user accounts, including registration (both email and OAuth), login, logout, session management, reviews, and updating user roles (such as customer to farmer, or customer to rider).
- PORTS: 8080

#### MICROSERVICE: insertions_service
- TYPE: backend
- DESCRIPTION: Handles the creation, modification, and deletion of insertions, managing boxes booking (send boxes booking request, retrieve boxes list and booking request), and maintaining details of expiring insertions.
- PORTS: 8081

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
- TYPE: other
- DESCRIPTION: The rabbitmq microservice hosts RabbitMQ, an open-source message broker software that implements the Advanced Message Queuing Protocol (AMQP). It is used to facilitate communication between different parts of a distributed system by sending and receiving messages, in this case between customers and farmers.
- PORTS: 5672