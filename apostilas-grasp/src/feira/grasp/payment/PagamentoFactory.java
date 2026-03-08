package feira.grasp.payment;

/**
 * PagamentoFactory — demonstra Pure Fabrication / Protected Variations:
 * escolhe um `PagamentoGateway` de acordo com a `FormaPagamento`.
 */
public class PagamentoFactory {
    public static PagamentoGateway criar(FormaPagamento forma) {
        switch (forma) {
            case PIX:
                return new FakePagamentoGateway();
            case CARTAO:
                return new FakePagamentoGateway();
            case BOLETO:
                return new FakePagamentoGateway();
            default:
                return new FakePagamentoGateway();
        }
    }
}
