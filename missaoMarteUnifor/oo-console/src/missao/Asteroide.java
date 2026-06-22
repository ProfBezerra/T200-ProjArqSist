package missao;

public class Asteroide {
    private int x;
    private int y;

    public Asteroide(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public boolean colideCom(Nave n) {
        return n.getX() == x && n.getY() == y;
    }
}
