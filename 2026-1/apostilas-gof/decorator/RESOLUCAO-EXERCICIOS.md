# Resolução dos Exercícios — Decorator

> Referência da apostila: [APOSTILA.md](APOSTILA.md)
> Código completo executável: [MainDecorator.java](MainDecorator.java)

---

## Exercício 1 — Criar decorador de taxa de entrega por distância

**Enunciado:** Criar um `ComTaxaEntrega` que calcula a taxa com base na distância em km.

**Solução:** já implementada em `MainDecorator.java`:

```java
class ComTaxaEntrega extends CalculadorDecorator {
    private final double taxaPorKm;
    private final double distanciaKm;
    private final String descricaoRota;

    ComTaxaEntrega(CalculadorValorPedido base,
                   double taxaPorKm, double distanciaKm, String descricaoRota) {
        super(base);
        this.taxaPorKm      = taxaPorKm;
        this.distanciaKm    = distanciaKm;
        this.descricaoRota  = descricaoRota;
    }

    @Override
    public double calcular(double subtotal, int qtdItens) {
        double taxaEntrega = taxaPorKm * distanciaKm;
        return wrapped.calcular(subtotal, qtdItens) + taxaEntrega;
    }

    @Override
    public String descricao() {
        return wrapped.descricao()
            + String.format(" + entrega %s (%.0fkm x R$%.2f)", descricaoRota, distanciaKm, taxaPorKm);
    }
}
```

**Uso:**
```java
double subtotal = 80.00;
CalculadorValorPedido calc = new ComTaxaEntrega(
    new CalculadorBase(),
    2.50,   // R$ 2,50 por km
    8.0,    // 8 km de distancia
    "Bairro Aldeota"
);
double total = calc.calcular(subtotal, 5);
// taxa = 2.50 * 8.0 = R$ 20,00  →  total = R$ 100,00
System.out.printf("Total: R$%.2f | %s%n", total, calc.descricao());
```

**Por que Decorator aqui?**
- A lógica de entrega é *adicional* ao cálculo base — não precisa de herança.
- A taxa pode ser combinada com `ComDesconto` ou `ComTaxaEmbalagem` livremente.

---

## Exercício 2 — Dois descontos em ordem diferente — comparar resultado

**Enunciado:** Aplicar `ComTaxaEmbalagem` seguida de `ComDesconto` vs. `ComDesconto` seguida de `ComTaxaEmbalagem`. Os totais devem ser diferentes.

**Solução:** demonstrada em `MainDecorator.java`. Reprodução do raciocínio:

```java
double sub    = 100.00;
int    qtd    = 5;
double pct    = 0.10;   // 10% de desconto
double emb    = 5.00;   // R$ 5 de embalagem

// Ordem A: embalagem ANTES do desconto
// base(100) → +embalagem(5) = 105 → -10%(105) = 94,50
CalculadorValorPedido ordem_A = new ComDesconto(
    new ComTaxaEmbalagem(new CalculadorBase(), emb),
    pct
);

// Ordem B: desconto ANTES da embalagem
// base(100) → -10%(100) = 90 → +embalagem(5) = 95,00
CalculadorValorPedido ordem_B = new ComTaxaEmbalagem(
    new ComDesconto(new CalculadorBase(), pct),
    emb
);

System.out.printf("Ordem A (emb → desc): R$%.2f%n", ordem_A.calcular(sub, qtd));
System.out.printf("Ordem B (desc → emb): R$%.2f%n", ordem_B.calcular(sub, qtd));
// Saída:
// Ordem A (emb → desc): R$94,50
// Ordem B (desc → emb): R$95,00
```

**Conclusão prática:** ao montar a cadeia de decoradores, a ordem importa quando combinamos taxas aditivas com descontos percentuais. Documente a ordem esperada ou defina uma ordem padrão na classe Diretor/Service.

---

## Exercício 3 — Teste validando composição de decoradores

**Enunciado:** Escrever um teste que verifica os valores calculados para diferentes composições.

```java
// Salvar como TesteDecorator.java
// javac MainDecorator.java TesteDecorator.java && java TesteDecorator

public class TesteDecorator {

    static int ok   = 0;
    static int erro = 0;

    public static void main(String[] args) {
        System.out.println("=== Testes: Decorator ===");

        double sub = 100.00;
        int    qtd = 5;

        // Teste 1: base sem decoradores
        testar(new CalculadorBase(), sub, qtd, 100.00, "Apenas base");

        // Teste 2: embalagem R$5
        testar(new ComTaxaEmbalagem(new CalculadorBase(), 5.00), sub, qtd, 105.00, "Base + embalagem");

        // Teste 3: desconto 10%
        testar(new ComDesconto(new CalculadorBase(), 0.10), sub, qtd, 90.00, "Base + desconto 10%");

        // Teste 4: embalagem + desconto (sobre base+emb)
        testar(
            new ComDesconto(new ComTaxaEmbalagem(new CalculadorBase(), 5.00), 0.10),
            sub, qtd, 94.50, "Base + embalagem + desconto sobre (base+emb)"
        );

        // Teste 5: desconto + embalagem (sobre base descontada)
        testar(
            new ComTaxaEmbalagem(new ComDesconto(new CalculadorBase(), 0.10), 5.00),
            sub, qtd, 95.00, "Base + desconto + embalagem sobre valor descontado"
        );

        System.out.println("\nResultado: " + ok + " OK, " + erro + " FALHA(S)");
    }

    static void testar(CalculadorValorPedido c, double sub, int qtd, double esperado, String desc) {
        double obtido = c.calcular(sub, qtd);
        if (Math.abs(obtido - esperado) < 0.01) {
            System.out.printf("  [OK] %-50s => R$%.2f%n", desc, obtido);
            ok++;
        } else {
            System.out.printf("  [FALHA] %-50s => esperado=R$%.2f obtido=R$%.2f%n",
                desc, esperado, obtido);
            erro++;
        }
    }
}
```

**Como executar:**
```
javac MainDecorator.java TesteDecorator.java
java TesteDecorator
```

---

## Resumo dos conceitos

| Exercício | Conceito reforçado |
|---|---|
| 1 | Decorator adicionando comportamento sem subclassing |
| 2 | Ordem da cadeia importa — composição não é comutativa |
| 3 | Testabilidade — cada composição pode ser verificada isoladamente |
