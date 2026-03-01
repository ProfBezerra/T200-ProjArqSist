package feira.problemasolid;

import java.util.List;

public interface PedidoRepository {
    void salvar(Pedido pedido);

    List<Pedido> listarTodos();
}
