package graspexercicio10.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * GRASP Information Expert: Missao conhece todos os elementos do tabuleiro
 * e portanto é responsável por verificar colisões e checar posições ocupadas.
 *
 * Dois métodos migrados de Main para cá:
 *   posicaoOcupada() — Main não tem os dados, Missao tem.
 *   verificaColisao() — já existia, agora usa getPerigos() (Polymorphism).
 */
public class Missao {
    private final Nave             nave;
    private final List<Passageiro> passageiros = new ArrayList<>();
    private final List<Asteroide>  asteroides  = new ArrayList<>();
    private final List<Inimigo>    inimigos    = new ArrayList<>();

    public Missao(Nave nave) {
        this.nave = nave;
    }

    public Nave             getNave()          { return nave; }
    public List<Passageiro> getPassageiros()   { return passageiros; }
    public List<Asteroide>  getAsteroides()    { return asteroides; }
    public List<Inimigo>    getInimigos()      { return inimigos; }

    public void addPassageiro(Passageiro p)    { passageiros.add(p); }
    public void addAsteroide(Asteroide a)      { asteroides.add(a); }
    public void addInimigo(Inimigo i)          { inimigos.add(i); }

    // GRASP Polymorphism: lista unificada via interface Perigo
    public List<Perigo> getPerigos() {
        List<Perigo> todos = new ArrayList<>();
        todos.addAll(asteroides);
        todos.addAll(inimigos);
        return todos;
    }

    // GRASP Information Expert: só Missao tem todos os dados necessários para esta verificação
    public boolean posicaoOcupada(int x, int y) {
        if (nave.getX() == x && nave.getY() == y) return true;
        for (Passageiro p : passageiros) {
            if (p.getX() == x && p.getY() == y) return true;
        }
        for (Perigo p : getPerigos()) {
            if (p.getX() == x && p.getY() == y) return true;
        }
        return false;
    }

    public boolean verificaColisao() {
        for (Perigo p : getPerigos()) {
            if (p.colideCom(nave)) return true;
        }
        return false;
    }

    public void moverInimigos(Random random, int minX, int maxX, int minY, int maxY) {
        for (Inimigo i : inimigos) {
            i.mover(random, minX, maxX, minY, maxY);
        }
    }

    public Passageiro passagemNaPosicao() {
        for (Passageiro p : passageiros) {
            if (p.getX() == nave.getX() && p.getY() == nave.getY()) return p;
        }
        return null;
    }

    public boolean embarcarPassageiroNaPosicao() {
        Iterator<Passageiro> it = passageiros.iterator();
        while (it.hasNext()) {
            Passageiro p = it.next();
            if (p.getX() == nave.getX() && p.getY() == nave.getY()) {
                boolean ok = nave.embarcar(p);
                if (ok) it.remove();
                return ok;
            }
        }
        return false;
    }

    public boolean todosEmbarcados() { return passageiros.isEmpty(); }
}
