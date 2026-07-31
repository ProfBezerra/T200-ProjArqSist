# Resolução dos Exercícios — Factory Method

> Referência da apostila: [APOSTILA.md](APOSTILA.md)
> Código completo executável: [MainFactoryMethod.java](MainFactoryMethod.java)

---

## Exercício 1 — Criar `LaticinioFactory` com validação específica

**Enunciado:** Criar uma fábrica para produtos do tipo LATICIONIO com validação própria.

**Solução:** a classe já está implementada em `MainFactoryMethod.java`. Veja o trecho abaixo:

```java
class LaticinioFactory implements ProdutoFactory {
    @Override
    public Produto criar(String nome, double preco) {
        if (preco <= 0)
            throw new IllegalArgumentException("Preco invalido para laticinios");
        if (preco > 200)
            throw new IllegalArgumentException("Preco de laticionio suspeito: R$" + preco);
        return new Produto(nome, preco, "LATICIONIO");
    }
}
```

**Raciocínio:**
- Cada fábrica encapsula suas próprias regras de validação.
- `HortifrutiFactory` valida preço. `GraosFactory` valida nome. `LaticinioFactory` valida preço e sanidade do valor.
- Adicionar `LaticinioFactory` **não exigiu alterar** `CadastroProdutoService` — princípio OCP em ação.

---

## Exercício 2 — Refatorar `if/else` para Factory Method

**Enunciado:** Dado o código abaixo (antes da refatoração), aplicar o padrão Factory Method.

### Código ANTES (sem o padrão)

```java
// ❌ Problema: adicionar novo tipo exige editar CadastroProdutoService
class CadastroProdutoServiceSemPadrao {

    Produto cadastrar(String tipo, String nome, double preco) {
        Produto produto;
        if (tipo.equals("HORTIFRUTI")) {
            if (preco <= 0) throw new IllegalArgumentException("Preco invalido");
            produto = new Produto(nome, preco, "HORTIFRUTI");
        } else if (tipo.equals("GRAOS")) {
            if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome obrigatorio");
            produto = new Produto(nome, preco, "GRAOS");
        } else if (tipo.equals("LATICIONIO")) {
            produto = new Produto(nome, preco, "LATICIONIO");
        } else {
            throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
        }
        System.out.println("Cadastrado: " + produto);
        return produto;
    }
}
```

### Código DEPOIS (com Factory Method)

```java
// ✅ Solução: cada tipo tem sua própria fábrica; CadastroProdutoService não muda

interface ProdutoFactory {
    Produto criar(String nome, double preco);
}

class HortifrutiFactory implements ProdutoFactory {
    @Override
    public Produto criar(String nome, double preco) {
        if (preco <= 0) throw new IllegalArgumentException("Preco invalido para hortifruti");
        return new Produto(nome, preco, "HORTIFRUTI");
    }
}

class GraosFactory implements ProdutoFactory {
    @Override
    public Produto criar(String nome, double preco) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome obrigatorio para graos");
        return new Produto(nome, preco, "GRAOS");
    }
}

class LaticinioFactory implements ProdutoFactory {
    @Override
    public Produto criar(String nome, double preco) {
        if (preco <= 0) throw new IllegalArgumentException("Preco invalido para laticinios");
        return new Produto(nome, preco, "LATICIONIO");
    }
}

class CadastroProdutoService {
    private final ProdutoFactory factory;

    CadastroProdutoService(ProdutoFactory factory) { this.factory = factory; }

    Produto cadastrar(String nome, double preco) {
        Produto p = factory.criar(nome, preco);
        System.out.println("Cadastrado: " + p);
        return p;
    }
}
```

**Benefício:** para adicionar `BebidaFactory`, basta criar a nova classe — `CadastroProdutoService` **não precisa ser tocada**.

---

## Exercício 3 — Teste para verificar que cada fábrica cria o tipo correto

**Enunciado:** Escrever um teste simples (sem JUnit) que garanta que cada fábrica produz produto com o tipo esperado.

```java
// Salvar como TesteFactoryMethod.java na mesma pasta e compilar junto:
// javac MainFactoryMethod.java TesteFactoryMethod.java && java TesteFactoryMethod

public class TesteFactoryMethod {

    static int ok  = 0;
    static int erro = 0;

    static void assertEqual(String esperado, String obtido, String descricao) {
        if (esperado.equals(obtido)) {
            System.out.println("  [OK] " + descricao);
            ok++;
        } else {
            System.out.println("  [FALHA] " + descricao + " => esperado='" + esperado + "' obtido='" + obtido + "'");
            erro++;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Testes: Factory Method ===");

        // Teste 1: HortifrutiFactory cria produto com tipo "HORTIFRUTI"
        Produto h = new HortifrutiFactory().criar("Tomate", 4.50);
        assertEqual("Hortifruti", tipoExibido(h), "HortifrutiFactory cria HORTIFRUTI");

        // Teste 2: GraosFactory cria produto com tipo "GRAOS"
        Produto g = new GraosFactory().criar("Feijao", 8.90);
        assertEqual("Graos", tipoExibido(g), "GraosFactory cria GRAOS");

        // Teste 3: LaticinioFactory cria produto com tipo "LATICIONIO"
        Produto l = new LaticinioFactory().criar("Queijo", 22.00);
        assertEqual("Laticionio", tipoExibido(l), "LaticinioFactory cria LATICIONIO");

        // Teste 4: preco invalido em HortifrutiFactory lanca excecao
        try {
            new HortifrutiFactory().criar("Invalido", -1.0);
            System.out.println("  [FALHA] Deveria lancar excecao para preco negativo");
            erro++;
        } catch (IllegalArgumentException e) {
            System.out.println("  [OK] HortifrutiFactory rejeita preco negativo");
            ok++;
        }

        // Teste 5: nome em branco em GraosFactory lanca excecao
        try {
            new GraosFactory().criar("", 5.00);
            System.out.println("  [FALHA] Deveria lancar excecao para nome vazio");
            erro++;
        } catch (IllegalArgumentException e) {
            System.out.println("  [OK] GraosFactory rejeita nome vazio");
            ok++;
        }

        System.out.println("\nResultado: " + ok + " OK, " + erro + " FALHA(S)");
    }

    // Extrai o tipo da representacao toString
    static String tipoExibido(Produto p) {
        String s = p.toString(); // "[TIPO] nome - R$ preco"
        return s.substring(1, s.indexOf(']'));
    }
}
```

**Como executar:**
```
javac MainFactoryMethod.java TesteFactoryMethod.java
java TesteFactoryMethod
```

---

## Resumo dos conceitos

| Exercício | Princípio reforçado |
|---|---|
| 1 | Encapsulamento da lógica de criação por variante |
| 2 | OCP — aberto para extensão, fechado para modificação |
| 3 | Testabilidade — cada fábrica é uma unidade independente |
