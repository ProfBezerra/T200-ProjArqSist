# Polymorphism

**Definição**: usar polimorfismo em vez de condicionais para variar comportamento com base no tipo de objeto.

**Problema**: Como tratar comportamentos que variam conforme o tipo sem usar ramificações explícitas (if/switch)?

**Solução**: Atribua o comportamento variável ao tipo para o qual a variação ocorre, utilizando operações polimórficas.

Quando aplicar:

- Quando o comportamento varia por tipo e você quer evitar `if/else` espalhados.

Exemplo: `Professor`, `Engenheiro` e `Astronauta` implementam `Passageiro` e cada um retorna seu próprio `getPontosValor()` sem nenhum `if` no cliente.

Relação com SOLID

- **OCP:** polimorfismo permite estender comportamentos (novos tipos de passageiro ou perigo) sem modificar o código cliente.
- **LSP:** ao usar hierarquias de tipos, garanta que substituições não quebrem contratos esperados.

## Exemplo evolutivo (Missão Marte)

Sem polimorfismo, `JogoService` usaria um `if/switch` por tipo de passageiro ao calcular pontos:

```java
// ❌ SEM POLIMORFISMO — condicional por tipo
if (p instanceof Professor) pontos += 100;
else if (p instanceof Engenheiro) pontos += 200;
else if (p instanceof Astronauta) pontos += 300;
```

Com polimorfismo, cada tipo responde pela sua própria pontuação:

```java
// ✅ COM POLIMORFISMO — delega para o objeto
pontos += passageiro.getPontosValor();
```

Trechos de código

1) `Passageiro` — abstração e implementações concretas:

```java
public abstract class Passageiro {
    public abstract int getPontosValor();
    public abstract String getTipo();
}

public class Professor extends Passageiro {
    @Override public int getPontosValor() { return 100; }
    @Override public String getTipo()     { return "Professor"; }
}

public class Engenheiro extends Passageiro {
    @Override public int getPontosValor() { return 200; }
    @Override public String getTipo()     { return "Engenheiro"; }
}

public class Astronauta extends Passageiro {
    @Override public int getPontosValor() { return 300; }
    @Override public String getTipo()     { return "Astronauta"; }
}
```

2) `Perigo` — interface polimórfica para asteroides e inimigos:

```java
public interface Perigo {
    int getPenalidadePontos();
    String getTipo();
}

public class Asteroide implements Perigo {
    @Override public int getPenalidadePontos() { return 150; }
    @Override public String getTipo()          { return "Asteroide"; }
}

public class Inimigo implements Perigo {
    @Override public int getPenalidadePontos() { return 300; }
    @Override public String getTipo()          { return "Inimigo"; }
}
```

3) `JogoService` — usa polimorfismo, sem nenhum `instanceof`:

```java
public int verificarResgates(Missao missao, Nave nave) {
    int pontos = 0;
    Iterator<Passageiro> it = missao.getPassageiros().iterator();
    while (it.hasNext()) {
        Passageiro p = it.next();
        if (nave.getX() == p.getX() && nave.getY() == p.getY()) {
            pontos += p.getPontosValor(); // polimorfismo
            it.remove();
        }
    }
    return pontos;
}
```

Diagramas (Polymorphism)

1) Diagrama de classes:

```mermaid
classDiagram
  class Passageiro {
    <<abstract>>
    + getPontosValor() int
    + getTipo() String
  }

  class Professor
  class Engenheiro
  class Astronauta

  class Perigo {
    <<interface>>
    + getPenalidadePontos() int
  }
  class Asteroide
  class Inimigo

  Passageiro <|-- Professor
  Passageiro <|-- Engenheiro
  Passageiro <|-- Astronauta
  Perigo <|.. Asteroide
  Perigo <|.. Inimigo
```

2) Diagrama de sequência — JogoService delega via polimorfismo:

```mermaid
sequenceDiagram
  participant JogoService
  participant Passageiro
  participant Perigo

  JogoService->>Passageiro: getPontosValor()
  Passageiro-->>JogoService: 100 (Professor) / 200 (Engenheiro) / 300 (Astronauta)

  JogoService->>Perigo: getPenalidadePontos()
  Perigo-->>JogoService: 150 (Asteroide) / 300 (Inimigo)
```


Diagramas (Polymorphism)

1) Diagrama de classes — mostra a interface `Desconto` e suas implementações, além da associação com `Pedido`:

```mermaid
classDiagram
  class Desconto {
    <<interface>>
    +aplicar(Pedido pedido) double
  }

  class DescontoClienteFiel {
    +aplicar(Pedido pedido) double
  }

  class DescontoPromocional {
    +aplicar(Pedido pedido) double
  }

  class Pedido {
    -List~PedidoItem~ itens
    -Desconto desconto
    +setDesconto(Desconto d)
    +calcularTotal() double
  }

    class PedidoItem {
        -String produto
        -double preco
    }

  Desconto <|.. DescontoClienteFiel
  Desconto <|.. DescontoPromocional
  Pedido o-- Desconto : composição/estratégia
    Pedido "1" *-- "1..*" PedidoItem : contém
```

Arquivo externo para edição: `diagrams/polymorphism-class-clean.mmd`.

2) Diagrama de sequência — fluxo de aplicação de desconto via estratégia:

```mermaid
sequenceDiagram
  participant Usuario
  participant PedidoController
  participant PedidoService
  participant Desconto
  participant Pedido

  Usuario->>PedidoController: aplicarDesconto(pedidoId, tipo)
  PedidoController->>PedidoService: aplicarDesconto(pedidoId, desconto)
  activate PedidoService
  PedidoService->>Desconto: aplicar(pedido)
  activate Desconto
  Desconto-->>PedidoService: valorDesconto
  deactivate Desconto
  PedidoService-->>PedidoController: descontoAplicado
  deactivate PedidoService
  PedidoController-->>Usuario: confirmado
```

Arquivo externo para edição: `diagrams/polymorphism-sequence.mmd`.

