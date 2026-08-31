# Apostila – SOLID: LSP (Substituição de Liskov)

![LSP – Substituição de Liskov](../assets/solid/lsp.svg)

**Objetivo:** Garantir que subtipos possam substituir seus tipos base sem quebrar o sistema.

## Conceito

LSP (Liskov Substitution Principle) diz que qualquer objeto de uma subclasse deve poder ser usado em qualquer lugar em que a classe base seria esperada, sem provocar comportamento inesperado.

A ideia é simples: se um método aceita Produto, ele deve aceitar qualquer subtipo de Produto sem exigir testes especiais ou causar falhas.

## Exemplo no projeto Feira Livre

Se a aplicação funciona com Produto, ela também deve funcionar com ProdutoOrganico.

```java
public class Produto {
    private String nome;
    private double preco;

    public double getPreco() {
        return preco;
    }
}

public class ProdutoOrganico extends Produto {
    @Override
    public double getPreco() {
        return super.getPreco() * 0.9;
    }
}
```

Agora imagine esta operação:

```java
public class Pedido {
    public double calcularTotal(Produto produto, int quantidade) {
        return produto.getPreco() * quantidade;
    }
}
```

Ela funciona com Produto e também com ProdutoOrganico, porque o contrato da classe base foi respeitado.

## Exemplo de violação do LSP

Imagine uma classe ProdutoSemEstoque que herda de Produto, mas sobrescreve getPreco() para lançar exceção:

```java
public class ProdutoSemEstoque extends Produto {
    @Override
    public double getPreco() {
        throw new IllegalStateException("Produto indisponível");
    }
}
```

Esse código viola o LSP porque o cliente que espera um Produto agora recebe uma exceção inesperada. O subtipo não respeita o comportamento esperado da superclasse.

## Como o projeto faz sentido com LSP

A classe ProdutoOrganico é uma especialização válida por causa do contrato de Produto:

- ela mantém o mesmo comportamento principal;
- ela apenas acrescenta variação de preço;
- ela pode ser usada sem que o cliente precise saber o tipo concreto.

## Exercícios

1. Crie um ProdutoPromocional e teste se o código que recebe Produto funciona com ele.
2. Crie uma subclasse que modifique o comportamento de forma que a aplicação quebre. Em seguida, explique por que ela violou LSP.
3. Verifique se o método getPreco() em todas as subclasses respeita o contrato esperado da classe base.

## Checklist

- A subclasse pode substituir a classe base sem efeitos colaterais?
- O cliente recebe os mesmos tipos de retorno e comportamentos esperados?
- Há exceções ou regras que mudam o contrato da base de forma inesperada?

## Como validar

- Substitua Produto por ProdutoOrganico em qualquer ponto do código que usa Produto.
- Se a aplicação continuar correta, o princípio está sendo respeitado.

## Referências

- Apostila OO (Herança/Polimorfismo)
- Projeto: Main.java, ProdutoOrganico.java
