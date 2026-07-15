# Exercício 6: Mapa Expandível

## 📋 Enunciado

Implemente a possibilidade de escolher o tamanho do mapa antes de iniciar a missão:

- O jogador informa um valor inteiro para o tamanho do mapa.
- O mapa passa a variar de `-tamanho` até `+tamanho` em ambos os eixos.
- A missão é criada dentro desses limites.

## 🎯 Objetivo

Demonstrar o uso de entrada de usuário, parâmetros dinâmicos e adaptação da lógica do jogo ao tamanho do mapa.

## 🔧 Modificações Realizadas

### 1. Leitura do tamanho do mapa

No [src/exercicio6/Main.java](src/exercicio6/Main.java), o programa solicita ao usuário:

```java
System.out.print("Tamanho do mapa (-X a +X): ");
int tamanhoMapa;
try {
    tamanhoMapa = Integer.parseInt(scanner.nextLine().trim());
} catch (NumberFormatException e) {
    tamanhoMapa = 5;
}
if (tamanhoMapa < 1) {
    tamanhoMapa = 5;
}
```

### 2. Uso dinâmico dos limites do mapa

Os limites passam a ser definidos a partir do valor informado:

```java
int minX = -tamanhoMapa;
int maxX = tamanhoMapa;
int minY = -tamanhoMapa;
int maxY = tamanhoMapa;
```

### 3. Desenho do mapa com os novos limites

O mapa agora é renderizado com base nesses valores:

```java
desenharMapa(missao, minX, maxX, minY, maxY, score, pilotoNome);
```

### 4. Geração de objetos dentro do novo espaço

A criação de passageiros e asteroides usa os limites recebidos pela missão:

```java
int x = random.nextInt(maxX - minX + 1) + minX;
int y = random.nextInt(maxY - minY + 1) + minY;
```

## 📁 Estrutura do Projeto

```text
src/exercicio6/
├── Passageiro.java
├── Professor.java
├── Engenheiro.java
├── Astronauta.java
├── Nave.java
├── Asteroide.java
├── Missao.java
├── Main.java
└── README.md
```

## 🎮 Exemplo de Gameplay

Exemplo de entrada:

```text
Tamanho do mapa (-X a +X): 8
```

Isso faz com que o mapa seja desenhado de `-8` a `+8` em cada eixo.

## 📚 Conceitos de OO Demonstrados

✅ Entrada de usuário e validação
✅ Uso de parâmetros dinâmicos
✅ Reutilização da lógica do jogo com limites flexíveis
✅ Encapsulamento e composição mantidos da solução anterior

## 🚀 Como Compilar e Executar

### Compilação

```bash
javac -d out src/exercicio6/*.java
```

### Execução

```bash
java -cp out exercicio6.Main
```

## ✅ Resultado Esperado

- ✓ O jogador informa o tamanho do mapa
- ✓ O mapa passa a usar esse tamanho em todos os eixos
- ✓ Passengers e asteroides são gerados dentro do novo intervalo
- ✓ A compilação ocorre sem erros

---

**Disciplina:** Programação Orientada a Objetos
**Tutorial:** Missão Marte Unifor
**Nível:** Intermediário
**Conceitos Principais:** Entrada de Usuário, Parâmetros Dinâmicos, Flexibilidade do Mapa
