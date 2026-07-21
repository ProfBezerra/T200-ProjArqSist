package solidexercicio10.model;

/**
 * Abstração comum para todos os tipos de passageiros.
 *
 * <p>Essa estrutura favorece o princípio aberto/fechado (OCP) e a substituição de
 * Liskov (LSP), pois novos tipos de passageiros podem ser adicionados sem mudar o
 * contrato básico usado pelo sistema.</p>
 */
public abstract class Passageiro extends EntidadeMapa {
    private final String nome;
    private final String tipo;

    protected Passageiro(String nome, String tipo, int x, int y) {
        super(x, y);
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public abstract int getPontuacao();
}
