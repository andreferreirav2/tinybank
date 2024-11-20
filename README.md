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

## Health-Check
To access health-checks: http://localhost:8080/actuator/health

## Prometheus
To access prometheus metrics: http://localhost:8080/actuator/prometheus
