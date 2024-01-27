package br.com.fiap.pagamento.gateway.repository.pagamento;

import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.core.exception.PagamentoInexistenteException;
import br.com.fiap.pagamento.gateway.repository.IPagamentoRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PagamentoRepository implements IPagamentoRepository {

    private final JpaPagamentoRepository repository;

    public PagamentoRepository(JpaPagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        final var entity = repository.save(new PagamentoEntity(pagamento));
        return new Pagamento(entity.getId(), entity.getStatus(), entity.getValor(), entity.getCliente(), entity.getQrData());
    }

    @Override
    public Pagamento buscarPorId(UUID pagamentoId) {
        final var optionalPagamento = repository.findById(pagamentoId);
        if (optionalPagamento.isEmpty())
            throw new PagamentoInexistenteException("Id de pagamento não encontrado.");
        final var entity = optionalPagamento.get();

        return new Pagamento(entity.getId(), entity.getStatus(), entity.getValor(), entity.getCliente(), entity.getQrData());
    }
}
