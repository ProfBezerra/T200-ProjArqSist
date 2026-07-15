package exercicio7;

import java.util.Random;

public class Inimigo {
    private int x;
    private int y;

    public Inimigo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean colideCom(Nave n) {
        return n.getX() == x && n.getY() == y;
    }

    public void mover(Random random, int minX, int maxX, int minY, int maxY) {
        int direcao = random.nextInt(4);
        switch (direcao) {
            case 0:
                if (x < maxX) x++;
                break;
            case 1:
                if (x > minX) x--;
                break;
            case 2:
                if (y < maxY) y++;
                break;
            case 3:
                if (y > minY) y--;
                break;
        }
    }
}
