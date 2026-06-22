package feira.graspcrud.repository;

import feira.graspcrud.domain.Produto;

import java.util.List;

/**
 * Abstracao de persistencia para produtos.
 */
public interface ProdutoRepository {

    /**
     * Salva ou atualiza produto.
     *
     * @param produto entidade
     * @return entidade persistida
     */
    Produto salvar(Produto produto);

    /**
     * Lista todos os produtos.
     *
     * @return lista de produtos
     */
    List<Produto> listarTodos();

    /**
     * Busca por id.
     *
     * @param id identificador
     * @return produto encontrado ou null
     */
    Produto buscarPorId(long id);

    /**
     * Remove por id.
     *
     * @param id identificador
     * @return true se removeu
     */
    boolean remover(long id);

    /**
     * Verifica se ha produto vinculado ao tipo.
     *
     * @param tipoId identificador do tipo
     * @return true se existir vinculo
     */
    boolean existePorTipo(long tipoId);
}
