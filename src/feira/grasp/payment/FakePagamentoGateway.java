package feira.grasp.payment;

import feira.grasp.Pedido;

public class FakePagamentoGateway implements PagamentoGateway {
    @Override
    public boolean pagar(Pedido pedido, PagamentoInfo info) {
        System.out.println("[FakePagamento] processando pagamento: " + info.getMetodo());
        return true;
    }
}
