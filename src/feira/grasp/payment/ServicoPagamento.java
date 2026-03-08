package feira.grasp.payment;

import feira.grasp.payment.PagamentoInfo;

public interface ServicoPagamento {
    boolean processar(PagamentoInfo info);
}
