// =============================================================================
// Factory Method — Feira Livre
// Compile: javac MainFactoryMethod.java
// Run:     java MainFactoryMethod
// =============================================================================

// ── dominio ──────────────────────────────────────────────────────────────────

class Produto {
    private final String nome;
    private final double preco;
    private final String tipo;

    Produto(String nome, double preco, String tipo) {
        this.nome  = nome;
        this.preco = preco;
        this.tipo  = tipo;
    }

    String getNome()  { return nome; }
    double getPreco() { return preco; }
    String getTipo()  { return tipo; }

    @Override
    public String toString() {
        return "[" + tipo + "] " + nome + " - R$ " + String.format("%.2f", preco);
    }
}

// ── interface da fabrica ──────────────────────────────────────────────────────

interface ProdutoFactory {
    Produto criar(String nome, double preco);
}

// ── fabricas concretas ────────────────────────────────────────────────────────

class HortifrutiFactory implements ProdutoFactory {
    @Override
    public Produto criar(String nome, double preco) {
        if (preco <= 0) throw new IllegalArgumentException("Preco invalido para hortifruti");
        return new Produto(nome, preco, "HORTIFRUTI");
    }
}

class GraosFactory implements ProdutoFactory {
    @Override
    public Produto criar(String nome, double preco) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome obrigatorio para graos");
        return new Produto(nome, preco, "GRAOS");
    }
}

class LaticinioFactory implements ProdutoFactory {
    @Override
    public Produto criar(String nome, double preco) {
        if (preco <= 0) throw new IllegalArgumentException("Preco invalido para laticinios");
        return new Produto(nome, preco, "LATICIONIO");
    }
}

// ── servico que usa a fabrica ─────────────────────────────────────────────────

class CadastroProdutoService {
    private final ProdutoFactory factory;

    CadastroProdutoService(ProdutoFactory factory) {
        this.factory = factory;
    }

    Produto cadastrar(String nome, double preco) {
        Produto p = factory.criar(nome, preco);
        System.out.println("Cadastrado: " + p);
        return p;
    }
}

// ── programa principal ────────────────────────────────────────────────────────

public class MainFactoryMethod {
    public static void main(String[] args) {
        System.out.println("=== Hortifruti ===");
        CadastroProdutoService svcH = new CadastroProdutoService(new HortifrutiFactory());
        svcH.cadastrar("Tomate",  4.50);
        svcH.cadastrar("Alface",  2.00);
        svcH.cadastrar("Cenoura", 3.20);

        System.out.println("\n=== Graos ===");
        CadastroProdutoService svcG = new CadastroProdutoService(new GraosFactory());
        svcG.cadastrar("Feijao Carioca", 8.90);
        svcG.cadastrar("Arroz Integral", 6.50);

        System.out.println("\n=== Laticinios ===");
        CadastroProdutoService svcL = new CadastroProdutoService(new LaticinioFactory());
        svcL.cadastrar("Queijo Minas", 22.00);
        svcL.cadastrar("Manteiga",     12.50);

        System.out.println("\n=== Erro esperado: preco invalido ===");
        try {
            svcH.cadastrar("Produto Ruim", -1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro capturado: " + e.getMessage());
        }
    }
}
