REQUIREMENTS:
	jdk-18.0.1.1
	Maven 3
COMMAND TO START PROJECT (inside the root dir):
	mvn clean install -DskipTests
	docker compose up -d --build
	java -jar .\PublicAdministrationInterface\target\PublicAdministrationInterface-1.0-SNAPSHOT.jar
