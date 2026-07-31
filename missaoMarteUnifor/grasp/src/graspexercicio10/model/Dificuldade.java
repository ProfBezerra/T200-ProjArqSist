package graspexercicio10.model;

/**
 * GRASP Information Expert: a própria Dificuldade conhece sua pontuação inicial.
 *
 * No exercício 10 original, Main.definirPontuacaoInicial(Dificuldade) continha
 * esse switch. Como Dificuldade possui a informação necessária (ela mesma), o
 * método pertence a ela.
 */
public enum Dificuldade {
    FACIL, MEDIO, DIFICIL;

    public int getPontuacaoInicial() {
        switch (this) {
            case FACIL:   return 30;
            case DIFICIL: return 15;
            default:      return 20;
        }
    }

    public static Dificuldade deString(String texto) {
        if (texto == null) return MEDIO;
        switch (texto.trim().toLowerCase()
                     .replace("á", "a").replace("í", "i").replace("é", "e")) {
            case "facil":   return FACIL;
            case "dificil": return DIFICIL;
            default:        return MEDIO;
        }
    }
}
