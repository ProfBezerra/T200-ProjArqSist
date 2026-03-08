# Indirection

**DefiniÃ§Ã£o**: colocar um objeto intermediÃ¡rio para mediar entre duas entidades, reduzindo acoplamento e responsabilidades diretas.

**Problema**: Como evitar o acoplamento direto entre dois ou mais elementos?

**SoluÃ§Ã£o**: Atribua a responsabilidade de mediaÃ§Ã£o a um objeto intermediÃ¡rio.

Quando usar:

- Para interpor dependÃªncias entre mÃ³dulos que nÃ£o devem conhecer detalhes um do outro
- Para reduzir dependÃªncias cÃ­clicas

Exemplo: um `Gateway` entre serviÃ§o de pagamentos e domÃ­nio para isolar API externa.

RelaÃ§Ã£o com SOLID

- **DIP:** indirection ajuda a depender de abstraÃ§Ãµes e isolar mudanÃ§as em implementaÃ§Ãµes especÃ­ficas.
- **OCP:** ao interpor intermediÃ¡rios, vocÃª protege partes do sistema contra mudanÃ§as externas.

## Exemplo evolutivo (Feira Livre)


Se decidirmos integrar mÃºltiplos serviÃ§os de pagamento, inserimos um `PagamentoGateway` que faz indirection entre `PedidoService` e provedores externos. Assim, `PedidoService` nÃ£o conhece detalhes das APIs externas.

Trecho ilustrativo (interface):

```java
public interface PagamentoGateway { boolean pagar(Pedido p, PagamentoInfo info); }
```

ReferÃªncia: padrÃ£o Indirection protege o domÃ­nio das variaÃ§Ãµes de provedores.

Exemplos de cÃ³digo

1) `PagamentoGateway` â€” interface que isola provedores externos

```java
package feira.grasp.payment;

import feira.grasp.Pedido;

public interface PagamentoGateway {
  boolean pagar(Pedido pedido, PagamentoInfo info);
}
```

2) `PagamentoInfo` â€” pequeno DTO com dados de pagamento

```java
package feira.grasp.payment;

public class PagamentoInfo {
  private final String metodo;
  private final String referencia;

  public PagamentoInfo(String metodo, String referencia) {
    this.metodo = metodo;
    this.referencia = referencia;
  }

  public String getMetodo() { return metodo; }
  public String getReferencia() { return referencia; }
}
```

3) Adapter de um provedor (exemplo simplificado)

```java
package feira.grasp.payment;

import feira.grasp.Pedido;

public class FakePagamentoGateway implements PagamentoGateway {
  @Override
  public boolean pagar(Pedido pedido, PagamentoInfo info) {
    // implementaÃ§Ã£o de teste: sempre aprova
    System.out.println("[FakePagamento] processando pagamento: " + info.getMetodo());
    return true;
  }
}
```

4) Uso em `PedidoService` â€” indirection: `PedidoService` depende da abstraÃ§Ã£o `PagamentoGateway`

```java
// dentro de PedidoService
private final PagamentoGateway pagamentoGateway;

public PedidoService(PagamentoGateway pagamentoGateway) {
  this.pagamentoGateway = pagamentoGateway;
}

public boolean pagarPedido(Pedido pedido, PagamentoInfo info) {
  return pagamentoGateway.pagar(pedido, info);
}
```

Diagramas

1) Diagrama de classes â€” interface `PagamentoGateway` e adaptadores:

```mermaid
classDiagram
  class Pedido {
  - id: String
  - itens: List~PedidoItem~
  }

  interface PagamentoGateway
  class FakePagamentoGateway
  class PedidoService

  PedidoService --> PagamentoGateway : usa
  PagamentoGateway <|-- FakePagamentoGateway
  Pedido "1" -- "*" PedidoItem
```

Arquivo externo para ediÃ§Ã£o: `diagrams/indirection-class.mmd`.

2) Diagrama de sequÃªncia â€” fluxo de pagamento via indirection:

```mermaid
sequenceDiagram
  participant Usuario
  participant PedidoController
  participant PedidoService
  participant PagamentoGateway

  Usuario->>PedidoController: solicitarPagamento(pedidoId)
  PedidoController->>PedidoService: pagarPedido(pedido, info)
  activate PedidoService
  PedidoService->>PagamentoGateway: pagar(pedido, info)
  activate PagamentoGateway
  PagamentoGateway-->>PedidoService: pagamentoOk
  deactivate PagamentoGateway
  PedidoService-->>PedidoController: resultado
  deactivate PedidoService
  PedidoController-->>Usuario: confirmado
```

Arquivo externo para ediÃ§Ã£o: `diagrams/indirection-sequence.mmd`.

Notas pedagÃ³gicas

- Explique que a interface `PagamentoGateway` Ã© a abstraÃ§Ã£o que permite inserir/adaptar mÃºltiplos provedores sem alterar `PedidoService`.
- Mostre variaÃ§Ãµes: adaptadores para Stripe, PayPal, ou mocks de teste â€” todos implementam a mesma interface.

