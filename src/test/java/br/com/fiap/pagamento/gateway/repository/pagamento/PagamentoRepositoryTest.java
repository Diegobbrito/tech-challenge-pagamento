package br.com.fiap.pagamento.gateway.repository.pagamento;

import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.utils.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PagamentoRepositoryTest {


    private PagamentoRepository pagamentoRepository;

    @Mock
    private JpaPagamentoRepository jpaRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        pagamentoRepository = new PagamentoRepository(jpaRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirConsultarPagamento() {
        // Arrange
        var pagamentoEntity = PagamentoHelper.gerarPagamentoEntity();
        when(jpaRepository.findById(any(UUID.class))).thenReturn(Optional.of(pagamentoEntity));
        // Act
        var pagamento = pagamentoRepository.buscarPorId(UUID.randomUUID());
        // Assert
        verify(jpaRepository, times(1)).findById(any(UUID.class));
        assertThat(pagamento)
                .isInstanceOf(Pagamento.class)
                .isNotNull();
        assertThat(pagamento)
                .extracting(Pagamento::getId)
                .isEqualTo(pagamentoEntity.getId());
    }


    @Test
    void devePermitirRegistrarPagamento() {
        // Arrange
        var pagamento = PagamentoHelper.gerarPagamento();
        var pagamentoEntity = PagamentoHelper.gerarPagamentoEntity();
        when(jpaRepository.save(any(PagamentoEntity.class))).thenReturn(pagamentoEntity);
        // Act
        var pagamentoSalvo = pagamentoRepository.salvar(pagamento);
        // Assert
        verify(jpaRepository, times(1)).save(any(PagamentoEntity.class));
        assertThat(pagamentoSalvo)
                .isInstanceOf(Pagamento.class)
                .isNotNull();

    }


}
