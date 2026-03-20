package feira.graspcrud.service;

import feira.graspcrud.domain.Produto;
import feira.graspcrud.dto.ProdutoRequest;
import feira.graspcrud.exception.RegraNegocioException;
import feira.graspcrud.repository.ProdutoRepository;
import feira.graspcrud.repository.TipoProdutoRepository;

import java.util.List;

/**
 * Caso de uso de manutencao de produto.
 */
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final TipoProdutoRepository tipoRepository;

    /**
     * Cria o servico com dependencias por abstracao.
     *
     * @param produtoRepository repositorio de produtos
     * @param tipoRepository repositorio de tipos
     */
    public ProdutoService(ProdutoRepository produtoRepository, TipoProdutoRepository tipoRepository) {
        this.produtoRepository = produtoRepository;
        this.tipoRepository = tipoRepository;
    }

    /**
     * Cadastra produto validando tipo de produto existente.
     *
     * @param request dados de entrada
     * @return produto cadastrado
     */
    public Produto criar(ProdutoRequest request) {
        validarTipoExiste(request.getTipoProdutoId());
        Produto produto = new Produto(null, request.getNome(), request.getPreco(), request.getEstoque(), request.getTipoProdutoId());
        produto.validar();
        return produtoRepository.salvar(produto);
    }

    /**
     * Atualiza produto existente.
     *
     * @param id id do produto
     * @param request novos dados
     * @return produto atualizado
     */
    public Produto atualizar(long id, ProdutoRequest request) {
        buscarPorId(id);
        validarTipoExiste(request.getTipoProdutoId());
        Produto produto = new Produto(id, request.getNome(), request.getPreco(), request.getEstoque(), request.getTipoProdutoId());
        produto.validar();
        return produtoRepository.salvar(produto);
    }

    /**
     * Busca produto por id.
     *
     * @param id identificador
     * @return produto encontrado
     */
    public Produto buscarPorId(long id) {
        Produto produto = produtoRepository.buscarPorId(id);
        if (produto == null) {
            throw new RegraNegocioException("Produto nao encontrado.");
        }
        return produto;
    }

    /**
     * Lista todos os produtos.
     *
     * @return lista de produtos
     */
    public List<Produto> listarTodos() {
        return produtoRepository.listarTodos();
    }

    /**
     * Exclui produto por id.
     *
     * @param id identificador
     */
    public void remover(long id) {
        if (!produtoRepository.remover(id)) {
            throw new RegraNegocioException("Produto nao encontrado.");
        }
    }

    private void validarTipoExiste(long tipoId) {
        if (tipoRepository.buscarPorId(tipoId) == null) {
            throw new RegraNegocioException("Tipo de produto nao encontrado.");
        }
    }
}
