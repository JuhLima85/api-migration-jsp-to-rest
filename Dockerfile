# Estágio 1: Build da aplicação
# Usa uma imagem com o Maven para compilar o código
FROM maven:3.8.5-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compila e empacota a aplicação, pulando os testes
RUN mvn clean package -DskipTests

# Estágio 2: Criação da imagem final
# Usa uma imagem mais leve, apenas com o JRE, para rodar a aplicação
FROM openjdk:11-jre
WORKDIR /opt/devsibre

# Copia o arquivo JAR do estágio de build para o diretório de trabalho do estágio final
COPY --from=build /app/target/devsibre.jar .

# Define os argumentos de build e variáveis de ambiente
# Eles podem ser passados diretamente pelo Railway
ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

# Expõe as portas que sua aplicação usa
EXPOSE 8080

# Comando para iniciar a aplicação
# O comando de início no Railway deve ser apenas 'java -jar devsibre.jar'
CMD java ${ADDITIONAL_OPTS} -jar devsibre.jar --spring.profiles.active=${PROFILE}