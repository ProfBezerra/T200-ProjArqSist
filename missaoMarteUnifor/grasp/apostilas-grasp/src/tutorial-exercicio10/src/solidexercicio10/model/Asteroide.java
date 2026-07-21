package solidexercicio10.model;

public class Asteroide extends EntidadeMapa {
    public Asteroide(int x, int y) {
        super(x, y);
    }

    @Override
    public String getSimbolo() {
        return "A";
    }
}
