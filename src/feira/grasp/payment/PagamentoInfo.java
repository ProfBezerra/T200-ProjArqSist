package feira.grasp.payment;

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
