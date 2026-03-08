package feira.grasp;

// Exemplo de Creator: PedidoService cria pedidos e coordena operações
public class PedidoService {

    public Pedido criarPedido() {
        return new Pedido();
    }

    public void processarPedido(Pedido pedido) {
        double total = pedido.calcularTotal();
        System.out.println("Processando pedido. Total = " + total);
    }
}
