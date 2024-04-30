# INFRASTRUCTURE

## Containers

| Container | Services | Images | Exposed ports |
| --------- | -------- | ------ | ------------- |
| API Gateway | api-gateway | node 18.18 | 8080 (localhost)
| Auth | auth | python:3.11.6 | 9701 (network) |
| Client-BE | client-be | openjdk:17 | 9702 (network) |
| Client-BE | mysql-client | mysql:8.0.34 | 3306 (internal) |
| Farmer-BE | farmer-be | openjdk:17 | 9703 (network) |
| Farmer-BE | mysql-farmer | mysql:8.0.34 | 3306 (internal) |
| Order-BE | order-be | openjdk:17 | 9704 (network) |
| Order-BE | mysql-order | mysql:8.0.34 | 3306 (internal) |
| Image Server | image-server | python:3.11.6 | 9705 (network) |
| Client-FE | client-fe | node:18 | 4201 (localhost) |
| Farmer-FE | farmer-fe | node:18 | 4202 (localhost) |
| Admin-FE | admin-fe | node:18 | 4203 (localhost) |



#### EXPOSED PORTS LEGEND

- Localhost: The port is exposed on localhost
- Network: The service is reachable through the port only using docker networks
- Internal: The service is available only from inside the container through internal docker networks

## Networks

- API Gateway 
  - g-auth (Bridge network with Auth container)
  - g-client-be (Bridge network with Client-BE container)
  - g-farmer-be (Bridge network with Farmer-BE container)
  - g-order-be (Bridge network with Order-BE container)
  - g-images (Bridge network with ImageServer container)
- Auth Server
  - a-farmer-be (Bridge network with Farmer-BE container)
  - a-client-be (Bridge network with Client-BE container)
  - a-order-be (Bridge network with Order-BE container)



