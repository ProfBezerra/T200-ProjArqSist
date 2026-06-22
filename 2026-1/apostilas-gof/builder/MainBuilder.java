// =============================================================================
// Builder — Feira Livre
// Compile: javac MainBuilder.java
// Run:     java MainBuilder
// =============================================================================

import java.util.ArrayList;
import java.util.List;

// ── dominio: item do pedido ───────────────────────────────────────────────────

class ItemPedido {
    private final String nome;
    private final double preco;

    ItemPedido(String nome, double preco) {
        this.nome  = nome;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome + " (R$ " + String.format("%.2f", preco) + ")";
    }
}

// ── produto final construido pelo builder ─────────────────────────────────────

class Pedido {
    private final String          cliente;
    private final List<ItemPedido> itens;
    private final String          observacao;
    private final String          tipoEntrega;
    private final String          cupom;

    private Pedido(Builder b) {
        this.cliente     = b.cliente;
        this.itens       = List.copyOf(b.itens);
        this.observacao  = b.observacao;
        this.tipoEntrega = b.tipoEntrega;
        this.cupom       = b.cupom;
    }

    @Override
    public String toString() {
        return "Pedido{"
             + "cliente='"   + cliente     + "'"
             + ", itens="    + itens
             + ", entrega='" + tipoEntrega + "'"
             + ", cupom='"   + cupom       + "'"
             + ", obs='"     + observacao  + "'"
             + "}";
    }

    // ── builder interno ───────────────────────────────────────────────────────

    static class Builder {
        private String            cliente;
        private final List<ItemPedido> itens = new ArrayList<>();
        private String            observacao  = "";
        private String            tipoEntrega = "RETIRADA";
        private String            cupom       = "";

        Builder cliente(String cliente)          { this.cliente = cliente;   return this; }
        Builder adicionarItem(ItemPedido item)   { this.itens.add(item);     return this; }
        Builder observacao(String obs)           { this.observacao = obs;    return this; }
        Builder tipoEntrega(String tipo)         { this.tipoEntrega = tipo;  return this; }
        Builder cupom(String cupom)              { this.cupom = cupom;       return this; }

        Pedido build() {
            if (cliente == null || cliente.isBlank())
                throw new IllegalStateException("Cliente obrigatorio");
            if (itens.isEmpty())
                throw new IllegalStateException("Pedido precisa de pelo menos um item");
            if (!cupom.isBlank() && !cupom.startsWith("FEIRA"))
                throw new IllegalStateException("Cupom invalido: deve comecar com FEIRA");
            return new Pedido(this);
        }
    }
}

// ── diretor: monta pedidos padrao ─────────────────────────────────────────────

class PedidoDiretor {
    /** Monta uma cesta semanal padrao para o cliente informado */
    static Pedido cestaSemanal(String cliente) {
        return new Pedido.Builder()
            .cliente(cliente)
            .adicionarItem(new ItemPedido("Tomate",  4.50))
            .adicionarItem(new ItemPedido("Alface",  2.00))
            .adicionarItem(new ItemPedido("Batata",  3.00))
            .adicionarItem(new ItemPedido("Cebola",  2.80))
            .adicionarItem(new ItemPedido("Cenoura", 3.20))
            .tipoEntrega("ENTREGA")
            .observacao("Cesta semanal padrao")
            .build();
    }
}

// ── programa principal ────────────────────────────────────────────────────────

public class MainBuilder {
    public static void main(String[] args) {
        System.out.println("=== Pedido completo ===");
        Pedido p1 = new Pedido.Builder()
            .cliente("Maria")
            .adicionarItem(new ItemPedido("Tomate",  4.50))
            .adicionarItem(new ItemPedido("Batata",  3.00))
            .adicionarItem(new ItemPedido("Cebola",  2.80))
            .tipoEntrega("ENTREGA")
            .cupom("FEIRA10")
            .observacao("Sem sacola plastica")
            .build();
        System.out.println(p1);

        System.out.println("\n=== Pedido minimo (retirada) ===");
        Pedido p2 = new Pedido.Builder()
            .cliente("Joao")
            .adicionarItem(new ItemPedido("Alface", 2.00))
            .build();
        System.out.println(p2);

        System.out.println("\n=== Cesta semanal via Diretor ===");
        Pedido cesta = PedidoDiretor.cestaSemanal("Ana");
        System.out.println(cesta);

        System.out.println("\n=== Erros esperados ===");
        try {
            new Pedido.Builder()
                .adicionarItem(new ItemPedido("Queijo", 15.00))
                .build();
        } catch (IllegalStateException e) {
            System.out.println("Sem cliente: " + e.getMessage());
        }

        try {
            new Pedido.Builder()
                .cliente("Pedro")
                .build();
        } catch (IllegalStateException e) {
            System.out.println("Sem itens: " + e.getMessage());
        }

        try {
            new Pedido.Builder()
                .cliente("Carlos")
                .adicionarItem(new ItemPedido("Mel", 20.00))
                .cupom("DESC10")
                .build();
        } catch (IllegalStateException e) {
            System.out.println("Cupom invalido: " + e.getMessage());
        }
    }
}
