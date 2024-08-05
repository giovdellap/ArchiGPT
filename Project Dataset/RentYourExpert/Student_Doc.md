# SYSTEM DESCRIPTION:

RentYourExpert is a platform that connects individuals who require expert services with skilled professionals across various fields. Its main features are :
- User-friendly Interface: The app is designed to be easy to navigate, allowing users to quickly find the information they need.
- Search Functionality: Users can search for workers based on their skills and location.
- Secure User Authentication and Authorization: Robust measures ensure the security of user access.
- Worker Reviews: The platform displays worker reviews, helping clients make informed decisions when hiring a worker.
- Efficient Communication: A built-in Q&A system makes it easy for both parties to discuss project requirements and expectations.
- Easy-to-Use Profile Management: Workers can efficiently manage their profiles, projects, and communication with clients through an intuitive interface.

# USER STORIES:

1) As a Customer, I want to be able to register, so that i can have an account 
2) As a Customer, I want to be able to login, so that i can start using the app
3) As a Customer, I want to be able to have access to my profile, so that i can look at my informations
4) As a Customer, I want to be able to have access to the catalogue, so that i can see all avaiable workers
5) As a Customer, I want to be able to have access to the page of each single customer, so that i can have all informations related to him
6) As a customer, I want to be able send a request to a worker, so that i can rent his expertise
7) As a Customer, I want to be able to a list of requests in my profile page, so that i can see all the requests that i did
8) As a customer i want to have a specific button attached to my request, so that i can delete it
9) As a customer, I want to be able to see the questions made by other customers to a specific worker, so that i can have other informations related to him
10) As a customer i want to be able to have a field of text in the worker's page, in order to be able to send questions to the expert
11) As a customer, I want to be able to leave a review, in order to evaluate the performance that i received
12) As a customer i want to have specific buttons attached to the review that i posted, in order to update or delete it.
13) As a customer i want to have a specific button in the pages of the application, that allow me to logout
14) As an admin, I want to be able to login, so that i can start the management of the e-commerce
15) As an admin, I want to be able to access to the customers list, so that i can see all the customers
16) As an admin, i want to have an add customer Button, so that i can generate new customers
17) As an admin, I want to be able to access to the profile page of each single customer, so that I can have informations related to that specific customer
18) As an admin, i want to have a delete customer button, so that i can delete that customer
19) As an Admin, I want to be able to access to the workers list, so that i can see all the workers
20) As an admin, I want to be able to access to the profile page of each single worker, so that I can have informations related to that specific worker
21) As an admin, I want to be able to access to the requests page, so that I can see each request done so far
22) As an Admin i want to be able to add or delete a request, in order to be able to handle them
23) As a worker, I want to have a requests page, so that i can look at all my requests
24) As a worker, i want to manage requests, in order to be able to accept or reject them


# CONTAINERS:

## CONTAINER_NAME: RentYourExpert

### DESCRIPTION: 
Manages and displays user account (customers, workers and admins), catalogue, job requests, reviews, profile pages along with its features.

### USER STORIES:
1) As a Customer, I want to be able to register, so that i can have an account 
2) As a Customer, I want to be able to login, so that i can start using the app
3) As a Customer, I want to be able to have access to my profile, so that i can look at my informations
4) As a Customer, I want to be able to have access to the catalogue, so that i can see all avaiable workers
5) As a Customer, I want to be able to have access to the page of each single customer, so that i can have all informations related to him
6) As a customer, I want to be able send a request to a worker, so that i can rent his expertise
7) As a Customer, I want to be able to a list of requests in my profile page, so that i can see all the requests that i did
8) As a customer i want to have a specific button attached to my request, so that i can delete it
9) As a customer, I want to be able to see the questions made by other customers to a specific worker, so that i can have other informations related to him
10) As a customer i want to be able to have a field of text in the worker's page, in order to be able to send questions to the expert
11) As a customer, I want to be able to leave a review, in order to evaluate the performance that i received
12) As a customer i want to have specific buttons attached to the review that i posted, in order to update or delete it.
13) As a customer i want to have a specific button in the pages of the application, that allow me to logout
14) As an admin, I want to be able to login, so that i can start the management of the e-commerce
15) As an admin, I want to be able to access to the customers list, so that i can see all the customers
16) As an admin, i want to have an add customer Button, so that i can generate new customers
17) As an admin, I want to be able to access to the profile page of each single customer, so that I can have informations related to that specific customer
18) As an admin, i want to have a delete customer button, so that i can delete that customer
19) As an Admin, I want to be able to access to the workers list, so that i can see all the workers
20) As an admin, I want to be able to access to the profile page of each single worker, so that I can have informations related to that specific worker
21) As an admin, I want to be able to access to the requests page, so that I can see each request done so far
22) As an Admin i want to be able to add or delete a request, in order to be able to handle them
23) As a worker, I want to have a requests page, so that i can look at all my requests
24) As a worker, i want to manage requests, in order to be able to accept or reject them


### PORTS: 
3000:32000

### DESCRIPTION:
This only RentYourExpert container is designed to handle all core functionalities related to user account (customers, workers and admins), catalogue, job requests, reviews, profile pages along with its features within the RentYourExpert platform.

### PERSISTANCE EVALUATION:
The container handles persistent and accessible storage for user, request, revies and q&a data. 

### EXTERNAL SERVICES CONNECTIONS:
The container does not connect to external services.

### MICROSERVICES:

#### MICROSERVICE: frontend
- TYPE: frontend
- DESCRIPTION: The frontend microservice will be able to provide all the web pages to the end user, and it will also be able to communicate with the backend server when necessary, in order to present to the end user all the data (s)he asked for, and more in general to manage the user input.
- PORTS: 3000

#### MICROSERVICE: catalogue_ms
- TYPE: backend
- DESCRIPTION: Manages catalogue pages and its features.
- PORTS: 5000

#### MICROSERVICE: worker_login_ms
- TYPE: backend
- DESCRIPTION: Manages worker accounts, including registration, login, logout and pending requests, allowing to accept or reject them.
- PORTS: 5001

#### MICROSERVICE: management_ms
- TYPE: backend
- DESCRIPTION: Manage workers and customer accounts data, admin login system and tools including request and worker deletion.
- PORTS: 5002

#### MICROSERVICE: cust_login_ms
- TYPE: backend
- DESCRIPTION: Manages customer accounts, including registration, login, logout, pending and accepted requests.
- PORTS: 5003

#### MICROSERVICE: request_ms
- TYPE: backend
- DESCRIPTION: Manages all the core functionalities of requests.
- PORTS: 5004

#### MICROSERVICE: qea_ms
- TYPE: backend
- DESCRIPTION: Manages all the core functionalities of questions and answers.
- PORTS: 5005

#### MICROSERVICE: review_ms
- TYPE: backend
- DESCRIPTION: Manages all the core functionalities of reviews.
- PORTS: 5006

#### MICROSERVICE: db
- TYPE: database
- DESCRIPTION: The database microservice provides a database management system, that will be able to manage all the data that are necessary for the correct behavior of the application.
- PORTS: 32000


