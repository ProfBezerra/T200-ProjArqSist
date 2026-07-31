package graspexercicio10.controller;

import graspexercicio10.model.Dificuldade;
import graspexercicio10.presentation.MapaRenderer;
import graspexercicio10.repository.IRankingRepository;
import graspexercicio10.repository.RankingEntry;
import graspexercicio10.service.JogoService;

import java.util.List;
import java.util.Scanner;

/**
 * GRASP Controller: ponto único de entrada para eventos do usuário.
 *
 * Responsável por:
 *   1. Ler comandos do console (Scanner).
 *   2. Traduzir esses comandos em chamadas à JogoService (lógica de domínio).
 *   3. Solicitar ao MapaRenderer que exiba o resultado.
 *
 * NÃO contém lógica de domínio nem código de I/O de arquivo.
 *
 * GRASP Low Coupling: depende de interfaces/abstrações (IRankingRepository),
 * não de implementações concretas.
 */
public class GameController {

    private final JogoService        jogo;
    private final MapaRenderer       renderer;
    private final IRankingRepository ranking;
    private final Scanner            scanner;

    public GameController(JogoService jogo, MapaRenderer renderer,
                          IRankingRepository ranking, Scanner scanner) {
        this.jogo     = jogo;
        this.renderer = renderer;
        this.ranking  = ranking;
        this.scanner  = scanner;
    }

    public void iniciar() {
        renderer.exibirBoasVindas();
        boolean rodando = true;
        while (rodando) {
            renderer.exibirMenu();
            switch (lerLinha("Escolha uma opção: ", "1")) {
                case "1": jogarPartida();                          break;
                case "2": renderer.exibirRanking(ranking.carregar()); break;
                case "3": resetarRanking();                        break;
                case "4": rodando = false; System.out.println("\nAté logo, piloto!"); break;
                default:  System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // ── Partida ───────────────────────────────────────────────────────────────

    private void jogarPartida() {
        String nome = lerLinha("\nNome do piloto: ", "Piloto Anônimo");
        if (nome.isEmpty()) nome = "Piloto Anônimo";

        Dificuldade dif = Dificuldade.deString(
                lerLinha("Dificuldade (facil/medio/dificil): ", "medio"));
        int tam = lerInteiro("Tamanho do mapa (ex: 5): ", 5);

        System.out.println("\nIniciando missão na dificuldade " + dif + "...");
        System.out.println("Pressione Enter para decolar!");
        scanner.nextLine();

        jogo.iniciarPartida(nome, dif, tam);

        while (jogo.isPartidaAtiva()) {
            renderer.desenharMapa(
                    jogo.getMissao(), jogo.getMinX(), jogo.getMaxX(),
                    jogo.getMinY(),   jogo.getMaxY(),
                    jogo.getScore(),  jogo.getPilotoNome());
            renderer.exibirStatusPartida(
                    jogo.getMissao().getNave(), jogo.getMissao(),
                    jogo.getScore(), jogo.getPilotoNome());

            String input = lerLinha("Comando (w/s/a/d/c/q): ", "").toLowerCase();
            if (input.isEmpty()) continue;

            char cmd = input.charAt(0);
            if (cmd == 'q') {
                System.out.println("Missão abortada pelo piloto.");
                break;
            }

            String resultado;
            if (cmd == 'c') {
                resultado = jogo.embarcar();
            } else if ("wasd".indexOf(cmd) >= 0) {
                resultado = jogo.mover(cmd);
            } else {
                System.out.println("Comando inválido (use w/s/a/d/c/q).");
                continue;
            }
            if (!resultado.isEmpty()) System.out.println(resultado);
        }

        if (!jogo.isPartidaAtiva()) {
            List<RankingEntry> top = ranking.carregar();
            int    recorde    = top.isEmpty() ? 0 : top.get(0).score;
            String nomeRecord = top.isEmpty() ? "" : top.get(0).nome;
            renderer.exibirEstatisticas(
                    jogo.getScore(), jogo.getMovimentos(), jogo.getTempoJogo(),
                    jogo.getMissao().getNave().getPassageiros().size(),
                    recorde, nomeRecord);
        }
    }

    // ── Ranking ───────────────────────────────────────────────────────────────

    private void resetarRanking() {
        String conf = lerLinha("Limpar ranking? (s/n): ", "n").toLowerCase();
        if (conf.equals("s") || conf.equals("sim")) {
            ranking.resetar();
            System.out.println("Ranking resetado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // ── Utilitários de leitura ────────────────────────────────────────────────

    private String lerLinha(String prompt, String fallback) {
        System.out.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine().trim() : fallback;
    }

    private int lerInteiro(String prompt, int padrao) {
        try {
            return Integer.parseInt(lerLinha(prompt, String.valueOf(padrao)));
        } catch (NumberFormatException e) {
            return padrao;
        }
    }
}
