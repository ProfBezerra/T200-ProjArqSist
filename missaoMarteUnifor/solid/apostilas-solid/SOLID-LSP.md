# Apostila – SOLID: LSP (Substituição de Liskov)

![LSP – Substituição de Liskov](../assets/solid/lsp.svg)

**Objetivo:** Garantir que subtipos possam substituir seus tipos base sem quebrar o sistema.

## Conceito
LSP (Liskov Substitution Principle) afirma que uma instância de subtipo deve funcionar onde se espera o tipo base.

## Exemplo (Missão Marte Unifor)
No tutorial, qualquer `Passageiro` pode ser usado no mesmo contexto, independentemente do subtipo.

```java
Passageiro passageiro = new Professor("Dr. Silva", 0, 0);
int pontos = passageiro.getPontuacao();

Passageiro outro = new Engenheiro("Eng. Rosa", 1, 1);
int maisPontos = outro.getPontuacao();
```

## Cuidados
- O subtipo deve respeitar a expectativa do tipo base.
- O método `getPontuacao()` deve continuar fazendo sentido para qualquer passageiro.

## Exercícios
- Crie um novo subtipo de `Passageiro` e teste se ele funciona no mesmo fluxo do jogo.
- Valide que o código cliente não precisa conhecer o tipo concreto para usar o objeto.

## Checklist
- `Professor`, `Engenheiro` e `Astronauta` podem ser tratados como `Passageiro`?
- O comportamento esperado foi preservado?

## Como validar
- Trocar um subtipo por outro no código não deve quebrar o funcionamento do embarque ou da pontuação.

## Referências
- Apostila OO (Herança/Polimorfismo)
- Projeto do tutorial: src/tutorial-exercicio10
