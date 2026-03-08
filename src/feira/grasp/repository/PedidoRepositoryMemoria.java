package feira.grasp.repository;

import java.util.*;
import feira.grasp.Pedido;

public class PedidoRepositoryMemoria implements PedidoRepository {
    private final Map<String, Pedido> storage = new HashMap<>();

    @Override
    public void salvar(Pedido pedido) {
        storage.put(pedido.getId(), pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(storage.values());
    }
}
