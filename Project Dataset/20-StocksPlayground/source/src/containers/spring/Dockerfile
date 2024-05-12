FROM maven:latest
WORKDIR /app
COPY ./spring-boot-docker/ ./
COPY ./wait-for-it.sh ./
RUN chmod +x wait-for-it.sh
RUN mvn clean install
CMD ["./wait-for-it.sh", "rabbitmq:5672", "--", "java", "-jar", "./target/spring-boot-docker-0.0.1-SNAPSHOT.jar"]
