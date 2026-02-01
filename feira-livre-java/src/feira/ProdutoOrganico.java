/**
 * ProdutoOrganico
 * - Herança: especializa Produto (relação "é um").
 * - Polimorfismo: sobrescreve getPreco() para aplicar comportamento diferente
 *   (desconto) mantendo o contrato da superclasse.
 */
package feira;

public class ProdutoOrganico extends Produto {
    public ProdutoOrganico(String nome, double preco) {
        super(nome, preco);
    }

    @Override
    public double getPreco() {
        // Polimorfismo em ação: orgânicos têm 10% de desconto padrão
        return super.getPreco() * 0.9;
    }
}
