# SYSTEM DESCRIPTION:

The RecipeCove project consists of a web application about recipes, food and cooking. 
The main goal is to provide an easy-to-use tool to aid in recipe discovering, bookmarking as well as finding exhaustive information about recipes and ingredients.
The central focus of the system is therefore the discovery of a wide range of recipes, through the use of a search bar, or with the use of a Chat-BOT which will suggest recipes to the user, following a specific diet.
Additionally, the user has the ability to keep a personal profile within the system, which will allow him to see his basic information, but also the ability to save recipes in a favorite list and publish reminders in an application-created calendar.

# USER STORIES:

1) As a visitor, I want to be able to register in the website with an external account, so that I can use an existing Google account
2) As a visitor, I want to be able to search a recipe by its name, so that I can search only what interests me
3) As a visitor, I want to be able to see the information of a recipe, so that I can learn more about it
4) As a visitor, I want to be able to see the information of an ingredient, so that I can know the details of each ingredient in a recipe
5) As a visitor, I want to be able to check the suggested recipes for a specific diet, so that I can be inspired in what to prepare each day
6) As a registered user, I want to be able to perform the login, so that I can access other system's functionalities
7) As a registered user, I want to be able to perform the logout, so that I can leave my information within the website
8) As a registered user, I want to be able to access my profile page, so that I can check my personal information
9) As a registered user, I want to be able to create a Calendar, so that I can populate it with events relating recipes 
10) As a registered user, I want to be able to delete a Calendar, so that I can remove the data gathered within the website 
11) As a registered user, I want to be able to add an event to my Calendar, so that I can be reminded on a specific day to prepare a particular recipe
12) As a registered user, I want to be able to add a recipe to my favorite list, so that I can keep track of all the recipes I like 
13) As a registered user, I want to be able to remove a recipe from my favorite list, so that I can stop being interested in it
14) As a registered user, I want to be able to see my favorite list, so that I can check my current interests in recipes 


# CONTAINERS:

## CONTAINER_NAME: RecipeCove

### DESCRIPTION: 
Manages and displays user account, recipes, chatBot and calendar features.

### USER STORIES:
1) As a visitor, I want to be able to register in the website with an external account, so that I can use an existing Google account
2) As a visitor, I want to be able to search a recipe by its name, so that I can search only what interests me
3) As a visitor, I want to be able to see the information of a recipe, so that I can learn more about it
4) As a visitor, I want to be able to see the information of an ingredient, so that I can know the details of each ingredient in a recipe
5) As a visitor, I want to be able to check the suggested recipes for a specific diet, so that I can be inspired in what to prepare each day
6) As a registered user, I want to be able to perform the login, so that I can access other system's functionalities
7) As a registered user, I want to be able to perform the logout, so that I can leave my information within the website
8) As a registered user, I want to be able to access my profile page, so that I can check my personal information
9) As a registered user, I want to be able to create a Calendar, so that I can populate it with events relating recipes 
10) As a registered user, I want to be able to delete a Calendar, so that I can remove the data gathered within the website 
11) As a registered user, I want to be able to add an event to my Calendar, so that I can be reminded on a specific day to prepare a particular recipe
12) As a registered user, I want to be able to add a recipe to my favorite list, so that I can keep track of all the recipes I like 
13) As a registered user, I want to be able to remove a recipe from my favorite list, so that I can stop being interested in it
14) As a registered user, I want to be able to see my favorite list, so that I can check my current interests in recipes 

### PORTS: 
3000:5984

### DESCRIPTION:
This only RecipeCove container is designed to handle all core functionalities related to user account, recipes, chatBot and calendar features within the EFarmers platform.

### PERSISTANCE EVALUATION:
The container handles persistent and accessible storage for user data including user informations and list of favorites recipes.

### EXTERNAL SERVICES CONNECTIONS:
The container connects to google external services such as GoogleAuth, GoogleCalendar and Gmail. Also integrates Spoonacular API for ChatBot suggestions implementation. 

### MICROSERVICES:

#### MICROSERVICE: couchdb
- TYPE: database
- DESCRIPTION: Its main function is to store the data related to users, including personal information and the recipes added to the favorite list. CouchDB is a document based database, providing a straightforward communication and management of files in the form of JSON documents, which in turn are easily parsable and not too verbose. Additionally, CouchDB provides resiliency of data in the form of replication, always keeping a replica set for the stored data and providing fault tolerance.
- PORTS: 5984

#### MICROSERVICE: webapp
- TYPE: frontend/backend
- DESCRIPTION: The main functionalities included in webapp microservice are the registration and login of the user with an existing Google account, the management of his profile, the Chat-BOT microservice implemented using web sockets, and the management of the discovery and visualization of recipes and their information as well as ingredients and related data.
- PORTS: 3000

#### MICROSERVICE: calendar
- TYPE: frontend/backend
- DESCRIPTION: this microservice is employed to serve the Google Calendar endpoints. It allows the user to create a Google Calendar directly from our system, while also allowing him to correctly delete it from the application. Additionally, there is the possibility for the user to directly create an event (which will function as a reminder) for each recipe the user may intend to prepare on a specific day.
- PORTS: 3001

#### MICROSERVICE: nodemailer
- TYPE: other
- DESCRIPTION: this microservice is devoted to providing a mail service, sending emails to the users that are registered in our application. It communicates with the Webapp microservice through the RabbitMQ microservice.
- PORTS: undefined

#### MICROSERVICE: rabbitmq
- TYPE: other
- DESCRIPTION: this microservice allows communication, in an asynchronous exchange of messages, between the Webapp and the Nodemailer containers. It works as a middleware that provides multiple messaging protocols.
- PORTS: 5672


