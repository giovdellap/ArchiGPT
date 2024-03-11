GitHub Repo:

https://github.com/andreademurtas/LAPProject

Notes on building and deploying: 

In the folder src/containers/mongo, execute the following command:
1) sudo chown 999 security.keyFile
2) (only for linux users) sudo chgrp docker security.keyFile
3) sudo chmod 400 security.keyFile

If on mac, use these commands in order: 1) docker-compose build 2) ./deploy_mac.sh
If on Linux: 1) docker-compose build 2) ./deploy_linux.sh
if needed, give exec permissions to the script (chmod +x ./deploy_[linux|mac].sh)
