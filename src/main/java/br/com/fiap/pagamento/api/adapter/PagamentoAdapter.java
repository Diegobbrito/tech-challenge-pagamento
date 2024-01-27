package br.com.fiap.pagamento.api.adapter;

import br.com.fiap.pagamento.api.dto.response.PagamentoResponse;
import br.com.fiap.pagamento.api.dto.response.PagamentoStatusResponse;
import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.core.enumerator.StatusEnum;

import java.math.BigDecimal;
import java.util.UUID;

public class PagamentoAdapter {

    public static Pagamento toPagamento(BigDecimal valor, StatusEnum status, String cpf, String qrData, Integer pedidoId) {
        return new Pagamento(UUID.randomUUID(), status, valor, cpf, qrData, pedidoId);
    }

    public static PagamentoResponse toResponse(Pagamento pagamento) {
        return new PagamentoResponse(pagamento.getQrData(), pagamento.getStatus(), pagamento.getValor());
    }

    public static PagamentoStatusResponse toPedidoStatus(String status) {
        return new PagamentoStatusResponse(status);
    }
}
