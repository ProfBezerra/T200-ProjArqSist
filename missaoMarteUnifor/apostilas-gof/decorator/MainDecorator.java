// =============================================================================
// Decorator — Feira Livre
// Compile: javac MainDecorator.java
// Run:     java MainDecorator
// =============================================================================

// ── componente base ───────────────────────────────────────────────────────────

interface CalculadorValorPedido {
    double  calcular(double valorBase);
    String  descricao();
}

class CalculadorBase implements CalculadorValorPedido {
    @Override public double calcular(double v) { return v; }
    @Override public String descricao()        { return "Valor base"; }
}

// ── decorator abstrato ────────────────────────────────────────────────────────

abstract class CalculadorDecorator implements CalculadorValorPedido {
    protected final CalculadorValorPedido interno;

    protected CalculadorDecorator(CalculadorValorPedido interno) {
        this.interno = interno;
    }
}

// ── decoradores concretos ─────────────────────────────────────────────────────

class ComTaxaEmbalagem extends CalculadorDecorator {
    private static final double TAXA = 2.50;

    ComTaxaEmbalagem(CalculadorValorPedido interno) { super(interno); }

    @Override
    public double calcular(double v) { return interno.calcular(v) + TAXA; }

    @Override
    public String descricao() {
        return interno.descricao() + " + embalagem(R$2,50)";
    }
}

class ComDesconto extends CalculadorDecorator {
    private final double percentual;

    ComDesconto(CalculadorValorPedido interno, double percentual) {
        super(interno);
        this.percentual = percentual;
    }

    @Override
    public double calcular(double v) {
        return interno.calcular(v) * (1.0 - percentual / 100.0);
    }

    @Override
    public String descricao() {
        return interno.descricao() + " - desconto(" + (int) percentual + "%)";
    }
}

class ComTaxaEntrega extends CalculadorDecorator {
    private final double taxa;
    private final String descricaoEntrega;

    ComTaxaEntrega(CalculadorValorPedido interno, double taxa, String descricaoEntrega) {
        super(interno);
        this.taxa              = taxa;
        this.descricaoEntrega  = descricaoEntrega;
    }

    @Override
    public double calcular(double v) { return interno.calcular(v) + taxa; }

    @Override
    public String descricao() {
        return interno.descricao()
             + " + frete-" + descricaoEntrega + "(R$" + String.format("%.2f", taxa) + ")";
    }
}

// ── utilitarios de exibicao ───────────────────────────────────────────────────

class Exibidor {
    static void mostrar(String titulo, CalculadorValorPedido calc, double base) {
        System.out.printf("%-50s base=R$%6.2f  total=R$%6.2f%n",
            titulo, base, calc.calcular(base));
        System.out.printf("  Composicao: %s%n%n", calc.descricao());
    }
}

// ── programa principal ────────────────────────────────────────────────────────

public class MainDecorator {
    public static void main(String[] args) {
        double base = 80.00;

        Exibidor.mostrar("Sem adicional",
            new CalculadorBase(), base);

        Exibidor.mostrar("Com embalagem",
            new ComTaxaEmbalagem(
                new CalculadorBase()), base);

        Exibidor.mostrar("Desconto 10% + embalagem",
            new ComDesconto(
                new ComTaxaEmbalagem(
                    new CalculadorBase()), 10), base);

        Exibidor.mostrar("Desconto 15%",
            new ComDesconto(
                new CalculadorBase(), 15), base);

        Exibidor.mostrar("Embalagem + desconto 10% + frete vizinho",
            new ComTaxaEntrega(
                new ComDesconto(
                    new ComTaxaEmbalagem(
                        new CalculadorBase()), 10),
                5.00, "vizinho"), base);

        Exibidor.mostrar("Embalagem + desconto 10% + frete distante",
            new ComTaxaEntrega(
                new ComDesconto(
                    new ComTaxaEmbalagem(
                        new CalculadorBase()), 10),
                18.00, "distante"), base);

        // Demonstracao: ordem importa
        System.out.println("=== A ordem dos decoradores importa ===");
        double b = 100.0;
        CalculadorValorPedido embalagePrimeiro = new ComDesconto(
            new ComTaxaEmbalagem(new CalculadorBase()), 10);
        CalculadorValorPedido descontoPrimeiro = new ComTaxaEmbalagem(
            new ComDesconto(new CalculadorBase(), 10));

        System.out.printf("Embalagem(+2,50) DEPOIS desconto 10%%  => R$ %.2f  (%s)%n",
            embalagePrimeiro.calcular(b), embalagePrimeiro.descricao());
        System.out.printf("Embalagem(+2,50) ANTES  desconto 10%%  => R$ %.2f  (%s)%n",
            descontoPrimeiro.calcular(b), descontoPrimeiro.descricao());
    }
}
