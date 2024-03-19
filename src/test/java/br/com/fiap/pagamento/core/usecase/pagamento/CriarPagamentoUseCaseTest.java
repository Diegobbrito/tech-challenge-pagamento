package br.com.fiap.pagamento.core.usecase.pagamento;

import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.gateway.dataprovider.IPagamentoDataProvider;
import br.com.fiap.pagamento.gateway.repository.IPagamentoRepository;
import br.com.fiap.pagamento.utils.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CriarPagamentoUseCaseTest {


    private CriarPagamentoUseCase useCase;

    @Mock
    private IPagamentoRepository pagamentoRepository;
    @Mock
    private IPagamentoDataProvider pagamentoDataProvider;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new CriarPagamentoUseCase(pagamentoRepository, pagamentoDataProvider);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void devePermitirCriarNovoPagamento() {
        // Arrange
        var pagamentosMock = PagamentoHelper.gerarPagamentoRequest();
        Pagamento pagamentoDominio = PagamentoHelper.gerarPagamento();
        when(pagamentoDataProvider.criarPagamento(anyList(), anyString())).thenReturn("");
        when(pagamentoRepository.salvar(any(Pagamento.class))).thenReturn(pagamentoDominio);
        // Act
        useCase.criar(pagamentosMock);
        // Assert
        verify(pagamentoDataProvider, times(1)).criarPagamento(anyList(), anyString());
        verify(pagamentoRepository, times(1)).salvar(any(Pagamento.class));
    }

}
