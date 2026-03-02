package feira.gabarito.desconto;

/**
 * Estratégia de desconto para cliente fiel.
 */
public class DescontoClienteFiel implements PoliticaDesconto {
    @Override
    public String codigo() {
        return "CLIENTE_FIEL";
    }

    @Override
    public double aplicar(double totalBruto) {
        return totalBruto * 0.90;
    }
}
