# Low Coupling (Baixo Acoplamento)

**DefiniÃ§Ã£o**: projetar classes de modo a minimizar dependÃªncias entre elas, reduzindo impacto de mudanÃ§as.

**Problema**: Como minimizar o impacto de mudanÃ§as e promover a reutilizaÃ§Ã£o?

**SoluÃ§Ã£o**: Atribua responsabilidades para manter as dependÃªncias entre classes o mais baixas possÃ­vel.

Vantagens:

- Facilita manutenÃ§Ã£o e evoluÃ§Ã£o
- Melhora testabilidade

EstratÃ©gias:

- Separar responsabilidades claras
- Usar interfaces/abstraÃ§Ãµes
- Aplicar Indirection quando apropriado

RelaÃ§Ã£o com SOLID

- **DIP:** reduzir acoplamento frequentemente exige dependÃªncia de abstraÃ§Ãµes ao invÃ©s de classes concretas.
- **ISP:** dividir interfaces grandes reduz acoplamento entre clientes e provedores.
- **SRP:** clareza nas responsabilidades diminui dependÃªncias desnecessÃ¡rias.

Exemplo evolutivo (Feira Livre)
Ao evoluir, extraÃ­mos `PedidoRepository` e programamos `PedidoService` para depender de uma interface `PedidoRepository` em vez de uma implementaÃ§Ã£o concreta. Isto Ã© `Low Coupling` aplicado:

```java
public interface PedidoRepository { void salvar(Pedido p); }
public class PedidoRepositoryMemoria implements PedidoRepository { /* implementaÃ§Ã£o */ }

public class PedidoService {
  private final PedidoRepository repo;
  public PedidoService(PedidoRepository repo) { this.repo = repo; }
}
```

ReferÃªncia: conceito de `PedidoRepository` em `diagrams/class-v2.mmd`.

Diagramas (Low Coupling)

1) Diagrama de classes â€” mostra como `PedidoService` depende da abstraÃ§Ã£o `PedidoRepository`, que pode ter uma implementaÃ§Ã£o `PedidoRepositoryMemoria`:

```mermaid
%% Diagrama de classes (versÃ£o simplificada)
classDiagram
  class Produto {
    - nome: String
    - preco: double
  }

  class PedidoItem {
    - produto: Produto
    - quantidade: int
  }

  class Pedido {
    - itens: List~PedidoItem~
  }

  class PedidoService {
    - repo: PedidoRepository
  }

  class PedidoRepository
  class PedidoRepositoryMemoria

  Produto "1" -- "*" PedidoItem
  Pedido "1" -- "*" PedidoItem
  PedidoService --> Pedido
  PedidoService ..> PedidoRepository : depende de
  PedidoRepository <|-- PedidoRepositoryMemoria
```

Arquivo externo para ediÃ§Ã£o: `diagrams/low-coupling-class.mmd`.

2) Diagrama de sequÃªncia â€” fluxo tÃ­pico quando o `PedidoService` delega persistÃªncia para a abstraÃ§Ã£o `PedidoRepository`, que por sua vez Ã© implementada por `PedidoRepositoryMemoria`:

```mermaid
sequenceDiagram
  participant Usuario
  participant PedidoController
  participant PedidoService
  participant Pedido
  participant PedidoRepository
  participant PedidoRepositoryMemoria

  Usuario->>PedidoController: novoPedido(dados)
  PedidoController->>PedidoService: criarPedido(dados)
  activate PedidoService
  PedidoService->>Pedido: new Pedido(dados)
  activate Pedido
  PedidoService->>PedidoRepository: salvar(pedido)
  activate PedidoRepository
  PedidoRepository->>PedidoRepositoryMemoria: armazenar(pedido)
  PedidoRepositoryMemoria-->>PedidoRepository: ok
  deactivate PedidoRepository
  PedidoService-->>PedidoController: pedidoCriado
  deactivate PedidoService
  PedidoController-->>Usuario: confirmarCriacao()

```

Arquivo externo para ediÃ§Ã£o: `diagrams/low-coupling-sequence.mmd`.

