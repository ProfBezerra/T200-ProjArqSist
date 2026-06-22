package feira.graspcrud.repository;

import feira.graspcrud.domain.TipoProduto;

import java.util.List;

/**
 * Abstracao de persistencia para tipos de produto.
 */
public interface TipoProdutoRepository {

    /**
     * Salva ou atualiza um tipo.
     *
     * @param tipo entidade
     * @return entidade persistida
     */
    TipoProduto salvar(TipoProduto tipo);

    /**
     * Lista todos os tipos.
     *
     * @return lista de tipos
     */
    List<TipoProduto> listarTodos();

    /**
     * Busca por id.
     *
     * @param id identificador
     * @return tipo encontrado ou null
     */
    TipoProduto buscarPorId(long id);

    /**
     * Verifica nome duplicado.
     *
     * @param nome nome a buscar
     * @param ignorarId id que deve ser ignorado na comparacao
     * @return true quando houver duplicidade
     */
    boolean existeNome(String nome, Long ignorarId);

    /**
     * Remove por id.
     *
     * @param id identificador
     * @return true se removeu
     */
    boolean remover(long id);
}
