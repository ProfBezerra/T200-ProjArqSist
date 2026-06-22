# Resolução dos Exercícios — Observer

> Referência da apostila: [APOSTILA.md](APOSTILA.md)
> Código completo executável: [MainObserver.java](MainObserver.java)

---

## Exercício 1 — Criar observador de SMS

**Enunciado:** Criar um `NotificadorSMS` que envia mensagem de texto quando o preço muda.

**Solução:** já implementada em `MainObserver.java`:

```java
class NotificadorSMS implements ObservadorPreco {
    private final String telefone;

    NotificadorSMS(String telefone) { this.telefone = telefone; }

    @Override
    public void precoAlterado(Produto produto, double precoAntigo, double precoNovo) {
        System.out.printf("  [SMS -> %s] ALERTA: %s agora custa R$%.2f%n",
            telefone, produto.getNome(), precoNovo);
    }
}
```

**Uso:**
```java
Produto tomate = new Produto("Tomate", 4.50);
tomate.adicionarObservador(new NotificadorSMS("(85)99999-0001"));
tomate.adicionarObservador(new NotificadorEmail("cliente@email.com"));

tomate.alterarPreco(5.80);
// Ambos os observadores são notificados automaticamente
```

**Por que Observer aqui?**
- O `Produto` não precisa saber quantos ou quais canais de notificação existem.
- Adicionar `NotificadorWhatsApp`, `NotificadorPush`… não requer **nenhuma alteração** em `Produto`.
- Isso é o **OCP aplicado** ao ponto de variação "quem recebe a notificação".

---

## Exercício 2 — Implementar remoção de observador

**Enunciado:** Após um cliente cancelar a assinatura, ele não deve mais receber alertas.

**Solução:** o método `removerObservador` já está em `Produto` (`MainObserver.java`):

```java
class Produto {
    private final List<ObservadorPreco> observadores = new ArrayList<>();

    void adicionarObservador(ObservadorPreco obs) { observadores.add(obs); }
    void removerObservador(ObservadorPreco obs)    { observadores.remove(obs); }   // ← aqui

    void alterarPreco(double novoPreco) {
        if (novoPreco == this.preco) return;
        double antigo = this.preco;
        this.preco    = novoPreco;
        // Itera sobre uma cópia para permitir que 'desfazer' aconteça durante iteração
        for (ObservadorPreco obs : new ArrayList<>(observadores)) {
            obs.precoAlterado(this, antigo, novoPreco);
        }
    }
    // ...
}
```

**Demonstração no main:**
```java
ObservadorPreco smsJoao = new NotificadorSMS("(85)99999-0001");
tomate.adicionarObservador(smsJoao);

tomate.alterarPreco(5.80);  // SMS recebe

tomate.removerObservador(smsJoao);
tomate.alterarPreco(4.20);  // SMS NÃO recebe mais
```

**Ponto de atenção:** guardar a referência do observador em uma variável para poder removê-lo depois. Criar uma nova instância na remoção (`removerObservador(new NotificadorSMS(...))`) **não funciona**, pois `ArrayList.remove()` usa `equals()`, e objetos distintos não são iguais por padrão.

---

## Exercício 3 — Evitar notificação quando preço não muda

**Enunciado:** Se `alterarPreco(4.50)` for chamado e o preço já é 4.50, nenhum observador deve ser notificado.

**Solução:** implementada com verificação de igualdade antes de notificar:

```java
void alterarPreco(double novoPreco) {
    if (novoPreco == this.preco) return;   // ← guarda de igualdade

    double antigo = this.preco;
    this.preco    = novoPreco;

    for (ObservadorPreco obs : new ArrayList<>(observadores)) {
        obs.precoAlterado(this, antigo, novoPreco);
    }
}
```

> **Nota sobre comparação de `double`:** para valores de moeda, a comparação com `==` funciona quando os valores vêm de atribuições diretas (sem cálculos de ponto flutuante). Em cenários com cálculos intermediários, use tolerância:
> ```java
> if (Math.abs(novoPreco - this.preco) < 0.001) return;
> ```

**Teste de verificação:**
```java
// Salvar como TesteObserver.java
// javac MainObserver.java TesteObserver.java && java TesteObserver

public class TesteObserver {

    static int ok   = 0;
    static int erro = 0;

    public static void main(String[] args) {
        System.out.println("=== Testes: Observer ===");

        // Teste 1: alteracao dispara notificacao
        testarAlteracaoDetectada();

        // Teste 2: sem mudanca nao dispara notificacao
        testarSemMudanca();

        // Teste 3: remocao impede notificacao
        testarRemocao();

        System.out.println("\nResultado: " + ok + " OK, " + erro + " FALHA(S)");
    }

    static void testarAlteracaoDetectada() {
        int[] contador = {0};
        ObservadorPreco obs = (p, ant, nov) -> contador[0]++;

        Produto prd = new Produto("Tomate", 4.50);
        prd.adicionarObservador(obs);
        prd.alterarPreco(5.00);

        if (contador[0] == 1) passou("Alteracao dispara exatamente 1 notificacao");
        else falhou("Esperado 1 notificacao, obtido: " + contador[0]);
    }

    static void testarSemMudanca() {
        int[] contador = {0};
        ObservadorPreco obs = (p, ant, nov) -> contador[0]++;

        Produto prd = new Produto("Batata", 3.00);
        prd.adicionarObservador(obs);
        prd.alterarPreco(3.00);   // mesmo preco

        if (contador[0] == 0) passou("Preco igual nao dispara notificacao");
        else falhou("Nao deveria notificar para preco igual. Notificacoes: " + contador[0]);
    }

    static void testarRemocao() {
        int[] contador = {0};
        ObservadorPreco obs = (p, ant, nov) -> contador[0]++;

        Produto prd = new Produto("Cebola", 2.80);
        prd.adicionarObservador(obs);
        prd.removerObservador(obs);
        prd.alterarPreco(3.50);

        if (contador[0] == 0) passou("Observador removido nao recebe notificacao");
        else falhou("Observador removido nao deveria ser notificado. Notificacoes: " + contador[0]);
    }

    static void passou(String msg) { System.out.println("  [OK] " + msg); ok++; }
    static void falhou(String msg) { System.out.println("  [FALHA] " + msg); erro++; }
}
```

**Como executar:**
```
javac MainObserver.java TesteObserver.java
java TesteObserver
```

---

## Resumo dos conceitos

| Exercício | Conceito reforçado |
|---|---|
| 1 | OCP — novo observador sem alterar `Produto` |
| 2 | Gerenciamento de ciclo de vida — inscrição/cancelamento explícito |
| 3 | Eficiência — evitar notificações desnecessárias |
