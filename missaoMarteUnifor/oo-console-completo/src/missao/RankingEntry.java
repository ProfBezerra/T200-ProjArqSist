package missao;

public class RankingEntry {
    private final String name;
    private final int score;
    private final String tipo;
    private final int passageirosResgatados;

    public RankingEntry(String name, int score, String tipo, int passageirosResgatados) {
        this.name = name;
        this.score = score;
        this.tipo = tipo;
        this.passageirosResgatados = passageirosResgatados;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPassageirosResgatados() {
        return passageirosResgatados;
    }
}
