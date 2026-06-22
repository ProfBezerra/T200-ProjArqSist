package missao;

public class Passageiro {
    private String nome;
    private String tipo;
    private int x;
    private int y;

    public Passageiro(String nome, String tipo, int x, int y) {
        this.nome = nome;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
    }

    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getX() { return x; }
    public int getY() { return y; }
}
