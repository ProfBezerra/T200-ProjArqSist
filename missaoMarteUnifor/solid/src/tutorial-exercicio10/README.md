# Atividade prática: Refatoração SOLID a partir do Exercício 10

Esta atividade propõe que o aluno transforme um código de jogo em console em uma versão mais organizada, reutilizável e alinhada aos princípios do SOLID.

## Objetivo

O aluno deve refatorar o código do Exercício 10, criando uma estrutura em pacotes e aplicando os princípios do SOLID, mantendo o comportamento básico do jogo original.

## Materiais

- Ambiente com Java instalado;
- Terminal para compilar e executar o projeto;
- Editor de texto ou IDE;
- Código do Exercício 10 como ponto de partida;
- Comandos de compilação com `javac` e `java`.

## Procedimento

### 1. Entender o problema

Observe o código original e responda:

Espaço para resposta do aluno:
- O que estava ruim no código original? ______________________________________
- Quais responsabilidades estavam misturadas? __________________________________
- Como você imagina organizar esse código em partes menores? ____________________

### 2. Criar a estrutura de pacotes

Crie os pacotes abaixo:
- `model`
- `presentation`
- `repository`
- `service`

### 3. Criar a classe principal

Crie o arquivo `Main.java` com o conteúdo abaixo:

```java
package solidexercicio10;

import java.util.Scanner;
import solidexercicio10.repository.RankingRepository;
import solidexercicio10.repository.RankingService;
import solidexercicio10.service.JogoService;

public class Main {
    public static void main(String[] args) {
        RankingRepository repository = new RankingService("ranking.json");
        JogoService jogoService = new JogoService(repository);
        Scanner scanner = new Scanner(System.in);
        jogoService.executarLoop(scanner);
    }
}
```

Espaço para resposta do aluno:
- Por que a classe `Main` ficou mais simples depois da refatoração? ________________

### 4. Criar a camada de serviço

Crie o arquivo `service/JogoService.java` com este conteúdo:

```java
package solidexercicio10.service;

import java.util.Scanner;
import solidexercicio10.repository.RankingRepository;

public class JogoService {
    private final RankingRepository rankingRepository;

    public JogoService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public void executarLoop(Scanner scanner) {
        System.out.println("Jogo iniciado");
    }
}
```

Espaço para resposta do aluno:
- Qual responsabilidade essa classe passou a ter? ______________________________

### 5. Criar a camada de apresentação

Crie o arquivo `presentation/MapaRenderer.java` com este conteúdo:

```java
package solidexercicio10.presentation;

public class MapaRenderer {
    public void desenhar() {
        System.out.println("Mapa renderizado");
    }
}
```

Espaço para resposta do aluno:
- Por que separar a apresentação é uma boa prática? ____________________________

### 6. Criar a abstração do ranking

Crie o arquivo `repository/RankingRepository.java` com este conteúdo:

```java
package solidexercicio10.repository;

public interface RankingRepository {
    void salvar(String nome, int pontuacao);
}
```

### 7. Criar a implementação do ranking

Crie o arquivo `repository/RankingService.java` com este conteúdo:

```java
package solidexercicio10.repository;

public class RankingService implements RankingRepository {
    private final String nomeArquivo;

    public RankingService(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public void salvar(String nome, int pontuacao) {
        System.out.println("Ranking salvo para " + nome + " com " + pontuacao + " pontos");
    }
}
```

Espaço para resposta do aluno:
- Como a interface ajuda a deixar o código mais flexível? ________________________

### 8. Criar o modelo do domínio

Crie o arquivo `model/Dificuldade.java` com este conteúdo:

```java
package solidexercicio10.model;

public enum Dificuldade {
    FACIL,
    MEDIO,
    DIFICIL
}
```

### 9. Criar os passageiros como abstração

Crie o arquivo `model/Passageiro.java` com este conteúdo:

```java
package solidexercicio10.model;

public abstract class Passageiro {
    private final String nome;

    protected Passageiro(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public abstract int getPontuacao();
}
```

Crie também os arquivos `model/Professor.java`, `model/Engenheiro.java` e `model/Astronauta.java` com os conteúdos abaixo.

```java
package solidexercicio10.model;

public class Professor extends Passageiro {
    public Professor(String nome) {
        super(nome);
    }

    @Override
    public int getPontuacao() {
        return 15;
    }
}
```

```java
package solidexercicio10.model;

public class Engenheiro extends Passageiro {
    public Engenheiro(String nome) {
        super(nome);
    }

    @Override
    public int getPontuacao() {
        return 20;
    }
}
```

```java
package solidexercicio10.model;

public class Astronauta extends Passageiro {
    public Astronauta(String nome) {
        super(nome);
    }

    @Override
    public int getPontuacao() {
        return 10;
    }
}
```

Espaço para resposta do aluno:
- Que princípio do SOLID essa estrutura ajuda a aplicar? _________________________

### 10. Criar a missão e a nave

Crie o arquivo `model/Nave.java` com este conteúdo:

```java
package solidexercicio10.model;

public class Nave {
    private int x;
    private int y;

    public Nave(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
```

Crie o arquivo `model/Missao.java` com este conteúdo:

```java
package solidexercicio10.model;

public class Missao {
    private final Nave nave;

    public Missao(Nave nave) {
        this.nave = nave;
    }

    public Nave getNave() {
        return nave;
    }
}
```

### 11. Completar o fluxo do jogo

Agora volte para `service/JogoService.java` e substitua o conteúdo pelo código abaixo:

```java
package solidexercicio10.service;

import java.util.Scanner;
import solidexercicio10.presentation.MapaRenderer;
import solidexercicio10.repository.RankingRepository;

public class JogoService {
    private final RankingRepository rankingRepository;
    private final MapaRenderer mapaRenderer;

    public JogoService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
        this.mapaRenderer = new MapaRenderer();
    }

    public void executarLoop(Scanner scanner) {
        System.out.println("Menu do jogo");
        mapaRenderer.desenhar();
        rankingRepository.salvar("Aluno", 40);
    }
}
```

### 12. Compilar e executar

Execute os comandos abaixo:

```bash
mkdir out
javac -d out -sourcepath missaoMarteUnifor/solid/src/tutorial-exercicio10/src missaoMarteUnifor/solid/src/tutorial-exercicio10/src/solidexercicio10/Main.java
java -cp out solidexercicio10.Main
```

Espaço para resposta do aluno:
- O projeto compilou e executou? _______________________________________________
- O que você observou de diferente em relação ao código original? ________________

---

## Versão com solução completa (para o professor)

Abaixo está uma versão completa da refatoração, que pode servir como referência após o aluno terminar a atividade.

### Main.java

```java
package solidexercicio10;

import java.util.Scanner;
import solidexercicio10.repository.RankingRepository;
import solidexercicio10.repository.RankingService;
import solidexercicio10.service.JogoService;

public class Main {
    public static void main(String[] args) {
        RankingRepository repository = new RankingService("ranking.json");
        JogoService jogoService = new JogoService(repository);
        Scanner scanner = new Scanner(System.in);
        jogoService.executarLoop(scanner);
    }
}
```

### service/JogoService.java

```java
package solidexercicio10.service;

import java.util.Scanner;
import solidexercicio10.presentation.MapaRenderer;
import solidexercicio10.repository.RankingRepository;

public class JogoService {
    private final RankingRepository rankingRepository;
    private final MapaRenderer mapaRenderer;

    public JogoService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
        this.mapaRenderer = new MapaRenderer();
    }

    public void executarLoop(Scanner scanner) {
        System.out.println("Menu do jogo");
        mapaRenderer.desenhar();
        rankingRepository.salvar("Aluno", 40);
    }
}
```

### presentation/MapaRenderer.java

```java
package solidexercicio10.presentation;

public class MapaRenderer {
    public void desenhar() {
        System.out.println("Mapa renderizado");
    }
}
```

### repository/RankingRepository.java

```java
package solidexercicio10.repository;

public interface RankingRepository {
    void salvar(String nome, int pontuacao);
}
```

### repository/RankingService.java

```java
package solidexercicio10.repository;

public class RankingService implements RankingRepository {
    private final String nomeArquivo;

    public RankingService(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public void salvar(String nome, int pontuacao) {
        System.out.println("Ranking salvo para " + nome + " com " + pontuacao + " pontos");
    }
}
```

### model/Dificuldade.java

```java
package solidexercicio10.model;

public enum Dificuldade {
    FACIL,
    MEDIO,
    DIFICIL
}
```

### model/Passageiro.java

```java
package solidexercicio10.model;

public abstract class Passageiro {
    private final String nome;

    protected Passageiro(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public abstract int getPontuacao();
}
```

### model/Professor.java

```java
package solidexercicio10.model;

public class Professor extends Passageiro {
    public Professor(String nome) {
        super(nome);
    }

    @Override
    public int getPontuacao() {
        return 15;
    }
}
```

### model/Engenheiro.java

```java
package solidexercicio10.model;

public class Engenheiro extends Passageiro {
    public Engenheiro(String nome) {
        super(nome);
    }

    @Override
    public int getPontuacao() {
        return 20;
    }
}
```

### model/Astronauta.java

```java
package solidexercicio10.model;

public class Astronauta extends Passageiro {
    public Astronauta(String nome) {
        super(nome);
    }

    @Override
    public int getPontuacao() {
        return 10;
    }
}
```

### model/Nave.java

```java
package solidexercicio10.model;

public class Nave {
    private int x;
    private int y;

    public Nave(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
```

### model/Missao.java

```java
package solidexercicio10.model;

public class Missao {
    private final Nave nave;

    public Missao(Nave nave) {
        this.nave = nave;
    }

    public Nave getNave() {
        return nave;
    }
}
```

---

## Avaliação

Use a checklist abaixo para avaliar se a atividade foi concluída corretamente.

- [ ] Criou os pacotes `model`, `presentation`, `repository` e `service`.
- [ ] Organizou a classe `Main` com fluxo simples e delegação de responsabilidades.
- [ ] Criou `JogoService` para controlar o fluxo do jogo.
- [ ] Criou `MapaRenderer` para a renderização.
- [ ] Criou `RankingRepository` e `RankingService` para persistência.
- [ ] Organizou os passageiros com uma abstração (`Passageiro`).
- [ ] Mantém o comportamento básico do jogo original.
- [ ] O código ficou mais organizado e com menor acoplamento.
- [ ] O projeto compilou e executou corretamente.

Espaço para resposta do aluno:
- Qual princípio do SOLID você achou mais importante nesta atividade? _____________
- O que você aprendeu com a refatoração? ______________________________________

