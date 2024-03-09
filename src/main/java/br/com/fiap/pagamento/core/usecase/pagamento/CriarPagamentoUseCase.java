package br.com.fiap.pagamento.core.usecase.pagamento;

import br.com.fiap.pagamento.api.adapter.PagamentoAdapter;
import br.com.fiap.pagamento.api.dto.request.CriarPagamentoRequest;
import br.com.fiap.pagamento.api.dto.response.PagamentoResponse;
import br.com.fiap.pagamento.config.UseCase;
import br.com.fiap.pagamento.core.entity.Pagamento;
import br.com.fiap.pagamento.core.enumerator.StatusEnum;
import br.com.fiap.pagamento.gateway.dataprovider.IPagamentoDataProvider;
import br.com.fiap.pagamento.gateway.repository.IPagamentoRepository;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class CriarPagamentoUseCase implements ICriarPagamento {

    private final IPagamentoRepository pagamentoRepository;
    private final IPagamentoDataProvider pagamentoDataProvider;

    public CriarPagamentoUseCase(IPagamentoRepository pagamentoRepository, IPagamentoDataProvider pagamentoDataProvider) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoDataProvider = pagamentoDataProvider;
    }

    @Transactional
    @Override
    public void criar(CriarPagamentoRequest request) {
        final var produtos = request.produtos();
        final var valor = request.valor();
        String cpfFormatado = "";
        if (request.cpf() != null && !request.cpf().isBlank()) {
            cpfFormatado = request.cpf().trim().replaceAll("\\.", "").replace("-", "");
        }
        final var qrData = pagamentoDataProvider.criarPagamento(produtos, cpfFormatado);
        Pagamento pagamento = PagamentoAdapter.toPagamento(valor, StatusEnum.PAGAMENTOPENDENTE, cpfFormatado, qrData, request.pedidoId());
        pagamentoRepository.salvar(pagamento);
    }
}
