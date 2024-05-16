TO START THE SYSTEM:

1) Add a .env file in the following directory: ArchiGPT/src/api_handler
    content of the .env file: OPENAI_API_KEY=<YOUR KEY>>

2)from the repo directory execute:
cd ArchiGPT/src
docker compose up

3)If the frontend service doesn't start:
    1) delete containers and images
    2) from the repo directory:
        cd ArchiGPT/src/frontend
        npm i
        cd ..
        docker compose up

4) frontend is available at localhost:3000
    mongo-express is available at localhost:8081
    mongo express credentials:
    user: admin
    password: pass


ASSISTANTS INSTRUCTIONS:
Assistant instructions are located here:
ArchiGPT/src/api_handler_resources


USER STORIES.txt

DESCRIPTION:

OneSport is dedicated to offering up-to-date and pertinent information on a wide range of sports and athletic events. 
Our primary aim is to keep fans and enthusiasts well-informed and engaged. 
The project strives to serve as a one-stop destination for comprehensive sports news, event updates, and a convenient platform for purchasing tickets to various sporting events.

USER STORIES:

1)As a User, I want to be able to signup in order to have an account 
2)As a User, I want to be able to login
3)As a User, I want to be able to have access to my profile settings
4)As a User, I want to logout from the account by clicking on the Navbar
5)As a User, I want to be able to delete my account by clicking on a button from the profile settings page
6)As a User, I want to be able to modify my email and password in order to change my credentials
7)As a User, I want to see all recent news
8)As a User, I want to filter Italian news by selecting from the menu of available countries 
9)As a User, I want to add to my favourite news all the news I'm interested in, in order to read them later
10)As a User, I want to delete from my favourite news all the news that I'm no longer interested 
11)As a User, I want to see available tickets for upcoming sporting events
12)As a User, I want to filter Italian tickets by selecting from the menu of available countries 
13)As a User, I want to add to my collection all the tickets I'm interested in, in order to purchase them later
14)As a User, I want to delete from my collection all the tickets that I'm no longer interested 
15)As a User, I want to be able to delete all the news and tickets in my favourite list
16)As a User, I want to send a friend request to another user, in order to add him to my friend list
17)As a User, I want to check if someone has sent me a friend request, in order to accept or delete it
18)As a User, I want to delete a friend from my friend list
