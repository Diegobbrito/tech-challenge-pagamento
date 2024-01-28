package br.com.fiap.pagamento.core.usecase.pagamento;


import br.com.fiap.pagamento.api.dto.response.PagamentoStatusResponse;
import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.gateway.dataprovider.IPagamentoDataProvider;
import br.com.fiap.pagamento.gateway.dataprovider.IPedidoDataProvider;
import br.com.fiap.pagamento.gateway.repository.IPagamentoRepository;
import br.com.fiap.pagamento.utils.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GerenciarPagamentoUseCaseTest {


    private GerenciarPagamentoUseCase useCase;

    @Mock
    private IPagamentoRepository pagamentoRepository;
    @Mock
    private IPagamentoDataProvider pagamentoDataProvider;
    @Mock
    private IPedidoDataProvider pedidoDataProvider;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new GerenciarPagamentoUseCase(pagamentoRepository, pagamentoDataProvider, pedidoDataProvider);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void devePermitirConsultarStatusDePagamento() {
        // Arrange
        var pagamentoDominio = PagamentoHelper.gerarPagamento();
        when(pagamentoRepository.buscarPorId(any(UUID.class))).thenReturn(pagamentoDominio);
        // Act
        var produto = useCase.consultarStatusDePagamento("0f44927d-5a3b-449a-9e6a-7ba0bf4d74c5");
        // Assert
        verify(pagamentoRepository, times(1)).buscarPorId(any(UUID.class));
        assertThat(produto)
                .isInstanceOf(PagamentoStatusResponse.class)
                .isNotNull();
        assertThat(produto)
                .extracting(PagamentoStatusResponse::status)
                .isEqualTo("Pagamento Pendente");
    }

    @Test
    void deveValidarPagamento() {
        // Arrange
        var pagamentosMock = PagamentoHelper.gerarPagamentoRealizadoRequest();
        var pagamentoDominio = PagamentoHelper.gerarPagamento();
        when(pagamentoRepository.buscarPorId(any(UUID.class))).thenReturn(pagamentoDominio);
        when(pagamentoDataProvider.validaPagamento(anyString())).thenReturn(true);
        when(pagamentoRepository.salvar(any(Pagamento.class))).thenReturn(pagamentoDominio);
        when(pedidoDataProvider.atualizarPedido(anyInt())).thenReturn(true);
        // Act
        useCase.validaPagamento("0f44927d-5a3b-449a-9e6a-7ba0bf4d74c5", pagamentosMock);
        // Assert
        verify(pagamentoRepository, times(1)).buscarPorId(any(UUID.class));
        verify(pagamentoDataProvider, times(1)).validaPagamento(anyString());
        verify(pagamentoRepository, times(1)).salvar(any(Pagamento.class));
        verify(pedidoDataProvider, times(1)).atualizarPedido(anyInt());
    }

}
