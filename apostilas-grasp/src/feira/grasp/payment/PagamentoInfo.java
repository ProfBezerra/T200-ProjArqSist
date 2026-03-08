package feira.grasp.payment;

/**
 * Dados de pagamento (didático) para enviar ao gateway.
 */
public class PagamentoInfo {
    private final String tipo;
    private final String referencia;

    public PagamentoInfo(String tipo, String referencia) {
        this.tipo = tipo;
        this.referencia = referencia;
    }

    public String getTipo() {
        return tipo;
    }

    public String getReferencia() {
        return referencia;
    }
}
