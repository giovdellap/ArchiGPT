## YourOnlineStore 
### Project structure:
- README.md
- docekr-compose.yml
- Database
- yourstore-gateway
- yourstore-ms-store

### Project goals
This is a project for Laboratory of Advanced Programming at Sapienza University of Rome by Caruso Paolo, Fioravanti Cristian, Volkov Anton.
The purpose of the project is to build a website, both front-end and back-end, where to sell whatever product the admin wants to sell.
The website provides a way for customers to find, inspect and buy Products with ease, it also learns from a user and provides suggestions to which products are best for the user.
It also provides statistics and other information to the seller to see how a product is selling.

### How to run
The needed steps to build and deploy the system on your platform using a IAC approach are the following ones:
  - Install Docker Desktop on your device.
  - Download the zip of the project from GitHub at 'https://github.com/Cristian-Fioravanti/YourOnlineStore'.
  - Unzip the project and open a terminal inside it.
  - Execute ```mvn clean install```
  - Execute ```docker-compose up --build -d```
  - From a Browser digit ```localhost:8080/home``` to access the website.
  - Stop executing ```docker compose down```.

The GitHub link contains all the source code, configuration files, docker compose files and dockerfiles.
