package solidexercicio10.repository;

import java.util.List;
import solidexercicio10.model.Dificuldade;

public interface RankingRepository {
    void salvar(String nome, int pontuacao);
    void salvar(String nome, int pontuacao, Dificuldade dificuldade, int passageirosColetados, long tempoJogo);
    List<RankingEntry> listar();
    void limpar();
}
