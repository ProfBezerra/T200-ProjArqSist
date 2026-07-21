package solidexercicio10.model;

/**
 * Enumeração das dificuldades possíveis do jogo.
 *
 * <p>Usar um enum torna o código mais expressivo e evita valores mágicos, o que
 * ajuda a manter a modelagem do domínio mais clara.</p>
 */
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
