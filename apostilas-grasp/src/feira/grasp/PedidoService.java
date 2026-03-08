package feira.grasp;

import feira.grasp.repository.PedidoRepository;
import feira.grasp.payment.PagamentoGateway;
import feira.grasp.payment.PagamentoInfo;

/**
 * PedidoService: camada de aplicação que coordena operações de pedido.
 *
 * Padrões GRASP demonstrados:
 * - Controller: serve de ponto de entrada para operações de pedido (via `PedidoController`).
 * - Low Coupling: depende de abstrações (`PedidoRepository`, `PagamentoGateway`) em vez de implementações concretas.
 * - Pure Fabrication / Indirection: persistência e gateways de pagamento são separados em classes próprias.
 */
public class PedidoService {
    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    // Creator: cria e persiste um novo Pedido
    public Pedido criarPedido() {
        Pedido p = new Pedido();
        repository.salvar(p);
        return p;
    }

    // Alta coesão: mantém lógica de processamento do pedido em um único lugar
    public void processarPedido(Pedido pedido) {
        double total = pedido.calcularTotalComDesconto();
        System.out.println("Processando pedido. Total com desconto = " + total);
        repository.salvar(pedido);
    }

    // Indirection: aceita o gateway como parâmetro, separando a lógica de pagamento
    public boolean pagarPedido(Pedido pedido, PagamentoGateway gateway, PagamentoInfo info) {
        boolean ok = gateway.pagar(pedido, info);
        if (ok) {
            repository.salvar(pedido);
        }
        return ok;
    }
}
