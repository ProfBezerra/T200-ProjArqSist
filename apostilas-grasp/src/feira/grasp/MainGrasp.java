package feira.grasp;

public class MainGrasp {
    public static void main(String[] args) {
        PedidoService service = new PedidoService();
        Pedido pedido = service.criarPedido();

        Produto banana = new Produto("Banana", 2.5);
        Produto maca = new Produto("Maçã", 3.0);

        pedido.addItem(new PedidoItem(banana, 4));
        pedido.addItem(new PedidoItem(maca, 2));

        service.processarPedido(pedido);
    }
}
