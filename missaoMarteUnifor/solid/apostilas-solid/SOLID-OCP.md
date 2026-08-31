# Apostila – SOLID: OCP (Aberto/Fechado)

![OCP – Aberto/Fechado](../assets/solid/ocp.svg)

**Objetivo:** Permitir extensão de comportamento sem modificar código estável.

## Conceito

OCP (Open-Closed Principle) diz que um módulo deve estar aberto para extensão e fechado para modificação.

Em outras palavras, quando surge uma nova regra ou um novo tipo de entidade, o sistema deve aceitar essa variação sem exigir reescrever o que já funciona. O ideal é adicionar comportamento por extensão, não por alteração do código base.

## Exemplo com Passageiro no tutorial

No jogo, o tipo base `Passageiro` representa o contrato principal. Subclasses como `Professor`, `Engenheiro` ou `Astronauta` representam variações específicas.

```java
public abstract class Passageiro {
    public abstract int getPontuacao();
}

public class Professor extends Passageiro {
    @Override
    public int getPontuacao() {
        return 15;
    }
}

public class Engenheiro extends Passageiro {
    @Override
    public int getPontuacao() {
        return 20;
    }
}
```

A lógica do sistema pode tratar qualquer passageiro da mesma forma:

```java
public class Missao {
    public int calcularTotalBonus(List<Passageiro> passageiros) {
        int total = 0;
        for (Passageiro p : passageiros) {
            total += p.getPontuacao();
        }
        return total;
    }
}
```

Se surgir um novo tipo de passageiro, como `Medico`, basta criar uma nova subclasse. O restante do código continua funcionando.

## Anti-exemplo a evitar

```java
public class Passageiro {
    private String tipo;

    public int getPontuacao() {
        if (tipo.equals("professor")) return 15;
        if (tipo.equals("engenheiro")) return 20;
        return 0;
    }
}
```

Esse código quebra OCP porque qualquer nova categoria exige mexer no método de cálculo principal.

## Por que isso importa?

O OCP reduz riscos de regressão. Quando a regra muda, você não precisa mexer em todo o sistema antigo. Em vez disso, cria uma especialização que adiciona comportamento novo sem quebrar o contrato anterior.

## Exercícios

1. Crie um novo tipo de passageiro e veja como ele pode ser incluído sem alterar a lógica principal.
2. Verifique se a lógica de embarque e pontuação continua funcionando sem mexer na classe base.
3. Observe que a base `Passageiro` continua estável e o sistema se expande por extensão.

## Checklist

- A classe base permaneceu estável ao adicionar novos tipos?
- O sistema passou a aceitar novas variações por extensão?
- As regras específicas ficaram em subclasses, e não em um código central sempre alterado?

## Como validar

- Adicionar um novo passageiro não deve exigir reescrever a lógica de pontuação ou embarque.
- Se isso acontece, o código ainda está preso a uma implementação rígida.

## Referências

- Apostila OO (Herança/Polimorfismo)
- Projeto do tutorial: src/tutorial-exercicio10
