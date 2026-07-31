package graspexercicio10.repository;

import graspexercicio10.model.Dificuldade;

/** Registro imutável de uma partida salva no ranking. */
public class RankingEntry {
    public final String      nome;
    public final int         score;
    public final Dificuldade dificuldade;
    public final int         passageirosColetados;
    public final String      dataHora;
    public final long        tempoJogo;

    public RankingEntry(String nome, int score, Dificuldade dificuldade,
                        int passageirosColetados, String dataHora, long tempoJogo) {
        this.nome                 = nome;
        this.score                = score;
        this.dificuldade          = dificuldade;
        this.passageirosColetados = passageirosColetados;
        this.dataHora             = dataHora;
        this.tempoJogo            = tempoJogo;
    }
}
