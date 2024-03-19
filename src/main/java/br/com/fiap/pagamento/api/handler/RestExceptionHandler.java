package br.com.fiap.pagamento.api.handler;

import br.com.fiap.pagamento.core.exception.PagamentoInexistenteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handlerPedidoInexistenteException(PagamentoInexistenteException ex) {
        final var details = new ExceptionDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(details);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handlerException(Exception ex) {
        final var details = new ExceptionDetails("Ocorreu um erro interno");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }
}

