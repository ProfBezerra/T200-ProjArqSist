package feira.problemasolid;

/**
 * Componente de notificação via WhatsApp (simulado no console).
 */
public class NotificadorWhatsApp {
    public void enviarMensagem(String telefone, String mensagem) {
        System.out.println("[WhatsApp] " + telefone + ": " + mensagem);
    }
}
