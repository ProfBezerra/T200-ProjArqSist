package feira.grasp.repository;

import feira.grasp.Pedido;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementação em memória de PedidoRepository para fins didáticos.
 */
public class PedidoRepositoryMemoria implements PedidoRepository {
    private final Map<String, Pedido> banco = new HashMap<>();

    @Override
    public void salvar(Pedido pedido) {
        banco.put(pedido.getId(), pedido);
    }
}
