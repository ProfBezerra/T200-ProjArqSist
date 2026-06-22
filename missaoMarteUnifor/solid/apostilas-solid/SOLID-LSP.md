# Apostila – SOLID: LSP (Substituição de Liskov)

![LSP – Substituição de Liskov](../assets/solid/lsp.svg)

**Objetivo:** Garantir que subtipos possam substituir seus tipos base sem quebrar o sistema.

## Conceito
LSP (Liskov Substitution Principle) afirma que uma instância de subtipo deve funcionar onde se espera o tipo base.

## Exemplo (Feira Livre)
- `ProdutoOrganico` pode ser usado como `Produto` em toda a aplicação — ver [feira-livre-java/src/feira/Main.java](../feira-livre-java/src/feira/Main.java).

```java
Produto p = new ProdutoOrganico("Tomate", 10.0); // tratado como Produto
double preco = p.getPreco(); // comportamento válido do subtipo
```

## Cuidados
- Respeite invariantes do tipo base (ex.: preço não negativo).
- Não quebre expectativas de métodos públicos.

## Exercícios
- Crie um novo subtipo garantindo que não retorna preço negativo. Teste substituição em pontos que esperam `Produto`.
- Valide que nenhum cliente precisa saber o tipo concreto para funcionar.

## Checklist
- Subtipos respeitam as pré/pós-condições do tipo base?
- Não há throws inesperados ou efeitos colaterais não previstos?

## Como validar
- Substitua `Produto` por seu subtipo em vários lugares (ex.: `PedidoItem`) e verifique se tudo continua correto.

## Referências
- Apostila OO (Herança/Polimorfismo)
- Projeto: `Main.java`, `ProdutoOrganico.java`
