package graspexercicio10;

import graspexercicio10.controller.GameController;
import graspexercicio10.presentation.MapaRenderer;
import graspexercicio10.repository.IRankingRepository;
import graspexercicio10.repository.RankingRepository;
import graspexercicio10.service.JogoService;

import java.nio.file.Paths;
import java.util.Scanner;

/**
 * GRASP Low Coupling: Main apenas monta as dependências (wiring) e entrega ao Controller.
 * Nenhuma lógica de domínio ou de apresentação reside aqui.
 *
 * Compare com o exercício 10 original: Main.java tinha ~550 linhas.
 * Após a migração GRASP: Main.java tem menos de 20 linhas.
 */
public class Main {
    public static void main(String[] args) {
        IRankingRepository rankingRepo = new RankingRepository(Paths.get("ranking-grasp.json"));
        JogoService        jogo        = new JogoService(rankingRepo);
        MapaRenderer       renderer    = new MapaRenderer();
        Scanner            scanner     = new Scanner(System.in);

        new GameController(jogo, renderer, rankingRepo, scanner).iniciar();
        scanner.close();
    }
}
