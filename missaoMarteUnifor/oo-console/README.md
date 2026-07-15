Missão Marte — Exemplo OO (console)
=====================================

Este é um exemplo minimalista para aplicar conceitos de Orientação a Objetos no projeto "Missão Marte Unifor".

Conteúdo:

- `src/missao` — código fonte Java (classes: `Nave`, `Passageiro`, `Professor`, `Engenheiro`, `Asteroide`, `Missao`, `Main`).

Compilar e executar (a partir da raiz do repositório):

```bash
javac -d out missaoMarteUnifor/oo-console/src/missao/*.java
java -cp out missao.Main
```

Descrição rápida do jogo em console:

- Comandos: `w` (up), `s` (down), `a` (left), `d` (right), `c` (embarcar se houver passageiro na mesma posição), `q` (sair).
- Objetivo: embarcar todos os passageiros sem colidir com asteroides.

Use este projeto como ponto de partida para exercícios de refatoração (SOLID), testes e aplicação de padrões.
