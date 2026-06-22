package feira.gabarito;

/**
 * Classe de execução do gabarito.
 *
 * Aqui é feita a composição das dependências (injeção manual) e a execução
 * do fluxo completo de pedido usando a arquitetura refatorada.
 */
import feira.gabarito.cupom.ImpressoraCupom;
import feira.gabarito.cupom.ImpressoraTermica;
import feira.gabarito.desconto.CalculadoraDesconto;
import feira.gabarito.desconto.DescontoClienteFiel;
import feira.gabarito.desconto.DescontoDomingo;
import feira.gabarito.desconto.DescontoQueimaEstoque;
import feira.gabarito.desconto.SemDesconto;
import feira.gabarito.domain.Pedido;
import feira.gabarito.domain.Produto;
import feira.gabarito.entrega.CalculadoraPrazoEntrega;
import feira.gabarito.entrega.EntregaExpressa;
import feira.gabarito.entrega.EntregaNormal;
import feira.gabarito.notificacao.NotificadorPedido;
import feira.gabarito.notificacao.NotificadorWhatsApp;
import feira.gabarito.pagamento.PagamentoBoleto;
import feira.gabarito.pagamento.PagamentoCartao;
import feira.gabarito.pagamento.PagamentoPix;
import feira.gabarito.pagamento.ServicoPagamento;
import feira.gabarito.relatorio.ExportadorCsvPedido;
import feira.gabarito.relatorio.ExportadorRelatorioPedido;
import feira.gabarito.repository.PedidoRepository;
import feira.gabarito.repository.PedidoRepositoryMemoria;
import feira.gabarito.service.FinalizadorPedidoService;
import feira.gabarito.service.PedidoFinalizado;
import java.util.Arrays;

public class AppMain {
    public static void main(String[] args) {
        PedidoRepository repository = new PedidoRepositoryMemoria();

        CalculadoraDesconto calculadoraDesconto = new CalculadoraDesconto(Arrays.asList(
                new SemDesconto(),
                new DescontoClienteFiel(),
                new DescontoQueimaEstoque(),
                new DescontoDomingo()));

        ServicoPagamento servicoPagamento = new ServicoPagamento(Arrays.asList(
                new PagamentoPix(),
                new PagamentoCartao(),
                new PagamentoBoleto()));

        ImpressoraCupom impressora = new ImpressoraTermica();
        NotificadorPedido notificador = new NotificadorWhatsApp();
        ExportadorRelatorioPedido exportador = new ExportadorCsvPedido();

        FinalizadorPedidoService finalizador = new FinalizadorPedidoService(
                repository,
                calculadoraDesconto,
                servicoPagamento,
                impressora,
                notificador,
                exportador);

        Pedido pedido = new Pedido("Maria da Feira");
        pedido.adicionarItem(new Produto("Tomate", 8.0), 2);
        pedido.adicionarItem(new Produto("Cenoura", 6.5), 3);

        PedidoFinalizado resultado = finalizador.finalizar(
                pedido,
                "CLIENTE_FIEL",
                "PIX",
                "85999990000");

        System.out.println("Total bruto: R$ " + resultado.getTotalBruto());
        System.out.println("Total líquido: R$ " + resultado.getTotalLiquido());
        System.out.println("CSV:\n" + resultado.getRelatorioCsv());

        CalculadoraPrazoEntrega entregaNormal = new EntregaNormal();
        CalculadoraPrazoEntrega entregaExpressa = new EntregaExpressa();
        System.out.println("Prazo normal (30km): " + entregaNormal.calcularPrazoDias(30) + " dia(s)");
        System.out.println("Prazo expresso (30km): " + entregaExpressa.calcularPrazoDias(30) + " dia(s)");
    }
}
