package feira.grasp.payment;

import feira.grasp.payment.PagamentoInfo;

/**
 * Serviço de pagamento (exemplo de Protected Variations / DIP):
 * define uma abstração para diferentes provedores de serviço de pagamento.
 */
public interface ServicoPagamento {
    boolean processar(PagamentoInfo info);
}
