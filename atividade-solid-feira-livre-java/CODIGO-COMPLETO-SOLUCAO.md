# Código completo da solução (classes a criar)

Este documento reúne **todos os códigos** sugeridos para a solução da atividade.
Os pacotes abaixo seguem a proposta do roteiro (`feira.solucao`).

## 1) Domínio

### `src/feira/solucao/domain/Produto.java`

```java
package feira.solucao.domain;

public class Produto {
    private final String nome;
    private final double preco;

    public Produto(String nome, double preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
```

### `src/feira/solucao/domain/PedidoItem.java`

```java
package feira.solucao.domain;

public class PedidoItem {
    private final Produto produto;
    private final int quantidade;

    public PedidoItem(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto é obrigatório");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double subtotal() {
        return produto.getPreco() * quantidade;
    }
}
```

### `src/feira/solucao/domain/Pedido.java`

```java
package feira.solucao.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private final String cliente;
    private final List<PedidoItem> itens;

    public Pedido(String cliente) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Cliente é obrigatório");
        }
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    public String getCliente() {
        return cliente;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        itens.add(new PedidoItem(produto, quantidade));
    }

    public List<PedidoItem> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public boolean vazio() {
        return itens.isEmpty();
    }

    public double totalBruto() {
        double total = 0.0;
        for (PedidoItem item : itens) {
            total += item.subtotal();
        }
        return total;
    }
}
```

## 2) Repositório

### `src/feira/solucao/repository/PedidoRepository.java`

```java
package feira.solucao.repository;

import feira.solucao.domain.Pedido;
import java.util.List;

public interface PedidoRepository {
    void salvar(Pedido pedido);

    List<Pedido> listarTodos();
}
```

### `src/feira/solucao/repository/PedidoRepositoryMemoria.java`

```java
package feira.solucao.repository;

import feira.solucao.domain.Pedido;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PedidoRepositoryMemoria implements PedidoRepository {
    private final List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public List<Pedido> listarTodos() {
        return Collections.unmodifiableList(pedidos);
    }
}
```

## 3) Desconto (OCP)

### `src/feira/solucao/desconto/PoliticaDesconto.java`

```java
package feira.solucao.desconto;

public interface PoliticaDesconto {
    String codigo();

    double aplicar(double totalBruto);
}
```

### `src/feira/solucao/desconto/SemDesconto.java`

```java
package feira.solucao.desconto;

public class SemDesconto implements PoliticaDesconto {
    @Override
    public String codigo() {
        return "NENHUM";
    }

    @Override
    public double aplicar(double totalBruto) {
        return totalBruto;
    }
}
```

### `src/feira/solucao/desconto/DescontoClienteFiel.java`

```java
package feira.solucao.desconto;

public class DescontoClienteFiel implements PoliticaDesconto {
    @Override
    public String codigo() {
        return "CLIENTE_FIEL";
    }

    @Override
    public double aplicar(double totalBruto) {
        return totalBruto * 0.90;
    }
}
```

### `src/feira/solucao/desconto/DescontoQueimaEstoque.java`

```java
package feira.solucao.desconto;

public class DescontoQueimaEstoque implements PoliticaDesconto {
    @Override
    public String codigo() {
        return "QUEIMA_ESTOQUE";
    }

    @Override
    public double aplicar(double totalBruto) {
        return totalBruto * 0.80;
    }
}
```

### `src/feira/solucao/desconto/DescontoDomingo.java`

```java
package feira.solucao.desconto;

public class DescontoDomingo implements PoliticaDesconto {
    @Override
    public String codigo() {
        return "DOMINGO";
    }

    @Override
    public double aplicar(double totalBruto) {
        return totalBruto * 0.95;
    }
}
```

### `src/feira/solucao/desconto/CalculadoraDesconto.java`

```java
package feira.solucao.desconto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
```

## 4) Pagamento (OCP + ISP)

### `src/feira/solucao/pagamento/ProcessadorPagamento.java`

```java
package feira.solucao.pagamento;

public interface ProcessadorPagamento {
    String codigo();

    void pagar(double valor);
}
```

### `src/feira/solucao/pagamento/PagamentoPix.java`

```java
package feira.solucao.pagamento;

public class PagamentoPix implements ProcessadorPagamento {
    @Override
    public String codigo() {
        return "PIX";
    }

    @Override
    public void pagar(double valor) {
        System.out.println("PIX pago: R$ " + valor);
    }
}
```

### `src/feira/solucao/pagamento/PagamentoCartao.java`

```java
package feira.solucao.pagamento;

public class PagamentoCartao implements ProcessadorPagamento {
    @Override
    public String codigo() {
        return "CARTAO";
    }

    @Override
    public void pagar(double valor) {
        System.out.println("Cartão pago: R$ " + valor);
    }
}
```

### `src/feira/solucao/pagamento/PagamentoBoleto.java`

```java
package feira.solucao.pagamento;

public class PagamentoBoleto implements ProcessadorPagamento {
    @Override
    public String codigo() {
        return "BOLETO";
    }

    @Override
    public void pagar(double valor) {
        System.out.println("Boleto emitido: R$ " + valor);
    }
}
```

### `src/feira/solucao/pagamento/ServicoPagamento.java`

```java
package feira.solucao.pagamento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
```

## 5) Cupom, notificação e relatório (SRP)

### `src/feira/solucao/cupom/ImpressoraCupom.java`

```java
package feira.solucao.cupom;

import feira.solucao.domain.Pedido;

public interface ImpressoraCupom {
    void imprimir(Pedido pedido, double totalLiquido);
}
```

### `src/feira/solucao/cupom/ImpressoraTermica.java`

```java
package feira.solucao.cupom;

import feira.solucao.domain.Pedido;
import feira.solucao.domain.PedidoItem;

public class ImpressoraTermica implements ImpressoraCupom {
    @Override
    public void imprimir(Pedido pedido, double totalLiquido) {
        System.out.println("=== CUPOM ===");
        System.out.println("Cliente: " + pedido.getCliente());
        for (PedidoItem item : pedido.getItens()) {
            System.out.println(item.getProduto().getNome() + " x" + item.getQuantidade() + " = R$ " + item.subtotal());
        }
        System.out.println("Total líquido: R$ " + totalLiquido);
        System.out.println("=============");
    }
}
```

### `src/feira/solucao/notificacao/NotificadorPedido.java`

```java
package feira.solucao.notificacao;

public interface NotificadorPedido {
    void notificarFinalizacao(String contato, double total);
}
```

### `src/feira/solucao/notificacao/NotificadorWhatsApp.java`

```java
package feira.solucao.notificacao;

public class NotificadorWhatsApp implements NotificadorPedido {
    @Override
    public void notificarFinalizacao(String contato, double total) {
        System.out.println("[WhatsApp] " + contato + " -> Pedido finalizado. Total: R$ " + total);
    }
}
```

### `src/feira/solucao/relatorio/ExportadorRelatorioPedido.java`

```java
package feira.solucao.relatorio;

import feira.solucao.domain.Pedido;

public interface ExportadorRelatorioPedido {
    String exportar(Pedido pedido, double totalLiquido);
}
```

### `src/feira/solucao/relatorio/ExportadorCsvPedido.java`

```java
package feira.solucao.relatorio;

import feira.solucao.domain.Pedido;
import feira.solucao.domain.PedidoItem;

public class ExportadorCsvPedido implements ExportadorRelatorioPedido {
    @Override
    public String exportar(Pedido pedido, double totalLiquido) {
        StringBuilder csv = new StringBuilder();
        csv.append("cliente,produto,quantidade,subtotal\\n");
        for (PedidoItem item : pedido.getItens()) {
            csv.append(pedido.getCliente()).append(",")
                    .append(item.getProduto().getNome()).append(",")
                    .append(item.getQuantidade()).append(",")
                    .append(item.subtotal()).append("\\n");
        }
        csv.append("TOTAL,,,").append(totalLiquido);
        return csv.toString();
    }
}
```

## 6) Entrega (LSP)

### `src/feira/solucao/entrega/CalculadoraPrazoEntrega.java`

```java
package feira.solucao.entrega;

public interface CalculadoraPrazoEntrega {
    String tipo();

    int calcularPrazoDias(double distanciaKm);
}
```

### `src/feira/solucao/entrega/EntregaNormal.java`

```java
package feira.solucao.entrega;

public class EntregaNormal implements CalculadoraPrazoEntrega {
    @Override
    public String tipo() {
        return "NORMAL";
    }

    @Override
    public int calcularPrazoDias(double distanciaKm) {
        validarDistancia(distanciaKm);
        return (int) Math.ceil(distanciaKm / 10.0);
    }

    private void validarDistancia(double distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("Distância inválida");
        }
    }
}
```

### `src/feira/solucao/entrega/EntregaExpressa.java`

```java
package feira.solucao.entrega;

public class EntregaExpressa implements CalculadoraPrazoEntrega {
    @Override
    public String tipo() {
        return "EXPRESSA";
    }

    @Override
    public int calcularPrazoDias(double distanciaKm) {
        validarDistancia(distanciaKm);
        int prazoBase = (int) Math.ceil(distanciaKm / 20.0);
        return Math.max(1, prazoBase);
    }

    private void validarDistancia(double distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("Distância inválida");
        }
    }
}
```

## 7) Serviços finais (SRP + DIP)

### `src/feira/solucao/service/CalculadoraTotalService.java`

```java
package feira.solucao.service;

import feira.solucao.domain.Pedido;

public class CalculadoraTotalService {
    public double calcularTotal(Pedido pedido) {
        return pedido.totalBruto();
    }
}
```

### `src/feira/solucao/service/PedidoFinalizado.java`

```java
package feira.solucao.service;

public class PedidoFinalizado {
    private final double totalBruto;
    private final double totalLiquido;
    private final String relatorioCsv;

    public PedidoFinalizado(double totalBruto, double totalLiquido, String relatorioCsv) {
        this.totalBruto = totalBruto;
        this.totalLiquido = totalLiquido;
        this.relatorioCsv = relatorioCsv;
    }

    public double getTotalBruto() {
        return totalBruto;
    }

    public double getTotalLiquido() {
        return totalLiquido;
    }

    public String getRelatorioCsv() {
        return relatorioCsv;
    }
}
```

### `src/feira/solucao/service/FinalizadorPedidoService.java`

```java
package feira.solucao.service;

import feira.solucao.cupom.ImpressoraCupom;
import feira.solucao.desconto.CalculadoraDesconto;
import feira.solucao.domain.Pedido;
import feira.solucao.notificacao.NotificadorPedido;
import feira.solucao.pagamento.ServicoPagamento;
import feira.solucao.relatorio.ExportadorRelatorioPedido;
import feira.solucao.repository.PedidoRepository;

public class FinalizadorPedidoService {
    private final PedidoRepository pedidoRepository;
    private final CalculadoraDesconto calculadoraDesconto;
    private final ServicoPagamento servicoPagamento;
    private final ImpressoraCupom impressoraCupom;
    private final NotificadorPedido notificadorPedido;
    private final ExportadorRelatorioPedido exportadorRelatorio;

    public FinalizadorPedidoService(
            PedidoRepository pedidoRepository,
            CalculadoraDesconto calculadoraDesconto,
            ServicoPagamento servicoPagamento,
            ImpressoraCupom impressoraCupom,
            NotificadorPedido notificadorPedido,
            ExportadorRelatorioPedido exportadorRelatorio) {
        this.pedidoRepository = pedidoRepository;
        this.calculadoraDesconto = calculadoraDesconto;
        this.servicoPagamento = servicoPagamento;
        this.impressoraCupom = impressoraCupom;
        this.notificadorPedido = notificadorPedido;
        this.exportadorRelatorio = exportadorRelatorio;
    }

    public PedidoFinalizado finalizar(Pedido pedido, String desconto, String pagamento, String contato) {
        if (pedido == null || pedido.vazio()) {
            throw new IllegalArgumentException("Pedido vazio ou nulo");
        }

        double totalBruto = pedido.totalBruto();
        double totalLiquido = calculadoraDesconto.aplicar(desconto, totalBruto);

        servicoPagamento.pagar(pagamento, totalLiquido);
        pedidoRepository.salvar(pedido);
        impressoraCupom.imprimir(pedido, totalLiquido);
        notificadorPedido.notificarFinalizacao(contato, totalLiquido);

        String csv = exportadorRelatorio.exportar(pedido, totalLiquido);
        return new PedidoFinalizado(totalBruto, totalLiquido, csv);
    }
}
```

## 8) Classe principal da solução

### `src/feira/solucao/SolucaoMain.java`

```java
package feira.solucao;

import feira.solucao.cupom.ImpressoraCupom;
import feira.solucao.cupom.ImpressoraTermica;
import feira.solucao.desconto.CalculadoraDesconto;
import feira.solucao.desconto.DescontoClienteFiel;
import feira.solucao.desconto.DescontoDomingo;
import feira.solucao.desconto.DescontoQueimaEstoque;
import feira.solucao.desconto.SemDesconto;
import feira.solucao.domain.Pedido;
import feira.solucao.domain.Produto;
import feira.solucao.entrega.CalculadoraPrazoEntrega;
import feira.solucao.entrega.EntregaExpressa;
import feira.solucao.entrega.EntregaNormal;
import feira.solucao.notificacao.NotificadorPedido;
import feira.solucao.notificacao.NotificadorWhatsApp;
import feira.solucao.pagamento.PagamentoBoleto;
import feira.solucao.pagamento.PagamentoCartao;
import feira.solucao.pagamento.PagamentoPix;
import feira.solucao.pagamento.ServicoPagamento;
import feira.solucao.relatorio.ExportadorCsvPedido;
import feira.solucao.relatorio.ExportadorRelatorioPedido;
import feira.solucao.repository.PedidoRepository;
import feira.solucao.repository.PedidoRepositoryMemoria;
import feira.solucao.service.FinalizadorPedidoService;
import feira.solucao.service.PedidoFinalizado;
import java.util.Arrays;

public class SolucaoMain {
    public static void main(String[] args) {
        PedidoRepository repository = new PedidoRepositoryMemoria();

        CalculadoraDesconto calculadoraDesconto = new CalculadoraDesconto(Arrays.asList(
                new SemDesconto(),
                new DescontoClienteFiel(),
                new DescontoQueimaEstoque(),
                new DescontoDomingo()));

        ServicoPagamento servicoPagamento = new ServicoPagamento(Arrays.asList(
                new PagamentoPix(),
                new PagamentoCartao(),
                new PagamentoBoleto()));

        ImpressoraCupom impressora = new ImpressoraTermica();
        NotificadorPedido notificador = new NotificadorWhatsApp();
        ExportadorRelatorioPedido exportador = new ExportadorCsvPedido();

        FinalizadorPedidoService finalizador = new FinalizadorPedidoService(
                repository,
                calculadoraDesconto,
                servicoPagamento,
                impressora,
                notificador,
                exportador);

        Pedido pedido = new Pedido("Maria da Feira");
        pedido.adicionarItem(new Produto("Tomate", 8.0), 2);
        pedido.adicionarItem(new Produto("Cenoura", 6.5), 3);

        PedidoFinalizado resultado = finalizador.finalizar(
                pedido,
                "CLIENTE_FIEL",
                "PIX",
                "85999990000");

        System.out.println("Total bruto: R$ " + resultado.getTotalBruto());
        System.out.println("Total líquido: R$ " + resultado.getTotalLiquido());
        System.out.println("CSV:\n" + resultado.getRelatorioCsv());

        CalculadoraPrazoEntrega entregaNormal = new EntregaNormal();
        CalculadoraPrazoEntrega entregaExpressa = new EntregaExpressa();
        System.out.println("Prazo normal (30km): " + entregaNormal.calcularPrazoDias(30) + " dia(s)");
        System.out.println("Prazo expresso (30km): " + entregaExpressa.calcularPrazoDias(30) + " dia(s)");
    }
}
```

## Observação final

Você pode usar este documento como referência de implementação durante a oficina.
Se quiser separar por etapa em sala, copie apenas os blocos da etapa atual.
