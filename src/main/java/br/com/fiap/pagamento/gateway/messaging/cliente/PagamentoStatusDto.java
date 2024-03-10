package br.com.fiap.pagamento.gateway.messaging.cliente;

public record PagamentoStatusDto(
        Integer pedidoId,
        String documentoCliente,
        boolean checkPagamento
) {
}
