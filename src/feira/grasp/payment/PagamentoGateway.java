package feira.grasp.payment;

import feira.grasp.Pedido;

public interface PagamentoGateway {
    boolean pagar(Pedido pedido, PagamentoInfo info);
}
