package feira.gabarito.service;

public class PedidoFinalizado {
    private final double totalBruto;
    private final double totalLiquido;
    private final String relatorioCsv;

    public PedidoFinalizado(double totalBruto, double totalLiquido, String relatorioCsv) {
        this.totalBruto = totalBruto;
        this.totalLiquido = totalLiquido;
        this.relatorioCsv = relatorioCsv;
    }

    public double getTotalBruto() {
        return totalBruto;
    }

    public double getTotalLiquido() {
        return totalLiquido;
    }

    public String getRelatorioCsv() {
        return relatorioCsv;
    }
}
