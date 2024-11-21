# TinyBank API

## Overview
Technical decisions:
- Java 21 due to being the latest LTS
- Spring Boot as a web framework
- Maven is used for build and dependency management
- Swagger-UI and OpenAPI3 are used for API documentation and discovery
- Spring Boot Actuator is used for health checks
- Prometheus is used for metric collection
- Docker is used for containerized build and deploy

Architecture:
- `domain` module contains all the business logic and interfaces for DOA classes, doesn't know about storage implementation or Spring/API
- `inmemorystorage` module implements the DOA interfaces using in-memory objects such as ConcurrentHashMap and LinkedList, doesn't know about Spring/API
- `api` module uses the Spring Boot framework to call the DOAs
- `inmemorystorage` is injected into `api`through the AppConfig class and can be transparently swapped by another implementation such as a DB or saving to filesystem

## Requirements
- Creation and deactivation of users
- Ability for users to deposit/withdraw money from their accounts
- Ability for users to transfer money to another user's account
- View account balances
- View transaction history

## Build and Run with Maven
### Setup
Have JDK 21 installed and `JAVA_HOME` configured.

### Build
To build the service:
`./mvnw clean package`

### Run
`java -jar ./api/target/api-0.0.1-SNAPSHOT.jar`

## Build and Run with Docker
### Setup
Have Docker installed and running

### Build
To build the service inside a docker container:
`docker build --tag tinybank .`

### Run
To run the service inside a docker container:
`docker run -p 8080:8080 tinybank`

## Endpoints
Full description of the API can be consulted in [Swagger-UI](#swagger-ui-and-openapi)

### Users
- `GET /api/v1/users` - List all users
- `POST /api/v1/users` - Create a new user
- `GET /api/v1/users/{email}` - Retrieve a user by email
- `PUT /api/v1/users/{email}/activate` - Activate a user
- `PUT /api/v1/users/{email}/deactivate` - Deactivate a user

### Transactions
- `GET /api/v1/users/{email}/balance` - Retrieve the balance of a user
- `GET /api/v1/users/{email}/transactions` - Retrieve the list of transactions of a user
- `POST /api/v1/users/{email}/transactions/withdraw` - Add a deposit transaction
- `POST /api/v1/users/{email}/transactions/transfer` - Add a withdrawal transaction
- `POST /api/v1/users/{email}/transactions/deposit` - Add a transfer transaction with another user

### Swagger UI and OpenAPI
To access Swagger UI: http://localhost:8080/swagger-ui/index.html

To access OpenAPI3 docs: http://localhost:8080/v3/api-docs and http://localhost:8080/v3/api-docs.yaml

### Health-Check and Prometheus
To access health-checks: http://localhost:8080/actuator/health

To access prometheus metrics: http://localhost:8080/actuator/prometheus

## Future Improvements
- Proper response objects with exception handling turned into http status
- Input sanity, validation and error checking
- Integration tests
- Pagination can be moved to DOA instead of being handled in the Service layer
- Implement a second DOA to validate the swappable architecture
- Setup docker compose with prometheus collector, grafana and DB
- GitlabCI or GithubActions for CI pipeline
- Move `*DOATests` to `domain` package and have an inherited test that passes in the DOA implementation.
- Authentication/Authorization
- Logging