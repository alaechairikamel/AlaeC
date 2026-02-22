# 📚 Bookshop API

Bookshop API is a Spring Boot REST application that allows users to register, authenticate using JWT, and manage books.  
The project is fully containerized using Docker and includes CI/CD automation with GitHub Actions.

---

## 🚀 Technologies Used

- Java 21
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA
- MySQL 8
- Docker & Docker Compose
- GitHub Actions

---

## 📦 Features

- User Registration
- User Login (JWT Authentication)
- Protected API endpoints
- Public API endpoints
- MySQL database integration
- Dockerized deployment
- Health monitoring with Actuator
- CI/CD pipeline with GitHub Actions

---

## ⚙️ Running the Project Locally

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/bookshop.git
cd bookshop
```

### 2️⃣ Run with Docker

```bash
docker compose up -d --build
```

### 3️⃣ Access the Application

```
http://localhost:8091
```

(Replace 8091 with your configured port)

---

## ❤️ Health Check

```
GET /actuator/health
```

Example:

```bash
curl http://localhost:8091/actuator/health
```

Expected Response:

```json
{"status":"UP"}
```

---

## 🔐 Authentication

### Register

```
POST /api/auth/register
```

### Login

```
POST /api/auth/login
```

After login, include JWT token in request headers:

```
Authorization: Bearer <your_token>
```

---

## 🐳 Docker Setup

The project uses two services:

- bookshop-app
- MySQL database

Docker Compose handles:
- Application container
- Database container
- Internal networking

---

## 🔄 CI/CD Pipeline

GitHub Actions automatically:

- Builds the project
- Runs tests
- Builds Docker image
- Deploys on push to main branch

Workflow file location:

```
.github/workflows/
```

---

## 📌 Environment Variables

- DB_URL
- DB_USER
- DB_PASSWORD
- JWT_SECRET

---

## 👨‍💻 Author

AlaeC

---

## 📜 License

This project was developed for educational and DevOps learning purposes.