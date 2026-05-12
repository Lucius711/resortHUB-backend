## Project Structure
```text
RESORTHUB-backend/
│
├── src/
│   ├── main/
│   │   ├── java/com/threektechone/resorthub/
│   │   │   ├── common/          # Security, JWT,Data and WebSocket configuration
│   │   │   ├── config/          # Security, JWT,Data and WebSocket configuration
│   │   │   ├── controller/      # REST Controllers
│   │   │   ├── dto/             # Request & Response DTOs
│   │   │   ├── enums/       # Enum value of attribute of entity
│   │   │   ├── helper/       # Enum value of attribute of entity
│   │   │   ├── mapper/          # MapStruct / manual mappers
│   │   │   ├── models/          # JPA Entities
│   │   │   ├── policy/          # JPA Entities
│   │   │   ├── repositories/      # JPA Repositories
│   │   │   ├── scheduler/      # JPA Repositories
│   │   │   ├── service/         # Business logic
│   │   │   │   └── impl/
│   │   │   ├── strategy/      # JPA Repositories
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
├── uploads/                      # Maven configuration
├── Dockerfile                    # Maven configuration
├── docker-compose.yml                     # Maven configuration
├── package.json                     # Maven configuration
├── package-lock.json                      # Maven configuration
├── pom.xml                      # Maven configuration
└── README.md
```