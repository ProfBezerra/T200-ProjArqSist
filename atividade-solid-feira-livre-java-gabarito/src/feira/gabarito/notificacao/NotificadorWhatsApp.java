package feira.gabarito.notificacao;

public class NotificadorWhatsApp implements NotificadorPedido {
    @Override
    public void notificarFinalizacao(String contato, double total) {
        System.out.println("[WhatsApp] " + contato + " -> Pedido finalizado. Total: R$ " + total);
    }
}
