# Apostila – SOLID: SRP (Responsabilidade Única)

![SRP – Responsabilidade Única](../assets/solid/srp.svg)

**Objetivo:** Garantir que cada classe tenha uma única responsabilidade, facilitando manutenção, testes e evolução.

## Conceito

SRP (Single Responsibility Principle) afirma que uma classe deve ter apenas um motivo para mudar.

Em outras palavras, uma classe não deve acumular responsabilidades que pertencem a áreas diferentes do sistema. Se o código de uma classe mistura regra de negócio, validação de dados e persistência, ela passa a ser difícil de manter.

## Exemplo no projeto Feira Livre

No projeto, a ideia é separar bem os papéis de cada classe:

- Produto: cuida apenas do estado e da validação do item
- Pedido: cuida somente do conjunto de itens e do cálculo total
- PedidoService: cuida do fluxo de finalização do pedido

Essas classes têm responsabilidades distintas, então cada uma pode mudar por motivos diferentes.

### Exemplo de classe com responsabilidade bem definida

```java
public class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        setNome(nome);
        setPreco(preco);
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        this.preco = preco;
    }
}
```

Aqui, Produto é responsável por representar um produto e validar seu próprio estado. Ela não calcula o total do pedido nem persiste dados.

### Exemplo de outra classe com responsabilidade bem definida

```java
public class Pedido {
    private final List<PedidoItem> itens = new ArrayList<>();

    public void adicionarItem(Produto produto, int quantidade) {
        itens.add(new PedidoItem(produto, quantidade));
    }

    public double total() {
        return itens.stream().mapToDouble(PedidoItem::subtotal).sum();
    }
}
```

Pedido sabe como agrupar itens e calcular o total. Esse é o seu motivo de mudança. Se a forma de calcular preço mudar, a classe Pedido será afetada — mas não o Produto em si.

## Anti-exemplo a evitar

Imagine que uma classe Produto também salva os dados no banco, calcula o total do pedido e imprime a nota fiscal:

```java
public class Produto {
    public void salvarNoBanco() { /* persistência */ }
    public double calcularTotalPedido() { /* regra de pedido */ }
    public void emitirNotaFiscal() { /* impressão */ }
}
```

Esse código viola SRP porque a classe tem mais de um motivo para mudar:

- se a regra de negócio do pedido mudar;
- se a persistência mudar;
- se a emissão da nota fiscal mudar.

## Por que isso importa?

Quando cada classe tem uma responsabilidade clara:

- o código fica mais fácil de entender;
- o acoplamento diminui;
- os testes ficam mais focados;
- uma mudança em uma parte do sistema não dispara modificações em outras partes sem necessidade.

## Exercícios

1. Verifique se a classe Produto está apenas validando o próprio estado.
2. Verifique se a classe Pedido está apenas gerenciando itens e cálculo do total.
3. Imagine que você precise trocar a forma de persistência dos pedidos. Isso muda a classe Pedido ou a classe PedidoService?

## Checklist

- Cada classe tem um único foco?
- Os métodos de uma classe estão alinhados com essa responsabilidade?
- A classe não mistura regra de negócio, persistência e interface de usuário?

## Como validar

- Se uma regra de produto mudar, é necessário mexer em Produto, mas não em Pedido.
- Se a lógica de cálculo do pedido mudar, é necessário mexer em Pedido, mas não em Produto.
- Isso mostra que cada classe tem um motivo específico para mudar.

## Referências

- Apostila OO (seções Encapsulamento e Composição)
- Projeto: Produto.java, Pedido.java
