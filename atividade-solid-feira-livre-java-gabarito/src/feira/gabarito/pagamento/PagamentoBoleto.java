package feira.gabarito.pagamento;

/**
 * Implementação de pagamento via boleto.
 */
public class PagamentoBoleto implements ProcessadorPagamento {
    @Override
    public String codigo() {
        return "BOLETO";
    }

    @Override
    public void pagar(double valor) {
        System.out.println("Boleto emitido: R$ " + valor);
    }
}
