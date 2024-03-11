FROM maven:3-openjdk-18-slim as build
RUN useradd -m myuser
WORKDIR /usr/src/app/
RUN chown myuser:myuser /usr/src/app/
USER myuser
COPY --chown=myuser . .
#
FROM openjdk:18-jdk-slim
COPY --from=build /usr/src/app/pai/target/*.jar /usr/app/app.jar
RUN useradd -m myuser
USER myuser
EXPOSE 8080
CMD java -jar /usr/app/app.jar