package feira.grasp;

/**
 * PedidoItem representa uma linha do pedido (produto + quantidade).
 *
 * Padrões GRASP demonstrados:
 * - Information Expert: `PedidoItem` conhece `Produto` e calcula seu subtotal.
 * - High Cohesion: responsabilidade única de representar um item do pedido.
 */
public class PedidoItem {
    private final Produto produto;
    private final int quantidade;

    public PedidoItem(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // Information Expert: calcula o subtotal usando informação local
    public double subtotal() {
        return produto.getPreco() * quantidade;
    }

    public Produto getProduto() {
        return produto;
    }
}
