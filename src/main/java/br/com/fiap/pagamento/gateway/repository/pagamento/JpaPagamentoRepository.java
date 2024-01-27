package br.com.fiap.pagamento.gateway.repository.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaPagamentoRepository extends JpaRepository<PagamentoEntity, UUID> {


}
