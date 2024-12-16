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

## CONTAINER NAME: Authentication

### DESCRIPTION: 
Handles registration via external accounts, logins, and logouts for secure user sessions.

### USER STORIES: 
1) As a visitor, I want to be able to register in the website with an external account, so that I can use an existing Google account.
6) As a registered user, I want to be able to perform the login, so that I can access other system's functionalities.
7) As a registered user, I want to be able to perform the logout, so that I can leave my information within the website.

### PORTS: 
12000:12050

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

## CONTAINER NAME: Recipe_Search

### DESCRIPTION: 
Manages recipe and ingredient data access, including search functionality and detailed recipe and ingredient information viewing.

### USER STORIES: 
2) As a visitor, I want to be able to search a recipe by its name, so that I can search only what interests me.
3) As a visitor, I want to be able to see the information of a recipe, so that I can learn more about it.
4) As a visitor, I want to be able to see the information of an ingredient, so that I can know the details of each ingredient in a recipe.

### PORTS: 
12051:12100

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

## CONTAINER NAME: Recipe_Suggestions
    
### DESCRIPTION: 
Features intelligent recipe recommendations based on diet choices through an interactable Chat-BOT.

### USER STORIES: 
5) As a visitor, I want to be able to check the suggested recipes for a specific diet, so that I can be inspired in what to prepare each day.

### PORTS: 
12101:12150

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

## CONTAINER NAME: User_Profile

### DESCRIPTION: 
Provides functionality for users to access and manage personal profiles, including viewing, adding, and removing favorites as well as handling calendar events.

### USER STORIES: 
8) As a registered user, I want to be able to access my profile page, so that I can check my personal information.
9) As a registered user, I want to be able to create a Calendar, so that I can populate it with events relating recipes.
10) As a registered user, I want to be able to delete a Calendar, so that I can remove the data gathered within the website.
11) As a registered user, I want to be able to add an event to my Calendar, so that I can be reminded on a specific day to prepare a particular recipe.
12) As a registered user, I want to be able to add a recipe to my favorite list, so that I can keep track of all the recipes I like.
13) As a registered user, I want to be able to remove a recipe from my favorite list, so that I can stop being interested in it.
14) As a registered user, I want to be able to see my favorite list, so that I can check my current interests in recipes.

### PORTS: 
12151:12200

### DESCRIPTION:

### PERSISTANCE EVALUATION:

### EXTERNAL SERVICES CONNECTIONS:

### MICROSERVICES:

#### MICROSERVICE: 
- TYPE: 
- DESCRIPTION:
- PORTS:

## CONTAINER NAME: Frontend

### DESCRIPTION: 
Handles the frontend serving of the web application, delivering user interfaces and managing interactions with backend services.

### PORTS: 
12201:12250


