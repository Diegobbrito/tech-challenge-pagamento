package br.com.fiap.pagamento.gateway.dataprovider.pedido;

public record PedidoResponseDto(
        Integer id,
        String valorTotal,
        StatusResponse status
) {
    record StatusResponse(String descricao) {
    }
}
