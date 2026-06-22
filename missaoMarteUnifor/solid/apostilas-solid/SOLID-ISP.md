# Apostila – SOLID: ISP (Segregação de Interface)

![ISP – Segregação de Interface](../assets/solid/isp.svg)

**Objetivo:** Evitar interfaces inchadas; preferir contratos pequenos e coesos.

## Conceito
ISP (Interface Segregation Principle) sugere dividir interfaces para que implementadores não sejam forçados a métodos desnecessários.

## Exemplo (Feira Livre)
- `PedidoRepository` tem um contrato mínimo `salvar(Pedido)` — ver [feira-livre-java/src/feira/PedidoRepository.java](../feira-livre-java/src/feira/PedidoRepository.java).

```java
public interface PedidoRepository { void salvar(Pedido pedido); }
```

## Extensões possíveis
- Separar leitura/escrita:
  - `PedidoEscritaRepository` — `salvar(Pedido)`
  - `PedidoLeituraRepository` — `listar()`, `buscarPorId()`

## Exercícios
- Crie `PedidoLeituraRepository` e uma implementação simples em memória que lista pedidos sem alterar quem escreve.
- Mostre que `PedidoService` pode depender apenas da interface de escrita quando só precisa salvar.

## Checklist
- Nenhuma implementação é obrigada a métodos que não usa.
- Interfaces pequenas e focadas por caso de uso.

## Como validar
- Adicionar uma operação de leitura não deve impactar implementações de escrita.

## Referências
- Apostila OO (Interfaces, Baixo acoplamento)
- Projeto: `PedidoRepository.java`, `PedidoRepositoryMemoria.java`
