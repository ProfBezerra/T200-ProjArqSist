# Universidade de Fortaleza - Unifor

# T200 - Projeto de Arquitetura de Sistemas

## Planejamento da Disciplina – 2026.2

**Professor:** Marcelo Alcântara

**Projeto Integrador:** Missão Marte Unifor

---

# Visão Geral

A disciplina será conduzida através da evolução incremental de um único projeto de software: o jogo  **Missão Marte Unifor** .

Durante o semestre, os alunos desenvolverão uma aplicação que inicia como um jogo de console em Java e evolui para uma aplicação baseada em arquitetura de software moderna, utilizando:

* Java
* Maven
* UML
* SOLID
* GRASP
* Design Patterns
* Programação Orientada a Aspectos
* Arquitetura em Camadas
* libGDX
* Android
* Spring Boot

---

# História do Projeto

No ano de 2085, a Unifor inicia seu programa de expansão espacial.

Uma base avançada foi construída na Lua para servir de apoio à implantação do primeiro Campus Marte Unifor.

O jogador assume o papel de um piloto responsável pelo transporte de:

* Professores
* Engenheiros
* Pesquisadores
* Equipamentos
* Suprimentos

Seu objetivo é garantir a chegada segura das equipes ao novo campus, enfrentando diversos desafios durante a viagem.

---

# Competências Desenvolvidas

Ao final da disciplina o aluno deverá ser capaz de:

* Aplicar princípios SOLID.
* Aplicar padrões GRASP.
* Utilizar Design Patterns.
* Projetar arquiteturas de software.
* Avaliar padrões arquiteturais.
* Desenvolver sistemas orientados a objetos.
* Utilizar Programação Orientada a Aspectos.
* Construir APIs REST.
* Estruturar aplicações em camadas.
* Evoluir sistemas legados.

---

# Cronograma da Disciplina

## Unidade I – Orientação a Objetos e Projeto de Sistemas

### Semana 1 – Apresentação do Projeto

Conteúdo:

* Apresentação da disciplina.
* Apresentação do jogo.
* Formação das equipes.
* Introdução ao problema.

Entrega:

* Documento de Visão.

---

### Semana 2 – Modelagem do Domínio

Conteúdo:

* Classes
* Objetos
* Associações
* Composição

Artefatos:

* Diagrama de Classes inicial.

Classes iniciais:

* Nave
* Piloto
* Professor
* Engenheiro
* Asteroide
* Missao

---

### Semana 3 – Herança e Polimorfismo

Conteúdo:

* Generalização
* Especialização
* Polimorfismo

Entrega:

* Modelo de domínio refinado.

---

### Semana 4 – Desenvolvimento da Fase 1

Conteúdo:

* Implementação do jogo em console.

Funcionalidades:

* Movimentação
* Pontuação
* Coleta de passageiros

---

# Unidade II – SOLID e GRASP

### Semana 5 – SOLID

Conteúdo:

* SRP
* OCP
* LSP
* ISP
* DIP

Atividade:

Refatoração do código desenvolvido.

---

### Semana 6 – GRASP

Conteúdo:

* Controller
* Creator
* Information Expert
* Low Coupling
* High Cohesion

Entrega:

* Documento justificando as responsabilidades.

---

### Semana 7 – Avaliação Arquitetural

Conteúdo:

* Análise crítica do projeto.
* Identificação de melhorias.

Entrega:

* Relatório arquitetural.

---

# Unidade III – Design Patterns

### Semana 8 – Padrões Criacionais

Implementação:

* Factory Method
* Abstract Factory
* Builder

Aplicação no jogo:

* Criação de entidades.
* Criação de fases.
* Construção de naves.

---

### Semana 9 – Padrões Estruturais

Implementação:

* Facade
* Adapter
* Proxy

Aplicação no jogo:

* Inicialização do jogo.
* Integração externa.
* Comunicação remota.

---

### Semana 10 – Padrões Comportamentais

Implementação:

* Strategy
* State
* Command

Aplicação:

* Movimento de inimigos.
* Estados do jogo.
* Controle da nave.

---

### Semana 11 – Seminário de Padrões

Cada equipe apresentará um padrão adicional.

Exemplos:

* Decorator
* Composite
* Flyweight
* Prototype
* Singleton
* Chain of Responsibility
* Mediator
* Template Method
* Visitor
* Memento

Entrega:

* Apresentação
* UML
* Código implementado

---

# Unidade IV – Programação Orientada a Aspectos

### Semana 12 – Introdução à POA

Conteúdo:

* Interesses transversais
* Entrelaçamento
* Espalhamento

---

### Semana 13 – AspectJ

Conteúdo:

* Aspectos
* Pointcuts
* Advice

Aplicação:

* Logs
* Auditoria
* Medição de desempenho

---

### Semana 14 – Evolução do Projeto

Aplicação de aspectos ao jogo.

Exemplos:

* Registro de eventos
* Controle de ranking
* Monitoramento de colisões

---

# Unidade V – Arquitetura de Software

### Semana 15 – Arquitetura em Camadas

Conteúdo:

* Domain
* Application
* Infrastructure
* Presentation

Refatoração do projeto.

---

### Semana 16 – Padrões Arquiteturais

Conteúdo:

* Monolito
* Microsserviços
* Serverless

Discussão:

Onde cada padrão se encaixaria no projeto.

---

# Unidade VI – Evolução para Engine de Jogos

### Semana 17 – libGDX

Conteúdo:

* Game Loop
* Sprites
* Input
* Colisões

Objetivo:

Migrar a interface de console para interface gráfica.

---

### Semana 18 – Android

Conteúdo:

* Deploy Android
* Touch
* Sensores

Entrega:

Primeira versão móvel do jogo.

---

# Unidade VII – Backend

### Semana 19 – Spring Boot

Conteúdo:

* REST
* Controllers
* Services

Endpoints:

* Pilotos
* Missões
* Ranking

---

### Semana 20 – Integração Final

Integração:

Android ↔ Spring Boot

Entrega:

Versão Final do Projeto.

---

# Estrutura Final Esperada

missao-marte

├── domain

├── application

├── infrastructure

├── desktop

├── android

└── backend

---

# Avaliação

| Item                    | Peso |
| ----------------------- | ---: |
| Modelagem OO            |  15% |
| SOLID                   |  10% |
| GRASP                   |  10% |
| Design Patterns         |  20% |
| POA                     |  10% |
| Arquitetura de Software |  15% |
| Projeto Final           |  20% |

---

# Resultado Esperado

Ao final da disciplina cada equipe deverá entregar:

* Jogo funcional em Java.
* Aplicação estruturada em camadas.
* Aplicação de SOLID.
* Aplicação de GRASP.
* Implementação de 9 Design Patterns.
* Implementação de POA.
* Versão gráfica utilizando libGDX.
* Versão Android.
* Backend Spring Boot integrado ao jogo.
* Documentação UML e arquitetural.
