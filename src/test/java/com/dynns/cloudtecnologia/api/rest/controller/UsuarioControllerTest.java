package com.dynns.cloudtecnologia.api.rest.controller;



import static com.dynns.cloudtecnologia.api.rest.controller.UsuariosSource.*;
import static org.junit.jupiter.api.Assertions.*;


import com.dynns.cloudtecnologia.api.rest.dto.UsuarioNewDTO;
import com.dynns.cloudtecnologia.api.rest.dto.UsuarioUpdateDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import java.util.stream.Stream;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
class UsuarioControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioControllerTest.class);
    private static final String URL_PATH = "/users";
    private static String idUsuarioSalvo;
    private static final String EMAIL_CINCO_CARACTERES = "O email deve ter no mínimo 5 caracteres";
    private static final String NOME_CINCO_CARACTERES = "O nome deve ter no mínimo 5 caracteres";
    private static final String EMAIL_INVALIDO = "O email informado é inválido!";
    private static final String ID_USUARIO_INEXISTENTE = "69";
    private static final String USER_NOTFOUND = "Nenhum usuário encontrado para o ID: ";
    private static final String CAMPO_ID = "id";
    private static final String CAMPO_NOME = "nome";
    private static final String CAMPO_EMAIL = "email";


    @ParameterizedTest
    @MethodSource("getUsuariosParaTeste")
    @DisplayName("Testar Cadastro de Usuários")
    @Order(1)
    void testarCadastroDeProdutoServico(UsuarioNewDTO dto, int index) {
        var resposta = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(URL_PATH)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());
            assertTrue(responseBody.contains("O campo nome é obrigatório!"));
            assertTrue(responseBody.contains(NOME_CINCO_CARACTERES));
            assertTrue(responseBody.contains("O campo email é obrigatório!"));
            assertTrue(responseBody.contains(EMAIL_CINCO_CARACTERES));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());
            assertTrue(responseBody.contains(EMAIL_CINCO_CARACTERES));
            assertTrue(responseBody.contains(EMAIL_INVALIDO));
            assertTrue(responseBody.contains(NOME_CINCO_CARACTERES));
        } else if (index == 3) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());
            assertTrue(responseBody.contains(EMAIL_INVALIDO));
        } else if (index == 4) {
            idUsuarioSalvo = resposta.jsonPath().getString(CAMPO_ID).trim();
            assertEquals(HttpStatus.SC_CREATED, resposta.statusCode());
            assertEquals(dto.getEmail(), resposta.jsonPath().getString(CAMPO_EMAIL));
            assertEquals(dto.getNome(), resposta.jsonPath().getString(CAMPO_NOME));
        } else if (index == 5) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());
            assertTrue(responseBody.contains("O campo email já está sendo usado em outro cadastro!"));
        }else{
            assertEquals(HttpStatus.SC_CREATED, resposta.statusCode());
            assertEquals(dto.getEmail(), resposta.jsonPath().getString(CAMPO_EMAIL));
            assertEquals(dto.getNome(), resposta.jsonPath().getString(CAMPO_NOME));
        }
    }

    @Test
    @DisplayName("Deve listar todos Usuários")
    @Order(2)
    void deveListarTodosUsuarios() {
        var resposta = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL_PATH)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_OK, resposta.statusCode());
        assertTrue(responseBody.contains("numberOfElements\":2"));
        assertTrue(responseBody.contains("\"totalElements\":2"));
    }

    @Test
    @DisplayName("Deve listar Usuários com filtros")
    @Order(3)
    void deveListarUsuariosComFiltros() {
        var resposta = given()
                .contentType(ContentType.JSON)
                .params(CAMPO_ID,idUsuarioSalvo)
                .when()
                .get(URL_PATH)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_OK, resposta.statusCode());
        assertTrue(responseBody.contains("numberOfElements\":1"));
        assertTrue(responseBody.contains("\"totalElements\":1"));
        assertTrue(responseBody.contains("\"id\":1"));
    }

    @ParameterizedTest
    @MethodSource("getIdsUsuariosParaTeste")
    @DisplayName("Deve listar Usuário pelo ID")
    @Order(4)
    void deveListarUsuarioPeloId(String id, int index) {
        var resposta = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL_PATH + "/" + id)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(USER_NOTFOUND + id));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_OK, resposta.statusCode());
            assertEquals(idUsuarioSalvo, resposta.jsonPath().getString(CAMPO_ID));
            assertEquals("Usuário Dados OK", resposta.jsonPath().getString(CAMPO_NOME));
            assertEquals("email@usuario.com.br", resposta.jsonPath().getString(CAMPO_EMAIL));
        }
    }

    @ParameterizedTest
    @MethodSource("getUsuariosUpdateParaTeste")
    @DisplayName("Deve atualizar o Usuário pelo ID")
    @Order(5)
    void deveAtualizarUsuario(UsuarioUpdateDTO dto, String idUsuario, int index) {
         var resposta = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .put(URL_PATH + "/" + idUsuario)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(USER_NOTFOUND + idUsuario));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_OK, resposta.statusCode());
            assertEquals(idUsuario, resposta.jsonPath().getString(CAMPO_ID));
            assertEquals(dto.getNome(), resposta.jsonPath().getString(CAMPO_NOME));
            assertEquals(dto.getEmail(), resposta.jsonPath().getString(CAMPO_EMAIL));
        }else{
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, resposta.statusCode());
            assertTrue(responseBody.contains("Não foi possível atualizar: O email '"+dto.getEmail()+"' já está sendo usado em outro cadastro!"));
        }
    }

    @ParameterizedTest
    @MethodSource("getIdsUsuariosParaTeste")
    @DisplayName("Deve deletar o Usuário pelo ID")
    @Order(6)
    void deveDeletarUsuario(String id, int index) {
        var resposta = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(URL_PATH + "/" + id)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(USER_NOTFOUND + id));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_NO_CONTENT, resposta.statusCode());
        }
    }

    static Stream<Arguments> getUsuariosParaTeste() {
        return Stream.of(
                Arguments.of(getUsuarioDadosVazios(), 1),
                Arguments.of(getUsuarioDadosInvalidos(), 2),
                Arguments.of(getUsuarioEmailInvalido(), 3),
                Arguments.of(getUsuarioValido(), 4),
                Arguments.of(getUsuarioValido(), 5),
                Arguments.of(getUsuarioValido2(), 6)
        );
    }

    static Stream<Arguments> getIdsUsuariosParaTeste() {
        return Stream.of(
                Arguments.of(ID_USUARIO_INEXISTENTE, 1),
                Arguments.of(idUsuarioSalvo, 2)
        );
    }

    static Stream<Arguments> getUsuariosUpdateParaTeste() {
        return Stream.of(
                Arguments.of(getUsuarioUpdateDTO(), ID_USUARIO_INEXISTENTE,1),
                Arguments.of(getUsuarioUpdateDTO(), idUsuarioSalvo,2),
                Arguments.of(getUsuarioUpdateEmailExistenteDTO(), idUsuarioSalvo,3)
        );
    }

}