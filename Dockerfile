FROM openjdk:17-ea-11-jdk-slim
WORKDIR /app
COPY build/libs/syncfit-0.0.1-SNAPSHOT.jar recommend-service.jar
ENTRYPOINT ["java", "-jar", "recommend-service.jar"]