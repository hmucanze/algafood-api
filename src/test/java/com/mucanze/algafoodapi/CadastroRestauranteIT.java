package com.mucanze.algafoodapi;

import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.mucanze.algafood.AlgafoodApiApplication;
import com.mucanze.algafood.domain.model.Cozinha;
import com.mucanze.algafood.domain.model.Restaurante;
import com.mucanze.algafood.domain.repository.CozinhaRepository;
import com.mucanze.algafood.domain.repository.RestauranteRepository;
import com.mucanze.algafoodapi.util.DatabaseCleaner;
import com.mucanze.algafoodapi.util.ResourceUtils;
import com.mucanze.algafoodapi.util.TestConfig;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@SpringBootTest(classes = AlgafoodApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@Import(TestConfig.class)
public class CadastroRestauranteIT {
	
	private static final int RESTAURANTE_ID_INEXISTENTE = 100;
	
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";
	
	private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
	
	@LocalServerPort
	private int localPort;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	private String jsonCorrectoRestauranteNewYork;
	
	private String jsonRestauranteNewYorkCozinhaInexistente;
	
	private String jsonRestauranteNewYorkSemCozinha;
	
	private String jsonRestauranteNewYorkSemFrete;
	
	private Restaurante restauranteOTango;
	
	private int quantidadeRestaurantesCadastrados;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/restaurantes";
		RestAssured.port = localPort;
		
		jsonCorrectoRestauranteNewYork =
				ResourceUtils.getContentFromResource("/json/correcto/restaurante_new_york_barbecue.json");
		
		jsonRestauranteNewYorkCozinhaInexistente =
				ResourceUtils.getContentFromResource("/json/incorrecto/restaurante_new_york_barbecue_cozinha_inexistente.json");
		
		jsonRestauranteNewYorkSemCozinha =
				ResourceUtils.getContentFromResource("/json/incorrecto/restaurante_new_york_barbecue_sem_cozinha.json");
		
		jsonRestauranteNewYorkSemFrete =
				ResourceUtils.getContentFromResource("/json/incorrecto/restaurante_new_york_barbecue_sem_frete.json");
		
		restauranteOTango = new Restaurante();
		
		databaseCleaner.clearTables();
		prepararDataTable();
	}
	
	@Test
	public void deveRetornar200QuandoConsultarRestaurantes() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornar201QuandoCadastrarRestauranteCorrecto() {
		RestAssured.given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonCorrectoRestauranteNewYork)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornar400QuandoCadastrarRestauranteComCozinhaInexistente() {
		RestAssured.given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonRestauranteNewYorkCozinhaInexistente)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornar400QuandoCadastrarRestauranteSemCozinha() {
		RestAssured.given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonRestauranteNewYorkSemCozinha)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornar400QuandoCadastrarRestauranteSemFrete() {
		RestAssured.given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonRestauranteNewYorkSemFrete)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}
	
	@Test
	public void deveConterItensCorrectosQuandoConsultarRestaurantes() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(quantidadeRestaurantesCadastrados));
	}
	
	@Test
	public void deveRetornar404QuandoConsultarRestauranteInexistente() {
		RestAssured.given()
			.accept(ContentType.JSON)
			.pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorrectoQuandoConsultarRestauranteExistente() {
		RestAssured.given()
			.accept(ContentType.JSON)
			.pathParam("restauranteId", restauranteOTango.getId())
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(restauranteOTango.getNome()));
	}
	
	private void prepararDataTable() {
		Cozinha cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);
		
		Cozinha cozinhaEspanhola = new Cozinha();
		cozinhaEspanhola.setNome("Espanhola");
		cozinhaRepository.save(cozinhaEspanhola);
		
		Restaurante restauranteSolange = new Restaurante();
		restauranteSolange.setNome("Solange Club");
		restauranteSolange.setTaxaFrete(BigDecimal.valueOf(15.5));
		restauranteSolange.setActivo(false);
		restauranteSolange.setAberto(true);
		restauranteSolange.setCozinha(cozinhaAmericana);
		restauranteRepository.save(restauranteSolange);
		
		restauranteOTango.setNome("O Tango");
		restauranteOTango.setTaxaFrete(BigDecimal.valueOf(14.0));
		restauranteOTango.setActivo(true);
		restauranteOTango.setAberto(true);
		restauranteOTango.setCozinha(cozinhaEspanhola);
		restauranteRepository.save(restauranteOTango);
		
		quantidadeRestaurantesCadastrados = (int) restauranteRepository.count();
	}

}
