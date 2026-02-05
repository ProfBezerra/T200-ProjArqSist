package feira;

/**
 * ReferenciasDemo
 * Demonstra na prática:
 * - Identidade (==) vs igualdade (equals)
 * - Duas variáveis com a mesma referência apontam para o mesmo objeto
 * - Passagem de parâmetro: Java passa o valor da referência (pass-by-value)
 */
public class ReferenciasDemo {

    public static void main(String[] args) {
        System.out.println("=== Demo de Referências (Stack vs Heap) ===\n");

        Produto a = new Produto("Banana", 5.0);
        Produto b = a; // b recebe a MESMA referência de a
        Produto c = new Produto("Banana", 5.0); // novo objeto, conteúdo semelhante

        System.out.println("a == b? " + (a == b)); // true (mesmo objeto)
        System.out.println("a == c? " + (a == c)); // false (objetos diferentes)
        System.out.println("a.equals(c)? " + a.equals(c)); // false (equals não sobrescrito)

        // Alterando via b reflete em a (mesmo objeto)
        b.setPreco(6.0);
        System.out.printf("Preco de a após alterar via b: R$ %.2f\n", a.getPreco());

        // Passagem de parâmetro: valor passado é a referência
        ajustarPreco(a);
        System.out.printf("Preco de a após ajustarPreco(a): R$ %.2f\n", a.getPreco());

        System.out.println("\nObservacoes:") ;
        System.out.println("- Variaveis a e b referenciam o MESMO objeto (identidade).");
        System.out.println("- c referencia outro objeto, ainda que o conteudo seja semelhante.");
        System.out.println("- Em Java, o valor passado ao metodo e a referencia; reatribuir p dentro do metodo nao muda a/b.");
    }

    // p recebe o valor da referencia de 'a'; alterar estado de p afeta o mesmo objeto de a
    private static void ajustarPreco(Produto p) {
        // altera estado do objeto referenciado
        p.setPreco(5.5);

        // reatribuicao local: passa a apontar para um NOVO objeto (nao afeta a/b no chamador)
        p = new Produto("Uva", 8.0);
        // esta alteracao fica somente no escopo local de p
    }
}
