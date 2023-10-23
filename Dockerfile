FROM openjdk:8

WORKDIR /app

# Copy the JAR file into the container
COPY ./target/service_soap-jar-with-dependencies.jar app.jar

EXPOSE 3003

ENTRYPOINT ["java", "-jar", "app.jar"]