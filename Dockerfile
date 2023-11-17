FROM maven:3.8.4-openjdk-8

WORKDIR /app

# Copy only the necessary files to leverage Docker cache
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean install

EXPOSE 3003

ENTRYPOINT ["java", "-jar", "target/service_soap-jar-with-dependencies.jar"]