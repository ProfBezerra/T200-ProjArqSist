# Exercício 9: Persistência do Ranking com Dados Extras

## 📋 Enunciado

Com base no exercício 8, melhore o sistema de persistência do ranking para salvar informações adicionais:

- nome do piloto
- pontuação final
- dificuldade escolhida
- quantidade de passageiros coletados
- data e hora da partida

## 🎯 Objetivo

Demonstrar como armazenar dados mais completos em um arquivo JSON e exibi-los posteriormente ao jogador.

## 🔧 Modificações Realizadas

### 1. Persistência ampliada do ranking

O jogo agora salva no arquivo JSON os campos:

```java
builder.append("{\"name\":\"")
        .append(entry.name.replace("\"", "\\\""))
        .append("\",\"score\":")
        .append(entry.score)
        .append(",\"dificuldade\":\"")
        .append(entry.dificuldade)
        .append("\",\"passageirosColetados\":")
        .append(entry.passageirosColetados)
        .append(",\"dataHora\":\"")
        .append(entry.dataHora.replace("\"", "\\\""))
        .append("\"}");
```

### 2. Leitura e exibição dos dados

Ao carregar o ranking, o programa lê os campos extras e exibe no topo da tela:

```java
System.out.printf("%d. %s - %d pontos | dificuldade: %s | passageiros: %d | %s%n",
        position++, entry.name, entry.score, entry.dificuldade, entry.passageirosColetados, entry.dataHora);
```

## 📁 Estrutura do Projeto

```text
src/exercicio9/
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

Ao final da missão, o jogo registra a partida com a dificuldade, o número de passageiros coletados e a data/hora em que ocorreu.

## 📚 Conceitos de OO Demonstrados

✅ Encapsulamento e organização do código
✅ Reuso da lógica do exercício 8
✅ Persistência em arquivo
✅ Leitura e escrita de dados estruturados

## 🚀 Como Compilar e Executar

### Compilação

```bash
javac -d out src/exercicio9/*.java
```

### Execução

```bash
java -cp out exercicio9.Main
```

## ✅ Resultado Esperado

- ✓ O ranking salva a dificuldade da partida
- ✓ O ranking registra a quantidade de passageiros coletados
- ✓ A data e hora da partida são armazenadas
- ✓ A compilação ocorre sem erros

---

**Disciplina:** Programação Orientada a Objetos
**Tutorial:** Missão Marte Unifor
**Nível:** Intermediário
**Conceitos Principais:** Persistência, JSON, Ranking, Dados Extras
