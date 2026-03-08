package feira.grasp;

import feira.grasp.repository.PedidoRepository;
import feira.grasp.repository.PedidoRepositoryMemoria;
import feira.grasp.payment.PagamentoInfo;
import feira.grasp.payment.FormaPagamento;
import feira.grasp.desconto.DescontoPromocional;

/**
 * Demo de execução do exemplo GRASP.
 *
 * Comentários GRASP:
 * - Demonstra criação (Creator), coordenação (Controller, PedidoService), uso de repositório (Pure Fabrication),
 *   seleção de gateway (Indirection / Protected Variations) e aplicação de desconto (Polymorphism).
 */
public class MainGrasp {
    public static void main(String[] args) {
        PedidoRepository repo = new PedidoRepositoryMemoria();
        PedidoService service = new PedidoService(repo);
        PedidoController controller = new PedidoController(service);

        Produto banana = new Produto("Banana", 2.5);
        Produto maca = new Produto("Maçã", 3.0);

        Pedido pedido = controller.criarPedido();
        controller.adicionarItem(pedido, banana, 4);
        controller.adicionarItem(pedido, maca, 2);

        // aplicar desconto (polymorphism)
        pedido.setDesconto(new DescontoPromocional(0.15)); // 15% promoção

        service.processarPedido(pedido);

        // pagar via gateway (indirection) escolhendo forma
        boolean pago = controller.pagar(pedido, FormaPagamento.PIX, new PagamentoInfo("PIX", "ref-123"));
        System.out.println("Pagamento aprovado? " + pago);
    }
}
