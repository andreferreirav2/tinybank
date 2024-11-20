# TinyBank API

## Build and Run with Maven
### Setup
Have JDK 21 installed and `JAVA_HOME` configured.

### Build
To build the service:
`./mvnw clean package`

### Run
`java -jar target/TinyBank-0.0.1-SNAPSHOT.jar`

## Build and Run with Docker
### Setup
Have Docker installed and running

### Build
To build the service inside a docker container:
`docker build --tag tinybank .`

### Run
To run the service inside a docker container:
`docker run -p 8080:8080 tinybank`

## Swagger UI and OpenAPI
To access Swagger UI: http://localhost:8080/swagger-ui/index.html

To access OpenAPI3 docs: http://localhost:8080/v3/api-docs and http://localhost:8080/v3/api-docs.yaml

## Health-Check and Prometheus
To access health-checks: http://localhost:8080/actuator/health

To access prometheus metrics: http://localhost:8080/actuator/prometheus

