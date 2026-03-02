package feira.gabarito.entrega;

/**
 * Contrato de cálculo de prazo de entrega (LSP).
 */
public interface CalculadoraPrazoEntrega {
    String tipo();

    int calcularPrazoDias(double distanciaKm);
}
