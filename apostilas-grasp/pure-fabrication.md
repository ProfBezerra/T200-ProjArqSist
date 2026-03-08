# Pure Fabrication

**DefiniÃ§Ã£o**: criar uma classe que nÃ£o representa um conceito do domÃ­nio, mas que reduz acoplamento ou aumenta coesÃ£o (ex.: classes utilitÃ¡rias, repositÃ³rios, adaptadores).

**Problema**: O que fazer quando o Especialista viola o Baixo Acoplamento ou a Alta CoesÃ£o?

**SoluÃ§Ã£o**: Crie uma classe artificial, nÃ£o pertencente ao domÃ­nio, para agrupar responsabilidades tÃ©cnicas.

Uso:

- Quando mover responsabilidade para fora das classes de domÃ­nio reduz acoplamento ou melhora a coesÃ£o.

Exemplo: `PedidoRepository` pode ser uma Pure Fabrication para separar persistÃªncia do domÃ­nio.

RelaÃ§Ã£o com SOLID

- **SRP:** Pure Fabrication separa responsabilidades (persistÃªncia, integraÃ§Ã£o) fora das entidades do domÃ­nio.
- **DIP:** ao isolar a persistÃªncia em uma classe fabricada, clientes podem depender de interfaces e nÃ£o de implementaÃ§Ãµes concretas.

## Exemplo evolutivo (Feira Livre)

`PedidoRepository` Ã© um exemplo de Pure Fabrication: nÃ£o Ã© um conceito do domÃ­nio

Exemplos de cÃ³digo

1) `PedidoRepository` â€” interface e implementaÃ§Ã£o em memÃ³ria (Pure Fabrication)

```java
package feira.grasp.repository;

import java.util.*;
import feira.grasp.Pedido;

public interface PedidoRepository {
  void salvar(Pedido pedido);
  Optional<Pedido> buscarPorId(String id);
  List<Pedido> listarTodos();
}
```

```java
package feira.grasp.repository;

import java.util.*;
import feira.grasp.Pedido;

public class PedidoRepositoryMemoria implements PedidoRepository {
  private final Map<String, Pedido> storage = new HashMap<>();

  @Override
  public void salvar(Pedido pedido) {
    storage.put(pedido.getId(), pedido);
  }

  @Override
  public Optional<Pedido> buscarPorId(String id) {
    return Optional.ofNullable(storage.get(id));
  }

  @Override
  public List<Pedido> listarTodos() {
    return new ArrayList<>(storage.values());
  }
}
```

2) Uso (trecho em `PedidoService`) â€” separa persistÃªncia do domÃ­nio

```java
// dentro de PedidoService
private final PedidoRepository repository;

public PedidoService(PedidoRepository repository) {
  this.repository = repository;
}

public void processar(Pedido pedido) {
  // lÃ³gica de domÃ­nio aqui
  repository.salvar(pedido);
}
```

Diagramas

1) Diagrama de classes â€” mostra a fÃ¡brica/padrao de Pure Fabrication e relaÃ§Ã£o com `Pedido`:

```mermaid
classDiagram
  class Pedido {
  - id: String
  - itens: List~PedidoItem~
  }

  interface PedidoRepository
  class PedidoRepositoryMemoria
  class PedidoService

  PedidoService --> PedidoRepository : usa
  PedidoRepository <|-- PedidoRepositoryMemoria
  Pedido "1" -- "*" PedidoItem
```

Arquivo externo para ediÃ§Ã£o: `diagrams/pure-fabrication-class.mmd`.

2) Diagrama de sequÃªncia â€” fluxo de persistÃªncia via `PedidoService` e `PedidoRepository`:

```mermaid
sequenceDiagram
  participant Usuario
  participant PedidoController
  participant PedidoService
  participant PedidoRepository

  Usuario->>PedidoController: criarPedido(dados)
  PedidoController->>PedidoService: criarPedido(dados)
  activate PedidoService
  PedidoService->>PedidoRepository: salvar(pedido)
  activate PedidoRepository
  PedidoRepository-->>PedidoService: ok
  deactivate PedidoRepository
  PedidoService-->>PedidoController: confirmado
  deactivate PedidoService
  PedidoController-->>Usuario: resposta
```

Arquivo externo para ediÃ§Ã£o: `diagrams/pure-fabrication-sequence.mmd`.

Notas pedagÃ³gicas

- Explique que `PedidoRepository` nÃ£o representa um conceito do domÃ­nio (nÃ£o Ã© "coisa" da feira), mas melhora o desenho ao isolar persistÃªncia.
- Mostre variaÃ§Ãµes: repositÃ³rio para JDBC, JPA ou adaptador para serviÃ§os externos â€” todas sÃ£o Pure Fabrications que evitam poluir entidades de domÃ­nio.

