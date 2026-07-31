# Introdução aos Padrões GoF — Missão Marte Unifor

Os padrões GoF surgiram para registrar soluções recorrentes em design orientado a objetos.

Livro de referência:

- *Design Patterns: Elements of Reusable Object-Oriented Software* (Gamma, Helm, Johnson, Vlissides, 1994).

## Histórico dos padrões de projeto

Antes dos padrões serem formalizados, equipes de software já reaproveitavam soluções parecidas para problemas recorrentes de design. Faltava, porém, uma linguagem comum para nomear essas soluções e discutir trade-offs com clareza.

Linha do tempo resumida:

1. Anos 1970–1980: em arquitetura civil, Christopher Alexander populariza a ideia de "pattern language", influenciando a área de software.
2. Final dos anos 1980 e início dos 1990: a comunidade OO amadurece técnicas de reuso e design colaborativo.
3. 1994: o livro GoF consolida 23 padrões clássicos e os organiza em criacionais, estruturais e comportamentais.
4. Anos 2000 em diante: os padrões passam a integrar currículos, frameworks e boas práticas de engenharia de software.

Impacto prático:

- Melhor comunicação entre desenvolvedores (ex.: "vamos usar Strategy para o movimento dos inimigos").
- Redução de retrabalho ao reutilizar soluções conhecidas.
- Base para diálogo com outros referenciais, como GRASP e SOLID.

Importante: padrão não é receita fixa. O valor está em compreender contexto, forças e consequências de cada escolha de design.

## As 3 categorias

1. **Criacionais**: tratam da criação de objetos.
2. **Estruturais**: tratam da composição e organização de classes/objetos.
3. **Comportamentais**: tratam da colaboração e distribuição de responsabilidades em tempo de execução.

## Padrões desta apostila organizados por grupo

- Criacionais:
  - [Factory Method](factory-method/APOSTILA.md)
  - [Abstract Factory](abstract-factory/APOSTILA.md)
  - [Builder](builder/APOSTILA.md)

- Estruturais:
  - [Adapter](adapter/APOSTILA.md)
  - [Decorator](decorator/APOSTILA.md)
  - [Facade](facade/APOSTILA.md)

- Comportamentais:
  - [Strategy](strategy/APOSTILA.md)
  - [Observer](observer/APOSTILA.md)
  - [Command](command/APOSTILA.md)

## Resumo rápido para decisão

| Grupo | Padrão | Problema típico | Quando usar |
|---|---|---|---|
| Criacional | [Factory Method](factory-method/APOSTILA.md) | Criação de `Passageiro` polimórfico com if/else | Quando quiser remover if/switch de instanciação e facilitar novos tipos |
| Criacional | [Abstract Factory](abstract-factory/APOSTILA.md) | Criar família de objetos por modo de jogo | Quando precisar variar "kits" de objetos por contexto (treino, combate) |
| Criacional | [Builder](builder/APOSTILA.md) | Configurar `Missao` com muitos campos opcionais | Quando construtor longo e confuso começa a prejudicar legibilidade |
| Estrutural | [Adapter](adapter/APOSTILA.md) | API externa de leaderboard com interface diferente | Quando precisar integrar serviços externos sem contaminar o domínio |
| Estrutural | [Decorator](decorator/APOSTILA.md) | Empilhar bônus de pontuação (velocidade, resgate completo…) | Quando precisar combinar comportamentos opcionais sem explodir herança |
| Estrutural | [Facade](facade/APOSTILA.md) | Iniciar partida envolve muitos subsistemas | Quando quiser expor uma API simples para um fluxo de alto nível |
| Comportamental | [Strategy](strategy/APOSTILA.md) | Inimigos com diferentes algoritmos de movimento | Quando quiser trocar comportamento em tempo de execução sem alterar o contexto |
| Comportamental | [Observer](observer/APOSTILA.md) | Notificar múltiplos componentes quando nave colide ou passageiro é resgatado | Quando eventos de domínio precisam acionar múltiplos destinos desacoplados |
| Comportamental | [Command](command/APOSTILA.md) | Movimentos da nave com desfazer e replay | Quando precisar separar invocação de execução e manter trilha de ações |

## Domínio da Missão Marte

Entidades comuns nos exemplos:

- `Nave` — jogador, se move no mapa
- `Passageiro` — abstração de Professor, Engenheiro, Astronauta
- `Perigo` — interface para Asteroide e Inimigo
- `Missao` — mapa 2D com todos os elementos
- `Dificuldade` — enum FACIL / MEDIO / DIFICIL
- `JogoService` — lógica pura do jogo
- `IRankingRepository` / `RankingEntry` — ranking de pontuações
