package graspexercicio10.presentation;

import graspexercicio10.model.Missao;
import graspexercicio10.model.Nave;
import graspexercicio10.model.Passageiro;
import graspexercicio10.model.Perigo;
import graspexercicio10.repository.RankingEntry;

import java.util.List;

/**
 * GRASP Pure Fabrication: não existe no domínio do jogo — foi criada para
 * centralizar toda a responsabilidade de exibição no console.
 *
 * GRASP High Cohesion: responsabilidade única — renderizar a interface textual.
 *
 * GRASP Polymorphism: usa getSimbolo() em Passageiro e Perigo, eliminando
 * completamente os blocos instanceof que existiam em Main.desenharMapa().
 */
public class MapaRenderer {

    public void exibirBoasVindas() {
        System.out.println("================================================================");
        System.out.println("        MISSÃO MARTE UNIFOR — GRASP Edition                    ");
        System.out.println("================================================================");
        System.out.println("  Pilote sua nave, salve os passageiros e desvie dos perigos!  ");
        System.out.println("================================================================");
    }

    public void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Iniciar Nova Missão");
        System.out.println("2. Visualizar Ranking Top 5");
        System.out.println("3. Resetar Histórico de Ranking");
        System.out.println("4. Sair do Jogo");
        System.out.println("----------------------");
    }

    public void desenharMapa(Missao missao, int minX, int maxX, int minY, int maxY,
                             int score, String pilotoNome) {
        Nave nave = missao.getNave();
        System.out.println();
        System.out.printf("Mapa da Missão (Pontos: %d) — Piloto: %s%n", score, pilotoNome);

        System.out.print("    ");
        for (int x = minX; x <= maxX; x++) System.out.printf(" %2d", x);
        System.out.println();
        System.out.print("    ");
        for (int x = minX; x <= maxX; x++) System.out.print(" __");
        System.out.println();

        for (int y = minY; y <= maxY; y++) {
            System.out.printf("%3d|", y);
            for (int x = minX; x <= maxX; x++) {
                System.out.printf(" %2c", resolverSimbolo(missao, nave, x, y));
            }
            System.out.println();
        }

        System.out.println("Legenda: @=Nave, P=Professor, E=Engenheiro, T=Astronauta, #=Asteroide, X=Inimigo, L=Base");
        System.out.println("Comandos: w/s/a/d (mover), c (embarcar), q (sair)");
        for (Passageiro p : missao.getPassageiros()) {
            System.out.printf(" - %s (%s) em (%d,%d)%n", p.getNome(), p.getTipo(), p.getX(), p.getY());
        }
    }

    public void exibirStatusPartida(Nave nave, Missao missao, int score, String piloto) {
        System.out.printf(
            "Nave em (%d,%d) | Pontos: %d | Vidas: %d | A bordo: %d/%d | Restantes: %d%n",
            nave.getX(), nave.getY(), score, nave.getVidas(),
            nave.getPassageiros().size(), nave.getCapacidade(),
            missao.getPassageiros().size()
        );
    }

    public void exibirEstatisticas(int score, int movimentos, long tempoSegundos,
                                   int passageiros, int recorde, String nomeRecorde) {
        System.out.println("================================================================");
        System.out.println("Estatísticas da Partida:");
        System.out.printf(" - Pontuação Final:        %d pontos%n", score);
        System.out.printf(" - Movimentos Efetuados:   %d%n", movimentos);
        System.out.printf(" - Tempo de Jogo:          %d segundos%n", tempoSegundos);
        System.out.printf(" - Passageiros Resgatados: %d%n", passageiros);
        if (recorde > 0) {
            System.out.printf(" - Recorde a bater:        %d pts (%s)%n", recorde, nomeRecorde);
        }
        System.out.println("================================================================");
    }

    public void exibirRanking(List<RankingEntry> ranking) {
        System.out.println("\n====== RANKING TOP 5 PILOTOS ======");
        if (ranking.isEmpty()) {
            System.out.println(" - Nenhum registro. Seja o primeiro!");
        } else {
            int pos = 1;
            for (RankingEntry e : ranking) {
                System.out.printf("%d. %-20s %4d pts | %-8s | %d passag. | %ds | %s%n",
                        pos++, e.nome, e.score, e.dificuldade,
                        e.passageirosColetados, e.tempoJogo, e.dataHora);
            }
        }
        System.out.println("===================================");
    }

    // GRASP Polymorphism: cada objeto retorna seu próprio símbolo — sem instanceof
    private char resolverSimbolo(Missao missao, Nave nave, int x, int y) {
        if (nave.getX() == x && nave.getY() == y) return '@';
        for (Passageiro p : missao.getPassageiros()) {
            if (p.getX() == x && p.getY() == y) return p.getSimbolo();
        }
        for (Perigo p : missao.getPerigos()) {
            if (p.getX() == x && p.getY() == y) return p.getSimbolo();
        }
        if (x == 0 && y == 0) return 'L';
        return '.';
    }
}
