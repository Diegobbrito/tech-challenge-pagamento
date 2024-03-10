package br.com.fiap.pagamento.api.controller;

import br.com.fiap.pagamento.api.controllers.PagamentoController;
import br.com.fiap.pagamento.api.dto.request.PagamentoRequest;
import br.com.fiap.pagamento.api.handler.RestExceptionHandler;
import br.com.fiap.pagamento.core.usecase.pagamento.IGerenciarPagamento;
import br.com.fiap.pagamento.utils.PagamentoHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PagamentoControllerTest {
    private MockMvc mockMvc;
    @Mock
    private IGerenciarPagamento gerenciarPagamentoUseCase;
    AutoCloseable openMocks;

    PagamentoControllerTest() {
    }

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        PagamentoController mensagemController = new PagamentoController(gerenciarPagamentoUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(mensagemController)
                .setControllerAdvice(new RestExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirAtualizarPagamento() throws Exception {
        var pagamentoRequest = PagamentoHelper.gerarPagamentoRealizadoRequest();

        doNothing().
                when(gerenciarPagamentoUseCase).validaPagamento(anyString(), any(PagamentoRequest.class));


        mockMvc.perform(post("/pagamentos/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pagamentoRequest)))
                .andExpect(status().isCreated());
        verify(gerenciarPagamentoUseCase, times(1))
                .validaPagamento(anyString(), any());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
