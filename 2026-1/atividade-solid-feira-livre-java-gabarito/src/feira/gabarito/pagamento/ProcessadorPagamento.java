package feira.gabarito.pagamento;

/**
 * Contrato mínimo para processadores de pagamento (ISP + OCP).
 */
public interface ProcessadorPagamento {
    String codigo();

    void pagar(double valor);
}
