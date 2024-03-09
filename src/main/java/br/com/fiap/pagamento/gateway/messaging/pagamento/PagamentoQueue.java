package br.com.fiap.pagamento.gateway.messaging.pagamento;

import br.com.fiap.pagamento.api.dto.request.CriarPagamentoRequest;
import br.com.fiap.pagamento.core.usecase.pagamento.ICriarPagamento;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PagamentoQueue {

    private final Gson gson;

    private final ICriarPagamento criarPagamento;

    public PagamentoQueue(ICriarPagamento criarPagamento, Gson gson) {
        this.criarPagamento = criarPagamento;
        this.gson = gson;
    }

    @RabbitListener(queues = {"${queue.pagamentos.criado}"})
    public void validarPagamentoCriado(@Payload String message) {
        CriarPagamentoRequest pagamentoDto = gson.fromJson(message, CriarPagamentoRequest.class);
        criarPagamento.criar(pagamentoDto);
    }


}