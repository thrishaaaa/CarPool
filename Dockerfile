# Use an official Ubuntu image as a parent image
FROM ubuntu:22.04

# Install OpenJDK and other necessary tools
RUN apt-get update && apt-get install -y openjdk-17-jdk maven && rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build file to the container
COPY pom.xml .

# Copy the source code to the container
COPY src ./src

# Copy the Maven wrapper files
COPY .mvn ./.mvn
COPY mvnw .

# Install Maven dependencies and package the application
RUN ./mvnw dependency:resolve
RUN ./mvnw package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "target/CarPool-0.0.1-SNAPSHOT.jar"]