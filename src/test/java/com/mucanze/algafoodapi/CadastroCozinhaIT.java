package com.mucanze.algafoodapi;

import static org.hamcrest.CoreMatchers.equalTo;

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
import com.mucanze.algafood.domain.repository.CozinhaRepository;
import com.mucanze.algafoodapi.util.DatabaseCleaner;
import com.mucanze.algafoodapi.util.ResourceUtils;
import com.mucanze.algafoodapi.util.TestConfig;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AlgafoodApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@Import(TestConfig.class)
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int localPort;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private static final int COZINHA_ID_INEXISTENTE = 100;
	
	private Cozinha cozinhaIndiana;
	
	private int quantidadeCozinhasCadastradas;
	
	private String jsonCorrectoCozinhaMocambicana;
	
	private static final String COZINHA_TAILANDESA = "Tailandesa";
	private static final String COZINHA_INDIANA = "Indiana";
	
	/*@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Test
	void deveAtribuirIdQuandoCadastrarCozinhaComDadosCorrectos() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Moçambicana");
		
		cozinha = cadastroCozinhaService.salvar(cozinha);
		
		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
	}
	
	@Test
	void deveFalharQuandoCadastrarCozinhaSemNome() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("");
		
		ConstraintViolationException erroEsperado =
				Assertions.assertThrows(ConstraintViolationException.class, () -> {
					cadastroCozinhaService.salvar(cozinha);
				});
		
		assertThat(erroEsperado).isNotNull();
	}
	
	@Test
	void deveFalharQuandoExcluirCozinhaEmUso() {
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		
		EntidadeEmUsoException erroEsperado = 
				Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
					cadastroCozinhaService.remover(cozinha.getId());
				});
		
		assertThat(erroEsperado).isNotNull();
	}
	
	@Test
	void deveFalharQuandoExcluirUmaCozinhaInexistente() {
		Cozinha cozinha = new Cozinha();
		cozinha.setId(10L);
		
		EntidadeInexistenteException erroEsperado = 
				Assertions.assertThrows(EntidadeInexistenteException.class, () -> {
					cadastroCozinhaService.remover(cozinha.getId());
				});
		
		assertThat(erroEsperado).isNotNull();
	}*/
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/cozinhas";
		RestAssured.port = localPort;
		
		cozinhaIndiana = new Cozinha();
		
		jsonCorrectoCozinhaMocambicana = ResourceUtils.getContentFromResource("/json/correcto/cozinha_mocambicana.json");
		
		this.databaseCleaner.clearTables();
		prepareDataTables();
	}
	
	@Test
	public void deveRetornar200QuandoConsultarCozinhas() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConterItensCorrectosQuandoConsultarCozinhas() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(quantidadeCozinhasCadastradas))
			.body("nome", Matchers.hasItems(COZINHA_TAILANDESA, COZINHA_INDIANA));
	}
	
	@Test
	public void deveRetornar201QuandoCadastrarCozinha() {
		RestAssured.given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(jsonCorrectoCozinhaMocambicana)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorrectoQuandoConsultarCozinhaExistente() {
		RestAssured.given()
			.pathParam("cozinhaId", cozinhaIndiana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaIndiana.getNome()));
	}
	
	@Test
	public void deveRetornar404QuandoConsultarCozinhaInexistente() {
		RestAssured.given()
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepareDataTables() {
		Cozinha cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome(COZINHA_TAILANDESA);
		cozinhaRepository.save(cozinhaTailandesa);
		
		cozinhaIndiana.setNome(COZINHA_INDIANA);
		cozinhaRepository.save(cozinhaIndiana);
		
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}

}
