package feira.grasp.payment;

import feira.grasp.Pedido;

/**
 * Abstração de gateway de pagamento (Indirection / Protected Variations).
 *
 * GRASP:
 * - Indirection: isola o `PedidoService` das variações de provedores de pagamento.
 * - Protected Variations: define uma interface estável para proteger o sistema de mudanças.
 */
public interface PagamentoGateway {
    boolean pagar(Pedido pedido, PagamentoInfo info);
}
