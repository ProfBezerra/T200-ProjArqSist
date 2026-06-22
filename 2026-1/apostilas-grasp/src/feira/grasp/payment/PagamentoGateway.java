package feira.grasp.payment;

import feira.grasp.Pedido;

/**
 * Contrato de gateway de pagamento (Indirection / Protected Variations).
 */
public interface PagamentoGateway {
    boolean pagar(Pedido pedido, PagamentoInfo info);
}
