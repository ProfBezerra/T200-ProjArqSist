# GRASP na Missão Marte

O GRASP é a parte prática do design: ele responde quem deve ficar com cada responsabilidade.

## Visão rápida

No projeto, a separação é simples:

- `Missao`: domínio do jogo
- `Nave`: comportamento da nave
- `JogoService`: regras e fluxo da partida
- `MapaRenderer`: apresentação no console
- `RankingRepository`: persistência do ranking

## Pergunta central

Em vez de perguntar só como o código funciona, o GRASP pergunta:

- quem conhece essa informação?
- quem executa essa ação?
- quem cria esse objeto?
- quem coordena esse fluxo?

## Por que isso importa?

Se tudo estivesse junto, a classe ficaria grande, acoplada e difícil de evoluir.

O GRASP ajuda a organizar o projeto em camadas com responsabilidades bem definidas:

- `model`: domínio
- `service`: regras e fluxo
- `presentation`: visualização
- `repository`: persistência

## Importante

Este README é só uma porta de entrada. O estudo detalhado do GRASP está no tutorial e nas demais páginas do material.

        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public abstract int getPontuacao();
}
```

Crie os arquivos model/Professor.java, model/Engenheiro.java e model/Astronauta.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public class Professor extends Passageiro {
    public Professor(String nome, int x, int y) {
        super(nome, "Professor", x, y);
    }

    @Override
    public int getPontuacao() {
        return 15;
    }

    @Override
    public String getSimbolo() {
        return "P";
    }
}
```

```java
package solidexercicio10.model;

public class Engenheiro extends Passageiro {
    public Engenheiro(String nome, int x, int y) {
        super(nome, "Engenheiro", x, y);
    }

    @Override
    public int getPontuacao() {
        return 20;
    }

    @Override
    public String getSimbolo() {
        return "E";
    }
}
```

```java
package solidexercicio10.model;

public class Astronauta extends Passageiro {
    public Astronauta(String nome, int x, int y) {
        super(nome, "Astronauta", x, y);
    }

    @Override
    public int getPontuacao() {
        return 10;
    }

    @Override
    public String getSimbolo() {
        return "T";
    }
}
```

Crie o arquivo model/Asteroide.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public class Asteroide extends EntidadeMapa {
    public Asteroide(int x, int y) {
        super(x, y);
    }

    @Override
    public String getSimbolo() {
        return "A";
    }
}
```

Crie o arquivo model/Inimigo.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

public class Inimigo extends EntidadeMapa implements Movel {
    public Inimigo(int x, int y) {
        super(x, y);
    }

    @Override
    public void mover(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public String getSimbolo() {
        return "X";
    }
}
```

Crie o arquivo model/Nave.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

import java.util.ArrayList;
import java.util.List;

public class Nave extends EntidadeMapa implements Movel {
    private final String nome;
    private final List<Passageiro> passageiros;
    private final int capacidade;
    private int vidas;

    public Nave(String nome, int x, int y) {
        this(nome, x, y, 3);
    }

    public Nave(String nome, int x, int y, int capacidade) {
        super(x, y);
        this.nome = nome;
        this.passageiros = new ArrayList<>();
        this.capacidade = capacidade;
        this.vidas = 3;
    }

    public String getNome() {
        return nome;
    }

    public void embarcar(Passageiro passageiro) {
        if (passageiros.size() < capacidade) {
            passageiros.add(passageiro);
        }
    }

    public void moverComLimites(char comando, int minX, int maxX, int minY, int maxY) {
        int dx = 0;
        int dy = 0;

        switch (comando) {
            case 'w' -> dy = 1;
            case 's' -> dy = -1;
            case 'a' -> dx = -1;
            case 'd' -> dx = 1;
        }

        int novoX = this.x + dx;
        int novoY = this.y + dy;
        if (novoX >= minX && novoX <= maxX && novoY >= minY && novoY <= maxY) {
            this.x = novoX;
            this.y = novoY;
        }
    }

    public void perderVida() {
        this.vidas = Math.max(0, this.vidas - 1);
    }

    public int getVidas() {
        return vidas;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    @Override
    public void mover(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public String getSimbolo() {
        return "N";
    }
}
```

Crie o arquivo model/Missao.java com o conteúdo abaixo:

```java
package solidexercicio10.model;

import java.util.ArrayList;
import java.util.List;

public class Missao {
    private final Nave nave;
    private final List<Passageiro> passageiros;
    private final List<Asteroide> asteroides;
    private final List<Inimigo> inimigos;

    public Missao(Nave nave) {
        this.nave = nave;
        this.passageiros = new ArrayList<>();
        this.asteroides = new ArrayList<>();
        this.inimigos = new ArrayList<>();
    }

    public void adicionarPassageiro(Passageiro passageiro) {
        passageiros.add(passageiro);
    }

    public void adicionarAsteroide(Asteroide asteroide) {
        asteroides.add(asteroide);
    }

    public void adicionarInimigo(Inimigo inimigo) {
        inimigos.add(inimigo);
    }

    public Passageiro passagemNaPosicao() {
        for (Passageiro passageiro : passageiros) {
            if (passageiro.getX() == nave.getX() && passageiro.getY() == nave.getY()) {
                return passageiro;
            }
        }
        return null;
    }

    public boolean embarcarPassageiroNaPosicao() {
        Passageiro passageiro = passagemNaPosicao();
        if (passageiro == null || nave.getPassageiros().size() >= nave.getCapacidade()) {
            return false;
        }
        nave.embarcar(passageiro);
        passageiros.remove(passageiro);
        return true;
    }

    public void moverInimigos() {
        for (Inimigo inimigo : inimigos) {
            int dx = (int) (Math.random() * 3) - 1;
            int dy = (int) (Math.random() * 3) - 1;
            inimigo.mover(dx, dy);
        }
    }

    public boolean verificaColisao() {
        for (Asteroide asteroide : asteroides) {
            if (asteroide.getX() == nave.getX() && asteroide.getY() == nave.getY()) {
                return true;
            }
        }
        for (Inimigo inimigo : inimigos) {
            if (inimigo.getX() == nave.getX() && inimigo.getY() == nave.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean todosEmbarcados() {
        return passageiros.isEmpty();
    }

    public Nave getNave() {
        return nave;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    public List<Asteroide> getAsteroides() {
        return asteroides;
    }

    public List<Inimigo> getInimigos() {
        return inimigos;
    }
}
```

## Compilação e execução

Execute os comandos abaixo na raiz do projeto:

```bash
mkdir missaoMarteUnifor\solid\src\tutorial-exercicio10\out -Force | Out-Null
javac -d missaoMarteUnifor\solid\src\tutorial-exercicio10\out -sourcepath missaoMarteUnifor\solid\src\tutorial-exercicio10\src missaoMarteUnifor\solid\src\tutorial-exercicio10\src\solidexercicio10\Main.java
java -cp missaoMarteUnifor\solid\src\tutorial-exercicio10\out solidexercicio10.Main
```

## Reflexão final

- Qual princípio do SOLID você achou mais importante nesta atividade?
- Em que parte da refatoração você percebeu melhor a diferença entre um código acoplado e um código mais organizado?
- O que você aprendeu sobre manutenção e evolução do software?
- Se fosse adicionar uma nova funcionalidade, qual parte do código você alteraria com mais segurança?
