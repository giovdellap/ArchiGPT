# Clone and Start Repos
mkdir NFFH
cp -r *.* ./NFFH
cd NFFH

### Clone Gatway
git clone https://github.com/giovdellap/NFFH-gateway.git
cd NFFH-gateway 
docker compose up &
cd ..

## Clone Auth
git clone https://github.com/alessandropisent/NFFH-Auth.git
cd NFFH-Auth
docker compose up &
cd ..

## Clone Image Server
git clone https://github.com/alessandropisent/NFFH-ImageServer.git
cd NFFH-ImageServer
docker compose up &
cd ..

## Clone Farmer BE
git clone https://github.com/IvanNic06/NFFH-Farmer-BE.git
### Install
cd NFFH-Farmer-BE
mvn install
docker compose up &
cd ..

## Clone Client BE
git clone https://github.com/IvanNic06/NFFH-Client-BE.git
### Install
cd NFFH-Client-BE
mvn install
docker compose up &
cd ..

## Clone Order BE
git clone https://github.com/IvanNic06/NFFH-Order-BE.git
### Install
cd NFFH-Order-BE
mvn install
docker compose up &
cd ..

## Clone Farmer FE
git clone https://github.com/giovdellap/NFFH-client-FE.git
cd NFFH-client-FE
docker compose up &
cd ..


## Clone Farmer-FE
git clone https://github.com/giovdellap/NFFH-farmer-FE.git
cd NFFH-farmer-FE
docker compose up &
cd ..

## Clone Admin-FE
git clone https://github.com/giovdellap/NFFH-admin-FE.git
cd NFFH-admin-FE
docker compose up &
cd ..
