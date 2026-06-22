package feira.graspcrud.repository.json;

import feira.graspcrud.domain.Produto;
import feira.graspcrud.repository.ProdutoRepository;
import feira.graspcrud.util.JsonMini;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio JSON para produtos.
 */
public class ProdutoRepositoryJson implements ProdutoRepository {
    private final Path arquivo;
    private final List<Produto> banco;
    private long proximoId;

    /**
     * Cria repositrio carregando dados do arquivo JSON.
     *
     * @param arquivo caminho do arquivo
     */
    public ProdutoRepositoryJson(Path arquivo) {
        this.arquivo = arquivo;
        this.banco = JsonMini.carregarProdutos(arquivo);
        this.proximoId = banco.stream().mapToLong(p -> p.getId() == null ? 0 : p.getId()).max().orElse(0) + 1;
    }

    @Override
    public Produto salvar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(proximoId++);
            banco.add(produto);
        } else {
            int idx = indexPorId(produto.getId());
            if (idx >= 0) {
                banco.set(idx, produto);
            } else {
                banco.add(produto);
            }
        }
        JsonMini.salvarProdutos(arquivo, banco);
        return produto;
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(banco);
    }

    @Override
    public Produto buscarPorId(long id) {
        for (Produto p : banco) {
            if (p.getId() != null && p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean remover(long id) {
        int idx = indexPorId(id);
        if (idx < 0) return false;
        banco.remove(idx);
        JsonMini.salvarProdutos(arquivo, banco);
        return true;
    }

    @Override
    public boolean existePorTipo(long tipoId) {
        for (Produto p : banco) {
            if (p.getTipoProdutoId() == tipoId) {
                return true;
            }
        }
        return false;
    }

    private int indexPorId(long id) {
        for (int i = 0; i < banco.size(); i++) {
            Produto p = banco.get(i);
            if (p.getId() != null && p.getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
