package com.montanha.gerenciador.test.viagens;
import com.montanha.gerenciador.test.utils.TestBase;
import io.restassured.http.ContentType;
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
	}
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
	}
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
	}
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
	}

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
	}

	@Test
	public void consultaViagemSemToken(){

		//consulta as viagens usando token do admin
		given()
			.when()
			.get("/v1/viagens")
			.then()
			.statusCode(401);
	}

	@Test
	public void cadastraViagemValida(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"cicrana\",\n" +
				" \t\"dataPartida\": \"2025-02-08\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/v1/viagens")
		.then()
			.statusCode(201)
		.and()
			.body(matchesJsonSchemaInClasspath("postViagemSchema.json"));
	}

	@Test
	public void cadastraViagemUsuarioNaoAutenticado(){

		String token = "pato";

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"cicrana\",\n" +
				" \t\"dataPartida\": \"2025-02-08\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/v1/viagens")
		.then()
			.statusCode(401);
	}

	@Test
	public void cadastraViagemUsuarioNaoAutorizado(){
		//login de admin
		String token = login("usuario@email.com","123456");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"cicrana\",\n" +
				" \t\"dataPartida\": \"2025-02-08\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/v1/viagens")
		.then()
			.statusCode(403);
	}
	@Test
	public void cadastraViagemSemToken(){

		//cadastra viagem
		given()
			.body("{\n" +
				"\t\"acompanhante\": \"cicrana\",\n" +
				" \t\"dataPartida\": \"2025-02-08\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(401);
	}

	@Test
	public void cadastraViagemContentTypeInvalido(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"cicrana\",\n" +
				" \t\"dataPartida\": \"2025-02-08\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.XML)
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(415);
	}

	@Test
	public void cadastraViagemSemContentType(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"cicrana\",\n" +
				" \t\"dataPartida\": \"2025-02-08\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(415);
	}

	@Test
	public void cadastraViagemBodyVazio(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{}")
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(400);
	}

	/*
	caso de teste abaixo não aplicável uma vez que o sistema transforma em string qualquer valor do
	body que tenha um tipo diferente, completando a request normalmente.
	@Test
	public void cadastraViagemAcompanhanteInvalido(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": 123,\n" +
				" \t\"dataPartida\": \"2025-02-08\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(400);
	}
	 */
	@Test
	public void cadastraViagemPartidaMaiorQueRetorno(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"beltrana\",\n" +
				" \t\"dataPartida\": \"2026-05-08\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(400);
	}

	@Test
	public void cadastraViagemSemAcompanhante(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				" \t\"dataPartida\": \"2025-02-08\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(400);
	}

	@Test
	public void cadastraViagemSemDataRetorno(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"beltrana\",\n" +
				" \t\"dataPartida\": \"2025-02-08\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(400);
	}

	@Test
	public void cadastraViagemSemPartida(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"beltrana\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(400);
	}

	@Test
	public void cadastraViagemSemDestino(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"beltrana\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"regiao\": \"Centro-Oeste\"\n" +
				"}")
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(400);
	}

	@Test
	public void cadastraViagemSemRegiao(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"beltrana\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"dataRetorno\": \"2025-02-15\",\n" +
				" \t\"localDeDestino\": \"Goiás\",\n" +
				"}")
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/viagens")
			.then()
			.statusCode(400);
	}
}
