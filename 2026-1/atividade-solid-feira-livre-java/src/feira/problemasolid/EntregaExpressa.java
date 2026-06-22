package feira.problemasolid;

/**
 * Subtipo usado para demonstrar quebra de LSP na atividade.
 *
 * Em alguns cenários válidos para o tipo base, esta classe lança exceção,
 * impedindo substituição transparente.
 */
public class EntregaExpressa extends Entrega {
    @Override
    public int calcularPrazoDias(double distanciaKm) {
        if (distanciaKm > 20) {
            throw new UnsupportedOperationException("Entrega expressa não atende acima de 20km");
        }
        return 1;
    }
}
