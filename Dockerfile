FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY Jeogiyo-0.0.1-SNAPSHOT.jar /app/Jeogiyo-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Jeogiyo-0.0.1-SNAPSHOT.jar"]