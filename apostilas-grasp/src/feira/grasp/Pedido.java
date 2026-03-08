package feira.grasp;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final List<PedidoItem> itens = new ArrayList<>();

    public void addItem(PedidoItem item) {
        itens.add(item);
    }

    // Exemplo de Information Expert: o Pedido conhece seus itens e calcula o total
    public double calcularTotal() {
        return itens.stream().mapToDouble(PedidoItem::subtotal).sum();
    }
}
