package graspexercicio10.model;

import java.util.Random;

// GRASP Polymorphism: implementa Perigo — sabe mover, colidir e exibir seu símbolo.
public class Inimigo implements Perigo {
    private int x;
    private int y;

    public Inimigo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override public int     getX()            { return x; }
    @Override public int     getY()            { return y; }
    @Override public boolean colideCom(Nave n) { return n.getX() == x && n.getY() == y; }
    @Override public char    getSimbolo()      { return 'X'; }

    public void mover(Random random, int minX, int maxX, int minY, int maxY) {
        switch (random.nextInt(4)) {
            case 0: if (x < maxX) x++; break;
            case 1: if (x > minX) x--; break;
            case 2: if (y < maxY) y++; break;
            case 3: if (y > minY) y--; break;
        }
    }
}
