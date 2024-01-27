package br.com.fiap.pagamento.gateway.dataprovider.pedido;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class PedidoDataProviderTest {

    private PedidoDataProvider pedidoDataProvider;
    @Mock
    private RestClient restClient;


    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoDataProvider = new PedidoDataProvider(restClient);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void atualizarPedido(Integer pedidoId) {

        var pedidoResponse = new PedidoResponseDto(
                1,
                "R$ 19,99",
                new PedidoResponseDto.StatusResponse("Recebido"));
        when(restClient.patch().body(any())).thenReturn(pedidoResponse);
        pedidoDataProvider.atualizarPedido(1);
    }
}
