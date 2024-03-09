package br.com.fiap.pagamento.gateway.repository.pagamento;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface MongoPagamentoRepository extends MongoRepository<PagamentoEntity, UUID> {
    Optional<PagamentoEntity> findByPedidoId(Integer pedidoId);
}
