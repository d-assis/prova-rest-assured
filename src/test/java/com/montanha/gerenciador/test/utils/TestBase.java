package com.montanha.gerenciador.test.utils;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestBase {
    @BeforeClass
    public static void testSetup(){
        baseURI = "http://localhost";
        port = 8089;
        basePath = "api";

        //login
        String token = login("admin@email.com","654321");

        //popula base
        cadastraViagem(token,
            "fulana",
            "2025-10-10",
            "2025-10-11",
            "Amazonas",
            "Norte");

    }

    public static String login(String email, String senha){
        String payload = String.format("{\"email\": \"%s\",\"senha\": \"%s\"}",email,senha);

        String token = given()
            .body(payload)
            .header("Content-Type","application/json")
        .when()
            .post("/v1/auth")
        .then()
            .extract().path("data.token");

        return token;
    }

    public static void cadastraViagem(String token, String acompanhante, String partida, String retorno, String destino, String regiao){
        String jsonTemplate = "{\"acompanhante\": \"%s\"," +
            "\"dataPartida\": \"%s\"," +
            "\"dataRetorno\": \"%s\"," +
            "\"localDeDestino\": \"%s\"," +
            "\"regiao\": \"%s\"}";

        String payload = String.format(jsonTemplate,
            acompanhante,
            partida,
            retorno,
            destino,
            regiao);

        try{
            given()
                .header("Authorization", token)
                .body(payload)
                .contentType(ContentType.JSON)
            .when()
                .post("/v1/viagens")
            .then()
                .assertThat()
                .statusCode(201);
        } catch(Exception e){
            System.out.println("erro ao cadastrar viagem, segue log: " + e);
        }
    }
}
