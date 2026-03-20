package feira.graspcrud.service;

import feira.graspcrud.domain.TipoProduto;
import feira.graspcrud.dto.TipoProdutoRequest;
import feira.graspcrud.exception.RegraNegocioException;
import feira.graspcrud.repository.ProdutoRepository;
import feira.graspcrud.repository.TipoProdutoRepository;

import java.util.List;

/**
 * Caso de uso de manutencao de tipo de produto.
 */
public class TipoProdutoService {
    private final TipoProdutoRepository tipoRepository;
    private final ProdutoRepository produtoRepository;

    /**
     * Cria o servico com dependencias por abstracao.
     *
     * @param tipoRepository repositorio de tipos
     * @param produtoRepository repositorio de produtos
     */
    public TipoProdutoService(TipoProdutoRepository tipoRepository, ProdutoRepository produtoRepository) {
        this.tipoRepository = tipoRepository;
        this.produtoRepository = produtoRepository;
    }

    /**
     * Cadastra tipo com validacoes de dominio e unicidade.
     *
     * @param request dados de entrada
     * @return tipo cadastrado
     */
    public TipoProduto criar(TipoProdutoRequest request) {
        if (tipoRepository.existeNome(request.getNome(), null)) {
            throw new RegraNegocioException("Ja existe tipo com este nome.");
        }
        TipoProduto tipo = new TipoProduto(null, request.getNome(), request.getDescricao());
        tipo.validar();
        return tipoRepository.salvar(tipo);
    }

    /**
     * Lista todos os tipos.
     *
     * @return lista de tipos
     */
    public List<TipoProduto> listarTodos() {
        return tipoRepository.listarTodos();
    }

    /**
     * Busca tipo por id.
     *
     * @param id identificador
     * @return tipo encontrado
     */
    public TipoProduto buscarPorId(long id) {
        TipoProduto tipo = tipoRepository.buscarPorId(id);
        if (tipo == null) {
            throw new RegraNegocioException("Tipo de produto nao encontrado.");
        }
        return tipo;
    }

    /**
     * Remove tipo quando nao houver vinculo com produto.
     *
     * @param id identificador
     */
    public void remover(long id) {
        if (produtoRepository.existePorTipo(id)) {
            throw new RegraNegocioException("Nao e permitido remover tipo em uso por produto.");
        }
        if (!tipoRepository.remover(id)) {
            throw new RegraNegocioException("Tipo de produto nao encontrado.");
        }
    }
}
