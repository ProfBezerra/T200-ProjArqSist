package solidexercicio10.model;

public class Inimigo extends EntidadeMapa implements Movel {
    public Inimigo(int x, int y) {
        super(x, y);
    }

    @Override
    public void mover(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public String getSimbolo() {
        return "X";
    }
}
