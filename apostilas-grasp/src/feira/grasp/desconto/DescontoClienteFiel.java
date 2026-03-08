package feira.grasp.desconto;

import feira.grasp.Pedido;

/**
 * Implementação concreta de desconto (Polymorphism).
 *
 * GRASP: Polymorphism — permite extensão de regras de desconto sem alterar clientes.
 */
public class DescontoClienteFiel implements Desconto {
    @Override
    public double aplicar(Pedido pedido) {
        return pedido.calcularTotal() * 0.10; // 10% exemplo
    }
}
