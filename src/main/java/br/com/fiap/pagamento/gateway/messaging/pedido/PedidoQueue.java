package br.com.fiap.pagamento.gateway.messaging.pedido;

import br.com.fiap.pagamento.gateway.dataprovider.pedido.PedidoStatusDto;
import br.com.fiap.pagamento.gateway.messaging.IPedidoQueue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoQueue implements IPedidoQueue {

    private final RabbitTemplate rabbitTemplate;

    private final MessageConverter messageConverter;


    @Value("${queue.pedidos.pagos}")
    private String filaPedidosPagos;

    public PedidoQueue(RabbitTemplate rabbitTemplate, MessageConverter messageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
    }


    @Override
    public void publicarAtualizacaoStatusPedido(Integer pedidoId) {
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.convertAndSend(filaPedidosPagos, new PedidoStatusDto(pedidoId,2));
    }

}