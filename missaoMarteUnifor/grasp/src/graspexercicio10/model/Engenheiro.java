package graspexercicio10.model;

public class Engenheiro extends Passageiro {
    public Engenheiro(String nome, int x, int y) {
        super(nome, "Engenheiro", x, y);
    }

    @Override public int  getPontuacao() { return 15; }
    @Override public char getSimbolo()   { return 'E'; }
}
