# SYSTEM DESCRIPTION:

The purpose of our project is to build a web application providing a set of services aimed at the management
and proposals of events and distribution of tickets.
The goal is provide to the users a way to discover events and buy or preorder tickets directly online. At the
same time the application allows also the events’ managers to publish their events in order to promote them
and eventually to give the possibility to sell tickets of the event through the website.

# USER STORIES:

1. As a client, I want to visit the web application, so that I can explore the functionalities.
2. As a client, I want to register to the service, so that I can be able to use the user's features.
3. As a client, I want to login to the service, so that I become an user, a manager or an admin.
4. As a client, I want to explore the list of events, so that I can see any event.
5. As an user, I want to access my personal page, so that I can see my data.
6. As an user, I want to be able to add a favourite category, so that I can have email notifications about related events.
7. As an user, I want to see the booked pre-sales, so that I can see them.
8. As an user, I want to see the bought tickets, so that I can see them.
9. As an user, I want to book a pre-sale, so that I obtain the pre-sale to access the event.
10. As an user, I want to buy a ticket, so that I obtain the ticket to access the event.
11. As a user, I want to pay for a ticket online so that I can get the ticket code.
12. As an user, I want to logout, so that I become a client.
13. As a manager, I want to see all events I have proposed.
14. As a manager, I want to insert a new event, so that I can add it into the web application.
15. As an manager, I want to access my personal page, so that I can see my data.
16. As a manager, I want to logout, so that I become a client.
17. As an admin, I want to add a new manager, so that he can publish events.
18. As an admin, I want to logout, so that I become a client.
19. As an admin, I want to access my personal page, so that I can see my data.


# CONTAINERS:

## CONTAINER NAME: User_Authentication

### DESCRIPTION: 
Manages all aspects of user authentication including registration, login, and logout functions.

### USER STORIES: 
2. As a client, I want to register to the service, so that I can be able to use the user's features.
3. As a client, I want to login to the service, so that I become an user, a manager or an admin.
12.  As an user, I want to logout, so that I become a client.
16.  As a manager, I want to logout, so that I become a client.
18.  As an admin, I want to logout, so that I become a client.

### PORTS: 10200:10299

## CONTAINER NAME: Events_Manager

### DESCRIPTION: 
Handles the creation, listing, and specific views of events.

### USER STORIES:
4. As a client, I want to explore the list of events, so that I can see any event.
13. As a manager, I want to see all events I have proposed.
14. As a manager, I want to insert a new event, so that I can add it into the web application.

### PORTS: 10300:10399

## CONTAINER NAME: Ticketing

### DESCRIPTION: 
Manages booking, purchasing, and viewing of tickets and pre-sales.

### USER STORIES:
7. As an user, I want to see the booked pre-sales, so that I can see them.
8. As an user, I want to see the bought tickets, so that I can see them.
9. As an user, I want to book a pre-sale, so that I obtain the pre-sale to access the event.
10. As an user, I want to buy a ticket, so that I obtain the ticket to access the event.
11. As a user, I want to pay for a ticket online so that I can get the ticket code.

### PORTS: 10400:10499

## CONTAINER NAME: Notification

### DESCRIPTION: 
Handles the dispatching of alerts and notifications to users regarding their preferred categories and ticketing updates.

### USER STORIES:
6. As an user, I want to be able to add a favourite category, so that I can have email notifications about related events.

### PORTS: 10500:10549

## CONTAINER NAME: Admin_Manager

### DESCRIPTION: 
Provides administrative functionalities such as adding new managers.

### USER STORIES:
17. As an admin, I want to add a new manager, so that he can publish events.

### PORTS: 10550:10599

## CONTAINER NAME: User_Personal_Page

### DESCRIPTION: 
Provides functionalities to view personal data and user-specific interfaces for the client, manager, and admin roles.

### USER STORIES:
5. As an user, I want to access my personal page, so that I can see my data.
15. As a manager, I want to access my personal page, so that I can see my data.
19. As an admin, I want to access my personal page, so that I can see my data.

### PORTS: 10600:10699

## CONTAINER NAME: Application_Entry

### DESCRIPTION: 
Welcomes and guides new and returning users to explore the functionalities of the web application without logging in.

### USER STORIES:
1. As a client, I want to visit the web application, so that I can explore the functionalities.

### PORTS: 10100:10199
