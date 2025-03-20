# Hostfully API Test Plan

## Objective
Validate the functionality and reliability of the Hostfully API endpoints (`/properties` and `/bookings`) hosted at `https://qa-assessment.svc.hostfully.com`. Ensure correct HTTP status codes, appropriate responses, and booking overlap prevention per the QA Engineer assessment requirements.

## Scope
### In Scope
- **Endpoints**: 
  - `POST /properties`: Create properties.
  - `GET /properties/{id}`: Retrieve properties.
  - `POST /bookings`: Create bookings with overlap validation.
- **Test Types**: Positive and negative scenarios.
- **Tools**: Rest Assured, Java, JUnit 4.

### Out of Scope
- Performance, security, or UI testing.
- Endpoints beyond `/properties` and `/bookings`.

## Test Strategy
- **Approach**: Automated API testing using Rest Assured.
- **Environment**: QA assessment server (`https://qa-assessment.svc.hostfully.com`).
- **Authentication**: HTTP Basic Auth with credentials `candidate@hostfully.com` and `NaX5k1wFadtkFf`.
- **Data**: Test-specific data created via POJOs (`PojosProperty`, `PojosBooking`).
- **Assertions**: Status codes, response body fields, and error messages.

## Test Cases

### Property Creation
| ID   | Test Case                          | Input                                      | Expected Outcome                   |
|------|------------------------------------|--------------------------------------------|------------------------------------|
| PC-1 | Valid property creation           | Name: "Test Property", Address: "123 Main St" | 201, ID not null, fields match    |
| PC-2 | Missing name                      | Address: "123 Main St"                    | 400, "Name is required"           |
| PC-3 | Empty name                        | Name: "", Address: "123 Main St"          | 400, "Name cannot be empty"       |

### Property Retrieval
| ID   | Test Case                          | Input                                      | Expected Outcome                   |
|------|------------------------------------|--------------------------------------------|------------------------------------|
| PR-1 | Retrieve valid property           | Valid property ID                         | 200, ID and fields match          |
| PR-2 | Retrieve invalid property         | ID: "invalid-id"                          | 404, "Property not found"         |

### Booking Creation
| ID   | Test Case                          | Input                                      | Expected Outcome                   |
|------|------------------------------------|--------------------------------------------|------------------------------------|
| BC-1 | Valid booking                     | Property ID, "2024-01-01" to "2024-01-05", "John Doe" | 201, ID not null, fields match |
| BC-2 | Missing guest name                | Property ID, "2024-01-01" to "2024-01-05" | 400, "Guest name is required"     |
| BC-3 | Overlapping bookings              | "2024-01-01" to "2024-01-05", then "2024-01-03" to "2024-01-07" | 409, "Booking overlaps..." |
| BC-4 | Non-overlapping bookings          | "2024-01-01" to "2024-01-05", then "2024-01-05" to "2024-01-10" | 201, Success                  |
| BC-5 | Invalid dates (end before start)  | "2024-01-05" to "2024-01-01"             | 400, "End date must be after..."  |

## Execution
- **Environment Setup**: Install JDK 11+, Maven, clone repo, run `mvn clean install`.
- **Run Tests**: `mvn test` from project root.
- **Duration**: ~5-10 minutes for all tests.
- **Reporting**: Console output; failures logged with details.

## Risks & Mitigation
- **Risk**: API unavailable.
  - **Mitigation**: Verify connectivity before execution.
- **Risk**: Incorrect credentials.
  - **Mitigation**: Confirm `candidate@hostfully.com` and `NaX5k1wFadtkFf` in `BaseUrlHostfully.java`.

## Deliverables
- Automated test suite in GitHub repository.
- README with setup and execution steps.
- This test plan.

## Schedule
- **Development**: March 18-19, 2025.
- **Execution & Submission**: By March 19, 2025.
