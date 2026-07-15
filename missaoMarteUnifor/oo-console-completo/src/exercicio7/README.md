# Exercício 7: Inimigos com Movimento Aleatório

## 📋 Enunciado

Adicione inimigos ao mapa que se movem aleatoriamente a cada turno e podem colidir com a nave.

- A missão passa a ter uma lista de inimigos.
- Cada inimigo se move de forma aleatória.
- Se a nave colidir com um inimigo, ela perde uma vida.
- O mapa exibe os inimigos com o símbolo `X`.

## 🎯 Objetivo

Demonstrar o uso de composição, objetos adicionais no domínio do jogo e lógica de atualização a cada turno.

## 🔧 Modificações Realizadas

### 1. Criação da classe `Inimigo`

A nova classe representa um inimigo com posição `(x, y)` e métodos para:

- verificar colisão com a nave;
- se mover aleatoriamente dentro dos limites do mapa.

```java
public class Inimigo {
    private int x;
    private int y;

    public boolean colideCom(Nave n) {
        return n.getX() == x && n.getY() == y;
    }
}
```

### 2. Integração na classe `Missao`

A missão passou a armazenar uma lista de inimigos e a oferecer um método para movê-los:

```java
private List<Inimigo> inimigos = new ArrayList<>();

public void moverInimigos(Random random, int minX, int maxX, int minY, int maxY) {
    for (Inimigo i : inimigos) {
        i.mover(random, minX, maxX, minY, maxY);
    }
}
```

### 3. Colisão com inimigos

A verificação de colisão passou a considerar tanto asteroides quanto inimigos:

```java
for (Inimigo i : inimigos) {
    if (i.colideCom(nave)) return true;
}
```

### 4. Exibição no mapa

No desenho do mapa, os inimigos são representados pelo símbolo `X`:

```java
if (symbol == '.') {
    for (Inimigo i : missao.getInimigos()) {
        if (i.getX() == x && i.getY() == y) {
            symbol = 'X';
            break;
        }
    }
}
```

## 📁 Estrutura do Projeto

```text
src/exercicio7/
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

A cada turno, os inimigos podem se mover para uma posição adjacente e, se chegarem à posição da nave, a missão registra uma colisão.

## 📚 Conceitos de OO Demonstrados

✅ Composição: a missão agora contém inimigos além de asteroides e passageiros
✅ Encapsulamento: cada entidade controla seu próprio estado
✅ Reuso: a lógica do jogo foi estendida sem reescrever tudo do zero
✅ Controle de fluxo: o jogo reage a cada atualização do turno

## 🚀 Como Compilar e Executar

### Compilação

```bash
javac -d out src/exercicio7/*.java
```

### Execução

```bash
java -cp out exercicio7.Main
```

## ✅ Resultado Esperado

- ✓ A missão cria inimigos no mapa
- ✓ Os inimigos se movem aleatoriamente a cada turno
- ✓ A nave perde vida ao colidir com um inimigo
- ✓ O mapa exibe os inimigos com o símbolo `X`
- ✓ A compilação ocorre sem erros

---

**Disciplina:** Programação Orientada a Objetos
**Tutorial:** Missão Marte Unifor
**Nível:** Intermediário
**Conceitos Principais:** Composição, Objetos Dinâmicos, Lógica de Turno
