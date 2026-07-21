package solidexercicio10.repository;

import java.util.List;
import solidexercicio10.model.Dificuldade;

/**
 * Contrato para persistência do ranking.
 *
 * <p>Define apenas o comportamento necessário para salvar e consultar registros.
 * Esse tipo de abstração favorece o princípio da inversão de dependência (DIP)
 * e também ajuda a manter o código mais flexível.</p>
 */
public interface RankingRepository {
    void salvar(String nome, int pontuacao);
    void salvar(String nome, int pontuacao, Dificuldade dificuldade, int passageirosColetados, long tempoJogo);
    List<RankingEntry> listar();
    void limpar();
}
