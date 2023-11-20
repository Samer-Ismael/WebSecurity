FROM openjdk:19-alpine

WORKDIR /app

COPY target/Samer-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "Samer-0.0.1-SNAPSHOT.jar"]