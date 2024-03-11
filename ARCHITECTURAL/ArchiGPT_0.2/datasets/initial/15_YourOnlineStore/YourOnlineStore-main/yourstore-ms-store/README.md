##H2 Console

Per accedere alla console H2 recarsi al path:

http://{url}:{port}/{microservice-name}/h2-console/

Es: http://localhost:8081/store/h2-console/

**NB:** E' possibile accederci solo facendo partire l'applicazione con "spring-boot:run"

Utilizzare come JDBC URL:
jdbc:h2:mem:store;MODE=MYSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

Utilizzare come User Name:
store

##Swagger

Per accedere alla console Swagger recarsi al path:

http://{url}:{port}/{microservice-name}/api/swagger-ui.html

Es: http://localhost:8081/store/api/swagger-ui.html

##Esecuzione applicazione da Server Tomcat

Aggiungere la seguente variabile per settare lo spring profile nel tab "Arguments" della "Launch configuration" di tomcat (Doppio click sul server -> Open Launch configuration"). 

-Dspring.profiles.active="prod"


##Build da Eclipse

Da Eclipse raggiungere la configurazione per utilizzare tool esterni di build

![Build and Run Project](buildRun.png)

Configurare lo script build and run.cmd valorizzando i campi come in immagine

![Build and Run Project](buildRun2.png)

Cliccare "Apply" e poi "Run"

Stessa procedura per Angular

![Run Angular Project](angularRun.png)
