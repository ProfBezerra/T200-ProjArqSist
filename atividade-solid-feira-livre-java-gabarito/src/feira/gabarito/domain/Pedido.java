package feira.gabarito.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private final String cliente;
    private final List<PedidoItem> itens;

    public Pedido(String cliente) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Cliente é obrigatório");
        }
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    public String getCliente() {
        return cliente;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        itens.add(new PedidoItem(produto, quantidade));
    }

    public List<PedidoItem> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public boolean vazio() {
        return itens.isEmpty();
    }

    public double totalBruto() {
        double total = 0.0;
        for (PedidoItem item : itens) {
            total += item.subtotal();
        }
        return total;
    }
}
