package exercicio10;

public class Astronauta extends Passageiro {
    public Astronauta(String nome, int x, int y) {
        super(nome, "Astronauta", x, y);
    }

    @Override
    public int getPontuacao() {
        return 20;  // Astronauta vale 20 pontos
    }
}
