package BaseTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;

public class BaseUrlHostfully {

    protected RequestSpecification spec;

    @Before
    public void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://qa-assessment.svc.hostfully.com")
                .setAuth(RestAssured.basic("candidate@hostfully.com", "NaX5k1wFadtkFf"))
                .build();
    }
}
