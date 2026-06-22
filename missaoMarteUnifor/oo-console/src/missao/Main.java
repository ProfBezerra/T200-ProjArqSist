package missao;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Nave nave = new Nave("A-1", 3);
        Missao missao = new Missao(nave);

        // Adiciona alguns passageiros e asteroides em posições simples
        missao.addPassageiro(new Professor("Dr. Silva", 2, 0));
        missao.addPassageiro(new Engenheiro("Eng. Rosa", -1, 1));
        missao.addPassageiro(new Professor("Dr. Lima", 0, 2));

        missao.addAsteroide(new Asteroide(1, 1));
        missao.addAsteroide(new Asteroide(-1, 0));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Missão Marte — console (w/a/s/d mover, c embarcar, q sair)");

        boolean running = true;
        while (running) {
            System.out.printf("Nave em (%d,%d) | Passageiros a bordo: %d | Passageiros restantes: %d\n",
                    nave.getX(), nave.getY(), nave.getPassageiros().size(), missao.todosEmbarcados() ? 0 : 1);

            if (missao.verificaColisao()) {
                System.out.println("Colisão com asteroide! Missão abortada.");
                break;
            }

            System.out.print("Comando: ");
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.isEmpty()) continue;
            char cmd = line.charAt(0);
            switch (cmd) {
                case 'w': nave.moveUp(); break;
                case 's': nave.moveDown(); break;
                case 'a': nave.moveLeft(); break;
                case 'd': nave.moveRight(); break;
                case 'c': {
                    Passageiro p = missao.passagemNaPosicao();
                    if (p == null) {
                        System.out.println("Nenhum passageiro nesta posição.");
                    } else {
                        boolean ok = missao.embarcarPassageiroNaPosicao();
                        System.out.println(ok ? "Passageiro embarcado." : "Nave cheia, não foi possível embarcar.");
                    }
                    break;
                }
                case 'q': running = false; break;
                default: System.out.println("Comando desconhecido.");
            }

            if (missao.todosEmbarcados()) {
                System.out.println("Todos os passageiros embarcados! Missão concluída com sucesso.");
                System.out.printf("Pontuação (passageiros a bordo): %d\n", nave.getPassageiros().size());
                break;
            }
        }

        scanner.close();
        System.out.println("Fim da execução.");
    }
}
