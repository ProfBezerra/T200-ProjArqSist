# Apostila GOF — Padrões de Projeto (Missão Marte Unifor)

> T200 – Projeto e Arquitetura de Sistemas · Unifor 2026.2

Esta apostila organiza os padrões GoF (Gang of Four) com exemplos do domínio **Missão Marte Unifor** — o jogo console de resgate de passageiros em grade 2D.

Objetivo:
- Entender quando usar cada padrão.
- Evitar acoplamento desnecessário.
- Melhorar manutenção e evolução do código.

## Estrutura da apostila

- [introducao-gof.md](introducao-gof.md): visão geral, categorias e estratégia de estudo
- [factory-method/APOSTILA.md](factory-method/APOSTILA.md): criação polimórfica de passageiros
- [builder/APOSTILA.md](builder/APOSTILA.md): montagem passo a passo de configurações de missão
- [abstract-factory/APOSTILA.md](abstract-factory/APOSTILA.md): famílias de objetos por modo de jogo
- [adapter/APOSTILA.md](adapter/APOSTILA.md): integrar API externa de leaderboard
- [decorator/APOSTILA.md](decorator/APOSTILA.md): empilhar bônus de pontuação
- [facade/APOSTILA.md](facade/APOSTILA.md): simplificar o início de uma partida
- [strategy/APOSTILA.md](strategy/APOSTILA.md): estratégias de movimento de inimigos
- [observer/APOSTILA.md](observer/APOSTILA.md): notificar componentes de eventos do jogo
- [command/APOSTILA.md](command/APOSTILA.md): encapsular movimentos da nave com desfazer

## Domínio da Missão Marte (base dos exemplos)

Entidades usadas nos exemplos:

| Entidade | Papel no domínio |
|---|---|
| `Nave` | Jogador — se move no mapa |
| `Passageiro` | Abstração de Professor, Engenheiro, Astronauta |
| `Professor`, `Engenheiro`, `Astronauta` | Passageiros concretos a resgatar |
| `Perigo` | Interface para Asteroide e Inimigo |
| `Asteroide`, `Inimigo` | Perigos que causam colisão |
| `Missao` | O mapa 2D com todos os elementos |
| `Dificuldade` | Enum FACIL / MEDIO / DIFICIL |
| `GameController` | Recebe input do usuário |
| `JogoService` | Lógica pura do jogo |
| `FabricaMissao` | Cria e popula missões |
| `MapaRenderer` | Renderiza o mapa no console |
| `IRankingRepository` | Interface de persistência do ranking |
| `RankingEntry` | Um registro de pontuação |

## Relação com GRASP e SOLID

- **GRASP** definiu quem é responsável pelo quê (já feito na migração do exercício 10).
- **SOLID** valida que cada classe tem forma saudável.
- **GOF** oferece estruturas reutilizáveis para problemas recorrentes de design.

Em resumo: GRASP define "quem faz", SOLID mede qualidade, GOF oferece estruturas clássicas para implementar.

## Sugestão de ordem de estudo

1. [Factory Method](factory-method/APOSTILA.md) — criação com variação controlada
2. [Builder](builder/APOSTILA.md) — objetos complexos com muitos campos
3. [Abstract Factory](abstract-factory/APOSTILA.md) — famílias de objetos
4. [Adapter](adapter/APOSTILA.md) — integração com APIs incompatíveis
5. [Decorator](decorator/APOSTILA.md) — comportamentos empilháveis
6. [Facade](facade/APOSTILA.md) — fluxo simples sobre subsistemas complexos
7. [Strategy](strategy/APOSTILA.md) — algoritmos intercambiáveis
8. [Observer](observer/APOSTILA.md) — propagação de eventos
9. [Command](command/APOSTILA.md) — ações com histórico e desfazer
