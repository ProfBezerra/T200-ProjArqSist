package missao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int minX = -5;
        int maxX = 5;
        int minY = -5;
        int maxY = 5;

        Path rankingPath = Paths.get("ranking.json");
        List<RankingEntry> ranking = loadRanking(rankingPath);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do piloto: ");
        String pilotoNome = readLineSafe(scanner);
        if (pilotoNome.isEmpty()) {
            pilotoNome = "Piloto Anônimo";
        }

        System.out.println("================================================================");
        System.out.println("Missão Marte Unifor — Console Completo");
        System.out.println();
        System.out.println("Ranking dos melhores pilotos:");
        if (ranking.isEmpty()) {
            System.out.println(" - Ainda não há pontuações registradas.");
        } else {
            for (int i = 0; i < Math.min(5, ranking.size()); i++) {
                RankingEntry entry = ranking.get(i);
                System.out.printf(" %d. %s: %d pontos (%s, %d passageiros)\n",
                        i + 1, entry.getName(), entry.getScore(), entry.getTipo(), entry.getPassageirosResgatados());
            }
        }

        System.out.println();
        System.out.println("Bem-vindo à Missão Marte Unifor! Sua nave foi selecionada para uma expedição de resgate e pesquisa na superfície marciana.");
        System.out.println("Seu objetivo é localizar e embarcar todos os passageiros necessários para completar a missão antes que o seu tempo (pontuação) chegue a zero.");
        System.out.println();
        System.out.println("Objetivo:");
        System.out.println(" - Mover a nave pelo mapa");
        System.out.println(" - Encontrar e embarcar todos os passageiros");
        System.out.println(" - Evitar colisões com asteroides");
        System.out.println(" - Manter a pontuação acima de zero");
        System.out.println();
        System.out.println("Comandos:");
        System.out.println(" - w: mover para cima");
        System.out.println(" - s: mover para baixo");
        System.out.println(" - a: mover para a esquerda");
        System.out.println(" - d: mover para a direita");
        System.out.println(" - c: embarcar passageiro na posição atual");
        System.out.println(" - q: sair do jogo");
        System.out.println();
        System.out.println("Pontuação inicial varia conforme a dificuldade. Cada movimento custa 1 ponto. Cada embarque vale pontos por tipo de passageiro.");
        System.out.println();

        Dificuldade dificuldade = selecionarDificuldade(scanner);
        int pontosIniciais = pontosIniciais(dificuldade);
        int vidasIniciais = vidasIniciais(dificuldade);
        int passageirosNecessarios = passageirosPorDificuldade(dificuldade);

        System.out.println("Pressione Enter para iniciar a missão...");
        scanner.nextLine();
        System.out.println("================================================================");

        boolean playAgain = true;
        while (playAgain) {
            Missao missao = criarNovaMissao(random, minX, maxX, minY, maxY, passageirosNecessarios, vidasIniciais);
            Nave nave = missao.getNave();
            int score = pontosIniciais;
            boolean running = true;
            int passageirosResgatados = 0;

            while (running) {
                desenharMapa(missao, minX, maxX, minY, maxY, score, pilotoNome);
                System.out.printf("Nave em (%d,%d) | Pontos: %d | Vidas: %d | Passageiros a bordo: %d | Passageiros restantes: %d\n",
                        nave.getX(), nave.getY(), score, nave.getVidas(), nave.getPassageiros().size(), missao.getPassageiros().size());

                if (missao.verificaColisao()) {
                    nave.perderVida();
                    System.out.printf("Colisão com asteroide! Vidas restantes: %d\n", nave.getVidas());
                    if (nave.getVidas() == 0) {
                        System.out.println("Missão perdida por falta de vidas.");
                        break;
                    }
                }

                System.out.print("Para onde ir? ");
                String line = scanner.nextLine().trim().toLowerCase();
                if (line.isEmpty()) continue;
                char cmd = line.charAt(0);
                switch (cmd) {
                    case 'w': nave.moveUp(); score--; break;
                    case 's': nave.moveDown(); score--; break;
                    case 'a': nave.moveLeft(); score--; break;
                    case 'd': nave.moveRight(); score--; break;
                    case 'c': {
                        Passageiro p = missao.passagemNaPosicao();
                        if (p == null) {
                            System.out.println("Nenhum passageiro nesta posição.");
                        } else {
                            boolean ok = missao.embarcarPassageiroNaPosicao();
                            if (ok) {
                                score += p.getPontuacao();
                                passageirosResgatados++;
                                System.out.printf("Passageiro embarcado. +%d pontos!\n", p.getPontuacao());
                            } else {
                                System.out.println("Nave cheia, não foi possível embarcar.");
                            }
                        }
                        break;
                    }
                    case 'q': running = false; break;
                    default: System.out.println("Comando desconhecido.");
                }

                if (score <= 0) {
                    System.out.println("Pontuação zerada. Missão perdida.");
                    break;
                }

                if (missao.todosEmbarcados()) {
                    System.out.println("Todos os passageiros embarcados! Missão concluída com sucesso.");
                    System.out.printf("Pontuação final: %d\n", score);
                    boolean top = isTopScore(ranking, score);
                    if (score > 0 && top) {
                        ranking.add(new RankingEntry(pilotoNome, score, dificuldade.name(), passageirosResgatados));
                        ranking = ranking.stream()
                                .sorted(Comparator.comparingInt(RankingEntry::getScore).reversed())
                                .limit(5)
                                .collect(Collectors.toList());
                        saveRanking(rankingPath, ranking);
                        System.out.println("Novo ranking salvo! Você está entre os 5 maiores pontuadores.");
                    }
                    break;
                }
            }

            if (!ranking.isEmpty()) {
                System.out.println();
                System.out.println("Ranking Top 5:");
                printRanking(ranking);
            } else {
                System.out.println();
                System.out.println("Ranking vazio. Seja o primeiro a marcar pontos!");
            }

            System.out.print("Deseja iniciar nova missão? (s/n): ");
            String resposta = readLineSafe(scanner).trim().toLowerCase();
            if (resposta.equals("s") || resposta.equals("sim")) {
                System.out.println("Preparando nova missão...");
                dificuldade = selecionarDificuldade(scanner);
                pontosIniciais = pontosIniciais(dificuldade);
                vidasIniciais = vidasIniciais(dificuldade);
                passageirosNecessarios = passageirosPorDificuldade(dificuldade);
            } else {
                playAgain = false;
            }
        }

        scanner.close();
        System.out.println("Fim da execução.");
    }

    private static Dificuldade selecionarDificuldade(Scanner scanner) {
        System.out.println("Escolha a dificuldade:");
        System.out.println("1 - Fácil");
        System.out.println("2 - Normal");
        System.out.println("3 - Difícil");
        System.out.print("Digite 1, 2 ou 3: ");

        String input = scanner.nextLine().trim();
        switch (input) {
            case "1": return Dificuldade.FACIL;
            case "3": return Dificuldade.DIFICIL;
            default: return Dificuldade.NORMAL;
        }
    }

    private static int pontosIniciais(Dificuldade dificuldade) {
        switch (dificuldade) {
            case FACIL: return 30;
            case DIFICIL: return 15;
            default: return 20;
        }
    }

    private static int vidasIniciais(Dificuldade dificuldade) {
        switch (dificuldade) {
            case FACIL: return 5;
            case DIFICIL: return 2;
            default: return 3;
        }
    }

    private static int passageirosPorDificuldade(Dificuldade dificuldade) {
        switch (dificuldade) {
            case FACIL: return 3;
            case DIFICIL: return 5;
            default: return 4;
        }
    }

    private static void printRanking(List<RankingEntry> ranking) {
        int position = 1;
        for (RankingEntry entry : ranking) {
            System.out.printf("%d. %s - %d pontos (%s, %d passageiros)\n",
                    position++, entry.getName(), entry.getScore(), entry.getTipo(), entry.getPassageirosResgatados());
        }
    }

    private static Missao criarNovaMissao(Random random, int minX, int maxX, int minY, int maxY, int passageirosNecessarios, int vidasIniciais) {
        Nave nave = new Nave("A-1", passageirosNecessarios, vidasIniciais);
        Missao missao = new Missao(nave);

        while (missao.getPassageiros().size() < passageirosNecessarios) {
            int x = random.nextInt(maxX - minX + 1) + minX;
            int y = random.nextInt(maxY - minY + 1) + minY;
            if (x == nave.getX() && y == nave.getY()) continue;
            if (posicaoOcupada(missao, x, y)) continue;

            if (missao.getPassageiros().size() == 0) {
                missao.addPassageiro(new Professor("Dr. Silva", x, y));
            } else if (missao.getPassageiros().size() == 1) {
                missao.addPassageiro(new Engenheiro("Eng. Rosa", x, y));
            } else if (missao.getPassageiros().size() == 2) {
                missao.addPassageiro(new Astronauta("Astro Ana", x, y));
            } else {
                missao.addPassageiro(new Professor("Dr. Lima", x, y));
            }
        }

        int asteroidesNecessarios = 2 + random.nextInt(3);
        while (missao.getAsteroides().size() < asteroidesNecessarios) {
            int x = random.nextInt(maxX - minX + 1) + minX;
            int y = random.nextInt(maxY - minY + 1) + minY;
            if (x == nave.getX() && y == nave.getY()) continue;
            if (posicaoOcupada(missao, x, y)) continue;
            missao.addAsteroide(new Asteroide(x, y));
        }

        return missao;
    }

    private static boolean posicaoOcupada(Missao missao, int x, int y) {
        if (missao.getNave().getX() == x && missao.getNave().getY() == y) return true;
        for (Passageiro p : missao.getPassageiros()) {
            if (p.getX() == x && p.getY() == y) return true;
        }
        for (Asteroide a : missao.getAsteroides()) {
            if (a.getX() == x && a.getY() == y) return true;
        }
        return false;
    }

    private static void desenharMapa(Missao missao, int minX, int maxX, int minY, int maxY, int score, String pilotoNome) {
        System.out.println();
        System.out.printf("Mapa da Missão (Pontos: %d) - Piloto: %s%n", score, pilotoNome);
        System.out.print("    ");
        for (int x = minX; x <= maxX; x++) {
            System.out.printf(" %2d", x);
        }
        System.out.println();
        System.out.print("    ");
        for (int x = minX; x <= maxX; x++) {
            System.out.print(" __");
        }
        System.out.println();

        for (int y = minY; y <= maxY; y++) {
            System.out.printf("%3d|", y);
            for (int x = minX; x <= maxX; x++) {
                char symbol = '.';
                if (missao.getNave().getX() == x && missao.getNave().getY() == y) {
                    symbol = 'N';
                } else {
                    for (Passageiro p : missao.getPassageiros()) {
                        if (p.getX() == x && p.getY() == y) {
                            if (p instanceof Engenheiro) {
                                symbol = 'E';
                            } else if (p instanceof Astronauta) {
                                symbol = 'A';
                            } else {
                                symbol = 'P';
                            }
                            break;
                        }
                    }
                    if (symbol == '.') {
                        for (Asteroide a : missao.getAsteroides()) {
                            if (a.getX() == x && a.getY() == y) {
                                symbol = '#';
                                break;
                            }
                        }
                    }
                }
                System.out.printf(" %2c", symbol);
            }
            System.out.println();
        }

        System.out.println("Legenda: N=Nave, P=Professor, E=Engenheiro, A=Astronauta, #=Asteroide, .=Vazio");
        System.out.println("Resumo de comandos: w(cima)/s(baixo)/a(esquerda)/d(direita) mover, c embarcar, q sair");
        System.out.println("Passageiros restantes:");
        for (Passageiro p : missao.getPassageiros()) {
            System.out.printf(" - %s (%s) em (%d,%d)\n", p.getNome(), p.getTipo(), p.getX(), p.getY());
        }
        System.out.println();
    }

    private static boolean isTopScore(List<RankingEntry> ranking, int score) {
        if (ranking.size() < 5) {
            return true;
        }
        return score > ranking.get(ranking.size() - 1).getScore();
    }

    private static String readLineSafe(Scanner scanner) {
        try {
            return scanner.nextLine();
        } catch (Exception e) {
            return "";
        }
    }

    private static List<RankingEntry> loadRanking(Path path) {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8).trim();
            return parseRankingJson(json);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static void saveRanking(Path path, List<RankingEntry> ranking) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < ranking.size(); i++) {
            RankingEntry entry = ranking.get(i);
            builder.append("{\"name\":\"")
                    .append(entry.getName().replace("\"", "\\\""))
                    .append("\",\"score\":")
                    .append(entry.getScore())
                    .append(",\"tipo\":\"")
                    .append(entry.getTipo().replace("\"", "\\\""))
                    .append("\",\"passageiros\":")
                    .append(entry.getPassageirosResgatados())
                    .append("}");
            if (i < ranking.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        try {
            Files.write(path, builder.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Não foi possível salvar o ranking: " + e.getMessage());
        }
    }

    private static List<RankingEntry> parseRankingJson(String json) {
        List<RankingEntry> ranking = new ArrayList<>();
        if (json.isEmpty() || json.equals("[]")) {
            return ranking;
        }
        json = json.trim();
        if (json.startsWith("[")) {
            json = json.substring(1);
        }
        if (json.endsWith("]")) {
            json = json.substring(0, json.length() - 1);
        }

        int index = 0;
        while (index < json.length()) {
            int start = json.indexOf('{', index);
            if (start < 0) break;
            int end = json.indexOf('}', start);
            if (end < 0) break;
            String object = json.substring(start + 1, end);
            String name = null;
            Integer score = null;
            String tipo = null;
            Integer passageiros = null;
            for (String part : object.split(",")) {
                String[] pair = part.split(":", 2);
                if (pair.length != 2) continue;
                String key = pair[0].trim().replaceAll("\"", "");
                String value = pair[1].trim();
                if (key.equals("name")) {
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        name = value.substring(1, value.length() - 1).replace("\\\"", "\"");
                    }
                } else if (key.equals("score")) {
                    try {
                        score = Integer.parseInt(value);
                    } catch (NumberFormatException ignored) {
                    }
                } else if (key.equals("tipo")) {
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        tipo = value.substring(1, value.length() - 1).replace("\\\"", "\"");
                    }
                } else if (key.equals("passageiros")) {
                    try {
                        passageiros = Integer.parseInt(value);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            if (name != null && score != null && tipo != null && passageiros != null) {
                ranking.add(new RankingEntry(name, score, tipo, passageiros));
            }
            index = end + 1;
        }

        ranking.sort(Comparator.comparingInt(RankingEntry::getScore).reversed());
        return ranking;
    }
}
