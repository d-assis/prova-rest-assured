package com.montanha.gerenciador.test.viagens;

import com.montanha.gerenciador.test.utils.TestBase;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class TestaViagemComID extends TestBase{
	@Test
	public void consultaViagemValidaPorID(){
		//login de usuário
		String token = login("usuario@email.com","123456");

		//consulta as viagens usando token do usuário
		given()
			.header("Authorization",token)
		.when()
			.get("/v1/viagens/1")
		.then()
			.statusCode(200)
		.and()
			.body(matchesJsonSchemaInClasspath("getViagemIDSchema.json"));
	}

	@Test
	public void consultaViagemPorIDNaoAutenticado(){
		//login de usuário
		String token = "pato";

		//consulta as viagens usando token do usuário
		given()
			.header("Authorization",token)
		.when()
			.get("/v1/viagens/1")
		.then()
			.statusCode(401);
	}

	@Test
	public void consultaViagemPorIDNaoAutorizado(){
		//login de usuário
		String token = login("admin@email.com","654321");

		//consulta as viagens usando token do usuário
		given()
			.header("Authorization",token)
		.when()
			.get("/v1/viagens/1")
		.then()
			.statusCode(403);
	}

	@Test
	public void consultaViagemPorIDSemToken(){
		//consulta as viagens sem token do usuário
		given()
		.when()
			.get("/v1/viagens/1")
		.then()
			.statusCode(401);
	}

	@Test
	public void consultaViagemIDInexistente(){
		//login de usuário
		String token = login("usuario@email.com","123456");

		//consulta as viagens usando token do usuário
		given()
			.header("Authorization",token)
		.when()
			.get("/v1/viagens/999")
		.then()
			.statusCode(404);
	}

	@Test
	public void consultaViagemIDInvalido(){
		//login de usuário
		String token = login("usuario@email.com","123456");

		//consulta as viagens usando token do usuário
		given()
			.header("Authorization",token)
		.when()
			.get("/v1/viagens/abc")
		.then()
			.statusCode(400);
	}

	@Test
	public void atualizarViagemValida(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-03-08\",\n" +
				" \t\"dataRetorno\": \"2025-05-15\",\n" +
				" \t\"localDeDestino\": \"Amapá\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(200)
		.and()
			.body(matchesJsonSchemaInClasspath("putViagemIDSchema.json"));
	}

	@Test
	public void atualizarViagemNaoAutenticado(){
		//login de admin
		String token = "pato";

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-03-08\",\n" +
				" \t\"dataRetorno\": \"2025-05-15\",\n" +
				" \t\"localDeDestino\": \"Amapá\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(401);
		}

	@Test
	public void atualizarViagemNaoAutorizado(){
		String token = login("usuario@email.com","123456");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-03-08\",\n" +
				" \t\"dataRetorno\": \"2025-05-15\",\n" +
				" \t\"localDeDestino\": \"Amapá\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(403);
	}

	@Test
	public void atualizarViagemSemToken(){

		//cadastra viagem
		given()
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-03-08\",\n" +
				" \t\"dataRetorno\": \"2025-05-15\",\n" +
				" \t\"localDeDestino\": \"Amapá\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(401);
	}

	@Test
	public void atualizarViagemContentTypeInvalido(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-03-08\",\n" +
				" \t\"dataRetorno\": \"2025-05-15\",\n" +
				" \t\"localDeDestino\": \"Amapá\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.XML)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(415);
	}

	@Test
	public void atualizarViagemSemContentType(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-03-08\",\n" +
				" \t\"dataRetorno\": \"2025-05-15\",\n" +
				" \t\"localDeDestino\": \"Amapá\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(415);
	}

	@Test
	public void atualizarViagemSemBody(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(400);
	}

	@Test
	public void atualizarViagemAcompanhanteInvalido(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": 999,\n" +
				" \t\"dataPartida\": \"2025-03-08\",\n" +
				" \t\"dataRetorno\": \"2025-05-15\",\n" +
				" \t\"localDeDestino\": \"Amapá\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
			.when()
			.put("/v1/viagens/1")
			.then()
			.statusCode(400);
	}

	@Test
	public void atualizarViagemPartidaInvalida(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": true,\n" +
				" \t\"dataRetorno\": \"2025-05-15\",\n" +
				" \t\"localDeDestino\": \"Amapá\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
			.when()
			.put("/v1/viagens/1")
			.then()
			.statusCode(400);
	}

	@Test
	public void atualizarViagemDataRetornoInvalido(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-05-15\",\n" +
				" \t\"dataRetorno\": false,\n" +
				" \t\"localDeDestino\": \"Amapá\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(400);
	}

	@Test
	public void atualizarViagemDestinoInvalido(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-05-15\",\n" +
				" \t\"dataRetorno\": \"2025-06-15\",\n" +
				" \t\"localDeDestino\": null,\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(400);
	}

	@Test
	public void atualizarViagemRegiaoInvalida(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-05-15\",\n" +
				" \t\"dataRetorno\": \"2025-06-15\",\n" +
				" \t\"localDeDestino\": \"Acre\",\n" +
				" \t\"regiao\": null\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(400);
	}
	@Test
	public void atualizarViagemPartidaMaiorQueRetorno(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-08-15\",\n" +
				" \t\"dataRetorno\": \"2025-06-15\",\n" +
				" \t\"localDeDestino\": \"Acre\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(400);
	}

	@Test
	public void atualizarViagemSemPartida(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataRetorno\": \"2025-06-15\",\n" +
				" \t\"localDeDestino\": \"Acre\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(400);
	}

	@Test
	public void atualizarViagemSemDestino(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-05-15\",\n" +
				" \t\"dataRetorno\": \"2025-06-15\",\n" +
				" \t\"regiao\": \"Norte\"\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(400);
	}

	@Test
	public void atualizarViagemSemRegiao(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		given()
			.header("Authorization", token)
			.body("{\n" +
				"\t\"acompanhante\": \"fulana\",\n" +
				" \t\"dataPartida\": \"2025-05-15\",\n" +
				" \t\"dataRetorno\": \"2025-06-15\",\n" +
				" \t\"localDeDestino\": \"Acre\",\n" +
				"}")
			.contentType(ContentType.JSON)
		.when()
			.put("/v1/viagens/1")
		.then()
			.statusCode(400);
	}

	@Test
	public void exclusaoDeViagemValida(){
		//login de admin
		String token = login("admin@email.com","654321");

		//cadastra viagem
		cadastraViagem(token,"zelda","2025-04-01","2025-05-01","Hyrule","Centro");

		//exclui viagem
		given()
			.header("Authorization", token)
			.contentType(ContentType.JSON)
		.when()
			.delete("/v1/viagens/2")
		.then()
			.statusCode(204);
	}

	@Test
	public void exclusaoDeViagemNaoAutenticado(){
		//login de admin
		String token = "pato";

		//exclui viagem
		given()
			.header("Authorization", token)
			.contentType(ContentType.JSON)
		.when()
			.delete("/v1/viagens/1")
		.then()
			.statusCode(401);
	}

	@Test
	public void exclusaoDeViagemNaoAutorizado(){
		//login de admin
		String token = login("usuario@email.com","123456");

		//exclui viagem
		given()
			.header("Authorization", token)
			.contentType(ContentType.JSON)
		.when()
			.delete("/v1/viagens/1")
		.then()
			.statusCode(403);
	}

	@Test
	public void exclusaoDeViagemSemToken(){

		//exclui viagem
		given()
			.contentType(ContentType.JSON)
		.when()
			.delete("/v1/viagens/1")
		.then()
			.statusCode(401);
	}

	@Test
	public void exclusaoDeViagemInexistente(){
		//login de admin
		String token = login("admin@email.com","654321");

		//exclui viagem
		given()
			.header("Authorization", token)
			.contentType(ContentType.JSON)
		.when()
			.delete("/v1/viagens/999")
		.then()
			.statusCode(500);
	}

	@Test
	public void exclusaoDeViagemIDInvalido(){
		//login de admin
		String token = login("admin@email.com","654321");

		//exclui viagem
		given()
			.header("Authorization", token)
			.contentType(ContentType.JSON)
		.when()
			.delete("/v1/viagens/pato")
		.then()
			.statusCode(400);
	}
}
