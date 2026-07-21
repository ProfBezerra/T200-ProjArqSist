package solidexercicio10.model;

public enum Dificuldade {
    FACIL,
    MEDIO,
    DIFICIL;

    public static Dificuldade deString(String valor) {
        if (valor == null) return MEDIO;
        switch (valor.toLowerCase()) {
            case "facil": return FACIL;
            case "dificil": return DIFICIL;
            default: return MEDIO;
        }
    }
}
