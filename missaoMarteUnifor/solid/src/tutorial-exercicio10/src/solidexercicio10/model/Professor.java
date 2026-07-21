package solidexercicio10.model;

public class Professor extends Passageiro {
    public Professor(String nome, int x, int y) {
        super(nome, "Professor", x, y);
    }

    @Override
    public int getPontuacao() {
        return 15;
    }

    @Override
    public String getSimbolo() {
        return "P";
    }
}
