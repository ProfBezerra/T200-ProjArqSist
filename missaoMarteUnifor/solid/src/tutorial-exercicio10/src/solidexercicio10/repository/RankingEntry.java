package solidexercicio10.repository;

import solidexercicio10.model.Dificuldade;

public class RankingEntry {
    public final String name;
    public final int score;
    public final Dificuldade dificuldade;
    public final int passageirosColetados;
    public final String dataHora;
    public final long tempoJogo;

    public RankingEntry(String name, int score, Dificuldade dificuldade, int passageirosColetados, String dataHora, long tempoJogo) {
        this.name = name;
        this.score = score;
        this.dificuldade = dificuldade;
        this.passageirosColetados = passageirosColetados;
        this.dataHora = dataHora;
        this.tempoJogo = tempoJogo;
    }
}
