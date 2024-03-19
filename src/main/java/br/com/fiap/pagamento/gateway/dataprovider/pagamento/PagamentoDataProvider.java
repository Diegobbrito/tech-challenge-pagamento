package br.com.fiap.pagamento.gateway.dataprovider.pagamento;

import br.com.fiap.pagamento.api.dto.request.ProdutoRequest;
import br.com.fiap.pagamento.gateway.dataprovider.IPagamentoDataProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PagamentoDataProvider implements IPagamentoDataProvider {

    private final MercadoPagoAPIDataProvider dataProvider;

    public PagamentoDataProvider(MercadoPagoAPIDataProvider repository) {
        this.dataProvider = repository;
    }

    @Override
    public String criarPagamento(List<ProdutoRequest> produtos, String cliente) {
        return dataProvider.criarPagamento(produtos, cliente);
    }

    @Override
    public boolean validaPagamento(String pagamentoId) {
        return dataProvider.validaPagamento(pagamentoId);
    }
}
