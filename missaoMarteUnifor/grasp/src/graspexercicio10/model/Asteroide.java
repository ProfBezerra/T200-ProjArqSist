package graspexercicio10.model;

// GRASP Polymorphism: implementa Perigo — sabe colidir e exibir seu próprio símbolo.
public class Asteroide implements Perigo {
    private final int x;
    private final int y;

    public Asteroide(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override public int     getX()            { return x; }
    @Override public int     getY()            { return y; }
    @Override public boolean colideCom(Nave n) { return n.getX() == x && n.getY() == y; }
    @Override public char    getSimbolo()      { return '#'; }
}
