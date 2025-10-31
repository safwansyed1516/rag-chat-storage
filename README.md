# 🧠 RAG Chat Storage — Backend Microservices

A **Java 17 / Spring Boot 3.2+** microservices-based backend for managing chat sessions and messages.  
It supports creating and maintaining chat sessions, storing messages, renaming or favoriting sessions, and retrieving message history — with API-key security, rate limiting, centralized logging, and full Docker setup.

---

## ⚙️ Tech Stack

| Category | Technology |
|-----------|-------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.2.x |
| **Architecture** | Microservices (Gateway + Session + Message + Common) |
| **Build Tool** | Maven |
| **Database** | Oracle XE (via Docker) |
| **Security** | API Key authentication |
| **Rate Limiting** | In-memory limiter |
| **Documentation** | Swagger / OpenAPI (optional) |
| **Containerization** | Docker & Docker Compose |
| **Config Management** | .env environment variables |

---

## 🧩 Modules Overview

| Module | Port | Description |
|---------|------|-------------|
| **api-gateway** | 8080 | Entry point, routes requests, validates API Key, applies CORS + rate-limiting |
| **session-service** | 8081 | Handles session CRUD, rename, favorite/unfavorite, delete |
| **message-service** | 8082 | Handles message creation, retrieval, pagination |
| **oracle-xe** | 1521 | Oracle Database for persistence |

---

## 🧾 Environment Variables (.env.example)

| Variable | Description | Example |
|-----------|--------------|----------|
| SPRING_DATASOURCE_URL | Oracle JDBC URL | jdbc:oracle:thin:@oracle-xe:1521:XE |
| SPRING_DATASOURCE_USERNAME | Oracle user | raguser |
| SPRING_DATASOURCE_PASSWORD | Oracle password | ragpass |
| SPRING_JPA_HIBERNATE_DDL_AUTO | Schema strategy | update |
| SPRING_JPA_DATABASE_PLATFORM | Hibernate dialect | org.hibernate.dialect.OracleDialect |
| GATEWAY_API_KEY | API key required for all requests | secret-key |
| RATE_LIMIT_REQUESTS_PER_MINUTE | Max requests per minute per client | 60 |
| CORS_ALLOWED_ORIGINS | Allowed frontend origins | http://localhost:3000 |
| SESSION_SERVICE_PORT | Session service port | 8081 |
| MESSAGE_SERVICE_PORT | Message service port | 8082 |
| GATEWAY_SERVICE_PORT | Gateway port | 8080 |

---

## 🚀 Quick Start (Local + Docker)

### 1️⃣ Clone the repo
```
git clone https://github.com/<your-username>/rag-chat-storage.git
cd rag-chat-storage/infra
```

### 2️⃣ Create .env file
Copy .env.example → .env and adjust credentials if needed.

### 3️⃣ Build and run everything
```
docker-compose up --build
```

### 4️⃣ Verify services
| Service | URL | Expected |
|----------|-----|----------|
| API Gateway | http://localhost:8080/actuator/health | {"status":"UP"} |
| Session Service | http://localhost:8081/actuator/health | {"status":"UP"} |
| Message Service | http://localhost:8082/actuator/health | {"status":"UP"} |

### 5️⃣ Stop services
```
docker-compose down
```

---

## 🔐 API Key Authentication

Every request must include a header:
```
x-api-key: <your GATEWAY_API_KEY value>
```

If missing or invalid, the gateway returns:
```
{"status":401,"message":"Invalid API Key"}
```

---

## ⚡ Rate Limiting

Each client (by IP) is limited to RATE_LIMIT_REQUESTS_PER_MINUTE requests per minute.  
If exceeded, response is:
```
HTTP/1.1 429 Too Many Requests
Retry-After: 60
```

---

## 🌐 CORS
Allowed origins are defined in .env via:
```
CORS_ALLOWED_ORIGINS=http://localhost:3000
```

---

## 🧠 API Reference (Short Summary)

### 🗂️ Session Service (:8081/api/v1/sessions)
| Method | Endpoint | Description |
|---------|-----------|-------------|
| POST | / | Create new session |
| GET | / | Get all sessions |
| GET | /{id} | Get session by ID |
| PATCH | /{id}/rename | Rename a session |
| PATCH | /{id}/favorite | Toggle favorite |
| DELETE | /{id} | Delete session (+ messages) |

### 💬 Message Service (:8082/api/v1/messages)
| Method | Endpoint | Description |
|---------|-----------|-------------|
| POST | / | Create message |
| GET | /session/{sessionId} | Get paginated messages |
| DELETE | /session/{sessionId} | Delete all messages |

---

## 🧪 Health Check Endpoints
| Service | Endpoint |
|----------|-----------|
| Gateway | /actuator/health |
| Session Service | /actuator/health |
| Message Service | /actuator/health |

---

## 🧩 Build without Docker
```
mvn clean package
java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar
```

---

## 👨‍💻 Author
**Safwan Syed**  
Java Developer | Spring Boot | Microservices | AI Automation

---
