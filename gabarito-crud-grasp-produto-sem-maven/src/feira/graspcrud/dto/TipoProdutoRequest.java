package feira.graspcrud.dto;

/**
 * DTO de entrada para cadastro de tipo de produto.
 */
public class TipoProdutoRequest {
    private final String nome;
    private final String descricao;

    /**
     * Cria o DTO com dados do menu.
     *
     * @param nome nome do tipo
     * @param descricao descricao opcional
     */
    public TipoProdutoRequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
