package feira.problemasolid;

/**
 * Classe base para cálculo de prazo de entrega.
 */
public class Entrega {
    public int calcularPrazoDias(double distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("Distância inválida");
        }
        return (int) Math.ceil(distanciaKm / 10);
    }
}
