package feira.gabarito.cupom;

import feira.gabarito.domain.Pedido;

public interface ImpressoraCupom {
    void imprimir(Pedido pedido, double totalLiquido);
}
