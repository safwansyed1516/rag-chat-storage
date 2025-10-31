# ======================================
# 🏗️ Build Stage
# ======================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

COPY pom.xml .
COPY api-gateway ./api-gateway

RUN mvn -pl api-gateway -am clean package -DskipTests

# ======================================
# 🚀 Runtime Stage
# ======================================
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=build /build/api-gateway/target/api-gateway-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
