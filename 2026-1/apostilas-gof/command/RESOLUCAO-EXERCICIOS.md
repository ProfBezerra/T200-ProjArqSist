# Resolução dos Exercícios — Command

> Referência da apostila: [APOSTILA.md](APOSTILA.md)
> Código completo executável: [MainCommand.java](MainCommand.java)

---

## Exercício 1 — Criar `RemoverItemCommand` com desfazer

**Enunciado:** Criar um comando para remover um item do pedido, que possa ser desfeito (re-adicionando o item).

**Solução:** já implementada em `MainCommand.java`:

```java
class RemoverItemCommand implements Comando {
    private final CaixaPedido caixa;
    private final String nome;
    private final double preco;

    RemoverItemCommand(CaixaPedido caixa, String nome, double preco) {
        this.caixa = caixa;
        this.nome  = nome;
        this.preco = preco;
    }

    @Override
    public void executar()    { caixa.removerItem(nome, preco); }

    @Override
    public void desfazer()    { caixa.adicionarItem(nome, preco); }   // ← operação inversa

    @Override
    public String descricao() { return "Remover " + nome; }
}
```

**Princípio-chave do Command:** cada comando conhece sua operação inversa. O `HistoricoComandos` não precisa saber como desfazer — apenas chama `desfazer()` no comando correto.

**Uso:**
```java
HistoricoComandos hist  = new HistoricoComandos();
CaixaPedido       caixa = new CaixaPedido();

hist.executar(new AdicionarItemCommand(caixa, "Tomate",  4.50));
hist.executar(new AdicionarItemCommand(caixa, "Batata",  3.00));
hist.executar(new RemoverItemCommand(caixa, "Tomate", 4.50)); // remove por engano

hist.desfazer();  // Tomate volta ao pedido
```

---

## Exercício 2 — Implementar "Refazer" com duas pilhas

**Enunciado:** Além de `desfazer`, o sistema deve permitir `refazer` (redo) — reexecutando um comando que foi desfeito.

**Solução:** as duas pilhas já estão implementadas em `HistoricoComandos` de `MainCommand.java`:

```java
class HistoricoComandos {
    private final Deque<Comando> pilhaDesfazer = new ArrayDeque<>();
    private final Deque<Comando> pilhaRefazer  = new ArrayDeque<>();

    void executar(Comando cmd) {
        cmd.executar();
        pilhaDesfazer.push(cmd);
        pilhaRefazer.clear();          // nova acao cancela o historico de refazer
    }

    void desfazer() {
        if (pilhaDesfazer.isEmpty()) { /* nada a fazer */ return; }
        Comando cmd = pilhaDesfazer.pop();
        cmd.desfazer();
        pilhaRefazer.push(cmd);        // comanda vai para pilha de refazer
    }

    void refazer() {
        if (pilhaRefazer.isEmpty()) { /* nada a refazer */ return; }
        Comando cmd = pilhaRefazer.pop();
        cmd.executar();
        pilhaDesfazer.push(cmd);       // volta para pilha de desfazer
    }
}
```

**Fluxo visual:**

```
Ação          | pilhaDesfazer      | pilhaRefazer
------------- | ------------------ | ---------------------
exec(ADD A)   | [A]                | []
exec(ADD B)   | [B, A]             | []
exec(DISC D)  | [D, B, A]          | []
desfazer()    | [B, A]             | [D]       ← desfaz desconto
desfazer()    | [A]                | [B, D]    ← desfaz item B
refazer()     | [B, A]             | [D]       ← refaz item B
exec(ADD C)   | [C, B, A]          | []        ← nova ação limpa refazer
```

**Regra importante:** sempre que uma **nova ação** é executada via `executar()`, a pilha de `refazer` é zerada — não faz sentido "refazer" algo depois de uma nova operação diferente.

---

## Exercício 3 — Persistir histórico em memória para auditoria simples

**Enunciado:** Cada operação (executar, desfazer, refazer) deve ser registrada em uma lista de log para auditoria.

**Solução:** o log já está implementado em `HistoricoComandos` de `MainCommand.java`:

```java
class HistoricoComandos {
    private final List<String> log = new ArrayList<>();

    void executar(Comando cmd) {
        cmd.executar();
        pilhaDesfazer.push(cmd);
        pilhaRefazer.clear();
        log.add("EXEC: " + cmd.descricao());    // ← registro
    }

    void desfazer() {
        if (pilhaDesfazer.isEmpty()) return;
        Comando cmd = pilhaDesfazer.pop();
        cmd.desfazer();
        pilhaRefazer.push(cmd);
        log.add("UNDO: " + cmd.descricao());    // ← registro
    }

    void refazer() {
        if (pilhaRefazer.isEmpty()) return;
        Comando cmd = pilhaRefazer.pop();
        cmd.executar();
        pilhaDesfazer.push(cmd);
        log.add("REDO: " + cmd.descricao());    // ← registro
    }

    List<String> getLog() { return List.copyOf(log); }   // imutável

    void imprimirLog() {
        System.out.println("=== Log de Operacoes ===");
        log.forEach(e -> System.out.println("  " + e));
    }
}
```

**Saída esperada após a demo do main:**
```
=== Log de Operacoes ===
  EXEC: Adicionar Tomate
  EXEC: Adicionar Batata
  EXEC: Adicionar Cebola
  EXEC: Adicionar Alface
  EXEC: Desconto FEIRA10
  EXEC: Remover Alface
  UNDO: Remover Alface
  UNDO: Desconto FEIRA10
  REDO: Desconto FEIRA10
```

**Para persistência real** (arquivo, banco de dados), bastaria alterar o `log.add()` para escrever em um `FileWriter` ou repositório:

```java
// Salvar em arquivo (extensão possível):
void executar(Comando cmd) {
    cmd.executar();
    pilhaDesfazer.push(cmd);
    pilhaRefazer.clear();
    String entrada = "EXEC: " + cmd.descricao();
    log.add(entrada);
    salvarEmArquivo(entrada);    // extensão: escrita em disco
}

private void salvarEmArquivo(String linha) {
    try (java.io.FileWriter fw = new java.io.FileWriter("historico.log", true)) {
        fw.write(linha + System.lineSeparator());
    } catch (java.io.IOException e) {
        System.err.println("[AUDITORIA] Falha ao salvar log: " + e.getMessage());
    }
}
```

---

## Resumo dos conceitos

| Exercício | Conceito reforçado |
|---|---|
| 1 | Reversibilidade — cada comando encapsula sua operação inversa |
| 2 | Undo/Redo com duas pilhas — estrutura de dados adequada ao padrão |
| 3 | Auditoria — histórico como cidadão de primeira classe no sistema |
