# Roteiro de Correção - Atividade SOLID (Feira Livre)

## Escala de pontuação

- 0 pontos: não aplicou o princípio ou solução incorreta.
- 1 ponto: aplicou parcialmente, com inconsistências.
- 2 pontos: aplicou corretamente, com solução clara e extensível.

Pontuação total sugerida: **10 pontos** (5 princípios x 2 pontos).

---

## Rubrica por princípio

### 1) SRP (Single Responsibility Principle) - até 2 pontos

**Verificar:**

- Se a classe central foi dividida em classes/serviços com responsabilidades únicas.
- Se cálculo de desconto, pagamento, persistência, impressão, notificação e relatório estão separados.

**Pontuação:**

- **0**: manteve classe "god class".
- **1**: separou parte das responsabilidades, mas ainda há acúmulo relevante.
- **2**: separação clara e coesa de responsabilidades.

### 2) OCP (Open/Closed Principle) - até 2 pontos

**Verificar:**

- Se removeu if/else central para descontos.
- Se removeu if/else central para tipos de pagamento.
- Se é possível adicionar novo desconto/pagamento sem editar classes principais.

**Pontuação:**

- **0**: if/else central permanece para novos casos.
- **1**: melhorou apenas um dos fluxos (desconto ou pagamento).
- **2**: ambos fluxos extensíveis por abstração/estratégia.

### 3) LSP (Liskov Substitution Principle) - até 2 pontos

**Verificar:**

- Se subtipos podem substituir o tipo base sem quebrar comportamento esperado.
- Se eliminaram exceções por limitação artificial do subtipo em cenários válidos.

**Pontuação:**

- **0**: ainda quebra substituição.
- **1**: reduziu a quebra, mas ainda há comportamento inconsistente.
- **2**: substituição segura e consistente para todos os subtipos.

### 4) ISP (Interface Segregation Principle) - até 2 pontos

**Verificar:**

- Se interfaces grandes foram segregadas em contratos menores.
- Se implementações não possuem métodos inúteis ou UnsupportedOperationException por design.

**Pontuação:**

- **0**: interface “inchada” permanece.
- **1**: separou parcialmente, ainda com métodos desnecessários.
- **2**: interfaces pequenas e específicas por necessidade real.

### 5) DIP (Dependency Inversion Principle) - até 2 pontos

**Verificar:**

- Se classes de alto nível dependem de abstrações.
- Se dependências concretas são recebidas por construtor (injeção).
- Se a orquestração não instancia diretamente detalhes de infraestrutura.

**Pontuação:**

- **0**: alto nível continua acoplado a classes concretas.
- **1**: parte das dependências foi abstraída.
- **2**: dependência consistente de abstrações com injeção por construtor.

---

## Checklist rápido de correção

- Compila e executa sem erro.
- Não há UnsupportedOperationException causada por mau design.
- Fluxo de finalização do pedido continua funcionando.
- Código ficou mais legível e testável.

---

## Sugestão de feedback por faixa

- **0 a 4 pontos:** refatoração insuficiente; revisar fundamentos de SOLID.
- **5 a 7 pontos:** boa direção, mas com lacunas de modelagem.
- **8 a 10 pontos:** aplicação sólida dos princípios e desenho consistente.
