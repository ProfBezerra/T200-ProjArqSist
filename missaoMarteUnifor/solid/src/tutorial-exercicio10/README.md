# Atividade prática: Refatoração SOLID a partir do Exercício 10

Esta atividade propõe transformar um jogo em console em uma versão mais organizada, reutilizável e alinhada aos princípios do SOLID.

## Objetivo

O aluno deve refatorar o código do Exercício 10, criando uma estrutura em pacotes e aplicando os princípios do SOLID, mantendo o comportamento básico do jogo original.

## O que este tutorial oferece

Este material foi reorganizado para mostrar uma versão incremental e, ao mesmo tempo, a solução completa do projeto final.

- A primeira parte apresenta a estrutura passo a passo.
- A segunda parte mostra o código completo de cada arquivo.
- O projeto já está implementado na pasta de origem, então o aluno pode comparar a solução com o resultado final.

## Princípios do SOLID envolvidos

- SRP: cada classe tem uma responsabilidade clara.
- OCP: novas categorias de passageiros podem ser adicionadas sem reescrever a estrutura principal.
- LSP: diferentes tipos de passageiros podem ser tratados como Passageiro.
- ISP: interfaces pequenas deixam o código mais enxuto.
- DIP: o serviço depende de abstrações, não de implementações concretas.

## Como o tutorial explora o SOLID em cada etapa

Este tutorial não é apenas uma reorganização de pastas. A intenção é mostrar como a refatoração transforma um código funcional em um código mais sustentável, com responsabilidades bem separadas e com menor acoplamento.

- Etapa 1: a classe Main atua como ponto de entrada e composição, deixando o fluxo principal em uma camada de serviço.
- Etapa 2: JogoService orquestra a lógica do jogo, mas não conhece detalhes do armazenamento de dados.
- Etapa 3: MapaRenderer cuida apenas da apresentação do mapa, sem mexer na regra de negócio.
- Etapa 4: RankingRepository define uma abstração para persistência, permitindo trocar a implementação sem alterar o serviço.
- Etapa 5: o modelo encapsula entidades do domínio, com hierarquias e interfaces que favorecem extensão e substituição.

A ideia central é: cada classe deve ter um motivo para mudar e o código deve ser fácil de ampliar sem quebrar o comportamento existente.

## Estrutura final do projeto

```text
src/
  solidexercicio10/
    Main.java
    model/
      Asteroide.java
      Astronauta.java
      Dificuldade.java
      Engenheiro.java
      EntidadeMapa.java
      Inimigo.java
      Missao.java
      Movel.java
      Nave.java
      Passageiro.java
      Posicionavel.java
      Professor.java
    presentation/
      MapaRenderer.java
    repository/
      RankingEntry.java
      RankingRepository.java
      RankingService.java
    service/
      JogoService.java
```

## Dica para o tutorial: usar Javadoc

Ao longo do projeto, os alunos podem começar a documentar as classes e os métodos com comentários em Javadoc. Isso ajuda a deixar o código mais compreensível e prepara o estudante para boas práticas de programação.

Exemplo simples:

```java
/**
 * Ponto de entrada da aplicação.
 *
 * <p>Esta classe inicia o jogo e delega o fluxo para o serviço principal.</p>
 */
public class Main {
}
```

Nos comentários, vale destacar pontos importantes do SOLID, como:

- SRP: a classe tem uma responsabilidade bem definida.
- OCP: o código permite extensão sem alterar a estrutura principal.
- LSP: subclasses podem ser usadas no lugar da classe base.
- ISP: interfaces pequenas evitam dependências desnecessárias.
- DIP: o código depende de abstrações, não de implementações concretas.

## Passo 1: criar a classe principal

Neste primeiro passo, o foco é o conceito de arquitetura de composição e o princípio de responsabilidade. A classe Main não deve conter regras de negócio nem lógica do jogo; ela apenas inicia a aplicação e conecta as dependências necessárias.

- SRP: a classe Main tem uma responsabilidade bem definida: iniciar a aplicação.
- DIP: o código depende da abstração RankingRepository, e não da implementação concreta RankingService.
- O objetivo é manter o ponto de entrada simples e deixar a lógica em camadas mais especializadas.

Crie o arquivo Main.java com o conteúdo abaixo:

```java
package solidexercicio10;

import java.util.Scanner;
import solidexercicio10.repository.RankingRepository;
import solidexercicio10.repository.RankingService;
import solidexercicio10.service.JogoService;

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
```

### Reflexão do passo 1

- O que aconteceria se `Main` também fosse responsável por salvar o ranking?
- Por que a classe de entrada da aplicação deve ficar mais simples e delegar a execução?
- Como esse desenho ajuda o princípio de responsabilidade única?

## Passo 2: criar a camada de serviço

Aqui a refatoração deixa clara a separação entre regra de negócio e infraestrutura. O serviço centraliza a lógica do jogo, mas não decide como o ranking será salvo; isso fica abstraído por meio da interface RankingRepository.

- SRP: JogoService cuida do fluxo do jogo, do menu e da coordenação das regras.
- DIP: ele depende de uma abstração (RankingRepository), e não de uma classe concreta de persistência.
- O código fica mais fácil de testar e evoluir, porque a parte de armazenamento pode ser trocada sem mexer na lógica do jogo.

Crie o arquivo service/JogoService.java com o conteúdo abaixo:

```java
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
            mapaRenderer.desenhar(missao, score, pilotoNome, minX, maxX, minY, maxY);
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

        posicionarPassageiros(missao, qtdPassageiros, minX, maxX, minY, maxY, nave);

        posicionarEntidades(missao, qtdAsteroides, minX, maxX, minY, maxY, nave, true);
        posicionarEntidades(missao, qtdInimigos, minX, maxX, minY, maxY, nave, false);

        return missao;
    }

    private void posicionarPassageiros(Missao missao, int qtdPassageiros, int minX, int maxX, int minY, int maxY, Nave nave) {
        int indice = 0;
        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                if (missao.getPassageiros().size() >= qtdPassageiros) {
                    return;
                }
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
        }

        if (missao.getPassageiros().size() < qtdPassageiros) {
            System.out.printf("Aviso: o mapa atual não suporta todos os passageiros da dificuldade escolhida (%d/%d).%n",
                    missao.getPassageiros().size(), qtdPassageiros);
        }
    }

    private void posicionarEntidades(Missao missao, int qtd, int minX, int maxX, int minY, int maxY, Nave nave, boolean asteroide) {
        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                if (asteroide) {
                    if (missao.getAsteroides().size() >= qtd) {
                        return;
                    }
                } else {
                    if (missao.getInimigos().size() >= qtd) {
                        return;
                    }
                }
                if (x == nave.getX() && y == nave.getY()) {
                    continue;
                }
                if (posicaoOcupada(missao, x, y)) {
                    continue;
                }
                if (asteroide) {
                    missao.adicionarAsteroide(new Asteroide(x, y));
                } else {
                    missao.adicionarInimigo(new Inimigo(x, y));
                }
            }
        }
    }

    private boolean posicaoOcupada(Missao missao, int x, int y) {
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

    private void exibirRanking() {
        List<RankingEntry> ranking = rankingRepository.listar();
        if (ranking.isEmpty()) {
            System.out.println("Nenhum registro de ranking ainda.");
            return;
        }
        System.out.println("\n=== TOP 5 DO RANKING ===");
        for (int i = 0; i < Math.min(5, ranking.size()); i++) {
            RankingEntry entry = ranking.get(i);
            System.out.printf("%d. %s | Pontos: %d | Dif.: %s | Passageiros: %d | %s | Tempo: %ds%n",
                    i + 1, entry.name, entry.score, entry.dificuldade, entry.passageirosColetados, entry.dataHora, entry.tempoJogo);
        }
    }

    private void exibirEstatisticas(int score, int movimentos, long tempoJogoSegundos, int passageirosColetados) {
        System.out.println("\n=== ESTATÍSTICAS DA MISSÃO ===");
        System.out.printf("Pontuação final: %d%n", score);
        System.out.printf("Movimentos realizados: %d%n", movimentos);
        System.out.printf("Tempo de missão: %d segundos%n", tempoJogoSegundos);
        System.out.printf("Passageiros resgatados: %d%n", passageirosColetados);
    }

    private String lerLinha(Scanner scanner, String mensagem, String valorPadrao) {
        if (!mensagem.isEmpty()) {
            System.out.print(mensagem);
        }
        String entrada = scanner.nextLine();
        if (entrada == null || entrada.isBlank()) {
            return valorPadrao;
        }
        return entrada;
    }
}
```

### Reflexão do passo 2

- Por que o jogo não deve conhecer como o ranking é salvo em arquivo?
- Qual parte do código ficaria mais difícil de manter se `JogoService` tratasse de persistência diretamente?
- Como essa separação ajuda na testabilidade do sistema?

## Passo 3: criar a camada de apresentação

A ideia desta etapa é separar completamente a representação visual do jogo da lógica do domínio. O mapa é apenas uma forma de exibir o estado da missão; ele não deve saber como as regras de pontuação ou de embarque são calculadas.

- SRP: MapaRenderer tem responsabilidade exclusiva de renderizar o estado do jogo.
- DIP: a apresentação depende do modelo da missão, mas não altera a regra de negócio.
- Essa separação reduz acoplamento e facilita mudanças na interface, como trocar o console por uma UI gráfica em outro momento.

Crie o arquivo presentation/MapaRenderer.java com o conteúdo abaixo:

```java
package solidexercicio10.presentation;

import solidexercicio10.model.Asteroide;
import solidexercicio10.model.Inimigo;
import solidexercicio10.model.Missao;
import solidexercicio10.model.Passageiro;

public class MapaRenderer {
    public void desenhar(Missao missao) {
        desenhar(missao, 0, "Piloto", -2, 2, -2, 2);
    }

    public void desenhar(Missao missao, int score, String pilotoNome) {
        desenhar(missao, score, pilotoNome, -2, 2, -2, 2);
    }

    public void desenhar(Missao missao, int score, String pilotoNome, int minX, int maxX, int minY, int maxY) {
        System.out.println();
        System.out.printf("Mapa da Missão | Pontos: %d | Piloto: %s%n", score, pilotoNome);
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

        for (int y = maxY; y >= minY; y--) {
            System.out.printf("%3d|", y);
            for (int x = minX; x <= maxX; x++) {
                char symbol = '.';
                if (missao.getNave().getX() == x && missao.getNave().getY() == y) {
                    symbol = '@';
                } else {
                    for (Passageiro passageiro : missao.getPassageiros()) {
                        if (passageiro.getX() == x && passageiro.getY() == y) {
                            if (passageiro.getTipo().equals("Engenheiro")) {
                                symbol = 'E';
                            } else if (passageiro.getTipo().equals("Astronauta")) {
                                symbol = 'T';
                            } else {
                                symbol = 'P';
                            }
                            break;
                        }
                    }
                    if (symbol == '.') {
                        for (Asteroide asteroide : missao.getAsteroides()) {
                            if (asteroide.getX() == x && asteroide.getY() == y) {
                                symbol = '#';
                                break;
                            }
                        }
                    }
                    if (symbol == '.') {
                        for (Inimigo inimigo : missao.getInimigos()) {
                            if (inimigo.getX() == x && inimigo.getY() == y) {
                                symbol = 'X';
                                break;
                            }
                        }
                    }
                }
                System.out.printf(" %2c", symbol);
            }
            System.out.println();
        }

        System.out.println("Legenda: @=Nave, P=Professor, E=Engenheiro, T=Astronauta, #=Asteroide, X=Inimigo, .=Vazio");
        System.out.println("Comandos: w/s/a/d (mover), c (embarcar), q (sair)");
    }
}
```

### Reflexão do passo 3

- Por que a camada de apresentação não deve decidir regras de pontuação ou embarque?
- O que acontece se a interface do jogo for trocada para uma GUI em vez do terminal?
- Como a separação de responsabilidades facilita a manutenção da aplicação?

## Passo 4: criar a abstração do ranking

Este é um exemplo clássico de inverção de dependência. Em vez de o jogo depender diretamente de um arquivo ou de uma classe concreta de persistência, ele depende de uma interface que define o contrato do ranking.

- DIP: o serviço depende da abstração RankingRepository e não da implementação RankingService.
- SRP: a interface deixa explícita a responsabilidade de persistência, enquanto a implementação cuida do acesso ao arquivo.
- OCP: se no futuro o código usar banco de dados, API ou cache, basta criar outra implementação sem alterar a lógica do jogo.

Crie o arquivo repository/RankingRepository.java com o conteúdo abaixo:

```java
package solidexercicio10.repository;

import java.util.List;
import solidexercicio10.model.Dificuldade;

public interface RankingRepository {
    void salvar(String nome, int pontuacao);
    void salvar(String nome, int pontuacao, Dificuldade dificuldade, int passageirosColetados, long tempoJogo);
    List<RankingEntry> listar();
    void limpar();
}
```

Crie o arquivo repository/RankingService.java com o conteúdo abaixo:

```java
package solidexercicio10.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import solidexercicio10.model.Dificuldade;

public class RankingService implements RankingRepository {
    private final Path arquivo;

    public RankingService(String nomeArquivo) {
        this.arquivo = Paths.get(nomeArquivo);
    }

    @Override
    public void salvar(String nome, int pontuacao) {
        salvar(nome, pontuacao, Dificuldade.MEDIO, 0, 0);
    }

    @Override
    public void salvar(String nome, int pontuacao, Dificuldade dificuldade, int passageirosColetados, long tempoJogo) {
        List<String> linhas = new ArrayList<>();
        if (Files.exists(arquivo)) {
            try {
                linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
            } catch (IOException ignored) {
            }
        }
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        linhas.add(nome + "|" + pontuacao + "|" + dificuldade + "|" + passageirosColetados + "|" + dataHora + "|" + tempoJogo);
        try {
            Files.createDirectories(arquivo.getParent());
            Files.write(arquivo, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível salvar o ranking", e);
        }
    }

    @Override
    public List<RankingEntry> listar() {
        if (!Files.exists(arquivo)) {
            return new ArrayList<>();
        }
        try {
            List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
            List<RankingEntry> ranking = new ArrayList<>();
            for (String linha : linhas) {
                String[] partes = linha.split("\\|");
                if (partes.length >= 6) {
                    ranking.add(new RankingEntry(
                            partes[0],
                            Integer.parseInt(partes[1]),
                            Dificuldade.deString(partes[2]),
                            Integer.parseInt(partes[3]),
                            partes[4],
                            Long.parseLong(partes[5])
                    ));
                }
            }
            ranking.sort(Comparator.comparingInt((RankingEntry entry) -> entry.score).reversed());
            return ranking;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void limpar() {
        try {
            Files.deleteIfExists(arquivo);
        } catch (IOException ignored) {
        }
    }
}
```

Crie o arquivo repository/RankingEntry.java com o conteúdo abaixo:

```java
package solidexercicio10.repository;

import solidexercicio10.model.Dificuldade;

public class RankingEntry {
    public final String name;
    public final int score;
    public final Dificuldade dificuldade;
    public final int passageirosColetados;
    public final String dataHora;
    public final long tempoJogo;

    public RankingEntry(String name, int score, Dificuldade dificuldade, int passageirosColetados, String dataHora, long tempoJogo) {
        this.name = name;
        this.score = score;
        this.dificuldade = dificuldade;
        this.passageirosColetados = passageirosColetados;
        this.dataHora = dataHora;
        this.tempoJogo = tempoJogo;
    }
}
```

### Reflexão do passo 4

- Qual seria o impacto se o jogo dependesse diretamente de um arquivo em vez de uma interface?
- Por que a abstração do ranking aumenta a flexibilidade do sistema?
- Como isso exemplifica a inversão de dependência do SOLID?

## Passo 5: criar o modelo do domínio

Nesta etapa, o código passa a modelar corretamente o domínio do problema. As entidades representam objetos do jogo, como nave, passageiros, asteroides e inimigos, com responsabilidades bem definidas.

- SRP: cada classe do modelo representa uma entidade ou conceito específico do jogo.
- OCP: novas categorias de passageiros podem ser criadas herdando de Passageiro sem alterar as regras existentes.
- LSP: todo passageiro pode ser tratado como Passageiro, independentemente do tipo concreto.
- ISP: interfaces como Movel e Posicionavel deixam o código mais enxuto e mais coerente.

Crie o arquivo model/EntidadeMapa.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public abstract class EntidadeMapa implements Posicionavel {
    protected int x;
    protected int y;

    protected EntidadeMapa(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract String getSimbolo();
}
```

### Reflexão do passo 5

- Como a herança de `Passageiro` permite a criação de novos tipos sem mudar a lógica principal?
- Por que a interface `Movel` é melhor do que criar métodos específicos em cada classe?
- Qual parte do modelo mostra melhor o princípio de substituição de Liskov?

Crie o arquivo model/Posicionavel.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public interface Posicionavel {
    int getX();
    int getY();
}
```

Crie o arquivo model/Movel.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public interface Movel {
    void mover(int dx, int dy);
}
```

Crie o arquivo model/Dificuldade.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public enum Dificuldade {
    FACIL,
    MEDIO,
    DIFICIL;

    public static Dificuldade deString(String valor) {
        if (valor == null) return MEDIO;
        switch (valor.toLowerCase()) {
            case "facil": return FACIL;
            case "dificil": return DIFICIL;
            default: return MEDIO;
        }
    }
}
```

Crie o arquivo model/Passageiro.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public abstract class Passageiro extends EntidadeMapa {
    private final String nome;
    private final String tipo;

    protected Passageiro(String nome, String tipo, int x, int y) {
        super(x, y);
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public abstract int getPontuacao();
}
```

Crie os arquivos model/Professor.java, model/Engenheiro.java e model/Astronauta.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public class Professor extends Passageiro {
    public Professor(String nome, int x, int y) {
        super(nome, "Professor", x, y);
    }

    @Override
    public int getPontuacao() {
        return 15;
    }

    @Override
    public String getSimbolo() {
        return "P";
    }
}
```

```java
package solidexercicio10.model;

public class Engenheiro extends Passageiro {
    public Engenheiro(String nome, int x, int y) {
        super(nome, "Engenheiro", x, y);
    }

    @Override
    public int getPontuacao() {
        return 20;
    }

    @Override
    public String getSimbolo() {
        return "E";
    }
}
```

```java
package solidexercicio10.model;

public class Astronauta extends Passageiro {
    public Astronauta(String nome, int x, int y) {
        super(nome, "Astronauta", x, y);
    }

    @Override
    public int getPontuacao() {
        return 10;
    }

    @Override
    public String getSimbolo() {
        return "T";
    }
}
```

Crie o arquivo model/Asteroide.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public class Asteroide extends EntidadeMapa {
    public Asteroide(int x, int y) {
        super(x, y);
    }

    @Override
    public String getSimbolo() {
        return "A";
    }
}
```

Crie o arquivo model/Inimigo.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public class Inimigo extends EntidadeMapa implements Movel {
    public Inimigo(int x, int y) {
        super(x, y);
    }

    @Override
    public void mover(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public String getSimbolo() {
        return "X";
    }
}
```

Crie o arquivo model/Nave.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

import java.util.ArrayList;
import java.util.List;

public class Nave extends EntidadeMapa implements Movel {
    private final String nome;
    private final List<Passageiro> passageiros;
    private final int capacidade;
    private int vidas;

    public Nave(String nome, int x, int y) {
        this(nome, x, y, 3);
    }

    public Nave(String nome, int x, int y, int capacidade) {
        super(x, y);
        this.nome = nome;
        this.passageiros = new ArrayList<>();
        this.capacidade = capacidade;
        this.vidas = 3;
    }

    public String getNome() {
        return nome;
    }

    public void embarcar(Passageiro passageiro) {
        if (passageiros.size() < capacidade) {
            passageiros.add(passageiro);
        }
    }

    public void moverComLimites(char comando, int minX, int maxX, int minY, int maxY) {
        int dx = 0;
        int dy = 0;

        switch (comando) {
            case 'w' -> dy = 1;
            case 's' -> dy = -1;
            case 'a' -> dx = -1;
            case 'd' -> dx = 1;
        }

        int novoX = this.x + dx;
        int novoY = this.y + dy;
        if (novoX >= minX && novoX <= maxX && novoY >= minY && novoY <= maxY) {
            this.x = novoX;
            this.y = novoY;
        }
    }

    public void perderVida() {
        this.vidas = Math.max(0, this.vidas - 1);
    }

    public int getVidas() {
        return vidas;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    @Override
    public void mover(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public String getSimbolo() {
        return "N";
    }
}
```

Crie o arquivo model/Missao.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

import java.util.ArrayList;
import java.util.List;

public class Missao {
    private final Nave nave;
    private final List<Passageiro> passageiros;
    private final List<Asteroide> asteroides;
    private final List<Inimigo> inimigos;

    public Missao(Nave nave) {
        this.nave = nave;
        this.passageiros = new ArrayList<>();
        this.asteroides = new ArrayList<>();
        this.inimigos = new ArrayList<>();
    }

    public void adicionarPassageiro(Passageiro passageiro) {
        passageiros.add(passageiro);
    }

    public void adicionarAsteroide(Asteroide asteroide) {
        asteroides.add(asteroide);
    }

    public void adicionarInimigo(Inimigo inimigo) {
        inimigos.add(inimigo);
    }

    public Passageiro passagemNaPosicao() {
        for (Passageiro passageiro : passageiros) {
            if (passageiro.getX() == nave.getX() && passageiro.getY() == nave.getY()) {
                return passageiro;
            }
        }
        return null;
    }

    public boolean embarcarPassageiroNaPosicao() {
        Passageiro passageiro = passagemNaPosicao();
        if (passageiro == null || nave.getPassageiros().size() >= nave.getCapacidade()) {
            return false;
        }
        nave.embarcar(passageiro);
        passageiros.remove(passageiro);
        return true;
    }

    public void moverInimigos() {
        for (Inimigo inimigo : inimigos) {
            int dx = (int) (Math.random() * 3) - 1;
            int dy = (int) (Math.random() * 3) - 1;
            inimigo.mover(dx, dy);
        }
    }

    public boolean verificaColisao() {
        for (Asteroide asteroide : asteroides) {
            if (asteroide.getX() == nave.getX() && asteroide.getY() == nave.getY()) {
                return true;
            }
        }
        for (Inimigo inimigo : inimigos) {
            if (inimigo.getX() == nave.getX() && inimigo.getY() == nave.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean todosEmbarcados() {
        return passageiros.isEmpty();
    }

    public Nave getNave() {
        return nave;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    public List<Asteroide> getAsteroides() {
        return asteroides;
    }

    public List<Inimigo> getInimigos() {
        return inimigos;
    }
}
```

## Compilação e execução

Execute os comandos abaixo na raiz do projeto:

```bash
mkdir missaoMarteUnifor\solid\src\tutorial-exercicio10\out -Force | Out-Null
javac -d missaoMarteUnifor\solid\src\tutorial-exercicio10\out -sourcepath missaoMarteUnifor\solid\src\tutorial-exercicio10\src missaoMarteUnifor\solid\src\tutorial-exercicio10\src\solidexercicio10\Main.java
java -cp missaoMarteUnifor\solid\src\tutorial-exercicio10\out solidexercicio10.Main
```

## Reflexão final

- Qual princípio do SOLID você achou mais importante nesta atividade?
- Em que parte da refatoração você percebeu melhor a diferença entre um código acoplado e um código mais organizado?
- O que você aprendeu sobre manutenção e evolução do software?
- Se fosse adicionar uma nova funcionalidade, qual parte do código você alteraria com mais segurança?
