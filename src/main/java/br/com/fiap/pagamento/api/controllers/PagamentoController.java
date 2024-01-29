package br.com.fiap.pagamento.api.controllers;

import br.com.fiap.pagamento.api.dto.request.CriarPagamentoRequest;
import br.com.fiap.pagamento.api.dto.request.PagamentoRequest;
import br.com.fiap.pagamento.api.dto.response.PagamentoResponse;
import br.com.fiap.pagamento.api.dto.response.PagamentoStatusResponse;
import br.com.fiap.pagamento.core.usecase.pagamento.ICriarPagamento;
import br.com.fiap.pagamento.core.usecase.pagamento.IGerenciarPagamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pedidos", description = "Controle de pedidos")
@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final ICriarPagamento criarPagamentoUseCase;
    private final IGerenciarPagamento gerenciarPagamentoUseCase;

    public PagamentoController(ICriarPagamento criarPagamentoUseCase, IGerenciarPagamento gerenciarPagamentoUseCase) {
        this.criarPagamentoUseCase = criarPagamentoUseCase;
        this.gerenciarPagamentoUseCase = gerenciarPagamentoUseCase;
    }

    @Operation(summary = "Criação de pagamento")
    @PostMapping
    public ResponseEntity<PagamentoResponse> criarPagamento(@RequestBody CriarPagamentoRequest request) {
        final var response = criarPagamentoUseCase.criar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Consulta status de pagamento")
    @GetMapping("/{pagamentoId}")
    public ResponseEntity<PagamentoStatusResponse> detalheDePagamentoDoPedido(@Parameter(example = "1") @PathVariable String pagamentoId) {
        return ResponseEntity.ok(gerenciarPagamentoUseCase.consultarStatusDePagamento(pagamentoId));
    }


    @Operation(summary = "Recebe dados de que o pagamento do pedido foi realizado")
    @PostMapping("/{pagamentoId}")
    public ResponseEntity<Void> pagamento(@Parameter(example = "1") @PathVariable String pagamentoId, @RequestBody PagamentoRequest request) {
        gerenciarPagamentoUseCase.validaPagamento(pagamentoId, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
