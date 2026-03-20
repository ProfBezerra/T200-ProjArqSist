package feira.graspcrud.repository.json;

import feira.graspcrud.domain.TipoProduto;
import feira.graspcrud.repository.TipoProdutoRepository;
import feira.graspcrud.util.JsonMini;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio JSON para tipos de produto.
 */
public class TipoProdutoRepositoryJson implements TipoProdutoRepository {
    private final Path arquivo;
    private final List<TipoProduto> banco;
    private long proximoId;

    /**
     * Cria repositrio carregando dados do arquivo JSON.
     *
     * @param arquivo caminho do arquivo
     */
    public TipoProdutoRepositoryJson(Path arquivo) {
        this.arquivo = arquivo;
        this.banco = JsonMini.carregarTipos(arquivo);
        this.proximoId = banco.stream().mapToLong(t -> t.getId() == null ? 0 : t.getId()).max().orElse(0) + 1;
    }

    @Override
    public TipoProduto salvar(TipoProduto tipo) {
        if (tipo.getId() == null) {
            tipo.setId(proximoId++);
            banco.add(tipo);
        } else {
            int idx = indexPorId(tipo.getId());
            if (idx >= 0) {
                banco.set(idx, tipo);
            } else {
                banco.add(tipo);
            }
        }
        JsonMini.salvarTipos(arquivo, banco);
        return tipo;
    }

    @Override
    public List<TipoProduto> listarTodos() {
        return new ArrayList<>(banco);
    }

    @Override
    public TipoProduto buscarPorId(long id) {
        for (TipoProduto t : banco) {
            if (t.getId() != null && t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    @Override
    public boolean existeNome(String nome, Long ignorarId) {
        if (nome == null) return false;
        String alvo = nome.trim().toLowerCase();
        for (TipoProduto t : banco) {
            if (t.getNome() != null && t.getNome().trim().toLowerCase().equals(alvo)) {
                if (ignorarId == null || !ignorarId.equals(t.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean remover(long id) {
        int idx = indexPorId(id);
        if (idx < 0) return false;
        banco.remove(idx);
        JsonMini.salvarTipos(arquivo, banco);
        return true;
    }

    private int indexPorId(long id) {
        for (int i = 0; i < banco.size(); i++) {
            TipoProduto t = banco.get(i);
            if (t.getId() != null && t.getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
