package solidexercicio10.repository;

import solidexercicio10.model.Dificuldade;

/**
 * Representa uma entrada de ranking registrada ao final de uma partida.
 *
 * <p>Essa classe é um simples objeto de transferência de dados, sem regras de
 * negócio, o que ajuda a manter a responsabilidade do domínio organizada.</p>
 */
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
