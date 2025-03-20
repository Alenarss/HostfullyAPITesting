# Hostfully API Test Suite

Tests for Hostfully API (`/properties` and `/bookings`) using Rest Assured, Java, and JUnit 4. Validates property creation, retrieval, and booking creation with overlap checks.

## Prerequisites
- JDK 11+ (tested with 11.0.12)
- Maven 3.6+
- Optional: IDE (e.g., IntelliJ)

## Dependencies
In `pom.xml`:
- `io.rest-assured:rest-assured:5.5.0`
- `junit:junit:4.13.2`
- `org.hamcrest:hamcrest:2.2`

## How to Run
1. Clone: `git clone <repository-url> && cd HostfullyAPITesting`
2. Install: `mvn clean install`
3. Run: `mvn test`

## Structure
- `src/main/java/Pojos`: `PojosProperty.java`, `PojosBooking.java`
- `src/test/java/BaseTest`: `BaseUrlHostfully.java`
- `src/test/java/Tests`: `PropertyCreation.java`, `PropertyRetrieval.java`, `BookingCreation_Guest.java`

## Test Cases
**PropertyCreation.java**
- Valid creation (201)
- Missing name (400)
- Empty name (400)

**PropertyRetrieval.java**
- Valid ID (200)
- Invalid ID (404)

**BookingCreation_Guest.java**
- Valid booking (201)
- Missing guest name (400)
- Overlapping (409)
- Non-overlapping (201)
- Invalid dates (400)
