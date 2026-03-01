package feira.gabarito.entrega;

public class EntregaExpressa implements CalculadoraPrazoEntrega {
    @Override
    public String tipo() {
        return "EXPRESSA";
    }

    @Override
    public int calcularPrazoDias(double distanciaKm) {
        validarDistancia(distanciaKm);
        int prazoBase = (int) Math.ceil(distanciaKm / 20.0);
        return Math.max(1, prazoBase);
    }

    private void validarDistancia(double distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("Distância inválida");
        }
    }
}
