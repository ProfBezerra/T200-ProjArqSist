package feira.gabarito.domain;

public class PedidoItem {
    private final Produto produto;
    private final int quantidade;

    public PedidoItem(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto é obrigatório");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
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
        return produto.getPreco() * quantidade;
    }
}
