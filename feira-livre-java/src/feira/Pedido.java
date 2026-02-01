/**
 * Pedido
 * - Composição/Agragação: mantém uma coleção de itens de pedido.
 * - Abstração: oferece operações de alto nível (adicionarItem, total)
 *   sem expor detalhes de cálculo.
 * - Coesão: centraliza regras de pedido (ex.: total).
 */
package feira;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private final List<PedidoItem> itens = new ArrayList<>();

    public void adicionarItem(Produto produto, int quantidade) {
        // Abstração: quem usa o pedido não precisa montar o item manualmente
        PedidoItem item = new PedidoItem(produto, quantidade);
        itens.add(item);
    }

    public List<PedidoItem> getItens() {
        // Encapsulamento: expõe visão imutável para proteger invariantes internos
        return Collections.unmodifiableList(itens);
    }

    public double total() {
        // Abstração/coesa: total do pedido é soma dos subtotais
        return itens.stream().mapToDouble(PedidoItem::subtotal).sum();
    }

    public boolean vazio() {
        return itens.isEmpty();
    }
}
