package Tests;

import BaseTest.BaseUrlHostfully;
import Pojos.PojosBooking;
import Pojos.PojosProperty;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingCreation_Guest extends BaseUrlHostfully {

    /* Helper method to create a property and return its ID */
    private String createProperty() {
        PojosProperty property = new PojosProperty("Test Property", "123 Main St");
        return given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(property)
                .post("/properties")
                .then()
                .extract().path("id");
    }

    /* Test 1: Create a booking with valid data */
    @Test
    public void testCreateBookingValid() {
        String propertyId = createProperty();
        PojosBooking booking = new PojosBooking(propertyId, "2024-01-01", "2024-01-05", "John Doe");
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post("/bookings")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("propertyId", equalTo(propertyId))
                .body("startDate", equalTo("2024-01-01"))
                .body("endDate", equalTo("2024-01-05"))
                .body("guestName", equalTo("John Doe"));
    }

    /* Test 2: Create a booking with missing guest name (invalid) */
    @Test
    public void testCreateBookingInvalidMissingGuestName() {
        String propertyId = createProperty();
        PojosBooking booking = new PojosBooking(propertyId, "2024-01-01", "2024-01-05", null);
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post("/bookings")
                .then()
                .statusCode(400)
                .body("message", equalTo("Guest name is required"));
    }

    /* Test 3: Create overlapping bookings (should fail) */
    @Test
    public void testCreateOverlappingBooking() {
        String propertyId = createProperty();

        // Create first booking: Jan 1 to Jan 5
        PojosBooking booking1 = new PojosBooking(propertyId, "2024-01-01", "2024-01-05", "John");
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(booking1)
                .post("/bookings");

        // Try overlapping booking: Jan 3 to Jan 7
        PojosBooking booking2 = new PojosBooking(propertyId, "2024-01-03", "2024-01-07", "Jane");
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(booking2)
                .when()
                .post("/bookings")
                .then()
                .statusCode(409)
                .body("message", equalTo("Booking overlaps with existing reservation"));
    }

    /* Test 4: Create non-overlapping bookings (should succeed) */
    @Test
    public void testCreateNonOverlappingBooking() {
        String propertyId = createProperty();

        // Create first booking: Jan 1 to Jan 5
        PojosBooking booking1 = new PojosBooking(propertyId, "2024-01-01", "2024-01-05", "John");
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(booking1)
                .post("/bookings");

        // Create non-overlapping booking: Jan 5 to Jan 10
        PojosBooking booking2 = new PojosBooking(propertyId, "2024-01-05", "2024-01-10", "Jane");
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(booking2)
                .when()
                .post("/bookings")
                .then()
                .statusCode(201);
    }

    /* Test 5: Create a booking with invalid dates (end before start) */
    @Test
    public void testCreateBookingInvalidDates() {
        String propertyId = createProperty();
        PojosBooking booking = new PojosBooking(propertyId, "2024-01-05", "2024-01-01", "John");
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post("/bookings")
                .then()
                .statusCode(400)
                .body("message", equalTo("End date must be after start date"));
    }
}
