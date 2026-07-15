# Exercício 2: Adicionar um novo tipo de passageiro

## Enunciado

Crie uma classe `Astronauta` que herda de `Passageiro`.

**Passos:**

1. ✅ Crie `Astronauta.java` com:

```java
package exercicio2;

public class Astronauta extends Passageiro {
    public Astronauta(String nome, int x, int y) {
        super(nome, "Astronauta", x, y);
    }
}
```

2. ✅ Modifique `Main.java` para adicionar um astronauta à missão
3. ✅ Teste

## O que foi implementado

### 1. Classe `Astronauta.java`

Nova classe que herda de `Passageiro`, representando um tipo especializado de passageiro:

```java
public class Astronauta extends Passageiro {
    public Astronauta(String nome, int x, int y) {
        super(nome, "Astronauta", x, y);
    }
}
```

**Características:**

- Herda atributos: `nome`, `tipo`, `x`, `y`
- Tipo é fixado como "Astronauta"
- Construtor recebe nome e coordenadas

### 2. Modificação em `Main.java`

A missão agora adiciona um **Astronauta** entre os 5 passageiros:

```java
case 4:
    missao.addPassageiro(new Astronauta("Ast. Maria", x, y));
    break;
```

**Passageiros da missão:**

1. Dr. Silva (Professor)
2. Eng. Rosa (Engenheiro)
3. Dr. Lima (Professor)
4. Eng. Carlos (Engenheiro)
5. **Ast. Maria (Astronauta)** ⭐ *Novo tipo adicionado*

### 3. Visualização no mapa

- Símbolo: **'T'** (de Astronauta)
- Legenda: `N=Nave, P=Professor, E=Engenheiro, T=Astronauta, A=Asteroide, .=Vazio`

## Conceitos de OO Demonstrados

| Conceito                 | Descrição                                        |
| ------------------------ | -------------------------------------------------- |
| **Herança**       | `public class Astronauta extends Passageiro`     |
| **Polimorfismo**   | Lista de Passageiros contém tipos diferentes      |
| **instanceof**     | Verificação de tipo para desenho correto no mapa |
| **Encapsulamento** | Atributos privados, acesso via getters             |

## Estrutura de Classes

```
Passageiro (superclasse)
├── Professor
├── Engenheiro
└── Astronauta ⭐ (novo tipo!)
```

## Como Compilar e Executar

### Windows (PowerShell)

```powershell
# Compilar
javac -d out src/exercicio2/*.java

# Executar
java -cp out exercicio2.Main
```

### Linux/macOS

```bash
# Compilar
javac -d out src/exercicio2/*.java

# Executar
java -cp out exercicio2.Main
```

## Teste Funcional

Ao jogar, você verá:

```
Mapa da Missão (Pontos: 20) - Piloto: Seu Nome

    -5  -4  -3  -2  -1   0   1   2   3   4   5
    __  __  __  __  __  __  __  __  __  __  __
 -5| .   .   .   .   .   N   .   .   .   .   .
 -4| .   P   .   .   .   .   .   .   .   .   .
 -3| .   .   E   .   .   .   .   .   .   .   .
 -2| .   .   .   T   .   .   .   .   .   .   .
 -1| .   .   .   .   P   .   .   .   .   .   .
  0| .   .   .   .   .   .   .   .   .   .   .
  1| .   .   .   .   .   .   .   .   E   .   .
  2| .   .   .   .   .   .   .   .   .   A   .
  3| .   .   .   .   .   .   .   .   .   .   .
  4| .   .   .   .   .   .   .   .   .   .   A
  5| .   .   .   .   .   .   .   .   .   .   .

Legenda: N=Nave, P=Professor, E=Engenheiro, T=Astronauta, A=Asteroide, .=Vazio
Resumo de comandos: w(cima)/s(baixo)/a(esquerda)/d(direita) mover, c embarcar, q sair
Passageiros restantes:
 - Dr. Silva (Professor) em (-2,1)
 - Eng. Rosa (Engenheiro) em (3,-2)
 - Dr. Lima (Professor) em (1,2)
 - Eng. Carlos (Engenheiro) em (-3,-1)
 - Ast. Maria (Astronauta) em (-2,-1)  ⭐ Novo tipo!
```

## Resultado Esperado

✅ Classe `Astronauta` criada herdando de `Passageiro`
✅ Astronauta adicionado à lista de passageiros
✅ Astronauta aparece no mapa com símbolo 'T'
✅ Mensagens mostram tipo "Astronauta" quando embarcado
✅ Polimorfismo funcionando: todos os tipos de passageiros tratados uniformemente

## Comparação com Exercício 1

| Aspecto             | Exercício 1         | Exercício 2                    |
| ------------------- | -------------------- | ------------------------------- |
| Capacidade Nave     | 5                    | 5                               |
| Passageiros no mapa | 5 (4 tipos)          | **5 (inclui Astronauta)** |
| Novo tipo           | Capacidade aumentada | **Astronauta criado**     |
| Objetivo            | Modificar atributos  | **Adicionar novo tipo**   |

---

**Disciplina:** Programação Orientada a Objetos
**Tutorial:** Missão Marte Unifor
**Nível:** Iniciante
**Conceitos Principais:** Herança, Polimorfismo, Extensão de Classes
