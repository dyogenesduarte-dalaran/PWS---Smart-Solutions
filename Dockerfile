# Etapa 1: Compilar o projeto instalando o Maven diretamente no Alpine
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
RUN apk add --no-cache maven
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Executar a aplicação de forma leve e otimizada
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]