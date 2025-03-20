package Tests;

import BaseTest.BaseUrlHostfully;
import Pojos.PojosProperty;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class PropertyCreation extends BaseUrlHostfully {

    /* Test 1: Create a property with valid data */
    @Test
    public void testCreatePropertyValid() {
        PojosProperty property = new PojosProperty("Test Property", "123 Main St");
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(property)
                .when()
                .post("/properties")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Test Property"))
                .body("address", equalTo("123 Main St"));
    }

    /* Test 2: Create a property with missing name (invalid) */
    @Test
    public void testCreatePropertyInvalidMissingName() {
        PojosProperty property = new PojosProperty();
        property.setAddress("123 Main St"); // Name is null
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(property)
                .when()
                .post("/properties")
                .then()
                .statusCode(400)
                .body("message", equalTo("Name is required"));
    }

    /* Test 3: Create a property with empty name (invalid) */
    @Test
    public void testCreatePropertyInvalidEmptyName() {
        PojosProperty property = new PojosProperty("", "123 Main St");
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(property)
                .when()
                .post("/properties")
                .then()
                .statusCode(400)
                .body("message", equalTo("Name cannot be empty"));
    }



}
