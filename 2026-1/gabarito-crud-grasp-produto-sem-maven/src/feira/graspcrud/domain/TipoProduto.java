package feira.graspcrud.domain;

import feira.graspcrud.exception.RegraNegocioException;

/**
 * Entidade de classificacao para produtos.
 */
public class TipoProduto {
    private Long id;
    private String nome;
    private String descricao;

    /**
     * Cria tipo com validacao de estado.
     *
     * @param id identificador (pode ser nulo para novo cadastro)
     * @param nome nome do tipo
     * @param descricao descricao opcional
     */
    public TipoProduto(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome == null ? null : nome.trim();
        this.descricao = descricao == null ? "" : descricao.trim();
    }

    /**
     * Valida regras de negocio da entidade.
     */
    public void validar() {
        if (nome == null || nome.length() < 3) {
            throw new RegraNegocioException("Nome do tipo deve ter ao menos 3 caracteres.");
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

    public String getDescricao() {
        return descricao;
    }
}
