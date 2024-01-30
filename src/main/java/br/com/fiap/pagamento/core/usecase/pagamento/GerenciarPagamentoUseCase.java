package br.com.fiap.pagamento.core.usecase.pagamento;

import br.com.fiap.pagamento.api.adapter.PagamentoAdapter;
import br.com.fiap.pagamento.api.dto.request.PagamentoRequest;
import br.com.fiap.pagamento.api.dto.response.PagamentoStatusResponse;
import br.com.fiap.pagamento.config.UseCase;
import br.com.fiap.pagamento.core.enumerator.StatusEnum;
import br.com.fiap.pagamento.gateway.dataprovider.IPagamentoDataProvider;
import br.com.fiap.pagamento.gateway.dataprovider.IPedidoDataProvider;
import br.com.fiap.pagamento.gateway.repository.IPagamentoRepository;

import java.util.UUID;

@UseCase
public class GerenciarPagamentoUseCase implements IGerenciarPagamento {

    private final IPagamentoRepository pagamentoRepository;
    private final IPagamentoDataProvider pagamentoDataProvider;

    private final IPedidoDataProvider pedidoDataProvider;

    public GerenciarPagamentoUseCase(IPagamentoRepository repository, IPagamentoDataProvider pagamentoDataProvider, IPedidoDataProvider pedidoDataProvider) {
        this.pagamentoRepository = repository;
        this.pagamentoDataProvider = pagamentoDataProvider;
        this.pedidoDataProvider = pedidoDataProvider;
    }

    @Override
    public void validaPagamento(String pagamentoId, PagamentoRequest request) {

        final var pagamento = pagamentoRepository.buscarPorId(UUID.fromString(pagamentoId));
        final var checkPagamento = pagamentoDataProvider.validaPagamento(request.data().id());
        if (checkPagamento) {
            pagamento.setStatus(StatusEnum.PAGO);
        }
        final var entity = pagamentoRepository.salvar(pagamento);
        pedidoDataProvider.atualizarPedido(entity.getPedidoId());
    }

    @Override
    public PagamentoStatusResponse consultarStatusDePagamento(UUID pagamentoId) {
        final var pedido = pagamentoRepository.buscarPorId(pagamentoId);
        if (pedido.getStatus() == StatusEnum.PAGAMENTOPENDENTE)
            return PagamentoAdapter.toPedidoStatus("Pagamento Pendente");
        return PagamentoAdapter.toPedidoStatus("Pago");
    }
}
