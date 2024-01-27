package br.com.fiap.pagamento.core.entity;

import br.com.fiap.pagamento.core.enumerator.StatusEnum;

import java.math.BigDecimal;
import java.util.UUID;

public class Pagamento {
    private UUID id;
    private StatusEnum status;
    private BigDecimal valor;
    private String documentoCliente;

    private String qrData;

    public Pagamento(UUID id, StatusEnum status, BigDecimal valor, String documentoCliente, String qrData) {
        this.id = id;
        this.status = status;
        this.valor = valor;
        this.documentoCliente = documentoCliente;
        this.qrData = qrData;
    }

    public UUID getId() {
        return id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public String getQrData() {
        return qrData;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
