# Missão Marte Unifor 🚀

* Link do jogo: (a ser definido)

---

# Motivação

O projeto Missão Marte Unifor foi concebido como uma ferramenta de aprendizagem prática para apoiar o ensino de Arquitetura e Desenvolvimento de Sistemas.

Ao longo da disciplina, os alunos evoluirão um mesmo projeto desde uma aplicação simples em console até um jogo completo utilizando uma engine de jogos e integração com serviços web.

A proposta permite aplicar de forma prática conceitos de:

* Orientação a Objetos
* SOLID
* GRASP
* Padrões de Projeto
* Arquitetura em Camadas
* Desenvolvimento de Jogos
* APIs REST

Além disso, o projeto utiliza um contexto lúdico e próximo da realidade acadêmica da Unifor, aumentando o engajamento dos alunos durante o semestre.

---

# Objetivo

Desenvolver um jogo em que o jogador assume o papel de um piloto contratado pela Unifor para transportar professores, engenheiros e suprimentos para as futuras instalações da universidade fora da Terra.

O objetivo principal do jogador é concluir as missões espaciais com segurança, administrando recursos da nave, evitando obstáculos e garantindo a chegada dos passageiros aos seus destinos.

Paralelamente, o projeto tem como objetivo pedagógico permitir que os alunos pratiquem técnicas modernas de desenvolvimento de software por meio da evolução contínua do sistema.

---

# Valor Agregado

O principal diferencial do projeto é sua evolução incremental ao longo da disciplina.

O mesmo sistema será utilizado para demonstrar:

* Conceitos básicos de programação orientada a objetos;
* Refatoração utilizando SOLID;
* Distribuição de responsabilidades utilizando GRASP;
* Aplicação de padrões de projeto;
* Arquitetura em camadas;
* Desenvolvimento de jogos utilizando Java e libGDX;
* Integração com APIs REST utilizando Spring Boot.

Dessa forma, os alunos percebem como os conceitos estudados se complementam na construção de um produto real.

---

# Fases

O jogo é dividido em fases que representam a expansão espacial da Unifor.

| Fases | Descrição                                                                                                                                  |
| ----- | -------------------------------------------------------------------------------------------------------------------------------------------- |
| 1     | Terra → Base Lunar Unifor. O jogador deve transportar professores, engenheiros e suprimentos para a primeira instalação da Unifor na Lua. |
| 2     | Base Lunar Unifor. O jogador recebe uma nova nave mais rápida e resistente para continuar a missão.                                        |
| 3     | Lua → Marte. O jogador enfrenta asteroides maiores, tempestades solares e naves alienígenas durante a viagem até Marte.                   |
| 4     | Campus Marte Unifor. Entrega final dos passageiros e suprimentos necessários para a inauguração do novo campus da universidade.           |

---

# Esquema de Pontuação

| Evento                              | Pontos    |
| ----------------------------------- | --------- |
| Professor transportado com sucesso  | 100       |
| Engenheiro transportado com sucesso | 150       |
| Suprimento entregue                 | 50        |
| Asteroide evitado                   | 10        |
| Nave alienígena evitada            | 20        |
| Conclusão da Fase 1                | 500       |
| Conclusão da Fase 3                | 1000      |
| Perda de passageiro                 | -100      |
| Colisão com asteroide              | -50       |
| Destruição da nave                | Game Over |

---

# Requisitos

## Épicos/Funcionalidades

### Épico 1 – Gerenciamento da Missão

1. Iniciar uma nova missão.
2. Selecionar piloto.
3. Exibir status da nave.
4. Exibir progresso da missão.

### Épico 2 – Controle da Nave

5. Movimentar a nave utilizando teclado.
6. Controlar velocidade da nave.
7. Realizar manobras evasivas.

### Épico 3 – Transporte de Passageiros

8. Resgatar professores.
9. Resgatar engenheiros.
10. Transportar passageiros entre as bases espaciais.

### Épico 4 – Transporte de Suprimentos

11. Coletar suprimentos.
12. Entregar suprimentos em destinos específicos.

### Épico 5 – Obstáculos Espaciais

13. Detectar asteroides.
14. Detectar naves alienígenas.
15. Detectar tempestades solares.
16. Evitar colisões.

### Épico 6 – Evolução da Nave

17. Trocar de nave ao chegar à Base Lunar.
18. Melhorar atributos da nave.
19. Instalar equipamentos especiais.

### Épico 7 – Sistema de Pontuação

20. Calcular pontuação da missão.
21. Exibir ranking.
22. Registrar pontuação final.

### Épico 8 – Integração Online

23. Cadastrar pilotos.
24. Consultar ranking online.
25. Registrar resultados em API REST.

---

## Personas

### Piloto Espacial

Responsável por conduzir a nave durante toda a missão.

Características:

* Habilidade de navegação.
* Responsável pela segurança da tripulação.
* Deve tomar decisões estratégicas durante a viagem.

### Professor

Passageiro que será transportado para atuar no novo campus da Unifor.

### Engenheiro

Passageiro responsável pela construção e manutenção das instalações espaciais da universidade.

### Nave Alienígena

Entidade hostil que tenta impedir o avanço da missão.

### Asteroide

Obstáculo espacial que oferece risco de colisão.

### Sistema de Controle da Unifor

Responsável por monitorar o andamento da missão e registrar os resultados.

---

# Protótipos de Tela

## Tela Principal

```text
+------------------------------------------------+
|              MISSÃO MARTE UNIFOR               |
+------------------------------------------------+
| Pontos: 1250                                   |
| Vidas : 3                                      |
| Combustível: 80%                               |
+------------------------------------------------+
|                                                |
|        *                                       |
|                                                |
|                     A                          |
|                                                |
|               P                                |
|                                                |
|                       ^                        |
|                                                |
+------------------------------------------------+
| W - Cima | A - Esquerda | S - Baixo | D - Direita |
+------------------------------------------------+
```

Legenda:

```text
^ = Nave
* = Asteroide
A = Nave Alienígena
P = Professor
E = Engenheiro
S = Suprimento
```

---

# Tecnologias e Ferramentas Utilizadas

## Desenvolvimento Inicial

1. Java 21
2. Maven
3. Git
4. GitHub
5. JUnit

## Desenvolvimento do Jogo

6. libGDX
7. Box2D (opcional)

## Backend

8. Spring Boot
9. Spring Web
10. Spring Data JPA
11. PostgreSQL

## Arquitetura e Modelagem

12. UML
13. Draw.io
14. Mermaid

## Qualidade

15. SonarQube
16. Checkstyle

## Gerenciamento

17. GitHub Projects
18. Kanban

---

# Padrões de Projeto Planejados

## Criacionais

* Factory Method
* Abstract Factory
* Builder

## Estruturais

* Facade
* Adapter
* Proxy

## Comportamentais

* Strategy
* State
* Command

## Padrão Bônus

* Observer
