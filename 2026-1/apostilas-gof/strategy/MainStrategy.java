// =============================================================================
// Strategy — Feira Livre
// Compile: javac MainStrategy.java
// Run:     java MainStrategy
// =============================================================================

import java.util.HashMap;
import java.util.Map;

// ── interface de estrategia ───────────────────────────────────────────────────

interface RegraDesconto {
    double aplicar(double subtotal, int quantidadeItens);
    String descricao();
}

// ── estrategias concretas ─────────────────────────────────────────────────────

class SemDesconto implements RegraDesconto {
    @Override public double aplicar(double s, int q) { return s; }
    @Override public String descricao() { return "Sem desconto"; }
}

class DescontoClienteFrequente implements RegraDesconto {
    @Override public double aplicar(double s, int q) { return s * 0.95; }
    @Override public String descricao() { return "Cliente frequente (5%)"; }
}

class DescontoPorVolume implements RegraDesconto {
    @Override
    public double aplicar(double s, int q) {
        return q >= 10 ? s * 0.90 : s;
    }
    @Override public String descricao() { return "Volume (10% a partir de 10 itens)"; }
}

class DescontoFeiraFimDeSemana implements RegraDesconto {
    @Override public double aplicar(double s, int q) { return s * 0.85; }
    @Override public String descricao() { return "Feira fim de semana (15%)"; }
}

class DescontoIdoso implements RegraDesconto {
    @Override public double aplicar(double s, int q) { return s * 0.88; }
    @Override public String descricao() { return "Desconto idoso (12%)"; }
}

// ── contexto ──────────────────────────────────────────────────────────────────

class CalculadoraPedido {
    private RegraDesconto regraDesconto;

    CalculadoraPedido(RegraDesconto regraDesconto) {
        this.regraDesconto = regraDesconto;
    }

    void trocarRegra(RegraDesconto novaRegra) { this.regraDesconto = novaRegra; }

    double totalComDesconto(double subtotal, int qtdItens) {
        return regraDesconto.aplicar(subtotal, qtdItens);
    }

    String descricaoRegra() { return regraDesconto.descricao(); }
}

// ── fabrica de estrategias por configuracao (exercicio 2) ────────────────────

class RegraDescontoFactory {
    private static final Map<String, RegraDesconto> REGRAS = new HashMap<>();

    static {
        REGRAS.put("NENHUM",        new SemDesconto());
        REGRAS.put("FREQUENTE",     new DescontoClienteFrequente());
        REGRAS.put("VOLUME",        new DescontoPorVolume());
        REGRAS.put("FIM_SEMANA",    new DescontoFeiraFimDeSemana());
        REGRAS.put("IDOSO",         new DescontoIdoso());
    }

    static RegraDesconto buscar(String chave) {
        RegraDesconto regra = REGRAS.get(chave.toUpperCase());
        if (regra == null) {
            System.out.println("  [AVISO] Regra '" + chave + "' nao encontrada. Usando SemDesconto.");
            return new SemDesconto();
        }
        return regra;
    }
}

// ── programa principal ────────────────────────────────────────────────────────

public class MainStrategy {

    static void calcular(CalculadoraPedido calc, double subtotal, int qtd) {
        System.out.printf("  %-35s | itens=%2d | sub=R$%6.2f | total=R$%6.2f%n",
            calc.descricaoRegra(), qtd, subtotal, calc.totalComDesconto(subtotal, qtd));
    }

    public static void main(String[] args) {
        double subtotal = 120.00;

        System.out.println("=== Troca de estrategia em tempo de execucao ===");
        CalculadoraPedido calc = new CalculadoraPedido(new SemDesconto());
        calcular(calc, subtotal, 5);

        calc.trocarRegra(new DescontoClienteFrequente());
        calcular(calc, subtotal, 5);

        calc.trocarRegra(new DescontoPorVolume());
        calcular(calc, subtotal, 5);   // abaixo do limite
        calcular(calc, subtotal, 12);  // acima do limite

        calc.trocarRegra(new DescontoFeiraFimDeSemana());
        calcular(calc, subtotal, 3);

        System.out.println("\n=== Carregando estrategia por configuracao ===");
        String[] configuracoes = {"FREQUENTE", "FIM_SEMANA", "IDOSO", "ATACADO"};
        for (String config : configuracoes) {
            CalculadoraPedido c = new CalculadoraPedido(RegraDescontoFactory.buscar(config));
            calcular(c, subtotal, 8);
        }
    }
}
