package br.com.fiap.pagamento.core.usecase.pagamento;

import br.com.fiap.pagamento.api.adapter.PagamentoAdapter;
import br.com.fiap.pagamento.api.dto.request.PagamentoRequest;
import br.com.fiap.pagamento.api.dto.response.PagamentoStatusResponse;
import br.com.fiap.pagamento.config.UseCase;
import br.com.fiap.pagamento.core.enumerator.StatusEnum;
import br.com.fiap.pagamento.gateway.dataprovider.IPagamentoDataProvider;
import br.com.fiap.pagamento.gateway.repository.IPagamentoRepository;

import java.util.UUID;

@UseCase
public class GerenciarPagamentoUseCase implements IGerenciarPagamento {

    private final IPagamentoRepository pagamentoRepository;
    private final IPagamentoDataProvider pagamentoDataProvider;

    public GerenciarPagamentoUseCase(IPagamentoRepository repository, IPagamentoDataProvider pagamentoDataProvider) {
        this.pagamentoRepository = repository;
        this.pagamentoDataProvider = pagamentoDataProvider;
    }

    @Override
    public void validaPagamento(String pagamentoId, PagamentoRequest request) {

        final var pagamento = pagamentoRepository.buscarPorId(UUID.fromString(pagamentoId));
        final var checkPagamento = pagamentoDataProvider.validaPagamento(request.data().id());
        if (checkPagamento) {
            pagamento.setStatus(StatusEnum.PAGO);
        }
        pagamentoRepository.salvar(pagamento);
    }

    @Override
    public PagamentoStatusResponse consultarStatusDePagamento(String pagamentoId) {
        final var pedido = pagamentoRepository.buscarPorId(UUID.fromString(pagamentoId));
        if (pedido.getStatus().equals(StatusEnum.PAGAMENTOPENDENTE))
            return PagamentoAdapter.toPedidoStatus("Pagamento Pendente");
        return PagamentoAdapter.toPedidoStatus("Pago");
    }
}
