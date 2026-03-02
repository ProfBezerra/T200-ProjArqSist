package feira.gabarito.repository;

import feira.gabarito.domain.Pedido;
import java.util.List;

/**
 * Abstração de persistência para pedidos (DIP).
 */
public interface PedidoRepository {
    void salvar(Pedido pedido);

    List<Pedido> listarTodos();
}
