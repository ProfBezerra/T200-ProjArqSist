# Exercício 8: Menu de Dificuldade com Inimigos

## 📋 Enunciado

Com base no exercício 7, adicione um menu de dificuldade ao jogo. O usuário deve escolher entre três níveis:

- Fácil: menos inimigos e mais pontos iniciais
- Médio: configuração equilibrada
- Difícil: mais inimigos, mais asteroides e menos pontos iniciais

## 🎯 Objetivo

Demonstrar como adaptar a missão de acordo com um nível de dificuldade, mantendo a lógica do exercício 7 com inimigos em movimento aleatório.

## 🔧 Modificações Realizadas

### 1. Menu de dificuldade

O jogo agora pergunta ao usuário qual dificuldade deseja jogar:

```java
System.out.print("Dificuldade (facil/medio/dificil): ");
String dificuldade = selecionarDificuldade(scanner);
```

### 2. Configuração da missão por dificuldade

A criação da missão passa a ajustar:

- quantidade de passageiros;
- quantidade de asteroides;
- quantidade de inimigos;
- pontuação inicial.

```java
int qtdPassageiros = 5;
int qtdAsteroides = 2;
int qtdInimigos = 2;

if (dificuldade.equals("facil")) {
    qtdPassageiros = 4;
    qtdAsteroides = 1;
    qtdInimigos = 1;
} else if (dificuldade.equals("dificil")) {
    qtdPassageiros = 6;
    qtdAsteroides = 3;
    qtdInimigos = 3;
}
```

### 3. Reuso da lógica do exercício 7

A nave continua enfrentando inimigos com movimento aleatório e colisões, mas agora com diferentes níveis de desafio.

## 📁 Estrutura do Projeto

```text
src/exercicio8/
├── Passageiro.java
├── Professor.java
├── Engenheiro.java
├── Astronauta.java
├── Nave.java
├── Asteroide.java
├── Inimigo.java
├── Missao.java
├── Main.java
└── README.md
```

## 🎮 Exemplo de Gameplay

O jogador escolhe a dificuldade antes da partida. Em níveis mais fáceis, há menos obstáculos; em níveis mais difíceis, o mapa fica mais desafiador e a pontuação inicial é menor.

## 📚 Conceitos de OO Demonstrados

✅ Encapsulamento e controle da missão
✅ Reuso da lógica do exercício 7
✅ Tomada de decisão por condição
✅ Adaptabilidade do jogo por dificuldade

## 🚀 Como Compilar e Executar

### Compilação

```bash
javac -d out src/exercicio8/*.java
```

### Execução

```bash
java -cp out exercicio8.Main
```

## ✅ Resultado Esperado

- ✓ O usuário escolhe a dificuldade
- ✓ A missão muda de acordo com o nível selecionado
- ✓ Inimigos continuam se movimentando e causando colisões
- ✓ A compilação ocorre sem erros

---

**Disciplina:** Programação Orientada a Objetos
**Tutorial:** Missão Marte Unifor
**Nível:** Intermediário
**Conceitos Principais:** Dificuldade, Condicionais, Reuso de Classes
