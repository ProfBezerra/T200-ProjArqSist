# Apostila – SOLID: OCP (Aberto/Fechado)

![OCP – Aberto/Fechado](../assets/solid/ocp.svg)

**Objetivo:** Permitir extensão de comportamento sem modificar código estável.

## Conceito
OCP (Open-Closed Principle) diz que módulos devem estar abertos para extensão e fechados para modificação.

## Exemplo (Feira Livre)
- `ProdutoOrganico` estende `Produto` e sobrescreve `getPreco()` — ver [feira-livre-java/src/feira/ProdutoOrganico.java](../feira-livre-java/src/feira/ProdutoOrganico.java).

```java
public class ProdutoOrganico extends Produto {
    @Override
    public double getPreco() { return super.getPreco() * 0.9; }
}
```

## Extensões possíveis
- `ProdutoPromocional`: aplica 20% de desconto.
- `ProdutoComImposto`: aplica acréscimo de imposto.

```java
public class ProdutoPromocional extends Produto {
    @Override
    public double getPreco() { return super.getPreco() * 0.8; }
}
```

## Exercícios
- Crie `ProdutoComImposto` sem editar `Produto` e integre na `Main` pela escolha do usuário.
- Garanta que `Produto` continue estável (fechado para modificação), e a variação venha por subclasses.

## Checklist
- O código base (`Produto`) permanece intacto ao adicionar novas variações?
- As novas regras são introduzidas por extensão (subclasses) ou composição (estratégias)?

## Como validar
- Adicionar um novo tipo de produto não deve quebrar o build nem exigir alterações em classes estáveis.

## Referências
- Apostila OO (Herança/Polimorfismo)
- Projeto: `ProdutoOrganico.java`
