# Resolução dos Exercícios — Strategy

> Referência da apostila: [APOSTILA.md](APOSTILA.md)
> Código completo executável: [MainStrategy.java](MainStrategy.java)

---

## Exercício 1 — Implementar `DescontoFeiraFimDeSemana`

**Enunciado:** Criar uma estratégia que aplica 15% de desconto nos pedidos feitos nos finais de semana.

**Solução:** já implementada em `MainStrategy.java`:

```java
class DescontoFeiraFimDeSemana implements RegraDesconto {
    @Override
    public double aplicar(double subtotal, int quantidadeItens) {
        return subtotal * 0.85;   // 15% de desconto
    }

    @Override
    public String descricao() {
        return "Feira fim de semana (15%)";
    }
}
```

**Para deixar a estratégia automática por dia da semana:**
```java
import java.time.DayOfWeek;
import java.time.LocalDate;

class DescontoFeiraFimDeSemana implements RegraDesconto {
    @Override
    public double aplicar(double subtotal, int quantidadeItens) {
        DayOfWeek hoje = LocalDate.now().getDayOfWeek();
        boolean fimDeSemana = (hoje == DayOfWeek.SATURDAY || hoje == DayOfWeek.SUNDAY);
        return fimDeSemana ? subtotal * 0.85 : subtotal;
    }

    @Override
    public String descricao() {
        return "Desconto fim de semana (automatico)";
    }
}
```

**Por que Strategy aqui?**
- Adicionar `DescontoFeiraFimDeSemana` não exigiu alterar `CalculadoraPedido` nem nenhuma das estratégias existentes — OCP.
- O cálculo específico de cada promoção está **encapsulado na sua classe**.

---

## Exercício 2 — Carregar estratégia com base em configuração

**Enunciado:** Em vez de instanciar as estratégias diretamente, carregá-las a partir de uma chave de configuração (ex: string lida de arquivo, banco de dados, parâmetro de URL).

**Solução:** a classe `RegraDescontoFactory` já está implementada em `MainStrategy.java`:

```java
class RegraDescontoFactory {
    private static final Map<String, RegraDesconto> REGRAS = new HashMap<>();

    static {
        REGRAS.put("NENHUM",     new SemDesconto());
        REGRAS.put("FREQUENTE",  new DescontoClienteFrequente());
        REGRAS.put("VOLUME",     new DescontoPorVolume());
        REGRAS.put("FIM_SEMANA", new DescontoFeiraFimDeSemana());
        REGRAS.put("IDOSO",      new DescontoIdoso());
    }

    static RegraDesconto buscar(String chave) {
        RegraDesconto regra = REGRAS.get(chave.toUpperCase());
        if (regra == null) {
            System.out.println("  [AVISO] Regra '" + chave + "' nao encontrada. Usando SemDesconto.");
            return new SemDesconto();
        }
        return regra;
    }
}
```

**Cenário de uso — configuração em tempo de execução:**
```java
// Simulando chave vinda de banco de dados ou parâmetro de requisição
String tipoCliente = lerDoBanco("cliente_id_123");  // ex: retorna "FREQUENTE"

CalculadoraPedido calc = new CalculadoraPedido(
    RegraDescontoFactory.buscar(tipoCliente)
);
double total = calc.totalComDesconto(subtotal, qtdItens);
```

**Vantagem:** Para adicionar uma nova estratégia `ESTUDANTE`, basta:
1. Criar a classe `DescontoEstudante implements RegraDesconto`
2. Registrar `REGRAS.put("ESTUDANTE", new DescontoEstudante())` no `static`

Nenhum outro código precisará ser alterado.

---

## Exercício 3 — Testes para cada estratégia isoladamente

**Enunciado:** Verificar que cada estratégia calcula o valor correto para entradas definidas.

```java
// Salvar como TesteStrategy.java
// javac MainStrategy.java TesteStrategy.java && java TesteStrategy

public class TesteStrategy {

    static int ok   = 0;
    static int erro = 0;

    public static void main(String[] args) {
        System.out.println("=== Testes: Strategy ===");

        double sub = 200.00;

        // Teste 1: SemDesconto nao altera valor
        testar(new SemDesconto(),              sub,  5, 200.00, "SemDesconto");

        // Teste 2: cliente frequente = 5%
        testar(new DescontoClienteFrequente(), sub,  5, 190.00, "DescontoClienteFrequente (5%)");

        // Teste 3: volume abaixo do minimo (nao aplica)
        testar(new DescontoPorVolume(),        sub,  9, 200.00, "DescontoPorVolume (9 itens - sem desconto)");

        // Teste 4: volume acima do minimo (10%)
        testar(new DescontoPorVolume(),        sub, 10, 180.00, "DescontoPorVolume (10 itens - 10%)");

        // Teste 5: fim de semana = 15%
        testar(new DescontoFeiraFimDeSemana(), sub,  3, 170.00, "DescontoFeiraFimDeSemana (15%)");

        // Teste 6: idoso = 12%
        testar(new DescontoIdoso(),            sub,  2, 176.00, "DescontoIdoso (12%)");

        // Teste 7: factory retorna SemDesconto para chave desconhecida
        RegraDesconto regra = RegraDescontoFactory.buscar("INEXISTENTE");
        testar(regra, sub, 5, 200.00, "Factory chave inexistente => SemDesconto");

        System.out.println("\nResultado: " + ok + " OK, " + erro + " FALHA(S)");
    }

    static void testar(RegraDesconto regra, double sub, int qtd,
                       double esperado, String descricao) {
        double obtido = regra.aplicar(sub, qtd);
        if (Math.abs(obtido - esperado) < 0.01) {
            System.out.printf("  [OK] %-50s => R$%.2f%n", descricao, obtido);
            ok++;
        } else {
            System.out.printf("  [FALHA] %-50s => esperado=R$%.2f obtido=R$%.2f%n",
                descricao, esperado, obtido);
            erro++;
        }
    }
}
```

**Como executar:**
```
javac MainStrategy.java TesteStrategy.java
java TesteStrategy
```

---

## Resumo dos conceitos

| Exercício | Conceito reforçado |
|---|---|
| 1 | OCP — nova estratégia sem alterar contexto ou estratégias antigas |
| 2 | Desacoplamento — contexto não precisa conhecer quais estratégias existem |
| 3 | Testabilidade — cada estratégia é uma unidade de teste independente |
