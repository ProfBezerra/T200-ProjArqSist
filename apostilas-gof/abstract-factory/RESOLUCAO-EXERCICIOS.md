# Resolução dos Exercícios — Abstract Factory

> Referência da apostila: [APOSTILA.md](APOSTILA.md)
> Código completo executável: [MainAbstractFactory.java](MainAbstractFactory.java)

---

## Exercício 1 — Criar `VendaAtacadoFactory` com comprovante próprio

**Enunciado:** Implementar uma fábrica para o canal de atacado, com etiqueta e comprovante próprios.

**Solução:** já implementada em `MainAbstractFactory.java`. Trechos relevantes:

```java
// ── produtos da família atacado ───────────────────────────────────────────────

class EtiquetaAtacado implements EtiquetaProduto {
    @Override
    public String gerarEtiqueta(String nome, double preco, int qtd) {
        double precoUnitAtacado = preco * 0.80;          // 20 % de desconto
        double precoTotal       = precoUnitAtacado * qtd;
        return String.format("[ATACADO] %s | unit=R$%.2f | x%d | total=R$%.2f",
            nome, precoUnitAtacado, qtd, precoTotal);
    }
}

class ComprovanteAtacado implements ComprovanteVenda {
    @Override
    public String emitir(double total, String cliente) {
        return String.format(
            "=== NOTA FISCAL ATACADO ===\n" +
            "  Empresa/cliente: %s\n"        +
            "  Total bruto:     R$%.2f\n"    +
            "  Desconto atacado: 20%%\n"     +
            "  Total liquido:   R$%.2f"      ,
            cliente, total / 0.80, total);
    }
}

// ── fábrica concreta ──────────────────────────────────────────────────────────

class VendaAtacadoFactory implements VendaFactory {
    @Override public EtiquetaProduto  criarEtiqueta()     { return new EtiquetaAtacado(); }
    @Override public ComprovanteVenda criarComprovante()  { return new ComprovanteAtacado(); }
}
```

**Ponto-chave:** Para adicionar um novo canal (atacado), bastou criar 3 novas classes: `EtiquetaAtacado`, `ComprovanteAtacado` e `VendaAtacadoFactory`. O `FinalizacaoVendaService` **não precisou ser alterado**.

---

## Exercício 2 — Adicionar `NotificadorVenda` como terceiro produto da família

**Enunciado:** Cada família deve agora criar também um notificador de venda (e-mail, WhatsApp, sem notificação).

**Solução:** já implementada em `MainAbstractFactory.java`. Veja a extensão da interface e das fábricas:

```java
// ── novo produto da família ───────────────────────────────────────────────────

interface NotificadorVenda {
    void notificar(String cliente, double total);
}

class NotificadorEmail implements NotificadorVenda {
    @Override
    public void notificar(String cliente, double total) {
        System.out.printf("  [EMAIL] Venda de R$%.2f confirmada para %s%n", total, cliente);
    }
}

class NotificadorWhatsApp implements NotificadorVenda {
    @Override
    public void notificar(String cliente, double total) {
        System.out.printf("  [WHATSAPP] Ola %s! Sua compra de R$%.2f foi confirmada.%n", cliente, total);
    }
}

class SemNotificacao implements NotificadorVenda {
    @Override
    public void notificar(String cliente, double total) { /* atacado nao notifica */ }
}

// ── VendaFactory atualizado ───────────────────────────────────────────────────

interface VendaFactory {
    EtiquetaProduto  criarEtiqueta();
    ComprovanteVenda criarComprovante();
    NotificadorVenda criarNotificador();   // terceiro produto da família
}

// ── fábricas adicionando o terceiro produto ───────────────────────────────────

class VendaPresencialFactory implements VendaFactory {
    // ...
    @Override public NotificadorVenda criarNotificador() { return new SemNotificacao(); }
}

class VendaOnlineFactory implements VendaFactory {
    // ...
    @Override public NotificadorVenda criarNotificador() { return new NotificadorEmail(); }
}

class VendaAtacadoFactory implements VendaFactory {
    // ...
    @Override public NotificadorVenda criarNotificador() { return new SemNotificacao(); }
}
```

**Por que isso demonstra Abstract Factory?**
- Cada família possui um conjunto coerente de produtos (etiqueta + comprovante + notificador).
- Misturar, por exemplo, `EtiquetaOnline` com `ComprovanteAtacado` seria inconsistente — a fábrica abstrata **previne** esse tipo de erro.

---

## Exercício 3 — Teste de consistência de família

**Enunciado:** Garantir que uma fábrica sempre retorna objetos da mesma família (sem mistura entre presencial e online, por exemplo).

```java
// Salvar como TesteAbstractFactory.java
// javac MainAbstractFactory.java TesteAbstractFactory.java && java TesteAbstractFactory

public class TesteAbstractFactory {

    static int ok   = 0;
    static int erro = 0;

    public static void main(String[] args) {
        System.out.println("=== Testes: Abstract Factory ===");

        // Teste 1: fabrica presencial gera etiqueta presencial
        testarFamilia(new VendaPresencialFactory(), "PRESENCIAL", "CUPOM FISCAL IMPRESSO");

        // Teste 2: fabrica online gera comprovante digital
        testarFamilia(new VendaOnlineFactory(), "[DIGITAL]", "PDF");

        // Teste 3: fabrica atacado gera comprovante de atacado
        testarFamilia(new VendaAtacadoFactory(), "ATACADO", "NOTA FISCAL ATACADO");

        System.out.println("\nResultado: " + ok + " OK, " + erro + " FALHA(S)");
    }

    static void testarFamilia(VendaFactory factory, String palavraEtiqueta, String palavraComprovante) {
        EtiquetaProduto  etiqueta    = factory.criarEtiqueta();
        ComprovanteVenda comprovante = factory.criarComprovante();

        String textEtiqueta    = etiqueta.gerarEtiqueta("Tomate", 4.50, 10);
        String textComprovante = comprovante.emitir(45.00, "ClienteTeste");

        String nomeFabrica = factory.getClass().getSimpleName();

        if (textEtiqueta.toUpperCase().contains(palavraEtiqueta.toUpperCase()))
            passou(nomeFabrica + " > etiqueta contem '" + palavraEtiqueta + "'");
        else
            falhou(nomeFabrica + " > etiqueta deveria conter '" + palavraEtiqueta + "'. Obtido: " + textEtiqueta);

        if (textComprovante.toUpperCase().contains(palavraComprovante.toUpperCase()))
            passou(nomeFabrica + " > comprovante contem '" + palavraComprovante + "'");
        else
            falhou(nomeFabrica + " > comprovante deveria conter '" + palavraComprovante + "'. Obtido: " + textComprovante);
    }

    static void passou(String msg) { System.out.println("  [OK] " + msg); ok++; }
    static void falhou(String msg) { System.out.println("  [FALHA] " + msg); erro++; }
}
```

**Como executar:**
```
javac MainAbstractFactory.java TesteAbstractFactory.java
java TesteAbstractFactory
```

---

## Resumo dos conceitos

| Exercício | Conceito reforçado |
|---|---|
| 1 | OCP — nova variante entra sem tocar código existente |
| 2 | Família coerente — cada fábrica entrega produtos compatíveis |
| 3 | Testabilidade — famílias podem ser verificadas isoladamente |
