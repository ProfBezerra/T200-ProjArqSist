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

Exemplo evolutivo (Missão Marte)

Ao evoluir, extraímos `IRankingRepository` e programamos `JogoService` para depender da interface em vez de uma implementação concreta. Isto é `Low Coupling` aplicado:

```java
public interface IRankingRepository {
    void salvar(RankingEntry entry);
    List<RankingEntry> carregar();
}
public class RankingRepositoryArquivo implements IRankingRepository { /* implementação */ }

public class JogoService {
    private final IRankingRepository ranking;
    public JogoService(IRankingRepository ranking) { this.ranking = ranking; }
}
```

Diagramas (Low Coupling)

1) Diagrama de classes — mostra como `JogoService` depende da abstração `IRankingRepository`:

```mermaid
classDiagram
  class Nave {
    - x: int
    - y: int
  }

  class Missao {
    - passageiros: List
    - perigos: List
  }

  class JogoService {
    - ranking: IRankingRepository
    + executarPartida(missao, nave, scanner) int
  }

  class IRankingRepository {
    <<interface>>
  }
  class RankingRepositoryArquivo

  JogoService --> Missao
  JogoService --> Nave
  JogoService ..> IRankingRepository : depende de
  IRankingRepository <|.. RankingRepositoryArquivo
```

2) Diagrama de sequência — `JogoService` delega persistência para a abstração `IRankingRepository`:

```mermaid
sequenceDiagram
  participant GameController
  participant JogoService
  participant IRankingRepository
  participant RankingRepositoryArquivo

  GameController->>JogoService: executarPartida(missao, nave, scanner)
  activate JogoService
  JogoService->>JogoService: loop de jogo...
  JogoService-->>GameController: pontuacaoFinal
  deactivate JogoService

  GameController->>IRankingRepository: salvar(entry)
  activate IRankingRepository
  IRankingRepository->>RankingRepositoryArquivo: persistir(entry)
  RankingRepositoryArquivo-->>IRankingRepository: ok
  deactivate IRankingRepository
```


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

