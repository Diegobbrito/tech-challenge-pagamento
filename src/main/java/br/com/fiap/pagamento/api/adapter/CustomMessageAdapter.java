package br.com.fiap.pagamento.api.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageAdapter implements MessageConverter {

    private final ObjectMapper objectMapper;

    public CustomMessageAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) {
        try {
            String json = objectMapper.writeValueAsString(object);
            return new Message(json.getBytes(), messageProperties);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro na conversão para JSON", e);
        }
    }

    @Override
    public Object fromMessage(Message message) {
        throw new UnsupportedOperationException("Conversão de mensagem para objeto não suportada.");
    }
}
