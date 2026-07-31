# GOF - Builder (Feira Livre)

## Definicao

Builder separa a construcao de um objeto complexo de sua representacao final, permitindo montar o objeto passo a passo.

## Aplicabilidade

Use o padrão Builder quando:

* o algoritmo para criação de um objeto complexo deve ser independente das partes que compõem o objeto e de como elas são montadas.
* o processo de construção deve permitir diferentes representações para o objeto que é construído.

## Estrutura

![1774807803219](image/APOSTILA/1774807803219.png)

## Participantes

* Builder
  * especifica uma interface abstrata para criação de partes de um objeto-produto.
* ConcreteBuilder
  * constrói e monta partes do produto pela implementação da interface de Builder;
  * define e mantém a representação que cria;
  * fornece uma interface para recuperação do produto
* Director
  * constrói um objeto usando a interface de Builder.
* Product
  * representa o objeto complexo em construção. ConcreteBuilder constrói a representação interna do produto e define o processo pelo qual ele é montado;
  * inclui classes que definem as partes constituintes, inclusive as interfaces para a montagem das partes no resultado final.

## Problema

Um pedido da feira pode ter muitos campos opcionais:

- cliente
- itens
- observacao
- tipo de entrega
- cupom

Sem Builder, o sistema acaba com construtores longos ou muitos overloads confusos.

## Solucao

Criar um builder para montar `Pedido` de forma legivel e segura.

## Diagrama de classes (Mermaid)

```mermaid
classDiagram
    class Cliente {
        +solicitarConstrucao()
    }
    class Director {
        +cestaSemanal(cliente) Product
    }
    class Builder {
        <<interface>>
        +cliente(cliente)
        +adicionarItem(item)
        +observacao(obs)
        +tipoEntrega(tipo)
        +cupom(cupom)
        +build() Product
    }
    class ConcreteBuilder {
        -cliente: String
        -itens: List~String~
        -observacao: String
        -tipoEntrega: String
        -cupom: String
        +build() Product
    }
    class Product {
        <<Pedido>>
    }
    class PedidoDiretor {
        +cestaSemanal(cliente) Pedido
    }
    class PedidoBuilder {
        <<ConcreteBuilder>>
        +cliente(cliente) PedidoBuilder
        +adicionarItem(item) PedidoBuilder
        +observacao(obs) PedidoBuilder
        +tipoEntrega(tipo) PedidoBuilder
        +cupom(cupom) PedidoBuilder
        +build() Pedido
    }
    class Pedido {
        -cliente: String
        -itens: List~String~
        -observacao: String
        -tipoEntrega: String
        -cupom: String
    }

    Cliente --> Director : pode usar
    Cliente --> Builder : pode usar direto
    Director --> Builder : define passos
    ConcreteBuilder ..|> Builder
    ConcreteBuilder --> Product : constroi

    PedidoDiretor ..> PedidoBuilder : orquestra
    PedidoBuilder ..> Pedido : constroi
```

No diagrama classico, o `Director` organiza a sequencia de montagem, o `Builder` define os passos, o `ConcreteBuilder` implementa esses passos e o `Product` e o objeto final. No codigo desta apostila, isso foi adaptado assim: `PedidoDiretor` faz o papel de `Director`, `Pedido.Builder` faz o papel de `ConcreteBuilder`, e `Pedido` e o `Product`.

Neste material, o `Director` continua opcional. Em exemplos modernos com builder fluente, muitas vezes o proprio cliente encadeia os passos e acaba assumindo esse papel. Quando queremos encapsular uma receita de montagem reutilizavel, criamos um director explicito.

## Exemplo

```java
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final String cliente;
    private final List<String> itens;
    private final String observacao;
    private final String tipoEntrega;
    private final String cupom;

    private Pedido(Builder builder) {
        this.cliente = builder.cliente;
        this.itens = List.copyOf(builder.itens);
        this.observacao = builder.observacao;
        this.tipoEntrega = builder.tipoEntrega;
        this.cupom = builder.cupom;
    }

    public static class Builder {
        private String cliente;
        private List<String> itens = new ArrayList<>();
        private String observacao = "";
        private String tipoEntrega = "RETIRADA";
        private String cupom = "";

        public Builder cliente(String cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder adicionarItem(String item) {
            this.itens.add(item);
            return this;
        }

        public Builder observacao(String observacao) {
            this.observacao = observacao;
            return this;
        }

        public Builder tipoEntrega(String tipoEntrega) {
            this.tipoEntrega = tipoEntrega;
            return this;
        }

        public Builder cupom(String cupom) {
            this.cupom = cupom;
            return this;
        }

        public Pedido build() {
            if (cliente == null || cliente.isBlank()) {
                throw new IllegalStateException("Cliente obrigatorio");
            }
            if (itens.isEmpty()) {
                throw new IllegalStateException("Pedido precisa de pelo menos um item");
            }
            return new Pedido(this);
        }
    }
}
```

Uso:

```java
Pedido pedido = new Pedido.Builder()
    .cliente("Maria")
    .adicionarItem("Tomate")
    .adicionarItem("Batata")
    .tipoEntrega("ENTREGA")
    .observacao("Sem sacola plastica")
    .build();

Pedido cesta = PedidoDiretor.cestaSemanal("Ana");
```

## Código completo

```java
import java.util.ArrayList;
import java.util.List;

// ── produto simples de dominio ────────────────────────────────────────────

class ItemPedido {
    private final String nome;
    private final double preco;

    public ItemPedido(String nome, double preco) {
        this.nome  = nome;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome + " (R$ " + String.format("%.2f", preco) + ")";
    }
}

// ── objeto que sera construido ────────────────────────────────────────────

class Pedido {
    private final String cliente;
    private final List<ItemPedido> itens;
    private final String observacao;
    private final String tipoEntrega;
    private final String cupom;

    private Pedido(Builder b) {
        this.cliente     = b.cliente;
        this.itens       = List.copyOf(b.itens);
        this.observacao  = b.observacao;
        this.tipoEntrega = b.tipoEntrega;
        this.cupom       = b.cupom;
    }

    @Override
    public String toString() {
        return "Pedido{cliente='" + cliente + "', itens=" + itens
             + ", entrega=" + tipoEntrega
             + ", cupom='" + cupom + "'"
             + ", obs='" + observacao + "'}";
    }

    // ── builder interno ───────────────────────────────────────────────────

    static class Builder {
        private String cliente;
        private final List<ItemPedido> itens = new ArrayList<>();
        private String observacao  = "";
        private String tipoEntrega = "RETIRADA";
        private String cupom       = "";

        Builder cliente(String cliente)          { this.cliente = cliente; return this; }
        Builder adicionarItem(ItemPedido item)   { this.itens.add(item);   return this; }
        Builder observacao(String obs)           { this.observacao = obs;  return this; }
        Builder tipoEntrega(String tipo)         { this.tipoEntrega = tipo; return this; }
        Builder cupom(String cupom)              { this.cupom = cupom;     return this; }

        Pedido build() {
            if (cliente == null || cliente.isBlank())
                throw new IllegalStateException("Cliente obrigatorio");
            if (itens.isEmpty())
                throw new IllegalStateException("Pedido precisa de pelo menos um item");
            return new Pedido(this);
        }
    }
}

// ── diretor: monta pedidos padrao ───────────────────────────────────────

class PedidoDiretor {
    static Pedido cestaSemanal(String cliente) {
        return new Pedido.Builder()
            .cliente(cliente)
            .adicionarItem(new ItemPedido("Tomate", 4.50))
            .adicionarItem(new ItemPedido("Alface", 2.00))
            .adicionarItem(new ItemPedido("Batata", 3.00))
            .adicionarItem(new ItemPedido("Cebola", 2.80))
            .adicionarItem(new ItemPedido("Cenoura", 3.20))
            .tipoEntrega("ENTREGA")
            .observacao("Cesta semanal padrao")
            .build();
    }
}

// ── demonstracao ──────────────────────────────────────────────────────────

public class MainBuilder {
    public static void main(String[] args) {
        Pedido pedido = new Pedido.Builder()
            .cliente("Maria")
            .adicionarItem(new ItemPedido("Tomate", 4.50))
            .adicionarItem(new ItemPedido("Batata", 3.00))
            .adicionarItem(new ItemPedido("Cebola", 2.80))
            .tipoEntrega("ENTREGA")
            .cupom("FEIRA10")
            .observacao("Sem sacola plastica")
            .build();

        System.out.println(pedido);

        // pedido minimo sem campos opcionais
        Pedido pedidoSimples = new Pedido.Builder()
            .cliente("Joao")
            .adicionarItem(new ItemPedido("Alface", 2.00))
            .build();

        System.out.println(pedidoSimples);

        Pedido cesta = PedidoDiretor.cestaSemanal("Ana");
        System.out.println(cesta);

        // tentativa invalida (sem cliente) - captura excecao esperada
        try {
            new Pedido.Builder()
                .adicionarItem(new ItemPedido("Queijo", 15.00))
                .build();
        } catch (IllegalStateException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}
```

Saída esperada:

```
Pedido{cliente='Maria', itens=[Tomate (R$ 4,50), Batata (R$ 3,00), Cebola (R$ 2,80)], entrega=ENTREGA, cupom='FEIRA10', obs='Sem sacola plastica'}
Pedido{cliente='Joao', itens=[Alface (R$ 2,00)], entrega=RETIRADA, cupom='', obs=''}
Pedido{cliente='Ana', itens=[Tomate (R$ 4,50), Alface (R$ 2,00), Batata (R$ 3,00), Cebola (R$ 2,80), Cenoura (R$ 3,20)], entrega=ENTREGA, cupom='', obs='Cesta semanal padrao'}
Erro esperado: Cliente obrigatorio
```

## Relacao com GRASP e SOLID

GRASP:

- Creator: o `Builder` assume responsabilidade de montar `Pedido` com consistencia.
- High Cohesion: validacoes de construcao ficam concentradas em `build()`.
- Low Coupling: cliente nao depende de construtores complexos nem detalhes internos de `Pedido`.

SOLID:

- SRP: `Pedido` representa estado; `Builder` concentra processo de construcao/validacao.
- OCP: novos campos opcionais podem ser adicionados por novos metodos fluentes.
- ISP: cliente usa apenas operacoes de montagem necessarias para o caso de uso.

## Beneficios

- Construcao legivel e com validacoes centralizadas.
- Evita construtores gigantes.
- Facilita objetos imutaveis.

## Riscos e anti-exemplo

Anti-exemplo:

- Usar Builder para objetos triviais com 2 campos.

Risco:

- Duplicar regra de validacao no Builder e em outros pontos.

## Exercicios

1. Incluir validacao de cupom no `build()`.
2. Escrever teste para validar erro quando nao houver itens.

## Checklist

- O objeto final tem muitos campos opcionais?
- A construcao ficou mais legivel do que com construtor tradicional?
- Validacoes essenciais estao no `build()`?
