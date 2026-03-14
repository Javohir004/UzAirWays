# ✈️ UzAirWays — Airline Booking System

A production-ready airline ticket booking system built with **Java 17** and **Spring Boot**,
deployed on a real server for 1 month. The system allows users to search flights, book tickets,
cancel reservations, and manage payments.

---

## 🚀 Features

- Flight search and ticket booking
- Booking cancellation up to 24 hours before departure
- Automatic refund processing for cancelled bookings
- Flight and payment history tracking
- User complaint and feedback management
- Role-based access control (User / Admin / Owner)

---

## 👥 Roles

### 👤 User
- Browse flights and book tickets
- Cancel bookings (up to 24h before departure)
- Enter passport / ID card details
- Simulated online payment
- View personal booking and payment history

### 🛠️ Admin
- Monitor and manage flights
- Delay or cancel flights due to weather or technical issues
- Create new flight routes by region
- Review user complaints and feedback

### 👑 Owner
- Full system overview: users, admins, flights, payments
- Add and remove admins
- Complete control over the system

---

## 🏗️ Architecture — Microservices

The system is built as a **microservice architecture**, with each service running independently:

| Service | Responsibility |
|---|---|
| User Service | User management and authentication (JWT) |
| Flight Service | Flights and routes |
| Booking Service | Booking logic and cancellations |
| Payment Service | Payment processing and refunds (simulated) |
| Notification Service | Email / push notifications |
| Config Service | Centralized configuration |

All services communicate through **Spring Cloud Gateway** and are registered via **Eureka Service Discovery**.

---

## 🛠️ Tech Stack

| Category | Technologies |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot, Spring Cloud (Gateway, Eureka, Config) |
| Security | Spring Security, JWT |
| Database | PostgreSQL, Spring Data JPA, Hibernate |
| Messaging | Kafka |
| Build | Gradle |
| DevOps | Docker, Docker Compose, GitHub Actions (CI/CD) |
| API Docs | Swagger UI (OpenAPI) |

---

## ⚙️ CI/CD

This project uses **GitHub Actions** for continuous integration and deployment:
- Automated build and test on every push
- Docker image build and push
- Automatic deployment to the server

---

## 🐳 Running Locally

```bash
git clone https://github.com/Javohir004/UzAirWays.git
cd UzAirWays
docker-compose up --build
```

The application will start on `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 📌 Notes

- Payment integration is simulated (mock) — not connected to a real payment gateway
- Server deployment has been stopped; run locally using Docker Compose

---

## 📫 Contact

- GitHub: [Javohir004](https://github.com/Javohir004)
