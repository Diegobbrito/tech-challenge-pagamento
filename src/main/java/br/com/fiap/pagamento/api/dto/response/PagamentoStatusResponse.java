package br.com.fiap.pagamento.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PagamentoStatusResponse(
        @Schema(example = "Pago")
        String status) {

}
