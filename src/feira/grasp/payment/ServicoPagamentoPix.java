package feira.grasp.payment;

public class ServicoPagamentoPix implements ServicoPagamento {
    @Override
    public boolean processar(PagamentoInfo info) {
        System.out.println("[ServicoPagamentoPix] processando: " + info.getReferencia());
        return true;
    }
}
