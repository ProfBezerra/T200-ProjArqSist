package feira.graspcrud;

import feira.graspcrud.controller.ProdutoController;
import feira.graspcrud.repository.ProdutoRepository;
import feira.graspcrud.repository.TipoProdutoRepository;
import feira.graspcrud.repository.json.ProdutoRepositoryJson;
import feira.graspcrud.repository.json.TipoProdutoRepositoryJson;
import feira.graspcrud.service.ProdutoService;
import feira.graspcrud.service.TipoProdutoService;

import java.nio.file.Path;
import java.util.Scanner;

/**
 * Ponto de entrada da aplicacao sem Maven.
 */
public class Main {
    private static final Path TIPOS_FILE = Path.of("data", "tipos-produto.json");
    private static final Path PRODUTOS_FILE = Path.of("data", "produtos.json");

    /**
     * Inicializa as dependencias e executa o menu textual.
     *
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {
        TipoProdutoRepository tipoRepository = new TipoProdutoRepositoryJson(TIPOS_FILE);
        ProdutoRepository produtoRepository = new ProdutoRepositoryJson(PRODUTOS_FILE);

        TipoProdutoService tipoService = new TipoProdutoService(tipoRepository, produtoRepository);
        ProdutoService produtoService = new ProdutoService(produtoRepository, tipoRepository);

        try (Scanner scanner = new Scanner(System.in)) {
            ProdutoController controller = new ProdutoController(produtoService, tipoService, scanner);
            controller.iniciarMenu();
        }

        System.out.println("Aplicacao finalizada.");
    }
}
