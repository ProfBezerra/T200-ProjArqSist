package feira.grasp.payment;

/**
 * DTO simples com informações de pagamento.
 *
 * Uso: transporta dados entre controller/service/gateway sem conter lógica de negócio.
 */
public class PagamentoInfo {
    private final String metodo;
    private final String referencia;

    public PagamentoInfo(String metodo, String referencia) {
        this.metodo = metodo;
        this.referencia = referencia;
    }

    public String getMetodo() { return metodo; }
    public String getReferencia() { return referencia; }
}
