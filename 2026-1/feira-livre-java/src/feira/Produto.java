/**
 * Produto
 * - Conceito de Encapsulamento: estado (nome, preço) é privado e
 *   acessado/modificado via métodos controlados (get/set), com validação.
 * - SRP (Responsabilidade Única): esta classe cuida apenas de dados e regras
 *   do produto (ex.: preço não-negativo).
 * - Abstração: quem usa o produto interage pela API sem conhecer detalhes
 *   internos de validação.
 */
package feira;

public class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        setNome(nome);
        setPreco(preco);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        // Encapsulamento: valida estado antes de aceitar alteração
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto inválido");
        }
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        // Regra de negócio encapsulada: preço não pode ser negativo
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        this.preco = preco;
    }
}
