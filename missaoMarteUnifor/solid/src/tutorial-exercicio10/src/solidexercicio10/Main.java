package solidexercicio10;

import java.util.Scanner;
import solidexercicio10.repository.RankingRepository;
import solidexercicio10.repository.RankingService;
import solidexercicio10.service.JogoService;

/**
 * Ponto de entrada da aplicação.
 *
 * <p>Esta classe apenas inicia o sistema e delega o fluxo para o serviço de jogo.
 * O objetivo aqui é mostrar que a classe Main não precisa concentrar regras de
 * negócio, o que reforça o princípio de responsabilidade única (SRP) e a inversão
 * de dependência (DIP).</p>
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("================================================================");
        System.out.println("             MISSÃO MARTE UNIFOR - VERSÃO SOLID                  ");
        System.out.println("================================================================");
        System.out.println("  Pilote sua nave, salve os passageiros e desvie dos perigos!   ");
        System.out.println("================================================================\n");

        RankingRepository repository = new RankingService("ranking-solid-exercicio10.json");
        JogoService jogoService = new JogoService(repository);

        try (Scanner scanner = new Scanner(System.in)) {
            jogoService.executarLoop(scanner);
        }
    }
}
