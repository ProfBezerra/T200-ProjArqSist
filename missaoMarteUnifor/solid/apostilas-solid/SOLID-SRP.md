# Apostila – SOLID: SRP (Responsabilidade Única)

![SRP – Responsabilidade Única](../assets/solid/srp.svg)

**Objetivo:** Garantir que cada classe tenha uma única responsabilidade, facilitando manutenção, testes e evolução.

## Conceito
SRP (Single Responsibility Principle) afirma que uma classe deve ter apenas um motivo para mudar.

## Exemplo (Feira Livre)
- `Produto`: foca em dados e validação de nome/preço — ver [feira-livre-java/src/feira/Produto.java](../feira-livre-java/src/feira/Produto.java).
- `Pedido`: foca em regras do pedido (itens, total) — ver [feira-livre-java/src/feira/Pedido.java](../feira-livre-java/src/feira/Pedido.java).

```java
// SRP: Produto apenas valida seu estado
public class Produto {
    private String nome;
    private double preco;
    // getters/setters com validação
}

// SRP: Pedido cuida do total e dos itens
public class Pedido {
    public double total() { /* soma dos subtotais */ }
}
```

## Anti‑exemplo a evitar
- Colocar `total()` do pedido dentro de `Produto` (mistura responsabilidades do domínio).

## Exercícios
- Separe responsabilidades: verifique se classes não acumulam funções de outras (ex.: cálculo de pedido em `Pedido`, validação de preços em `Produto`).
- Extra: se alguma classe passou a ter mais de um motivo de mudança, divida-a em duas coesas.

## Checklist
- Dados de produto e regras de pedido estão separados?
- Validações de estado estão encapsuladas na própria classe?
- Métodos fazem apenas o que o nome indica?

## Como validar
- Mudanças em `Produto` não devem demandar alterações em `Pedido` (e vice-versa).
- Testes unitários por classe focam em uma responsabilidade.

## Referências
- Apostila OO (seções Encapsulamento e Composição)
- Projeto: `Produto.java`, `Pedido.java`
