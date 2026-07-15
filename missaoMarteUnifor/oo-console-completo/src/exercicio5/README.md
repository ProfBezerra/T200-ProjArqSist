# Exercício 5: Sistema de Vidas

## 📋 Enunciado

Implemente um sistema de vidas para a nave:

- A nave começa com 3 vidas.
- Cada colisão com um asteroide reduz 1 vida.
- Quando as vidas chegam a 0, o jogo termina com "GAME OVER".

## 🎯 Objetivo

Demonstrar o uso de atributos de estado, encapsulamento e controle de fluxo em um jogo simples.

## 🔧 Modificações Realizadas

### 1. Adicionar atributo `vidas` na classe `Nave`

**Localização:** [src/exercicio5/Nave.java](src/exercicio5/Nave.java)

```java
private int vidas;

public Nave(String id, int capacidade) {
    this.id = id;
    this.capacidade = capacidade;
    this.vidas = 3;
    this.x = 0;
    this.y = 0;
}
```

### 2. Implementar `perderVida()`

```java
public void perderVida() {
    if (vidas > 0) {
        vidas--;
    }
}
```

### 3. Exibir vidas durante o jogo

No [src/exercicio5/Main.java](src/exercicio5/Main.java), a interface mostra:

```java
System.out.printf("Nave em (%d,%d) | Pontos: %d | Vidas: %d | Passageiros a bordo: %d | Passageiros restantes: %d\n",
        nave.getX(), nave.getY(), score, nave.getVidas(), nave.getPassageiros().size(), missao.todosEmbarcados() ? 0 : missao.getPassageiros().size());
```

### 4. Reduzir vida ao colidir com asteroide

```java
if (missao.verificaColisao()) {
    nave.perderVida();
    if (nave.getVidas() > 0) {
        System.out.printf("Colisão com asteroide! Você perdeu 1 vida. Vidas restantes: %d%n", nave.getVidas());
    } else {
        System.out.println("Colisão com asteroide! Suas vidas acabaram. GAME OVER!");
        break;
    }
}
```

## 📁 Estrutura do Projeto

```text
src/exercicio5/
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

Ao colidir com um asteroide, a mensagem abaixo aparece:

```text
Colisão com asteroide! Você perdeu 1 vida. Vidas restantes: 2
```

Se a nave ficar sem vidas:

```text
Colisão com asteroide! Suas vidas acabaram. GAME OVER!
```

## 📚 Conceitos de OO Demonstrados

✅ Encapsulamento: o estado das vidas fica protegido dentro da classe `Nave`
✅ Composição: a missão continua usando a nave e seus objetos internos
✅ Controle de fluxo: o jogo termina quando `vidas == 0`
✅ Reutilização: a estrutura do exercício anterior foi mantida e expandida

## 🚀 Como Compilar e Executar

### Compilação

```bash
javac -d out src/exercicio5/*.java
```

### Execução

```bash
java -cp out exercicio5.Main
```

## ✅ Resultado Esperado

- ✓ A nave começa com 3 vidas
- ✓ Cada colisão reduz 1 vida
- ✓ O jogo mostra as vidas restantes
- ✓ O jogo termina com "GAME OVER" quando as vidas chegam a 0
- ✓ Compilação sem erros

---

**Disciplina:** Programação Orientada a Objetos
**Tutorial:** Missão Marte Unifor
**Nível:** Intermediário
**Conceitos Principais:** Encapsulamento, Estado do Objeto, Controle de Fluxo
