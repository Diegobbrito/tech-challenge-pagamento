package br.com.fiap.pagamento.gateway.repository.pagamento;

import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.core.exception.PagamentoInexistenteException;
import br.com.fiap.pagamento.gateway.repository.IPagamentoRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PagamentoRepository implements IPagamentoRepository {

    private final MongoPagamentoRepository repository;

    public PagamentoRepository(MongoPagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        final var entity = repository.save(new PagamentoEntity(pagamento));
        return getPagamento(entity);
    }

    @Override
    public Pagamento buscarPorId(UUID pagamentoId) {
        final var optionalPagamento = repository.findById(pagamentoId);
        if (optionalPagamento.isEmpty())
            throw new PagamentoInexistenteException("Id de pagamento não encontrado.");
        final var entity = optionalPagamento.get();

        return getPagamento(entity);
    }

    @Override
    public Pagamento buscarPorPedidoId(Integer pedidoId) {
        final var optionalPagamento = repository.findByPedidoId(pedidoId);
        if (optionalPagamento.isEmpty())
            throw new PagamentoInexistenteException("Id de pagamento não encontrado.");
        final var entity = optionalPagamento.get();

        return getPagamento(entity);
    }

    private Pagamento getPagamento(PagamentoEntity entity) {
        final var id = entity.getId();
        final var pedidoId = entity.getPedidoId();
        final var status = entity.getStatus();
        final var valor = entity.getValor();
        final var cliente = entity.getCliente();
        final var qrData = entity.getQrData();
        return new Pagamento(id, status, valor, cliente, qrData, pedidoId);
    }
}
