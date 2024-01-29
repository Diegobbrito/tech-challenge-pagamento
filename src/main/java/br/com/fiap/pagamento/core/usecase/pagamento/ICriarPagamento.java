package br.com.fiap.pagamento.core.usecase.pagamento;

import br.com.fiap.pagamento.api.dto.request.CriarPagamentoRequest;
import br.com.fiap.pagamento.api.dto.response.PagamentoResponse;

public interface ICriarPagamento {
    PagamentoResponse criar(CriarPagamentoRequest request);
}
