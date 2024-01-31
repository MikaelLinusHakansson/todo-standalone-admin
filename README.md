# Todo App Standalone Admin

This project is a standalone admin panel for managing a todo application. It provides a secure authentication system for administrators to manage todo items. While the primary data access layer uses JPA and Hibernate for object-relational mapping and data manipulation, the project also includes an experimental implementation using JDBC for educational purposes.

## Features

- Secure user authentication
- Role-based access control for administrators
- CRUD operations for managing todo items
- Persistent storage with JPA and Hibernate
- Experimental JDBC data access objects (DAO) alongside JPA

## Technologies

- Spring Boot for the backend framework
- Spring Security for authentication and authorization
- JPA and Hibernate for ORM and data persistence
- Experimental JDBC implementation for direct database access
- Project Lombok for reducing boilerplate code

## Project Structure

- The `src/main/java/com/example/todoappstandaloneadmin/repository` directory contains JPA repositories (DTOs).
- The `src/main/java/com/example/todoappstandaloneadmin/dao` directory holds the JDBC implementation (DAOs), which is not actively used in the project but included for demonstration and learning purposes.

For more details on how to set up and use the application, please refer to the project documentation.