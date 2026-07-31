# Resolução dos Exercícios — Builder

> Referência da apostila: [APOSTILA.md](APOSTILA.md)
> Código completo executável: [MainBuilder.java](MainBuilder.java)

---

## Exercício 1 — Incluir validação de cupom no `build()`

**Enunciado:** O cupom deve ser validado antes do pedido ser criado. Regra: cupom, quando informado, deve começar com "FEIRA".

**Solução:** a validação já está implementada no método `build()` de `MainBuilder.java`. Veja o trecho:

```java
Pedido build() {
    if (cliente == null || cliente.isBlank())
        throw new IllegalStateException("Cliente obrigatorio");
    if (itens.isEmpty())
        throw new IllegalStateException("Pedido precisa de pelo menos um item");
    if (!cupom.isBlank() && !cupom.startsWith("FEIRA"))
        throw new IllegalStateException("Cupom invalido: deve comecar com FEIRA");
    return new Pedido(this);
}
```

**Raciocínio:**
- O `build()` é o **único ponto de validação**. Nenhuma regra de negócio fica espalhada pelo código cliente.
- O campo `cupom` é opcional (padrão `""`). A validação só ocorre se o cupom foi preenchido.
- Isso que o Builder permite: definir campos opcionais livremente, mas validar o estado **no momento da construção**.

**Teste para validar:**
```java
// cupom valido deve funcionar
new Pedido.Builder()
    .cliente("Ana")
    .adicionarItem(new ItemPedido("Tomate", 4.50))
    .cupom("FEIRA10")
    .build(); // OK

// cupom invalido deve lancar excecao
try {
    new Pedido.Builder()
        .cliente("Ana")
        .adicionarItem(new ItemPedido("Tomate", 4.50))
        .cupom("DESCONTO10")  // nao comeca com FEIRA
        .build();
} catch (IllegalStateException e) {
    System.out.println("Cupom invalido detectado: " + e.getMessage());
}
```

---

## Exercício 2 — Criar `PedidoDiretor` para cesta semanal padrão

**Enunciado:** Criar uma classe Diretor que encapsula a montagem de um pedido "cesta semanal" pré-definido.

**Solução:** já implementada em `MainBuilder.java`:

```java
class PedidoDiretor {

    /** Monta a cesta basica padrão da feira para um cliente. */
    Pedido montarCestaSemanal(String cliente) {
        return new Pedido.Builder()
            .cliente(cliente)
            .adicionarItem(new ItemPedido("Tomate",         4.50))
            .adicionarItem(new ItemPedido("Cebola",         2.80))
            .adicionarItem(new ItemPedido("Batata",         3.00))
            .adicionarItem(new ItemPedido("Alface",         2.00))
            .adicionarItem(new ItemPedido("Feijao Carioca", 8.90))
            .tipoEntrega("RETIRADA")
            .observacao("Cesta semanal padrao")
            .build();
    }

    /** Monta um pedido express com apenas 2 itens e entrega. */
    Pedido montarPedidoExpress(String cliente) {
        return new Pedido.Builder()
            .cliente(cliente)
            .adicionarItem(new ItemPedido("Tomate", 4.50))
            .adicionarItem(new ItemPedido("Batata", 3.00))
            .tipoEntrega("MOTOBOY")
            .build();
    }
}
```

**Por que usar o Diretor?**
- Centraliza receitas de montagem (cesta básica, pedido express, pedido corporativo…).
- O código cliente não precisa conhecer quais itens compõem cada "receita".
- Reutiliza o mesmo `Builder` para variantes diferentes.

**Uso:**
```java
PedidoDiretor diretor = new PedidoDiretor();
Pedido cesta = diretor.montarCestaSemanal("Carlos");
System.out.println(cesta);
```

---

## Exercício 3 — Teste para validar erro quando não há itens

**Enunciado:** Verificar que `build()` lança exceção ao tentar criar um pedido sem itens.

```java
// Salvar como TesteBuilder.java e compilar junto com MainBuilder.java:
// javac MainBuilder.java TesteBuilder.java && java TesteBuilder

public class TesteBuilder {

    static int ok   = 0;
    static int erro = 0;

    public static void main(String[] args) {
        System.out.println("=== Testes: Builder ===");

        // Teste 1: pedido sem itens lanca excecao
        testarExcecaoSemItens();

        // Teste 2: pedido sem cliente lanca excecao
        testarExcecaoSemCliente();

        // Teste 3: cupom invalido lanca excecao
        testarExcecaoCupomInvalido();

        // Teste 4: pedido valido com campos opcionais padrao
        testarPedidoValido();

        // Teste 5: cupom valido aceito
        testarCupomValido();

        System.out.println("\nResultado: " + ok + " OK, " + erro + " FALHA(S)");
    }

    static void testarExcecaoSemItens() {
        try {
            new Pedido.Builder().cliente("Joao").build();
            falhou("Pedido sem itens deve lancar excecao");
        } catch (IllegalStateException e) {
            passou("Pedido sem itens rejeitado: " + e.getMessage());
        }
    }

    static void testarExcecaoSemCliente() {
        try {
            new Pedido.Builder().adicionarItem(new ItemPedido("Tomate", 4.50)).build();
            falhou("Pedido sem cliente deve lancar excecao");
        } catch (IllegalStateException e) {
            passou("Pedido sem cliente rejeitado: " + e.getMessage());
        }
    }

    static void testarExcecaoCupomInvalido() {
        try {
            new Pedido.Builder()
                .cliente("Maria")
                .adicionarItem(new ItemPedido("Alface", 2.00))
                .cupom("PROMO5")
                .build();
            falhou("Cupom invalido deve lancar excecao");
        } catch (IllegalStateException e) {
            passou("Cupom invalido rejeitado: " + e.getMessage());
        }
    }

    static void testarPedidoValido() {
        try {
            Pedido p = new Pedido.Builder()
                .cliente("Carlos")
                .adicionarItem(new ItemPedido("Batata", 3.00))
                .build();
            passou("Pedido valido criado com sucesso");
        } catch (Exception e) {
            falhou("Pedido valido nao deveria lancar excecao: " + e.getMessage());
        }
    }

    static void testarCupomValido() {
        try {
            new Pedido.Builder()
                .cliente("Ana")
                .adicionarItem(new ItemPedido("Cebola", 2.80))
                .cupom("FEIRA15")
                .build();
            passou("Cupom FEIRA15 aceito");
        } catch (Exception e) {
            falhou("Cupom valido nao deveria ser rejeitado: " + e.getMessage());
        }
    }

    static void passou(String msg) { System.out.println("  [OK] " + msg); ok++; }
    static void falhou(String msg) { System.out.println("  [FALHA] " + msg); erro++; }
}
```

**Como executar:**
```
javac MainBuilder.java TesteBuilder.java
java TesteBuilder
```

---

## Resumo dos conceitos

| Exercício | Conceito reforçado |
|---|---|
| 1 | Validação centralizada no `build()` — único ponto de controle |
| 2 | Diretor encapsula receitas de montagem — reutilização do Builder |
| 3 | Testabilidade — estado inválido gera exceção antes de criar objeto |
