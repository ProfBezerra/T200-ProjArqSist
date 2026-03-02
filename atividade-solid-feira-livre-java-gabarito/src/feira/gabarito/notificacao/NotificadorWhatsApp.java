package feira.gabarito.notificacao;

/**
 * Implementação de notificação por WhatsApp (simulada).
 */
public class NotificadorWhatsApp implements NotificadorPedido {
    @Override
    public void notificarFinalizacao(String contato, double total) {
        System.out.println("[WhatsApp] " + contato + " -> Pedido finalizado. Total: R$ " + total);
    }
}
