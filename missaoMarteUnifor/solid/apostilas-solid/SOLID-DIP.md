# Apostila – SOLID: DIP (Inversão de Dependência)

![DIP – Inversão de Dependência](../assets/solid/dip.svg)

**Objetivo:** Depender de abstrações em vez de implementações concretas para reduzir acoplamento.

## Conceito

DIP (Dependency Inversion Principle) afirma que módulos de alto nível não devem depender diretamente de módulos de baixo nível; ambos devem depender de abstrações.

Em prática, a regra de negócio deve depender de um contrato, e não de um detalhe de infraestrutura. Isso reduz acoplamento e permite trocar implementações sem mexer no comportamento principal da aplicação.

## Exemplo problemático

```java
public class JogoService {
    private final RankingService rankingService;

    public JogoService() {
        this.rankingService = new RankingService("ranking.json");
    }
}
```

Esse código acopla a lógica do jogo à implementação concreta de persistência. Se a forma de salvar ranking mudar, o serviço precisa ser alterado.

## Exemplo correto com DIP

```java
public class Main {
    public static void main(String[] args) {
        RankingRepository repository = new RankingService("ranking-solid-exercicio10.json");
        JogoService jogoService = new JogoService(repository);
    }
}

public class JogoService {
    private final RankingRepository rankingRepository;

    public JogoService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }
}
```

Agora o serviço não sabe nem se a persistência é em arquivo, banco ou memória. Ele só conhece o contrato `RankingRepository`.

## Importante sobre arquitetura

No tutorial, `RankingService` fica em `repository` porque é a implementação concreta da persistência. `JogoService` fica em `service` porque orquestra a lógica do jogo. Essa separação é uma aplicação clara de DIP: a regra de negócio depende da abstração, e a infraestrutura fica atrás do contrato.

## Por que isso importa?

O DIP ajuda a manter a regra de negócio estável mesmo quando a infraestrutura muda. Isso facilita:

- testes automatizados;
- troca de tecnologia de persistência;
- evolução incremental sem quebrar a lógica principal.

## Exercícios

1. Troque `RankingService` por outra implementação sem alterar `JogoService`.
2. Mostre que a regra de negócio do jogo não depende da forma como o ranking é armazenado.
3. Identifique qual parte do sistema corresponde a alto nível e qual corresponde a infraestrutura.

## Checklist

- O serviço depende de uma interface?
- A implementação pode ser trocada com pouco impacto?
- A regra de negócio continua estável mesmo quando a persistência muda?

## Como validar

- Alterar o mecanismo de persistência não deve exigir alterações na lógica do jogo.
- Isso mostra que a inversão de dependência foi aplicada corretamente.

## Referências

- Apostila OO (Interfaces, DIP)
- Projeto do tutorial: src/tutorial-exercicio10
