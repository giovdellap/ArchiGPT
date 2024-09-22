# SYSTEM DESCRIPTION:

RentYourExpert is a platform that connects individuals who require expert services with skilled professionals across various fields. Its main features are :
- User-friendly Interface: The app is designed to be easy to navigate, allowing users to quickly find the information they need.
- Search Functionality: Users can search for workers based on their skills and location.
- Secure User Authentication and Authorization: Robust measures ensure the security of user access.
- Worker Reviews: The platform displays worker reviews, helping clients make informed decisions when hiring a worker.
- Efficient Communication: A built-in Q&A system makes it easy for both parties to discuss project requirements and expectations.
- Easy-to-Use Profile Management: Workers can efficiently manage their profiles, projects, and communication with clients through an intuitive interface.

# USER STORIES:

1) As a customer, I want to be able to register, so that I can have an account 
2) As a customer, I want to be able to login, so that I can start using the app
3) As a customer, I want to be able to have access to my profile, so that I can look at my informations
4) As a customer, I want to be able to have access to the catalogue, so that I can see all available workers
5) As a customer, I want to be able to have access to the page of each single worker, so that I can have all informations related to him
6) As a customer, I want to be able send a request to a worker, so that I can rent his expertise
7) As a customer, I want to be able to a list of requests in my profile page, so that I can see all the requests that I did
8) As a customer, I want to have a specific button attached to my request, so that I can delete it
9) As a customer, I want to be able to see the questions made by other customers to a specific worker, so that I can have other informations related to him
10) As a customer, I want to be able to have a field of text in the worker's page, in order to be able to send questions to the expert
11) As a customer, I want to be able to leave a review, in order to evaluate the performance that I received
12) As a customer, I want to have specific buttons attached to the review that I posted, in order to update or delete it.
13) As a customer, I want to have a specific button in the pages of the application, that allow me to logout
14) As an admin, I want to be able to login, so that I can start the management of the e-commerce
15) As an admin, I want to be able to access to the customers list, so that I can see all the customers
16) As an admin, I want to have an add customer Button, so that I can generate new customers
17) As an admin, I want to be able to access to the profile page of each single customer, so that I can have informations related to that specific customer
18) As an admin, I want to have a delete customer button, so that I can delete that customer
19) As an admin, I want to be able to access to the workers list, so that I can see all the workers
20) As an admin, I want to be able to access to the profile page of each single worker, so that I can have informations related to that specific worker
21) As an admin, I want to be able to access to the requests page, so that I can see each request done so far
22) As an admin, I want to be able to add or delete a request, in order to be able to handle them
23) As a worker, I want to have a requests page, so that I can look at all my requests
24) As a worker, I want to manage requests, in order to be able to accept or reject them
25) As a worker, I want to be able to register, so that I can have an account 
26) As a worker, I want to be able to login, so that I can start using the app
27) As a worker, I want to be able to have access to my profile, so that I can look at my informations
28) As a worker, I want to be able to modify my informations
29) As an admin, I want to have an add worker Button, so that I can generate new workers
30) As an admin, I want to have a delete worker Button, so that I can delete that worker
31) As an admin, I want to be able to delete a review, in order to be able to handle them
32) As a customer, I want to be able to modify my informations
33) As a worker, I want to be able to answer to the questions made by the customers to me
34) As an admin, I want to be able to delete a question with his relative answers, in order to be able to handle them
35) As a customer, I want access to a worker's review section, so that I can read reviews left by other customers about the worker


# CONTAINERS:

## CONTAINER NAME: User_Auth

### DESCRIPTION: 
Manages user registrations, logins, and sessions ensuring secure access for customers and admin.

### USER STORIES: 
1) As a Customer, I want to be able to register, so that I can have an account 
2) As a Customer, I want to be able to login, so that I can start using the app
13) As a customer, I want to have a specific button in the pages of the application, that allow me to logout
14) As an admin, I want to be able to login, so that I can start the management of the e-commerce

### PORTS: 
10000:10100

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER NAME: Profile_Management

### DESCRIPTION: 
Allows users to manage and view their profiles, access personal information, and handle related user-specific data.

### USER STORIES: 
3) As a Customer, I want to be able to have access to my profile, so that I can look at my information
7) As a Customer, I want to be able to see a list of requests in my profile page, so that I can see all the requests that I did
15) As an admin, I want to be able to access the customers list, so that I can see all the customers
17) As an admin, I want to be able to access the profile page of each single customer, so that I can have information related to that specific customer
23) As a worker, I want to have a requests page, so that I can look at all my requests

### PORTS: 
10200:10300

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER NAME: Catalogue_Manager

### DESCRIPTION: 
Manages display and access of workers, allowing users to browse through various profiles and see detailed information, including reviews.

### USER STORIES:
4) As a Customer, I want to be able to have access to the catalogue, so that I can see all available workers
5) As a Customer, I want to be able to have access to the page of each single customer, so that I can have all information related to him
9) As a customer, I want to see the questions made by other customers to a specific worker, so that I can have other information related to him
10) As a customer, I want to be able to have a field of text in the worker's page, in order to be able to send questions to the expert

### PORTS: 
10400:10500

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER NAME: Request_Manager

### DESCRIPTION: 
Handles the creation, updating, and deletion of requests and reviews.

### USER STORIES:
6) As a customer, I want to be able send a request to a worker, so that I can rent his expertise
8) As a customer I want to have a specific button attached to my request, so that I can delete it
11) As a customer, I want to be able to leave a review, in order to evaluate the performance that I received
12) As a customer I want to have specific buttons attached to the review that I posted, in order to update or delete it
21) As an admin, I want to be able to access to the requests page, so that I can see each request done so far
22) As an admin, I want to be able to add or delete a request, in order to be able to handle them
24) As a worker, I want to manage requests, in order to be able to accept or reject them

### PORTS: 
10600:10700

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER NAME: Admin_Tools

### DESCRIPTION: 
Provides administrative tools for managing and manipulating user and worker data.

### USER STORIES:
16) As an admin, I want to have an add customer button, so that I can generate new customers
18) As an admin, I want to have a delete customer button, so that I can delete that customer
19) As an Admin, I want to be able to access the workers list, so that I can see all the workers
20) As an admin, I want to be able to access the profile page of each single worker, so that I can have information related to that specific worker

### PORTS: 
10800:10900

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 

## CONTAINER NAME: Frontend

### DESCRIPTION: 
Serves the web application content, acting as the user interface for RentYourExpert which provides an easy-to-navigate system to interact with backend services. 

### PORTS: 
20000:20100

### DESCRIPTION:


### PERSISTANCE EVALUATION:


### EXTERNAL SERVICES CONNECTIONS:


### MICROSERVICES:

#### MICROSERVICE: ......
- TYPE: 
- DESCRIPTION: 
- PORTS: 


