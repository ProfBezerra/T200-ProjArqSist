package feira.problemasolid;

/**
 * Classe intencionalmente "ruim" para a atividade de refatoração SOLID.
 *
 * Violações presentes:
 * - SRP: concentra cálculo, pagamento, persistência, impressão, notificação e relatório.
 * - OCP: usa if/else para desconto e forma de pagamento.
 * - DIP: depende de implementações concretas e instancia tudo internamente.
 */
public class ProcessadorPedidoRuim {
    private final PedidoRepositoryMemoria repository = new PedidoRepositoryMemoria();
    private final ImpressoraTermica impressora = new ImpressoraTermica();
    private final NotificadorWhatsApp notificador = new NotificadorWhatsApp();
    private final PagamentoGateway pagamento = new PagamentoPix();

    public Pedido criarPedido(String cliente) {
        return new Pedido(cliente);
    }

    public void adicionarItem(Pedido pedido, Produto produto, int quantidade) {
        pedido.adicionarItem(produto, quantidade);
    }

    public double finalizarPedido(Pedido pedido, String tipoDesconto, String formaPagamento, String telefone) {
        double total = calcularTotalComDesconto(pedido, tipoDesconto);

        if ("PIX".equalsIgnoreCase(formaPagamento)) {
            pagamento.pagarPix(total);
        } else if ("CARTAO".equalsIgnoreCase(formaPagamento)) {
            pagamento.pagarCartao(total);
        } else if ("BOLETO".equalsIgnoreCase(formaPagamento)) {
            System.out.println("Boleto gerado para R$ " + total);
        } else {
            throw new IllegalArgumentException("Forma de pagamento inválida");
        }

        repository.salvar(pedido);
        impressora.imprimirCupom(pedido, total);
        notificador.enviarMensagem(telefone, "Seu pedido foi finalizado. Total: R$ " + total);

        String csv = gerarCsvFechamento(pedido, total);
        System.out.println(csv);

        return total;
    }

    public int prazoEntrega(Entrega entrega, double distanciaKm) {
        return entrega.calcularPrazoDias(distanciaKm);
    }

    public void rotinaAdministrativa() {
        // Exemplo usado para mostrar ISP quebrado: nem todo meio de pagamento
        // deveria ser obrigado a suportar relatório administrativo.
        pagamento.gerarRelatorioFechamento();
    }

    private double calcularTotalComDesconto(Pedido pedido, String tipoDesconto) {
        double total = 0.0;
        for (PedidoItem item : pedido.getItens()) {
            total += item.subtotal();
        }

        if ("NENHUM".equalsIgnoreCase(tipoDesconto)) {
            return total;
        } else if ("CLIENTE_FIEL".equalsIgnoreCase(tipoDesconto)) {
            return total * 0.90;
        } else if ("QUEIMA_ESTOQUE".equalsIgnoreCase(tipoDesconto)) {
            return total * 0.80;
        } else if ("DOMINGO".equalsIgnoreCase(tipoDesconto)) {
            return total * 0.95;
        }

        return total;
    }

    private String gerarCsvFechamento(Pedido pedido, double total) {
        StringBuilder csv = new StringBuilder();
        csv.append("cliente,produto,quantidade,subtotal\\n");
        for (PedidoItem item : pedido.getItens()) {
            csv.append(pedido.getCliente()).append(",")
                    .append(item.getProduto().getNome()).append(",")
                    .append(item.getQuantidade()).append(",")
                    .append(item.subtotal()).append("\\n");
        }
        csv.append("TOTAL,,,").append(total);
        return csv.toString();
    }
}
