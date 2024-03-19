package br.com.fiap.pagamento.core.usecase.pagamento;

import br.com.fiap.pagamento.api.adapter.PagamentoAdapter;
import br.com.fiap.pagamento.api.dto.request.PagamentoRequest;
import br.com.fiap.pagamento.api.dto.response.PagamentoResponse;
import br.com.fiap.pagamento.config.UseCase;
import br.com.fiap.pagamento.core.enumerator.StatusEnum;
import br.com.fiap.pagamento.gateway.dataprovider.IPagamentoDataProvider;
import br.com.fiap.pagamento.gateway.messaging.IClienteQueue;
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

    public GerenciarPagamentoUseCase(IPagamentoRepository repository, IPagamentoDataProvider pagamentoDataProvider, IPedidoQueue pedidoQueue, IClienteQueue clienteQueue) {
        this.pagamentoRepository = repository;
        this.pagamentoDataProvider = pagamentoDataProvider;
        this.pedidoQueue = pedidoQueue;
        this.clienteQueue = clienteQueue;
    }

    @Transactional
    @Override
    public void validaPagamento(String pagamentoId, PagamentoRequest request) {

        final var pagamento = pagamentoRepository.buscarPorId(UUID.fromString(pagamentoId));
        final var checkPagamento = pagamentoDataProvider.validaPagamento(request.action());
        if (checkPagamento) {
            pagamento.setStatus(StatusEnum.PAGO);
            pedidoQueue.publicarAtualizacaoStatusPedido(pagamento.getPedidoId());
        }
        final var entity = pagamentoRepository.salvar(pagamento);
        if(entity.getDocumentoCliente() != null && !entity.getDocumentoCliente().isBlank())
            clienteQueue.publicarResultadoPagamento(entity, checkPagamento);
    }

    @Override
    public PagamentoResponse consultarPagamento(Integer pagamentoId) {
        final var pagamento = pagamentoRepository.buscarPorPedidoId(pagamentoId);
        return PagamentoAdapter.toResponse(pagamento);
    }
}
