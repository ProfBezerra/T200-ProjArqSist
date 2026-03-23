// =============================================================================
// Adapter — Feira Livre
// Compile: javac MainAdapter.java
// Run:     java MainAdapter
// =============================================================================

// ── objeto de resultado (exercicio 1) ────────────────────────────────────────

class ResultadoPagamento {
    final boolean aprovado;
    final int     codigoStatus;
    final String  mensagem;

    ResultadoPagamento(boolean aprovado, int codigoStatus, String mensagem) {
        this.aprovado     = aprovado;
        this.codigoStatus = codigoStatus;
        this.mensagem     = mensagem;
    }

    @Override
    public String toString() {
        return (aprovado ? "APROVADO" : "RECUSADO")
             + " [" + codigoStatus + "] " + mensagem;
    }
}

// ── interface interna do dominio ──────────────────────────────────────────────

interface GatewayPagamento {
    ResultadoPagamento cobrar(String pedidoId, double valor);
}

// ── API externa de terceiro (nao pode ser alterada) ───────────────────────────

class ApiPagamentoExterna {
    /** Retorna HTTP status code: 200 = sucesso, 402 = saldo insuf., 500 = erro */
    int efetuarCobranca(String referencia, double montante) {
        System.out.println("  [API-EXTERNA] ref=" + referencia
                         + " montante=R$ " + String.format("%.2f", montante));
        if (montante > 1000.0)  return 402;
        if (montante < 0)       return 500;
        return 200;
    }
}

// ── adapter principal ─────────────────────────────────────────────────────────

class GatewayPagamentoAdapter implements GatewayPagamento {
    private final ApiPagamentoExterna apiExterna;

    GatewayPagamentoAdapter(ApiPagamentoExterna apiExterna) {
        this.apiExterna = apiExterna;
    }

    @Override
    public ResultadoPagamento cobrar(String pedidoId, double valor) {
        int status = apiExterna.efetuarCobranca(pedidoId, valor);
        return switch (status) {
            case 200 -> new ResultadoPagamento(true,  200, "Pagamento aprovado");
            case 402 -> new ResultadoPagamento(false, 402, "Saldo insuficiente");
            case 500 -> new ResultadoPagamento(false, 500, "Erro interno da API");
            default  -> new ResultadoPagamento(false, status, "Resposta desconhecida");
        };
    }
}

// ── segundo adapter: provedor alternativo (exercicio 2) ──────────────────────

class ApiPagamentoAlternativa {
    /** Provedor diferente: retorna 1 (ok) ou 0 (falha) */
    int processarPagamento(String codigo, double valor) {
        System.out.println("  [API-ALTERNATIVA] codigo=" + codigo
                         + " valor=R$ " + String.format("%.2f", valor));
        return valor <= 500.0 ? 1 : 0;
    }
}

class GatewayAlternativoAdapter implements GatewayPagamento {
    private final ApiPagamentoAlternativa apiAlt;

    GatewayAlternativoAdapter(ApiPagamentoAlternativa apiAlt) {
        this.apiAlt = apiAlt;
    }

    @Override
    public ResultadoPagamento cobrar(String pedidoId, double valor) {
        int resultado = apiAlt.processarPagamento(pedidoId, valor);
        boolean ok = resultado == 1;
        return new ResultadoPagamento(ok, resultado,
            ok ? "Transacao aprovada" : "Transacao negada pelo provedor alternativo");
    }
}

// ── servico de pagamento do dominio ──────────────────────────────────────────

class PagamentoService {
    private final GatewayPagamento gateway;

    PagamentoService(GatewayPagamento gateway) {
        this.gateway = gateway;
    }

    void processar(String pedidoId, double valor) {
        ResultadoPagamento resultado = gateway.cobrar(pedidoId, valor);
        System.out.println("  -> Pedido " + pedidoId + ": " + resultado);
    }
}

// ── programa principal ────────────────────────────────────────────────────────

public class MainAdapter {
    public static void main(String[] args) {
        System.out.println("=== API Principal ===");
        PagamentoService svc1 = new PagamentoService(
            new GatewayPagamentoAdapter(new ApiPagamentoExterna()));
        svc1.processar("PED-001", 145.90);
        svc1.processar("PED-002", 1500.00);

        System.out.println("\n=== API Alternativa ===");
        PagamentoService svc2 = new PagamentoService(
            new GatewayAlternativoAdapter(new ApiPagamentoAlternativa()));
        svc2.processar("PED-003", 299.00);
        svc2.processar("PED-004", 750.00);
    }
}
