package br.com.fiap.pagamento.gateway.dataprovider.pedido;

import br.com.fiap.pagamento.core.exception.UpdateStatusException;
import br.com.fiap.pagamento.gateway.dataprovider.IPedidoDataProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class PedidoDataProvider implements IPedidoDataProvider {

    @Value("pedido.host")
    private String pedidoHost;

    RestClient restClient = RestClient.create();

    @Override
    public void atualizarPedido(Integer pedidoId) {
        final var request = new PedidoStatusDto(2);
        restClient.patch()
                .uri(pedidoHost + pedidoId)
                .accept(APPLICATION_JSON)
                .body(request)
                .retrieve()
                .onStatus(status -> status.value() == 404, (res, response) -> {
                    throw new UpdateStatusException("Pedido não encontrado");
                })
                .onStatus(status -> status.value() == 400, (res, response) -> {
                    throw new UpdateStatusException("Erro ao atualizar pedido");
                });
    }
}
