package br.com.fiap.pagamento.api.dto.response;

import br.com.fiap.pagamento.core.enumerator.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PagamentoResponse {
    private UUID id;
    private String qrData;
    private StatusEnum status;
    private BigDecimal valor;
}
