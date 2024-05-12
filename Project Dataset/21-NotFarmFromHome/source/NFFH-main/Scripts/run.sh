cd NFFH-gateway
docker compose up &
cd ..

cd NFFH-Auth
docker compose up &
cd ..

cd NFFH-Client-BE
mvn install
docker compose up &
cd ..

cd NFFH-Farmer-BE
mvn install
docker compose up &
cd ..

cd NFFH-ImageServer
docker compose up &
cd ..

cd NFFH-Order-BE
mvn install
docker compose up &
cd ..

cd NFFH-client-FE
docker compose up &
cd ..

cd NFFH-farmer-FE
docker compose up &
cd ..

cd NFFH-admin-FE
docker compose up &
cd ..
