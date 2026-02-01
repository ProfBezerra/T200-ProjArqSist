/**
 * PedidoService
 * - Camada de serviço: orquestra regras de finalização sem conhecer detalhes
 *   da persistência.
 * - DIP: depende da interface PedidoRepository (abstração), permitindo trocar
 *   implementações sem impacto.
 * - Coesão: responsabilidade centrada na finalização e persistência do pedido.
 */
package feira;

public class PedidoService {
    private final PedidoRepository repository; // DIP: depende de abstração

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public double finalizar(Pedido pedido) {
        // Valida estado do pedido (encapsulamento/coesa)
        if (pedido == null || pedido.vazio()) {
            throw new IllegalArgumentException("Pedido vazio ou nulo");
        }
        double total = pedido.total();
        repository.salvar(pedido);
        return total;
    }
}
