package missao;

import java.util.ArrayList;
import java.util.List;

public class Nave {
    private String id;
    private int x;
    private int y;
    private int capacidade;
    private List<Passageiro> passageiros = new ArrayList<>();

    public Nave(String id, int capacidade) {
        this.id = id;
        this.capacidade = capacidade;
        this.x = 0;
        this.y = 0;
    }

    public String getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getCapacidade() { return capacidade; }
    public List<Passageiro> getPassageiros() { return passageiros; }

    public void moveUp() { y--; }
    public void moveDown() { y++; }
    public void moveLeft() { x--; }
    public void moveRight() { x++; }

    public boolean embarcar(Passageiro p) {
        if (passageiros.size() < capacidade) {
            passageiros.add(p);
            return true;
        }
        return false;
    }
}
