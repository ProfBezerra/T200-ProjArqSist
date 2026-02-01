## Estrutura de Slides – Parte Teórica da Disciplina

---

# UNIDADE I – DESENHO DE PROJETOS DE SISTEMAS

## Slide 1 – Abertura da Disciplina

**Conteúdo:**

* O que é Arquitetura de Software
* Diferença entre escrever código e projetar sistemas
* Visão geral do semestre

**Pergunta para discussão:**

* Por que sistemas “funcionando” ainda falham no longo prazo?

---

## Slide 1A – Revisão Rápida de OO

**Conteúdo:**

* Classes e objetos; estado (dados) e comportamento (métodos)
* Encapsulamento e contratos (interfaces)
* Herança vs. composição; polimorfismo
* Modificadores de acesso: public, private, protected
* Contratos com `interface` + `implements`; herança com `extends`

**Exemplo (Feira):**

* `Produto`, `Pedido`, `ItemPedido` e suas responsabilidades

**Mini exemplo (Java/Spring):**

```java
public interface Autenticavel {
		boolean autenticar(String senha);
}

public class Usuario implements Autenticavel {
		protected String nome;
		private String senha;
		public Usuario(String nome, String senha) {
				this.nome = nome;
				this.senha = senha;
		}
		public boolean autenticar(String senha) { return this.senha.equals(senha); }
}

public class Admin extends Usuario {
		public Admin(String nome, String senha) { super(nome, senha); }
		public void ativarFeira(String id) { /* ... */ }
}
```

**Exercício (3–5 min):**

* Identificar onde aplicar composição em vez de herança no domínio da feira

---

## Slide 1B – Coesão e Acoplamento em OO

**Conteúdo:**

* Coesão: foco claro de responsabilidade por classe
* Acoplamento: dependências necessárias e controladas
* Regra prática: evitar mais de 3 dependências diretas por componente

**Discussão:**

* Como coesão e acoplamento influenciam evolução e teste?

**Exercício (3–5 min):**

* Revisar um pseudo-código e apontar smells (classe Deus, dependências circulares)

---

## Slide 2 – Projeto de Software x Implementação

**Conteúdo:**

* Projeto: decisões estruturais
* Implementação: materialização das decisões
* Custo da mudança ao longo do tempo

**Exercício em sala:**

* Listar decisões de projeto que impactam manutenção

---

## Slide 3 – Problemas Clássicos em Projetos OO

**Conteúdo:**

* Classes Deus
* Acoplamento excessivo
* Efeitos colaterais

**Discussão:**

* Esses problemas surgem por falta de linguagem ou de projeto?

---

## Slide 4 – Introdução ao SOLID

**Conteúdo:**

* Origem dos princípios
* Relação com qualidade e evolução

---

## Slide 5 – SRP

**Conteúdo:**

* Uma razão para mudar
* Responsabilidade ≠ função

**Exemplo (Feira):**

* Classe Produto não deve calcular faturamento

---

## Slide 6 – OCP

**Conteúdo:**

* Extensão sem modificação
* Políticas de preço como variação

**Exercício:**

* Como adicionar um novo tipo de feira sem alterar código existente?

---

## Slide 7 – LSP

**Conteúdo:**

* Subtipos substituíveis
* Herança problemática

**Discussão:**

* Quando herança deve ser evitada?

---

## Slide 8 – ISP

**Conteúdo:**

* Interfaces coesas
* Papéis distintos no sistema

**Exemplo (Feira):**

* Admin ≠ Feirante ≠ Cliente

---

## Slide 9 – DIP

**Conteúdo:**

* Abstrações como contrato
* Inversão de dependência

---

## Slide 10 – SOLID Integrado

**Conteúdo:**

* Conflitos entre princípios
* Trade-offs reais

---

# GRASP – ATRIBUIÇÃO DE RESPONSABILIDADES

## Slide 11 – O Problema das Responsabilidades

**Conteúdo:**

* Onde colocar regras de negócio?

---

## Slide 12 – Visão Geral do GRASP

**Conteúdo:**

* GRASP como heurística

---

## Slide 13 – Information Expert

**Exemplo (Feira):**

* Pedido calcula seu próprio total

---

## Slide 14 – Creator

**Exemplo:**

* Pedido cria ItemPedido

---

## Slide 15 – Controller

**Conteúdo:**

* Orquestração de casos de uso

---

## Slide 16 – Low Coupling / High Cohesion

**Discussão:**

* Impacto no crescimento do sistema

---

## Slide 17 – Pure Fabrication

**Exemplo:**

* Serviço de faturamento da feira

---

# DESIGN PATTERNS

## Slide 18 – O que são Padrões de Projeto

**Conteúdo:**

* Soluções recorrentes

---

## Slide 19 – Classificação

* Criacionais
* Estruturais
* Comportamentais

---

## Slide 20 – Padrões Criacionais

**Exemplo:**

* Factory para criação de pedidos

---

## Slide 21 – Padrões Estruturais

**Exemplo:**

* Facade para acesso ao sistema da feira

---

## Slide 22 – Padrões Comportamentais

**Exemplo:**

* Strategy para políticas de preço

---

## Slide 23 – Quando NÃO Usar Padrões

**Conteúdo:**

* Complexidade desnecessária

---

# UNIDADE II – PROGRAMAÇÃO ORIENTADA A ASPECTOS

## Slide 24 – Limitações da OO

**Conteúdo:**

* Espalhamento
* Entrelaçamento

---

## Slide 25 – Interesses Transversais

**Exemplo (Feira):**

* Autenticação
* Auditoria

---

## Slide 26 – Conceitos de POA

**Conteúdo:**

* Aspecto
* Pointcut
* Advice

**Nota (Java/Spring):** usar Spring AOP com `@Aspect` e expressões de pointcut.

```java
@Aspect
@Component
class LogAspect {
	@Before("execution(* com.feira..service.*.*(..))")
	public void log() { /* ... */ }
}
```

---

## Slide 27 – Benefícios e Riscos

**Discussão:**

* POA reduz ou aumenta complexidade?

---

# UNIDADE III – ARQUITETURA DE SOFTWARE

## Slide 28 – O que é Arquitetura

**Conteúdo:**

* Decisões difíceis de mudar

---

## Slide 29 – Arquitetura x Design

**Conteúdo:**

* Escopo de decisão

---

## Slide 30 – Padrões Arquiteturais

* Camadas
* Hexagonal

---

## Slide 31 – Monolito Modular

**Exemplo (Feira local):**

* Simplicidade e controle

---

## Slide 32 – Microsserviços

**Discussão:**

* Feira pequena precisa disso?

---

## Slide 33 – Serverless

**Conteúdo:**

* Funções específicas

---

# UNIDADE IV – INTEGRAÇÃO CONCEITUAL

## Slide 34 – Integração dos Conceitos

* SOLID + GRASP + Patterns
* POA + Arquitetura

---

## Slide 35 – Arquitetura Conceitual da Feira

**Descrição:**

* Atores
* Módulos
* Fluxos principais

---

## Slide 36 – Trade-offs Arquiteturais

**Exercício:**

* Defender uma decisão arquitetural

---

## Slide 37 – Da Teoria ao Código

**Conteúdo:**

* Preparação para Java + Spring (exemplos)

---

## Slide 38 – Encerramento

* Principais aprendizados
* Próximos passos

---

# Bibliografia Sugerida por Unidade

**SOLID / GRASP / Patterns:**

* Larman – Utilizando UML e Padrões
* Gamma et al. – Design Patterns
* Freeman – Use a Cabeça! Padrões de Projeto

**Arquitetura:**

* Fowler – UML Essencial
* Candia – Arquitetura de Sistemas
* Pressman – Engenharia de Software

**Recursos complementares (OO/TypeScript):**

* Artigo – TypeScript: Programação Orientada a Objetos (POO), por Hugo Habbema: https://medium.com/@habbema/typescript-programa%C3%A7%C3%A3o-orientada-a-objetos-poo-c19e4cec08c3

**Recursos complementares (Java/Spring):**

* Spring Boot – Getting Started: https://spring.io/guides/gs/spring-boot/
* Spring AOP – Referência: https://docs.spring.io/spring-framework/reference/core/aop.html

---

# Exercícios Avaliativos Teóricos

* Análise crítica de projeto
* Justificativa de decisões arquiteturais
* Comparação de padrões

---

# Diagramas Conceituais (descrição)

* **Diagrama de Contexto (C4):** Feira, clientes, feirantes, admin
* **Diagrama de Containers:** Frontend, Backend, Banco de Dados
* **Diagrama de Componentes:** Módulos de domínio
