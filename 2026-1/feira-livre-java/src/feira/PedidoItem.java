/**
 * PedidoItem
 * - Composição: relaciona um Produto com uma quantidade.
 * - Coesão: responsabilidade focada em subtotal e dados do item.
 */
package feira;

public class PedidoItem {
    private final Produto produto;
    private final int quantidade;

    public PedidoItem(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
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
        // Regra coesa: subtotal = preço do produto * quantidade
        return produto.getPreco() * quantidade;
    }
}
