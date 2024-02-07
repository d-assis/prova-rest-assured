package com.montanha.gerenciador.test.auth;

import com.montanha.gerenciador.test.utils.TestBase;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class TestaLogin extends TestBase {
    @Test
    public void loginValidoAdmin(){
        given()
            .body("{\"email\": \"admin@email.com\",\"senha\": \"654321\"}")
            .header("Content-Type","application/json")
        .when()
            .post("/v1/auth")
        .then()
           .assertThat().statusCode(200)
        .and()
           .body(matchesJsonSchemaInClasspath("authSchema.json"));

    }

    @Test
    public void loginValidoUsuario(){
        given()
            .body("{\"email\": \"usuario@email.com\",\"senha\": \"123456\"}")
            .header("Content-Type","application/json")
        .when()
            .post("/v1/auth")
        .then()
            .assertThat().statusCode(200);

    }

    @Test
    public void loginInvalidoSenhaIncorreta(){
        given()
            .header("Content-Type","application/json")
            .body("{\"email\": \"usuario@email.com\",\"senha\": \"000000\"}")
        .when()
            .post("/v1/auth")
        .then()
            .statusCode(401);
    }

    @Test
    public void loginInvalidoUsuarioInexistente(){
        given()
            .header("Content-Type","application/json")
            .body("{\"email\": \"fulano@email.com\",\"senha\": \"000000\"}")
        .when()
            .post("/v1/auth")
        .then()
            .statusCode(401);
    }

    @Test
    public void loginInvalidoBodySemEmail(){
        given()
            .header("Content-Type","application/json")
            .body("{\"senha\": \"000000\"}")
            .when()
            .post("/v1/auth")
            .then()
            .statusCode(400);
    }

    @Test
    public void loginInvalidoBodySemSenha(){
        given()
            .header("Content-Type","application/json")
            .body("{\"email\": \"fulano@email.com\"}")
            .when()
            .post("/v1/auth")
            .then()
            .statusCode(400);
    }

    @Test
    public void loginInvalidoSemBody(){
        given()
            .header("Content-Type","application/json")
            .when()
            .post("/v1/auth")
            .then()
            .statusCode(400);
    }

    @Test
    public void loginInvalidoBodySemFormato(){
        given()
            .body("{\"email\": \"usuario@email.com\",\"senha\": \"123456\"}")
            .when()
            .post("/v1/auth")
            .then()
            .statusCode(415);
    }
}
