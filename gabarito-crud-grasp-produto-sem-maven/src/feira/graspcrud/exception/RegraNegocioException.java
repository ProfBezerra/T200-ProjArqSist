package feira.graspcrud.exception;

/**
 * Excecao para representar violacoes de regra de negocio.
 */
public class RegraNegocioException extends RuntimeException {

    /**
     * Cria a excecao com mensagem amigavel ao usuario.
     *
     * @param message mensagem da violacao
     */
    public RegraNegocioException(String message) {
        super(message);
    }
}
