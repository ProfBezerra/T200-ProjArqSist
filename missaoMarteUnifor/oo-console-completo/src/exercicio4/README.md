# Exercício 4: Adicionar Pontuação Bônus por Tipo

## 📋 Enunciado

Implemente um sistema de pontuação diferenciada para cada tipo de passageiro:

- **Professor:** +10 pontos
- **Engenheiro:** +15 pontos
- **Astronauta:** +20 pontos

## 🎯 Objetivo

Demonstrar o uso de **polimorfismo** através de `@Override` para implementar comportamentos específicos por tipo de passageiro.

## 🔧 Modificações Realizadas

### 1. Adicionar método `getPontuacao()` em `Passageiro.java`

**Localização:** Classe base `Passageiro.java`

```java
// Retorna a pontuação bônus ao embarcar este passageiro
public int getPontuacao() {
    return 10;  // Default: 10 pontos
}
```

**Características:**
- Método público que retorna `int`
- Valor padrão de 10 pontos
- Serve como método base para override em subclasses

### 2. Override em `Professor.java`

```java
@Override
public int getPontuacao() {
    return 10;  // Professor vale 10 pontos
}
```

### 3. Override em `Engenheiro.java`

```java
@Override
public int getPontuacao() {
    return 15;  // Engenheiro vale 15 pontos
}
```

### 4. Override em `Astronauta.java`

```java
@Override
public int getPontuacao() {
    return 20;  // Astronauta vale 20 pontos
}
```

### 5. Modificação em `Main.java` - Embarque com Bônus

**Antes:**
```java
if (ok) {
    score += 10;  // Sempre 10 pontos
    System.out.println("Passageiro embarcado. +10 pontos!");
}
```

**Depois:**
```java
if (ok) {
    int bonus = p.getPontuacao();  // Chama getPontuacao() do tipo específico
    score += bonus;
    System.out.printf("Passageiro embarcado (%s). +%d pontos!%n", p.getTipo(), bonus);
}
```

## 📁 Estrutura do Projeto

```
src/exercicio4/
├── Passageiro.java      (superclasse com getPontuacao() = 10)
├── Professor.java       (herança com @Override getPontuacao() = 10)
├── Engenheiro.java      (herança com @Override getPontuacao() = 15)
├── Astronauta.java      (herança com @Override getPontuacao() = 20)
├── Nave.java            (capacidade = 5)
├── Asteroide.java
├── Missao.java
├── Main.java            (com sistema de pontuação por tipo)
└── README.md
```

## 🎮 Exemplo de Gameplay

Ao embarcar diferentes passageiros, você verá mensagens específicas:

```
Passageiro embarcado (Professor). +10 pontos!
Passageiro embarcado (Engenheiro). +15 pontos!
Passageiro embarcado (Astronauta). +20 pontos!
```

## 🔄 Comparação entre Exercícios

| Aspecto | Ex. 1 | Ex. 2 | Ex. 3 | Ex. 4 |
|---------|-------|-------|-------|-------|
| Foco | Capacidade | Novo tipo | Aparência | **Pontuação** |
| Nave | Cap. = 5 | Cap. = 5 | Cap. = 5 | Cap. = 5 |
| Símbolos | N, A | N, A | @, # | @, # |
| Pontuação | +10 fixo | +10 fixo | +10 fixo | **Dinâmica** |
| Professor | +10 | +10 | +10 | **+10** |
| Engenheiro | +10 | +10 | +10 | **+15** |
| Astronauta | - | +10 | +10 | **+20** |
| Classes | 8 | 9 | 9 | 9 |

## 📚 Conceitos de OO Demonstrados

✅ **Herança:** Classes especializadas herdam de `Passageiro`  
✅ **Polimorfismo:** Diferentes tipos implementam `getPontuacao()` diferente  
✅ **@Override:** Anotação indicando método sobrescrito da superclasse  
✅ **Encapsulamento:** Lógica de pontuação encapsulada em cada classe  
✅ **Composição:** Missão contém diferentes tipos de passageiros  
✅ **Tratamento Uniforme:** Main.java trata todos como `Passageiro`  

## 🚀 Como Compilar e Executar

### Compilação
```bash
javac -d out src/exercicio4/*.java
```

### Execução
```bash
java -cp out exercicio4.Main
```

## ✅ Resultado Esperado

- ✓ Método `getPontuacao()` retorna valores diferentes por tipo
- ✓ Professor embarque dá +10 pontos
- ✓ Engenheiro embarque dá +15 pontos
- ✓ Astronauta embarque dá +20 pontos
- ✓ Mensagens mostram tipo e pontos específicos
- ✓ Compilação sem erros

## 💡 Extensibilidade

Este sistema é fácil de estender para novos tipos:

```java
public class Cientista extends Passageiro {
    public Cientista(String nome, int x, int y) {
        super(nome, "Cientista", x, y);
    }

    @Override
    public int getPontuacao() {
        return 25;  // Novos tipo com nova pontuação
    }
}
```

---

**Disciplina:** Programação Orientada a Objetos
**Tutorial:** Missão Marte Unifor
**Nível:** Intermediário
**Conceitos Principais:** Polimorfismo, Override, Métodos Virtuais
