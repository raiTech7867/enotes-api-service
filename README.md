# eNotes API Service

A professional RESTful backend service for **eNotes**, built with **Java 17** and **Spring Boot 4**.

The project follows a clean layered architecture and is designed to provide a scalable foundation for managing notes, categories, users, and application-level features.

---

## 🚀 Project Overview

**eNotes API Service** is a backend REST API designed for a notes management application.

The project focuses on:

* Clean and maintainable backend architecture
* RESTful API development
* Database persistence using Spring Data JPA
* MySQL integration
* DTO-based API communication
* Separation of concerns
* Scalable service-layer architecture
* Secure and controlled repository development

---

## 🛠️ Tech Stack

| Technology            | Usage                         |
| --------------------- | ----------------------------- |
| **Java 17**           | Programming language          |
| **Spring Boot 4.0.6** | Backend framework             |
| **Spring Web**        | REST API development          |
| **Spring Data JPA**   | Database persistence          |
| **Hibernate**         | ORM                           |
| **MySQL**             | Relational database           |
| **ModelMapper 3.2.4** | Entity ↔ DTO mapping          |
| **Lombok**            | Boilerplate code reduction    |
| **Maven**             | Dependency management & build |
| **Spring Boot Test**  | Testing                       |

---

## 🏗️ Project Architecture

The application follows a layered architecture to keep business logic organized and maintainable.

```text
Client
   │
   ▼
Controller Layer
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
MySQL Database
```

### Layers

**Controller**

Responsible for handling HTTP requests and returning API responses.

**Service**

Contains the application's business logic and coordinates operations between controllers and repositories.

**Repository**

Handles database communication using Spring Data JPA.

**Entity**

Represents database tables and their relationships.

**DTO**

Defines the data exchanged between the API and clients.

**Config**

Contains application configuration and supporting configuration classes.

---

## 📁 Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── raiTech/
    │           ├── config/
    │           ├── controller/
    │           ├── dto/
    │           ├── entity/
    │           ├── repository/
    │           ├── service/
    │           │   └── impl/
    │           └── ENoteApiServiceApplication.java
    │
    └── resources/
        └── application.properties
```

---

## 📌 Current API

### Category Management

The current application provides category-related REST APIs.

#### Save Category

```http
POST /api/v1/category/save-category
```

Creates a new category.

#### Get Categories

```http
GET /api/v1/category/category
```

Retrieves available categories.

#### Get Active Categories

```http
GET /api/v1/category/active-category
```

Retrieves active categories.

---

## ⚙️ Configuration

The application uses environment variables for database configuration.

### Environment Variables

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

Example:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Using environment variables helps keep database credentials outside the source code.

> **Never commit production database credentials, passwords, API keys, or other secrets to the repository.**

---

## ▶️ Running the Project

### Prerequisites

Make sure the following are installed:

* Java 17
* Maven
* MySQL
* Git

### Database

Create a MySQL database for the application and configure the required environment variables.

### Start the Application

The application can be started using Maven or directly from an IDE.

```bash
mvn spring-boot:run
```

Once started, the API will be available on the configured application port.

---

## 🔐 Repository Security

This repository is **publicly available** for learning, reference, and portfolio purposes.

Public visibility does **not** mean that everyone has permission to modify the repository.

### Repository Access

* The repository is publicly visible.
* Anyone can view the source code.
* Anyone can clone the repository.
* Anyone can fork the repository for personal use or experimentation.
* Direct write access is restricted to authorized repository collaborators.
* Protected branches are controlled by the repository owner.
* Repository development and merging are managed by the repository owner.

### Important

**GitHub repository security and API security are separate concerns.**

Spring Security protects the running application and its APIs.

GitHub access controls protect the source code and repository.

---

## 🔒 Security Practices

The project follows security-conscious development practices, including:

* Keeping database credentials outside the source code
* Using environment-based configuration
* Restricting repository write access
* Protecting important branches
* Avoiding sensitive information in commits
* Separating application security from repository access control

Security features will continue to evolve as the project develops.

---

## 🧪 Testing

The project uses **Spring Boot Test** for application testing.

Tests can be executed using:

```bash
mvn test
```

---

## 📈 Development Roadmap

The project is being developed incrementally.

Planned areas include:

* [ ] User management
* [ ] Authentication & authorization
* [ ] JWT-based security
* [ ] Notes management
* [ ] Notes CRUD APIs
* [ ] Search and filtering
* [ ] Pagination
* [ ] Role-based access control
* [ ] Request validation
* [ ] Global exception handling
* [ ] Standardized API responses
* [ ] Swagger / OpenAPI documentation
* [ ] Docker support
* [ ] CI/CD integration
* [ ] Cloud deployment

> Roadmap items may change as the architecture and application requirements evolve.

---

## 🎯 Project Goals

The primary goals of this project are to build a backend that is:

* **Clean** — organized code with clear responsibilities
* **Maintainable** — easy to understand and extend
* **Scalable** — structured for future features
* **Secure** — following modern application and repository security practices
* **Testable** — supported by automated testing
* **Production-oriented** — following practical backend development patterns

---

## 📊 Development Status

**Status:** 🚧 Active Development

The project is currently under development, with additional backend functionality and security features being added progressively.

---

## 👨‍💻 Author

**Rahul**

Java & Android Developer

Focused on building scalable applications using:

* Java
* Spring Boot
* REST APIs
* Android
* Kotlin
* Jetpack Compose
* MySQL

---

## 📄 License

This project is intended primarily for **learning, experimentation, and portfolio/reference purposes**.

Please check the repository license for the applicable usage terms.
