# Apostilas – SOLID (Feira Livre)

Este diretório reúne as apostilas do SOLID com exemplos do mini projeto de console Feira Livre. Cada arquivo explora um princípio separadamente, mas todos caminham juntos para mostrar como um código pode evoluir de uma solução simples para uma solução mais organizada, extensível e fácil de manter.

![Arquitetura Conceitual](../assets/common/architecture-flow.svg)

## Como usar estas apostilas

As apostilas usam os mesmos elementos do projeto de exemplo:

- Produto: representa o item vendido na feira
- Pedido: agrega os produtos e calcula o total
- PedidoService: orquestra a regra de negócio
- PedidoRepository: persiste os pedidos
- ProdutoOrganico: um tipo específico de produto com comportamento diferenciado

A ideia é que o aluno entenda que o SOLID não é só uma teoria abstrata. Ele aparece no código quando a classe ganha responsabilidade demais, quando a herança quebra expectativas, quando a interface obriga métodos inúteis ou quando uma classe depende de detalhes concretos em vez de abstrações.

## Os princípios e seus arquivos

- SRP – Single Responsibility Principle: Responsabilidade Única (arquivo: SOLID-SRP.md)
- OCP – Open-Closed Principle: Aberto/Fechado (arquivo: SOLID-OCP.md)
- LSP – Liskov Substitution Principle: Substituição de Liskov (arquivo: SOLID-LSP.md)
- ISP – Interface Segregation Principle: Segregação de Interface (arquivo: SOLID-ISP.md)
- DIP – Dependency Inversion Principle: Inversão de Dependência (arquivo: SOLID-DIP.md)

## Sequência recomendada de estudo

1. Comece pelo SRP para entender o problema de responsabilidade.
2. Depois veja OCP para descobrir como estender sem quebrar o sistema.
3. Em seguida, LSP para confirmar que substituições de tipos continuam corretas.
4. Depois ISP para separar contratos grandes em interfaces menores.
5. Por fim, DIP para reduzir acoplamento e facilitar trocar implementações.

## Referências úteis

- Projeto exemplo: feira-livre-java
- Apostila principal de OO: apostila_oo_java_projeto_arquitetura.md
- UML e C4: UML-Cheat-Sheet.md, C4-guidelines.md

## Reflexão geral

O SOLID ajuda a responder uma pergunta importante: “se eu precisar alterar esse código amanhã, o quê vai quebrar e o quê vai continuar estável?”

A resposta está em separar responsabilidades, proteger contratos, evitar acoplamento excessivo e deixar o sistema pronto para evolução sem dor de manutenção.
