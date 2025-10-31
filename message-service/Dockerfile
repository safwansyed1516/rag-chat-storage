# ======================================
# 🏗️ Build Stage
# ======================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

COPY pom.xml .
COPY common ./common
COPY message-service ./message-service

RUN mvn -pl message-service -am clean package -DskipTests

# ======================================
# 🚀 Runtime Stage
# ======================================
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=build /build/message-service/target/message-service-1.0.0.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
