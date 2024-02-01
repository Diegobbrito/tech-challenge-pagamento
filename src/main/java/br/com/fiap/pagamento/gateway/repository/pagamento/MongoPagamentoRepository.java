package br.com.fiap.pagamento.gateway.repository.pagamento;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MongoPagamentoRepository extends MongoRepository<PagamentoEntity, UUID> {
}
