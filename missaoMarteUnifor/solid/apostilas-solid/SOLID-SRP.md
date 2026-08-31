# Apostila – SOLID: SRP (Responsabilidade Única)

![SRP – Responsabilidade Única](../assets/solid/srp.svg)

**Objetivo:** Garantir que cada classe tenha uma única responsabilidade, facilitando manutenção, testes e evolução.

## Conceito

SRP (Single Responsibility Principle) afirma que uma classe deve ter apenas um motivo para mudar.

Em termos práticos, isso significa que uma classe não deve misturar regra de negócio, apresentação e persistência em um único lugar. Se ela faz tudo ao mesmo tempo, qualquer mudança em qualquer parte do sistema pode exigir mexer nela.

## Exemplo no tutorial de Missão Marte

No projeto, a separação é clara:

- Main: inicia o sistema e monta as dependências.
- JogoService: coordena a partida, o menu e a lógica do jogo.
- MapaRenderer: desenha o mapa e a interface textual.
- RankingService: persiste as pontuações no arquivo JSON.

A estrutura correta é a seguinte:

```java
public class Main {
    public static void main(String[] args) {
        RankingRepository repository = new RankingService("ranking-solid-exercicio10.json");
        JogoService jogoService = new JogoService(repository);
        jogoService.executarLoop(new Scanner(System.in));
    }
}
```

```java
public class JogoService {
    private final RankingRepository rankingRepository;

    public JogoService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public void registrarPontuacao(String nome, int pontos) {
        rankingRepository.salvar(nome, pontos);
    }
}
```

```java
public class MapaRenderer {
    public void desenhar(Missao missao, int score, String nome, int minX, int maxX, int minY, int maxY) {
        // apenas desenha o estado do jogo
    }
}
```

```java
public class RankingService implements RankingRepository {
    @Override
    public void salvar(String nome, int pontuacao) {
        // apenas grava a pontuação
    }
}
```

Aqui, cada classe tem um motivo específico para mudar:

- o jogo muda por causa da regra de partida;
- o mapa muda por causa da interface visual;
- o ranking muda por causa da persistência.

## Anti-exemplo a evitar

Imagine uma classe chamada JogoService que também salva no arquivo, desenha o mapa e decide a lógica de pontuação:

```java
public class JogoService {
    public void executarLoop(Scanner scanner) {
        // regra do jogo
    }

    public void salvarRankingNoArquivo() {
        // persistência
    }

    public void desenharMapa() {
        // apresentação
    }
}
```

Esse código quebra SRP porque a classe tem três motivos para mudar:

- se a regra da missão mudar;
- se o jeito de mostrar o mapa trocar;
- se a persistência do ranking mudar.

## Importante sobre pacote e responsabilidade

Neste tutorial, `RankingService` não fica em `service`; ele fica em `repository` porque ele implementa a persistência e atua como adaptação da infraestrutura. Já `JogoService` fica em `service` por realizar a orquestração da partida. Essa separação deixa o desenho mais coerente e facilita a aplicação de DIP depois.

## Exercícios

1. Verifique se `JogoService` está responsável apenas pela lógica do jogo.
2. Verifique se `MapaRenderer` não decide regras de negócio.
3. Pergunte: o que mudaria se o ranking passasse a ser salvo em banco, em memória ou em arquivo JSON?

## Checklist

- A classe Main apenas inicia o sistema?
- JogoService cuida do fluxo da partida?
- MapaRenderer não decide regras de negócio?
- RankingService não desenha o mapa nem controla a missão?
- O pacote da classe reflete a sua responsabilidade real?

## Como validar

- Alterar a forma de mostrar o mapa não deve exigir mudar a regra do jogo.
- Trocar o mecanismo de persistência não deve exigir reescrever o fluxo principal.
- Se uma parte do código mudar por um motivo totalmente diferente, provavelmente há problema de responsabilidade.

## Referências

- Apostila OO (seções Encapsulamento e Composição)
- Projeto do tutorial: src/tutorial-exercicio10
