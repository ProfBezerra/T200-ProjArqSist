# Resolução dos Exercícios — Adapter

> Referência da apostila: [APOSTILA.md](APOSTILA.md)
> Código completo executável: [MainAdapter.java](MainAdapter.java)

---

## Exercício 1 — Adaptar retorno com código e mensagem de erro

**Enunciado:** Em vez de retornar `boolean`, o gateway deve retornar um objeto com `aprovado`, `codigo` e `mensagem`.

**Solução:** a classe `ResultadoPagamento` e o adapter atualizado já estão em `MainAdapter.java`:

```java
// ── objeto de resultado ───────────────────────────────────────────────────────

class ResultadoPagamento {
    final boolean aprovado;
    final String  codigo;
    final String  mensagem;

    ResultadoPagamento(boolean aprovado, String codigo, String mensagem) {
        this.aprovado  = aprovado;
        this.codigo    = codigo;
        this.mensagem  = mensagem;
    }

    @Override
    public String toString() {
        return String.format("[%s] codigo=%s | %s",
            aprovado ? "APROVADO" : "RECUSADO", codigo, mensagem);
    }
}

// ── interface do domínio (atualizada) ─────────────────────────────────────────

interface GatewayPagamento {
    ResultadoPagamento processar(String pedidoId, double valor);
}

// ── adapter retornando objeto estruturado ─────────────────────────────────────

class GatewayPagamentoAdapter implements GatewayPagamento {
    private final ApiPagamentoExterna api;

    GatewayPagamentoAdapter(ApiPagamentoExterna api) { this.api = api; }

    @Override
    public ResultadoPagamento processar(String pedidoId, double valor) {
        try {
            String resposta = api.efetuarCobranca(pedidoId, valor);
            boolean ok = resposta.startsWith("OK");
            String  codigo = ok ? "PAG_OK"      : "PAG_ERR";
            String  msg    = ok ? "Pagamento aprovado" : "Recusado: " + resposta;
            return new ResultadoPagamento(ok, codigo, msg);
        } catch (Exception ex) {
            return new ResultadoPagamento(false, "PAG_EX", "Excecao: " + ex.getMessage());
        }
    }
}
```

**Por que `ResultadoPagamento` é melhor do que `boolean`?**
- Fornece ao chamador informações sobre o motivo da recusa.
- Permite logging e auditoria mais ricos.
- Facilita tratamento diferenciado por código (`PAG_OK`, `PAG_ERR`, `PAG_TIMEOUT`…).

---

## Exercício 2 — Criar segundo adapter para outro provedor

**Enunciado:** O sistema precisa suportar um segundo gateway de pagamento (API diferente).

**Solução:** a `ApiPagamentoAlternativa` e o `GatewayAlternativoAdapter` já estão em `MainAdapter.java`:

```java
// ── API do segundo provedor (legada/externa diferente) ────────────────────────

class ApiPagamentoAlternativa {
    /** Retorna 0 = aprovado, > 0 = codigo de erro */
    int cobrar(String referencia, double quantia, String moeda) {
        System.out.printf("    [API-ALT] cobrar(%s, %.2f, %s)%n", referencia, quantia, moeda);
        // Simula recusa para valores altos
        return quantia > 100 ? 42 : 0;
    }
}

// ── segundo adapter ──────────────────────────────────────────────────────────

class GatewayAlternativoAdapter implements GatewayPagamento {
    private final ApiPagamentoAlternativa api;

    GatewayAlternativoAdapter(ApiPagamentoAlternativa api) { this.api = api; }

    @Override
    public ResultadoPagamento processar(String pedidoId, double valor) {
        int codigo = api.cobrar("REF-" + pedidoId, valor, "BRL");
        if (codigo == 0)
            return new ResultadoPagamento(true,  "ALT_OK",  "Aprovado pelo gateway alternativo");
        return new ResultadoPagamento(false, "ALT_" + codigo,
            "Recusado pelo gateway alternativo (cod=" + codigo + ")");
    }
}
```

**Ponto central do padrão:** `PagamentoService` usa apenas `GatewayPagamento`. Ao instanciar com `GatewayAlternativoAdapter`, a implementação muda **sem alterar nenhum código cliente**:

```java
// Usando o gateway original
PagamentoService svc1 = new PagamentoService(new GatewayPagamentoAdapter(new ApiPagamentoExterna()));

// Usando o gateway alternativo — código cliente identico!
PagamentoService svc2 = new PagamentoService(new GatewayAlternativoAdapter(new ApiPagamentoAlternativa()));
```

---

## Exercício 3 — Simular falha da API e validar tratamento

**Enunciado:** Verificar que o adapter trata exceções da API e não deixa propagá-las para o chamador.

**Solução:** a simulação de falha já acontece em `MainAdapter.java` quando a API lança `RuntimeException`. O adapter captura e devolve `ResultadoPagamento` com `aprovado=false`:

```java
// Para adicionar um teste explicito, crie uma API que sempre lanca excecao:
class ApiPagamentoFalhante {
    String efetuarCobranca(String pedidoId, double valor) {
        throw new RuntimeException("Timeout ao conectar ao gateway");
    }
}

// Copie o adapter wrapper (precisa acessar a API interna):
class GatewayFalhanteAdapter implements GatewayPagamento {
    private final ApiPagamentoFalhante api = new ApiPagamentoFalhante();

    @Override
    public ResultadoPagamento processar(String pedidoId, double valor) {
        try {
            String resp = api.efetuarCobranca(pedidoId, valor);
            return new ResultadoPagamento(true, "OK", resp);
        } catch (Exception ex) {
            return new ResultadoPagamento(false, "PAG_EX", "Excecao: " + ex.getMessage());
        }
    }
}
```

**Teste manual:**
```java
// Adicionar ao main ou TesteAdapter.java
GatewayPagamento gw = new GatewayFalhanteAdapter();
ResultadoPagamento r = gw.processar("P999", 50.00);
assert !r.aprovado        : "Deveria ser recusado";
assert r.codigo.equals("PAG_EX") : "Codigo deveria ser PAG_EX";
System.out.println("Falha tratada corretamente: " + r);
```

**O que isso valida:**
- O adapter isola o chamador de detalhes de falha da API.
- `PagamentoService` nunca lida com `RuntimeException` — recebe sempre um objeto estruturado.

---

## Resumo dos conceitos

| Exercício | Conceito reforçado |
|---|---|
| 1 | Interface estável do domínio + retorno rico (ISP / encapsulamento) |
| 2 | Troca de provedor sem alterar código cliente (DIP + OCP) |
| 3 | Adapter como camada de proteção contra falhas externas (robustez) |
