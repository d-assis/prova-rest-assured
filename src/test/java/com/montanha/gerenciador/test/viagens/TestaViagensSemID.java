package com.montanha.gerenciador.test.viagens;
import com.montanha.gerenciador.test.utils.TestBase;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
public class TestaViagensSemID extends TestBase{

	@Test
	public void consultaViagemSemRegiao(){
		//login de usuário
		String token = login("usuario@email.com","123456");

		//consulta as viagens usando token do usuário
		given()
			.header("Authorization",token)
		.when()
			.get("/v1/viagens")
		.then()
			.statusCode(200)
		.and()
			.body(matchesJsonSchemaInClasspath("getViagensSchema.json"));
	};
	@Test
	public void consultaViagemComRegiao(){
		//login de usuário
		String token = login("usuario@email.com","123456");

		//consulta as viagens usando token do usuário
		given()
			.header("Authorization",token)
		.when()
			.get("/v1/viagens?regiao=Norte")
		.then()
			.statusCode(200)
		.and()
			.body(matchesJsonSchemaInClasspath("getViagensSchema.json"));
	};
}
