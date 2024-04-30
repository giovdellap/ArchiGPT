cd NFFH
git checkout -- .
echo 1
git pull
cd ..

cd NFFH-Auth
git checkout -- .
echo 2
git pull
cd ..

cd NFFH-Client-BE
git checkout -- .
echo 3
git pull
cd ..

cd NFFH-Farmer-BE
git checkout -- .
echo 4
git pull
cd ..

cd NFFH-gateway
git checkout -- .
echo 5
git pull
cd ..

cd NFFH-ImageServer
git checkout -- .
echo 6
git pull
cd ..


cd NFFH-Order-BE
git checkout -- .
echo 7
git pull
cd ..


cd NFFH-farmer-FE
git checkout -- .
echo 8
git pull
cd ..

cd NFFH-client-FE
git checkout -- .
echo 9
git pull
cd ..

cd NFFH-admin-FE
git checkout -- .
echo 10
git pull
cd ..