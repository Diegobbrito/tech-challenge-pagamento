package br.com.fiap.pagamento.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProdutoSelecionadoRequest(

        ProdutoRequest produto,
        @Schema(example = "1")
        Integer quantidade
) {
}
