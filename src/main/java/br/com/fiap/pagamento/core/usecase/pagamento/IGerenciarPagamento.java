package br.com.fiap.pagamento.core.usecase.pagamento;

import br.com.fiap.pagamento.api.dto.request.PagamentoRequest;
import br.com.fiap.pagamento.api.dto.response.PagamentoStatusResponse;

public interface IGerenciarPagamento {

    void validaPagamento(String pedidoId, PagamentoRequest request);

    PagamentoStatusResponse consultarStatusDePagamento(String pedidoId);
}
