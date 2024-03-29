package br.com.fiap.pagamento.gateway.dataprovider.pagamento;

import br.com.fiap.pagamento.api.dto.request.ProdutoRequest;
import br.com.fiap.pagamento.api.dto.request.ProdutoSelecionadoRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class PagamentoDataProviderTest {

    private PagamentoDataProvider pedidoDataProvider;
    @Mock
    MercadoPagoAPIDataProvider dataProvider;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoDataProvider = new PagamentoDataProvider(dataProvider);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testCriarPagamento() {

        ProdutoRequest produto = new ProdutoRequest(
                "Hamburguer",
                "Hamburguer da casa",
                new BigDecimal("19.99"),
                1,
                "");
        ProdutoSelecionadoRequest produtoSelecionado = new ProdutoSelecionadoRequest(produto, 1);

        when(dataProvider.criarPagamento(any(), any())).thenReturn("qrData");

        var pagamento = pedidoDataProvider.criarPagamento(List.of(produtoSelecionado), "");

        assertThat(pagamento)
                .isInstanceOf(String.class)
                .isNotNull();
        assertThat(pagamento)
                .isEqualTo("qrData");
    }

    @Test
    void testValidarPagamento() {

        ProdutoRequest produto = new ProdutoRequest(
                "Hamburguer",
                "Hamburguer da casa",
                new BigDecimal("19.99"),
                1,
                "");
        ProdutoSelecionadoRequest produtoSelecionado = new ProdutoSelecionadoRequest(produto, 1);

        when(dataProvider.validaPagamento(anyString())).thenReturn(true);

        var pagamento = pedidoDataProvider.validaPagamento("");

        assertThat(pagamento)
                .isInstanceOf(Boolean.class)
                .isNotNull();
        assertThat(pagamento)
                .isEqualTo(true);
    }
}
