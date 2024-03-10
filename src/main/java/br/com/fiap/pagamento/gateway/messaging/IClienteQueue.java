package br.com.fiap.pagamento.gateway.messaging;

import br.com.fiap.pagamento.core.entity.Pagamento;

public interface IClienteQueue {
    void publicarResultadoPagamento(Pagamento entity, boolean checkPagamento);
}
