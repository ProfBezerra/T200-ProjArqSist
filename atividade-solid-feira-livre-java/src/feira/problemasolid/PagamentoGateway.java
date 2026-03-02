package feira.problemasolid;

/**
 * Interface propositalmente ampla para a atividade.
 *
 * Viola ISP porque força implementações a dependerem de métodos que
 * não fazem parte do seu caso de uso.
 */
public interface PagamentoGateway {
    void pagarPix(double valor);

    void pagarCartao(double valor);

    void emitirNotaFiscal(String cpf, double valor);

    void gerarRelatorioFechamento();

    void enviarEmailConfirmacao(String email);
}
