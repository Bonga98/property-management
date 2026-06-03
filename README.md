# Dywili Property Management API

A Spring Boot REST API for managing properties and users.

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Spring Boot 3.2 | Backend framework |
| Spring Security + JWT | Authentication |
| BCrypt | Password encryption |
| MySQL | Database |
| Spring Data JPA | Database access |
| SpringDoc OpenAPI | API documentation |
| Lombok | Reduce boilerplate code |

---

## Getting Started

### Prerequisites
- Java 18
- MySQL (via XAMPP or standalone)
- Maven

### Setup
1. Start MySQL
2. Create the database:
   ```sql
   CREATE DATABASE `pmsdb-dev`;
   ```
3. Run the application — Hibernate will create the tables automatically
4. API is available at: `http://localhost:8080`
5. Swagger UI: `http://localhost:8080/swagger-ui/index.html`

---

## Authentication Flow

### Login
```
POST /api/v1/user/login
    → BCrypt checks password 
    → JwtUtil generates token
    → Token returned to client
```

### Accessing Protected Endpoints
```
GET /api/v1/properties  (with token in header)
    → JwtFilter validates token 
    → Email extracted from token
    → SecurityContext updated
    → Controller runs and returns data
```

### No Token
```
GET /api/v1/properties  (no token)
    → JwtFilter finds nothing
    → 403 Forbidden 
```

### How to use the token
After login, attach the token to every request in the `Authorization` header:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## API Endpoints

### User
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | `/api/v1/user/register` | Register a new user | No |
| POST | `/api/v1/user/login` | Login and get JWT token | No |

### Properties
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| GET | `/api/v1/properties` | Get all properties | Yes |
| POST | `/api/v1/properties` | Add a new property | Yes |
| PUT | `/api/v1/properties/{id}` | Update a property | Yes |
| PATCH | `/api/v1/properties/update-description/{id}` | Update description only | Yes |
| DELETE | `/api/v1/properties/{id}` | Delete a property | Yes |

---

## Security

- Passwords are hashed using **BCrypt** before being stored — plain text passwords are never saved
- Authentication uses **JWT tokens** valid for **24 hours**
- All property endpoints require a valid token in the `Authorization` header

---

## Design Principles

This project follows SOLID principles.
See [SOLID.md](docs/SOLID.md) for a full breakdown with code examples.
