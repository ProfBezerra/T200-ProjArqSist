# Tutorial SOLID a partir do Exercício 10

Este tutorial usa o exercício 10 da pasta [src/exercicio10](../exercicio10) como ponto de partida e mostra como converter esse projeto para uma estrutura mais alinhada aos princípios do SOLID.

## Objetivo

Transformar o projeto do mini-jogo em uma versão mais organizada, com separação de responsabilidades, extensibilidade e menor acoplamento.

## Fonte de partida

O projeto original está em [src/exercicio10](../exercicio10). Ele já tem:
- menu de jogo;
- ranking;
- enum de dificuldade;
- lógica de missão e renderização no console.

Esses elementos serão reorganizados em pacotes e classes com responsabilidades bem definidas.

## Estrutura proposta do projeto refatorado

```text
src/tutorial-exercicio10/
├── README.md
└── src/solidexercicio10/
    ├── Main.java
    ├── model/
    │   ├── Asteroide.java
    │   ├── Astronauta.java
    │   ├── Engenheiro.java
    │   ├── EntidadeMapa.java
    │   ├── Inimigo.java
    │   ├── Missao.java
    │   ├── Nave.java
    │   ├── Passageiro.java
    │   ├── Professor.java
    │   └── Dificuldade.java
    ├── presentation/
    │   └── MapaRenderer.java
    ├── repository/
    │   ├── RankingRepository.java
    │   └── RankingService.java
    └── service/
        ├── JogoService.java
        └── RankingServiceFacade.java
```

## Passo a passo da refatoração

### 1. SRP — Single Responsibility Principle

Problema no exercício 10:
- a classe Main concentra menu, gameplay, ranking, entrada e saída.

Refatoração:
- `JogoService` cuida do fluxo de jogo;
- `MapaRenderer` cuida da tela;
- `RankingService` cuida do armazenamento.

### 2. OCP — Open/Closed Principle

Problema:
- novos tipos de passageiros exigem mudanças espalhadas no código.

Refatoração:
- `Passageiro` vira uma abstração;
- `Professor`, `Engenheiro` e `Astronauta` implementam comportamentos específicos.

### 3. LSP — Liskov Substitution Principle

Problema:
- subclasses precisam ser substituíveis sem quebrar a lógica.

Refatoração:
- qualquer subtipo de `Passageiro` deve funcionar corretamente dentro de `Missao`.

### 4. ISP — Interface Segregation Principle

Problema:
- interfaces grandes podem obrigar classes a implementar métodos irrelevantes.

Refatoração:
- separar contratos menores como `Posicionavel` e `Movel`.

### 5. DIP — Dependency Inversion Principle

Problema:
- o fluxo do jogo depende diretamente de uma implementação concreta de persistência.

Refatoração:
- `JogoService` depende de `RankingRepository` e não de uma implementação específica.

## Como executar

```bash
mkdir out
javac -d out -sourcepath missaoMarteUnifor/solid/src/tutorial-exercicio10/src missaoMarteUnifor/solid/src/tutorial-exercicio10/src/solidexercicio10/Main.java
java -cp out solidexercicio10.Main
```
