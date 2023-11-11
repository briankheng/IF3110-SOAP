FROM openjdk:8

COPY ./target /app

WORKDIR /app

EXPOSE 3003

ENTRYPOINT ["java", "-jar", "service_soap-jar-with-dependencies.jar"]