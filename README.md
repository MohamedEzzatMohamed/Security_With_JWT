# Security with JWT

This project is a demonstration of a secure Spring Boot application using JSON Web Tokens (JWT) for authentication and authorization. It provides a set of RESTful APIs for managing users and roles, with a focus on secure and robust development practices.

## Features

- **Authentication & Authorization**: Implements a JWT-based security mechanism with access tokens and refresh tokens.
- **API Endpoints**: Offers a comprehensive set of APIs for user management, role management, and token handling.
- **Relational Database**: Utilizes a relational database (MySQL) to store user and role information.
- **Testing**: Includes a suite of unit and integration tests to ensure the quality and robustness of the application.

## AI-Powered Development

This project was developed with the assistance of an AI agent, which played a key role in enhancing the codebase and ensuring its quality:

- **Code Enhancement**: The AI agent was instrumental in introducing best practices such as Data Transfer Objects (DTOs), mappers, and global exception handling, resulting in a more modular and maintainable codebase.
- **Test Generation**: The AI agent assisted in the creation and refinement of test cases, ensuring that the application is well-tested and reliable.

## Getting Started

To get started with this project, you will need to have the following software installed:

- Java 17 or higher
- Maven
- MySQL

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/security_with_jwt.git
   ```
2. **Configure the database**:
   - Create a new MySQL database.
   - Update the `application.properties` file with your database credentials.
3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

## Technologies Used

- **Spring Boot**: For building the application.
- **Spring Security**: for authentication and authorization.
- **JPA (Hibernate)**: For object-relational mapping.
- **MySQL**: As the relational database.
- **JWT**: For generating and verifying JSON Web Tokens.
- **MapStruct**: For mapping between DTOs and entities.
- **Lombok**: To reduce boilerplate code.
- **JUnit & Mockito**: For testing.
