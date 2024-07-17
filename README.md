# File Data Transfer Agent

## Description
This microservice is responsible for transferring csv files provided by our client to the
user-service in order to accomplish data-migration.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Running Tests](#running-tests)
- [Dockerization](#dockerization)
- [Contact Information](#contact-information)

## Prerequisites
List the software required to run and develop the microservice: 
* Java 17
* Gradle
* Kafka
* PostgreSql
* Docker

## Installation
### Cloning the Repository
```sh
git clone https://github.com/mlipilov/file-data-transfer-agent.git
cd file-data-transfer-agent
```
### Building the project
```sh
./gradlew build
```
### Running locally
```sh
./gradlew bootRun
```

## Configuration
### Application properties
Application properties are divided into several parts:
* [application.yaml](src%2Fmain%2Fresources%2Fapplication.yaml) - activates all sub-files
* [application-batch.yaml](src%2Fmain%2Fresources%2Fapplication-batch.yaml) - configures Spring Batch
* [application-datasource.yaml](src%2Fmain%2Fresources%2Fapplication-datasource.yaml) - configures Datasource
* [application-kafka.yaml](src%2Fmain%2Fresources%2Fapplication-kafka.yaml) - configures Kafka
* [application-liquibase.yaml](src%2Fmain%2Fresources%2Fapplication-liquibase.yaml) - configures Liquibase
* [application-log.yaml](src%2Fmain%2Fresources%2Fapplication-log.yaml) - configures Logging
### Environment Variables
These can be edited to satisfy your local needs
* DB_LOGIN - Database login
* DB_PASS - Database password
* DB_URL - Database URL
* KAFKA_SERVER_URL - Kafka bootstrap server url

## Usage
### API and request examples
API and request example can be found on swagger [click](http://localhost:8080/swagger-ui/index.html#/)
### Authentication
We use open source solution called Keycloak

## Running tests
### Unit tests
In order to run unit tests only execute this command:
```sh
./gradlew test
```
### Integration tests:
In order to run integration tests only execute this command:
```sh
./gradlew integrationTest
```
### Test Coverage
To measure the test coverage of your application, you can use Jacoco, which is integrated with Gradle. 
To generate a test coverage report, run the following command:
```sh
./gradlew jacocoTestReport
```
This command will generate a coverage report: [index.html](build%2Freports%2Fjacoco%2Ftest%2Fhtml%2Findex.html). 
You can open the index.html file in this directory to view the detailed coverage report.

## Dockerization
### Building Docker Image
```sh
docker build -t file-data-transfer-agent-image .
```
### Running with Docker
```sh
docker run -p 8080:8080 file-data-transfer-agent-image
```

**Attention!** When running locally be sure you have provided all the dependencies like DB so the 
container can start with no issues.

## Contact Information
If you have any questions, issues, or suggestions regarding this project, 
please feel free to reach out to the maintainers listed below.
### Maintainers
Mykhailo Lipilov
* Role: Java Developer
* Email: lipilov.wm@gmail.com