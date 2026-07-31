package graspexercicio10.service;

import graspexercicio10.model.*;

import java.util.Random;

/**
 * GRASP Creator: tem os dados necessários para criar uma Missao completa com
 * todos os seus elementos (nave, passageiros, asteroides, inimigos).
 *
 * GRASP Pure Fabrication: não representa um conceito do domínio — existe
 * para encapsular a lógica de montagem da missão, que antes estava em Main.
 *
 * GRASP Polymorphism: criarPassageiro() instancia o tipo correto sem if/instanceof
 * nos chamadores.
 */
public class FabricaMissao {

    public Missao criar(Dificuldade dificuldade, int minX, int maxX, int minY, int maxY,
                        Random random) {
        Nave  nave   = new Nave("A-1", 5);
        Missao missao = new Missao(nave);

        int[] qtds = quantidades(dificuldade);   // [passageiros, asteroides, inimigos]

        while (missao.getPassageiros().size() < qtds[0]) {
            int x = rand(random, minX, maxX);
            int y = rand(random, minY, maxY);
            if (missao.posicaoOcupada(x, y)) continue;
            missao.addPassageiro(criarPassageiro(missao.getPassageiros().size(), x, y));
        }
        while (missao.getAsteroides().size() < qtds[1]) {
            int x = rand(random, minX, maxX);
            int y = rand(random, minY, maxY);
            if (!missao.posicaoOcupada(x, y)) missao.addAsteroide(new Asteroide(x, y));
        }
        while (missao.getInimigos().size() < qtds[2]) {
            int x = rand(random, minX, maxX);
            int y = rand(random, minY, maxY);
            if (!missao.posicaoOcupada(x, y)) missao.addInimigo(new Inimigo(x, y));
        }

        return missao;
    }

    // GRASP Polymorphism: produz o tipo concreto correto — o chamador não precisa saber
    private Passageiro criarPassageiro(int indice, int x, int y) {
        switch (indice % 5) {
            case 0:  return new Professor("Dr. Silva",    x, y);
            case 1:  return new Engenheiro("Eng. Rosa",   x, y);
            case 2:  return new Professor("Dr. Lima",     x, y);
            case 3:  return new Engenheiro("Eng. Carlos", x, y);
            default: return new Astronauta("Ast. Maria",  x, y);
        }
    }

    private int[] quantidades(Dificuldade d) {
        switch (d) {
            case FACIL:   return new int[]{4, 1, 1};
            case DIFICIL: return new int[]{5, 3, 3};
            default:      return new int[]{5, 2, 2};
        }
    }

    private int rand(Random r, int min, int max) {
        return r.nextInt(max - min + 1) + min;
    }
}
