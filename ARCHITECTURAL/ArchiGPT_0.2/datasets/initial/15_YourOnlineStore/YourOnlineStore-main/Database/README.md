# How to use
## Starting
check the docker-compose.yml to set the left port in the application service to select on which port the app listens

Then run ```docker compose up<``` or ```docker-compose up```
to start the service.

## Usage
To make requests to the databse make a REST call towards localhost:8070/Database (or whatever port you've selected)

Eg. ```curl localhost:8070/Database/all```

## Available calls

```/add```
Adds an item to the database, with the following parameters:
- **productName**:  name of this product
- **cost** : how much the product costs
- **disponibility** :  how many items of that product are available
- **imageURL** : url position to the product image (WIP)
- **description** : textual description of the item
also return the id given to the item

being a POST call, for every parameter write ```-d parameter_name=parameter_value```

```/all```
Returns a list of all items in the database and their informations

```/buy```
If the item is available the database reduces its disponibility count by one.
Needs item id for the search

```/get```
Returns data on one particular item. Needs id as input
