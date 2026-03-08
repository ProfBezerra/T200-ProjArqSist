package feira.grasp.payment;

/**
 * Implementação de serviço de pagamento (exemplo de provedor concreto).
 * Usado para demonstrar Protected Variations: `PedidoService` depende da abstração, não da implementação concreta.
 */
public class ServicoPagamentoPix implements ServicoPagamento {
    @Override
    public boolean processar(PagamentoInfo info) {
        System.out.println("[ServicoPagamentoPix] processando: " + info.getReferencia());
        return true;
    }
}
