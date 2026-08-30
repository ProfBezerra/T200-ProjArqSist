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

        Path rankingPath = Paths.get("ranking.json");
        List<RankingEntry> ranking = loadRanking(rankingPath);

        Scanner scanner = new Scanner(System.in);

        String pilotoNome = lerLinha(
            scanner,
            "Digite o nome do piloto: ",
            "Piloto Anônimo"
        );

        if (pilotoNome.isEmpty()) {
            pilotoNome = "Piloto Anônimo";
        }

        System.out.println("================================================================");
        System.out.println("Missão Marte Unifor — Console");
        System.out.println();

        System.out.println("Ranking dos melhores pilotos:");

        if (ranking.isEmpty()) {

            System.out.println(
                " - Ainda não há pontuações registradas."
            );

        } else {

            printRanking(ranking);
        }

        System.out.println();

        System.out.println(
            "Bem-vindo à Missão Marte Unifor! Sua nave foi selecionada para uma expedição de resgate e pesquisa na superfície marciana."
        );

        System.out.println(
            "Seu objetivo é localizar e embarcar todos os passageiros necessários para completar a missão antes que o seu tempo (pontuação) chegue a zero."
        );

        System.out.println();

        System.out.println("Objetivo:");
        System.out.println(" - Mover a nave pelo mapa");
        System.out.println(" - Encontrar e embarcar todos os passageiros");
        System.out.println(" - Evitar colisões com asteroides e inimigos");
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

        System.out.println(
            "Cada movimento custa 1 ponto."
        );

        System.out.println();

        System.out.println("Bônus por tipo de passageiro:");
        System.out.println(" - Professor: +10 pontos");
        System.out.println(" - Engenheiro: +15 pontos");
        System.out.println(" - Astronauta: +20 pontos");

        System.out.println();

        System.out.println("Sistema de vidas:");
        System.out.println(" - Nave começa com 3 vidas");
        System.out.println(
            " - Cada colisão com asteroide ou inimigo custa 1 vida"
        );
        System.out.println(
            " - Se vidas chegarem a 0: GAME OVER"
        );

        System.out.println();

        String dificuldade = selecionarDificuldade(scanner);

        System.out.println(
            "Dificuldade selecionada: " + dificuldade
        );

        int pontuacaoInicial =
            definirPontuacaoInicial(dificuldade);

        System.out.println(
            "Pontuação inicial: " + pontuacaoInicial
        );

        System.out.println();

        int tamanhoMapa;

        try {

            tamanhoMapa = Integer.parseInt(
                lerLinha(
                    scanner,
                    "Tamanho do mapa (-X a +X): ",
                    "5"
                )
            );

        } catch (NumberFormatException e) {

            tamanhoMapa = 5;
        }

        if (tamanhoMapa < 1) {
            tamanhoMapa = 5;
        }

        int minX = -tamanhoMapa;
        int maxX = tamanhoMapa;
        int minY = -tamanhoMapa;
        int maxY = tamanhoMapa;

        System.out.println();
        System.out.println("Iniciando missão...");
        System.out.println(
            "================================================================"
        );

        boolean playAgain = true;

        while (playAgain) {

            Missao missao = criarNovaMissao(
                random,
                minX,
                maxX,
                minY,
                maxY,
                dificuldade
            );

            Nave nave = missao.getNave();

            int score =
                definirPontuacaoInicial(dificuldade);

            boolean running = true;

            while (running) {

                desenharMapa(
                    missao,
                    minX,
                    maxX,
                    minY,
                    maxY,
                    score,
                    pilotoNome
                );

                System.out.printf(
                    "Nave em (%d,%d) | Pontos: %d | Vidas: %d | Passageiros a bordo: %d | Passageiros restantes: %d%n",
                    nave.getX(),
                    nave.getY(),
                    score,
                    nave.getVidas(),
                    nave.getPassageiros().size(),
                    missao.todosEmbarcados()
                        ? 0
                        : missao.getPassageiros().size()
                );

                String line = lerLinha(
                    scanner,
                    "Para onde ir? ",
                    ""
                ).toLowerCase();

                if (line.isEmpty()) {

                    if (!scanner.hasNextLine()) {
                        running = false;
                        break;
                    }

                    continue;
                }

                char cmd = line.charAt(0);

                boolean jogadorSeMoveu = false;

                switch (cmd) {

                    case 'w':

                        if (nave.getY() > minY) {

                            nave.moveUp();
                            score--;
                            jogadorSeMoveu = true;

                        } else {

                            System.out.println(
                                "Você chegou ao limite do mapa."
                            );
                        }

                        break;

                    case 's':

                        if (nave.getY() < maxY) {

                            nave.moveDown();
                            score--;
                            jogadorSeMoveu = true;

                        } else {

                            System.out.println(
                                "Você chegou ao limite do mapa."
                            );
                        }

                        break;

                    case 'a':

                        if (nave.getX() > minX) {

                            nave.moveLeft();
                            score--;
                            jogadorSeMoveu = true;

                        } else {

                            System.out.println(
                                "Você chegou ao limite do mapa."
                            );
                        }

                        break;

                    case 'd':

                        if (nave.getX() < maxX) {

                            nave.moveRight();
                            score--;
                            jogadorSeMoveu = true;

                        } else {

                            System.out.println(
                                "Você chegou ao limite do mapa."
                            );
                        }

                        break;

                    case 'c': {

                        Passageiro p =
                            missao.passagemNaPosicao();

                        if (p == null) {

                            System.out.println(
                                "Nenhum passageiro nesta posição."
                            );

                        } else {

                            boolean ok =
                                missao.embarcarPassageiroNaPosicao();

                            if (ok) {

                                int bonus =
                                    p.getPontuacao();

                                score += bonus;

                                System.out.printf(
                                    "Passageiro embarcado (%s). +%d pontos!%n",
                                    p.getTipo(),
                                    bonus
                                );

                            } else {

                                System.out.println(
                                    "Nave cheia, não foi possível embarcar."
                                );
                            }
                        }

                        break;
                    }

                    case 'q':

                        running = false;
                        break;

                    default:

                        System.out.println(
                            "Comando desconhecido."
                        );

                        continue;
                }

                if (!running) {
                    break;
                }

                if (
                    jogadorSeMoveu &&
                    missao.verificaColisao()
                ) {

                    nave.perderVida();

                    if (nave.getVidas() > 0) {

                        System.out.printf(
                            "Você colidiu! Perdeu 1 vida. Vidas restantes: %d%n",
                            nave.getVidas()
                        );

                    } else {

                        System.out.printf(
                            "Você colidiu! Vidas restantes: %d%n",
                            nave.getVidas()
                        );

                        System.out.println(
                            "Suas vidas acabaram. GAME OVER!"
                        );

                        break;
                    }
                }

                missao.moverInimigos(
                    random,
                    minX,
                    maxX,
                    minY,
                    maxY
                );

                if (missao.verificaColisao()) {

                    nave.perderVida();

                    if (nave.getVidas() > 0) {

                        System.out.printf(
                            "Um inimigo atingiu sua nave! Você perdeu 1 vida. Vidas restantes: %d%n",
                            nave.getVidas()
                        );

                    } else {

                        System.out.printf(
                            "Um inimigo atingiu sua nave! Vidas restantes: %d%n",
                            nave.getVidas()
                        );

                        System.out.println(
                            "Suas vidas acabaram. GAME OVER!"
                        );

                        break;
                    }
                }

                if (score <= 0) {

                    System.out.println(
                        "Pontuação zerada. Missão perdida."
                    );

                    break;
                }

                if (missao.todosEmbarcados()) {

                    System.out.println(
                        "Todos os passageiros embarcados! Missão concluída com sucesso."
                    );

                    System.out.printf(
                        "Pontuação final: %d%n",
                        score
                    );

                    int passageirosColetados =
                        nave.getPassageiros().size();

                    String dataHora =
                        java.time.LocalDateTime
                            .now()
                            .toString();

                    if (
                        score > 0 &&
                        isTopScore(ranking, score)
                    ) {

                        ranking.add(
                            new RankingEntry(
                                pilotoNome,
                                score,
                                dificuldade,
                                passageirosColetados,
                                dataHora
                            )
                        );

                        ranking = ranking
                            .stream()
                            .sorted(
                                Comparator
                                    .comparingInt(
                                        (RankingEntry e) ->
                                            e.score
                                    )
                                    .reversed()
                            )
                            .limit(5)
                            .collect(
                                Collectors.toList()
                            );

                        saveRanking(
                            rankingPath,
                            ranking
                        );

                        System.out.println(
                            "Novo ranking salvo! Você está entre os 5 maiores pontuadores."
                        );
                    }

                    break;
                }
            }

            System.out.println();

            if (!ranking.isEmpty()) {

                System.out.println(
                    "Ranking Top 5:"
                );

                printRanking(ranking);

            } else {

                System.out.println(
                    "Ranking vazio. Seja o primeiro a marcar pontos!"
                );
            }

            String resposta = lerLinha(
                scanner,
                "Deseja iniciar nova missão? (s/n): ",
                "n"
            ).toLowerCase();

            if (
                resposta.equals("s") ||
                resposta.equals("sim")
            ) {

                System.out.println(
                    "Preparando nova missão..."
                );

            } else {

                playAgain = false;
            }
        }

        scanner.close();

        System.out.println(
            "Fim da execução."
        );
    }

    private static void printRanking(
        List<RankingEntry> ranking
    ) {

        int position = 1;

        for (RankingEntry entry : ranking) {

            System.out.printf(
                "%d. %s - %d pontos | dificuldade: %s | passageiros: %d | %s%n",
                position++,
                entry.name,
                entry.score,
                entry.dificuldade,
                entry.passageirosColetados,
                entry.dataHora
            );
        }
    }

    private static Missao criarNovaMissao(
        Random random,
        int minX,
        int maxX,
        int minY,
        int maxY,
        String dificuldade
    ) {

        Nave nave =
            new Nave(
                "A-1",
                5
            );

        Missao missao =
            new Missao(nave);

        int qtdPassageiros =
            Math.min(
                5,
                nave.getCapacidade()
            );

        int qtdAsteroides = 2;
        int qtdInimigos = 2;

        if (
            dificuldade.equals("facil")
        ) {

            qtdPassageiros =
                Math.min(
                    4,
                    nave.getCapacidade()
                );

            qtdAsteroides = 1;
            qtdInimigos = 1;

        } else if (
            dificuldade.equals("dificil")
        ) {

            qtdPassageiros =
                Math.min(
                    5,
                    nave.getCapacidade()
                );

            qtdAsteroides = 3;
            qtdInimigos = 3;
        }

        while (
            missao
                .getPassageiros()
                .size()
                < qtdPassageiros
        ) {

            int x =
                random.nextInt(
                    maxX - minX + 1
                ) + minX;

            int y =
                random.nextInt(
                    maxY - minY + 1
                ) + minY;

            if (
                x == nave.getX() &&
                y == nave.getY()
            ) {
                continue;
            }

            if (
                posicaoOcupada(
                    missao,
                    x,
                    y
                )
            ) {
                continue;
            }

            int indicePassageiro =
                missao
                    .getPassageiros()
                    .size();

            missao.addPassageiro(
                criarPassageiro(
                    indicePassageiro,
                    x,
                    y
                )
            );
        }

        while (
            missao
                .getAsteroides()
                .size()
                < qtdAsteroides
        ) {

            int x =
                random.nextInt(
                    maxX - minX + 1
                ) + minX;

            int y =
                random.nextInt(
                    maxY - minY + 1
                ) + minY;

            if (
                x == nave.getX() &&
                y == nave.getY()
            ) {
                continue;
            }

            if (
                posicaoOcupada(
                    missao,
                    x,
                    y
                )
            ) {
                continue;
            }

            missao.addAsteroide(
                new Asteroide(
                    x,
                    y
                )
            );
        }

        while (
            missao
                .getInimigos()
                .size()
                < qtdInimigos
        ) {

            int x =
                random.nextInt(
                    maxX - minX + 1
                ) + minX;

            int y =
                random.nextInt(
                    maxY - minY + 1
                ) + minY;

            if (
                x == nave.getX() &&
                y == nave.getY()
            ) {
                continue;
            }

            if (
                posicaoOcupada(
                    missao,
                    x,
                    y
                )
            ) {
                continue;
            }

            missao.addInimigo(
                new Inimigo(
                    x,
                    y
                )
            );
        }

        return missao;
    }

    private static Passageiro criarPassageiro(
        int indice,
        int x,
        int y
    ) {

        switch (indice % 5) {

            case 0:

                return new Professor(
                    "Dr. Silva",
                    x,
                    y
                );

            case 1:

                return new Engenheiro(
                    "Eng. Rosa",
                    x,
                    y
                );

            case 2:

                return new Professor(
                    "Dr. Lima",
                    x,
                    y
                );

            case 3:

                return new Engenheiro(
                    "Eng. Carlos",
                    x,
                    y
                );

            default:

                return new Astronauta(
                    "Ast. Maria",
                    x,
                    y
                );
        }
    }

    private static boolean posicaoOcupada(
        Missao missao,
        int x,
        int y
    ) {

        if (
            missao.getNave().getX() == x &&
            missao.getNave().getY() == y
        ) {
            return true;
        }

        for (
            Passageiro p :
            missao.getPassageiros()
        ) {

            if (
                p.getX() == x &&
                p.getY() == y
            ) {
                return true;
            }
        }

        for (
            Asteroide a :
            missao.getAsteroides()
        ) {

            if (
                a.getX() == x &&
                a.getY() == y
            ) {
                return true;
            }
        }

        for (
            Inimigo i :
            missao.getInimigos()
        ) {

            if (
                i.getX() == x &&
                i.getY() == y
            ) {
                return true;
            }
        }

        return false;
    }

    private static void desenharMapa(
        Missao missao,
        int minX,
        int maxX,
        int minY,
        int maxY,
        int score,
        String pilotoNome
    ) {

        System.out.println();

        System.out.printf(
            "Mapa da Missão (Pontos: %d) - Piloto: %s%n",
            score,
            pilotoNome
        );

        System.out.print("    ");

        for (
            int x = minX;
            x <= maxX;
            x++
        ) {

            System.out.printf(
                " %2d",
                x
            );
        }

        System.out.println();

        System.out.print("    ");

        for (
            int x = minX;
            x <= maxX;
            x++
        ) {

            System.out.print(" __");
        }

        System.out.println();

        for (
            int y = minY;
            y <= maxY;
            y++
        ) {

            System.out.printf(
                "%3d|",
                y
            );

            for (
                int x = minX;
                x <= maxX;
                x++
            ) {

                char symbol = '.';

                if (
                    missao
                        .getNave()
                        .getX() == x &&
                    missao
                        .getNave()
                        .getY() == y
                ) {

                    symbol = '@';

                } else {

                    for (
                        Passageiro p :
                        missao.getPassageiros()
                    ) {

                        if (
                            p.getX() == x &&
                            p.getY() == y
                        ) {

                            if (
                                p instanceof Engenheiro
                            ) {

                                symbol = 'E';

                            } else if (
                                p instanceof Astronauta
                            ) {

                                symbol = 'T';

                            } else {

                                symbol = 'P';
                            }

                            break;
                        }
                    }

                    if (symbol == '.') {

                        for (
                            Asteroide a :
                            missao.getAsteroides()
                        ) {

                            if (
                                a.getX() == x &&
                                a.getY() == y
                            ) {

                                symbol = '#';
                                break;
                            }
                        }
                    }

                    if (symbol == '.') {

                        for (
                            Inimigo i :
                            missao.getInimigos()
                        ) {

                            if (
                                i.getX() == x &&
                                i.getY() == y
                            ) {

                                symbol = 'X';
                                break;
                            }
                        }
                    }
                }

                System.out.printf(
                    " %2c",
                    symbol
                );
            }

            System.out.println();
        }

        System.out.println(
            "Legenda: @=Nave, P=Professor, E=Engenheiro, T=Astronauta, #=Asteroide, X=Inimigo, .=Vazio"
        );

        System.out.println(
            "Resumo de comandos: w(cima)/s(baixo)/a(esquerda)/d(direita) mover, c embarcar, q sair"
        );

        System.out.println(
            "Passageiros restantes:"
        );

        for (
            Passageiro p :
            missao.getPassageiros()
        ) {

            System.out.printf(
                " - %s (%s) em (%d,%d)%n",
                p.getNome(),
                p.getTipo(),
                p.getX(),
                p.getY()
            );
        }

        System.out.println();
    }

    private static String selecionarDificuldade(
        Scanner scanner
    ) {

        System.out.print(
            "Dificuldade (facil/medio/dificil): "
        );

        String dificuldade =
            lerLinha(
                scanner,
                "",
                "medio"
            ).toLowerCase();

        if (
            dificuldade.equals("facil") ||
            dificuldade.equals("medio") ||
            dificuldade.equals("dificil")
        ) {

            return dificuldade;
        }

        return "medio";
    }

    private static String lerLinha(
        Scanner scanner,
        String prompt,
        String fallback
    ) {

        if (
            prompt != null &&
            !prompt.isEmpty()
        ) {

            System.out.print(prompt);
        }

        if (scanner.hasNextLine()) {

            return scanner
                .nextLine()
                .trim();
        }

        return fallback;
    }

    private static int definirPontuacaoInicial(
        String dificuldade
    ) {

        switch (dificuldade) {

            case "facil":
                return 30;

            case "dificil":
                return 15;

            default:
                return 20;
        }
    }

    private static boolean isTopScore(
        List<RankingEntry> ranking,
        int score
    ) {

        if (ranking.size() < 5) {
            return true;
        }

        return score >
            ranking.get(
                ranking.size() - 1
            ).score;
    }

    private static List<RankingEntry> loadRanking(
        Path path
    ) {

        if (!Files.exists(path)) {

            return new ArrayList<>();
        }

        try {

            String json =
                new String(
                    Files.readAllBytes(path),
                    StandardCharsets.UTF_8
                ).trim();

            return parseRankingJson(json);

        } catch (IOException e) {

            return new ArrayList<>();
        }
    }

    private static void saveRanking(
        Path path,
        List<RankingEntry> ranking
    ) {

        StringBuilder builder =
            new StringBuilder();

        builder.append("[");

        for (
            int i = 0;
            i < ranking.size();
            i++
        ) {

            RankingEntry entry =
                ranking.get(i);

            builder
                .append("{\"name\":\"")
                .append(
                    entry.name.replace(
                        "\"",
                        "\\\""
                    )
                )
                .append("\",\"score\":")
                .append(entry.score)
                .append(",\"dificuldade\":\"")
                .append(entry.dificuldade)
                .append("\",\"passageirosColetados\":")
                .append(entry.passageirosColetados)
                .append(",\"dataHora\":\"")
                .append(
                    entry.dataHora.replace(
                        "\"",
                        "\\\""
                    )
                )
                .append("\"}");

            if (
                i < ranking.size() - 1
            ) {

                builder.append(",");
            }
        }

        builder.append("]");

        try {

            Files.write(
                path,
                builder
                    .toString()
                    .getBytes(
                        StandardCharsets.UTF_8
                    )
            );

        } catch (IOException e) {

            System.out.println(
                "Não foi possível salvar o ranking: "
                + e.getMessage()
            );
        }
    }

    private static List<RankingEntry> parseRankingJson(
        String json
    ) {

        List<RankingEntry> ranking =
            new ArrayList<>();

        if (
            json.isEmpty() ||
            json.equals("[]")
        ) {

            return ranking;
        }

        json = json.trim();

        if (json.startsWith("[")) {

            json = json.substring(1);
        }

        if (json.endsWith("]")) {

            json = json.substring(
                0,
                json.length() - 1
            );
        }

        int index = 0;

        while (
            index < json.length()
        ) {

            int start =
                json.indexOf(
                    '{',
                    index
                );

            if (start < 0) {
                break;
            }

            int end =
                json.indexOf(
                    '}',
                    start
                );

            if (end < 0) {
                break;
            }

            String object =
                json.substring(
                    start + 1,
                    end
                );

            String name = null;
            Integer score = null;
            String dificuldade = "medio";
            Integer passageirosColetados = 0;
            String dataHora = "";

            for (
                String part :
                object.split(",")
            ) {

                String[] pair =
                    part.split(
                        ":",
                        2
                    );

                if (
                    pair.length != 2
                ) {
                    continue;
                }

                String key =
                    pair[0]
                        .trim()
                        .replaceAll(
                            "\"",
                            ""
                        );

                String value =
                    pair[1].trim();

                if (
                    key.equals("name")
                ) {

                    if (
                        value.startsWith("\"") &&
                        value.endsWith("\"")
                    ) {

                        name =
                            value
                                .substring(
                                    1,
                                    value.length() - 1
                                )
                                .replace(
                                    "\\\"",
                                    "\""
                                );
                    }

                } else if (
                    key.equals("score")
                ) {

                    try {

                        score =
                            Integer.parseInt(
                                value
                            );

                    } catch (
                        NumberFormatException ignored
                    ) {
                    }

                } else if (
                    key.equals("dificuldade")
                ) {

                    if (
                        value.startsWith("\"") &&
                        value.endsWith("\"")
                    ) {

                        dificuldade =
                            value.substring(
                                1,
                                value.length() - 1
                            );
                    }

                } else if (
                    key.equals(
                        "passageirosColetados"
                    )
                ) {

                    try {

                        passageirosColetados =
                            Integer.parseInt(
                                value
                            );

                    } catch (
                        NumberFormatException ignored
                    ) {
                    }

                } else if (
                    key.equals("dataHora")
                ) {

                    if (
                        value.startsWith("\"") &&
                        value.endsWith("\"")
                    ) {

                        dataHora =
                            value
                                .substring(
                                    1,
                                    value.length() - 1
                                )
                                .replace(
                                    "\\\"",
                                    "\""
                                );
                    }
                }
            }

            if (
                name != null &&
                score != null
            ) {

                ranking.add(
                    new RankingEntry(
                        name,
                        score,
                        dificuldade,
                        passageirosColetados,
                        dataHora
                    )
                );
            }

            index = end + 1;
        }

        ranking.sort(
            Comparator
                .comparingInt(
                    (RankingEntry e) ->
                        e.score
                )
                .reversed()
        );

        return ranking;
    }

    private static class RankingEntry {

        private final String name;
        private final int score;
        private final String dificuldade;
        private final int passageirosColetados;
        private final String dataHora;

        private RankingEntry(
            String name,
            int score,
            String dificuldade,
            int passageirosColetados,
            String dataHora
        ) {

            this.name = name;
            this.score = score;
            this.dificuldade = dificuldade;
            this.passageirosColetados =
                passageirosColetados;
            this.dataHora = dataHora;
        }
    }
}