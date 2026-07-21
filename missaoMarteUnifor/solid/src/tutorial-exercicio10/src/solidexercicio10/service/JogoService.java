package solidexercicio10.service;

import java.util.List;
import java.util.Scanner;
import solidexercicio10.model.Asteroide;
import solidexercicio10.model.Dificuldade;
import solidexercicio10.model.Engenheiro;
import solidexercicio10.model.Inimigo;
import solidexercicio10.model.Missao;
import solidexercicio10.model.Nave;
import solidexercicio10.model.Passageiro;
import solidexercicio10.model.Professor;
import solidexercicio10.presentation.MapaRenderer;
import solidexercicio10.repository.RankingEntry;
import solidexercicio10.repository.RankingRepository;

public class JogoService {
    private final RankingRepository rankingRepository;
    private final MapaRenderer mapaRenderer;

    public JogoService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
        this.mapaRenderer = new MapaRenderer();
    }

    public void executarLoop(Scanner scanner) {
        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            String opcao = lerLinha(scanner, "Escolha uma opção: ", "1").trim();
            switch (opcao) {
                case "1":
                    jogarPartida(scanner);
                    break;
                case "2":
                    exibirRanking();
                    break;
                case "3":
                    rankingRepository.limpar();
                    System.out.println("Histórico de ranking removido.");
                    break;
                case "4":
                    rodando = false;
                    System.out.println("\nObrigado por jogar a Missão Marte Unifor!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public void registrarPontuacao(String nome, int pontuacao) {
        rankingRepository.salvar(nome, pontuacao);
    }

    public List<RankingEntry> listarRanking() {
        return rankingRepository.listar();
    }

    private void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Iniciar Nova Missão");
        System.out.println("2. Visualizar Ranking Top 5");
        System.out.println("3. Resetar Ranking");
        System.out.println("4. Sair");
        System.out.println("----------------------");
    }

    private void jogarPartida(Scanner scanner) {
        String pilotoNome = lerLinha(scanner, "\nDigite o nome do piloto: ", "Piloto Anônimo").trim();
        if (pilotoNome.isEmpty()) {
            pilotoNome = "Piloto Anônimo";
        }

        Dificuldade dificuldade = lerDificuldade(scanner);
        int tamanhoMapa = lerTamanhoMapa(scanner);
        int minX = -tamanhoMapa;
        int maxX = tamanhoMapa;
        int minY = -tamanhoMapa;
        int maxY = tamanhoMapa;

        System.out.println("\nIniciando missão na dificuldade " + dificuldade + "...");
        lerLinha(scanner, "Pressione Enter para decolar!", "");

        Missao missao = criarNovaMissao(dificuldade, minX, maxX, minY, maxY);
        Nave nave = missao.getNave();
        int score = definirPontuacaoInicial(dificuldade);
        int movimentos = 0;
        boolean partidaAtiva = true;
        long tempoInicio = System.currentTimeMillis();

        while (partidaAtiva) {
            mapaRenderer.desenhar(missao, score, pilotoNome);
            System.out.printf("Nave em (%d,%d) | Pontos: %d | Vidas: %d | A bordo: %d/%d | Restantes: %d%n",
                    nave.getX(), nave.getY(), score, nave.getVidas(), nave.getPassageiros().size(), nave.getCapacidade(), missao.getPassageiros().size());

            String entrada = lerLinha(scanner, "Comando (w/s/a/d/c/q): ", "").trim().toLowerCase();
            if (entrada.isEmpty()) {
                continue;
            }

            char cmd = entrada.charAt(0);
            if (cmd == 'q') {
                System.out.println("Missão abortada pelo piloto.");
                partidaAtiva = false;
            } else if (cmd == 'c') {
                Passageiro passageiro = missao.passagemNaPosicao();
                if (passageiro == null) {
                    System.out.println("Nenhum passageiro nesta posição.");
                } else {
                    boolean embarcou = missao.embarcarPassageiroNaPosicao();
                    if (embarcou) {
                        score += passageiro.getPontuacao();
                        System.out.printf("Passageiro %s embarcado com sucesso! +%d pontos!%n", passageiro.getNome(), passageiro.getPontuacao());
                    } else {
                        System.out.println("Nave cheia! Não há espaço para mais passageiros.");
                    }
                }
            } else if (cmd == 'w' || cmd == 's' || cmd == 'a' || cmd == 'd') {
                nave.moverComLimites(cmd, minX, maxX, minY, maxY);
                score--;
                movimentos++;
            } else {
                System.out.println("Comando inválido.");
            }

            missao.moverInimigos();
            if (missao.verificaColisao()) {
                nave.perderVida();
                if (nave.getVidas() > 0) {
                    System.out.printf("Alerta! Colisão detectada! Vidas restantes: %d%n", nave.getVidas());
                } else {
                    System.out.println("GAME OVER! A nave foi destruída.");
                    partidaAtiva = false;
                }
            }

            if (score <= 0 && partidaAtiva) {
                System.out.println("Combustível/Pontuação zerada! Missão perdida.");
                partidaAtiva = false;
            }

            if (missao.todosEmbarcados() && partidaAtiva) {
                if (nave.getX() == 0 && nave.getY() == 0) {
                    long tempoFim = System.currentTimeMillis();
                    long tempoJogoSegundos = (tempoFim - tempoInicio) / 1000;
                    System.out.println("\n================================================================");
                    System.out.println("🚀 DECOLAGEM AUTORIZADA! Nave acoplada à plataforma em (0,0).");
                    System.out.println("Retornando à órbita marciana com todos os passageiros. Missão cumprida!");
                    System.out.println("================================================================\n");
                    exibirEstatisticas(score, movimentos, tempoJogoSegundos, nave.getPassageiros().size());
                    rankingRepository.salvar(pilotoNome, score, dificuldade, nave.getPassageiros().size(), tempoJogoSegundos);
                    partidaAtiva = false;
                } else {
                    System.out.println("✨ ALERTA: Todos os passageiros resgatados! Retorne para a Plataforma de Pouso 'L' em (0,0) para completar a missão.");
                }
            }
        }
    }

    private Dificuldade lerDificuldade(Scanner scanner) {
        System.out.print("Escolha a Dificuldade (facil/medio/dificil): ");
        String valor = lerLinha(scanner, "", "medio").trim();
        return Dificuldade.deString(valor);
    }

    private int lerTamanhoMapa(Scanner scanner) {
        try {
            int tamanho = Integer.parseInt(lerLinha(scanner, "Tamanho do mapa (ex: 5): ", "5"));
            return tamanho > 0 ? tamanho : 5;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida, usando tamanho padrão (5).");
            return 5;
        }
    }

    private int definirPontuacaoInicial(Dificuldade dificuldade) {
        return switch (dificuldade) {
            case FACIL -> 30;
            case DIFICIL -> 15;
            default -> 20;
        };
    }

    private Missao criarNovaMissao(Dificuldade dificuldade, int minX, int maxX, int minY, int maxY) {
        Nave nave = new Nave("A-1", 0, 0, 3);
        Missao missao = new Missao(nave);

        int qtdPassageiros = 4;
        int qtdAsteroides = 2;
        int qtdInimigos = 2;

        if (dificuldade == Dificuldade.MEDIO) {
            qtdPassageiros = 5;
        } else if (dificuldade == Dificuldade.DIFICIL) {
            qtdPassageiros = 6;
            qtdAsteroides = 3;
            qtdInimigos = 3;
        }

        int indice = 0;
        while (missao.getPassageiros().size() < qtdPassageiros) {
            int x = (int) (Math.random() * (maxX - minX + 1)) + minX;
            int y = (int) (Math.random() * (maxY - minY + 1)) + minY;
            if (x == nave.getX() && y == nave.getY()) {
                continue;
            }
            if (posicaoOcupada(missao, x, y)) {
                continue;
            }
            if (indice % 3 == 0) {
                missao.adicionarPassageiro(new Professor("Dr. Silva", x, y));
            } else if (indice % 3 == 1) {
                missao.adicionarPassageiro(new Engenheiro("Eng. Rosa", x, y));
            } else {
                missao.adicionarPassageiro(new Professor("Dr. Lima", x, y));
            }
            indice++;
        }

        while (missao.getAsteroides().size() < qtdAsteroides) {
            int x = (int) (Math.random() * (maxX - minX + 1)) + minX;
            int y = (int) (Math.random() * (maxY - minY + 1)) + minY;
            if (x == nave.getX() && y == nave.getY()) {
                continue;
            }
            if (posicaoOcupada(missao, x, y)) {
                continue;
            }
            missao.adicionarAsteroide(new Asteroide(x, y));
        }

        while (missao.getInimigos().size() < qtdInimigos) {
            int x = (int) (Math.random() * (maxX - minX + 1)) + minX;
            int y = (int) (Math.random() * (maxY - minY + 1)) + minY;
            if (x == nave.getX() && y == nave.getY()) {
                continue;
            }
            if (posicaoOcupada(missao, x, y)) {
                continue;
            }
            missao.adicionarInimigo(new Inimigo(x, y));
        }

        return missao;
    }

    private boolean posicaoOcupada(Missao missao, int x, int y) {
        if (missao.getNave().getX() == x && missao.getNave().getY() == y) {
            return true;
        }
        for (Passageiro passageiro : missao.getPassageiros()) {
            if (passageiro.getX() == x && passageiro.getY() == y) {
                return true;
            }
        }
        for (Asteroide asteroide : missao.getAsteroides()) {
            if (asteroide.getX() == x && asteroide.getY() == y) {
                return true;
            }
        }
        for (Inimigo inimigo : missao.getInimigos()) {
            if (inimigo.getX() == x && inimigo.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private void exibirEstatisticas(int score, int movimentos, long tempoSegundos, int passageiros) {
        System.out.println("Estatísticas da Partida:");
        System.out.printf(" - Pontuação Final: %d pontos%n", score);
        System.out.printf(" - Movimentos Efetuados: %d%n", movimentos);
        System.out.printf(" - Tempo de Jogo: %d segundos%n", tempoSegundos);
        System.out.printf(" - Passageiros Resgatados: %d%n", passageiros);
        System.out.println("================================================================");
    }

    private void exibirRanking() {
        System.out.println("\n====== RANKING TOP 5 PILOTOS ======");
        List<RankingEntry> ranking = rankingRepository.listar();
        if (ranking.isEmpty()) {
            System.out.println(" - Nenhum registro encontrado. Seja o primeiro a jogar!");
        } else {
            for (int i = 0; i < Math.min(5, ranking.size()); i++) {
                RankingEntry entry = ranking.get(i);
                System.out.printf("%d. %s - %d pts | Dificuldade: %s | Coletados: %d | Tempo: %ds%n",
                        i + 1, entry.name, entry.score, entry.dificuldade, entry.passageirosColetados, entry.tempoJogo);
            }
        }
        System.out.println("===================================");
    }

    private String lerLinha(Scanner scanner, String prompt, String fallback) {
        if (prompt != null && !prompt.isEmpty()) {
            System.out.print(prompt);
        }
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return fallback;
    }
}
