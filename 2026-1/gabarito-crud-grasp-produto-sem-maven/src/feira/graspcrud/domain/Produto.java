package feira.graspcrud.domain;

import feira.graspcrud.exception.RegraNegocioException;

/**
 * Entidade de produto comercializado na feira.
 */
public class Produto {
    private Long id;
    private String nome;
    private double preco;
    private int estoque;
    private long tipoProdutoId;

    /**
     * Cria produto com validacao de estado.
     *
     * @param id identificador (pode ser nulo para novo cadastro)
     * @param nome nome do produto
     * @param preco preco unitario
     * @param estoque quantidade em estoque
     * @param tipoProdutoId id do tipo associado
     */
    public Produto(Long id, String nome, double preco, int estoque, long tipoProdutoId) {
        this.id = id;
        this.nome = nome == null ? null : nome.trim();
        this.preco = preco;
        this.estoque = estoque;
        this.tipoProdutoId = tipoProdutoId;
    }

    /**
     * Valida regras de negocio da entidade.
     */
    public void validar() {
        if (nome == null || nome.length() < 3) {
            throw new RegraNegocioException("Nome do produto deve ter ao menos 3 caracteres.");
        }
        if (preco <= 0) {
            throw new RegraNegocioException("Preco deve ser maior que zero.");
        }
        if (estoque < 0) {
            throw new RegraNegocioException("Estoque nao pode ser negativo.");
        }
        if (tipoProdutoId <= 0) {
            throw new RegraNegocioException("Tipo de produto invalido.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
