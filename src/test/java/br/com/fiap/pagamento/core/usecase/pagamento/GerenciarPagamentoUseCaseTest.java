package br.com.fiap.pagamento.core.usecase.pagamento;


import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.gateway.dataprovider.IPagamentoDataProvider;
import br.com.fiap.pagamento.gateway.messaging.IClienteQueue;
import br.com.fiap.pagamento.gateway.messaging.IPedidoQueue;
import br.com.fiap.pagamento.gateway.repository.IPagamentoRepository;
import br.com.fiap.pagamento.utils.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GerenciarPagamentoUseCaseTest {


    private GerenciarPagamentoUseCase useCase;

    @Mock
    private IPagamentoRepository pagamentoRepository;
    @Mock
    private IPagamentoDataProvider pagamentoDataProvider;
    @Mock
    private IPedidoQueue pedidoQueue;

    @Mock
    private IClienteQueue clienteQueue;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new GerenciarPagamentoUseCase(pagamentoRepository, pagamentoDataProvider, pedidoQueue, clienteQueue);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveValidarPagamento() {
        // Arrange
        var pagamentosMock = PagamentoHelper.gerarPagamentoRealizadoRequest();
        var pagamentoDominio = PagamentoHelper.gerarPagamento();
        when(pagamentoRepository.buscarPorId(any(UUID.class))).thenReturn(pagamentoDominio);
        when(pagamentoDataProvider.validaPagamento(anyString())).thenReturn(true);
        when(pagamentoRepository.salvar(any(Pagamento.class))).thenReturn(pagamentoDominio);
        doNothing().when(pedidoQueue).publicarAtualizacaoStatusPedido(anyInt());
        // Act
        useCase.validaPagamento("0f44927d-5a3b-449a-9e6a-7ba0bf4d74c5", pagamentosMock);
        // Assert
        verify(pagamentoRepository, times(1)).buscarPorId(any(UUID.class));
        verify(pagamentoDataProvider, times(1)).validaPagamento(anyString());
        verify(pagamentoRepository, times(1)).salvar(any(Pagamento.class));
        verify(pedidoQueue, times(1)).publicarAtualizacaoStatusPedido(anyInt());
    }

}
