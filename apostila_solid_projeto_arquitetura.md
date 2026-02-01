# Apostila – Princípios SOLID

**Disciplina:** Projeto e Arquitetura de Sistemas  
**Objetivo:** Apresentar e consolidar os cinco princípios SOLID como base para o desenvolvimento de software orientado a objetos de alta qualidade, com baixo acoplamento e alta coesão.

---

## 1. Introdução ao SOLID

SOLID é um acrônimo para cinco princípios de design orientado a objetos propostos por Robert C. Martin (Uncle Bob). Esses princípios visam tornar os sistemas:

- mais fáceis de manter
- mais fáceis de entender
- mais fáceis de evoluir
- menos propensos a erros

Os princípios SOLID não são regras rígidas, mas **diretrizes arquiteturais**.

---

## 2. S – Single Responsibility Principle (SRP)

### Definição

> Uma classe deve ter **um, e apenas um, motivo para mudar**.

Ou seja, uma classe deve ter **uma única responsabilidade bem definida**.

### Exemplo ruim

```java
public class Pedido {
    public void calcularTotal() {}
    public void salvarNoBanco() {}
}
```

A classe mistura regra de negócio com persistência.

### Exemplo bom

```java
public class Pedido {
    public double calcularTotal() { return 0; }
}

public class PedidoRepository {
    public void salvar(Pedido pedido) {}
}
```

### Benefícios
- Alta coesão
- Código mais legível
- Facilita testes

---

## 3. O – Open/Closed Principle (OCP)

### Definição

> Entidades de software devem estar **abertas para extensão**, mas **fechadas para modificação**.

### Exemplo conceitual

Errado: sempre modificar a classe para novos comportamentos.

Correto: usar herança ou composição.

```java
public interface Desconto {
    double calcular(double valor);
}

public class DescontoNatal implements Desconto {
    public double calcular(double valor) { return valor * 0.9; }
}
```

### Benefícios
- Menos impacto em código existente
- Facilita evolução

---

## 4. L – Liskov Substitution Principle (LSP)

### Definição

> Subtipos devem poder substituir seus tipos base sem quebrar o sistema.

### Exemplo conceitual

Se `ProdutoOrganico` herda de `Produto`, ele deve se comportar como um Produto.

Violação comum:
- Subclasse lança exceções inesperadas
- Subclasse muda contrato

### Benefícios
- Herança correta
- Polimorfismo seguro

---

## 5. I – Interface Segregation Principle (ISP)

### Definição

> Uma classe não deve ser forçada a depender de métodos que não utiliza.

### Exemplo ruim

```java
public interface Sistema {
    void vender();
    void gerarRelatorio();
}
```

### Exemplo bom

```java
public interface Venda {
    void vender();
}

public interface Relatorio {
    void gerarRelatorio();
}
```

### Benefícios
- Interfaces menores
- Menor acoplamento

---

## 6. D – Dependency Inversion Principle (DIP)

### Definição

> Dependa de abstrações, não de implementações concretas.

### Exemplo ruim

```java
public class PedidoService {
    private MySQLRepository repo;
}
```

### Exemplo bom

```java
public class PedidoService {
    private PedidoRepository repo;
}
```

### Benefícios
- Baixo acoplamento
- Facilita testes
- Permite trocar tecnologias

---

## 7. SOLID e Arquitetura de Software

Os princípios SOLID são a base para:

- Arquitetura em camadas
- Clean Architecture
- Hexagonal Architecture
- Microservices

Sem SOLID, arquiteturas se tornam rígidas e frágeis.

---

## 8. Erros comuns ao aplicar SOLID

- Criar abstrações desnecessárias
- Usar muitos padrões sem necessidade
- Confundir simplicidade com falta de design

SOLID deve ser aplicado com **bom senso**.

---

## 9. Estudo de Caso – Feira Livre

Aplicando SOLID no sistema da feira:

- SRP: `Pedido` não salva no banco
- OCP: novos tipos de desconto
- LSP: produtos respeitam contratos
- ISP: interfaces específicas
- DIP: serviços dependem de interfaces

---

## 10. Quiz de Revisão – SOLID

### Questão 1
O que significa SRP?

A) Uma classe deve ser pequena.
B) Uma classe deve ter uma única responsabilidade.
C) Uma classe deve usar herança.
D) Uma classe deve ter muitos métodos.

---

### Questão 2
Qual princípio indica que devemos estender sem modificar?

A) SRP
B) OCP
C) LSP
D) ISP

---

### Questão 3
Qual princípio garante herança segura?

A) OCP
B) ISP
C) DIP
D) LSP

---

### Questão 4
Qual princípio fala sobre depender de abstrações?

A) SRP
B) ISP
C) DIP
D) LSP

---

### Questão 5
Por que SOLID é importante?

A) Para melhorar performance.
B) Para reduzir linhas de código.
C) Para melhorar qualidade arquitetural.
D) Para eliminar testes.

---

## 11. Gabarito do Quiz

1. B
2. B
3. D
4. C
5. C

---

## 12. Conclusão

SOLID não é uma técnica de programação, mas uma **forma de pensar software**.

Eles permitem criar sistemas:
- flexíveis
- testáveis
- evolutivos
- arquiteturalmente saudáveis.

São um dos pilares fundamentais da Engenharia de Software moderna.

