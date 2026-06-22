package feira.gabarito.entrega;

/**
 * Implementação de prazo para entrega normal.
 */
public class EntregaNormal implements CalculadoraPrazoEntrega {
    @Override
    public String tipo() {
        return "NORMAL";
    }

    @Override
    public int calcularPrazoDias(double distanciaKm) {
        validarDistancia(distanciaKm);
        return (int) Math.ceil(distanciaKm / 10.0);
    }

    private void validarDistancia(double distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("Distância inválida");
        }
    }
}
