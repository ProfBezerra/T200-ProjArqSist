// =============================================================================
// Observer — Feira Livre
// Compile: javac MainObserver.java
// Run:     java MainObserver
// =============================================================================

import java.util.ArrayList;
import java.util.List;

// ── interface do observador ───────────────────────────────────────────────────

interface ObservadorPreco {
    void precoAlterado(Produto produto, double precoAntigo, double precoNovo);
}

// ── sujeito observado ─────────────────────────────────────────────────────────

class Produto {
    private final String nome;
    private double preco;
    private final List<ObservadorPreco> observadores = new ArrayList<>();

    Produto(String nome, double preco) {
        this.nome  = nome;
        this.preco = preco;
    }

    void adicionarObservador(ObservadorPreco obs) { observadores.add(obs); }
    void removerObservador(ObservadorPreco obs)    { observadores.remove(obs); }

    void alterarPreco(double novoPreco) {
        if (novoPreco == this.preco) return; // sem mudanca real
        double antigo = this.preco;
        this.preco    = novoPreco;
        for (ObservadorPreco obs : observadores) {
            obs.precoAlterado(this, antigo, novoPreco);
        }
    }

    String getNome()  { return nome; }
    double getPreco() { return preco; }
}

// ── observadores concretos ────────────────────────────────────────────────────

class NotificadorEmail implements ObservadorPreco {
    private final String email;

    NotificadorEmail(String email) { this.email = email; }

    @Override
    public void precoAlterado(Produto p, double antigo, double novo) {
        System.out.printf("  [EMAIL -> %s] %s: R$%.2f -> R$%.2f%n",
            email, p.getNome(), antigo, novo);
    }
}

class NotificadorSMS implements ObservadorPreco {
    private final String telefone;

    NotificadorSMS(String telefone) { this.telefone = telefone; }

    @Override
    public void precoAlterado(Produto p, double antigo, double novo) {
        System.out.printf("  [SMS -> %s] ALERTA: %s agora custa R$%.2f%n",
            telefone, p.getNome(), novo);
    }
}

class PainelFeira implements ObservadorPreco {
    @Override
    public void precoAlterado(Produto p, double antigo, double novo) {
        String tendencia = novo > antigo ? "↑ SUBIU" : "↓ BAIXOU";
        System.out.printf("  [PAINEL] %s %s (era R$%.2f, agora R$%.2f, %.1f%%)%n",
            p.getNome(), tendencia, antigo, novo, ((novo - antigo) / antigo) * 100);
    }
}

class AuditoriaPreco implements ObservadorPreco {
    private final List<String> log = new ArrayList<>();

    @Override
    public void precoAlterado(Produto p, double antigo, double novo) {
        String entrada = String.format("[AUDITORIA] %s: R$%.2f -> R$%.2f", p.getNome(), antigo, novo);
        log.add(entrada);
        System.out.println("  " + entrada);
    }

    List<String> getLog() { return List.copyOf(log); }
}

// ── programa principal ────────────────────────────────────────────────────────

public class MainObserver {
    public static void main(String[] args) {
        Produto tomate = new Produto("Tomate", 4.50);

        ObservadorPreco emailMaria  = new NotificadorEmail("maria@email.com");
        ObservadorPreco smsJoao     = new NotificadorSMS("(85)99999-0001");
        ObservadorPreco painel      = new PainelFeira();
        AuditoriaPreco  auditoria   = new AuditoriaPreco();

        tomate.adicionarObservador(emailMaria);
        tomate.adicionarObservador(smsJoao);
        tomate.adicionarObservador(painel);
        tomate.adicionarObservador(auditoria);

        System.out.println("=== Alta de preco ===");
        tomate.alterarPreco(5.80);

        System.out.println("\n=== Reducao de preco ===");
        tomate.alterarPreco(3.90);

        System.out.println("\n=== Preco igual (nao dispara notificacao) ===");
        tomate.alterarPreco(3.90);
        System.out.println("  (nenhum observador notificado)");

        System.out.println("\n=== Remocao de SMS e nova alteracao ===");
        tomate.removerObservador(smsJoao);
        tomate.alterarPreco(4.20);

        System.out.println("\n=== Historico de auditoria ===");
        auditoria.getLog().forEach(e -> System.out.println("  " + e));
    }
}
