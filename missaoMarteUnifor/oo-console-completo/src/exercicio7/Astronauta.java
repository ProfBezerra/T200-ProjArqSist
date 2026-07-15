package exercicio7;

/**
 * Exercício 2: Adicionar um novo tipo de passageiro
 * 
 * Enunciado: Crie uma classe `Astronauta` que herda de `Passageiro`.
 * 
 * Esta classe demonstra o conceito de herança em Java.
 * Astronauta é uma especialização de Passageiro com tipo fixo "Astronauta".
 */
public class Astronauta extends Passageiro {
    public Astronauta(String nome, int x, int y) {
        super(nome, "Astronauta", x, y);
    }

    @Override
    public int getPontuacao() {
        return 20;  // Astronauta vale 20 pontos
    }
}
