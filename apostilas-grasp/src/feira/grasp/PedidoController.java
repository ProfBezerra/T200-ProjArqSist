package feira.grasp;

import feira.grasp.payment.PagamentoInfo;
import feira.grasp.payment.FormaPagamento;
import feira.grasp.payment.PagamentoFactory;
import feira.grasp.payment.PagamentoGateway;

/**
 * PedidoController: camada que representa o ponto de entrada (Controller) entre UI e serviço.
 *
 * Padrões GRASP demonstrados:
 * - Controller: centraliza chamadas vindas da interface/entrada e delega ao `PedidoService`.
 * - Indirection / Protected Variations: escolhe a implementação de pagamento via `PagamentoFactory`.
 */
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    public Pedido criarPedido() {
        return service.criarPedido();
    }

    // Controller coordena chamadas simples de alteração do domínio
    public void adicionarItem(Pedido pedido, Produto produto, int quantidade) {
        pedido.addItem(produto, quantidade);
        service.processarPedido(pedido);
    }

    // Protected Variations: seleciona gateway por forma de pagamento e delega ao serviço
    public boolean pagar(Pedido pedido, FormaPagamento forma, PagamentoInfo info) {
        PagamentoGateway gateway = PagamentoFactory.criar(forma);
        return service.pagarPedido(pedido, gateway, info);
    }
}
