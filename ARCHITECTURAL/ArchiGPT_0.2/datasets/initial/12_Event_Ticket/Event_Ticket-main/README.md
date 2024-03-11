## Event Ticket

### Project structure:
- README.md
- compose.yaml
- nginx
- payments
- event
- rabbit_consumer
- rabbit_producer
- auth

### Project goals
This is a project for Laboratory of Advanced Programming at Sapienza University of Rome by Brunetti Jacopo, Carmignani Federico, Cicchetti Giordano.
The purpose of our project is to build a web application providing a set of services aimed at the management and proposals of events and distribution of tickets.
The goal is provide to the users a way to discover events and buy or preorder tickets directly online. At the same time the application allows also the events’ managers to publish their events in order to promote them and eventually to give the possibility to sell tickets of the event through the website.

### How to run
The needed steps to build and deploy the system on your platform using a IAC approach are the following ones:
  - Install Docker Desktop on your device.
  - Download the zip of the project from GitHub at 'https://github.com/Giordano-Cicchetti/Event_Ticket'.
  - Unzip the project and open a terminal inside it.
  - Execute ```
docker compose up –build```
  - From a Browser digit ```
localhost:80``` to access the website.
  - Stop executing ```docker compose down```.
The GitHub link contains all the source code, configuration files, docker compose files and dockerfiles.
