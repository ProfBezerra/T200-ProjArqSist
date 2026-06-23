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

// Ambos são Passageiro e respondemmétodos herdados
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
- `Cachorropequeno` (com método `late()` retornando "au...")

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
- Tem `nome` (String) e `jogadores` (List<Jogador>)
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
                    saveRanking(ranking);
                }
                break;
            }
        }
        
        // Mostrar ranking
        exibirRanking(ranking);
        
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
        // Repositonar nave
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

### Exercício 7: Enemies com IA

**Enunciado:** Adicione "inimigos" que se movem aleatoriamente a cada turno.

**Passos:**
1. Crie classe `Inimigo` com posição
2. Adicione lista de inimigos em `Missao`
3. Adicione método `moverInimigos(Random r)`
4. Chame esse método a cada turno do jogo
5. Detecte colisão com inimigos (como com asteroides)

---

### Exercício 8: Persistência com mais dados

**Enunciado:** Salve não apenas nome e pontos, mas também:
- Data e hora da partida
- Número de passageiros coletados
- Tipo de jogo (fácil/médio/difícil)

**Passos:**
1. Crie classe `RankingEntry` melhorada com esses campos
2. Modifique `saveRanking()` para usar JSON mais complexo
3. Modifique `loadRanking()` para parsear os novos campos

---

### Exercício 9: Menu de dificuldade

**Enunciado:** Ofereça 3 níveis de dificuldade:
- **Fácil:** 5 asteroides, 30 pontos iniciais, 5 passageiros
- **Normal:** 3 asteroides, 20 pontos iniciais, 3 passageiros
- **Difícil:** 6 asteroides, 15 pontos iniciais, 4 passageiros

**Passos:**
1. Crie método `selecionarDificuldade()` que retorna enum
2. Modifique `criarNovaMissao()` para aceitar dificuldade
3. Ajuste quantidade de asteroides, pontos iniciais, passageiros

---

## Nível 4: Desafio Final

### Exercício 10: Mini-jogo completo com todas as features

Implemente um jogo que combina:
- ✅ Tipos diferentes de passageiros com pontos diferentes
- ✅ Dificuldades múltiplas
- ✅ Sistema de vidas
- ✅ Ranking persistente em JSON
- ✅ Possibilidade de resetar ranking
- ✅ Estatísticas (tempo de jogo, melhor pontuação, etc.)
- ✅ Menu inicial com opções

**Dicas:**
- Use enums para dificuldade
- Use `System.currentTimeMillis()` para cronometro
- Organize Main em métodos menores (separe concerns)

---

# Referências e Aprofundamento

## Conceitos OO Revisados

| Conceito | Definição | Exemplo |
|----------|-----------|---------|
| **Classe** | Molde para criar objetos | `class Nave { ... }` |
| **Objeto** | Instância de uma classe | `new Nave("A-1", 3)` |
| **Atributo** | Dado do objeto | `int x;` |
| **Método** | Comportamento do objeto | `void moveUp() { y--; }` |
| **Encapsulamento** | Esconder detalhes internos | `private int x;` |
| **Herança** | Especializar uma classe | `class Professor extends Passageiro` |
| **Polimorfismo** | Diferentes tipos, mesma interface | `List<Passageiro>` com Professor e Engenheiro |
| **Composição** | Um objeto feito de outros | `Missao` contém `Nave` |

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

6. **Padrões de Design**
   - Singleton
   - Factory
   - Observer
   - Strategy

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

---

**Fim do Tutorial**

Desenvolvido para a disciplina de Programação Orientada a Objetos - Unifor 2026
