package br.com.fiap.pagamento.gateway.dataprovider.pedido;

import br.com.fiap.pagamento.core.exception.UpdateStatusException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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
        // Configuração do mock para um cenário onde o pedido é encontrado

        RestClient.RequestBodyUriSpec requestBodyUriSpec = mock(RestClient.RequestBodyUriSpec.class);
        when(restClient.patch()).thenReturn(requestBodyUriSpec);

        RestClient.RequestBodySpec requestBodySpec = mock(RestClient.RequestBodySpec.class);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.accept(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any())).thenReturn(requestBodySpec);

        // Se o RestClient se comportar como WebClient e body retornar null, configure-o para retornar requestBodySpec
        when(requestBodySpec.body((Object) any()))
                .thenReturn(requestBodySpec);

        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);

        var pedidoDto = new PedidoResponseDto(1, "R$ 19,99", new PedidoResponseDto.StatusResponse("Pago"));
        when(responseSpec.toEntity(PedidoResponseDto.class)).thenReturn(new ResponseEntity<>(pedidoDto, HttpStatus.OK));

        assertTrue(pedidoDataProvider.atualizarPedido(1));
    }

    @Test
    void testAtualizarPedidoPedidoEncontradoT() {
        // Configuração do mock para um cenário onde o pedido é encontrado

        RestClient.RequestBodyUriSpec requestBodyUriSpec = mock(RestClient.RequestBodyUriSpec.class);
        when(restClient.patch()).thenReturn(requestBodyUriSpec);

        RestClient.RequestBodySpec requestBodySpec = mock(RestClient.RequestBodySpec.class);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.accept(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any())).thenReturn(requestBodySpec);

        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);

        var pedidoDto = new PedidoResponseDto(1, "R$ 19,99", new PedidoResponseDto.StatusResponse("Pago"));
        when(responseSpec.toEntity(PedidoResponseDto.class)).thenReturn(new ResponseEntity<>(pedidoDto, HttpStatus.OK));

        assertTrue(pedidoDataProvider.atualizarPedido(1));
    }

    @Test
    void testAtualizarPedidoPedidoEncontrado() {
        // Configuração do mock para um cenário onde o pedido é encontrado

        when(restClient.patch())
                .thenReturn(requestBodyUriSpec);

        when(requestBodyUriSpec.uri(anyString()))
                .thenReturn(requestBodySpec);

        when(requestBodySpec.accept(any()))
                .thenReturn(requestBodySpec);

        when(requestBodySpec.body(any()))
                .thenReturn(requestBodySpec);

        when(requestBodySpec.retrieve())
                .thenReturn(responseSpec);

        var pedidoDto = new PedidoResponseDto(1,"R$ 19,99", new PedidoResponseDto.StatusResponse("Pago"));

        when(responseSpec.toEntity(PedidoResponseDto.class)).thenReturn(new ResponseEntity<>(pedidoDto, HttpStatus.OK));


        assertTrue(pedidoDataProvider.atualizarPedido(1));
    }

//    @Test
//    void testAtualizarPedidoPedidoNaoEncontrado() {
//        // Configuração do mock para um cenário onde o pedido não é encontrado
//        when(restClient.patch())
//                .thenReturn(restClient);
//
//        when(restClient.uri(anyString()))
//                .thenReturn(restClient);
//
//        when(restClient.accept(any()))
//                .thenReturn(restClient);
//
//        when(restClient.body(any()))
//                .thenReturn(restClient);
//
//        when(restClient.retrieve())
//                .thenThrow(WebClientResponseException.create(404, "Not Found", null, null, null));
//
//        assertThrows(UpdateStatusException.class, () -> pedidoDataProvider.atualizarPedido(1));
//    }
//
//    @Test
//    void testAtualizarPedidoErroAoAtualizar() {
//        // Configuração do mock para um cenário onde ocorre um erro ao atualizar o pedido
//        when(restClient.patch())
//                .thenReturn(restClient);
//
//        when(restClient.uri(anyString()))
//                .thenReturn(restClient);
//
//        when(restClient.accept(any()))
//                .thenReturn(restClient);
//
//        when(restClient.body(any()))
//                .thenReturn(restClient);
//
//        when(restClient.retrieve())
//                .thenThrow(WebClientResponseException.create(400, "Bad Request", null, null, null));
//
//        assertThrows(UpdateStatusException.class, () -> pedidoDataProvider.atualizarPedido(1));
//    }
//
//
//
//    @Test
//    public void atualizarPedido(Integer pedidoId) {
//
//        var pedidoResponse = new PedidoResponseDto(
//                1,
//                "R$ 19,99",
//                new PedidoResponseDto.StatusResponse("Recebido"));
//        when(restClient.patch().body(any())).thenReturn(pedidoResponse);
//        pedidoDataProvider.atualizarPedido(1);
//    }
//
}
