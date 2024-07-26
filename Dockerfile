# Etapa de build
FROM maven:3.8.6-jdk-11 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -P production -DskipTests

# Etapa final
FROM openjdk:11
WORKDIR /app

# Copiar o JAR gerado
COPY --from=build ./build/target/*.jar ./application.jar

# Copiar scripts de migração do Flyway
COPY --from=build /build/src/main/resources/db/migration /app/resources/db/migration

#Expor a porta
EXPOSE 8080

ENTRYPOINT java -jar application.jar