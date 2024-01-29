package br.com.fiap.pagamento.gateway.dataprovider.pedido;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class PedidoDataProviderTest {

    private PedidoDataProvider pedidoDataProvider;
    @Mock
    private RestClient restClient;

    @Mock
    RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    RestClient.RequestBodySpec requestBodySpec;

    @Mock
    RestClient.ResponseSpec responseSpec;


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
    void testAtualizarPedidoPedidoEncontrado2() {

        var pedidoDto = new PedidoResponseDto(1, "R$ 19,99", new PedidoResponseDto.StatusResponse("Recebido"));

        when(restClient.patch()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.accept(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.body((Object) any())).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.toEntity(PedidoResponseDto.class)).thenReturn(new ResponseEntity<>(pedidoDto, HttpStatus.OK));

        assertTrue(pedidoDataProvider.atualizarPedido(1));
    }
}
