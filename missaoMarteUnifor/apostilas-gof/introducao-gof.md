# Introducao aos Padroes GoF no contexto da Feira Livre

Os padroes GoF surgiram para registrar solucoes recorrentes em design orientado a objetos.

Livro de referencia:

- Design Patterns: Elements of Reusable Object-Oriented Software (Gamma, Helm, Johnson, Vlissides, 1994).

## Historico dos padroes de projeto

Antes dos padroes serem formalizados, equipes de software ja reaproveitavam solucoes parecidas para problemas recorrentes de design. Faltava, porem, uma linguagem comum para nomear essas solucoes e discutir trade-offs com clareza.

Linha do tempo resumida:

1. Anos 1970-1980: em arquitetura civil, Christopher Alexander populariza a ideia de "pattern language", influenciando a area de software.
2. Final dos anos 1980 e inicio dos 1990: a comunidade OO amadurece tecnicas de reuso e design colaborativo.
3. 1994: o livro GoF consolida 23 padroes classicos e organiza o tema em criacionais, estruturais e comportamentais.
4. Anos 2000 em diante: os padroes passam a integrar curriculos, frameworks e boas praticas de engenharia de software.

Impacto pratico:

- Melhor comunicacao entre desenvolvedores (ex.: "vamos usar Strategy aqui").
- Reducao de retrabalho ao reutilizar solucoes conhecidas.
- Base para dialogo com outros referenciais, como GRASP e SOLID.

Importante: padrao nao e receita fixa. O valor esta em compreender contexto, forcas e consequencias de cada escolha de design.

## As 3 categorias

1. Criacionais: tratam da criacao de objetos.
2. Estruturais: tratam da composicao e organizacao de classes/objetos.
3. Comportamentais: tratam da colaboracao e distribuicao de responsabilidades em tempo de execucao.

Padroes desta apostila organizados por grupo:

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

Ordem de estudo por categoria pura:

1. Criacionais: [Factory Method](factory-method/APOSTILA.md), [Builder](builder/APOSTILA.md), [Abstract Factory](abstract-factory/APOSTILA.md)
2. Estruturais: [Adapter](adapter/APOSTILA.md), [Decorator](decorator/APOSTILA.md), [Facade](facade/APOSTILA.md)
3. Comportamentais: [Strategy](strategy/APOSTILA.md), [Observer](observer/APOSTILA.md), [Command](command/APOSTILA.md)

Resumo rapido para decisao:

| Grupo          | Padrao                                        | Problema tipico                                              | Quando usar                                                                        |
| -------------- | --------------------------------------------- | ------------------------------------------------------------ | ---------------------------------------------------------------------------------- |
| Criacional     | [Abstract Factory](abstract-factory/APOSTILA.md) | Criar familias de objetos relacionados e consistentes        | Quando voce precisa variar "kits" de objetos por canal, ambiente ou contexto       |
| Criacional     | [Builder](builder/APOSTILA.md)                   | Montagem de objeto complexo com muitos campos opcionais      | Quando construtor longo e confuso comeca a prejudicar legibilidade                 |
| Criacional     | [Factory Method](factory-method/APOSTILA.md)     | Criacao de objetos com regras que variam por tipo            | Quando voce quer remover if/switch de instanciacao e facilitar novos tipos         |
| Estrutural     | [Adapter](adapter/APOSTILA.md)                   | Interfaces incompativeis entre sistema interno e API externa | Quando precisa integrar servicos legados ou terceiros sem contaminar o dominio     |
| Estrutural     | [Decorator](decorator/APOSTILA.md)               | Combinacao dinamica de comportamentos sem explodir heranca   | Quando precisa empilhar regras opcionais (taxa, desconto, adicional)               |
| Estrutural     | [Facade](facade/APOSTILA.md)                     | Fluxo de uso complexo com muitos servicos                    | Quando quer expor uma API simples para casos de uso de alto nivel                  |
| Comportamental | [Strategy](strategy/APOSTILA.md)                 | Variacao de algoritmo/regra de negocio                       | Quando quer trocar comportamento em tempo de execucao sem alterar o contexto       |
| Comportamental | [Observer](observer/APOSTILA.md)                 | Notificar varios interessados apos mudanca de estado         | Quando eventos de dominio precisam acionar multiplos destinos de forma desacoplada |
| Comportamental | [Command](command/APOSTILA.md)                   | Encapsular acoes para historico, fila e desfazer             | Quando precisa separar invocacao de execucao e manter trilha de comandos           |

## Dominio da Feira Livre (base da apostila)

Entidades comuns nos exemplos:

- Produto
- TipoProduto
- ItemPedido
- Pedido
- Cliente
- Notificacao

Casos de uso frequentes:

- Cadastrar produtos por tipo (hortifruti, graos, laticinios)
- Calcular total do pedido com regras diferentes
- Notificar clientes quando o preco muda
- Integrar meios de pagamento externos

## Quando NAO usar padroes

- Quando o problema ainda e simples e nao ha variacao real.
- Quando o padrao aumenta complexidade sem ganho claro.
- Quando o time nao entende o objetivo da abstracao criada.

Regra pratica:

- Prefira clareza hoje.
- Introduza padrao quando houver repeticao de problema ou variacao previsivel.

## Mapa mental rapido

- Preciso criar familias consistentes de objetos por contexto? Abstract Factory.
- Preciso montar objeto complexo em etapas? Builder.
- Preciso variar forma de criar objetos? Factory Method.
- Preciso conectar classes incompativeis? Adapter.
- Preciso adicionar funcionalidades sem explodir heranca? Decorator.
- Preciso simplificar um fluxo complexo? Facade.
- Preciso trocar algoritmo/regra sem if encadeado? Strategy.
- Preciso avisar varios interessados em um evento? Observer.
- Preciso ter historico de acoes com desfazer/refazer? Command.

## Mini roteiro de avaliacao de design

Antes de aplicar um padrao, pergunte:

1. Qual problema concreto estou resolvendo?
2. Quais variacoes espero no proximo semestre?
3. O custo da abstracao compensa o beneficio?
4. Existe alternativa mais simples?

## Exercicio inicial

Modele um caso de compra na feira com:

- Duas formas de desconto.
- Dois meios de notificacao.

Depois responda:

- Onde ha variacao de comportamento?
- Que padrao ajudaria a reduzir condicionais?
- Que parte merece uma fachada para simplificar uso?
