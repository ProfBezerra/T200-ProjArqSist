package feira.problemasolid;

/**
 * Componente responsável por imprimir o cupom do pedido.
 */
public class ImpressoraTermica {
    public void imprimirCupom(Pedido pedido, double total) {
        System.out.println("=== CUPOM FISCAL ===");
        System.out.println("Cliente: " + pedido.getCliente());
        for (PedidoItem item : pedido.getItens()) {
            System.out.println(item.getProduto().getNome() + " x" + item.getQuantidade() + " = R$ " + item.subtotal());
        }
        System.out.println("Total: R$ " + total);
        System.out.println("====================");
    }
}
