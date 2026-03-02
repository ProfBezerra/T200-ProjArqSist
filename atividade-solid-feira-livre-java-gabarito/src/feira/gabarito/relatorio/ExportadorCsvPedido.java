package feira.gabarito.relatorio;

import feira.gabarito.domain.Pedido;
import feira.gabarito.domain.PedidoItem;

/**
 * Implementação de exportação de relatório em CSV.
 */
public class ExportadorCsvPedido implements ExportadorRelatorioPedido {
    @Override
    public String exportar(Pedido pedido, double totalLiquido) {
        StringBuilder csv = new StringBuilder();
        csv.append("cliente,produto,quantidade,subtotal\n");
        for (PedidoItem item : pedido.getItens()) {
            csv.append(pedido.getCliente()).append(",")
                    .append(item.getProduto().getNome()).append(",")
                    .append(item.getQuantidade()).append(",")
                    .append(item.subtotal()).append("\n");
        }
        csv.append("TOTAL,,,").append(totalLiquido);
        return csv.toString();
    }
}
