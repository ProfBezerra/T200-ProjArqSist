package feira.gabarito.pagamento;

public class PagamentoCartao implements ProcessadorPagamento {
    @Override
    public String codigo() {
        return "CARTAO";
    }

    @Override
    public void pagar(double valor) {
        System.out.println("Cartão pago: R$ " + valor);
    }
}
