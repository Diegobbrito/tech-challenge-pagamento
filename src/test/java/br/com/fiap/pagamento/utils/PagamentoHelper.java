package br.com.fiap.pagamento.utils;


import br.com.fiap.pagamento.api.dto.request.CriarPagamentoRequest;
import br.com.fiap.pagamento.api.dto.request.PagamentoRequest;
import br.com.fiap.pagamento.api.dto.request.ProdutoRequest;
import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.core.enumerator.StatusEnum;
import br.com.fiap.pagamento.gateway.repository.pagamento.PagamentoEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public abstract class PagamentoHelper {

    public static CriarPagamentoRequest gerarPagamentoRequest() {
        var produto = new ProdutoRequest(
                "Hamburguer",
                "Hamburguer com queijo",
                new BigDecimal("19.99"),
                "");

        return new CriarPagamentoRequest(List.of(produto), "", new BigDecimal("19.99"), 2);
    }

    public static PagamentoRequest gerarPagamentoRealizadoRequest() {
        Long id = 1L;
        String action = "payment";
        String apiVersion = "1";
        PagamentoRequest.Data data = new PagamentoRequest.Data("1");
        String dateCreated = "20/12/2023";
        Boolean livemode = true;
        String type = "";
        String userID = "15212027020";
        return new PagamentoRequest(id, action, apiVersion, data, dateCreated, livemode, type, userID);
    }

    public static Pagamento gerarPagamento() {
        return new Pagamento(UUID.fromString("0f44927d-5a3b-449a-9e6a-7ba0bf4d74c5"),
                StatusEnum.PAGAMENTOPENDENTE,
                new BigDecimal("19.99"),
                "15212027020",
                "teste",
                1);
    }

    public static PagamentoEntity gerarPagamentoEntity() {
        return new PagamentoEntity(gerarPagamento());
    }
}
