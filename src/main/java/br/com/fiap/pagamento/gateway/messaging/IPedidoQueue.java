package br.com.fiap.pagamento.gateway.messaging;

public interface IPedidoQueue {
    void publicarAtualizacaoStatusPedido(Integer pedidoId);
}
