# Bank Account Management System

## Introduction

This Bank Account Management System is a Java-based Spring Boot application designed to provide a RESTful API for managing customer bank accounts and transactions. It utilizes a microservices architecture with a service registry for easy service discovery and management.

## Project Structure

The application is structured into several Maven modules, each responsible for a different aspect of the system:

- **accountweb**: Frontend module handling the user interface and client interactions.
- **bankaccount**: Service handling all bank account operations.
- **banktransaction**: Service responsible for managing financial transactions.
- **register-server**: Eureka service registry for microservices discovery.

## Technologies Used

- **Java 17**: The core programming language used for building the application.
- **Spring Boot 3**: Framework used to create stand-alone, production-grade Spring-based applications with ease.
- **Maven**: Dependency management and build automation tool.
- **H2 Database**: In-memory database used for development and testing purposes.
- **Spring Data JPA**: Module of Spring for data access layer in a persistence store.
- **Spring Cloud Netflix Eureka**: Embedded service registry for discovery of microservices.
- **Spring WebFlux's WebClient**: Reactive web client used for non-blocking HTTP requests between microservices.
- **Swagger UI**: Automated API documentation and exploration tool integrated with Spring Boot.
- **Spring MVC**: Model-View-Controller architecture for creating web applications.
- **JUnit**: Framework used for writing and running repeatable automated tests.
- **Eureka Dashboard**: Web interface for monitoring and managing service instances registered with Eureka.

## Getting Started

### Prerequisites

- Java 17
- Maven 3.6+ or Maven Wrapper

### Setup

1. **Clone the Repository**
   First, clone the project repository to your local machine using Git:
   ```bash
   git clone [URL to the project repository]

2. **Build the Project**
   Navigate to the project root directory and build the project using Maven:
   ```
   ./mvnw clean install
   ```
### Running the Application

1. **Start the Register Server:**
   Navigate to the `register-server` directory and run the server using Maven.
   ```
   cd register-server  
   ./mvnw spring-boot:run
   ```
2. **Start the Bank Account Service:**
   Change to the `bankaccount` directory and start the service.
   ```
   cd bankaccount  
   ./mvnw spring-boot:run
   ```
3. **Start the Bank Transaction Service:**
   From the `banktransaction` directory, launch the service.
   ```
   cd banktransaction  
   ./mvnw spring-boot:run
   ```
4. **Start the Bank Account Web App:**
   From the `accountweb` directory, launch the service.
   ```
   cd accountweb  
   ./mvnw spring-boot:run
   ```
## Usage

After starting each service, you can visit the following URLs to interact with the system:

- **Eureka Service Dashboard**: `http://localhost:8761/`

- **Swagger UI for Bank Account Service**: `http://localhost:8200/swagger-ui.html`
- **Swagger UI for Bank Transaction Service**: `http://localhost:8100/swagger-ui.html`
- **AccountWeb Base URL**: `http://localhost:8080/accountweb/`

- **Open an Account**: `POST /api/accounts` with `customerID` and `initialCredit`.
- **View User Information**: `GET /api/users/{customerID}` to retrieve user details.

- **Open an Account:** `POST /api/accounts` with `customerID` and `initialCredit`.
- **View Customer Information:** `GET /api/customers/{customerID}` to retrieve customer details.

## Testing

Each module contains unit and integration tests. Run the following command in each module to execute tests:
   ```
   /mvnw test
   ```

