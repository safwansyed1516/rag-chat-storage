# Use official Java 17 image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the fat jar built by Maven
COPY target/session-service-1.0.0.jar app.jar

# Expose internal port (optional, Compose maps anyway)
EXPOSE 8081

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
