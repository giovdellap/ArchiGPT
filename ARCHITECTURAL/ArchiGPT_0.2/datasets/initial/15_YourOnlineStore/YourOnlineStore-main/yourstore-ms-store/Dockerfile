FROM openjdk:11.0-jdk
ADD /target/yourstore-ms-store.jar /
EXPOSE 8014
RUN apt-get update
RUN apt-get install dos2unix

ADD wait-for-it.sh /
# CMD cd / && ./wait-for-it.sh db:3306 
#CMD ./s_start.sh
ENTRYPOINT ["./wait-for-it.sh", "db:3306", "--", "java", "-jar", "yourstore-ms-store.jar"]