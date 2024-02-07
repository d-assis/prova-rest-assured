package com.montanha.gerenciador.test.status;

import com.montanha.gerenciador.test.utils.TestBase;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestaStatus extends TestBase {

    @Test
    public void testaStatusApp() {
        given()
        .when()
            .get("/v1/status")
        .then()
            .statusCode(200);
    }
}
