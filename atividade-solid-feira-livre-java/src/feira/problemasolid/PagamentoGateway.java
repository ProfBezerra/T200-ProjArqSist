package feira.problemasolid;

public interface PagamentoGateway {
    void pagarPix(double valor);

    void pagarCartao(double valor);

    void emitirNotaFiscal(String cpf, double valor);

    void gerarRelatorioFechamento();

    void enviarEmailConfirmacao(String email);
}
