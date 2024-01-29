package br.com.fiap.pagamento.gateway.dataprovider;

import br.com.fiap.pagamento.api.dto.request.ProdutoSelecionadoRequest;

import java.util.List;

public interface IPagamentoDataProvider {

    boolean validaPagamento(String pagamento);

    String criarPagamento(List<ProdutoSelecionadoRequest> produtos, String cpfFormatado);
}
