# NOT FAR(M) FROM HOME

#### Giovanni Della Pelle - 2020124 - @giovdellap

#### Alessandro Pisent - 2085678 - @alessandropisent

#### Ivan Nicolò - 1857050 - @IvanNic06

Documentation on Endpoints and Infrastructure is available on NFFH repository ( https://github.com/giovdellap/NFFH )

### To start:

"start" script creates a new folder inside the current one called NFFH containing all the repos source code

At first : **ALWAYS** cd in the folder: Scripts

- sh start.sh
  - Creates new folder "NFFH"
  - Clones the repositories and docker compose up

After that cd into Scripts/NFFH/

- sh remove.sh
  - Deletes all running containers
  - Deletes all images
- sh update.sh
  - Pulls from all git repositories
- sh run.sh
  - docker compose up on all containers
- sh reboot.sh
  - Executes remove.sh, update.sh, run.sh in sequence

In some occasions, after start.sh, you need to manually restart the following services: client-be, farmer-be, order-be


### Repositories

- NFFH-gateway:https://github.com/giovdellap/NFFH-gateway
- NFFH-auth: https://github.com/alessandropisent/NFFH-Auth
- NFFH-Client-BE: https://github.com/IvanNic06/NFFH-Client-BE
- NFFH-Farmer-BE: https://github.com/IvanNic06/NFFH-Farmer-BE
- NFFH-Order-BE: https://github.com/IvanNic06/NFFH-Order-BE
- NFFH-ImageServer: https://github.com/alessandropisent/NFFH-ImageServer
- NFFH-Client-FE: https://github.com/giovdellap/NFFH-client-FE
- NFFH-Farmer-FE: https://github.com/giovdellap/NFFH-farmer-FE
- NFFH-Admin-FE: https://github.com/giovdellap/NFFH-admin-FE



### Exposed Ports

- Client Frontend: http://localhost:4201
- Farmer Frontend: http://localhost:4202
- Admin Frontend: http://localhost:4203
- Gateway: http://localhost:8080



