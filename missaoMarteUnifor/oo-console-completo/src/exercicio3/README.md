# Exercício 3: Mudar a Aparência do Mapa

## 📋 Enunciado

Modifique a aparência do mapa alterando os símbolos utilizados para representar os objetos do jogo no método `desenharMapa`:

- **Nave:** `N` → `@`
- **Asteroide:** `A` → `#`
- Os demais símbolos permanecem iguais: `P` (Professor), `E` (Engenheiro), `T` (Astronauta), `.` (Vazio)

## 🎯 Objetivo

Demonstrar a capacidade de **modificar características visuais** do programa sem alterar sua lógica principal.

## 🔧 Modificações Realizadas

### 1. Alteração do Símbolo da Nave

**Localização:** `Main.java` linha 254

```java
// Antes
if (missao.getNave().getX() == x && missao.getNave().getY() == y) {
    symbol = 'N';
}

// Depois
if (missao.getNave().getX() == x && missao.getNave().getY() == y) {
    symbol = '@';
}
```

### 2. Alteração do Símbolo do Asteroide

**Localização:** `Main.java` linha 271

```java
// Antes
if (a.getX() == x && a.getY() == y) {
    symbol = 'A';
    break;
}

// Depois
if (a.getX() == x && a.getY() == y) {
    symbol = '#';
    break;
}
```

### 3. Atualização da Legenda

**Localização:** `Main.java` linha 279

```java
// Antes
System.out.println("Legenda: N=Nave, P=Professor, E=Engenheiro, T=Astronauta, A=Asteroide, .=Vazio");

// Depois
System.out.println("Legenda: @=Nave, P=Professor, E=Engenheiro, T=Astronauta, #=Asteroide, .=Vazio");
```

### 4. Atualização do Pacote

O código foi movido para o pacote `exercicio3` para manter cada exercício isolado.

## 📁 Estrutura do Projeto

```
src/exercicio3/
├── Passageiro.java      (superclasse)
├── Professor.java       (herança)
├── Engenheiro.java      (herança)
├── Astronauta.java      (herança - do Exercício 2)
├── Nave.java            (capacidade = 5)
├── Asteroide.java
├── Missao.java
├── Main.java            (com novos símbolos @=Nave, #=Asteroide)
└── README.md
```

## 🎮 Exemplo Visual

### Mapa do Exercício 2 (símbolos antigos):
```
Mapa (Pontos: 20) - Piloto: Bezerra
     -5  -4  -3  -2  -1   0   1   2   3   4   5
     ___________________________________________
 -5 |   .   .   .   .   .   .   .   .   .   .   .
 -4 |   .   .   .   .   N   .   .   A   .   .   .
 -3 |   .   .   .   .   .   .   .   .   .   .   .
 -2 |   .   P   .   .   .   .   .   .   .   .   .
 -1 |   .   .   .   E   .   .   .   .   .   .   .
  0 |   .   .   .   .   .   .   .   .   .   .   .
  1 |   .   .   .   .   .   .   T   .   .   .   .
  2 |   .   .   .   .   .   .   .   .   A   .   .
  3 |   .   .   .   .   .   .   .   .   .   .   .
  4 |   .   .   .   .   .   .   .   .   .   .   .
  5 |   .   .   .   .   .   .   .   .   .   .   .

Legenda: N=Nave, P=Professor, E=Engenheiro, T=Astronauta, A=Asteroide, .=Vazio
```

### Mapa do Exercício 3 (novos símbolos):
```
Mapa (Pontos: 20) - Piloto: Bezerra
     -5  -4  -3  -2  -1   0   1   2   3   4   5
     ___________________________________________
 -5 |   .   .   .   .   .   .   .   .   .   .   .
 -4 |   .   .   .   .   @   .   .   #   .   .   .
 -3 |   .   .   .   .   .   .   .   .   .   .   .
 -2 |   .   P   .   .   .   .   .   .   .   .   .
 -1 |   .   .   .   E   .   .   .   .   .   .   .
  0 |   .   .   .   .   .   .   .   .   .   .   .
  1 |   .   .   .   .   .   .   T   .   .   .   .
  2 |   .   .   .   .   .   .   .   .   #   .   .
  3 |   .   .   .   .   .   .   .   .   .   .   .
  4 |   .   .   .   .   .   .   .   .   .   .   .
  5 |   .   .   .   .   .   .   .   .   .   .   .

Legenda: @=Nave, P=Professor, E=Engenheiro, T=Astronauta, #=Asteroide, .=Vazio
```

## 🔄 Comparação entre Exercícios

| Aspecto | Exercício 1 | Exercício 2 | Exercício 3 |
|---------|-------------|-------------|------------|
| Foco | Capacidade + 5 passageiros | Novo tipo (Astronauta) | **Aparência visual** |
| Nave | Capacidade = 5 | Capacidade = 5 | Capacidade = 5 |
| Símbolo Nave | `N` | `N` | **`@`** |
| Símbolo Asteroide | `A` | `A` | **`#`** |
| Passageiros | 5 | 5 | 5 |
| Tipos | Professor, Engenheiro, Astronauta | Professor, Engenheiro, Astronauta | Professor, Engenheiro, Astronauta |
| Classes | 8 (base + 7 específicas) | 9 (base + 8 específicas) | 9 (base + 8 específicas) |

## 📚 Conceitos de OO Aplicados

✅ **Encapsulamento:** Caracteres estão definidos dentro do método `desenharMapa`  
✅ **Polimorfismo:** Mesmo tratamento para todos os tipos de passageiros  
✅ **Composição:** Missão contém Nave, Passageiros e Asteroides  
✅ **Herança:** Diferentes tipos de passageiros herdam de `Passageiro`  
✅ **Reutilização:** Cópia do Exercício 2 com modificações mínimas  

## 🚀 Como Compilar e Executar

### Windows (PowerShell)

```powershell
# Compilar
javac -d out src/exercicio3/*.java

# Executar
java -cp out exercicio3.Main
```

### Linux/macOS

```bash
# Compilar
javac -d out src/exercicio3/*.java

# Executar
java -cp out exercicio3.Main
```

## ✅ Resultado Esperado

- ✓ Nave aparece como `@` no mapa
- ✓ Asteroides aparecem como `#` no mapa
- ✓ Legenda atualizada com novos símbolos
- ✓ Lógica do jogo permanece idêntica ao Exercício 2
- ✓ Compilação sem erros

---

**Disciplina:** Programação Orientada a Objetos
**Tutorial:** Missão Marte Unifor
**Nível:** Iniciante
**Conceitos Principais:** Modificação de Atributos, Interface Visual, Reutilização de Código
