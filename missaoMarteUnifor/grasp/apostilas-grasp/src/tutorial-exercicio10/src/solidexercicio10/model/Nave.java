package solidexercicio10.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a nave do jogador.
 *
 * <p>Esta classe modela o estado da nave e suas ações básicas, sem misturar a
 * lógica completa do jogo. Assim, ela mantém uma responsabilidade bem definida.</p>
 */
public class Nave extends EntidadeMapa implements Movel {
    private final String nome;
    private final List<Passageiro> passageiros;
    private final int capacidade;
    private int vidas;

    public Nave(String nome, int x, int y) {
        this(nome, x, y, 3);
    }

    public Nave(String nome, int x, int y, int capacidade) {
        super(x, y);
        this.nome = nome;
        this.passageiros = new ArrayList<>();
        this.capacidade = capacidade;
        this.vidas = 3;
    }

    public String getNome() {
        return nome;
    }

    public void embarcar(Passageiro passageiro) {
        if (passageiros.size() < capacidade) {
            passageiros.add(passageiro);
        }
    }

    public void moverComLimites(char comando, int minX, int maxX, int minY, int maxY) {
        int dx = 0;
        int dy = 0;

        switch (comando) {
            case 'w' -> dy = 1;
            case 's' -> dy = -1;
            case 'a' -> dx = -1;
            case 'd' -> dx = 1;
        }

        int novoX = this.x + dx;
        int novoY = this.y + dy;
        if (novoX >= minX && novoX <= maxX && novoY >= minY && novoY <= maxY) {
            this.x = novoX;
            this.y = novoY;
        }
    }

    public void perderVida() {
        this.vidas = Math.max(0, this.vidas - 1);
    }

    public int getVidas() {
        return vidas;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    @Override
    public void mover(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public String getSimbolo() {
        return "N";
    }
}
