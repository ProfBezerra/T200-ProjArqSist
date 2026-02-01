# ADR-001 – Backend com Spring Boot (substituição de Node.js)

Status: Aceita
Data: 24/01/2026
Autor: Equipe T200

## Contexto
O planejamento anterior previa **backend em Node.js** (NestJS/Express). A disciplina quer **alinhar o conteúdo prático** com conceitos de **POA (AOP), SOLID, GRASP e Arquitetura de Software** usando recursos nativos do ecossistema Java.

## Decisão
Adotar **Spring Boot** como backend padrão do projeto integrador, mantendo **frontend SPA (Angular/Vue)**. O backend evoluirá de persistência **em memória** para **JPA/Hibernate** (H2 em dev; PostgreSQL/MySQL em produção/aula avançada).

## Justificativa
- **Aderência ao conteúdo da disciplina**: AOP/POA com `@Aspect`, Interceptors, Filters alinhados ao currículo.
- **Ecosistema maduro**: Spring Web, Validation, Security, Actuator, Springdoc OpenAPI.
- **Didática**: facilita demonstração de **camadas** (Controller/Service/Repository/Domain) e princípios **SOLID**/**GRASP**.
- **Continuidade**: turma já trabalha OO/Java; reduz curva de aprendizagem.

## Alternativas Consideradas
- Node.js (NestJS/Express):
  - Pró: popular, rápido para protótipos, TypeScript.
  - Contra: AOP/POA menos nativo; exigiria mudança de base da linguagem.
- Quarkus/Micronaut:
  - Pró: leves e modernos.
  - Contra: menor ubiquidade/recursos prontos para ensino básico.

## Consequências
- Ajuste dos materiais (planejamento, slides e exemplos) para Spring.
- Alunos praticarão AOP, Validation e Security no ecossistema Java.
- Frontend permanece SPA consumindo REST.

## Riscos e Mitigações
- **Risco**: ambiente Java/Spring não configurado em máquinas dos alunos.
  - **Mitigação**: guia de setup (JDK + Maven), uso de H2 em dev, projeto base com Spring Initializr.
- **Risco**: tempo de build/execução mais alto que Node.
  - **Mitigação**: foco em monolito modular leve inicialmente; evitar complexidade desnecessária.

## Evidências e Referências
- Spring Initializr – https://start.spring.io
- Spring Docs (Web/Security/Validation/AOP) – https://spring.io/projects/spring-boot
- Springdoc OpenAPI – https://springdoc.org/
