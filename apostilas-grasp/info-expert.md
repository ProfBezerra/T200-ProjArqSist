# Information Expert

DefiniÃ§Ã£o: atribuir responsabilidade Ã  classe que possui a informaÃ§Ã£o necessÃ¡ria para cumpri-la.

* **Problema:** Qual Ã© o princÃ­pio bÃ¡sico para atribuir responsabilidades aos objetos?
* **SoluÃ§Ã£o:** Atribua a responsabilidade Ã  classe que possui a informaÃ§Ã£o necessÃ¡ria para cumpri-la.

Quando aplicar:

- A classe jÃ¡ contÃ©m (ou pode acessar facilmente) os dados necessÃ¡rios.
- Evitar mover dados entre classes apenas para cumprir uma responsabilidade.

Exemplo: em um pedido, o cÃ¡lculo do total Ã© responsabilidade do `Pedido`, porque ele conhece seus itens.

Dicas:

- Prefira colocar comportamento onde estÃ£o os dados.
- Use com moderaÃ§Ã£o quando violaÃ§Ãµes de encapsulamento surgirem.

RelaÃ§Ã£o com SOLID

- **SRP (Single Responsibility):** colocar comportamento no `Information Expert` ajuda a manter responsabilidades Ãºnicas em classes.
- **OCP (Open/Closed):** ao manter lÃ³gica relacionada aos dados na mesma classe, vocÃª facilita estender comportamento sem alterar outras classes.

## Exemplo evolutivo (Feira Livre)

No nosso exemplo, o mÃ©todo `calcularTotal()` permanece em `Pedido` â€” um caso clÃ¡ssico de `Information Expert`. Ã€ medida que adicionamos lÃ³gica (ex.: desconto por item), comece mantendo o cÃ¡lculo no `Pedido` e extraia polÃ­ticas (por exemplo, estratÃ©gias de desconto) quando crescer a complexidade.

ReferÃªncia de cÃ³digo: `src/feira/grasp/Pedido.java` contÃ©m a responsabilidade de calcular o total.

Diagramas (Information Expert)

1) Diagrama de classes â€” mostra onde a responsabilidade de cÃ¡lculo estÃ¡ localizada:

```mermaid
classDiagram
  class Produto {
    +String nome
    +double preco
    +getNome()
    +getPreco()
  }

  class PedidoItem {
    - Produto produto
    - int quantidade
    + subtotal()
  }

  class Pedido {
    - List~PedidoItem~ itens
    + addItem(PedidoItem)
    + calcularTotal()
  }

  Produto "1" -- "*" PedidoItem : contem
  Pedido "1" -- "*" PedidoItem : possui
```

Arquivo externo para ediÃ§Ã£o: `diagrams/info-expert-class.mmd`.

2) Diagrama de sequÃªncia â€” fluxo do cÃ¡lculo do total:

```mermaid
sequenceDiagram
  participant Usuario
  participant Pedido
  participant PedidoItem
  participant Produto

  Usuario->>Pedido: calcularTotal()
  loop para cada item
    Pedido->>PedidoItem: subtotal()
    PedidoItem->>Produto: getPreco()
    Produto-->>PedidoItem: preco
    PedidoItem-->>Pedido: subtotal(valor)
  end
  Pedido-->>Usuario: total
```

Arquivo externo para ediÃ§Ã£o: `diagrams/info-expert-sequence.mmd`.

