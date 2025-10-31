# Java 17 runtime
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy jar built from Maven
COPY target/message-service-1.0.0.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
