package feira.gabarito.notificacao;

public interface NotificadorPedido {
    void notificarFinalizacao(String contato, double total);
}
