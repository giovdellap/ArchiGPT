From openjdk:11.0-jdk
ADD /target/database.jar /
ADD wait-for-it.sh /
ENTRYPOINT ["./wait-for-it.sh", "db:3306", "--", "java","-jar","database.jar"]
