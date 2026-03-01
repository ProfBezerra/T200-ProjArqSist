package feira.gabarito.pagamento;

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
