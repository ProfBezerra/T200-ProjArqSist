# Apostila GOF — Padrões de Projeto (Feira Livre)

Esta apostila organiza os padrões GoF (Gang of Four) em uma trilha prática, com exemplos de um domínio simples de Feira Livre.

Objetivo:
- Entender quando usar cada padrão.
- Evitar acoplamento desnecessário.
- Melhorar manutenção e evolução do código.

## Estrutura da apostila

- `introducao-gof.md`: visão geral, categorias e estratégia de estudo
- `gof-abstract-factory.md`: criação de famílias de objetos relacionados
- `gof-builder.md`: construção passo a passo de objetos complexos
- `gof-factory-method.md`: criação de objetos com variação controlada
- `gof-strategy.md`: troca de regras de negócio em tempo de execução
- `gof-observer.md`: propagação de eventos entre objetos
- `gof-command.md`: encapsulamento de ações com histórico de execução
- `gof-decorator.md`: extensão de comportamento sem herança rígida
- `gof-adapter.md`: integração com APIs incompatíveis
- `gof-facade.md`: simplificação de fluxos complexos

## Sugestão de ordem de estudo

Ordem por categoria pura:

1. Criacionais
2. Estruturais
3. Comportamentais

Sequencia recomendada dentro de cada categoria:

1. Abstract Factory (Criacional)
2. Builder (Criacional)
3. Factory Method (Criacional)
4. Adapter (Estrutural)
5. Decorator (Estrutural)
6. Facade (Estrutural)
7. Strategy (Comportamental)
8. Observer (Comportamental)
9. Command (Comportamental)

## Como estudar

1. Leia o conceito e o problema que o padrão resolve.
2. Implemente o exemplo da Feira Livre em pequenos passos.
3. Faça os exercícios propostos no fim de cada arquivo.
4. Revise os checklists antes de aplicar em atividades maiores.

## Relação com GRASP e SOLID

- GRASP ajuda a decidir responsabilidades iniciais.
- SOLID ajuda a manter o design saudável ao longo da evolução.
- GOF oferece soluções reutilizáveis para problemas recorrentes de design.

Em resumo: GRASP define "quem faz", SOLID mede qualidade, GOF oferece estruturas clássicas para implementar.
