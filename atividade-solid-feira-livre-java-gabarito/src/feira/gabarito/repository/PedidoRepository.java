package feira.gabarito.repository;

import feira.gabarito.domain.Pedido;
import java.util.List;

public interface PedidoRepository {
    void salvar(Pedido pedido);

    List<Pedido> listarTodos();
}
