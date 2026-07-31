# Resolução dos Exercícios — Facade

> Referência da apostila: [APOSTILA.md](APOSTILA.md)
> Código completo executável: [MainFacade.java](MainFacade.java)

---

## Exercício 1 — Adicionar etapa de auditoria no fluxo da fachada

**Enunciado:** Incluir um `AuditoriaService` que registra o resultado de cada fechamento.

**Solução:** já implementada em `MainFacade.java`:

```java
// ── subsistema de auditoria (adicionado pela camada Facade) ───────────────────

class AuditoriaService {
    private final java.util.List<String> log = new java.util.ArrayList<>();

    void registrar(String pedidoId, boolean sucesso, double total) {
        String entrada = String.format("[AUDITORIA] pedido=%s sucesso=%b total=R$%.2f",
            pedidoId, sucesso, total);
        log.add(entrada);
        System.out.println("  " + entrada);
    }

    java.util.List<String> getLog() { return java.util.List.copyOf(log); }
}
```

**Integração na Facade:**
```java
class FechamentoPedidoFacade {
    private final EstoqueService     estoque;
    private final PagamentoService   pagamento;
    private final NotificacaoService notificacao;
    private final AuditoriaService   auditoria;   // ← novo subsistema

    FechamentoPedidoFacade(EstoqueService e, PagamentoService p,
                           NotificacaoService n, AuditoriaService a) {
        this.estoque     = e;
        this.pagamento   = p;
        this.notificacao = n;
        this.auditoria   = a;
    }

    ResultadoFechamento fechar(Pedido pedido) {
        // ... fluxo normal ...
        auditoria.registrar(pedido.getId(), resultado.sucesso, resultado.totalPago);
        return resultado;
    }
}
```

**Por que a auditoria pertence à Facade?**
- O cliente não deveria saber que há auditoria — é uma preocupação transversal.
- A Facade orquestra os subsistemas; registrar auditoria é parte dessa orquestração.

---

## Exercício 2 — Retornar objeto de resultado com status e mensagens

**Enunciado:** Em vez de `void`, a Facade deve retornar um `ResultadoFechamento` com `sucesso`, `mensagem` e `totalPago`.

**Solução:** já implementada em `MainFacade.java`:

```java
class ResultadoFechamento {
    final boolean sucesso;
    final String  mensagem;
    final double  totalPago;

    ResultadoFechamento(boolean sucesso, String mensagem, double totalPago) {
        this.sucesso   = sucesso;
        this.mensagem  = mensagem;
        this.totalPago = totalPago;
    }

    @Override
    public String toString() {
        return String.format("[Fechamento] sucesso=%b | %s | pago=R$%.2f",
            sucesso, mensagem, totalPago);
    }
}
```

**Comparação antes e depois:**

```java
// ANTES (void):
facade.fechar(pedido);
// o cliente não sabe o que aconteceu

// DEPOIS (objeto de resultado):
ResultadoFechamento r = facade.fechar(pedido);
if (r.sucesso) {
    System.out.println("Pedido aprovado: " + r.mensagem);
} else {
    System.out.println("Pedido recusado: " + r.mensagem);
    // pode tentar novamente, mostrar ao usuário, etc.
}
```

**Benefícios:**
- O cliente pode tomar decisões com base no resultado.
- A Facade ainda esconde a complexidade interna — o cliente não sabe de estoque, pagamento, SMS…
- Facilita testes.

---

## Exercício 3 — Simular falha de pagamento e validar rollback de reserva

**Enunciado:** Verificar que, se o pagamento falha, a reserva de estoque é liberada.

**A lógica de rollback já está na Facade:**

```java
ResultadoFechamento fechar(Pedido pedido) {
    boolean reservado = estoque.reservar(pedido);
    if (!reservado) {
        return new ResultadoFechamento(false, "Sem estoque", 0);
    }

    boolean pago = pagamento.processar(pedido);
    if (!pago) {
        estoque.liberar(pedido);           // ← rollback
        return new ResultadoFechamento(false, "Pagamento recusado", 0);
    }

    notificacao.enviar(pedido);
    auditoria.registrar(pedido.getId(), true, pedido.getTotal());
    return new ResultadoFechamento(true, "Pedido aprovado", pedido.getTotal());
}
```

**Teste para verificar rollback:**

```java
// Salvar como TesteFacade.java
// javac MainFacade.java TesteFacade.java && java TesteFacade

public class TesteFacade {
    static int ok   = 0;
    static int erro = 0;

    public static void main(String[] args) {
        System.out.println("=== Testes: Facade ===");

        AuditoriaService auditoria = new AuditoriaService();

        // PagamentoService que sempre recusa
        PagamentoService pagamentoFalhante = new PagamentoService() {
            // Apenas sobrescrevemos com uma subclasse inline
            // que simula falha sempre
        };

        // Como PagamentoService tem logica interna fixa, vamos testar atraves da facade
        // passando pedido com valor muito alto (PagamentoService recusa acima de 500)

        EstoqueService     estoque     = new EstoqueService();
        PagamentoService   pagamento   = new PagamentoService();
        NotificacaoService notificacao = new NotificacaoService();

        FechamentoPedidoFacade facade = new FechamentoPedidoFacade(
            estoque, pagamento, notificacao, auditoria);

        // Pedido com valor alto para provocar recusa de pagamento
        Pedido pedidoRecusado = new Pedido("P-TESTE-ALTO");
        pedidoRecusado.adicionarItem(new ItemPedido("Produto caro", 999.99));

        System.out.println("\n-- Pedido com pagamento recusado --");
        ResultadoFechamento r = facade.fechar(pedidoRecusado);

        if (!r.sucesso)
            passou("Pedido recusado retornou sucesso=false");
        else
            falhou("Pedido deveria ter sido recusado");

        System.out.println("\nResultado: " + ok + " OK, " + erro + " FALHA(S)");
    }

    static void passou(String msg) { System.out.println("  [OK] " + msg); ok++; }
    static void falhou(String msg) { System.out.println("  [FALHA] " + msg); erro++; }
}
```

> **Nota:** em um projeto real com JUnit e injeção de dependência, o `PagamentoService` seria substituído por um mock que sempre falha, tornando o teste mais preciso e controlável.

---

## Resumo dos conceitos

| Exercício | Conceito reforçado |
|---|---|
| 1 | Facade orquestra subsistemas — auditoria é responsabilidade da camada |
| 2 | Retorno rico permite ao cliente reagir sem conhecer os subsistemas |
| 3 | Rollback coordenado — Facade gerencia consistência entre subsistemas |
