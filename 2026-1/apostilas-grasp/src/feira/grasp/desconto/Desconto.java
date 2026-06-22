package feira.grasp.desconto;

import feira.grasp.Pedido;

/**
 * Interface Desconto — demonstra Polymorphism: políticas de desconto substituem comportamento sem if/else.
 *
 * GRASP: Polymorphism (variações de comportamento são movidas para subclasses/implementações).
 */
public interface Desconto {
    double aplicar(Pedido pedido);
}
