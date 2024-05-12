
![recipe_cove_logo_black](https://github.com/valeriodiste/recipe-cove/assets/103375912/5b249ca0-be8a-4108-bb8a-3b6a345aadf3)


# Introduction

The main idea for our project consists of a web application about recipes, food and cooking. Our goal was to provide an easy-to-use tool to aid in recipe discovering, bookmarking as well as finding exhaustive information about recipes and ingredients.

The central focus of the system is therefore the discovery of a wide range of recipes, through the use of a search bar, or with the use of a Chat-BOT which will suggest recipes to the user, following a specific diet.

Additionally, the user has the ability to keep a personal profile within the system, which will allow him to see his basic information, but also the ability to save recipes in a favorite list and publish reminders in an application-created calendar.

# System Infrastructure

The system is composed of several components, running on different Docker containers. Through the use of the docker compose tool, it is possible to re-deploy the whole environment on different clients, with a single instruction.

We also provided a simple Python script which allows to run the system with just a click.

The different main components that have been implemented in our system are the following:
* Webapp container: it is the main container of the system, based on Node.js, which contains the majority of the core functionalities of the system. It uses Express.js, a routing and middleware web framework, as the main application framework for executing the following middleware functions:
  * Application-level middleware (to manage routes and their handler functions);
  * Built-in middleware (which are built-in function, to serve static elements, with express.static, or to properly encode the payloads, with express.urlencoded);
  * Third-party middleware (used to include several third-party functionalities, such as the management of the session or the correct parsing of the body of HTTP requests).

  The main functionalities included in the main container are the registration and login of the user with an existing Google account, the management of his profile, the Chat-BOT microservice implemented using web sockets, and the management of the discovery and visualization of recipes and their information as well as ingredients and related data.
* Calendar container: this container is employed to serve the Google Calendar microservice. It allows the user to create a Google Calendar directly from our system, while also allowing him to correctly delete it from the application. Additionally, there is the possibility for the user to directly create an event (which will function as a reminder) for each recipe the user may intend to prepare on a specific day.
* CouchDB container: it is used as the persistent layer of our system. Its main function is to store the data related to users, including personal information and the recipes added to the favorite list. CouchDB is a document based database, providing a straightforward communication and management of files in the form of JSON documents, which in turn are easily parsable and not too verbose. Additionally, CouchDB provides resiliency of data in the form of replication, always keeping a replica set for the stored data and providing fault tolerance.
* Nodemailer container: this container is devoted to providing a mail service, sending emails to the users that are registered in our application. It communicates with the Webapp container through the RabbitMQ container.
* RabbitMQ container: this container allows communication, in an asynchronous exchange of messages, between the Webapp and the Nodemailer containers. It works as a middleware that provides multiple messaging protocols.

![RecipeCove - AppSchema](https://github.com/valeriodiste/recipe-cove/assets/103375912/8a350287-54d2-47f0-98b2-9edb4c310cdf)

# Deployment

Docker Compose makes it straightforward to build and deploy the entire application, through the use of the docker-compose.yaml file, which specifies all the services that compose the application, and how they interact and communicate with each other.

The docker-compose.yaml file specifies containers’ names, the ports that should be exposed, the build and volume directories, the dependencies and the networks used to communicate.

The deployment of the application itself is very simple, following these steps:
* Open a terminal
* Move to the folder which contains the application
* Execute the following command: docker-compose up -d --build
* Open on any browser the following link: https://localhost:3000



The command is used to first build the necessary images and then to run all the containers composing the application. As an alternative, we also provided a python script named recipe_cove_starter.py, which serves the same purpose.
Depending on the system used to run the application, the aforementioned command may require admin privileges to be executed: to overcome this, one should simply add the “sudo” string before the command or run the terminal as an administrator.

