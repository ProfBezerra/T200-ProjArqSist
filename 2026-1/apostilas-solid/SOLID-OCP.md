# Apostila – SOLID: OCP (Aberto/Fechado)

![OCP – Aberto/Fechado](../assets/solid/ocp.svg)

**Objetivo:** Permitir extensão de comportamento sem modificar código estável.

## Conceito

OCP (Open-Closed Principle) diz que um módulo deve estar aberto para extensão e fechado para modificação.

Isso significa que novas regras devem ser adicionadas sem reescrever o código que já funciona. Em vez de alterar a classe base, é melhor criar novas variações ou comportamentos por extensão.

## Exemplo no projeto Feira Livre

A classe Produto representa o comportamento geral de um item da feira. Diferentes tipos de produto podem existir, como orgânico, promocional ou com imposto, mas a classe base deve continuar sendo usada sem precisar ser alterada sempre que surgir um novo tipo.

### Versão sem OCP

```java
public class Produto {
    private String nome;
    private double preco;
    private String tipo; // "organico", "promocional", "normal"

    public double getPreco() {
        if (tipo.equals("organico")) {
            return preco * 0.9;
        }
        if (tipo.equals("promocional")) {
            return preco * 0.8;
        }
        return preco;
    }
}
```

Esse código quebra OCP porque toda vez que surgirem novos tipos de produto, o método getPreco() precisa ser alterado. O código base é modificado repetidamente.

### Versão com OCP

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

public class ProdutoPromocional extends Produto {
    @Override
    public double getPreco() {
        return super.getPreco() * 0.8;
    }
}
```

Agora, cada variação é adicionada por extensão. O código base Produto continua estável.

## Por que isso é importante?

O OCP reduz o risco de regressão. Quando você cria uma nova categoria ou regra, não precisa mexer em todo o sistema para adaptar a lógica antiga.

É também uma forma de manter o código previsível: a classe base continua com seu comportamento principal, e as variações ficam encapsuladas em subclasses.

## Exemplo com o projeto

Observe a classe ProdutoOrganico do projeto:

```java
public class ProdutoOrganico extends Produto {
    @Override
    public double getPreco() {
        return super.getPreco() * 0.9;
    }
}
```

Isso mantém a regra do desconto orgânico separada do comportamento padrão do produto.

## Exercícios

1. Crie uma classe ProdutoComImposto sem alterar Produto.
2. Crie uma classe ProdutoPremium com preço diferente e veja como isso se encaixa sem mexer no código base.
3. Escreva um pequeno método que recebe Produto e imprime o preço final, independentemente da variação específica.

## Checklist

- O código base continua estável ao adicionar novas regras?
- As variações são expressas por extensão?
- Novos tipos exigem alteração em uma classe central ou em subclasses isoladas?

## Como validar

- Adicionar um novo tipo de produto não deve exigir alteração em todas as regras antigas.
- A classe base continua funcionando como contrato principal.

## Referências

- Apostila OO (Herança/Polimorfismo)
- Projeto: ProdutoOrganico.java
