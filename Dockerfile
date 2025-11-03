# Estágio 1: Build
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /opt/devsibre

COPY --from=build /app/target/*.jar app.jar

# Railway define PORT automaticamente
ENV PORT=8080
EXPOSE 8080

CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
