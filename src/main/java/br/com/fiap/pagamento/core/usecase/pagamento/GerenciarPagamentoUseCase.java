package br.com.fiap.pagamento.core.usecase.pagamento;

import br.com.fiap.pagamento.api.adapter.PagamentoAdapter;
import br.com.fiap.pagamento.api.dto.request.PagamentoRequest;
import br.com.fiap.pagamento.api.dto.response.PagamentoStatusResponse;
import br.com.fiap.pagamento.config.UseCase;
import br.com.fiap.pagamento.core.enumerator.StatusEnum;
import br.com.fiap.pagamento.gateway.dataprovider.IPagamentoDataProvider;
import br.com.fiap.pagamento.gateway.dataprovider.IPedidoDataProvider;
import br.com.fiap.pagamento.gateway.messaging.IPedidoQueue;
import br.com.fiap.pagamento.gateway.repository.IPagamentoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
public class GerenciarPagamentoUseCase implements IGerenciarPagamento {

    private final IPagamentoRepository pagamentoRepository;
    private final IPagamentoDataProvider pagamentoDataProvider;
    private final IPedidoQueue pedidoQueue;
    private final IClienteQueue clienteQueue;

    public GerenciarPagamentoUseCase(IPagamentoRepository repository, IPagamentoDataProvider pagamentoDataProvider, IPedidoQueue pedidoQueue) {
        this.pagamentoRepository = repository;
        this.pagamentoDataProvider = pagamentoDataProvider;
        this.pedidoQueue = pedidoQueue;
    }

    @Transactional
    @Override
    public void validaPagamento(String pagamentoId, PagamentoRequest request) {

        final var pagamento = pagamentoRepository.buscarPorId(UUID.fromString(pagamentoId));
        final var checkPagamento = pagamentoDataProvider.validaPagamento(request.data().id());
        if (checkPagamento) {
            pagamento.setStatus(StatusEnum.PAGO);
        }
        final var entity = pagamentoRepository.salvar(pagamento);
        pedidoQueue.publicarAtualizacaoStatusPedido(entity.getPedidoId());
    }

    @Override
    public PagamentoStatusResponse consultarStatusDePagamento(Integer pagamentoId) {
        final var pedido = pagamentoRepository.buscarPorPedidoId(pagamentoId);
        if (pedido.getStatus() == StatusEnum.PAGAMENTOPENDENTE)
            return PagamentoAdapter.toPedidoStatus("Pagamento Pendente");
        return PagamentoAdapter.toPedidoStatus("Pago");
    }
}
