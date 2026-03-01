package feira.gabarito.repository;

import feira.gabarito.domain.Pedido;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PedidoRepositoryMemoria implements PedidoRepository {
    private final List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public List<Pedido> listarTodos() {
        return Collections.unmodifiableList(pedidos);
    }
}
