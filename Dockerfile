FROM eclipse-temurin:21-jdk as build
COPY . /usr/app
WORKDIR /usr/app
RUN ./mvnw -f ./pom.xml clean package

FROM eclipse-temurin:21-jre-alpine
LABEL authors="andreferreira"
ARG JAR_FILE=/usr/app/api/target/*.jar
COPY --from=build $JAR_FILE /app/tinybank.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/tinybank.jar"]