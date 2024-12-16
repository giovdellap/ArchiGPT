(EFarmersV3)
# SYSTEM DESCRIPTION:

E-Farmers is an e-commerce website that aims to connect local farmers with customers who are interested in buying fresh and locally sourced products. Our platform allows farmers to showcase their products by publishing insertions, while customers can easily browse and purchase these goods. Whether you are a farmer looking to sell your products or a customers seeking high-quality ”farm-to-table” items, E-Farmers has something for everyone. Moreover, if you cannot reach the farmer’s warehouse yourself, you can rely on riders who can ship the products to your home

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
16) As a customer. I want to generate an order which contains items from my shopping cart 
17) As a customer, I want to be able to checkout and pay for my order using a secure payment gateway. 
18) As a customer, I want to book a product from the calendar
19) As a customer, I can subscribe to a farmer, so I will be notify for his every new insertion
20) As a customer, I want to have a list of the farmers that I am subscribed to 
21) As a farmer, I want to accept or decline incoming requests for booked boxes
22) As a customer, I want to check my order history, so I can remember past purchases 
23) As a user that bought a box, I want to leave a comment to the farmer
24) As a customer, I want to update my status to Rider, so that I can deliver stuffs. 
25) As a rider, I want to have a personal delivery page 
26) As a rider, I want to change my availability status to available so that I can receive delivery requests 
27) As a customer, I want to select a delivery option, so that I can receive the products at home 
28) As a farmer, I want to earn badges so that my achievements are showcased on my profile page
29) As a visitor, I want to register to the website with OAuth
30) As a user, I want to be able to modify my informations
31) As a the owner of an insertion, I want to be able to add boxes on my insertion
32) As a user, I want to see the status of my requests for booked boxes
33) As a user, I want to see my cart, so I can see all the products I am about to purchase
34) As a rider, I want to be able to update the status of an order that i have delivered
35) As a customer, I want to be able to unsubscribe from a farmer, so that I no longer receive notifications for his insertions
36) As a customer, I want to have a notification center, so I can read my notifications


# CONTAINERS:

## CONTAINER NAME: User_Management

### DESCRIPTION: 
Manages user accounts, including registration (both email and OAuth), login, logout, session management, and updating user roles (such as customer to farmer, or customer to rider).

### USER STORIES:
1) As a visitor, I want to register to the website with an email
2) As a user, I want to logout from my account
3) As a user, I want to reach a login page so that I can login into my account
12) As a customer, I want to update my status to Farmer
24) As a customer, I want to update my status to Rider, so that I can deliver stuffs
29) As a visitor, I want to register to the website with OAuth

### PORTS: 
12000:12099

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

## CONTAINER NAME: Product_Inventory

### DESCRIPTION: 
Handles the creation, modification, and deletion of product listings or insertions, managing seasonal availability via a calendar, and maintaining details of expiring products.

### USER STORIES:
4) As a farmer, I want to publish a new insertion
7) As a customer, I can check on a calendar all seasonal foodstuff
9) As the owner of an insertion, I want to delete one of my insertions
10) As a farmer, I want to modify my insertions
11) As a user, I want to see the expiring boxes
18) As a customer, I want to book a product from the calendar
21) As a farmer, I want to accept incoming requests for booked boxes

### PORTS: 
12100:12199

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

## CONTAINER NAME: Commerce

### DESCRIPTION: 
Manages shopping cart functions, including adding, modifying, and removing items, as well as processing secure payments and handling delivery options.

### USER STORIES:
14) As a customer, I want to add a product to my cart, so that I can buy this product
15) As a customer, I want to delete the boxes added to the shopping cart
16) As a customer, I want to buy boxes added to the shopping cart
17) As a customer, I want to be able to checkout and pay for my order using a secure payment gateway
27) As a customer, I want to select a delivery option, so that I can receive the products at home
    
### PORTS: 
12200:12299

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

## CONTAINER NAME: User_Interaction
    
### DESCRIPTION: 
Manages user-specific pages, including individual user account and farmer profiles, user subscriptions to farmers, and user feedback mechanisms like comments and reviews.
   
### USER STORIES:
5) As a user, I want to access the home page
6) As a user, I want to access my personal page, so that I can see my information
13) As a user, I want to access a farmer page, so I can see his information
19) As a customer, I can subscribe to a farmer, so I will be notified of every new insertion
20) As a customer, I want to have a list of the farmers that I am subscribed to
23) As a user that bought a box, I want to leave a comment to the farmer
    
### PORTS: 
12300:12399

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

## CONTAINER NAME: Rider_Portal

### DESCRIPTION: 
Manages functionalities specific to riders, including a personal delivery management page and setting availability status.
    
### USER STORIES:
25) As a rider, I want to have a personal delivery page
26) As a rider, I want to change my availability status to available so that I can receive delivery requests
    
### PORTS: 
12400:12499

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

## CONTAINER NAME: Purchase_Review

### DESCRIPTION:
Manages the order histories and achievements system, including viewing past purchases and allocation of badges to farmers.
    
### USER STORIES:
22) As a customer, I want to check my order history, so I can remember past purchases
28) As a farmer, I want to achieve a badge
    
### PORTS: 
12500:12599

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

## CONTAINER NAME: Web_Interface

### DESCRIPTION: 
Serves the E-Farmers' web interface, handling all client-side interactive functions and rendering for user browsers.

### PORTS: 
12600:12699

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

# UNASSIGNED: 
8) As a user or visitor, I can search for farmer's insertions