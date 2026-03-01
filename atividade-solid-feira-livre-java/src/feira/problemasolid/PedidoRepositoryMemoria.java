package feira.problemasolid;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepositoryMemoria implements PedidoRepository {
    private final List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidos;
    }
}
