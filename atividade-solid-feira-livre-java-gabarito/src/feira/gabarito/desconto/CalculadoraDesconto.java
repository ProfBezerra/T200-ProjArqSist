package feira.gabarito.desconto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serviço que seleciona e aplica a política de desconto por código.
 */
public class CalculadoraDesconto {
    private final Map<String, PoliticaDesconto> politicasPorCodigo;

    public CalculadoraDesconto(List<PoliticaDesconto> politicas) {
        this.politicasPorCodigo = new HashMap<>();
        for (PoliticaDesconto politica : politicas) {
            this.politicasPorCodigo.put(politica.codigo().toUpperCase(), politica);
        }
    }

    public double aplicar(String codigoDesconto, double totalBruto) {
        if (codigoDesconto == null) {
            throw new IllegalArgumentException("Código de desconto é obrigatório");
        }
        PoliticaDesconto politica = politicasPorCodigo.get(codigoDesconto.toUpperCase());
        if (politica == null) {
            throw new IllegalArgumentException("Desconto não suportado: " + codigoDesconto);
        }
        return politica.aplicar(totalBruto);
    }
}
