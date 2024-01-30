package br.com.fiap.pagamento.bdd;


import br.com.fiap.pagamento.api.dto.request.PagamentoRequest;
import br.com.fiap.pagamento.api.dto.response.PagamentoResponse;
import br.com.fiap.pagamento.api.dto.response.PagamentoStatusResponse;
import br.com.fiap.pagamento.utils.PagamentoHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DefinicaoPassos {

    private Response response;

    private PagamentoResponse pagamentoResponse;

    private String ENDPOINT_BASE = "http://localhost:8080/lanchonete/pagamentos";

    @Quando("submeter um novo pagamento")
    public PagamentoResponse submeterNovoPagamento() {
        var pagamentoRequest = PagamentoHelper.gerarPagamentoRequest();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequest)
                .when().post(ENDPOINT_BASE);
        return response.then().extract().as(PagamentoResponse.class);
    }

    @Então("o pagamento é gerado e registrado com sucesso")
    public void pagamentoGeradoERegistradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/PagamentoResponseSchema.json"));
    }

    @Dado("que um pagamento já foi registrado")
    public void pagamentoJaRegistrado() {
        pagamentoResponse = submeterNovoPagamento();
    }

    @Quando("requisitar a busca de um pagamento")
    public void requisitarBuscaDePagamento() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_BASE + "/{id}", pagamentoResponse.getId());
        response.then().extract().as(PagamentoStatusResponse.class);
    }

    @Então("o status do pagamento é exibido com sucesso")
    public void statusDePagamentoExibidoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/StatusResponseSchema.json"));
    }

    @Quando("requisitar a confirmação do pagamento")
    public void requisitarConfirmaçãoDoPagamento() {
        PagamentoRequest pagamentoRequest = PagamentoHelper.gerarPagamentoRealizadoRequest();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequest )
                .when().post(ENDPOINT_BASE + "/{id}", pagamentoResponse.getId());
    }

    @Então("o pagamento é confirmado e atualizado")
    public void pagamentoConfirmadoEAtualizado() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

}
