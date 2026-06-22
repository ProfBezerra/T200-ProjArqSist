package feira.gabarito.cupom;

import feira.gabarito.domain.Pedido;
import feira.gabarito.domain.PedidoItem;

/**
 * Implementação de impressão de cupom em modo textual.
 */
public class ImpressoraTermica implements ImpressoraCupom {
    @Override
    public void imprimir(Pedido pedido, double totalLiquido) {
        System.out.println("=== CUPOM ===");
        System.out.println("Cliente: " + pedido.getCliente());
        for (PedidoItem item : pedido.getItens()) {
            System.out.println(item.getProduto().getNome() + " x" + item.getQuantidade() + " = R$ " + item.subtotal());
        }
        System.out.println("Total líquido: R$ " + totalLiquido);
        System.out.println("=============");
    }
}
