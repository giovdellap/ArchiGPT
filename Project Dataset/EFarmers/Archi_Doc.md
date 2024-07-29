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

## CONTAINER_NAME: User_Authentication

### DESCRIPTION:  
Manages user registration, login, logout, and session management. Handles both email and OAuth sign-ups.

### USER STORIES: 
1) As a visitor, I want to register to the website with an email
3) As a user, I want to reach a login page so that I can login into my account
2) As a user, I want to logout from my account
29) As a visitor, I want to register to the website with OAuth

### PORTS: 
10000:10100

## CONTAINER_NAME: User_Interface

### DESCRIPTION:  
Provides interfaces for home, personal, farmer, and rider pages allowing users to interact with various functionalities.

### USER STORIES: 
5) As a user, I want to access the home page
6) As a user, I want to access my personal page, so I can see my informations
13) As a user, I want to access a farmer page, so I can see his information
25) As a rider, I want to have a personal delivery page

### PORTS: 
10101:10200

## CONTAINER_NAME: Product_Management

### DESCRIPTION:  
Handles publishing, modifying, searching, and deleting of product insertions by the farmers.

### USER STORIES: 
4) As a farmer, I want to publish a new insertion
8) As a user or visitor, I can search for farmer's insertions
9) As the owner of an insertion, I want to delete one of my insertions
10) As a farmer, I want to modify my insertions

### PORTS: 
10201:10300

## CONTAINER_NAME: Cart_Management

### DESCRIPTION: 
Manages the shopping cart including adding, deleting, and purchasing products.

### USER STORIES: 
14) As a customer, I want to add a product in my cart, so that I buy this product
15) As a customer, I want to delete the boxes added to the shopping cart
16) As a customer, I want to buy boxes added to the shopping cart
17) As a customer, I want to be able to checkout and pay for my order using a secure payment gateway.

### PORTS: 
10301:10400

## CONTAINER_NAME: Subscription_and_Notifications

### DESCRIPTION:
Manages customer subscriptions to farmers and notifies customers of any new updates.

### USER STORIES: 
19) As a customer, I can subscribe to a farmer, so I will be notify for his every new insertion
20) As a customer, I want to have a list of the farmers that I am subscribed to 

### PORTS: 
10401:10500

## CONTAINER_NAME: Rider_Management

### DESCRIPTION:
Handles rider specific tasks such as updating availability and receiving delivery requests.

### USER STORIES: 
24) As a customer, I want to update my status to Rider, so that I can deliver stuffs.
26) As a rider, I want to change my availability status to available so that I can receive delivery requests

### PORTS: 
10501:10600

## CONTAINER_NAME: Delivery_Options

### DESCRIPTION:
Manages delivery options and interactions between customers and riders.

### USER STORIES: 
27) As a customer, I want to select a delivery option, so that I can receive the products at home

### PORTS: 
10601:10700

## CONTAINER_NAME: Frontend

### DESCRIPTION:
Handles the frontend exposure to the user, providing the visual interface for deploying the e-commerce website.

### USER STORIES: 

### PORTS: 
12000:12100

UNASSIGNED:
    - 7) As a customer, I can check on calendar all seasonal foodstuff 
    - 11) As a user, I want to see the expiring boxes
    - 12) As a customer, I want to update my status to Farmer
    - 18) As a customer, I want to book a product from the calendar
    - 21) As a farmer, I want to accept incoming requests for booked boxes
    - 22) As a customer, I want to check my order history, so I can remember past purchases
    - 23) As a user that bought a box, I want to leave a comment to the farmer
    - 28) As a farmer, I want to achieve a badge




### DESCRIPTION:


### PERSISTANCE EVALUATION


### EXTERNAL SERVICES CONNECTIONS


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 


