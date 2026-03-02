# ResortHub

## Introduction
### 1.Purpose

The **Resort Booking & Real-time Chat System** is designed to provide a comprehensive online platform that connects customers, resort owners, staff, and administrators within a centralized system.

The system aims to:

- Provide an online resort booking platform for customers.
- Allow customers to submit booking requests and track booking status in real time.
- Enable resort staff and owners to review, approve, or reject booking requests.
- Offer a real-time chat feature between customers and resorts for each booking.
- Support administrators in monitoring and managing the overall system operations.

### 2. Objectives

The main objectives of the system are:

- Optimize the resort booking process.
- Reduce manual operations and paperwork.
- Increase transparency in booking and approval workflows.
- Improve overall customer experience through real-time interaction and clear status tracking.

By integrating booking management, payment processing, contract handling, and communication features into a single platform, the system ensures efficiency, reliability, and scalability for all stakeholders.

## Tech Stack
- Java 21
- Spring Boot
- MySQL

## Installation
1. Clone repo
2. Run mvn install
3. Run application

## Project Structure
---text
RESORTHUB-backend/
│
├── src/
│   ├── main/
│   │   ├── java/com/threektechone/resorthub/
│   │   │   ├── config/          # Security, JWT, WebSocket configuration
│   │   │   ├── controller/      # REST Controllers
│   │   │   ├── service/         # Business logic
│   │   │   │   └── impl/
│   │   │   ├── repositories/      # JPA Repositories
│   │   │   ├── models/          # JPA Entities
│   │   │   ├── dto/             # Request & Response DTOs
│   │   │   ├── mapper/          # MapStruct / manual mappers
│   │   │   ├── websocket/       # WebSocket handlers
│   │   │   ├── enums/       # Enum value of attribute of entity
│   │   │   └── ResortHubApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   │
│   └── test/                    # Unit & Integration tests
│
├── docs/                        # API & system documentation
├── pom.xml                      # Maven configuration
└── README.md
---
## API Overview
---

### 1. Authentication Module

| No | Method | Endpoint | Purpose |
|----|--------|----------|---------|
| 1 | POST | `/api/auth/register` | Register account |
| 2 | POST | `/api/auth/verify-otp` | Verify OTP |
| 3 | POST | `/api/auth/login` | Login account |
| 4 | POST | `/api/auth/refresh-token` | Generate new access token when expired |
| 5 | POST | `/api/auth/logout` | Logout account |

---

### 2. User Module (Admin)

| No | Method | Endpoint | Purpose |
|----|--------|----------|---------|
| 1 | GET | `/api/admin/users` | View user list with pagination, filter and search |
| 2 | PUT | `/api/admin/users/{id}` | Update user information |
| 3 | DELETE | `/api/admin/users/{id}` | Inactive or delete user account |
| 4 | PATCH | `/api/admin/users/{id}/role` | Insert, update and retrieve user role |
| 5 | PATCH | `/api/admin/users/{id}/status` | Insert, update and retrieve user status |

---

### 3. Resort Module

| No | Method | Endpoint | Purpose |
|----|--------|----------|---------|
| 1 | POST | `/api/owner/resorts` | Register new resort for staff review |
| 2 | PATCH | `/api/staff/resorts/{id}/status` | Accept or reject resort after review |
| 3 | GET | `/api/resorts` | View accepted resort list |
| 4 | GET | `/api/staff/resorts` | View accepted and rejected resorts |
| 5 | GET | `/api/resorts/{id}` | View resort details |

---

### 4. Booking Module

| No | Method | Endpoint | Purpose |
|----|--------|----------|---------|
| 1 | POST | `/api/customer/bookings` | Customer books a resort |
| 2 | DELETE | `/api/customer/bookings/{id}` | Cancel booking if not approved by owner |
| 3 | PATCH | `/api/owner/bookings/{id}/status` | Owner accepts or rejects booking (PENDING) |
| 4 | GET | `/api/customer/bookings` | Customer views booking list and status |

---

### 5. Payment Module

| No | Method | Endpoint | Purpose |
|----|--------|----------|---------|
| 1 | POST | `/api/customer/payments` | Generate payment process and redirect to gateway |
| 2 | POST | `/api/payments/callback` | Receive payment result and update status |
| 3 | GET | `/api/customer/payments/{id}` | Check payment status of a booking |

---

### 6. Chat Module

## WebSocket Endpoint
- `/ws/chat` → Real-time connection between Customer, Owner, and Staff  

## Topic Channels
- `/topic/booking/{bookingId}`
- `/topic/resort/{resortId}`

### REST APIs

| No | Method | Endpoint | Purpose |
|----|--------|----------|---------|
| 1 | GET | `/api/chat/booking/{bookingId}` | Get booking chat history |
| 2 | GET | `/api/chat/resort/{resortId}` | Get resort chat history |

---

### 7. Contract Module

| No | Method | Endpoint | Purpose |
|----|--------|----------|---------|
| 1 | POST | `/api/staff/contracts` | Staff creates contract after resort approval |
| 2 | POST | `/api/owner/contracts` | Owner creates contract after booking approval |

---

### 8. Lost Item Report Module

| No | Method | Endpoint | Purpose |
|----|--------|----------|---------|
| 1 | POST | `/api/customer/lost-items` | Submit lost item report for a booking |
| 2 | PATCH | `/api/staff/lost-items/{id}` | Staff processes and updates report status |

---

### 9. Dashboard Module

| No | Method | Endpoint | Purpose |
|----|--------|----------|---------|
| 1 | GET | `/api/owner/dashboard` | Revenue statistics & performance metrics |
| 2 | GET | `/api/admin/dashboard` | System overview for administrators |

---
