package br.com.fiap.pagamento.gateway.messaging.cliente;

import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.gateway.messaging.IClienteQueue;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClienteQueue implements IClienteQueue {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson;


    @Value("${queue.cliente.pedido}")
    private String filaClientePedido;

    public ClienteQueue(RabbitTemplate rabbitTemplate, Gson gson) {
        this.rabbitTemplate = rabbitTemplate;

        this.gson = gson;
    }

    @Override
    public void publicarResultadoPagamento(Pagamento pagamento, boolean checkPagamento) {
        var message = new PagamentoStatusDto(pagamento.getPedidoId(), pagamento.getDocumentoCliente(), checkPagamento);
        rabbitTemplate.convertAndSend(filaClientePedido, gson.toJson(message));
    }
}