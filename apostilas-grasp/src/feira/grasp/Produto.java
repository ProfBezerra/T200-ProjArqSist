package feira.grasp;

/**
 * Produto (entidade de domínio).
 *
 * Padrões GRASP demonstrados:
 * - Information Expert: `Produto` é a fonte de informação sobre nome e preço.
 * - High Cohesion: classe pequena com responsabilidade única (dados do produto).
 */
public class Produto {
    private final String nome;
    private final double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Information Expert: fornece acesso aos seus dados encapsulados
    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
