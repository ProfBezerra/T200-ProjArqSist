package graspexercicio10.repository;

import java.util.List;

/**
 * GRASP Protected Variations: escuda o sistema contra mudanças no formato de persistência.
 *
 * Se amanhã o ranking migrar de JSON para banco de dados, apenas a implementação
 * concreta muda — nenhum outro código é afetado.
 */
public interface IRankingRepository {
    List<RankingEntry> carregar();
    void salvar(List<RankingEntry> ranking);
    void resetar();
    boolean ehTopScore(List<RankingEntry> ranking, int score);
}
