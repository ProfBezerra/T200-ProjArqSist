package feira.grasp.desconto;

import feira.grasp.Pedido;

/**
 * Implementação parametrizada de desconto (Polymorphism).
 */
public class DescontoPromocional implements Desconto {
    private final double taxa;

    public DescontoPromocional(double taxa) {
        this.taxa = taxa;
    }

    @Override
    public double aplicar(Pedido pedido) {
        return pedido.calcularTotal() * taxa;
    }
}
