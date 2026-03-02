package feira.problemasolid;

/**
 * Implementação de pagamento PIX para a atividade.
 *
 * Lança UnsupportedOperationException em vários métodos por causa de
 * uma interface grande demais (violação de ISP).
 */
public class PagamentoPix implements PagamentoGateway {
    @Override
    public void pagarPix(double valor) {
        System.out.println("Pagamento PIX efetuado: R$ " + valor);
    }

    @Override
    public void pagarCartao(double valor) {
        throw new UnsupportedOperationException("PIX não suporta cartão");
    }

    @Override
    public void emitirNotaFiscal(String cpf, double valor) {
        throw new UnsupportedOperationException("PIX não emite NF");
    }

    @Override
    public void gerarRelatorioFechamento() {
        throw new UnsupportedOperationException("PIX não gera fechamento");
    }

    @Override
    public void enviarEmailConfirmacao(String email) {
        throw new UnsupportedOperationException("PIX não envia e-mail");
    }
}
