package feira.gabarito.desconto;

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
