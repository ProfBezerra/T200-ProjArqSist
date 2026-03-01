package feira.gabarito.entrega;

public interface CalculadoraPrazoEntrega {
    String tipo();

    int calcularPrazoDias(double distanciaKm);
}
