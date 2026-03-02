package feira.problemasolid;

/**
 * Classe de entrada da atividade.
 *
 * Este fluxo executa os cenários que evidenciam os problemas de design
 * no pacote `problemasolid` para que os alunos refatorem em seguida.
 */
public class AtividadeMain {
    public static void main(String[] args) {
        ProcessadorPedidoRuim processador = new ProcessadorPedidoRuim();

        Pedido pedido = processador.criarPedido("Maria da Feira");
        processador.adicionarItem(pedido, new Produto("Tomate", 8.0), 2);
        processador.adicionarItem(pedido, new Produto("Cenoura", 6.5), 3);

        try {
            double total = processador.finalizarPedido(pedido, "CLIENTE_FIEL", "PIX", "85999990000");
            System.out.println("Pedido finalizado com total = R$ " + total);
        } catch (Exception e) {
            System.out.println("Erro ao finalizar pedido: " + e.getMessage());
        }

        Entrega entregaNormal = new Entrega();
        Entrega entregaExpressa = new EntregaExpressa();

        System.out.println("Prazo normal (30km): " + processador.prazoEntrega(entregaNormal, 30) + " dia(s)");
        try {
            System.out.println("Prazo expresso (30km): " + processador.prazoEntrega(entregaExpressa, 30) + " dia(s)");
        } catch (Exception e) {
            System.out.println("Falha ao usar subtipo de Entrega: " + e.getMessage());
        }

        try {
            processador.rotinaAdministrativa();
        } catch (Exception e) {
            System.out.println("Falha em rotina administrativa: " + e.getMessage());
        }
    }
}
