FROM openjdk:23-ea-17-jdk-slim

ADD target/*.jar application.jar

COPY province.json /src/main/resources

ENTRYPOINT ["java", "-jar", "/application.jar"]

EXPOSE 8080