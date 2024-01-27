package br.com.fiap.pagamento.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PagamentoInexistenteException extends RuntimeException {
    public PagamentoInexistenteException(String mensagem) {
        super(mensagem);
    }
}
