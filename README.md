<p align="center">
  <img src="https://d9hhrg4mnvzow.cloudfront.net/lp.3035tech.com/96c1669d-logo-teach-horiz-branco_1000000000000000000028.png" alt="3035tech Logo" width="200"/>
</p>

# ToDoApp — Task Management API

This project was developed as the final challenge of **Module 6** in the [3035Teach](https://lp.3035tech.com/) bootcamp.  
It is a fully functional backend application designed for managing personal tasks using **Spring Boot**, **JWT-based authentication**, and a **PostgreSQL** database. The architecture follows best practices in modular design, clean code, and RESTful API development.

## Features

- User authentication and authorization using **JWT**
- Secure **role-based access control** for task ownership
- **CRUD operations** for personal tasks
- Persistent storage using **Spring Data JPA**
- Fully documented API using **Swagger / OpenAPI**
- Centralized and semantic **exception handling**
- Modular and scalable **MVC architecture**

## Technologies

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Tokens)
- Swagger (SpringDoc OpenAPI)
- Maven

## System Overview

The application is structured into distinct layers to ensure separation of concerns and code maintainability:

## Data Models

### User Entity

| Field     | Type   | Description                  |
|-----------|--------|------------------------------|
| id        | Long   | Unique identifier            |
| username  | String | Unique user name             |
| password  | String | Hashed user password         |

### Task Entity

| Field       | Type   | Description                                   |
|-------------|--------|-----------------------------------------------|
| id          | Long   | Unique identifier                             |
| titulo      | String | Title of the task                             |
| descricao   | String | Task description                              |
| status      | Enum   | Task state: PENDENTE, EM_ANDAMENTO, CONCLUIDA |
| user        | User   | Reference to the task's owner (foreign key)   |

## API Endpoints

### Public Endpoints

These routes are accessible without authentication:

- `POST /auth/register` — Register a new user
- `POST /auth/login` — Authenticate user and receive a JWT token

### Protected Endpoints

These routes require a valid JWT token in the `Authorization` header:

> Each user can only access and manage their own tasks.

- `GET /tasks` — List all tasks for the authenticated user, with optional filtering by `status`
- `GET /tasks/{id}` — Get task details by ID (must belong to user)
- `POST /tasks` — Create a new task
- `PATCH /tasks/{id}` — Update task status or description
- `DELETE /tasks/{id}` — Remove a task by ID

## Authentication Workflow

1. **User registers via** `POST /auth/register`
2. **Login via** `POST /auth/login` and receive a JWT
3. **Attach the token** to all protected requests using:

```http
Authorization: Bearer <your-jwt-token>
