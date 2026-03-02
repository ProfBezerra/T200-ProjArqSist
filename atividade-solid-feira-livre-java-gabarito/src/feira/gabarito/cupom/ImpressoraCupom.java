package feira.gabarito.cupom;

import feira.gabarito.domain.Pedido;

/**
 * Abstração para impressão de cupom.
 */
public interface ImpressoraCupom {
    void imprimir(Pedido pedido, double totalLiquido);
}
