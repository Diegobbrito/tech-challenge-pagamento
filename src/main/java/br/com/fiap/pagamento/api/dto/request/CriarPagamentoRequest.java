package br.com.fiap.pagamento.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public record CriarPagamentoRequest(
        List<ProdutoSelecionadoRequest> produtos,
        @Schema(example = "055.069.020-42")
        String cpf,

        BigDecimal valor
) {
}
