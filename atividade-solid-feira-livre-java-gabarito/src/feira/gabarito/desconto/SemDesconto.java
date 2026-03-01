package feira.gabarito.desconto;

public class SemDesconto implements PoliticaDesconto {
    @Override
    public String codigo() {
        return "NENHUM";
    }

    @Override
    public double aplicar(double totalBruto) {
        return totalBruto;
    }
}
