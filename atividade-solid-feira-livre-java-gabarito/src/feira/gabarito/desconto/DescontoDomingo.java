package feira.gabarito.desconto;

public class DescontoDomingo implements PoliticaDesconto {
    @Override
    public String codigo() {
        return "DOMINGO";
    }

    @Override
    public double aplicar(double totalBruto) {
        return totalBruto * 0.95;
    }
}
