package com.montanha.gerenciador.test.viagens;
import com.montanha.gerenciador.test.utils.TestBase;
import org.junit.Test;
import static io.restassured.RestAssured.*;
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
	public void consultaViagemComRegiaoValida(){
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
	@Test
	public void consultaViagemComRegiaoInvalida(){
		//login de usuário
		String token = login("usuario@email.com","123456");

		//consulta as viagens usando token do usuário
		given()
			.header("Authorization",token)
			.when()
			.get("/v1/viagens?regiao=Pato")
			.then()
			.statusCode(500);
	};
	@Test
	public void consultaViagemNaoAutenticado(){
		//login de usuário
		String token = "batata";

		//consulta as viagens usando token do usuário
		given()
			.header("Authorization",token)
			.when()
			.get("/v1/viagens")
			.then()
			.statusCode(401);
	};

	@Test
	public void consultaViagemNaoAutorizado(){
		//login de usuário
		String token = login("admin@email.com","654321");

		//consulta as viagens usando token do admin
		given()
			.header("Authorization",token)
			.when()
			.get("/v1/viagens")
			.then()
			.statusCode(403);
	};

	@Test
	public void consultaViagemSemToken(){

		//consulta as viagens usando token do admin
		given()
			.when()
			.get("/v1/viagens")
			.then()
			.statusCode(401);
	};

	@Test
	public void cadastraViagemValida(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		//TO-DO: encaixar mais validações nesse cadastro de viagem (JsonSchema)
		cadastraViagem(token,"cicrana","2025-02-08","2025-02-15","Goiás","Centro-Oeste");
	};

}
