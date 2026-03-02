package feira.gabarito.pagamento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serviço que resolve o processador de pagamento por código.
 */
public class ServicoPagamento {
    private final Map<String, ProcessadorPagamento> processadoresPorCodigo;

    public ServicoPagamento(List<ProcessadorPagamento> processadores) {
        this.processadoresPorCodigo = new HashMap<>();
        for (ProcessadorPagamento processador : processadores) {
            this.processadoresPorCodigo.put(processador.codigo().toUpperCase(), processador);
        }
    }

    public void pagar(String codigoPagamento, double valor) {
        if (codigoPagamento == null) {
            throw new IllegalArgumentException("Forma de pagamento é obrigatória");
        }
        ProcessadorPagamento processador = processadoresPorCodigo.get(codigoPagamento.toUpperCase());
        if (processador == null) {
            throw new IllegalArgumentException("Pagamento não suportado: " + codigoPagamento);
        }
        processador.pagar(valor);
    }
}
