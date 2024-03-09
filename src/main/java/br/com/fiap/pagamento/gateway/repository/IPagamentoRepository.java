package br.com.fiap.pagamento.gateway.repository;

import br.com.fiap.pagamento.core.entity.Pagamento;

import java.util.UUID;

public interface IPagamentoRepository {
    Pagamento salvar(Pagamento pagamento);

    Pagamento buscarPorId(UUID pagamentoId);
    Pagamento buscarPorPedidoId(Integer pedidoId);
}
