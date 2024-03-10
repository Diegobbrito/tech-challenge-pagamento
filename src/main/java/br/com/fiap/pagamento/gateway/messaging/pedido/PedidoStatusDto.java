package br.com.fiap.pagamento.gateway.messaging.pedido;

public record PedidoStatusDto(
        Integer pedidoId,
        Integer statusId) {
}
