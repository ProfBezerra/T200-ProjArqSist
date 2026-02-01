/**
 * PedidoRepository
 * - Interface (contrato): define a operação de persistência.
 * - DIP (Dependency Inversion Principle): consumidores dependem desta
 *   abstração, não de implementações concretas.
 */
package feira;

public interface PedidoRepository {
    void salvar(Pedido pedido);
}
