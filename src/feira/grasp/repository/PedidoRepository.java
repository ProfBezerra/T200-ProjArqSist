package feira.grasp.repository;

import java.util.*;
import feira.grasp.Pedido;

/**
 * PedidoRepository — Pure Fabrication: abstrai a persistência fora do domínio.
 *
 * Padrões GRASP:
 * - Pure Fabrication: separar persistência em uma classe não pertencente ao modelo de domínio.
 * - Low Coupling: as classes de domínio dependem desta abstração, não da implementação concreta.
 */
public interface PedidoRepository {
    void salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(String id);
    List<Pedido> listarTodos();
}
