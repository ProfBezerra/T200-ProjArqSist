package solidexercicio10.model;

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
