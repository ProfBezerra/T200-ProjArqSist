package feira.problemasolid;

import java.util.ArrayList;
import java.util.List;

/**
 * Agregado de pedido com cliente e lista de itens.
 */
public class Pedido {
    private final String cliente;
    private final List<PedidoItem> itens = new ArrayList<>();

    public Pedido(String cliente) {
        this.cliente = cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        itens.add(new PedidoItem(produto, quantidade));
    }
}
