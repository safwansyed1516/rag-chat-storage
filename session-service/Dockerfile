# ======================================
# 🏗️ Build Stage
# ======================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

# Copy only relevant module files to optimize caching
COPY pom.xml .
COPY common ./common
COPY session-service ./session-service

# Package session-service only (skip tests for speed)
RUN mvn -pl session-service -am clean package -DskipTests

# ======================================
# 🚀 Runtime Stage
# ======================================
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy built JAR from previous stage
COPY --from=build /build/session-service/target/session-service-1.0.0.jar app.jar

# Expose port (read from .env during docker-compose)
EXPOSE 8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
