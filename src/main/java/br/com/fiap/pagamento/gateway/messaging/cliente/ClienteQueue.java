package br.com.fiap.pagamento.gateway.messaging.cliente;

import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.gateway.messaging.IClienteQueue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClienteQueue implements IClienteQueue {

    private final RabbitTemplate rabbitTemplate;

    private final MessageConverter messageConverter;


    @Value("${queue.cliente.pedido}")
    private String filaClientePedido;

    public ClienteQueue(RabbitTemplate rabbitTemplate, MessageConverter messageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
    }

    @Override
    public void publicarResultadoPagamento(Pagamento pagamento, boolean checkPagamento) {
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.convertAndSend(filaClientePedido,
                new PagamentoStatusDto(pagamento.getPedidoId(), pagamento.getDocumentoCliente(), checkPagamento));
    }
}