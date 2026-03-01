package feira.gabarito.relatorio;

import feira.gabarito.domain.Pedido;

public interface ExportadorRelatorioPedido {
    String exportar(Pedido pedido, double totalLiquido);
}
