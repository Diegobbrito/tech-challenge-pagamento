package br.com.fiap.pagamento.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UpdateStatusException extends RuntimeException {
    public UpdateStatusException(String mensagem) {
        super(mensagem);
    }
}
