# Task Management API

## 🔧 How to Run

```bash
git clone git@github.com:Narvane/task-management-api.git
cd task-management-api
mvn quarkus:dev
```

## 📑 Documentation

- **Swagger (OpenAPI):** [http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)
- **Quarkus Dev UI:** [http://localhost:8080/q/dev-ui](http://localhost:8080/q/dev-ui)

> All endpoints are protected with **HTTP Basic Authentication**.  
> Use the **lock icon in Swagger** to authenticate.  
> **Username:** `admin`  
> **Password:** `123`

---

## 🧠 Project Structure

The project is divided into three main layers:

### `domain`
Contains models, exceptions, and **use cases**, representing the business logic of the application.  
This layer only uses essential dependencies such as **component injection** and **bean validations**, staying free from infrastructure concerns.

### `app`
This layer is responsible for exposing the **REST endpoints**, handling requests, and managing exceptions. It acts as the **entry point** into the domain layer.

### `persistence`
Holds the **DAOs**, which are solely responsible for mapping and transferring data between the database and the domain, keeping the domain decoupled from persistence logic.

---

## 🧱 Architecture

The structure follows **Clean Architecture** and **Domain-Driven Design (DDD)** principles.

This separation was chosen to demonstrate clarity, testability, and architectural knowledge.  
In a smaller real-world project, a more straightforward approach could be used, such as:

- Using JPA directly in the models
- Replacing use cases with simple services

---

## ✅ Testing

Unit tests were implemented for:

- **UseCases** (business logic)
- **Resources** (endpoints)
- **Repositories** (persistence layer)
