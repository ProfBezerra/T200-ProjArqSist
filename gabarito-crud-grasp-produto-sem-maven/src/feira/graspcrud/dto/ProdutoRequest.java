package feira.graspcrud.dto;

/**
 * DTO de entrada para cadastro/atualizacao de produto.
 */
public class ProdutoRequest {
    private final String nome;
    private final double preco;
    private final int estoque;
    private final long tipoProdutoId;

    /**
     * Cria o DTO com dados do menu.
     *
     * @param nome nome do produto
     * @param preco preco unitario
     * @param estoque quantidade em estoque
     * @param tipoProdutoId id do tipo de produto
     */
    public ProdutoRequest(String nome, double preco, int estoque, long tipoProdutoId) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.tipoProdutoId = tipoProdutoId;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public long getTipoProdutoId() {
        return tipoProdutoId;
    }
}
