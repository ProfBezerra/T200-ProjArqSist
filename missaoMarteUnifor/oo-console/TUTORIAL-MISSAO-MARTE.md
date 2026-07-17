# Tutorial: Desenvolvimento do Jogo "Missão Marte Unifor" em Java

**Disciplina:** Programação Orientada a Objetos (OO)
**Nível:** Iniciante/Intermediário
**Duração recomendada:** 6-8 horas (pode ser dividido em 3-4 aulas)
**Objetivo:** Aprender conceitos de OO através da implementação de um jogo interativo em console

---

## 📋 Índice

1. [Introdução](#introdução)
2. [Conceitos Fundamentais](#conceitos-fundamentais)
3. [Estrutura do Projeto](#estrutura-do-projeto)
4. [Aula 1: Classes de Domínio](#aula-1-classes-de-domínio)
5. [Aula 2: Herança e Polimorfismo](#aula-2-herança-e-polimorfismo)
6. [Aula 3: Composição e Agregação](#aula-3-composição-e-agregação)
7. [Aula 4: Lógica do Jogo e Interface](#aula-4-lógica-do-jogo-e-interface)
8. [Exercícios Práticos](#exercícios-práticos)
9. [Referências e Aprofundamento](#referências-e-aprofundamento)

---

## Introdução

### O que é o ambiente Java?

Java é uma plataforma de desenvolvimento que permite escrever programas que rodam em muitas máquinas diferentes. O ecossistema Java é composto por duas partes principais:

- **JDK (Java Development Kit)**: conjunto de ferramentas para desenvolver em Java, incluindo o compilador `javac`.
- **JRE (Java Runtime Environment)**: ambiente que executa programas Java, incluindo a Máquina Virtual Java (JVM).

### JDK vs JRE

- **JDK** é para programadores.

  - Contém o compilador `javac`.
  - Contém a `java` para executar programas.
  - Contém bibliotecas e ferramentas de desenvolvimento.
- **JRE** é para execução.

  - Contém a JVM e bibliotecas de runtime.
  - Não contém o compilador.

### O que é `javac`?

`javac` é o compilador Java.

- Ele transforma código-fonte `.java` em bytecode `.class`.
- Bytecode é um formato intermediário que a JVM entende.
- Exemplo de uso:

```bash
javac missao/ Main.java
```

Isso gera arquivos `.class` que podem ser executados com `java`.

### O que é `java`?

`java` é o comando que inicia a JVM e executa o bytecode Java.

- Ele carrega as classes compiladas.
- Ele executa a aplicação.
- Exemplo:

```bash
java missao.Main
```

### Como funciona o ciclo Java

1. Escrevo código em arquivos `.java`
2. Compilo com `javac`
3. Gero arquivos `.class`
4. Executo com `java`

### Exemplo de compilação e execução

No terminal:

```bash
javac -d out missaoMarteUnifor/oo-console/src/missao/*.java
java -cp out missao.Main
```

---

### O que vamos construir?

Um jogo interativo em console chamado **Missão Marte Unifor** onde:

- O jogador controla uma **nave** em um mapa bidimensional
- Deve **coletar passageiros** (Professor, Engenheiro)
- Deve **evitar asteroides**
- Cada movimento custa **1 ponto**, cada embarque rende **+10 pontos**
- O programa salva um **ranking** em arquivo JSON
- Permite **replayability** (jogar múltiplas missões)

### Por que este projeto?

Este projeto é ideal para aprender OO porque:

✅ Envolve **múltiplas classes** com responsabilidades claras
✅ Demonstra **herança** (Professor e Engenheiro herdam de Passageiro)
✅ Explora **composição** (Missão contém Nave, Passageiros e Asteroides)
✅ Pratica **encapsulamento** (dados privados, acesso via métodos)
✅ Usa **polimorfismo** (diferentes tipos de passageiros)
✅ Aplica **persistência** (salva dados em arquivo)

---

## Conceitos Fundamentais

### 1. O que é Orientação a Objetos?

OO organiza código em **objetos** que representam entidades do mundo real.

**Exemplo:**

- Nave é um objeto com posição `(x, y)` e capacidade
- Passageiro é um objeto com nome e tipo
- Asteroide é um objeto com posição

### 2. Pilares da OO

#### 📦 Encapsulamento

Esconder detalhes internos do objeto, expor apenas o necessário.

```java
public class Nave {
    private int x;  // Privado: só a classe pode acessar
    private int y;
  
    public int getX() {  // Público: qualquer um pode chamar
        return x;
    }
}
```

#### 🧬 Herança

Uma classe "especializada" herda de uma classe "base".

```java
public class Professor extends Passageiro {
    // Professor herda nome, tipo, x, y de Passageiro
}
```

#### 🔄 Polimorfismo

Objetos diferentes respondem a mesma mensagem de formas diferentes.

```java
// Ambos são Passageiro, mas tipos diferentes
Passageiro prof = new Professor("Dr. Silva", 1, 2);
Passageiro eng = new Engenheiro("Eng. Rosa", 3, 4);
```

#### 🧩 Composição

Um objeto é feito de outros objetos.

```java
public class Missao {
    private Nave nave;
    private List<Passageiro> passageiros;  // Missão é feita de passageiros
}
```

---

## Estrutura do Projeto

### Diagrama de Classes

```
Passageiro
├── Professor
└── Engenheiro

Nave
Asteroide
Missao (contém Nave, List<Passageiro>, List<Asteroide>)
Main (orquestra o jogo)
```

### Layout de Arquivos

```
missaoMarteUnifor/
├── oo-console/
│   ├── src/
│   │   └── missao/
│   │       ├── Main.java
│   │       ├── Missao.java
│   │       ├── Nave.java
│   │       ├── Passageiro.java
│   │       ├── Professor.java
│   │       ├── Engenheiro.java
│   │       └── Asteroide.java
│   ├── ranking.json  (criado em runtime)
│   └── TUTORIAL-MISSAO-MARTE.md (este arquivo)
```

---

# Aula 1: Classes de Domínio

**Duração:** ~1 hora
**Objetivo:** Entender como modelar entidades do jogo como classes Java

## 1.1 Conceito: O que é uma Classe?

Uma classe é um **molde** para criar objetos.

```
Classe Nave  →  Objeto nave1 (instância)
             →  Objeto nave2 (instância)
             →  Objeto nave3 (instância)
```

Cada objeto tem seus próprios atributos, mas a estrutura é a mesma.

## 1.2 Primeira Classe: `Passageiro`

### Especificação

- **Responsabilidade:** Representar um passageiro a ser resgatado
- **Atributos:** nome, tipo, posição (x, y)
- **Métodos:** getters para acessar os atributos

### Implementação

```java
package missao;

public class Passageiro {
    // Atributos privados (encapsulamento)
    private String nome;
    private String tipo;
    private int x;
    private int y;

    // Construtor: inicializa um novo passageiro
    public Passageiro(String nome, String tipo, int x, int y) {
        this.nome = nome;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
    }

    // Getters: permitem acesso de leitura aos atributos
    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
```

### Explicação

1. **`package missao;`** → agrupa as classes num namespace
2. **`private`** → atributos são privados (encapsulamento)
3. **Construtor** → especial, chamado com `new`
4. **`this.nome = nome;`** → atributo = parâmetro
5. **Getters** → métodos públicos que retornam valores

### Como usar

```java
// Criar um passageiro
Passageiro prof = new Passageiro("Dr. Silva", "Professor", 2, 3);

// Acessar dados
String nome = prof.getNome();    // "Dr. Silva"
int posX = prof.getX();          // 2
```

## 1.3 Segunda Classe: `Asteroide`

### Especificação

- **Responsabilidade:** Representar um obstáculo no mapa
- **Atributos:** posição (x, y)
- **Métodos:** getters + verificar colisão com nave

### Implementação

```java
package missao;

public class Asteroide {
    private int x;
    private int y;

    public Asteroide(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Verifica se este asteroide colidiu com a nave
    public boolean colideCom(Nave n) {
        return n.getX() == x && n.getY() == y;
    }
}
```

### Novo conceito: Métodos que retornam `boolean`

```java
public boolean colideCom(Nave n) {
    return n.getX() == x && n.getY() == y;
}
```

- Recebe um objeto `Nave` como parâmetro
- Compara posição do asteroide com posição da nave
- Retorna `true` se colidiu, `false` caso contrário

## 1.4 Terceira Classe: `Nave`

### Especificação

- **Responsabilidade:** Representar a nave do jogador
- **Atributos:** id, posição (x, y), capacidade, lista de passageiros
- **Métodos:** getters + movimento + embarque

### Implementação

```java
package missao;

import java.util.ArrayList;
import java.util.List;

public class Nave {
    private String id;
    private int x;
    private int y;
    private int capacidade;
    private List<Passageiro> passageiros = new ArrayList<>();

    // Construtor
    public Nave(String id, int capacidade) {
        this.id = id;
        this.capacidade = capacidade;
        this.x = 0;   // Inicia no centro do mapa
        this.y = 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    // Métodos de movimento
    public void moveUp() {
        y--;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    // Método para embarcar passageiro
    public boolean embarcar(Passageiro p) {
        if (passageiros.size() < capacidade) {
            passageiros.add(p);
            return true;  // Sucesso
        }
        return false;  // Nave cheia
    }
}
```

### Novos conceitos

1. **`List<Passageiro>`** → lista dinâmica de passageiros
2. **`ArrayList`** → implementação de List
3. **`passageiros = new ArrayList<>()`** → inicializa a lista vazia
4. **`passageiros.add(p)`** → adiciona elemento à lista
5. **`passageiros.size()`** → retorna quantidade de elementos

## ✏️ Exercício 1.1: Criar classe `Cachorro`

Crie uma classe `Cachorro` com:

- Atributos: `nome`, `raca`, `idade`
- Construtor que inicializa os três atributos
- Getters para os três atributos
- Método `falar()` que retorna uma String com "Au au!"

**Solução esperada:**

```java
public class Cachorro {
    private String nome;
    private String raca;
    private int idade;

    public Cachorro(String nome, String raca, int idade) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
    }

    public String getNome() { return nome; }
    public String getRaca() { return raca; }
    public int getIdade() { return idade; }

    public String falar() {
        return "Au au! Meu nome é " + nome;
    }
}
```

---

# Aula 2: Herança e Polimorfismo

**Duração:** ~1 hora
**Objetivo:** Entender como especializar classes através de herança

## 2.1 Conceito: Por que Herança?

Sem herança, teríamos que criar duas classes muito semelhantes:

```java
// ❌ Sem herança: código repetido
public class Professor {
    private String nome;
    private int x;
    private int y;
    // ... getters ...
}

public class Engenheiro {
    private String nome;
    private int x;
    private int y;
    // ... getters ... (código idêntico!)
}
```

Com herança:

```java
// ✅ Com herança: código reutilizado
public class Professor extends Passageiro {
    public Professor(String nome, int x, int y) {
        super(nome, "Professor", x, y);
    }
}

public class Engenheiro extends Passageiro {
    public Engenheiro(String nome, int x, int y) {
        super(nome, "Engenheiro", x, y);
    }
}
```

## 2.2 Classe Derivada: `Professor`

```java
package missao;

public class Professor extends Passageiro {
    // Herda: nome, tipo, x, y de Passageiro
  
    public Professor(String nome, int x, int y) {
        // super() chama o construtor da classe pai
        super(nome, "Professor", x, y);
    }
}
```

### Explicação

- **`extends Passageiro`** → Professor é um tipo especializado de Passageiro
- **`super(...)`** → chama o construtor da classe pai
- **Herança** → Professor herda todos os métodos e atributos de Passageiro

### Uso

```java
Professor prof = new Professor("Dr. Silva", 2, 3);
System.out.println(prof.getNome());   // "Dr. Silva" (do Passageiro)
System.out.println(prof.getTipo());   // "Professor"
```

## 2.3 Classe Derivada: `Engenheiro`

```java
package missao;

public class Engenheiro extends Passageiro {
    public Engenheiro(String nome, int x, int y) {
        super(nome, "Engenheiro", x, y);
    }
}
```

Segue o mesmo padrão de Professor.

## 2.4 Conceito: Polimorfismo

Objetos diferentes podem ser tratados de forma uniforme:

```java
// Criar diferentes tipos de passageiro
Passageiro p1 = new Professor("Dr. Silva", 1, 2);
Passageiro p2 = new Engenheiro("Eng. Rosa", 3, 4);

// Ambos são Passageiro e respondem a métodos herdados
System.out.println(p1.getNome());  // "Dr. Silva"
System.out.println(p2.getNome());  // "Eng. Rosa"

// Mas sabemos o tipo de cada um
if (p1 instanceof Professor) {
    System.out.println("É um professor!");
}

if (p2 instanceof Engenheiro) {
    System.out.println("É um engenheiro!");
}
```

### `instanceof`

Verifica se um objeto é instância de uma classe.

```java
Object obj = new Professor("Dr. Silva", 1, 2);

if (obj instanceof Professor) {
    System.out.println("É Professor");  // Será impresso
}

if (obj instanceof Engenheiro) {
    System.out.println("É Engenheiro");  // NÃO será impresso
}
```

## ✏️ Exercício 2.1: Estender `Cachorro`

Crie duas classes que herdam de `Cachorro`:

- `CachorroGrande` (com método `late()` retornando "AUUUUU!")
- `CachorroPequeno` (com método `late()` retornando "au...")

Crie uma lista mista de cachorros e faça cada um latir polimorficamente.

---

# Aula 3: Composição e Agregação

**Duração:** ~1.5 horas
**Objetivo:** Entender como combinar objetos em estruturas maiores

## 3.1 Conceito: Composição

Uma classe é **feita de** outras classes:

```
Missao é feita de:
  - 1 Nave
  - Múltiplos Passageiro
  - Múltiplos Asteroide
```

## 3.2 Classe `Missao`

### Especificação

- **Responsabilidade:** Orquestrar nave, passageiros e asteroides
- **Atributos:** nave, lista de passageiros, lista de asteroides
- **Métodos:** adicionar passageiro/asteroide, verificar colisão, embarcar, checar conclusão

### Implementação

```java
package missao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Missao {
    private Nave nave;
    private List<Passageiro> passageiros = new ArrayList<>();
    private List<Asteroide> asteroides = new ArrayList<>();

    // Construtor recebe uma nave já criada
    public Missao(Nave nave) {
        this.nave = nave;
    }

    // Getters
    public Nave getNave() {
        return nave;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    public List<Asteroide> getAsteroides() {
        return asteroides;
    }

    // Adicionar passageiro à missão
    public void addPassageiro(Passageiro p) {
        passageiros.add(p);
    }

    // Adicionar asteroide à missão
    public void addAsteroide(Asteroide a) {
        asteroides.add(a);
    }

    // Verificar se nave bateu em asteroide
    public boolean verificaColisao() {
        for (Asteroide a : asteroides) {
            if (a.colideCom(nave)) {
                return true;  // Houve colisão
            }
        }
        return false;  // Sem colisão
    }

    // Verificar se há passageiro na posição da nave
    public Passageiro passagemNaPosicao() {
        for (Passageiro p : passageiros) {
            if (p.getX() == nave.getX() && p.getY() == nave.getY()) {
                return p;  // Encontrou passageiro
            }
        }
        return null;  // Sem passageiro
    }

    // Embarcar passageiro na posição da nave
    public boolean embarcarPassageiroNaPosicao() {
        // Usar Iterator para poder remover durante iteração
        Iterator<Passageiro> it = passageiros.iterator();
        while (it.hasNext()) {
            Passageiro p = it.next();
            if (p.getX() == nave.getX() && p.getY() == nave.getY()) {
                boolean ok = nave.embarcar(p);
                if (ok) {
                    it.remove();  // Remove de passageiros a coletar
                }
                return ok;
            }
        }
        return false;
    }

    // Verificar se todos os passageiros foram embarcados
    public boolean todosEmbarcados() {
        return passageiros.isEmpty();
    }
}
```

### Novos conceitos

1. **`List<Asteroide> asteroides = new ArrayList<>()`**

   - Cria uma lista vazia de asteroides
2. **`for (Asteroide a : asteroides)`**

   - Loop "for-each" que percorre cada elemento da lista
3. **`Iterator<Passageiro> it = passageiros.iterator()`**

   - Permite iterar E remover elementos simultaneamente
   - Usar `it.remove()` é seguro dentro de um while com Iterator
4. **Delegação**

   - `Missao` pede à `Nave` para embarcar: `nave.embarcar(p)`
   - Cada classe faz sua parte

## 3.3 Usando Composição

```java
// Criar uma nave
Nave nave = new Nave("A-1", 3);

// Criar uma missão com essa nave
Missao missao = new Missao(nave);

// Adicionar passageiros
missao.addPassageiro(new Professor("Dr. Silva", 1, 2));
missao.addPassageiro(new Engenheiro("Eng. Rosa", 3, 4));

// Adicionar asteroides
missao.addAsteroide(new Asteroide(5, 5));
missao.addAsteroide(new Asteroide(-2, -3));

// Mover nave
nave.moveRight();
nave.moveUp();

// Verificar se há passageiro na posição
Passageiro p = missao.passagemNaPosicao();
if (p != null) {
    System.out.println("Encontrou: " + p.getNome());
}
```

## ✏️ Exercício 3.1: Classe `Time`

Crie uma classe `Time` que:

- Tem `nome` (String) e `jogadores` (List<Jogador></jogador>)
- Tem método `adicionarJogador(Jogador j)`
- Tem método `listarJogadores()` que imprime todos
- Tem método `totalGols()` que soma gols de todos os jogadores

Crie uma classe `Jogador` com `nome` e `gols` (int).

---

# Aula 4: Lógica do Jogo e Interface

**Duração:** ~2-3 horas
**Objetivo:** Implementar o loop principal do jogo e interface com usuário

## 4.1 Estrutura do `Main`

O `Main` é responsável por:

1. **Inicialização** → criar objetos, carregar ranking
2. **Loop de jogo** → ler entrada, atualizar estado, desenhar mapa
3. **Persistência** → salvar ranking em arquivo
4. **Interface** → exibir mapa e mensagens

## 4.2 Entrada do Usuário

```java
Scanner scanner = new Scanner(System.in);

// Ler uma linha inteira
String linha = scanner.nextLine();

// Pegar primeiro caractere
char comando = linha.charAt(0);

// Exemplo de processamento
switch (comando) {
    case 'w': nave.moveUp(); break;
    case 'a': nave.moveLeft(); break;
    case 's': nave.moveDown(); break;
    case 'd': nave.moveRight(); break;
    case 'c': missao.embarcarPassageiroNaPosicao(); break;
    case 'q': running = false; break;
    default: System.out.println("Comando inválido!");
}
```

## 4.3 Desenhar o Mapa

```java
private static void desenharMapa(Missao missao, int minX, int maxX, 
                                  int minY, int maxY, int score, String pilotoNome) {
    System.out.println();
    System.out.printf("Mapa (Pontos: %d) - Piloto: %s%n", score, pilotoNome);
  
    // Cabeçalho com coordenadas X
    System.out.print("    ");
    for (int x = minX; x <= maxX; x++) {
        System.out.printf(" %2d", x);
    }
    System.out.println();
  
    // Linhas do mapa
    for (int y = minY; y <= maxY; y++) {
        System.out.printf("%3d|", y);
        for (int x = minX; x <= maxX; x++) {
            char symbol = '.';  // Padrão: vazio
      
            // Verificar se há nave nesta posição
            if (missao.getNave().getX() == x && missao.getNave().getY() == y) {
                symbol = 'N';
            } else {
                // Verificar passageiros
                for (Passageiro p : missao.getPassageiros()) {
                    if (p.getX() == x && p.getY() == y) {
                        if (p instanceof Engenheiro) {
                            symbol = 'E';
                        } else {
                            symbol = 'P';
                        }
                        break;
                    }
                }
          
                // Verificar asteroides (se não achou passageiro)
                if (symbol == '.') {
                    for (Asteroide a : missao.getAsteroides()) {
                        if (a.getX() == x && a.getY() == y) {
                            symbol = 'A';
                            break;
                        }
                    }
                }
            }
      
            System.out.printf(" %2c", symbol);
        }
        System.out.println();
    }
  
    // Legenda
    System.out.println("N=Nave  P=Professor  E=Engenheiro  A=Asteroide  .=Vazio");
}
```

## 4.4 Loop Principal do Jogo

```java
public static void main(String[] args) {
    Random random = new Random();
    int minX = -5, maxX = 5, minY = -5, maxY = 5;
  
    // Carregar ranking
    List<RankingEntry> ranking = loadRanking(Paths.get("ranking.json"));
  
    Scanner scanner = new Scanner(System.in);
    System.out.print("Digite o nome do piloto: ");
    String pilotoNome = scanner.nextLine();
  
    // Mostrar instruções
    System.out.println("Bem-vindo! Colete todos os passageiros sem bater em asteroides.");
    System.out.println("Comandos: w/a/s/d mover, c embarcar, q sair");
  
    boolean playAgain = true;
    while (playAgain) {
        // Criar nova missão
        Missao missao = criarNovaMissao(random, minX, maxX, minY, maxY);
        Nave nave = missao.getNave();
        int score = 20;
        boolean running = true;
  
        // Loop de uma partida
        while (running) {
            desenharMapa(missao, minX, maxX, minY, maxY, score, pilotoNome);
      
            // Verificar colisão
            if (missao.verificaColisao()) {
                System.out.println("Colisão! Missão abortada.");
                break;
            }
      
            // Ler comando
            System.out.print("Comando: ");
            String input = scanner.nextLine().toLowerCase();
            if (input.isEmpty()) continue;
      
            char cmd = input.charAt(0);
            switch (cmd) {
                case 'w': nave.moveUp(); score--; break;
                case 's': nave.moveDown(); score--; break;
                case 'a': nave.moveLeft(); score--; break;
                case 'd': nave.moveRight(); score--; break;
                case 'c': {
                    Passageiro p = missao.passagemNaPosicao();
                    if (p != null && missao.embarcarPassageiroNaPosicao()) {
                        score += 10;
                        System.out.println("Passageiro embarcado! +10 pontos");
                    }
                    break;
                }
                case 'q': running = false; break;
            }
      
            // Verificar derrota por falta de pontos
            if (score <= 0) {
                System.out.println("Pontuação zerada. Derrota!");
                break;
            }
      
            // Verificar vitória (todos embarcados)
            if (missao.todosEmbarcados()) {
                System.out.println("Sucesso! Todos embarcados. Pontuação: " + score);
                if (isTopScore(ranking, score)) {
                    ranking.add(new RankingEntry(pilotoNome, score));
                    saveRanking(Paths.get("ranking.json"), ranking);
                }
                break;
            }
        }
  
        // Mostrar ranking
        printRanking(ranking);
  
        // Perguntar se quer jogar de novo
        System.out.print("Nova missão? (s/n): ");
        String resp = scanner.nextLine().toLowerCase();
        playAgain = resp.equals("s") || resp.equals("sim");
    }
  
    scanner.close();
    System.out.println("Obrigado por jogar!");
}
```

## 4.5 Métodos Auxiliares

```java
// Criar uma nova missão com passageiros e asteroides aleatórios
private static Missao criarNovaMissao(Random random, int minX, int maxX, 
                                       int minY, int maxY) {
    Nave nave = new Nave("A-1", 3);
    Missao missao = new Missao(nave);
  
    // Adicionar 3 passageiros em posições aleatórias
    while (missao.getPassageiros().size() < 3) {
        int x = random.nextInt(maxX - minX + 1) + minX;
        int y = random.nextInt(maxY - minY + 1) + minY;
  
        if (posicaoOcupada(missao, x, y)) continue;
  
        if (missao.getPassageiros().isEmpty()) {
            missao.addPassageiro(new Professor("Dr. Silva", x, y));
        } else if (missao.getPassageiros().size() == 1) {
            missao.addPassageiro(new Engenheiro("Eng. Rosa", x, y));
        } else {
            missao.addPassageiro(new Professor("Dr. Lima", x, y));
        }
    }
  
    // Adicionar 2 asteroides
    while (missao.getAsteroides().size() < 2) {
        int x = random.nextInt(maxX - minX + 1) + minX;
        int y = random.nextInt(maxY - minY + 1) + minY;
  
        if (posicaoOcupada(missao, x, y)) continue;
  
        missao.addAsteroide(new Asteroide(x, y));
    }
  
    return missao;
}

// Verificar se uma posição está ocupada
private static boolean posicaoOcupada(Missao missao, int x, int y) {
    Nave n = missao.getNave();
    if (n.getX() == x && n.getY() == y) return true;
  
    for (Passageiro p : missao.getPassageiros()) {
        if (p.getX() == x && p.getY() == y) return true;
    }
  
    for (Asteroide a : missao.getAsteroides()) {
        if (a.getX() == x && a.getY() == y) return true;
    }
  
    return false;
}
```

---

# Exercícios Práticos

## Nível 1: Iniciante

### Exercício 1: Modificar atributos da nave

**Enunciado:** A nave atualmente tem capacidade de 3 passageiros. Mude para 5 e recompile.

**Passos:**

1. Abra `Main.java`
2. Procure a linha `new Nave("A-1", 3);`
3. Troque para `new Nave("A-1", 5);`
4. Recompile e teste

**Esperado:** O jogo permitirá embarcar 5 passageiros

---

### Exercício 2: Adicionar um novo tipo de passageiro

**Enunciado:** Crie uma classe `Astronauta` que herda de `Passageiro`.

**Passos:**

1. Crie `Astronauta.java` com:

```java
package missao;

public class Astronauta extends Passageiro {
    public Astronauta(String nome, int x, int y) {
        super(nome, "Astronauta", x, y);
    }
}
```

2. Modifique `Main.java` para adicionar um astronauta à missão
3. Teste

---

### Exercício 3: Mudar a aparência do mapa

**Enunciado:** No método `desenharMapa`, altere os símbolos:

- `N` para `@` (nave)
- `A` para `#` (asteroide)

**Passos:**

1. Procure as linhas `symbol = 'N'` e `symbol = 'A'`
2. Altere para `symbol = '@'` e `symbol = '#'`
3. Recompile e teste

---

## Nível 2: Intermediário

### Exercício 4: Adicionar pontuação bonus por tipo

**Enunciado:** Diferenças de pontuação:

- Professor: +10 pontos
- Engenheiro: +15 pontos
- Astronauta: +20 pontos

**Passos:**

1. Adicione um método em `Passageiro` que retorna pontos bonus (override em subclasses)
2. Modifique `Main.java` para chamar esse método ao embarcar

**Dica:**

```java
// Em Passageiro.java
public int getPontuacao() {
    return 10;  // Default
}

// Em Professor.java
@Override
public int getPontuacao() {
    return 10;
}

// Em Engenheiro.java
@Override
public int getPontuacao() {
    return 15;
}
```

---

### Exercício 5: Sistema de vidas

**Enunciado:** A nave tem 3 vidas. Cada colisão com asteroide custa 1 vida. Se chegar a 0, game over.

**Passos:**

1. Adicione um atributo `vidas` em `Nave`
2. Adicione método `perderVida()`
3. Modifique o loop de jogo em `Main` para contar vidas

**Dica:**

```java
if (missao.verificaColisao()) {
    nave.perderVida();
    if (nave.getVidas() == 0) {
        System.out.println("Game Over!");
        break;
    } else {
        System.out.println("Bateu em asteroide! Vidas restantes: " + nave.getVidas());
        // Reposicionar nave
    }
}
```

---

### Exercício 6: Mapa expandível

**Enunciado:** Pergunte ao jogador o tamanho do mapa antes de começar.

**Passos:**

1. Adicione entrada antes do loop de jogo:

```java
System.out.print("Tamanho do mapa (-X a +X): ");
int tamanho = Integer.parseInt(scanner.nextLine());
```

2. Use `tamanho` em vez de números fixos
3. Recompile e teste com diferentes tamanhos

---

## Nível 3: Avançado

### Exercício 7: Inimigos com IA

**Enunciado:** Adicione "inimigos" que se movem aleatoriamente a cada turno.

**Passos:**

1. Crie classe `Inimigo` com posição
2. Adicione lista de inimigos em `Missao`
3. Adicione método `moverInimigos(Random r)`
4. Chame esse método a cada turno do jogo
5. Detecte colisão com inimigos (como com asteroides)

---

### Exercício 8: Menu de dificuldade

**Enunciado:** Ofereça 3 níveis de dificuldade:

- **Fácil:** 5 asteroides, 30 pontos iniciais, 5 passageiros
- **Normal:** 3 asteroides, 20 pontos iniciais, 3 passageiros
- **Difícil:** 6 asteroides, 15 pontos iniciais, 4 passageiros

**Passos:**

1. Crie método `selecionarDificuldade()` que retorna enum
2. Modifique `criarNovaMissao()` para aceitar dificuldade
3. Ajuste quantidade de asteroides, pontos iniciais, passageiros

---

### Exercício 9: Persistência com mais dados

**Enunciado:** Salve não apenas nome e pontos, mas também:

- Data e hora da partida
- Número de passageiros coletados
- Tipo de jogo (fácil/médio/difícil)

**Passos:**

1. Crie classe `RankingEntry` melhorada com esses campos
2. Modifique `saveRanking()` para usar JSON mais complexo
3. Modifique `loadRanking()` para parsear os novos campos

---

## Nível 4: Desafio Final

### Exercício 10: Mini-jogo Completo com Todas as Features (Estilo Pouso na Lua)

**Enunciado:** 
Chegou a hora de consolidar tudo o que você aprendeu. Crie uma versão final do jogo integrando todas as features anteriores em um código limpo, modular e estruturado. Além disso, você deve implementar um **novo fluxo de encerramento da fase inspirado no pouso na lua** (onde o piloto precisa pousar e depois decolar com segurança).

#### Requisitos do Desafio:

1.  **Regra de Pouso e Decolagem:**
    *   O jogo **não deve** terminar imediatamente após salvar o último passageiro.
    *   Uma vez que todos os passageiros estão a bordo, o piloto deve pilotar a nave de volta até a **Plataforma de Pouso** localizada na coordenada central `(0, 0)`.
    *   O símbolo `L` deve ser desenhado na posição `(0, 0)` do mapa para indicar visualmente a plataforma de pouso (use a letra `L` de *Landing Pad*).
    *   A vitória só é decretada quando a nave pousa com sucesso na plataforma `(0, 0)` com todos os passageiros salvos.
    *   **Atenção:** Cada movimento de retorno consome 1 ponto de combustível. Se a pontuação zerar antes de alcançar a plataforma, o jogador perde a partida.
2.  **Menu Inicial com Opções:**
    *   Exibir um menu interativo antes do início da partida com as opções:
        1. Iniciar Nova Missão
        2. Visualizar Ranking Top 5
        3. Resetar Histórico de Ranking
        4. Sair do Jogo
3.  **Enum para Dificuldades:**
    *   Crie um Enum `Dificuldade` (`FACIL`, `MEDIO`, `DIFICIL`) para substituir o uso de strings soltas.
4.  **Estatísticas e Recordes de Fim de Partida:**
    *   Armazene o tempo de início e fim da partida usando `System.currentTimeMillis()` e exiba a duração da partida em segundos.
    *   Mostre a quantidade de movimentos realizados no mapa marciano.
    *   Ao final da partida, confira se a pontuação alcançada superou a maior pontuação persistida no ranking para parabenizar o piloto pelo recorde do servidor.
5.  **Reset de Ranking:**
    *   No menu, permita ao jogador apagar os dados do arquivo `ranking.json`.
6.  **Refatoração do Código:**
    *   Evite métodos excessivamente longos. Divida o loop principal e as verificações da classe `Main` em métodos menores e coesos (como `jogarPartida`, `resetarRanking` e `exibirEstatisticas`).

---

# Referências e Aprofundamento

## Conceitos OO Revisados

| Conceito                 | Definição                       | Exemplo                                         |
| ------------------------ | --------------------------------- | ----------------------------------------------- |
| **Classe**         | Molde para criar objetos          | `class Nave { ... }`                          |
| **Objeto**         | Instância de uma classe          | `new Nave("A-1", 3)`                          |
| **Atributo**       | Dado do objeto                    | `int x;`                                      |
| **Método**        | Comportamento do objeto           | `void moveUp() { y--; }`                      |
| **Encapsulamento** | Esconder detalhes internos        | `private int x;`                              |
| **Herança**       | Especializar uma classe           | `class Professor extends Passageiro`          |
| **Polimorfismo**   | Diferentes tipos, mesma interface | `List<Passageiro>` com Professor e Engenheiro |
| **Composição**   | Um objeto feito de outros         | `Missao` contém `Nave`                     |

## Palavras-chave Java

```java
// Declaração
class        // Define uma classe
public       // Acessível de qualquer lugar
private      // Acessível apenas na classe
static       // Pertence à classe, não ao objeto

// Herança
extends      // Uma classe especializa outra
super        // Chama método/construtor da classe pai

// Tipos
int          // Inteiro
String       // Texto
boolean      // Verdadeiro/Falso
List<T>      // Lista de elementos tipo T

// Fluxo de controle
if/else      // Decisão
switch       // Múltiplas decisões
for          // Loop com contador
while        // Loop condicional
break        // Sai do loop
continue     // Próxima iteração

// Objetos
new          // Cria um novo objeto
null         // Referência nula
instanceof   // Verifica tipo
```

## Tópicos para Aprofundamento

1. **Interfaces** (`implements`)

   - Contrato que uma classe deve cumprir
   - Múltiplas implementações da mesma interface
2. **Classes Abstratas** (`abstract`)

   - Classe que não pode ser instanciada
   - Define métodos que subclasses devem implementar
3. **Exceções** (`try/catch`)

   - Tratamento de erros
   - Criação de exceções customizadas
4. **Genéricos** (`<T>`)

   - `List<String>`, `Map<String, Integer>`
   - Type-safety em coleções
5. **Enumerações** (`enum`)

   - Conjunto fixo de valores
   - Melhor prática para constantes

## Leitura Recomendada

- **"Head First Java"** - Excelente introdução com muitos exemplos
- **"Effective Java"** - Best practices após dominar o básico
- **"Clean Code"** - Escrever código legível e mantível

## Ferramentas Úteis

- **IDE:** NetBeans, IntelliJ IDEA, Eclipse
- **Build:** Maven, Gradle
- **Versionamento:** Git
- **Testes:** JUnit

---

# Checklist de Conceitos

Você entendeu tudo se conseguir responder:

- [ ] O que é uma classe e qual é a diferença entre classe e objeto?
- [ ] Por que usamos `private` em atributos?
- [ ] Como criar um objeto com `new` e qual é o papel do construtor?
- [ ] Por que usamos herança? Qual a diferença entre `extends` e `super`?
- [ ] O que é polimorfismo e como `instanceof` nos ajuda?
- [ ] Como usar `List` e `ArrayList` em Java?
- [ ] Como iterar sobre uma lista com `for` e `Iterator`?
- [ ] Qual é a diferença entre um método que retorna `void` e um que retorna um tipo?
- [ ] Como parsear JSON manualmente (sem bibliotecas externas)?
- [ ] Por que dividir o código em múltiplas classes é melhor que tudo em `Main`?

---

# Gabarito dos Exercícios

## Exercício 1.1: Classe `Cachorro`

```java
public class Cachorro {
    private String nome;
    private String raca;
    private int idade;

    public Cachorro(String nome, String raca, int idade) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
    }

    public String getNome() { return nome; }
    public String getRaca() { return raca; }
    public int getIdade() { return idade; }

    public String falar() {
        return "Au au! Meu nome é " + nome;
    }
}
```

## Exercício 2.1: Estender `Cachorro`

```java
public class CachorroGrande extends Cachorro {
    public CachorroGrande(String nome, String raca, int idade) {
        super(nome, raca, idade);
    }

    public String late() {
        return "AUUUUU!";
    }
}

public class CachorroPequeno extends Cachorro {
    public CachorroPequeno(String nome, String raca, int idade) {
        super(nome, raca, idade);
    }

    public String late() {
        return "au...";
    }
}

// Uso em Main:
List<Cachorro> rebanho = new ArrayList<>();
rebanho.add(new CachorroGrande("Rex", "Pastor Alemão", 5));
rebanho.add(new CachorroPequeno("Tiny", "Chihuahua", 2));

for (Cachorro c : rebanho) {
    if (c instanceof CachorroGrande) {
        CachorroGrande cg = (CachorroGrande) c;
        System.out.println(c.getNome() + " late: " + cg.late());
    }
}
```

## Exercício 3.1: Classe `Time`

```java
public class Jogador {
    private String nome;
    private int gols;

    public Jogador(String nome, int gols) {
        this.nome = nome;
        this.gols = gols;
    }

    public String getNome() { return nome; }
    public int getGols() { return gols; }
}

public class Time {
    private String nome;
    private List<Jogador> jogadores = new ArrayList<>();

    public Time(String nome) {
        this.nome = nome;
    }

    public void adicionarJogador(Jogador j) {
        jogadores.add(j);
    }

    public void listarJogadores() {
        System.out.println("Time " + nome + ":");
        for (Jogador j : jogadores) {
            System.out.println("  - " + j.getNome() + ": " + j.getGols() + " gols");
        }
    }

    public int totalGols() {
        int total = 0;
        for (Jogador j : jogadores) {
            total += j.getGols();
        }
        return total;
    }
}
```

## Exercício 10: Solução do Desafio Final (Mini-jogo Completo)

Esta solução integra todas as features adicionais solicitadas no **Nível 4: Desafio Final**:

1. **Dificuldade representada por Enum (`Dificuldade.java`)**.
2. **Menu Inicial interativo** (Jogar, Visualizar Ranking, Resetar Ranking, Sair).
3. **Estatísticas da Partida** (tempo de jogo em segundos calculado via `System.currentTimeMillis()`, quantidade de movimentos e avisos de novos recordes).
4. **Possibilidade de resetar o ranking** apagando o arquivo JSON.
5. **Organização e divisão da classe `Main`** em métodos especializados e coesos.

### 10.1 Enum `Dificuldade.java`

```java
package missao;

public enum Dificuldade {
    FACIL, MEDIO, DIFICIL;

    public static Dificuldade deString(String s) {
        if (s == null) return MEDIO;
        switch (s.trim().toLowerCase()) {
            case "facil":
            case "fácil":
                return FACIL;
            case "dificil":
            case "difícil":
                return DIFICIL;
            default:
                return MEDIO;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case FACIL: return "Fácil";
            case DIFICIL: return "Difícil";
            default: return "Médio";
        }
    }
}
```

### 10.2 Classe `Main.java` Completa

```java
package missao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Path RANKING_PATH = Paths.get("ranking.json");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        List<RankingEntry> ranking = loadRanking(RANKING_PATH);

        exibirBoasVindas();

        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            String opcao = lerLinha(scanner, "Escolha uma opção: ", "1");
            switch (opcao) {
                case "1":
                    jogarPartida(scanner, random, ranking);
                    // Recarrega o ranking após a partida
                    ranking = loadRanking(RANKING_PATH);
                    break;
                case "2":
                    exibirRankingCompleto(ranking);
                    break;
                case "3":
                    ranking = resetarRanking(scanner);
                    break;
                case "4":
                    rodando = false;
                    System.out.println("\nObrigado por jogar a Missão Marte Unifor!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void exibirBoasVindas() {
        System.out.println("================================================================");
        System.out.println("             MISSÃO MARTE UNIFOR - VERSÃO COMPLETA              ");
        System.out.println("================================================================");
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Iniciar Nova Missão");
        System.out.println("2. Visualizar Ranking Top 5");
        System.out.println("3. Resetar Histórico de Ranking");
        System.out.println("4. Sair do Jogo");
        System.out.println("----------------------");
    }

    private static void jogarPartida(Scanner scanner, Random random, List<RankingEntry> ranking) {
        String pilotoNome = lerLinha(scanner, "\nDigite o nome do piloto: ", "Piloto Anônimo");
        if (pilotoNome.isEmpty()) {
            pilotoNome = "Piloto Anônimo";
        }

        Dificuldade dificuldade = lerDificuldade(scanner);
        int tamanhoMapa = lerTamanhoMapa(scanner);

        int minX = -tamanhoMapa;
        int maxX = tamanhoMapa;
        int minY = -tamanhoMapa;
        int maxY = tamanhoMapa;

        System.out.println("\nDecolagem em 3... 2... 1... Pressione Enter para iniciar!");
        scanner.nextLine();

        Missao missao = criarNovaMissao(random, minX, maxX, minY, maxY, dificuldade);
        Nave nave = missao.getNave();
        int score = definirPontuacaoInicial(dificuldade);
        int movimentos = 0;
        boolean partidaAtiva = true;

        long tempoInicio = System.currentTimeMillis();

        while (partidaAtiva) {
            desenharMapa(missao, minX, maxX, minY, maxY, score, pilotoNome);
            System.out.printf("Nave em (%d,%d) | Pontos: %d | Vidas: %d | A bordo: %d/%d | Restantes: %d%n",
                    nave.getX(), nave.getY(), score, nave.getVidas(),
                    nave.getPassageiros().size(), nave.getCapacidade(),
                    missao.todosEmbarcados() ? 0 : missao.getPassageiros().size());

            String comandoStr = lerLinha(scanner, "Comando (w/s/a/d/c/q): ", "").toLowerCase();
            if (comandoStr.isEmpty()) continue;

            char cmd = comandoStr.charAt(0);
            if (cmd == 'q') {
                System.out.println("Missão abortada.");
                partidaAtiva = false;
                break;
            } else if (cmd == 'c') {
                Passageiro p = missao.passagemNaPosicao();
                if (p == null) {
                    System.out.println("Nenhum passageiro nesta posição.");
                } else {
                    boolean embarcou = missao.embarcarPassageiroNaPosicao();
                    if (embarcou) {
                        int bonus = p.getPontuacao();
                        score += bonus;
                        System.out.printf("Passageiro %s embarcado! +%d pontos!%n", p.getNome(), bonus);
                    } else {
                        System.out.println("Nave cheia! Não foi possível embarcar.");
                    }
                }
            } else if (cmd == 'w' || cmd == 's' || cmd == 'a' || cmd == 'd') {
                // Utiliza os limites definidos para o mapa
                nave.moverComLimites(cmd, minX, maxX, minY, maxY);
                score--;
                movimentos++;
            } else {
                System.out.println("Comando desconhecido.");
                continue;
            }

            // Atualiza posição dos inimigos
            missao.moverInimigos(random, minX, maxX, minY, maxY);

            // Verifica colisões com perigos
            if (missao.verificaColisao()) {
                nave.perderVida();
                if (nave.getVidas() > 0) {
                    System.out.printf("Colisão! Vidas restantes: %d%n", nave.getVidas());
                } else {
                    System.out.println("GAME OVER! A nave foi destruída por completo.");
                    partidaAtiva = false;
                }
            }

            // Sem energia/pontos
            if (score <= 0) {
                System.out.println("Pontuação zerada. Fim de missão por falta de combustível.");
                partidaAtiva = false;
            }

            // Verificar vitória (todos resgatados E nave retornou à Plataforma de Pouso em (0,0))
            if (missao.todosEmbarcados() && partidaAtiva) {
                if (nave.getX() == 0 && nave.getY() == 0) {
                    long tempoFim = System.currentTimeMillis();
                    long tempoJogoSegundos = (tempoFim - tempoInicio) / 1000;

                    System.out.println("\n================================================================");
                    System.out.println("🚀 DECOLAGEM AUTORIZADA! Nave acoplada à plataforma em (0,0).");
                    System.out.println("Retornando à órbita marciana com todos os passageiros. Missão cumprida!");
                    System.out.println("================================================================");
                    exibirEstatisticas(score, movimentos, tempoJogoSegundos, nave.getPassageiros().size(), ranking);

                    if (score > 0 && isTopScore(ranking, score)) {
                        RankingEntry novaEntrada = new RankingEntry(
                                pilotoNome,
                                score,
                                dificuldade,
                                nave.getPassageiros().size(),
                                java.time.LocalDateTime.now().toString().substring(0, 19).replace('T', ' '),
                                tempoJogoSegundos
                        );
                        ranking.add(novaEntrada);
                        List<RankingEntry> rankingFiltrado = ranking.stream()
                                .sorted(Comparator.comparingInt((RankingEntry e) -> e.score).reversed())
                                .limit(5)
                                .collect(Collectors.toList());
                        saveRanking(RANKING_PATH, rankingFiltrado);
                        System.out.println("Parabéns! Novo registro salvo no ranking!");
                    }
                    partidaAtiva = false;
                } else {
                    System.out.println("✨ ALERTA: Todos os passageiros resgatados! Retorne para a Plataforma de Pouso 'L' em (0,0) para completar a missão.");
                }
            }
        }
    }

    private static void exibirEstatisticas(int score, int movimentos, long tempoSegundos, int passageiros, List<RankingEntry> ranking) {
        System.out.println("\n================ ESTATÍSTICAS ================");
        System.out.printf(" - Pontuação Concluída: %d pontos%n", score);
        System.out.printf(" - Movimentos Efetuados: %d%n", movimentos);
        System.out.printf(" - Duração da Partida: %d segundos%n", tempoSegundos);
        System.out.printf(" - Passageiros Coletados: %d%n", passageiros);

        int recorde = ranking.isEmpty() ? 0 : ranking.get(0).score;
        if (score > recorde && recorde > 0) {
            System.out.println("🏆 Novo Recorde do Servidor!");
        } else if (recorde > 0) {
            System.out.printf(" - Recorde atual: %d pontos (Piloto: %s)%n", recorde, ranking.get(0).name);
        }
        System.out.println("==============================================");
    }

    private static Dificuldade lerDificuldade(Scanner scanner) {
        System.out.print("Escolha a Dificuldade (facil/medio/dificil): ");
        String difStr = lerLinha(scanner, "", "medio");
        return Dificuldade.deString(difStr);
    }

    private static int lerTamanhoMapa(Scanner scanner) {
        try {
            int tamanho = Integer.parseInt(lerLinha(scanner, "Tamanho do mapa (ex: 5 para mapa de -5 a +5): ", "5"));
            return tamanho > 0 ? tamanho : 5;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Usando mapa padrão (5).");
            return 5;
        }
    }

    private static int definirPontuacaoInicial(Dificuldade dificuldade) {
        switch (dificuldade) {
            case FACIL: return 30;
            case DIFICIL: return 15;
            default: return 20;
        }
    }

    private static Missao criarNovaMissao(Random random, int minX, int maxX, int minY, int maxY, Dificuldade dificuldade) {
        Nave nave = new Nave("A-1", 5);
        Missao missao = new Missao(nave);

        int qtdPassageiros = 5;
        int qtdAsteroides = 2;
        int qtdInimigos = 2;

        if (dificuldade == Dificuldade.FACIL) {
            qtdPassageiros = 4;
            qtdAsteroides = 1;
            qtdInimigos = 1;
        } else if (dificuldade == Dificuldade.DIFICIL) {
            qtdPassageiros = 5;
            qtdAsteroides = 3;
            qtdInimigos = 3;
        }

        // Adicionar passageiros aleatórios
        while (missao.getPassageiros().size() < qtdPassageiros) {
            int x = random.nextInt(maxX - minX + 1) + minX;
            int y = random.nextInt(maxY - minY + 1) + minY;
            if (x == nave.getX() && y == nave.getY()) continue;
            if (posicaoOcupada(missao, x, y)) continue;

            int index = missao.getPassageiros().size();
            missao.addPassageiro(criarPassageiroPolimorfico(index, x, y));
        }

        // Adicionar asteroides aleatórios
        while (missao.getAsteroides().size() < qtdAsteroides) {
            int x = random.nextInt(maxX - minX + 1) + minX;
            int y = random.nextInt(maxY - minY + 1) + minY;
            if (x == nave.getX() && y == nave.getY()) continue;
            if (posicaoOcupada(missao, x, y)) continue;

            missao.addAsteroide(new Asteroide(x, y));
        }

        // Adicionar inimigos aleatórios
        while (missao.getInimigos().size() < qtdInimigos) {
            int x = random.nextInt(maxX - minX + 1) + minX;
            int y = random.nextInt(maxY - minY + 1) + minY;
            if (x == nave.getX() && y == nave.getY()) continue;
            if (posicaoOcupada(missao, x, y)) continue;

            missao.addInimigo(new Inimigo(x, y));
        }

        return missao;
    }

    private static Passageiro criarPassageiroPolimorfico(int indice, int x, int y) {
        switch (indice % 5) {
            case 0: return new Professor("Dr. Silva", x, y);
            case 1: return new Engenheiro("Eng. Rosa", x, y);
            case 2: return new Professor("Dr. Lima", x, y);
            case 3: return new Engenheiro("Eng. Carlos", x, y);
            default: return new Astronauta("Ast. Maria", x, y);
        }
    }

    private static boolean posicaoOcupada(Missao missao, int x, int y) {
        if (missao.getNave().getX() == x && missao.getNave().getY() == y) return true;
        for (Passageiro p : missao.getPassageiros()) {
            if (p.getX() == x && p.getY() == y) return true;
        }
        for (Asteroide a : missao.getAsteroides()) {
            if (a.getX() == x && a.getY() == y) return true;
        }
        for (Inimigo i : missao.getInimigos()) {
            if (i.getX() == x && i.getY() == y) return true;
        }
        return false;
    }

    private static void desenharMapa(Missao missao, int minX, int maxX, int minY, int maxY, int score, String pilotoNome) {
        System.out.println();
        System.out.printf("Mapa da Missão (Pontos: %d) - Piloto: %s%n", score, pilotoNome);
        System.out.print("    ");
        for (int x = minX; x <= maxX; x++) {
            System.out.printf(" %2d", x);
        }
        System.out.println();
        System.out.print("    ");
        for (int x = minX; x <= maxX; x++) {
            System.out.print(" __");
        }
        System.out.println();

        for (int y = minY; y <= maxY; y++) {
            System.out.printf("%3d|", y);
            for (int x = minX; x <= maxX; x++) {
                char symbol = '.';
                if (missao.getNave().getX() == x && missao.getNave().getY() == y) {
                    symbol = '@';
                } else {
                    for (Passageiro p : missao.getPassageiros()) {
                        if (p.getX() == x && p.getY() == y) {
                            if (p instanceof Engenheiro) {
                                symbol = 'E';
                            } else if (p instanceof Astronauta) {
                                symbol = 'T';
                            } else {
                                symbol = 'P';
                            }
                            break;
                        }
                    }
                    if (symbol == '.') {
                        for (Asteroide a : missao.getAsteroides()) {
                            if (a.getX() == x && a.getY() == y) {
                                symbol = '#';
                                break;
                            }
                        }
                    }
                    if (symbol == '.') {
                        for (Inimigo i : missao.getInimigos()) {
                            if (i.getX() == x && i.getY() == y) {
                                symbol = 'X';
                                break;
                            }
                        }
                    }
                    if (symbol == '.' && x == 0 && y == 0) {
                        symbol = 'L'; // L para Plataforma de Pouso (Landing Pad)
                    }
                }
                System.out.printf(" %2c", symbol);
            }
            System.out.println();
        }

        System.out.println("Legenda: @=Nave, P=Professor, E=Engenheiro, T=Astronauta, #=Asteroide, X=Inimigo, L=Plataforma de Pouso, .=Vazio");
        System.out.println("Comandos: w/s/a/d (mover), c (embarcar), q (sair)");
    }

    private static String lerLinha(Scanner scanner, String prompt, String fallback) {
        if (prompt != null && !prompt.isEmpty()) {
            System.out.print(prompt);
        }
        if (scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return fallback;
    }

    private static void exibirRankingCompleto(List<RankingEntry> ranking) {
        System.out.println("\n====== RANKING TOP 5 PILOTOS ======");
        if (ranking.isEmpty()) {
            System.out.println(" - Nenhum registro encontrado. Seja o primeiro a jogar!");
        } else {
            int pos = 1;
            for (RankingEntry entry : ranking) {
                System.out.printf("%d. %s - %d pts | Dificuldade: %s | Coletados: %d | Tempo: %ds | %s%n",
                        pos++, entry.name, entry.score, entry.dificuldade, entry.passageirosColetados, entry.tempoJogo, entry.dataHora);
            }
        }
        System.out.println("===================================");
    }

    private static List<RankingEntry> resetarRanking(Scanner scanner) {
        System.out.print("Você realmente deseja limpar o histórico de ranking? (s/n): ");
        String confirmacao = lerLinha(scanner, "", "n").toLowerCase();
        if (confirmacao.equals("s") || confirmacao.equals("sim")) {
            try {
                Files.deleteIfExists(RANKING_PATH);
                System.out.println("Histórico de ranking resetado!");
            } catch (IOException e) {
                System.out.println("Erro ao deletar ranking: " + e.getMessage());
            }
            return new ArrayList<>();
        }
        System.out.println("Operação cancelada.");
        return loadRanking(RANKING_PATH);
    }

    private static boolean isTopScore(List<RankingEntry> ranking, int score) {
        if (ranking.size() < 5) {
            return true;
        }
        return score > ranking.get(ranking.size() - 1).score;
    }

    private static List<RankingEntry> loadRanking(Path path) {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8).trim();
            return parseRankingJson(json);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static void saveRanking(Path path, List<RankingEntry> ranking) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < ranking.size(); i++) {
            RankingEntry entry = ranking.get(i);
            builder.append("{\"name\":\"")
                    .append(entry.name.replace("\"", "\\\""))
                    .append("\",\"score\":")
                    .append(entry.score)
                    .append(",\"dificuldade\":\"")
                    .append(entry.dificuldade.name())
                    .append("\",\"passageirosColetados\":")
                    .append(entry.passageirosColetados)
                    .append(",\"dataHora\":\"")
                    .append(entry.dataHora)
                    .append("\",\"tempoJogo\":")
                    .append(entry.tempoJogo)
                    .append("}");
            if (i < ranking.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        try {
            Files.write(path, builder.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Não foi possível salvar o ranking: " + e.getMessage());
        }
    }

    private static List<RankingEntry> parseRankingJson(String json) {
        List<RankingEntry> ranking = new ArrayList<>();
        if (json.isEmpty() || json.equals("[]")) {
            return ranking;
        }
        json = json.trim();
        if (json.startsWith("[")) {
            json = json.substring(1);
        }
        if (json.endsWith("]")) {
            json = json.substring(0, json.length() - 1);
        }

        int index = 0;
        while (index < json.length()) {
            int start = json.indexOf('{', index);
            if (start < 0) break;
            int end = json.indexOf('}', start);
            if (end < 0) break;
            String object = json.substring(start + 1, end);
            String name = null;
            Integer score = null;
            Dificuldade dificuldade = Dificuldade.MEDIO;
            Integer passageirosColetados = 0;
            String dataHora = "";
            long tempoJogo = 0;

            for (String part : object.split(",")) {
                String[] pair = part.split(":", 2);
                if (pair.length != 2) continue;
                String key = pair[0].trim().replaceAll("\"", "");
                String value = pair[1].trim();

                if (key.equals("name")) {
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        name = value.substring(1, value.length() - 1).replace("\\\"", "\"");
                    }
                } else if (key.equals("score")) {
                    try {
                        score = Integer.parseInt(value);
                    } catch (NumberFormatException ignored) {}
                } else if (key.equals("dificuldade")) {
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        dificuldade = Dificuldade.deString(value.substring(1, value.length() - 1));
                    }
                } else if (key.equals("passageirosColetados")) {
                    try {
                        passageirosColetados = Integer.parseInt(value);
                    } catch (NumberFormatException ignored) {}
                } else if (key.equals("dataHora")) {
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        dataHora = value.substring(1, value.length() - 1);
                    }
                } else if (key.equals("tempoJogo")) {
                    try {
                        tempoJogo = Long.parseLong(value);
                    } catch (NumberFormatException ignored) {}
                }
            }
            if (name != null && score != null) {
                ranking.add(new RankingEntry(name, score, dificuldade, passageirosColetados, dataHora, tempoJogo));
            }
            index = end + 1;
        }

        ranking.sort(Comparator.comparingInt((RankingEntry e) -> e.score).reversed());
        return ranking;
    }

    private static class RankingEntry {
        private final String name;
        private final int score;
        private final Dificuldade dificuldade;
        private final int passageirosColetados;
        private final String dataHora;
        private final long tempoJogo;

        private RankingEntry(String name, int score, Dificuldade dificuldade, int passageirosColetados, String dataHora, long tempoJogo) {
            this.name = name;
            this.score = score;
            this.dificuldade = dificuldade;
            this.passageirosColetados = passageirosColetados;
            this.dataHora = dataHora;
            this.tempoJogo = tempoJogo;
        }
    }
}
```

---

**Fim do Tutorial**

Desenvolvido para a disciplina de Programação Orientada a Objetos - Unifor 2026
