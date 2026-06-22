package feira.problemasolid;

/**
 * Item do pedido com produto e quantidade.
 */
public class PedidoItem {
    private final Produto produto;
    private final int quantidade;

    public PedidoItem(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double subtotal() {
        return produto.getPreco() * quantidade;
    }
}
