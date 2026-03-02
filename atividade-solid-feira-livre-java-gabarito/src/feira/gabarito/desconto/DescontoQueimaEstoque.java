package feira.gabarito.desconto;

/**
 * Estratégia de desconto usada para queima de estoque.
 */
public class DescontoQueimaEstoque implements PoliticaDesconto {
    @Override
    public String codigo() {
        return "QUEIMA_ESTOQUE";
    }

    @Override
    public double aplicar(double totalBruto) {
        return totalBruto * 0.80;
    }
}
