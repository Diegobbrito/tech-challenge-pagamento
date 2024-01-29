package br.com.fiap.pagamento.gateway.dataprovider.pedido;

import br.com.fiap.pagamento.gateway.dataprovider.IPedidoDataProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class PedidoDataProvider implements IPedidoDataProvider {

    @Value("pedido.host")
    private String pedidoHost;

    private final RestClient restClient;

    public PedidoDataProvider(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public boolean atualizarPedido(Integer pedidoId) {
        final var request = new PedidoStatusDto(2);
        final var response = restClient.patch()
                .uri(pedidoHost + pedidoId)
                .accept(APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(PedidoResponseDto.class);
        if (response.getBody() != null && response.getBody().status() != null)
            return "Recebido".equals(response.getBody().status().descricao());
        return false;
    }
}
