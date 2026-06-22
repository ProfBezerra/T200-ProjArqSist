package feira.grasp.repository;

import feira.grasp.Pedido;

/**
 * Abstração de persistência de Pedido.
 *
 * GRASP: Pure Fabrication / Low Coupling.
 */
public interface PedidoRepository {
    void salvar(Pedido pedido);
}
