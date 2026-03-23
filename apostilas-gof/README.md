# Apostila GOF — Padrões de Projeto (Feira Livre)

Esta apostila organiza os padrões GoF (Gang of Four) em uma trilha prática, com exemplos de um domínio simples de Feira Livre.

Objetivo:
- Entender quando usar cada padrão.
- Evitar acoplamento desnecessário.
- Melhorar manutenção e evolução do código.

## Estrutura da apostila

- [introducao-gof.md](introducao-gof.md): visão geral, categorias e estratégia de estudo
- [factory-method/APOSTILA.md](factory-method/APOSTILA.md): criação de objetos com variação controlada
- [builder/APOSTILA.md](builder/APOSTILA.md): construção passo a passo de objetos complexos
- [abstract-factory/APOSTILA.md](abstract-factory/APOSTILA.md): criação de famílias de objetos relacionados
- [adapter/APOSTILA.md](adapter/APOSTILA.md): integração com APIs incompatíveis
- [decorator/APOSTILA.md](decorator/APOSTILA.md): extensão de comportamento sem herança rígida
- [facade/APOSTILA.md](facade/APOSTILA.md): simplificação de fluxos complexos
- [strategy/APOSTILA.md](strategy/APOSTILA.md): troca de regras de negócio em tempo de execução
- [observer/APOSTILA.md](observer/APOSTILA.md): propagação de eventos entre objetos
- [command/APOSTILA.md](command/APOSTILA.md): encapsulamento de ações com histórico de execução

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

---

## Código e exercícios por padrão

Cada padrão possui uma pasta com código Java compilável e a resolução dos exercícios.

| Padrão | Apostila | Código | Exercícios |
|---|---|---|---|
| Factory Method | [APOSTILA.md](factory-method/APOSTILA.md) | [MainFactoryMethod.java](factory-method/MainFactoryMethod.java) | [RESOLUCAO](factory-method/RESOLUCAO-EXERCICIOS.md) |
| Builder | [APOSTILA.md](builder/APOSTILA.md) | [MainBuilder.java](builder/MainBuilder.java) | [RESOLUCAO](builder/RESOLUCAO-EXERCICIOS.md) |
| Abstract Factory | [APOSTILA.md](abstract-factory/APOSTILA.md) | [MainAbstractFactory.java](abstract-factory/MainAbstractFactory.java) | [RESOLUCAO](abstract-factory/RESOLUCAO-EXERCICIOS.md) |
| Adapter | [APOSTILA.md](adapter/APOSTILA.md) | [MainAdapter.java](adapter/MainAdapter.java) | [RESOLUCAO](adapter/RESOLUCAO-EXERCICIOS.md) |
| Decorator | [APOSTILA.md](decorator/APOSTILA.md) | [MainDecorator.java](decorator/MainDecorator.java) | [RESOLUCAO](decorator/RESOLUCAO-EXERCICIOS.md) |
| Facade | [APOSTILA.md](facade/APOSTILA.md) | [MainFacade.java](facade/MainFacade.java) | [RESOLUCAO](facade/RESOLUCAO-EXERCICIOS.md) |
| Strategy | [APOSTILA.md](strategy/APOSTILA.md) | [MainStrategy.java](strategy/MainStrategy.java) | [RESOLUCAO](strategy/RESOLUCAO-EXERCICIOS.md) |
| Observer | [APOSTILA.md](observer/APOSTILA.md) | [MainObserver.java](observer/MainObserver.java) | [RESOLUCAO](observer/RESOLUCAO-EXERCICIOS.md) |
| Command | [APOSTILA.md](command/APOSTILA.md) | [MainCommand.java](command/MainCommand.java) | [RESOLUCAO](command/RESOLUCAO-EXERCICIOS.md) |

### Como compilar e executar cada padrão

Todos os arquivos são independentes — não precisam de Maven nem de configuração.

```bash
# Exemplo: Factory Method
cd apostilas-gof/factory-method
javac MainFactoryMethod.java
java MainFactoryMethod

# Exemplo: Strategy
cd apostilas-gof/strategy
javac MainStrategy.java
java MainStrategy
```

O mesmo padrão se aplica a todos os 9 padrões: `javac Main*.java && java Main*`.
