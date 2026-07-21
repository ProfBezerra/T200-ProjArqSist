package solidexercicio10.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa o estado e as regras de uma missão em andamento.
 *
 * <p>Esta classe agrupa os elementos da missão e suas regras principais, ajudando a
 * separar o domínio do jogo em uma camada organizada e coerente.</p>
 */
public class Missao {
    private final Nave nave;
    private final List<Passageiro> passageiros;
    private final List<Asteroide> asteroides;
    private final List<Inimigo> inimigos;

    public Missao(Nave nave) {
        this.nave = nave;
        this.passageiros = new ArrayList<>();
        this.asteroides = new ArrayList<>();
        this.inimigos = new ArrayList<>();
    }

    public void adicionarPassageiro(Passageiro passageiro) {
        passageiros.add(passageiro);
    }

    public void adicionarAsteroide(Asteroide asteroide) {
        asteroides.add(asteroide);
    }

    public void adicionarInimigo(Inimigo inimigo) {
        inimigos.add(inimigo);
    }

    public Passageiro passagemNaPosicao() {
        for (Passageiro passageiro : passageiros) {
            if (passageiro.getX() == nave.getX() && passageiro.getY() == nave.getY()) {
                return passageiro;
            }
        }
        return null;
    }

    public boolean embarcarPassageiroNaPosicao() {
        Passageiro passageiro = passagemNaPosicao();
        if (passageiro == null || nave.getPassageiros().size() >= nave.getCapacidade()) {
            return false;
        }
        nave.embarcar(passageiro);
        passageiros.remove(passageiro);
        return true;
    }

    public void moverInimigos() {
        for (Inimigo inimigo : inimigos) {
            int dx = (int) (Math.random() * 3) - 1;
            int dy = (int) (Math.random() * 3) - 1;
            inimigo.mover(dx, dy);
        }
    }

    public boolean verificaColisao() {
        for (Asteroide asteroide : asteroides) {
            if (asteroide.getX() == nave.getX() && asteroide.getY() == nave.getY()) {
                return true;
            }
        }
        for (Inimigo inimigo : inimigos) {
            if (inimigo.getX() == nave.getX() && inimigo.getY() == nave.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean todosEmbarcados() {
        return passageiros.isEmpty();
    }

    public Nave getNave() {
        return nave;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    public List<Asteroide> getAsteroides() {
        return asteroides;
    }

    public List<Inimigo> getInimigos() {
        return inimigos;
    }
}
