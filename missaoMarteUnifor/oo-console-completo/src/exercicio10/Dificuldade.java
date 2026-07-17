package exercicio10;

public enum Dificuldade {
    FACIL, MEDIO, DIFICIL;

    public static Dificuldade deString(String s) {
        if (s == null) return MEDIO;
        switch (s.trim().toLowerCase()) {
            case "facil":
            case "fácil":
                return FACIL;
            case "dificil":
            case "difícil":
                return DIFICIL;
            default:
                return MEDIO;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case FACIL: return "Fácil";
            case DIFICIL: return "Difícil";
            default: return "Médio";
        }
    }
}
