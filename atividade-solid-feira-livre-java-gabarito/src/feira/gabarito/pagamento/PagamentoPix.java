package feira.gabarito.pagamento;

/**
 * Implementação de pagamento via PIX.
 */
public class PagamentoPix implements ProcessadorPagamento {
    @Override
    public String codigo() {
        return "PIX";
    }

    @Override
    public void pagar(double valor) {
        System.out.println("PIX pago: R$ " + valor);
    }
}
