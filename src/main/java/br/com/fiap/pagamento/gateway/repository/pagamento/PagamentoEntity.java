package br.com.fiap.pagamento.gateway.repository.pagamento;

import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.core.enumerator.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@Document(collection = "pagamentos")
public class PagamentoEntity {

    @Id
    private UUID id;

    private BigDecimal valor;

    private String cliente;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private String qrData;

    private Integer pedidoId;

    public PagamentoEntity(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.valor = pagamento.getValor();
        this.cliente = pagamento.getDocumentoCliente();
        this.status = pagamento.getStatus();
        this.qrData = pagamento.getQrData();
        this.pedidoId = pagamento.getPedidoId();
    }

}
