# Low Coupling (Baixo Acoplamento)

**Definição**: projetar classes de modo a minimizar dependências entre elas, reduzindo impacto de mudanças.

**Problema**: Como minimizar o impacto de mudanças e promover a reutilização?

**Solução**: Atribua responsabilidades para manter as dependências entre classes o mais baixas possível.

Vantagens:

- Facilita manutenção e evolução
- Melhora testabilidade

Estratégias:

- Separar responsabilidades claras
- Usar interfaces/abstrações
- Aplicar Indirection quando apropriado

Relação com SOLID

- **DIP:** reduzir acoplamento frequentemente exige dependência de abstrações ao invés de classes concretas.
- **ISP:** dividir interfaces grandes reduz acoplamento entre clientes e provedores.
- **SRP:** clareza nas responsabilidades diminui dependências desnecessárias.

Exemplo evolutivo (Feira Livre)
Ao evoluir, extraímos `PedidoRepository` e programamos `PedidoService` para depender de uma interface `PedidoRepository` em vez de uma implementação concreta. Isto é `Low Coupling` aplicado:

```java
public interface PedidoRepository { void salvar(Pedido p); }
public class PedidoRepositoryMemoria implements PedidoRepository { /* implementação */ }

public class PedidoService {
  private final PedidoRepository repo;
  public PedidoService(PedidoRepository repo) { this.repo = repo; }
}
```

Referência: conceito de `PedidoRepository` em `diagrams/class-v2.mmd`.

Diagramas (Low Coupling)

1) Diagrama de classes — mostra como `PedidoService` depende da abstração `PedidoRepository`, que pode ter uma implementação `PedidoRepositoryMemoria`:

```mermaid
%% Diagrama de classes (versão simplificada)
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

Arquivo externo para edição: `diagrams/low-coupling-class.mmd`.

2) Diagrama de sequência — fluxo típico quando o `PedidoService` delega persistência para a abstração `PedidoRepository`, que por sua vez é implementada por `PedidoRepositoryMemoria`:

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

Arquivo externo para edição: `diagrams/low-coupling-sequence.mmd`.

