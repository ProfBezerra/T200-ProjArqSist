package feira.problemasolid;

public class EntregaExpressa extends Entrega {
    @Override
    public int calcularPrazoDias(double distanciaKm) {
        if (distanciaKm > 20) {
            throw new UnsupportedOperationException("Entrega expressa não atende acima de 20km");
        }
        return 1;
    }
}
