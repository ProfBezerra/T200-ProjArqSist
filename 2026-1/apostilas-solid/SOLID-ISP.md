# Apostila – SOLID: ISP (Segregação de Interface)

![ISP – Segregação de Interface](../assets/solid/isp.svg)

**Objetivo:** Evitar interfaces inchadas; preferir contratos pequenos e coesos.

## Conceito

ISP (Interface Segregation Principle) diz que uma classe não deve depender de métodos que ela não usa.

Quando uma interface cresce demais, ela obriga implementações a fornecer comportamentos irrelevantes. Isso gera acoplamento, código vazio e classes mais difíceis de manter.

## Exemplo problemático

Imagine uma interface genérica para repositórios:

```java
public interface PedidoRepository {
    void salvar(Pedido pedido);
    Pedido buscarPorId(int id);
    List<Pedido> listarTodos();
    void excluir(int id);
    void atualizar(Pedido pedido);
}
```

Agora pense em uma implementação simples em memória:

```java
public class PedidoRepositoryMemoria implements PedidoRepository {
    @Override
    public void salvar(Pedido pedido) { /* ... */ }

    @Override
    public Pedido buscarPorId(int id) { return null; }

    @Override
    public List<Pedido> listarTodos() { return List.of(); }

    @Override
    public void excluir(int id) { /* ... */ }

    @Override
    public void atualizar(Pedido pedido) { /* ... */ }
}
```

Esse contrato é grande demais. Talvez a aplicação só precise salvar pedidos, mas a interface força buscar, listar, excluir e atualizar também.

## Versão com ISP

Divida a interface por necessidade:

```java
public interface PedidoEscritaRepository {
    void salvar(Pedido pedido);
}

public interface PedidoLeituraRepository {
    Pedido buscarPorId(int id);
    List<Pedido> listarTodos();
}
```

Assim, uma classe que só grava o pedido não precisa implementar métodos de leitura e vice-versa.

## Exemplo no projeto Feira Livre

No mini projeto, a ideia é que a camada de serviço dependa de um contrato específico de persistência:

```java
public interface PedidoRepository {
    void salvar(Pedido pedido);
}
```

Essa interface é pequena e atende ao caso de uso principal. O serviço não precisa conhecer operações extras que não usa.

## Por que isso importa?

Quando a interface é pequena e focada:

- a implementação fica mais simples;
- o código é mais consistente;
- evita “contratos de barriga cheia”;
- reduz a chance de erros por implementação incompleta.

## Exercícios

1. Crie uma interface PedidoLeituraRepository com listarTodos() e buscarPorId().
2. Crie uma classe PedidoRepositoryArquivo que implemente somente a leitura ou somente a escrita.
3. Compare a manutenção de uma interface grande com uma interface pequena e coesa.

## Checklist

- A interface tem somente operações relevantes para o cliente?
- Nenhuma classe é obrigada a implementar métodos que não usa?
- As interfaces representam casos de uso e não “tudo de um repositório” em um único contrato?

## Como validar

- Se um cliente usa apenas salvar(), ele não deve depender de listarTodos() ou excluir().
- Isso mostra que a interface está bem segregada.

## Referências

- Apostila OO (Interfaces, Baixo acoplamento)
- Projeto: PedidoRepository.java
