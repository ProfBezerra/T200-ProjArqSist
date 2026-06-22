package feira.gabarito.notificacao;

/**
 * Contrato para envio de notificações de finalização do pedido.
 */
public interface NotificadorPedido {
    void notificarFinalizacao(String contato, double total);
}
