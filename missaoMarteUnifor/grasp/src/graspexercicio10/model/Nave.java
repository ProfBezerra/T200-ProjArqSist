package graspexercicio10.model;

import java.util.ArrayList;
import java.util.List;

public class Nave {
    private final String id;
    private int x;
    private int y;
    private final int capacidade;
    private int vidas;
    private final List<Passageiro> passageiros = new ArrayList<>();

    public Nave(String id, int capacidade) {
        this.id        = id;
        this.capacidade = capacidade;
        this.vidas     = 3;
        this.x         = 0;
        this.y         = 0;
    }

    public String           getId()          { return id; }
    public int              getX()           { return x; }
    public int              getY()           { return y; }
    public int              getCapacidade()  { return capacidade; }
    public int              getVidas()       { return vidas; }
    public List<Passageiro> getPassageiros() { return passageiros; }

    public void moverComLimites(char direcao, int minX, int maxX, int minY, int maxY) {
        switch (direcao) {
            case 'w': if (y > minY) y--; break;
            case 's': if (y < maxY) y++; break;
            case 'a': if (x > minX) x--; break;
            case 'd': if (x < maxX) x++; break;
        }
    }

    public boolean embarcar(Passageiro p) {
        if (passageiros.size() < capacidade) {
            passageiros.add(p);
            return true;
        }
        return false;
    }

    public void perderVida() {
        if (vidas > 0) vidas--;
    }
}
