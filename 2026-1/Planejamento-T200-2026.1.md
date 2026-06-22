
## Planejamento do Próximo Semestre – T200 Projeto de Arquitetura de Sistemas

**Baseado no Projeto de Ensino 2025.1**

### Premissas adotadas

* Carga horária total aproximada: **72 h/a**
* Organização em **18 semanas** (4 h/a por semana)
* Metodologia ativa com **projeto integrador contínuo**
* Avaliação concentrada em  **entrega final (AV3)** , com checkpoints formativos

---

## Estrutura Geral do Semestre

* **Unidade I – Desenho de Projetos de Sistemas** : Semanas 1 a 6 (24 h/a)
* **Unidade II – Programação Orientada a Aspectos (POA)** : Semanas 7 a 9 (12 h/a)
* **Unidade III – Arquitetura de Software e Padrões Arquiteturais** : Semanas 10 a 14 (20 h/a)
* **Unidade IV – Projeto de Arquitetura de Software** : Semanas 15 a 18 (16 h/a)

---

## Planejamento Semana a Semana

### **Unidade I – Desenho de Projetos de Sistemas**

**Semana 1**

* Apresentação da disciplina e do projeto integrador
* Revisão de OO e papel do arquiteto de software
* Introdução ao SOLID

**Semana 2**

* SOLID em profundidade (SRP, OCP, LSP)
* Estudos de caso e refatoração guiada

**Semana 3**

* SOLID (ISP, DIP)
* Discussão crítica de más decisões de projeto

**Semana 4**

* GRASP: conceitos e padrões principais
* Aplicação dos GRASP no projeto integrador

**Semana 5**

* Design Patterns: criacionais e estruturais
* Exemplos práticos e mapeamento no projeto

**Semana 6**

* Design Patterns comportamentais
* Avaliação crítica de padrões (quando usar / não usar)

📌  *Checkpoint 1* : Documento de projeto OO (classes + justificativas SOLID/GRASP)

---

### **Unidade II – Programação Orientada a Aspectos (POA)**

**Semana 7**

* Limitações da OO tradicional
* Interesses transversais, espalhamento e entrelaçamento

**Semana 8**

* Conceitos de POA: aspectos, pointcuts e advices
* Exemplos práticos (ex.: logging, segurança, auditoria)

**Semana 9**

* Evolução do projeto integrador com POA
* Discussão crítica: quando POA é realmente necessária

📌  *Checkpoint 2* : Implementação de pelo menos um aspecto no projeto

---

### **Unidade III – Arquitetura de Software e Padrões Arquiteturais**

**Semana 10**

* Conceitos de arquitetura de software
* Visões arquiteturais e trade-offs

**Semana 11**

* Padrões arquiteturais clássicos
* Análise de cenários

**Semana 12**

* Arquitetura monolítica vs microsserviços
* Vantagens, riscos e critérios de decisão

**Semana 13**

* Migração de sistemas legados para serviços
* Estratégias de decomposição

**Semana 14**

* Serverless Computing (FaaS)
* Discussão comparativa com microsserviços

📌  *Checkpoint 3* : Documento de arquitetura (diagramas + justificativas)

---

### **Unidade IV – Elaboração do Projeto de Arquitetura de Software**

**Semana 15**

* Análise detalhada do cenário-problema
* Escolha dos padrões arquiteturais

**Semana 16**

* Desenho completo da arquitetura
* Aplicação integrada de SOLID, padrões e POA

**Semana 17**

* Implementação e ajustes finais do projeto
* Preparação da apresentação

**Semana 18**

* Apresentação do Projeto Final (AV3)
* Avaliação e reflexão crítica

---

## Estratégia de Avaliação

* **AV3 – Projeto Final de Arquitetura de Software**
  * Documento arquitetural completo
  * Código/protótipo funcional
  * Justificativas técnicas das decisões

---

## Proposta de Projeto Integrador (Spring Boot + Angular ou Vue)

### Visão Geral do Projeto

Desenvolver um **sistema web completo** utilizando:

* **Backend** : Spring Boot (Spring Web, Validation; opcional: Spring Security, Actuator)
* **Frontend** : Angular *ou* Vue.js
* **Arquitetura** : camadas bem definidas, com possibilidade de evolução para microsserviços

O projeto servirá como  **fio condutor de todas as unidades** , evoluindo conforme novos conceitos arquiteturais são introduzidos.

---

### Tema do Sistema (definido)

**Sistema de Feira Livre Digital**

Uma plataforma web para apoiar a gestão e operação de uma  **feira livre** , conectando  **feirantes, clientes e administração** , com foco em organização, transparência e escalabilidade.

 **Atores principais** :

* Administrador da feira
* Feirante (vendedor)
* Cliente/Consumidor

---

### Arquitetura Geral

**Visão de Alto Nível**

* Aplicação web com frontend SPA e backend Spring Boot
* Arquitetura monolítica modular com possibilidade de evolução para microsserviços
* Comunicação via API REST

**Frontend (Angular ou Vue)**

* Arquitetura baseada em componentes
* Separação clara entre:
  * Views / Pages
  * Componentes reutilizáveis
  * Services (comunicação com API)
* Consumo de API REST

**Backend (Spring Boot)**

* Arquitetura em camadas:
  * Controllers (REST)
  * Services (regras de negócio)
  * Repositories (contratos; memória inicialmente, JPA/Hibernate futuramente)
  * Domain / Entities
* Aplicação rigorosa de **SOLID** e **GRASP**
* Validações com Bean Validation (`@Valid`, `@NotBlank`, `@Positive`)

---

### Domínio do Sistema – Feira Livre

 **Principais módulos de domínio** :

* **Usuários** (admin, feirantes, clientes)
* **Bancas** (localização, feirante responsável)
* **Produtos** (categoria, preço, disponibilidade)
* **Pedidos/Compras**
* **Pagamentos** (simulados)
* **Avaliações e comentários**
* **Relatórios da feira**

---

### Mapeamento do Projeto às Unidades da Disciplina

**Unidade I – Projeto OO e Padrões**

* Modelagem de domínio (UML)
* Aplicação de SOLID no backend
* Uso de padrões como:
  * Factory
  * Strategy
  * Repository
  * Singleton (com criticidade)

**Unidade II – Programação Orientada a Aspectos (POA)**

* Implementação de interesses transversais no backend Spring:
  * Logging (Filters/Interceptors ou AOP)
  * Autenticação (Spring Security)
  * Auditoria (AOP com `@Aspect`)
  * Tratamento de erros (ControllerAdvice / ExceptionHandler)
* Uso de Interceptors/AOP como mecanismo de aspectos

**Unidade III – Arquitetura de Software**

* Arquitetura monolítica modular inicialmente
* Análise de decomposição em microsserviços
* Discussão sobre:
  * Comunicação síncrona (REST)
  * Comunicação assíncrona (eventos)
  * Possível uso de Serverless (funções específicas)

**Unidade IV – Projeto Final de Arquitetura**

* Desenho arquitetural completo (C4 Model ou UML)
* Justificativa das decisões técnicas
* Protótipo funcional integrado (frontend + backend)

---

### Interesses Transversais (POA)

Implementados principalmente no **backend Spring Boot** por meio de **Filters/Interceptors e AOP**:

* Logging de requisições e erros
* Autenticação e autorização (Spring Security + JWT)
* Auditoria de ações (criação/edição de produtos e pedidos) via AOP
* Tratamento global de exceções (ControllerAdvice)

---

### Tecnologias de Apoio (opcionais)

* Banco de dados: PostgreSQL ou MySQL (H2 para desenvolvimento)
* ORM: JPA/Hibernate
* Autenticação: Spring Security + JWT
* Documentação da API: Springdoc OpenAPI (Swagger UI)

---

## Metodologias e Estratégias Didáticas

* Aprendizagem baseada em projeto (PBL)
* Estudos de caso reais
* Discussões orientadas por trade-offs arquiteturais
* Revisões coletivas de projeto

---

Se quiser, posso **adaptar este planejamento** para:

* semestre reduzido (CCE mais curto),
* número específico de semanas/aulas,
* perfil de turma (mais prática ou mais teórica),
* ou já transformar isso em  **plano de ensino oficial** .
