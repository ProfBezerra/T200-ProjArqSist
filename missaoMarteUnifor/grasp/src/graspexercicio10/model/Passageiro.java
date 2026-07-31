package graspexercicio10.model;

/**
 * GRASP Information Expert: conhece posição e pontuação, e agora também seu símbolo visual.
 * GRASP Polymorphism: getSimbolo() é sobrescrito em cada subclasse — sem instanceof no renderer.
 */
public class Passageiro {
    private final String nome;
    private final String tipo;
    private final int x;
    private final int y;

    public Passageiro(String nome, String tipo, int x, int y) {
        this.nome = nome;
        this.tipo = tipo;
        this.x    = x;
        this.y    = y;
    }

    public String getNome()         { return nome; }
    public String getTipo()         { return tipo; }
    public int    getX()            { return x; }
    public int    getY()            { return y; }
    public int    getPontuacao()    { return 10; }
    public char   getSimbolo()      { return 'P'; }
}
