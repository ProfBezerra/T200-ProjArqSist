# Apostilas – SOLID (Missão Marte Unifor)

Este diretório contém apostilas independentes para cada princípio do SOLID, agora com exemplos alinhados ao projeto do tutorial de refatoração da Missão Marte Unifor.

![Arquitetura Conceitual](../assets/common/architecture-flow.svg)

- SRP – Single Responsibility Principle: Responsabilidade Única (arquivo: SOLID-SRP.md)
- OCP – Open-Closed Principle: Aberto/Fechado (arquivo: SOLID-OCP.md)
- LSP – Liskov Substitution Principle: Substituição de Liskov (arquivo: SOLID-LSP.md)
- ISP – Interface Segregation Principle: Segregação de Interface (arquivo: SOLID-ISP.md)
- DIP – Dependency Inversion Principle: Inversão de Dependência (arquivo: SOLID-DIP.md)

## Contexto usado nas apostilas

As explicações abaixo usam o mesmo cenário do tutorial:
- Main inicia a aplicação.
- JogoService controla o fluxo da missão.
- MapaRenderer exibe o mapa e os elementos visuais.
- Passageiro representa os passageiros resgatados.
- RankingRepository e RankingService tratam do armazenamento do ranking.

## Referências úteis

- Projeto exemplo do tutorial: src/tutorial-exercicio10
- Apostila principal de OO: apostila_oo_java_projeto_arquitetura.md
- UML e C4: UML-Cheat-Sheet.md, C4-guidelines.md
