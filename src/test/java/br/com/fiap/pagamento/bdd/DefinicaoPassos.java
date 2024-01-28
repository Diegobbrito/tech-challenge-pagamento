package br.com.fiap.pagamento.bdd;


import br.com.fiap.pagamento.api.dto.response.PagamentoResponse;
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

    private String ENDPOINT_BASE = "http://localhost:8080/lanchonete";

    @Quando("submeter um novo pagamento")
    public void submeterNovoPagamento() {
        var pagamentoRequest = PagamentoHelper.gerarPagamentoRequest();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequest)
                .when().post(ENDPOINT_BASE + "/pagamentos");
        response.then().extract().as(PagamentoResponse.class);
    }

    @Então("o pagamento é gerado e registrado com sucesso")
    public void pagamentoGeradoERegistradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/PagamentoResponseSchema.json"));
    }

    @Dado("que um pagamento já foi registrado")
    public void que_um_pagamento_já_foi_registrado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Quando("requisitar a busca de um pagamento")
    public void requisitar_a_busca_de_um_pagamento() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Então("o pagamento é exibido com sucesso")
    public void o_pagamento_é_exibido_com_sucesso() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Quando("requisitar a confirmação do pagamento")
    public void requisitar_a_confirmação_do_pagamento() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Então("o pagamento é confirmado e atualizado")
    public void o_pagamento_é_confirmado_e_atualizado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
