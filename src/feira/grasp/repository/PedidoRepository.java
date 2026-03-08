package feira.grasp.repository;

import java.util.*;
import feira.grasp.Pedido;

public interface PedidoRepository {
    void salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(String id);
    List<Pedido> listarTodos();
}
