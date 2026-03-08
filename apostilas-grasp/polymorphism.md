# Polymorphism

**DefiniÃ§Ã£o**: usar polimorfismo em vez de condicionais para variar comportamento com base no tipo de objeto.

**Problema**: Como tratar comportamentos que variam conforme o tipo sem usar ramificaÃ§Ãµes explÃ­citas (if/switch)?

**SoluÃ§Ã£o**: Atribua o comportamento variÃ¡vel ao tipo para o qual a variaÃ§Ã£o ocorre, utilizando operaÃ§Ãµes polimÃ³rficas

Quando aplicar:

- Quando o comportamento varia por tipo e vocÃª quer evitar `if/else` espalhados.

Exemplo: diferentes estratÃ©gias de desconto implementando uma interface `Desconto`.

RelaÃ§Ã£o com SOLID

- **OCP:** polimorfismo permite estender comportamentos (novas estratÃ©gias) sem modificar o cÃ³digo cliente.
- **LSP:** ao usar hierarquias de tipos, garanta que substituiÃ§Ãµes nÃ£o quebrem contratos esperados.

## Exemplo evolutivo (Feira Livre)

Quando o cÃ¡lculo de preÃ§o comeÃ§a a variar (descontos por fidelidade, promoÃ§Ãµes), extraimos uma interface `Desconto` e criamos implementaÃ§Ãµes:

```java
public interface Desconto { double aplicar(Pedido p); }
public class DescontoClienteFiel implements Desconto { /* ... */ }
```

`Pedido` pode aceitar uma polÃ­tica de desconto externa, aplicando polimorfismo em vez de `if/else`.

Trechos de cÃ³digo (exemplos simples)

1) `Desconto` â€” interface e implementaÃ§Ãµes:

```java
public interface Desconto {
    double aplicar(Pedido pedido);
}

public class DescontoClienteFiel implements Desconto {
    @Override
    public double aplicar(Pedido pedido) {
        // exemplo simples: 10% de desconto
        return pedido.calcularTotal() * 0.10;
    }
}

public class DescontoPromocional implements Desconto {
    private final double taxa;
    public DescontoPromocional(double taxa) { this.taxa = taxa; }
    @Override
    public double aplicar(Pedido pedido) { return pedido.calcularTotal() * taxa; }
}
```

2) `Pedido` aceita uma polÃ­tica de desconto externa:

```java
public class Pedido {
    private final List<PedidoItem> itens = new ArrayList<>();
    private Desconto desconto;

    public void setDesconto(Desconto desconto) { this.desconto = desconto; }

    public double calcularTotalComDesconto() {
        double total = calcularTotal();
        if (desconto != null) {
            return total - desconto.aplicar(this);
        }
        return total;
    }
}
```

Diagramas (Polymorphism)

1) Diagrama de classes â€” mostra a interface `Desconto` e suas implementaÃ§Ãµes, alÃ©m da associaÃ§Ã£o com `Pedido`:

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
  Pedido o-- Desconto : composiÃ§Ã£o/estratÃ©gia
    Pedido "1" *-- "1..*" PedidoItem : contÃ©m
```

Arquivo externo para ediÃ§Ã£o: `diagrams/polymorphism-class-clean.mmd`.

2) Diagrama de sequÃªncia â€” fluxo de aplicaÃ§Ã£o de desconto via estratÃ©gia:

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

Arquivo externo para ediÃ§Ã£o: `diagrams/polymorphism-sequence.mmd`.

