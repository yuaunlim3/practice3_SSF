# Use a base image with JDK installed
FROM eclipse-temurin:23-jdk

LABEL maintainer="yuaunlim"

## How to build the application

# Create a directory /app and change directory into /app
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY ./mvnw . 
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code into the container
COPY src src

# Build the application
RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true 

# After the build, the jar should be in the target folder
# Modify this according to your actual JAR name, which might differ

# Set environment variables (use appropriate values for your setup)
ENV PORT=8080
ENV SPRING_DATA_REDIS_HOST=localhost
ENV SPRING_DATA_REDIS_PORT=6379
ENV SPRING_DATA_REDIS_USERNAME=""
ENV SPRING_DATA_REDIS_PASSWORD=""

# Expose the port your application will run on
EXPOSE ${PORT}

# Run the application
ENTRYPOINT ["java", "-jar", "target/practice3-0.0.1-SNAPSHOT.jar"]
