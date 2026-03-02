package feira.problemasolid;

import java.util.List;

/**
 * Contrato de persistência de pedidos.
 */
public interface PedidoRepository {
    void salvar(Pedido pedido);

    List<Pedido> listarTodos();
}
