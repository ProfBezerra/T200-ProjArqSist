package solidexercicio10.model;

public abstract class EntidadeMapa implements Posicionavel {
    protected int x;
    protected int y;

    protected EntidadeMapa(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract String getSimbolo();
}
