# Apostila – SOLID: SRP (Responsabilidade Única)

![SRP – Responsabilidade Única](../assets/solid/srp.svg)

**Objetivo:** Garantir que cada classe tenha uma única responsabilidade, facilitando manutenção, testes e evolução.

## Conceito
SRP (Single Responsibility Principle) afirma que uma classe deve ter apenas um motivo para mudar.

## Exemplo (Missão Marte Unifor)
No tutorial, a ideia é separar bem as responsabilidades do projeto:
- Main inicializa o jogo e delega a execução.
- JogoService controla o fluxo da missão.
- MapaRenderer cuida da parte visual.
- RankingService salva as pontuações.

```java
public class Main {
    public static void main(String[] args) {
        RankingRepository repository = new RankingService("ranking.json");
        JogoService jogoService = new JogoService(repository);
        jogoService.executarLoop(new Scanner(System.in));
    }
}

public class JogoService {
    public void executarLoop(Scanner scanner) {
        // controla a lógica do jogo
    }
}

public class MapaRenderer {
    public void desenhar() {
        // apenas exibe o estado do mapa
    }
}
```

## Anti‑exemplo a evitar
- Colocar a lógica da missão, o desenho do mapa e o salvamento do ranking dentro da mesma classe.
- Deixar a classe Main responsável por tudo: entrada, regras do jogo e persistência.

## Exercícios
- Verifique se cada classe do tutorial tem uma responsabilidade bem definida.
- Se uma classe fizer mais de uma coisa, proponha uma divisão.

## Checklist
- A classe Main apenas inicia o sistema?
- JogoService cuida do fluxo da partida?
- MapaRenderer não decide regras de negócio?
- RankingService não desenha o mapa nem controla a missão?

## Como validar
- Alterar a forma de mostrar o mapa não deve exigir mudar a regra do jogo.
- Trocar o mecanismo de persistência não deve exigir reescrever o fluxo principal.

## Referências
- Apostila OO (seções Encapsulamento e Composição)
- Projeto do tutorial: src/tutorial-exercicio10
