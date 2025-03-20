package Tests;

import BaseTest.BaseUrlHostfully;
import Pojos.PojosProperty;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PropertyRetrieval extends BaseUrlHostfully {

    /* Test 1: Retrieve a property with a valid ID */
    @Test
    public void testRetrievePropertyValidId() {
        // Create a property first
        PojosProperty property = new PojosProperty("Test Property", "123 Main St");
        String propertyId = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(property)
                .post("/properties")
                .then()
                .extract().path("id");

        // Retrieve the property
        given()
                .spec(spec)
                .when()
                .get("/properties/" + propertyId)
                .then()
                .statusCode(200)
                .body("id", equalTo(propertyId))
                .body("name", equalTo("Test Property"))
                .body("address", equalTo("123 Main St"));
    }

    /* Test 2: Retrieve a property with an invalid ID */
    @Test
    public void testRetrievePropertyInvalidId() {
        given()
                .spec(spec)
                .when()
                .get("/properties/invalid-id")
                .then()
                .statusCode(404)
                .body("message", equalTo("Property not found"));
    }
}
