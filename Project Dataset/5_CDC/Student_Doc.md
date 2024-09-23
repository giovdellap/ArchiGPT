# SYSTEM DESCRIPTION:

CDC shop is a distributed system that handles the management, the payment and the shipment of products: in particular for materials that you can usually find in an hardware store.

# USER STORIES:
1) As a Customer, I want to be able to register in order to have an account 
2) As a Customer, I want to be able to login
3) As a Customer, I want to be able to have access to my profile
4) As a Customer, I want to see my current shopping cart
5) As a Customer, I want to order the products for ascending/descending price
6) As a Customer, I want to retrieve a product by giving its name in a search bar
7) As a Customer, I want to pay for the current shopping cart
8) As a Customer, I want to logout from the profile by clicking on a button
9) As a Customer, I want to check my order to see informations about my package
10) As a Customer, I want to access the About Us page
11) As a Customer, I want to see the list of products
12) As a Customer, I want to see the details of a product
13) As a Merchant, I want to logout from the profile by clicking on a button
14) As an Merchant, I want to be able to login to the Merchant page
15) As an Merchant, I want to change the price of a product
16) As an Merchant, I want to insert/remove products
17) As an Merchant, I want to order the products for ascending/descending price
18) As an Merchant, I want to retrieve a product by giving its name in a search bar
19) As an Merchant, I want to access to my profile
20) As an Merchant, I want to access the About Us page
21) As an Merchant, I want to see the list of products
22) As an Merchant, I want to see the details of a product
23) As a Customer, I want to contact the shop for any doubt
24) As a Customer, I want to be able to add a product on my cart
25) As a Customer, I want to be able to remove all the items in my cart


# CONTAINERS:

## CONTAINER_NAME: CDC

### DESCRIPTION:
Manages and displays customer, merchant authentication, products and orders.

### USER STORIES:
1) As a Customer, I want to be able to register in order to have an account 
2) As a Customer, I want to be able to login
3) As a Customer, I want to be able to have access to my profile
4) As a Customer, I want to see my current shopping cart
5) As a Customer, I want to order the products for ascending/descending price
6) As a Customer, I want to retrieve a product by giving its name in a search bar
7) As a Customer, I want to pay for the current shopping cart
8) As a Customer, I want to logout from the profile by clicking on a button
9) As a Customer, I want to check my order to see informations about my package
10) As a Customer, I want to access the About Us page
11) As a Customer, I want to see the list of products
12) As a Customer, I want to see the details of a product
13) As a Merchant, I want to logout from the profile by clicking on a button
14) As an Merchant, I want to be able to login to the Merchant page
15) As an Merchant, I want to change the price of a product
16) As an Merchant, I want to insert/remove products
17) As an Merchant, I want to order the products for ascending/descending price
18) As an Merchant, I want to retrieve a product by giving its name in a search bar
19) As an Merchant, I want to access to my profile
20) As an Merchant, I want to access the About Us page
21) As an Merchant, I want to see the list of products
22) As an Merchant, I want to see the details of a product
23) As a Customer, I want to contact the shop for any doubt
24) As a Customer, I want to be able to add a product on my cart
25) As a Customer, I want to be able to remove all the items in my cart

### PORTS: 
8000:8003 - 32000 - 3000

### DESCRIPTION:
This container is designed to handle all core functionalities related to user accounts, products and order features within the CDC platform.

### PERSISTANCE EVALUATION:
The container handles persistent and accessible storage for users, products and order features data. 

### EXTERNAL SERVICES CONNECTIONS:
The container does not connect to external services.

### MICROSERVICES:

#### MICROSERVICE: warehouse_api
- TYPE: backend
- DESCRIPTION: Manages product related features like insertion, modification, deletion and queries.
- PORTS: 8000
- TECHNOLOGICAL SPECIFICATION: The warehouse_api microservice uses Python as its primary programming language. For building a web application with RESTful APIs, the microservice leverages the **Flask** web framework. In order to achieve his aims, it uses the following libraries and technologies:
	- **flask_cors**: Extension for handling Cross-Origin Resource Sharing (CORS) in Flask.
	- **mysql-connector**: A Python driver used to connect to and interact with MySQL databases.
- SERVICE ARCHITECTURE: There is no service architecture.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| POST | /products/ | Insert a product in the database | 16 |
	| GET | /products/ | Returns all the products | 5,6,11,21 |
	| GET | /products/{product_id} | Return the product with id = {product_id} | 12,22 |
	| DELETE | /products/{product_id} | Delete the product with id = {product_id} from the database | 16 |

- DB STRUCTURE: 

	**_Products_** :	| **_id_** | name | description | category | price | stock | image_url |

#### MICROSERVICE: catalog_api
- TYPE: backend
- DESCRIPTION: Manages cart-related features like product insertion in the cart and product deletion from the cart.
- PORTS: 8001
- TECHNOLOGICAL SPECIFICATION: The catalog_api microservice uses Python as its primary programming language. For building a web application with RESTful APIs, the microservice leverages the **Flask** web framework. In order to achieve his aims, it uses the following libraries and technologies:
	- **flask_cors**: Extension for handling Cross-Origin Resource Sharing (CORS) in Flask.
	- **mysql-connector**: A Python driver used to connect to and interact with MySQL databases.
- SERVICE ARCHITECTURE: There is no service architecture.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| GET | /cart/ | Returns all the products of an user with id = {user_id} | 4 |
	| POST | /cart/ | Add a product to the cart of a user | 24 |
	| DELETE | /cart/ | Delete a product from a cart | 25 |

- DB STRUCTURE: 

	**_Cart_** :	| ***id*** | order_id | product_id | quantity |

#### MICROSERVICE: purchase_refund_api
- TYPE: backend
- DESCRIPTION: Handles the order checkout.
- PORTS: 8002
- TECHNOLOGICAL SPECIFICATION: The purchase_refund_api microservice uses Python as its primary programming language. For building a web application with RESTful APIs, the microservice leverages the **Flask** web framework. In order to achieve his aims, it uses the following libraries and technologies:
	- **flask_cors**: Extension for handling Cross-Origin Resource Sharing (CORS) in Flask.
	- **mysql-connector**: A Python driver used to connect to and interact with MySQL databases.
- SERVICE ARCHITECTURE: There is no service architecture.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| GET | /checkout/ | Returns all the products of an user | 7 |

#### MICROSERVICE: auth_api
- TYPE: backend
- DESCRIPTION: Handles authentication for customers and merchants.
- PORTS: 8003
- TECHNOLOGICAL SPECIFICATION: The auth_api microservice uses Python as its primary programming language. For building a web application with RESTful APIs, the microservice leverages the **Flask** web framework. In order to achieve his aims, it uses the following libraries and technologies:
	- **flask_cors**: Extension for handling Cross-Origin Resource Sharing (CORS) in Flask.
	- **mysql-connector**: A Python driver used to connect to and interact with MySQL databases.
- SERVICE ARCHITECTURE: There is no service architecture.
- ENDPOINTS:
		
	| HTTP METHOD | URL | Description | User Stories |
	| ----------- | --- | ----------- | ------------ |
	| POST | /register/ | Handle user registration process | 1 |
	| POST | /login/ | Handle user login process | 2,14 |

- DB STRUCTURE: 

	**_Users_** :	| ***id*** | name | email | phone | password |

#### MICROSERVICE: db
- TYPE: database
- DESCRIPTION: Stores data for users, products, carts and orders features.
- PORTS: 32000
- TECHNOLOGICAL SPECIFICATION: The db microservice will use MySQL for storing, retrieving, and managing structured data in a reliable and scalable manner, enabling efficient data access and persistence for the application.
- SERVICE ARCHITECTURE: There is no architecture.

- DB STRUCTURE: 

	**_Orders_**:	| **_id_** | date | customer_name | customer_email | customer_phone | total_price | paid | status |

	**_Purchase_items_**:	| **_id_** | purchase_id | prod_id | quantity | price |
	
	**_Purchases:_**:	| **_id_** | date | supplier_id | total_price |

	**_Suppliers_**:	| **_id_** | name | address | email | phone |

#### MICROSERVICE: frontend
- TYPE: frontend
- DESCRIPTION: Serves the web app to interact with the system.
- PORTS: 3000
- TECHNOLOGICAL SPECIFICATION: The frontend microservice is built using ReactJS, which serves as the core framework for developing user interfaces. By utilizing its component-based architecture, the application is both responsive and dynamic. The microservice uses simple fetch to handle asynchronous HTTP requests.
- SERVICE ARCHITECTURE: The frontend microservice architecture is built on a component-based design pattern, promoting the creation of reusable, modular, and isolated UI components.
- PAGES:

	| Name | Description | Related Microservice | User Stories |
	| ---- | ----------- | -------------------- | ------------ |
	| Cart.jsx | Manages and Display cart features | catalog_api | 4 |
	| MerchantPage.jsx | Manages and Display product features | warehouse_api | 5,6,17,18,21 |
	| Login.jsx | Allow the user to login into the application | auth_api | 2,14 |
	| Registration.jsx | Allow the user to register in the application | auth_api | 1 |
	| Account.jsx | Allow the user to see his informations | auth_api | 3,8,13,19 |
	| AboutPage.jsx | Allow the user to see About Us page |  | 10,20 |