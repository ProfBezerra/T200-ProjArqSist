// =============================================================================
// Abstract Factory — Feira Livre
// Compile: javac MainAbstractFactory.java
// Run:     java MainAbstractFactory
// =============================================================================

// ── interfaces dos produtos da familia ───────────────────────────────────────

interface EtiquetaProduto {
    String gerarTexto(String nomeProduto, double preco);
}

interface ComprovanteVenda {
    String gerar(String pedidoId, double total);
}

interface NotificadorVenda {
    void notificar(String pedidoId);
}

// ── interface da fabrica abstrata ─────────────────────────────────────────────

interface VendaFactory {
    EtiquetaProduto  criarEtiqueta();
    ComprovanteVenda criarComprovante();
    NotificadorVenda criarNotificador();
}

// ── familia: canal presencial ─────────────────────────────────────────────────

class VendaPresencialFactory implements VendaFactory {
    @Override
    public EtiquetaProduto criarEtiqueta() {
        return (nome, preco) ->
            "ETQ-PRESENCIAL | " + nome + " | R$ " + String.format("%.2f", preco);
    }

    @Override
    public ComprovanteVenda criarComprovante() {
        return (pedidoId, total) ->
            "=== COMPROVANTE IMPRESSO === #" + pedidoId
            + " | Total: R$ " + String.format("%.2f", total);
    }

    @Override
    public NotificadorVenda criarNotificador() {
        return pedidoId ->
            System.out.println("[PAINEL LOCAL] Pedido " + pedidoId + " finalizado.");
    }
}

// ── familia: canal online ─────────────────────────────────────────────────────

class VendaOnlineFactory implements VendaFactory {
    @Override
    public EtiquetaProduto criarEtiqueta() {
        return (nome, preco) ->
            "ETQ-ONLINE-QR | " + nome + " | R$ " + String.format("%.2f", preco)
            + " | qr://feira/" + nome.toLowerCase().replace(" ", "-");
    }

    @Override
    public ComprovanteVenda criarComprovante() {
        return (pedidoId, total) ->
            ">>> COMPROVANTE DIGITAL <<< #" + pedidoId
            + " | Total: R$ " + String.format("%.2f", total)
            + " | email enviado";
    }

    @Override
    public NotificadorVenda criarNotificador() {
        return pedidoId ->
            System.out.println("[EMAIL/SMS] Pedido " + pedidoId + " confirmado — aguarde entrega.");
    }
}

// ── familia: canal atacado ────────────────────────────────────────────────────

class VendaAtacadoFactory implements VendaFactory {
    @Override
    public EtiquetaProduto criarEtiqueta() {
        return (nome, preco) ->
            "ETQ-ATACADO | " + nome
            + " | R$ " + String.format("%.2f", preco)
            + " | MIN 10 unidades";
    }

    @Override
    public ComprovanteVenda criarComprovante() {
        return (pedidoId, total) ->
            "NOTA FISCAL ATACADO #" + pedidoId
            + " | Total: R$ " + String.format("%.2f", total)
            + " | CNPJ obrigatorio";
    }

    @Override
    public NotificadorVenda criarNotificador() {
        return pedidoId ->
            System.out.println("[ERP] Pedido atacado " + pedidoId + " registrado para faturamento.");
    }
}

// ── servico de finalizacao (independente da familia) ──────────────────────────

class FinalizacaoVendaService {
    private final VendaFactory factory;

    FinalizacaoVendaService(VendaFactory factory) {
        this.factory = factory;
    }

    void finalizar(String pedidoId, String nomeProduto, double preco) {
        EtiquetaProduto  etiqueta    = factory.criarEtiqueta();
        ComprovanteVenda comprovante = factory.criarComprovante();
        NotificadorVenda notificador = factory.criarNotificador();

        System.out.println(etiqueta.gerarTexto(nomeProduto, preco));
        System.out.println(comprovante.gerar(pedidoId, preco));
        notificador.notificar(pedidoId);
        System.out.println();
    }
}

// ── programa principal ────────────────────────────────────────────────────────

public class MainAbstractFactory {
    public static void main(String[] args) {
        System.out.println("=== Canal Presencial ===");
        new FinalizacaoVendaService(new VendaPresencialFactory())
            .finalizar("PED-001", "Queijo Minas", 22.00);

        System.out.println("=== Canal Online ===");
        new FinalizacaoVendaService(new VendaOnlineFactory())
            .finalizar("PED-002", "Mel Silvestre", 35.00);

        System.out.println("=== Canal Atacado ===");
        new FinalizacaoVendaService(new VendaAtacadoFactory())
            .finalizar("PED-003", "Feijao Carioca", 890.00);
    }
}
