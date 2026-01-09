📂 Project StructureOrganize 

├── src/main/java/com/stapubox/Stapubox/

│   ├── controllers/      # REST API Endpoints (Venue, Slot, Booking, Sport)

│   ├── services/         # Business logic (Concurrency, External API sync)

│   ├── repositories/     # Database access (Pessimistic Locking logic)

│   ├── entities/         # MySQL database models

│   ├── dtos/             # Data Transfer Objects (Prevents JSON recursion)

│   └── enums/            # BookingStatus (CONFIRMED, CANCELLED)

├── src/main/resources/

│   └── application.properties # DB connection string & external API URL

├── docs/                 # ER Diagram & Postman Collection

├── .gitignore            # Crucial: Ignore mysql_data/ and target/

├── docker-compose.yml    # App + MySQL setup

├── Dockerfile            # Java environment setup

└── README.md             # Fully documented assumptions & APIs

## Assumptions & Logic
Booking Rule: Each booking corresponds to exactly one slot (1:1).
Immutability: Once a slot is booked, the time range is locked and cannot be edited.
Conflict Prevention: Uses Pessimistic Locking (SELECT FOR UPDATE) to handle high-traffic concurrency.
External Integration: Sports are dynamically validated against stapubox.com/sportslist/.

-🚀 1. Getting Started
Prerequisites
Docker and Docker Compose installed.
Postman (for testing the provided API collection).

 ## Installation & Setup
Clone the repository and navigate to the project root.

Launch the stack:

Bash
docker-compose up --build


🛠️ Mandatory API List
A. Venue & Sport Management

POST /api/sports: Sync/Register sports from the mandatory external API (https://stapubox.com/sportslist/).

POST /api/venues: Add a new sports venue.

GET /api/venues: List all registered venues.

B. Slot Management

POST /api/venues/{venueId}/slots: Add time slots for a specific venue (Includes logic to prevent overlapping ranges).

GET /api/venues/available: Fetch available venues for a given sport and time range.

C. Booking Operations

POST /api/bookings: Create a booking safely using pessimistic locks.

PUT /api/bookings/{id}/cancel: Cancel a booking and release the slot immediately.

⚙️ Docker Setup (docker-compose.yml)

Database: MySQL 8.0 with a volume mapping to store data.

Healthcheck: The application service should depend on the database being "healthy" to prevent startup crashes.

Environment: Use .env files to store database credentials.

⚖️ Database Design
Indexes: On venue_id, sport_code, and is_booked.

Constraints: Unique constraints on time slots to prevent overlapping data at the DB level.






