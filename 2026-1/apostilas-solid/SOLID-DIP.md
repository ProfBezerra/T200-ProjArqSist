# Apostila – SOLID: DIP (Inversão de Dependência)

![DIP – Inversão de Dependência](../assets/solid/dip.svg)

**Objetivo:** Depender de abstrações em vez de implementações concretas para reduzir acoplamento.

## Conceito

DIP (Dependency Inversion Principle) afirma que módulos de alto nível não devem depender diretamente de módulos de baixo nível. Ambos devem depender de abstrações.

Em termos práticos: a regra de negócio não deve conhecer a implementação específica do banco, arquivo ou classe de persistência. Ela deve depender apenas do contrato que define o que pode ser feito.

## Exemplo problemático

Veja uma classe de serviço que depende de uma implementação concreta:

```java
public class PedidoService {
    private final PedidoRepositoryMemoria repository;

    public PedidoService() {
        this.repository = new PedidoRepositoryMemoria();
    }

    public double finalizar(Pedido pedido) {
        if (pedido == null || pedido.vazio()) {
            throw new IllegalArgumentException("Pedido inválido");
        }

        double total = pedido.total();
        repository.salvar(pedido);
        return total;
    }
}
```

Esse código acopla a regra de negócio à implementação concreta. Se a persistência trocar de memória para arquivo, a classe precisa ser modificada.

## Exemplo correto com DIP

```java
public class PedidoService {
    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public double finalizar(Pedido pedido) {
        if (pedido == null || pedido.vazio()) {
            throw new IllegalArgumentException("Pedido inválido");
        }

        double total = pedido.total();
        repository.salvar(pedido);
        return total;
    }
}
```

Agora a classe depende da abstração PedidoRepository. Isso permite trocar a implementação sem mexer no serviço.

## Exemplo com a aplicação

Na Main, você pode criar a implementação desejada e injetar no serviço:

```java
public class Main {
    public static void main(String[] args) {
        PedidoRepository repo = new PedidoRepositoryMemoria();
        PedidoService service = new PedidoService(repo);

        Pedido pedido = new Pedido();
        service.finalizar(pedido);
    }
}
```

Se no futuro surgir PedidoRepositoryArquivo, basta trocar a instância:

```java
PedidoRepository repo = new PedidoRepositoryArquivo();
PedidoService service = new PedidoService(repo);
```

A lógica do serviço continua igual.

## Por que isso importa?

O DIP ajuda a manter a regra de negócio estável mesmo quando a infraestrutura muda. Isso reduz acoplamento, facilita testes e permite evoluir o sistema passo a passo.

## Exercícios

1. Crie uma segunda implementação de PedidoRepository, como PedidoRepositoryArquivo.
2. Injete a nova implementação na Main e compare com a versão anterior.
3. Verifique que PedidoService não precisa mudar para trocar a infraestrutura.

## Checklist

- A classe de negócio depende de interface ou abstração?
- A infraestrutura pode ser trocada sem mexer no serviço?
- Há baixo acoplamento entre regras de negócio e persistência?

## Como validar

- Trocar de PedidoRepositoryMemoria para outro tipo de repositório não deve exigir mudança em PedidoService.
- Isso mostra que as dependências foram invertidas corretamente.

## Referências

- Apostila OO (Interfaces, DIP)
- Projeto: PedidoService.java, PedidoRepository.java
