package feira.grasp.payment;

import feira.grasp.Pedido;

/**
 * Gateway fake para demonstração em aula.
 */
public class FakePagamentoGateway implements PagamentoGateway {
    @Override
    public boolean pagar(Pedido pedido, PagamentoInfo info) {
        return pedido != null
                && info != null
                && info.getTipo() != null
                && !info.getTipo().isBlank()
                && info.getReferencia() != null
                && !info.getReferencia().isBlank();
    }
}
