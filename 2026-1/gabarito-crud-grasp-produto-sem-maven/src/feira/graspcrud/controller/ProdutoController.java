package feira.graspcrud.controller;

import feira.graspcrud.domain.Produto;
import feira.graspcrud.domain.TipoProduto;
import feira.graspcrud.dto.ProdutoRequest;
import feira.graspcrud.dto.TipoProdutoRequest;
import feira.graspcrud.exception.RegraNegocioException;
import feira.graspcrud.service.ProdutoService;
import feira.graspcrud.service.TipoProdutoService;

import java.util.List;
import java.util.Scanner;

/**
 * Controller de entrada textual para orquestrar casos de uso.
 */
public class ProdutoController {
    private final ProdutoService produtoService;
    private final TipoProdutoService tipoService;
    private final Scanner scanner;

    /**
     * Cria o controller com as dependencias necessarias.
     *
     * @param produtoService servico de produto
     * @param tipoService servico de tipo de produto
     * @param scanner leitor de terminal
     */
    public ProdutoController(ProdutoService produtoService, TipoProdutoService tipoService, Scanner scanner) {
        this.produtoService = produtoService;
        this.tipoService = tipoService;
        this.scanner = scanner;
    }

    /**
     * Inicia o loop do menu textual.
     */
    public void iniciarMenu() {
        boolean executando = true;
        while (executando) {
            mostrarMenu();
            String opcao = scanner.nextLine().trim();
            try {
                switch (opcao) {
                    case "1":
                        cadastrarTipo();
                        break;
                    case "2":
                        listarTipos();
                        break;
                    case "3":
                        cadastrarProduto();
                        break;
                    case "4":
                        listarProdutos();
                        break;
                    case "5":
                        buscarProduto();
                        break;
                    case "6":
                        atualizarProduto();
                        break;
                    case "7":
                        excluirProduto();
                        break;
                    case "8":
                        excluirTipo();
                        break;
                    case "9":
                        executando = false;
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                        break;
                }
            } catch (RegraNegocioException e) {
                System.out.println("Regra de negocio: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void mostrarMenu() {
        System.out.println("=============================");
        System.out.println(" CRUD Produto / TipoProduto ");
        System.out.println("=============================");
        System.out.println("1. Cadastrar tipo de produto");
        System.out.println("2. Listar tipos de produto");
        System.out.println("3. Cadastrar produto");
        System.out.println("4. Listar produtos");
        System.out.println("5. Buscar produto por id");
        System.out.println("6. Atualizar produto");
        System.out.println("7. Excluir produto");
        System.out.println("8. Excluir tipo de produto");
        System.out.println("9. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private void cadastrarTipo() {
        System.out.print("Nome do tipo: ");
        String nome = scanner.nextLine();
        System.out.print("Descricao do tipo: ");
        String descricao = scanner.nextLine();

        TipoProduto tipo = tipoService.criar(new TipoProdutoRequest(nome, descricao));
        System.out.println("Tipo cadastrado com ID: " + tipo.getId());
    }

    private void listarTipos() {
        List<TipoProduto> tipos = tipoService.listarTodos();
        if (tipos.isEmpty()) {
            System.out.println("Nenhum tipo cadastrado.");
            return;
        }
        for (TipoProduto t : tipos) {
            System.out.printf("- ID: %d | Nome: %s | Descricao: %s%n", t.getId(), t.getNome(), t.getDescricao());
        }
    }

    private void cadastrarProduto() {
        if (tipoService.listarTodos().isEmpty()) {
            throw new RegraNegocioException("Cadastre ao menos um tipo de produto antes.");
        }

        listarTipos();

        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Preco: ");
        double preco = Double.parseDouble(scanner.nextLine());
        System.out.print("Estoque: ");
        int estoque = Integer.parseInt(scanner.nextLine());
        System.out.print("ID do tipo: ");
        long tipoId = Long.parseLong(scanner.nextLine());

        Produto produto = produtoService.criar(new ProdutoRequest(nome, preco, estoque, tipoId));
        System.out.println("Produto cadastrado com ID: " + produto.getId());
    }

    private void listarProdutos() {
        List<Produto> produtos = produtoService.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (Produto p : produtos) {
            TipoProduto t = tipoService.buscarPorId(p.getTipoProdutoId());
            System.out.printf("- ID: %d | Nome: %s | Preco: %.2f | Estoque: %d | Tipo: %s%n",
                p.getId(), p.getNome(), p.getPreco(), p.getEstoque(), t.getNome());
        }
    }

    private void buscarProduto() {
        System.out.print("ID do produto: ");
        long id = Long.parseLong(scanner.nextLine());
        Produto p = produtoService.buscarPorId(id);
        TipoProduto t = tipoService.buscarPorId(p.getTipoProdutoId());

        System.out.printf("Produto: ID=%d | Nome=%s | Preco=%.2f | Estoque=%d | Tipo=%s%n",
            p.getId(), p.getNome(), p.getPreco(), p.getEstoque(), t.getNome());
    }

    private void atualizarProduto() {
        System.out.print("ID do produto para atualizar: ");
        long id = Long.parseLong(scanner.nextLine());

        listarTipos();

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo preco: ");
        double preco = Double.parseDouble(scanner.nextLine());
        System.out.print("Novo estoque: ");
        int estoque = Integer.parseInt(scanner.nextLine());
        System.out.print("Novo ID do tipo: ");
        long tipoId = Long.parseLong(scanner.nextLine());

        produtoService.atualizar(id, new ProdutoRequest(nome, preco, estoque, tipoId));
        System.out.println("Produto atualizado com sucesso.");
    }

    private void excluirProduto() {
        System.out.print("ID do produto para excluir: ");
        long id = Long.parseLong(scanner.nextLine());

        produtoService.remover(id);
        System.out.println("Produto excluido com sucesso.");
    }

    private void excluirTipo() {
        System.out.print("ID do tipo para excluir: ");
        long id = Long.parseLong(scanner.nextLine());

        tipoService.remover(id);
        System.out.println("Tipo de produto excluido com sucesso.");
    }
}
