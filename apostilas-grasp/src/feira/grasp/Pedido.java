package feira.grasp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import feira.grasp.desconto.Desconto;

/**
 * Pedido (agregação de itens do pedido).
 *
 * Padrões GRASP demonstrados:
 * - Information Expert: `Pedido` conhece seus `PedidoItem` e calcula totais.
 * - Creator: `Pedido` é responsável por criar `PedidoItem` a partir de produto+quantidade.
 * - Low Coupling / High Cohesion: mantém lógica de negócio coesa; persistência é delegada a um repositório (Pure Fabrication).
 * - Polymorphism: aceita uma estratégia de `Desconto` que altera o comportamento sem condicionais.
 */
public class Pedido {
    private final String id = UUID.randomUUID().toString();
    private final List<PedidoItem> itens = new ArrayList<>();
    private Desconto desconto;

    // Creator: método de conveniência que cria PedidoItem internamente
    public void addItem(Produto produto, int quantidade) {
        this.itens.add(new PedidoItem(produto, quantidade));
    }

    // Mantém compatibilidade com código que fornece PedidoItem
    public void addItem(PedidoItem item) {
        itens.add(item);
    }

    public String getId() {
        return id;
    }

    public List<PedidoItem> getItens() {
        return new ArrayList<>(itens);
    }

    public void setDesconto(Desconto desconto) {
        this.desconto = desconto;
    }

    // Information Expert: agrega conhecimento para calcular o total
    public double calcularTotal() {
        return itens.stream().mapToDouble(PedidoItem::subtotal).sum();
    }

    // Aplica estratégia de desconto (Polymorphism)
    public double calcularTotalComDesconto() {
        double total = calcularTotal();
        if (desconto != null) {
            return total - desconto.aplicar(this);
        }
        return total;
    }
}
