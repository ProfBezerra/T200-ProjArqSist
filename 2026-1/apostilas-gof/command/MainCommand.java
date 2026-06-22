// =============================================================================
// Command — Feira Livre
// Compile: javac MainCommand.java
// Run:     java MainCommand
// =============================================================================

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// ── dominio: caixa do pedido ──────────────────────────────────────────────────

class CaixaPedido {
    private double subtotal  = 0.0;
    private double desconto  = 0.0;

    void adicionarItem(String nome, double preco) {
        subtotal += preco;
        System.out.printf("  [CAIXA] + %-20s R$%6.2f  | provisorio=R$%.2f%n",
            nome, preco, totalLiquido());
    }

    void removerItem(String nome, double preco) {
        subtotal -= preco;
        System.out.printf("  [CAIXA] - %-20s R$%6.2f  | provisorio=R$%.2f%n",
            nome, preco, totalLiquido());
    }

    void aplicarDesconto(String descricao, double valor) {
        desconto += valor;
        System.out.printf("  [CAIXA] desconto %-15s R$%6.2f | provisorio=R$%.2f%n",
            descricao, valor, totalLiquido());
    }

    void estornarDesconto(String descricao, double valor) {
        desconto -= valor;
        System.out.printf("  [CAIXA] estorno  %-15s R$%6.2f | provisorio=R$%.2f%n",
            descricao, valor, totalLiquido());
    }

    double totalLiquido() { return subtotal - desconto; }
}

// ── interface do comando ──────────────────────────────────────────────────────

interface Comando {
    void executar();
    void desfazer();
    String descricao();
}

// ── comandos concretos ────────────────────────────────────────────────────────

class AdicionarItemCommand implements Comando {
    private final CaixaPedido caixa;
    private final String nome;
    private final double preco;

    AdicionarItemCommand(CaixaPedido c, String nome, double preco) {
        this.caixa = c; this.nome = nome; this.preco = preco;
    }

    @Override public void executar()     { caixa.adicionarItem(nome, preco); }
    @Override public void desfazer()     { caixa.removerItem(nome, preco); }
    @Override public String descricao()  { return "Adicionar " + nome; }
}

class RemoverItemCommand implements Comando {
    private final CaixaPedido caixa;
    private final String nome;
    private final double preco;

    RemoverItemCommand(CaixaPedido c, String nome, double preco) {
        this.caixa = c; this.nome = nome; this.preco = preco;
    }

    @Override public void executar()    { caixa.removerItem(nome, preco); }
    @Override public void desfazer()    { caixa.adicionarItem(nome, preco); }
    @Override public String descricao() { return "Remover " + nome; }
}

class AplicarDescontoCommand implements Comando {
    private final CaixaPedido caixa;
    private final String descricao;
    private final double valor;

    AplicarDescontoCommand(CaixaPedido c, String descricao, double valor) {
        this.caixa     = c;
        this.descricao = descricao;
        this.valor     = valor;
    }

    @Override public void executar()    { caixa.aplicarDesconto(descricao, valor); }
    @Override public void desfazer()    { caixa.estornarDesconto(descricao, valor); }
    @Override public String descricao() { return "Desconto " + descricao; }
}

// ── historico de comandos com desfazer e refazer ──────────────────────────────

class HistoricoComandos {
    private final Deque<Comando> pilhaDesfazer = new ArrayDeque<>();
    private final Deque<Comando> pilhaRefazer  = new ArrayDeque<>();
    private final List<String>   log           = new ArrayList<>();

    void executar(Comando cmd) {
        cmd.executar();
        pilhaDesfazer.push(cmd);
        pilhaRefazer.clear();
        log.add("EXEC: " + cmd.descricao());
    }

    void desfazer() {
        if (pilhaDesfazer.isEmpty()) {
            System.out.println("  [HISTORICO] nada para desfazer");
            return;
        }
        Comando cmd = pilhaDesfazer.pop();
        System.out.println("  [HISTORICO] Desfazendo: " + cmd.descricao());
        cmd.desfazer();
        pilhaRefazer.push(cmd);
        log.add("UNDO: " + cmd.descricao());
    }

    void refazer() {
        if (pilhaRefazer.isEmpty()) {
            System.out.println("  [HISTORICO] nada para refazer");
            return;
        }
        Comando cmd = pilhaRefazer.pop();
        System.out.println("  [HISTORICO] Refazendo: " + cmd.descricao());
        cmd.executar();
        pilhaDesfazer.push(cmd);
        log.add("REDO: " + cmd.descricao());
    }

    void imprimirLog() {
        System.out.println("  === Log de Operacoes ===");
        log.forEach(e -> System.out.println("  " + e));
    }
}

// ── programa principal ────────────────────────────────────────────────────────

public class MainCommand {
    public static void main(String[] args) {
        CaixaPedido       caixa = new CaixaPedido();
        HistoricoComandos hist  = new HistoricoComandos();

        System.out.println("=== Montando pedido ===");
        hist.executar(new AdicionarItemCommand(caixa, "Tomate",   4.50));
        hist.executar(new AdicionarItemCommand(caixa, "Batata",   3.00));
        hist.executar(new AdicionarItemCommand(caixa, "Cebola",   2.80));
        hist.executar(new AdicionarItemCommand(caixa, "Alface",   2.00));

        System.out.println("\n=== Aplicando desconto ===");
        hist.executar(new AplicarDescontoCommand(caixa, "FEIRA10", 1.23));

        System.out.println("\n=== Removendo item errado ===");
        hist.executar(new RemoverItemCommand(caixa, "Alface", 2.00));

        System.out.println("\n=== Desfazendo remocao ===");
        hist.desfazer();

        System.out.println("\n=== Desfazendo desconto ===");
        hist.desfazer();

        System.out.println("\n=== Refazendo desconto ===");
        hist.refazer();

        System.out.println("\n=== Tentando refazer alem do disponivel ===");
        hist.refazer();

        System.out.printf("%n=== Total final: R$%.2f ===%n", caixa.totalLiquido());

        System.out.println();
        hist.imprimirLog();
    }
}
