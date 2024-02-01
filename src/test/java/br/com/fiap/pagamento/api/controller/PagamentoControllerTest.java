package br.com.fiap.pagamento.api.controller;

import br.com.fiap.pagamento.api.controllers.PagamentoController;
import br.com.fiap.pagamento.api.dto.request.CriarPagamentoRequest;
import br.com.fiap.pagamento.api.dto.request.PagamentoRequest;
import br.com.fiap.pagamento.api.dto.response.PagamentoStatusResponse;
import br.com.fiap.pagamento.api.handler.RestExceptionHandler;
import br.com.fiap.pagamento.core.exception.PagamentoInexistenteException;
import br.com.fiap.pagamento.core.usecase.pagamento.ICriarPagamento;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PagamentoControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ICriarPagamento criarPagamentoUseCase;
    @Mock
    private IGerenciarPagamento gerenciarPagamentoUseCase;
    AutoCloseable openMocks;

    PagamentoControllerTest() {
    }

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        PagamentoController mensagemController = new PagamentoController(criarPagamentoUseCase, gerenciarPagamentoUseCase);
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
    void devePermitirRegistrarPagamento() throws Exception {

        var pagamentoRequest = PagamentoHelper.gerarPagamentoRequest();
        var pagamentoResponse = PagamentoHelper.gerarPagamentoResponse();
        when(criarPagamentoUseCase.criar(any(CriarPagamentoRequest.class)))
                .thenReturn(pagamentoResponse);

        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pagamentoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.qrData").value(pagamentoResponse.getQrData()))
                .andExpect(jsonPath("$.valor").value(pagamentoResponse.getValor()));

        verify(criarPagamentoUseCase, times(1))
                .criar(any(CriarPagamentoRequest.class));

    }

    @Test
    void devePermitirConsultarStatusDePagamento() throws Exception {
        var pagamentoStatus = new PagamentoStatusResponse("Pagamento pendente");
        when(gerenciarPagamentoUseCase.consultarStatusDePagamento(any(UUID.class)))
                .thenReturn(pagamentoStatus);

        mockMvc.perform(get("/pagamentos/{pagamentoId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(pagamentoStatus.status()));
        verify(gerenciarPagamentoUseCase, times(1)).consultarStatusDePagamento(any(UUID.class));
    }

    @Test
    void deveRetornarErroAoConsultarStatusDePagamentoComIdInexistente() throws Exception {
        when(gerenciarPagamentoUseCase.consultarStatusDePagamento(any(UUID.class)))
                .thenThrow(new PagamentoInexistenteException("Pagamento Inexistente"));

        mockMvc.perform(get("/pagamentos/{pagamentoId}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(gerenciarPagamentoUseCase, times(1)).consultarStatusDePagamento(any(UUID.class));
    }

    @Test
    void deveRetornarErroAoConsultarStatusDePagamento() throws Exception {
        when(gerenciarPagamentoUseCase.consultarStatusDePagamento(any(UUID.class)))
                .thenThrow(new IllegalArgumentException("Pedido não encontrado"));

        mockMvc.perform(get("/pagamentos/{pagamentoId}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
        verify(gerenciarPagamentoUseCase, times(1)).consultarStatusDePagamento(any(UUID.class));
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
