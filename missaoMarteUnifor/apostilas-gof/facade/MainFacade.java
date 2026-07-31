// =============================================================================
// Facade — Feira Livre
// Compile: javac MainFacade.java
// Run:     java MainFacade
// =============================================================================

import java.util.ArrayList;
import java.util.List;

// ── dominio ───────────────────────────────────────────────────────────────────

class ItemPedido {
    final String nome;
    final double preco;
    final int    quantidade;

    ItemPedido(String nome, double preco, int quantidade) {
        this.nome       = nome;
        this.preco      = preco;
        this.quantidade = quantidade;
    }
}

class Pedido {
    private final String          id;
    private final List<ItemPedido> itens = new ArrayList<>();

    Pedido(String id) { this.id = id; }

    void   adicionarItem(ItemPedido item) { itens.add(item); }
    String getId()    { return id; }
    double calcularTotal() {
        return itens.stream().mapToDouble(i -> i.preco * i.quantidade).sum();
    }
}

// ── objeto de resultado (exercicio 2) ────────────────────────────────────────

class ResultadoFechamento {
    final boolean sucesso;
    final String  mensagem;

    ResultadoFechamento(boolean sucesso, String mensagem) {
        this.sucesso  = sucesso;
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return (sucesso ? "OK" : "FALHA") + ": " + mensagem;
    }
}

// ── subsistemas ───────────────────────────────────────────────────────────────

class EstoqueService {
    void validarDisponibilidade(Pedido p) {
        System.out.println("  [ESTOQUE] Validando disponibilidade — pedido " + p.getId());
    }
    void reservarItens(Pedido p) {
        System.out.println("  [ESTOQUE] Itens reservados — pedido " + p.getId());
    }
    void desfazerReserva(Pedido p) {
        System.out.println("  [ESTOQUE] Reserva desfeita — pedido " + p.getId());
    }
}

class PagamentoService {
    /** Recusa valores acima de R$1000 */
    boolean cobrar(String pedidoId, double valor) {
        boolean ok = valor <= 1000.0;
        System.out.printf("  [PAGAMENTO] %s R$%.2f -> %s%n",
            pedidoId, valor, ok ? "APROVADO" : "RECUSADO");
        return ok;
    }
}

class NotificacaoService {
    void enviarConfirmacao(Pedido p) {
        System.out.println("  [NOTIFICACAO] Email de confirmacao enviado — pedido " + p.getId());
    }
}

class AuditoriaService {
    void registrar(String pedidoId, boolean sucesso, double total) {
        System.out.printf("  [AUDITORIA] pedido=%s sucesso=%b total=R$%.2f%n",
            pedidoId, sucesso, total);
    }
}

// ── facade ────────────────────────────────────────────────────────────────────

class FechamentoPedidoFacade {
    private final EstoqueService     estoqueService;
    private final PagamentoService   pagamentoService;
    private final NotificacaoService notificacaoService;
    private final AuditoriaService   auditoriaService;

    FechamentoPedidoFacade(
            EstoqueService e, PagamentoService p,
            NotificacaoService n, AuditoriaService a) {
        this.estoqueService     = e;
        this.pagamentoService   = p;
        this.notificacaoService = n;
        this.auditoriaService   = a;
    }

    ResultadoFechamento fechar(Pedido pedido) {
        estoqueService.validarDisponibilidade(pedido);
        double total = pedido.calcularTotal();
        System.out.printf("  [FACADE] Total calculado: R$%.2f%n", total);

        estoqueService.reservarItens(pedido);
        boolean pago = pagamentoService.cobrar(pedido.getId(), total);

        if (!pago) {
            estoqueService.desfazerReserva(pedido);
            auditoriaService.registrar(pedido.getId(), false, total);
            return new ResultadoFechamento(false, "Pagamento nao aprovado");
        }

        notificacaoService.enviarConfirmacao(pedido);
        auditoriaService.registrar(pedido.getId(), true, total);
        return new ResultadoFechamento(true, "Pedido fechado com sucesso");
    }
}

// ── programa principal ────────────────────────────────────────────────────────

public class MainFacade {

    static FechamentoPedidoFacade criarFacade() {
        return new FechamentoPedidoFacade(
            new EstoqueService(),
            new PagamentoService(),
            new NotificacaoService(),
            new AuditoriaService()
        );
    }

    public static void main(String[] args) {
        FechamentoPedidoFacade facade = criarFacade();

        System.out.println("=== Pedido aprovado ===");
        Pedido p1 = new Pedido("PED-001");
        p1.adicionarItem(new ItemPedido("Tomate",  4.50, 3));
        p1.adicionarItem(new ItemPedido("Batata",  3.00, 5));
        p1.adicionarItem(new ItemPedido("Cebola",  2.80, 2));
        System.out.println("Resultado: " + facade.fechar(p1));

        System.out.println("\n=== Pedido recusado (valor alto) ===");
        Pedido p2 = new Pedido("PED-002");
        p2.adicionarItem(new ItemPedido("Cesta Premium", 1200.00, 1));
        System.out.println("Resultado: " + facade.fechar(p2));
    }
}
