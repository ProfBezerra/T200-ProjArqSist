package feira.gabarito.desconto;

/**
 * Contrato de estratégia de desconto (OCP).
 */
public interface PoliticaDesconto {
    String codigo();

    double aplicar(double totalBruto);
}
