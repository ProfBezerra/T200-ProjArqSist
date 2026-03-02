package feira.gabarito.relatorio;

import feira.gabarito.domain.Pedido;

/**
 * Contrato para exportação de relatório de pedido.
 */
public interface ExportadorRelatorioPedido {
    String exportar(Pedido pedido, double totalLiquido);
}
