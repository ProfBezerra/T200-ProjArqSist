Missão Marte Unifor — Aplicação Completa
=========================================

Este projeto é uma versão completa do jogo em console "Missão Marte Unifor".
Ele inclui todas as soluções dos exercícios práticos e demonstra conceitos de
Orientação a Objetos em Java.

## Visão geral

O jogo simula uma missão em Marte onde o piloto deve:

- mover a nave em um mapa bidimensional
- embarcar passageiros antes que o tempo acabe
- evitar colisão com asteroides
- gerir vidas e pontuação
- competir pelo ranking top 5 persistente em JSON

## Funcionalidades

- menu de dificuldade (Fácil, Normal, Difícil)
- sistema de vidas
- pontuação rendendo bônus por tipo de passageiro
- três tipos de passageiro: Professor, Engenheiro, Astronauta
- geração aleatória de passageiros e asteroides
- ranking persistente em `ranking.json`
- opção para iniciar nova missão sem sair do programa

## Estrutura do projeto

```
missaoMarteUnifor/oo-console-completo/
├── README.md
└── src/
    └── missao/
        ├── Asteroide.java
        ├── Astronauta.java
        ├── Dificuldade.java
        ├── Engenheiro.java
        ├── Main.java
        ├── Missao.java
        ├── Nave.java
        ├── Passageiro.java
        ├── Professor.java
        └── RankingEntry.java
```

## Requisitos

- Java JDK instalado (versão 8 ou superior)
- Terminal / prompt de comando

## Compilação

Execute a partir da raiz do repositório:

```bash
javac -d out missaoMarteUnifor/oo-console-completo/src/missao/*.java
```

No Windows PowerShell ou Prompt de Comando, você também pode usar:

```powershell
javac -d out missaoMarteUnifor\oo-console-completo\src\missao\*.java
```

## Execução

Após compilar, execute:

```bash
java -cp out missao.Main
```

No Windows:

```powershell
java -cp out missao.Main
```

## Como jogar

- `w` → mover para cima
- `s` → mover para baixo
- `a` → mover para a esquerda
- `d` → mover para a direita
- `c` → embarcar passageiro na posição atual
- `q` → sair do jogo

### Objetivo

Embarque todos os passageiros antes que a pontuação chegue a zero ou antes de perder todas as vidas.

### Símbolos do mapa

- `N` = Nave
- `P` = Professor
- `E` = Engenheiro
- `A` = Astronauta
- `#` = Asteroide
- `.` = Vazio

## Ranking

O jogo salva o ranking dos melhores pilotos em `ranking.json`.
O ranking exibe:

- nome do piloto
- pontuação
- dificuldade escolhida
- número de passageiros resgatados

## Melhorias possíveis

Algumas ideias para estender o jogo:

- adicionar inimigos móveis
- controlar o tamanho do mapa
- separar a lógica em mais classes
- adicionar testes unitários com JUnit
- usar uma biblioteca JSON em vez de parse manual

## Contato

Este projeto foi criado como exemplo educacional de Java e OO.
