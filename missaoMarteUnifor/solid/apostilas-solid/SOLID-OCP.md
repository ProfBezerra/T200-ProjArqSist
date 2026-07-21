# Apostila – SOLID: OCP (Aberto/Fechado)

![OCP – Aberto/Fechado](../assets/solid/ocp.svg)

**Objetivo:** Permitir extensão de comportamento sem modificar código estável.

## Conceito
OCP (Open-Closed Principle) diz que módulos devem estar abertos para extensão e fechados para modificação.

## Exemplo (Missão Marte Unifor)
No tutorial, o conceito aparece na abstração `Passageiro`.
- `Professor`, `Engenheiro` e `Astronauta` são subclasses diferentes.
- O sistema pode tratar todos como `Passageiro` sem precisar mudar a lógica principal.

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

## Extensões possíveis
- Criar um novo tipo de passageiro, como `Medico`, sem alterar a lógica do jogo.
- Adicionar novas regras de pontuação por meio de subclasses.

## Exercícios
- Crie um novo tipo de passageiro e faça com que ele seja reconhecido no fluxo do jogo.
- Verifique se a lógica de embarque e pontuação continua funcionando sem alterar a classe base.

## Checklist
- A classe base `Passageiro` permaneceu estável ao adicionar novos tipos?
- O sistema passou a aceitar novas variações por extensão?

## Como validar
- Adicionar um novo passageiro não deve exigir reescrever a lógica de pontuação ou embarque.

## Referências
- Apostila OO (Herança/Polimorfismo)
- Projeto do tutorial: src/tutorial-exercicio10
