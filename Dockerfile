# Estágio 1: Build da aplicação
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Execução
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /opt/devsibre

COPY --from=build /app/target/devsibre.jar .

ARG PROFILE
ARG ADDITIONAL_OPTS
ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

EXPOSE 8080
CMD java ${ADDITIONAL_OPTS} -jar devsibre.jar --spring.profiles.active=${PROFILE}
