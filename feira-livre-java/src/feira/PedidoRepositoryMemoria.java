/**
 * PedidoRepositoryMemoria
 * - Implementação concreta do contrato de persistência em memória.
 * - Baixo acoplamento: pode ser substituída por outra implementação
 *   sem afetar consumidores que dependem da interface.
 */
package feira;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepositoryMemoria implements PedidoRepository {
    private final List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> todos() {
        // Compatível com JDK 8: retorna cópia imutável
        return java.util.Collections.unmodifiableList(new ArrayList<>(pedidos));
    }
}
