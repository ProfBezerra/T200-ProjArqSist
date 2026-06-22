/**
 * Main (console)
 * - Camada de interface (texto): coleta entradas e delega ao domínio/serviço.
 * - Baixo acoplamento: não conhece detalhes de cálculo/persistência; usa
 *   PedidoService e as entidades via APIs públicas.
 * - Demonstra polimorfismo: criação de Produto ou ProdutoOrganico altera
 *   comportamento de preço em runtime.
 */
package feira;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Pedido pedido = new Pedido();
        PedidoRepository repository = new PedidoRepositoryMemoria();
        PedidoService service = new PedidoService(repository);

        System.out.println("=== Feira Livre (console) ===");
        boolean adicionando = true;
        while (adicionando) {
            System.out.println("\n1) Adicionar produto  2) Finalizar pedido  3) Listar produtos  0) Sair");
            System.out.print("Escolha: ");
            String escolha = in.nextLine().trim();
            switch (escolha) {
                case "1":
                    try {
                        System.out.print("Nome do produto: ");
                        String nome = in.nextLine().trim();

                        System.out.print("Preço base (ex: 10.50): ");
                        double preco = Double.parseDouble(in.nextLine().trim());

                        System.out.print("Quantidade: ");
                        int quantidade = Integer.parseInt(in.nextLine().trim());

                        System.out.print("É orgânico? (s/n): ");
                        boolean organico = in.nextLine().trim().equalsIgnoreCase("s");

                        Produto produto = organico
                                ? new ProdutoOrganico(nome, preco)
                                : new Produto(nome, preco);

                        pedido.adicionarItem(produto, quantidade);

                        System.out.printf("Adicionado: %s x%d  | Preço unitário aplicado: R$ %.2f\n",
                                produto.getNome(), quantidade, produto.getPreco());
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case "2":
                    try {
                        double total = service.finalizar(pedido);
                        System.out.printf("\nTotal do pedido: R$ %.2f\n", total);
                        System.out.println("Pedido salvo (memória).\n");
                        adicionando = false;
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case "3":
                    // Listar produtos cadastrados no pedido atual (distintos por nome)
                    if (pedido.vazio()) {
                        System.out.println("Nenhum produto cadastrado.");
                        break;
                    }
                    Map<String, Double> precoPorNome = new LinkedHashMap<>();
                    Map<String, Integer> qtdPorNome = new LinkedHashMap<>();
                    for (PedidoItem item : pedido.getItens()) {
                        String nome = item.getProduto().getNome();
                        double preco = item.getProduto().getPreco();
                        precoPorNome.putIfAbsent(nome, preco);
                        qtdPorNome.put(nome, qtdPorNome.getOrDefault(nome, 0) + item.getQuantidade());
                    }
                    System.out.println("\nProdutos cadastrados:");
                    for (Map.Entry<String, Integer> entry : qtdPorNome.entrySet()) {
                        String nome = entry.getKey();
                        int qtd = entry.getValue();
                        double preco = precoPorNome.get(nome);
                        System.out.printf("- %s | Preço unitário: R$ %.2f | Quantidade: %d | Subtotal: R$ %.2f\n",
                                nome, preco, qtd, preco * qtd);
                    }
                    break;
                case "0":
                    adicionando = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        in.close();
        System.out.println("Encerrado.");
    }
}
