# Tutorial: API, Gradle e libGDX

## Desenvolvendo um Jogo de Nave Espacial

**Disciplina:** T200 – Projeto de Arquitetura de Sistemas
**Projeto:** Missão Marte Unifor
**Unidade VI – Semana 17**

---

## Objetivos de Aprendizagem

Ao final deste tutorial o aluno será capaz de:

- Explicar o conceito de **API** com exemplos concretos.
- Descrever a **história e evolução do Gradle**.
- Compreender os **conceitos fundamentais** do Gradle: tasks, DAG, daemon, cache e wrapper.
- Ler e escrever um arquivo **`build.gradle`** com plugins, repositórios e dependências.
- Distinguir os **escopos de dependência** (`implementation`, `api`, `runtimeOnly`).
- Descrever a **história e características da libGDX** (criador, versões, plataformas, módulos).
- Compreender o pipeline **Sprite** na libGDX: Pixmap → Texture → Sprite → SpriteBatch.
- Implementar **efeitos sonoros** usando a API `Sound` do libGDX.
- Construir um **HUD** com texto, barras de escudo e contadores.
- Implementar **detecção de colisão** círculo×círculo com `Intersector`.
- Compreender a **arquitetura multi-módulo** da libGDX (core + lwjgl3).
- Configurar um projeto Java multi-módulo com **Gradle** no VS Code.
- Criar um projeto **libGDX** e executá-lo no VS Code.
- Implementar um jogo de nave básico com movimentação, colisões e pontuação.

---

# Parte 1 – O que é uma API?

## 1.1 Definição

**API** significa _Application Programming Interface_ (Interface de Programação de Aplicação).

Uma API é um **conjunto de classes, interfaces e métodos** que outra pessoa já escreveu e que você pode usar no seu programa sem precisar saber como foi implementado internamente.

> Pense em uma API como o painel de controle de um carro.
> Você usa o volante, o acelerador e o freio **sem precisar entender** como o motor funciona por dentro.
> A API expõe o que você precisa e esconde a complexidade.

## 1.2 APIs que você já usou


| API                   | O que oferece                 |
| ----------------------- | ------------------------------- |
| `java.util.ArrayList` | Lista dinâmica de objetos    |
| `java.util.Scanner`   | Leitura de dados do console   |
| `java.io.File`        | Acesso ao sistema de arquivos |
| `javax.swing.*`       | Interface gráfica desktop    |
| **libGDX**            | Engine de jogos 2D/3D         |
| **Spring Boot**       | Backend REST em Java          |

## 1.3 Por que APIs existem?

Sem APIs, cada desenvolvedor precisaria escrever do zero:

- Como abrir uma janela gráfica.
- Como ler o teclado.
- Como desenhar um sprite na tela.
- Como detectar colisões.

Com a API **libGDX**, basta chamar:

```java
batch.draw(texturanave, x, y);
```

E a nave aparece na tela. Todo o trabalho de OpenGL, memória de vídeo e buffers gráficos já está feito dentro da API.

## 1.4 Analogia Arquitetural

```
┌─────────────────────────────────┐
│         SEU CÓDIGO              │  ← você escreve aqui
│  MissaoMarteGame, GameScreen    │
└─────────────┬───────────────────┘
              │ usa
┌─────────────▼───────────────────┐
│           libGDX API            │  ← API (você apenas usa)
│  SpriteBatch, Texture, Screen   │
└─────────────┬───────────────────┘
              │ usa
┌─────────────▼───────────────────┐
│      OpenGL / Sistema Operac.   │  ← implementação interna
└─────────────────────────────────┘
```

O seu código **depende** da libGDX. A libGDX **depende** do sistema operacional. Você não vê as camadas de baixo — só a interface que a API expõe.

---

# Parte 2 – Gradle: Gerenciamento de Build e Dependências

---

## 2.1 História e Origem do Gradle

### Quem criou?

O Gradle foi criado por **Hans Dockter** e **Adam Murdoch**, dois desenvolvedores que trabalhavam com Maven e estavam insatisfeitos com suas limitações — principalmente a rigidez da configuração em XML e a dificuldade de expressar lógica condicional no build.

Em **2007**, Hans Dockter começou a desenvolver o Gradle como um projeto pessoal. A ideia central era criar uma ferramenta de build que combinasse:

- O **modelo de dependências** do Maven (repositórios, artefatos)
- A **flexibilidade de scripting** do Ant (tarefas personalizáveis)
- Uma **linguagem de domínio (DSL)** expressiva, baseada em Groovy

Em **2008**, a primeira versão pública foi lançada como projeto open source.

Em **2011**, foi fundada a empresa **Gradle Inc.** para sustentar o desenvolvimento do projeto e oferecer suporte corporativo. Hoje a empresa mantém tanto o Gradle Build Tool (open source) quanto o **Gradle Enterprise** (produto comercial para grandes empresas).

### Repositório e licença


| Item             | Detalhe                          |
| ------------------ | ---------------------------------- |
| **Repositório** | https://github.com/gradle/gradle |
| **Licença**     | Apache License 2.0 (open source) |
| **Linguagem**    | Java, Groovy, Kotlin             |
| **Website**      | https://gradle.org               |
| **Empresa**      | Gradle Inc. (San Francisco, EUA) |

### Linha do tempo

```
2007  Hans Dockter inicia o desenvolvimento pessoal
2008  Primeira versão pública open source
2009  Gradle 0.7 – suporte a projetos multi-módulo
2010  Gradle 0.9 – integração com Maven Central
2011  Fundação da Gradle Inc.
2012  Gradle 1.0 – primeira versão estável
2013  Google adota Gradle como build system do Android
2014  Gradle 2.0
2016  Gradle 3.0 – composite builds
2017  Gradle 4.0 – nova API de Tasks (Provider/Property)
2018  Gradle 5.0 – Kotlin DSL estável, alinhamento de versões
2019  Gradle 6.0 – verificação de dependências, metadados
2021  Gradle 7.0 – compilação incremental, Java 16
2023  Gradle 8.0 – Configuration Cache estável
2024  Gradle 8.7 – melhorias de performance e toolchain Java
```

> **Curiosidade:** Em 2013, o Google escolheu o Gradle como o sistema de build padrão do Android Studio. Isso foi decisivo para tornar o Gradle a ferramenta dominante no ecossistema Java/Kotlin mobile, e consequentemente também para libGDX (que usa a mesma stack).

---

## 2.2 Versões do Gradle

O Gradle segue **versionamento semântico** (major.minor.patch). As versões major trazem mudanças significativas; as minor adicionam funcionalidades; as patch corrigem bugs.

### Versões Principais


| Versão  | Ano  | Destaques                                                                                  |
| ---------- | ------ | -------------------------------------------------------------------------------------------- |
| **1.0**  | 2012 | Primeira versão estável. Suporte a Java, Groovy, Scala.                                  |
| **2.0**  | 2014 | Daemon habilitado por padrão. Melhora de performance.                                     |
| **2.14** | 2016 | Build scan (diagnóstico visual do build).                                                 |
| **3.0**  | 2016 | Composite builds (projetos que dependem de outros projetos locais).                        |
| **4.0**  | 2017 | Nova API de Tasks com`Provider<T>` e `Property<T>` (lazy evaluation).                      |
| **4.6**  | 2018 | Suporte nativo a testes JUnit 5.                                                           |
| **5.0**  | 2018 | **Kotlin DSL estável.** Alinhamento de versões de dependências. Dependency constraints. |
| **6.0**  | 2019 | Verificação de checksum de dependências. Gradle Module Metadata.                        |
| **6.8**  | 2021 | Configuration cache (experimental). Suporte a Java 16.                                     |
| **7.0**  | 2021 | Compilação incremental do Java aprimorada. Suporte a Java 16.                            |
| **7.4**  | 2022 | Toolchain Java estável. Builds reproduzíveis.                                            |
| **8.0**  | 2023 | **Configuration Cache estável.** Isolamento de projetos.                                  |
| **8.4**  | 2023 | Gradle Develocity integrado.                                                               |
| **8.7**  | 2024 | Melhorias de performance. Build toolchain aprimorado.                                      |
| **8.10** | 2024 | Suporte aprimorado a Java 21+.                                                             |

### Como verificar a versão instalada

```bat
gradle --version
```

Ou com o wrapper do projeto:

```bat
.\gradlew.bat --version
```

Saída esperada:

```
------------------------------------------------------------
Gradle 8.7
------------------------------------------------------------

Build time:   2024-03-22 15:52:46 UTC
Revision:     ...

Kotlin:       1.9.22
Groovy:       3.0.21
Ant:          Apache Ant(TM) version 1.10.13
JVM:          17.0.x (Eclipse Adoptium 17.0.x+x)
OS:           Windows 11 10.0 amd64
```

### Qual versão usar?

Para este projeto usamos **Gradle 8.7**, que é compatível com:

- Java 17 (LTS) e Java 21 (LTS)
- libGDX 1.12.1
- Android Gradle Plugin 8.x

> **Regra prática:** Para projetos novos, use sempre a versão **LTS mais recente** do Gradle. Evite versões com mais de 2 anos, pois podem ter incompatibilidades com plugins modernos.

---

## 2.3 O que é um Build Tool?

Antes de mergulhar nos detalhes do Gradle, pense no problema que ele resolve.

Para compilar e executar um projeto Java você precisa:

1. Baixar bibliotecas externas (como a libGDX).
2. Colocar os `.jar` no classpath.
3. Compilar todos os `.java` na ordem correta.
4. Empacotar o resultado em um `.jar` ou `.zip`.
5. Executar o programa.

Fazer isso **manualmente** para projetos grandes com dezenas de dependências é inviável. Um **build tool** automatiza todo esse processo com um único comando.

```
Você escreve:   gradlew.bat lwjgl3:run
                        ↓
Gradle faz:     [1] Baixa libGDX do Maven Central
                [2] Compila core/ e lwjgl3/
                [3] Copia os assets
                [4] Executa o jogo
```

---

## 2.4 Gradle vs Maven


| Característica         | Maven                      | Gradle                              |
| ------------------------- | ---------------------------- | ------------------------------------- |
| Configuração          | XML (`pom.xml`) — verboso | Groovy/Kotlin DSL — conciso        |
| Flexibilidade           | Baixa (estrutura rígida)  | Alta (lógica condicional, scripts) |
| Performance             | Mais lento                 | Mais rápido (cache incremental)    |
| Uso em Android          | Não                       | **Padrão oficial**                 |
| Uso em jogos            | Raro                       | Padrão (libGDX)                    |
| Curva de aprendizado    | Moderada                   | Moderada                            |
| Suporte a multi-módulo | Sim                        | Sim (mais flexível)                |
| Cache de build          | Não                       | **Sim (Build Cache)**               |

> A libGDX usa **Gradle** como padrão. Android também usa Gradle. Por isso aprenderemos Gradle aqui.

**Exemplo comparativo** — declarar uma dependência:

```xml
<!-- Maven (pom.xml) — mais verboso -->
<dependency>
    <groupId>com.badlogicgames.gdx</groupId>
    <artifactId>gdx</artifactId>
    <version>1.12.1</version>
</dependency>
```

```groovy
// Gradle (build.gradle) — mais conciso
implementation 'com.badlogicgames.gdx:gdx:1.12.1'
```

---

## 2.5 Conceitos Fundamentais do Gradle

### 2.5.1 Tasks (Tarefas)

A unidade básica de trabalho no Gradle é a **Task**. Tudo que o Gradle faz é uma task.

```
Exemplos de tasks:
  compileJava   → compila os arquivos .java
  processResources → copia assets para o build
  jar           → empacota o .jar
  run           → executa o programa
  clean         → apaga a pasta build/
  test          → executa os testes
```

Cada task:

- Tem **entradas** (inputs): código-fonte, configurações
- Tem **saídas** (outputs): arquivos compilados, JARs
- Pode **depender** de outras tasks

```groovy
// Exemplo de task personalizada no build.gradle
tasks.register('ola') {
    doLast {
        println 'Ola, Missao Marte!'
    }
}
```

Execute com:

```bat
.\gradlew.bat ola
```

### 2.5.2 DAG – Grafo Acíclico Direcionado

O Gradle organiza todas as tasks em um **DAG** (Directed Acyclic Graph — Grafo Acíclico Direcionado). Isso significa que:

- Cada task pode depender de outras.
- O Gradle calcula a **ordem correta** de execução automaticamente.
- Não há ciclos (A não pode depender de B que depende de A).

```
Exemplo ao executar  gradlew lwjgl3:run :

compileJava (core)
      ↓
    jar (core)
      ↓
compileJava (lwjgl3)
      ↓
  classes (lwjgl3)
      ↓
     run (lwjgl3)  ← task solicitada
```

O Gradle executa apenas as tasks **necessárias** para atingir a task solicitada.

### 2.5.3 Gradle Daemon

O **Gradle Daemon** é um processo que fica rodando em **segundo plano** após o primeiro build.

- O primeiro `gradlew.bat build` é lento (inicializa a JVM, carrega plugins).
- Os builds seguintes são rápidos porque o Daemon já está na memória.

```
Sem Daemon:   gradlew build → [inicializa JVM] → [carrega] → [compila] → 30s
Com Daemon:   gradlew build → [compila]                                 →  5s
```

O Daemon é **habilitado por padrão** desde o Gradle 3.0. Você pode verificá-lo:

```bat
.\gradlew.bat --status
```

### 2.5.4 Build Cache (Cache de Build)

O Gradle armazena os **resultados das tasks** em um cache local. Se as entradas de uma task não mudaram, o Gradle **reutiliza** o resultado anterior sem recompilar.

```
Primeira execução:    compileJava → compila 7 arquivos → armazena no cache
Segunda execução:     compileJava → nada mudou → UP-TO-DATE (0 arquivos recompilados)
Após editar Nave.java: compileJava → apenas Nave.java foi alterado → recompila 1 arquivo
```

Você vê isso na saída do Gradle:

```
> Task :core:compileJava UP-TO-DATE
> Task :lwjgl3:compileJava
```

`UP-TO-DATE` significa que o Gradle **não recompilou** porque nada mudou — economizando tempo.

### 2.5.5 Configuration Cache

Desde o **Gradle 8.0**, existe o **Configuration Cache** — um cache ainda mais agressivo que armazena o resultado da **leitura do `build.gradle`** em si.

Isso faz com que o Gradle nem precise ler e interpretar os arquivos `.gradle` nas execuções subsequentes, tornando o build ainda mais rápido.

---

## 2.6 DSL: Groovy vs Kotlin

O arquivo `build.gradle` pode ser escrito em duas linguagens:


|                                 | Groovy DSL         | Kotlin DSL                         |
| --------------------------------- | -------------------- | ------------------------------------ |
| **Arquivo**                     | `build.gradle`     | `build.gradle.kts`                 |
| **Linguagem**                   | Groovy (dinâmica) | Kotlin (estática)                 |
| **Autocompletar no VS Code**    | Parcial            | Completo                           |
| **Velocidade de parse**         | Mais rápido       | Um pouco mais lento                |
| **Verificação em tempo real** | Não               | Sim (erros detectados na edição) |
| **Popularidade**                | Padrão histórico | Crescente (tendência atual)       |

**Neste tutorial usamos Groovy DSL** (`build.gradle`) por ser mais simples para iniciantes e mais comum nos exemplos da libGDX.

**Exemplo do mesmo código nas duas linguagens:**

```groovy
// Groovy DSL (build.gradle)
dependencies {
    implementation 'com.badlogicgames.gdx:gdx:1.12.1'
}
```

```kotlin
// Kotlin DSL (build.gradle.kts)
dependencies {
    implementation("com.badlogicgames.gdx:gdx:1.12.1")
}
```

A diferença mais visível é o uso de **aspas** ao invés de apóstrofos, e parênteses obrigatórios nas chamadas de função.

---

## 2.7 Estrutura de um `build.gradle`

Todo projeto Gradle tem ao menos um arquivo `build.gradle`. Veja a estrutura completa anotada:

```groovy
// ─── 1. PLUGINS ─────────────────────────────────────────────
// Plugins adicionam tarefas e funcionalidades ao projeto
plugins {
    id 'java'          // habilita compileJava, jar, test...
    id 'application'   // habilita run, installDist...
}

// ─── 2. IDENTIDADE DO PROJETO ───────────────────────────────
group   = 'br.unifor.missaomarte' // identificador do autor/organização
version = '1.0'                   // versão do seu software

// ─── 3. REPOSITÓRIOS ────────────────────────────────────────
// Onde o Gradle vai buscar as dependências
repositories {
    mavenCentral()                          // repositório público principal
    maven { url 'https://s01.oss.sonatype.org' } // repositório da libGDX
}

// ─── 4. DEPENDÊNCIAS ────────────────────────────────────────
dependencies {
    implementation 'com.badlogicgames.gdx:gdx:1.12.1'
    implementation 'com.badlogicgames.gdx:gdx-backend-lwjgl3:1.12.1'
}

// ─── 5. CONFIGURAÇÕES DO PLUGIN ─────────────────────────────
application {
    mainClass = 'br.unifor.missaomarte.Lwjgl3Launcher'
}

// ─── 6. CONFIGURAÇÕES DO COMPILADOR ─────────────────────────
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
```

### O que é `group` e `version`?

No ecossistema Java, todo artefato publicado tem três coordenadas, chamadas de **GAV**:

```
grupo : artefato : versão
  ↑         ↑        ↑
group    (nome do  version
         projeto)
```

Exemplo real: `com.badlogicgames.gdx:gdx:1.12.1`

Para nosso projeto: `br.unifor.missaomarte:missao-marte:1.0`

---

## 2.8 Dependências e seus Escopos

Quando você declara uma dependência, você escolhe um **escopo** que define *onde* ela será usada:


| Escopo               | Usado em                  | Exportado para quem depende? | Exemplo de uso                         |
| ---------------------- | --------------------------- | ------------------------------ | ---------------------------------------- |
| `implementation`     | compilação + execução | ❌ Não                      | Dependências internas                 |
| `api`                | compilação + execução | ✅ Sim                       | Dependências expostas na API pública |
| `runtimeOnly`        | apenas execução         | ❌ Não                      | Drivers JDBC, natives                  |
| `testImplementation` | apenas testes             | ❌ Não                      | JUnit, Mockito                         |
| `compileOnly`        | apenas compilação       | ❌ Não                      | Annotations, Lombok                    |

**Exemplo no projeto missao-marte:**

```groovy
// core/build.gradle
dependencies {
    // gdx é usado internamente — não expõe para quem depende do :core
    implementation "com.badlogicgames.gdx:gdx:$gdxVersion"
}

// lwjgl3/build.gradle
dependencies {
    implementation project(':core')           // depende do módulo core
    implementation "com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion" // backend desktop
    runtimeOnly    "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop" // DLLs nativas
}
```

> **Por que `runtimeOnly` para os natives?**
> As bibliotecas nativas (`.dll`, `.so`, `.dylib`) não são necessárias durante a compilação do Java — o código Java não referencia classes delas diretamente. Elas são carregadas dinamicamente em tempo de execução pela libGDX.

### Como o Gradle baixa as dependências

```
build.gradle declara:
  implementation 'com.badlogicgames.gdx:gdx:1.12.1'
            ↓
Gradle consulta:
  https://repo1.maven.org/maven2/com/badlogicgames/gdx/gdx/1.12.1/
            ↓
Gradle baixa:
  gdx-1.12.1.jar  (biblioteca)
  gdx-1.12.1.pom  (metadados de dependências transitivas)
            ↓
Gradle armazena em cache local:
  C:\Users\<usuario>\.gradle\caches\modules-2\files-2.1\...
            ↓
Gradle adiciona ao classpath do projeto
```

O cache fica em `~/.gradle/caches/` — por isso **o segundo build é muito mais rápido**: o Gradle não precisa baixar nada novamente.

---

## 2.9 O Gradle Wrapper em Detalhes

O **Gradle Wrapper** é um dos recursos mais importantes do Gradle para trabalho em equipe.

### O problema que ele resolve

Imagine que:

- Aluno A tem Gradle 7.6 instalado na máquina.
- Aluno B tem Gradle 8.7 instalado.
- O projeto foi criado com Gradle 8.7.

Resultado: os builds serão diferentes, podendo gerar erros de incompatibilidade.

### A solução: o Wrapper

O Wrapper é um **script incluído no próprio projeto** que:

1. Lê qual versão do Gradle o projeto precisa (em `gradle-wrapper.properties`).
2. Verifica se essa versão já está no cache local (`~/.gradle/wrapper/dists/`).
3. Se não estiver, **baixa automaticamente** a versão correta.
4. Executa o Gradle na versão exata especificada.

```
missao-marte/
├── gradlew.bat                      ← script wrapper (Windows)
├── gradlew                          ← script wrapper (Linux/Mac)
└── gradle/wrapper/
    ├── gradle-wrapper.jar           ← mini-programa que baixa o Gradle
    └── gradle-wrapper.properties    ← especifica a versão e URL de download
```

### Conteúdo do `gradle-wrapper.properties`

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.7-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

A linha `distributionUrl` aponta para **exatamente qual versão** do Gradle será usada. Qualquer aluno que clonar o projeto e rodar `.\gradlew.bat` usará **sempre a mesma versão**.

### Regra de ouro

> Nunca use `gradle <task>` (instalação global).
> **Sempre use** `.\gradlew.bat <task>` (wrapper do projeto).
> Isso garante que todos na equipe usem a mesma versão do Gradle.

---

## 2.10 Ciclo de Vida de um Build Gradle

Todo build Gradle passa por **três fases**:

```
┌──────────────────────────────────────────────────────────────┐
│                    CICLO DE VIDA DO BUILD                    │
│                                                              │
│  ┌──────────────────┐                                        │
│  │  1. INICIALIZAÇÃO│  Lê settings.gradle                    │
│  │                  │  Determina quais módulos existem        │
│  └────────┬─────────┘                                        │
│           ↓                                                  │
│  ┌──────────────────┐                                        │
│  │  2. CONFIGURAÇÃO │  Executa todos os build.gradle         │
│  │                  │  Monta o DAG de tasks                  │
│  └────────┬─────────┘                                        │
│           ↓                                                  │
│  ┌──────────────────┐                                        │
│  │  3. EXECUÇÃO     │  Executa apenas as tasks solicitadas   │
│  │                  │  na ordem definida pelo DAG            │
│  └──────────────────┘                                        │
└──────────────────────────────────────────────────────────────┘
```

**Fase 1 — Inicialização:** o Gradle lê o `settings.gradle` para saber quais módulos (`core`, `lwjgl3`) fazem parte do projeto.

**Fase 2 — Configuração:** o Gradle lê e executa todos os `build.gradle`, registra plugins, dependências e tasks, e constrói o DAG. Nenhuma compilação acontece aqui.

**Fase 3 — Execução:** agora sim o Gradle executa as tasks, na ordem correta definida pelo DAG.

---

## 2.11 Projeto Multi-módulo (libGDX)

A libGDX organiza o projeto em **módulos** porque o mesmo jogo pode rodar em:

- Desktop (Windows, Mac, Linux)
- Android
- Web (HTML5)

```
missao-marte/
├── build.gradle          ← configuração raiz (versões compartilhadas)
├── settings.gradle       ← lista os módulos
├── core/                 ← lógica do jogo (independente de plataforma)
│   ├── build.gradle
│   └── src/
├── lwjgl3/               ← launcher para desktop
│   ├── build.gradle
│   └── src/
└── android/              ← launcher para Android (futuro)
    ├── build.gradle
    └── src/
```

O módulo `core` contém **toda a lógica do jogo** e não conhece nenhuma plataforma. Os módulos `lwjgl3` e `android` apenas inicializam a plataforma e delegam ao `core`.

**`settings.gradle` — registra os módulos:**

```groovy
rootProject.name = 'missao-marte'
include 'core'
include 'lwjgl3'
```

**Dependência entre módulos:**

```groovy
// lwjgl3/build.gradle
dependencies {
    implementation project(':core')  // ← lwjgl3 depende de core
}
```

---

## 2.12 Comandos Gradle Essenciais

Abra o terminal na pasta raiz do projeto:

```bat
:: ── BUILD ──────────────────────────────────────────────────
:: Compila e testa tudo
.\gradlew.bat build

:: Compila apenas o módulo core
.\gradlew.bat core:compileJava

:: ── EXECUÇÃO ────────────────────────────────────────────────
:: Executa o jogo desktop
.\gradlew.bat lwjgl3:run

:: ── LIMPEZA ─────────────────────────────────────────────────
:: Remove todos os arquivos compilados (pasta build/)
.\gradlew.bat clean

:: ── DIAGNÓSTICO ─────────────────────────────────────────────
:: Lista todas as tasks disponíveis
.\gradlew.bat tasks

:: Lista todas as tasks, incluindo internas
.\gradlew.bat tasks --all

:: Exibe a árvore de dependências do módulo core
.\gradlew.bat core:dependencies

:: Exibe a versão do Gradle em uso
.\gradlew.bat --version

:: Exibe informações do build (tempo de cada task)
.\gradlew.bat build --profile

:: Executa com mais detalhes no log
.\gradlew.bat lwjgl3:run --info

:: Executa mostrando as tasks que seriam executadas (sem executar)
.\gradlew.bat lwjgl3:run --dry-run
```

### Saída típica de um build bem-sucedido

```
> Task :core:compileJava
> Task :core:processResources NO-SOURCE
> Task :core:classes
> Task :core:jar
> Task :lwjgl3:compileJava
> Task :lwjgl3:processResources
> Task :lwjgl3:classes
> Task :lwjgl3:run

BUILD SUCCESSFUL in 4s
6 actionable tasks: 3 executed, 3 up-to-date
```

- **`executed`** = task foi executada (algo mudou)
- **`up-to-date`** = task não foi executada (nada mudou — cache funcionando)

> **Nota:** O `gradlew.bat` é o **Gradle Wrapper** — um script incluído no projeto que baixa automaticamente a versão correta do Gradle. Você **não precisa instalar o Gradle manualmente** para usar o wrapper. Veja a Parte 4 para configurar o ambiente completo.

---

# Parte 3 – A API libGDX

---

## 3.1 História e Origem

### Quem criou?

A libGDX foi criada por **Mario Zechner**, um programador austríaco apaixonado por jogos. Em **2009**, enquanto trabalhava como desenvolvedor de software, Mario começou a desenvolver a biblioteca em seu tempo livre com um objetivo claro: criar jogos em Java que rodassem tanto no desktop quanto em **Android** — sem reescrever o código para cada plataforma.

Em **2010**, Mario lançou a primeira versão pública da libGDX no Google Code e publicou o livro *"Beginning Android Games"* (Apress, 2011 e 2012), que se tornou a principal referência da biblioteca na época.

A empresa **Bad Logic Games** foi fundada por Mario e seu sócio **Nate Robins** para sustentar e promover a libGDX. Embora a empresa seja pequena, o projeto é mantido hoje por uma comunidade ativa de contribuidores ao redor do mundo.


| Item                     | Detalhe                                     |
| -------------------------- | --------------------------------------------- |
| **Criador**              | Mario Zechner                               |
| **Empresa**              | Bad Logic Games                             |
| **Primeiro lançamento** | 2010                                        |
| **Repositório**         | https://github.com/libgdx/libgdx            |
| **Licença**             | Apache License 2.0 (open source e gratuita) |
| **Linguagem**            | Java + C/C++ (código nativo via JNI)       |
| **Website**              | https://libgdx.com                          |

> **Curiosidade:** Mario publicou um livro sobre desenvolvimento de jogos com Android que apresentava a libGDX ao grande público. Isso impulsionou a adoção da biblioteca e formou a comunidade inicial.

### Linha do tempo

```
2009  Mario Zechner inicia o desenvolvimento pessoal
2010  Primeiro lançamento público no Google Code
2011  Livro "Beginning Android Games" (1ª ed.) — libGDX ganha visibilidade
2012  Livro "Beginning Android Games" (2ª ed.) — comunidade cresce
2013  Migração para GitHub — contribuições externas aceleram
2014  libGDX 1.0 — primeira versão estável
2015  Backend LWJGL3 introduzido (substituindo o LWJGL2)
2019  libGDX.com relançado — novo site e fórum
2020  Adoção ampla em game jams (itch.io, Ludum Dare)
2021  libGDX 1.9.13 — suporte a Java 11+
2022  libGDX 1.9.14 e 1.11.0 — refatorações internas
2023  libGDX 1.12.0 — gdx-liftoff como gerador oficial de projetos
2024  libGDX 1.12.1 — versão usada neste tutorial
```

---

## 3.2 O que é a libGDX?

A libGDX é um **framework de desenvolvimento de jogos** escrito em Java. Diferente de motores como Unity ou Godot, a libGDX **não tem editor visual** — você escreve todo o jogo em código. Isso a torna ideal para aprender programação orientada a objetos aplicada a jogos.

### Posicionamento no mercado

```
Unity / Godot / Unreal    ← engines visuais (drag-and-drop, scripts)
        ↑
     libGDX                ← framework em código (Java)
        ↑
    LWJGL / OpenGL         ← bibliotecas de baixo nível (janela, GPU)
        ↑
  Sistema Operacional      ← Windows, Linux, macOS
```

A libGDX fica entre o código de baixo nível (OpenGL) e o desenvolvedor, fornecendo uma **API de alto nível** para criar jogos sem precisar conhecer OpenGL diretamente.

---

## 3.3 Características Principais

### Multiplataforma (Write Once, Run Anywhere)

A principal vantagem da libGDX é a capacidade de escrever o código **uma vez** e rodar em múltiplas plataformas:


| Plataforma                      | Backend    | Descrição                            |
| --------------------------------- | ------------ | ---------------------------------------- |
| **Desktop** (Windows/Mac/Linux) | LWJGL3     | Usa Java e OpenGL via LWJGL            |
| **Android**                     | Android    | Usa OpenGL ES nativo                   |
| **iOS**                         | RoboVM/MOE | Compila Java para código nativo Apple |
| **Web (HTML5)**                 | GWT        | Transpila Java para JavaScript + WebGL |

O código do jogo fica no módulo `core` e é **100% compartilhado** entre todas as plataformas. Apenas o launcher (inicialização) muda.

### Open Source e Gratuita

A libGDX é completamente **gratuita** para uso **comercial e educacional**. Não há taxa de royalties, sem limitações de licença. Você pode publicar e vender seu jogo sem pagar nada à Bad Logic Games.

### Baseada em Java

Toda a API da libGDX é Java puro do lado do desenvolvedor. Internamente, a biblioteca usa **código nativo** (C/C++) via JNI para operações de alto desempenho como:

- Renderização OpenGL/ES
- Decodificação de áudio (OGG, WAV, MP3)
- Físicas com Box2D
- Leitura de arquivos nativos da plataforma

### Módulos da libGDX

A biblioteca é organizada em módulos acessíveis via a classe `Gdx`:

```java
Gdx.graphics   // resolução, delta time, renderização
Gdx.input      // teclado, mouse, touch, giroscópio
Gdx.audio      // sons, músicas
Gdx.files      // leitura/escrita de arquivos
Gdx.net        // requisições HTTP
Gdx.app        // log, informações do dispositivo
```

### Principais Classes da API


| Classe                 | Módulo  | Para que serve                                               |
| ------------------------ | ---------- | -------------------------------------------------------------- |
| `Game`                 | core     | Classe base do jogo; gerencia`Screen`s                       |
| `Screen`               | core     | Interface para uma "tela" do jogo                            |
| `SpriteBatch`          | graphics | Renderiza sprites/imagens em lote                            |
| `ShapeRenderer`        | graphics | Desenha formas geométricas (círculos, linhas, retângulos) |
| `Texture`              | graphics | Carrega e armazena uma imagem na GPU                         |
| `TextureAtlas`         | graphics | Carrega múltiplas imagens empacotadas                       |
| `BitmapFont`           | graphics | Renderiza texto usando uma textura de fonte                  |
| `OrthographicCamera`   | graphics | Câmera 2D; controla o "ponto de vista"                      |
| `SpriteBatch`          | graphics | Acumula chamadas de desenho para enviar à GPU em lote       |
| `Circle` / `Rectangle` | math     | Representam formas para detecção de colisão               |
| `Intersector`          | math     | Verifica sobreposição entre formas                         |
| `MathUtils`            | math     | Funções matemáticas e gerador de números aleatórios     |
| `Array<T>`             | utils    | Lista otimizada para jogos (sem alocação de lixo)          |
| `Sound` / `Music`      | audio    | Sons curtos e músicas de fundo                              |
| `Box2D`                | physics  | Motor de física 2D                                          |

---

## 3.4 Versões da libGDX

### Histórico de Versões Principais


| Versão    | Ano  | Destaques                                                                  |
| ------------ | ------ | ---------------------------------------------------------------------------- |
| **0.7**    | 2010 | Primeiro lançamento público. Desktop + Android.                          |
| **0.9**    | 2011 | Melhorias no sistema de input e áudio.                                    |
| **1.0**    | 2014 | Primeira versão**estável**. API madura.                                  |
| **1.5**    | 2015 | Refatoração do sistema de câmeras.                                      |
| **1.9.0**  | 2016 | Backend**LWJGL3** introduzido (substitui LWJGL2). Suporte a multi-janelas. |
| **1.9.4**  | 2017 | Melhorias no suporte a iOS.                                                |
| **1.9.9**  | 2019 | Novo site e fórum. Correções acumuladas.                                |
| **1.9.10** | 2019 | Melhorias na API de`InputProcessor`.                                       |
| **1.9.11** | 2020 | Suporte aprimorado a Android 10+.                                          |
| **1.9.12** | 2021 | Estabilidade e correções de bugs.                                        |
| **1.9.13** | 2021 | **Java 11+ suportado.** Internos modernizados.                             |
| **1.9.14** | 2022 | Melhorias de performance no`SpriteBatch`.                                  |
| **1.11.0** | 2022 | Refatoração de build (Gradle). GWT atualizado.                           |
| **1.12.0** | 2023 | **gdx-liftoff** torna-se o gerador oficial de projetos.                    |
| **1.12.1** | 2024 | Correções e melhorias.**← Versão usada neste tutorial**                |

### Como verificar a versão usada no projeto

No arquivo `build.gradle` raiz:

```groovy
ext {
    gdxVersion = '1.12.1'  // ← versão da libGDX
}
```

Essa variável é usada em todas as dependências:

```groovy
implementation "com.badlogicgames.gdx:gdx:$gdxVersion"
```

### Qual versão usar?

Para novos projetos, use sempre a **versão mais recente estável** (atualmente 1.12.1). A libGDX mantém boa compatibilidade retroativa — código escrito para a 1.9.x geralmente compila na 1.12.x com pequenas adaptações.

---

## 3.5 Por que a libGDX usa Gradle?

A libGDX adotou o Gradle como padrão porque:

1. **Multi-plataforma exige multi-módulo:** O mesmo jogo precisa compilar para desktop, Android e web — cada um com dependências diferentes. O Gradle resolve isso com módulos.
2. **Dependências nativas:** As bibliotecas nativas (`.dll`, `.so`, `.dylib`) para cada plataforma são gerenciadas automaticamente via `runtimeOnly natives-*`.
3. **Android exige Gradle:** Se você quiser publicar para Android, o Android Studio usa Gradle obrigatoriamente.

```groovy
// Exemplo de multi-plataforma no build.gradle
// O mesmo projeto core compila para todos:
dependencies {
    implementation project(':core')                     // lógica do jogo

    // Desktop:
    implementation "com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion"
    runtimeOnly    "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"

    // Android (futuro):
    // implementation "com.badlogicgames.gdx:gdx-backend-android:$gdxVersion"
}
```

---

## 3.6 Alternativas à libGDX

Para entender o posicionamento da libGDX, veja as principais alternativas:


| Framework/Engine  | Linguagem      | Tipo                    | Plataformas                   | Gratuito?      |
| ------------------- | ---------------- | ------------------------- | ------------------------------- | ---------------- |
| **libGDX**        | Java/Kotlin    | Framework (código)     | Desktop, Android, iOS, Web    | ✅ Sim         |
| **Unity**         | C#             | Engine visual           | Desktop, Mobile, Console, Web | ⚠️ Freemium  |
| **Godot**         | GDScript/C#    | Engine visual           | Desktop, Mobile, Web          | ✅ Sim         |
| **Unreal Engine** | C++/Blueprints | Engine visual           | Desktop, Console, Mobile      | ⚠️ Royalties |
| **LWJGL**         | Java           | Biblioteca baixo nível | Desktop                       | ✅ Sim         |
| **Defold**        | Lua            | Engine visual           | Desktop, Mobile, Web          | ✅ Sim         |
| **MonoGame**      | C#             | Framework (código)     | Desktop, Console, Mobile      | ✅ Sim         |

A libGDX é ideal para **aprendizado de programação** porque:

- Você escreve **Java puro** (sem scripts proprietários)
- Não há "magia" de editor — você vê **como tudo funciona**
- Os conceitos (game loop, câmera, batch rendering) se aplicam a qualquer engine

---

## 3.7 Ecossistema da libGDX

### Ferramentas oficiais


| Ferramenta          | Descrição                                            | Link                                 |
| --------------------- | -------------------------------------------------------- | -------------------------------------- |
| **gdx-liftoff**     | Gerador de projetos libGDX (substitui o gdx-setup.jar) | github.com/libgdx/gdx-liftoff        |
| **Texture Packer**  | Empacota imagens em um TextureAtlas                    | libgdx.com/wiki/tools/texture-packer |
| **Hiero**           | Gera BitmapFont a partir de fontes TrueType            | Incluso no projeto                   |
| **Particle Editor** | Editor visual de partículas                           | Incluso no projeto                   |

### Extensões populares (não oficiais)


| Extensão    | Para que serve                                        |
| -------------- | ------------------------------------------------------- |
| **Ashley**   | Entity-Component System (ECS) — para jogos complexos |
| **Box2D**    | Motor de física 2D (incluso na libGDX)               |
| **TiledMap** | Carrega mapas criados no editor Tiled                 |
| **VisUI**    | Componentes de UI para menus                          |
| **gdx-ai**   | Inteligência artificial (pathfinding, steering)      |

### Comunidade

- **Fórum oficial:** https://libgdx.com/community/
- **Discord:** discord.gg/libgdx
- **Subreddit:** reddit.com/r/libgdx
- **Ludum Dare / itch.io:** centenas de jogos publicados com libGDX

---

## 3.8 Sprites e Texturas

Um **sprite** é uma imagem 2D desenhada na tela para representar um objeto do jogo (nave, inimigo, projétil, item, etc.).

### Pipeline de renderização

```
Arquivo PNG             →   Pixmap         →   Texture        →   Sprite
(disco / assets)            (RAM / CPU)        (VRAM / GPU)       (posição + escala)
Gdx.files.internal()        new Pixmap()       new Texture()      new Sprite(texture)
                                                                   sprite.draw(batch)
```

### Pixmap — imagem na RAM

Um `Pixmap` é um bitmap criado **em memória** (na CPU). Você pode desenhar nele com métodos como `fillCircle()`, `drawLine()`, `setColor()`. Útil para gerar texturas proceduralmente (sem arquivo de imagem):

```java
// Cria um bitmap 16×16 pixels na RAM
Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
pixmap.setColor(0, 0, 0, 0);   // fundo transparente
pixmap.fill();

pixmap.setColor(Color.YELLOW);
pixmap.fillCircle(8, 8, 7);    // círculo amarelo

pixmap.setColor(Color.WHITE);
pixmap.fillCircle(8, 8, 3);    // núcleo branco brilhante

// Envia os pixels para a GPU → libera o Pixmap da RAM
Texture texture = new Texture(pixmap);
pixmap.dispose();
```

### Texture — imagem na GPU

Uma `Texture` reside na **memória da GPU (VRAM)**. Carregar uma textura de arquivo:

```java
// A partir de um arquivo em assets/ (maneira mais comum)
Texture texture = new Texture(Gdx.files.internal("nave.png"));
```

> **Regra:** cada `Texture` criada deve ser liberada com `texture.dispose()` quando não for mais usada — senão vaza memória de GPU.

### Sprite — textura com transformações

Um `Sprite` combina uma `Texture` com posição, tamanho, rotação e cor:

```java
Sprite sprite = new Sprite(texture);
sprite.setSize(32, 32);        // largura e altura em pixels do mundo
sprite.setOriginCenter();      // ponto de rotação no centro
sprite.setPosition(x - 16, y - 16); // posiciona o canto inferior-esquerdo

// Dentro de render():
batch.begin();
sprite.draw(batch);            // envia o sprite para o SpriteBatch
batch.end();                   // flush: envia tudo para a GPU de uma vez
```

### SpriteBatch — renderização em lote

O `SpriteBatch` **acumula** todas as chamadas `draw()` e as envia para a GPU em **uma única operação** (batch). Isso reduz drasticamente as chamadas à GPU (draw calls) e melhora a performance:

```
Sem batch:  100 sprites = 100 draw calls para a GPU  (lento)
Com batch:  100 sprites = 1  draw call para a GPU    (rápido)
```

```java
// Sempre use begin/end para delimitar o bloco de renderização
batch.begin();
  for (Sprite s : sprites) s.draw(batch); // acumula
batch.end();                               // envia tudo de uma vez
```

### No projeto Missão Marte

O arquivo `Projetil.java` demonstra o pipeline completo:

```java
// Cria textura com Pixmap (sem arquivo externo)
Pixmap px = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
px.setColor(Color.YELLOW);
px.fillCircle(8, 8, 7);
Texture t = new Texture(px);
px.dispose();

// Usa como Sprite
Sprite sprite = new Sprite(t);
sprite.setSize(RAIO * 2, RAIO * 2);
sprite.setPosition(x - RAIO, y - RAIO);

// Renderiza
batch.begin();
sprite.draw(batch);
batch.end();
```

> **Dica de performance:** Texturas usadas por muitos objetos devem ser **estáticas** (criadas uma vez, compartilhadas por todas as instâncias). Em `Projetil.java`, a `Texture` é `static` — todos os projéteis compartilham a mesma textura na GPU.

---

## 3.9 Sons e Áudio

A libGDX oferece duas formas de reproduzir áudio:


|                              | `Sound`                          | `Music`                                  |
| ------------------------------ | ---------------------------------- | ------------------------------------------ |
| **Uso**                      | Efeitos curtos (tiro, explosão) | Músicas longas (trilha sonora)          |
| **Carregamento**             | Inteiro na memória RAM          | Streaming (lê do disco em pedaços)     |
| **Reprodução simultânea** | Vários sons ao mesmo tempo      | Uma instância por vez                   |
| **Formato**                  | WAV, OGG, MP3                    | OGG, MP3                                 |
| **API principal**            | `sound.play(volume)`             | `music.play()`, `music.setLooping(true)` |

### Carregando e reproduzindo sons

```java
// Carregamento (em create() ou show())
Sound somTiro = Gdx.audio.newSound(Gdx.files.internal("sounds/tiro.wav"));
Music trilha  = Gdx.audio.newMusic(Gdx.files.internal("sounds/musica.ogg"));

// Reprodução
somTiro.play(0.8f);         // volume 0.0 a 1.0
somTiro.play(1f, 1.2f, 0f); // volume, pitch, pan

// Música em loop
trilha.setLooping(true);
trilha.setVolume(0.5f);
trilha.play();

// Liberação de memória (obrigatório em dispose())
somTiro.dispose();
trilha.dispose();
```

### Sons procedurais com PCM

Para gerar sons sem arquivos externos (como no projeto Missão Marte), usa-se síntese PCM (Pulse-Code Modulation):

```java
// PCM: sequência de amostras numéricas que representam a onda sonora
// 44100 amostras por segundo = qualidade CD
short[] samples = new short[44100 / 10]; // 0.1 segundo de som

for (int i = 0; i < samples.length; i++) {
    double angulo = 2 * Math.PI * 880 /* Hz */ * i / 44100;
    samples[i] = (short) (Math.sin(angulo) * Short.MAX_VALUE * 0.7f);
}
// → onda senoidal de 880 Hz (nota A5 — som de "bip" agudo)
```

### No projeto Missão Marte

`GerenciadorSom.java` gera três sons proceduralmente e os carrega como `Sound`:

- `tocarTiro()` — bip agudo (880 Hz, 80 ms)
- `tocarExplosao()` — ruído branco com decaimento (200 ms)
- `tocarGameOver()` — sequência descendente 440→330→220 Hz

```java
// Uso em GameScreen
jogo.som.tocarTiro();      // ao disparar (tecla SPACE)
jogo.som.tocarExplosao();  // quando projétil acerta asteroide
jogo.som.tocarGameOver();  // ao perder todas as vidas
```

---

## 3.10 HUD (Head-Up Display)

O **HUD** é a camada de interface sobreposta ao jogo — pontuação, vidas, mini-mapa, barra de vida, munição. Ele é desenhado **depois** de todos os elementos do jogo para aparecer por cima.

### Estrutura do HUD em libGDX

```java
private void desenhar() {
    // 1. Limpa a tela
    ScreenUtils.clear(0, 0, 0.1f, 1);

    // 2. Elementos do jogo (fundo, entidades)
    shapes.begin(ShapeRenderer.ShapeType.Filled);
    desenharMundo();   // estrelas, naves, asteroides...
    shapes.end();

    // 3. HUD (sempre por cima)
    batch.begin();
    desenharHUD();     // texto, ícones, barras de vida
    batch.end();
}
```

### Componentes típicos do HUD


| Componente                 | Implementação libGDX                                 |
| ---------------------------- | -------------------------------------------------------- |
| **Texto** (pontos, nível) | `BitmapFont.draw(batch, texto, x, y)`                  |
| **Barra de vida/escudo**   | `ShapeRenderer.rect(x, y, largura, altura)`            |
| **Ícones de vidas**       | Mini-triângulos desenhados com`shapes.triangle()`     |
| **Mini-mapa**              | Retângulo no canto com pontos representando entidades |
| **Contador**               | `BitmapFont.draw()` com valor atualizado a cada frame  |

### No projeto Missão Marte

O HUD inclui três camadas:

```
┌─────────────────────────────────────────────────────────┐
│ Pontos: 1850      Escudo: [██░] [░░░]   Nivel 3         │  ← linha 1
│ Vidas: [■][■][□]  Destruidos: 12        Nivel           │  ← linha 2
├─────────────────────────────────────────────────────────┤
│                   ÁREA DO JOGO                          │
│                                                         │
└─────────────────────────────────────────────────────────┘
│ [WASD] Mover  [SPACE] Tiro  [P] Pausa  [ESC] Menu       │  ← rodapé
```

**Barra de escudo** — representação visual das vidas restantes:

```java
for (int i = 0; i < VIDAS_INICIAIS; i++) {
    float ratio = (float) vidas / VIDAS_INICIAIS;
    if (i < vidas) {
        shapes.setColor(1f - ratio, ratio, 0f, 1f); // verde→vermelho
    } else {
        shapes.setColor(0.15f, 0.15f, 0.15f, 1f);   // célula vazia
    }
    shapes.rect(startX + i * (cellW + gap), startY, cellW, cellH);
}
```

---

## 3.11 Detecção de Colisões

**Colisão** é o momento em que dois objetos do jogo se sobrepõem. Detectar colisões com precisão é um dos desafios centrais do desenvolvimento de jogos.

### Tipos de hitbox (bounding shape)


| Tipo              | Forma              | Precisão | Performance     | Uso típico             |
| ------------------- | -------------------- | ----------- | ----------------- | ------------------------- |
| **Circle**        | Círculo           | Média    | ✅ Mais rápido | Personagens, projéteis |
| **Rectangle**     | Retângulo         | Baixa     | ✅ Rápido      | Plataformas, paredes    |
| **Polygon**       | Polígono          | Alta      | ❌ Lento        | Terreno complexo        |
| **Pixel-perfect** | Máscara de pixels | Perfeita  | ❌ Muito lento  | Raramente usada         |

> **Regra de ouro:** Use sempre a hitbox **mais simples possível**. Nenhum jogador consegue distinguir a diferença entre hitbox circular e poligonal a 60 fps — mas a performance importa.

### Intersector — colisão na libGDX

A classe `Intersector` oferece métodos estáticos para testar sobreposição:

```java
// Círculo × Círculo (usado no projeto Missão Marte)
Circle nave  = new Circle(x, y, raio);
Circle pedra = new Circle(px, py, praio);
boolean colidiu = Intersector.overlaps(nave, pedra);

// Retângulo × Retângulo
Rectangle r1 = new Rectangle(x, y, w, h);
Rectangle r2 = new Rectangle(px, py, pw, ph);
boolean colidiu = Intersector.overlaps(r1, r2);

// Círculo × Retângulo (híbrido)
boolean colidiu = Intersector.overlaps(circle, rectangle);
```

### Hitbox vs visual

Uma boa prática é deixar a hitbox **menor** que o visual — isso torna o jogo mais justo e satisfatório:

```
Visual do asteroide:  ●   (raio = 30px)
Hitbox real:          •   (raio = 24px = 80% do visual)

Visual da nave:     △   (raio = 22px)
Hitbox real:        ▵   (raio = 15px = 70% do visual)
```

### No projeto Missão Marte

Há dois tipos de colisão implementados em `GameScreen.verificarColisoes()`:

```java
// 1. Projétil (Circle 5px) × Asteroide (Circle 70-80% do visual)
for (Projetil p : projeteis) {
    for (Asteroide a : asteroides) {
        if (Intersector.overlaps(p.getBounds(), a.getBounds())) {
            // → destroi asteroide, +50 pontos, explosão, som
        }
    }
}

// 2. Nave (Circle 70% do visual) × Asteroide
if (Intersector.overlaps(nave.getBounds(), asteroide.getBounds())) {
    // → perde vida, 2s de invencibilidade, nave pisca
}
```

### Efeito visual de explosão

Quando uma colisão projétil×asteroide ocorre, um objeto `Explosao` é criado:

```java
// Explosao: círculo que cresce e muda de cor ao longo de 0.45 segundos
explosoes.add(new Explosao(a.getX(), a.getY(), a.getRaio() * 1.8f));

// Em cada frame (atualizar):
float t = intensidade(); // 1.0 → 0.0
shapes.setColor(t, t * 0.4f, 0f, 1f); // laranja → vermelho → apaga
shapes.circle(x, y, raioAtual(), 14);
```

---

## 3.1 Visão Geral do Ambiente

Para desenvolver com libGDX no VS Code você precisa de quatro componentes:

```
┌──────────────────────────────────────────────────────────┐
│                  Ambiente de Desenvolvimento             │
│                                                          │
│  ┌────────────┐   ┌──────────────┐   ┌───────────────┐  │
│  │  JDK  17+  │   │  VS Code     │   │  Gradle       │  │
│  │  (Java)    │   │  + Extensões │   │  (via wrapper)│  │
│  └────────────┘   └──────────────┘   └───────────────┘  │
│                                                          │
│  JDK compila o Java.  VS Code edita e depura.            │
│  Gradle baixa dependências e executa o build.            │
└──────────────────────────────────────────────────────────┘
```

---

## 3.2 Passo 1 – Instalar o JDK 17

O **JDK** (Java Development Kit) é o compilador Java. Sem ele, nada funciona.

### Download

1. Acesse: https://adoptium.net/temurin/releases/
2. Selecione:
   - **Version:** 17
   - **OS:** Windows
   - **Architecture:** x64
   - **Package Type:** JDK
3. Baixe o instalador `.msi` e execute.
4. Na tela de opções do instalador, marque:
   - ✅ **Set JAVA_HOME variable**
   - ✅ **JavaSoft registry keys**

### Verificar instalação

Abra o **Prompt de Comando** (Win + R → `cmd`) e execute:

```bat
java -version
javac -version
```

Saída esperada:

```
openjdk version "17.x.x" ...
javac 17.x.x
```

> Se aparecer erro `'java' is not recognized`, reinicie o computador após a instalação do JDK.

---

## 3.3 Passo 2 – Instalar o Visual Studio Code

1. Acesse: https://code.visualstudio.com/
2. Clique em **Download for Windows** e execute o instalador.
3. Durante a instalação, marque:
   - ✅ **Add to PATH**
   - ✅ **Add "Open with Code" action to Windows Explorer**

---

## 3.4 Passo 3 – Instalar as Extensões no VS Code

Abra o VS Code e instale as seguintes extensões pelo painel de extensões (`Ctrl + Shift + X`):

### Extension Pack for Java (obrigatório)


| Campo          | Valor                      |
| ---------------- | ---------------------------- |
| **Nome**       | Extension Pack for Java    |
| **Publicador** | Microsoft                  |
| **ID**         | `vscjava.vscode-java-pack` |

Esta extensão instala automaticamente:

- Language Support for Java (compilação, autocompletar)
- Debugger for Java (depuração)
- Test Runner for Java
- Maven for Java
- Project Manager for Java

### Gradle for Java (obrigatório)


| Campo          | Valor                   |
| ---------------- | ------------------------- |
| **Nome**       | Gradle for Java         |
| **Publicador** | Microsoft               |
| **ID**         | `vscjava.vscode-gradle` |

Permite ver e executar tarefas Gradle diretamente no VS Code, sem precisar usar o terminal.

### Como instalar

1. Pressione `Ctrl + Shift + X` para abrir o painel de extensões.
2. Pesquise `Extension Pack for Java` e clique em **Install**.
3. Pesquise `Gradle for Java` e clique em **Install**.
4. Reinicie o VS Code após as instalações.

---

## 3.5 Passo 4 – Instalar o Gradle (Opcional)

> **Importante:** O projeto `missao-marte` já inclui o **Gradle Wrapper** (`gradlew.bat`), que baixa a versão correta do Gradle automaticamente na primeira execução.
> **Você não precisa instalar o Gradle para usar este projeto.**
>
> Siga os passos abaixo apenas se quiser usar o comando `gradle` globalmente no sistema.

### Instalação manual no Windows

1. Acesse: https://gradle.org/releases/
2. Baixe a versão mais recente — arquivo **Binary-only** (`.zip`), por exemplo: `gradle-8.7-bin.zip`.
3. Extraia o ZIP em uma pasta, por exemplo: `C:\Ferramentas\gradle-8.7`
4. Adicione o Gradle ao PATH do sistema:
   - Abra **Painel de Controle → Sistema → Configurações avançadas do sistema → Variáveis de Ambiente**.
   - Em **Variáveis do sistema**, selecione `Path` e clique em **Editar**.
   - Clique em **Novo** e adicione: `C:\Ferramentas\gradle-8.7\bin`
   - Clique em **OK** em todas as janelas.
5. Feche e reabra o terminal, depois verifique:

```bat
gradle --version
```

Saída esperada:

```
Gradle 8.7
...
JVM:          17.x.x ...
```

### Instalação via winget (alternativa mais simples)

Se você tiver o **winget** instalado (disponível no Windows 10/11):

```bat
winget install Gradle.Gradle
```

Reinicie o terminal e verifique com `gradle --version`.

---

## 3.6 Passo 5 – Abrir o Projeto no VS Code

1. Abra o VS Code.
2. Vá em **File → Open Folder** (ou `Ctrl + K, Ctrl + O`).
3. Selecione a pasta `missao-marte` (a pasta raiz que contém `settings.gradle`).
4. Se aparecer a pergunta **"Do you trust the authors?"**, clique em **Yes, I trust the authors**.
5. O VS Code detectará automaticamente o projeto Gradle e iniciará a sincronização.

Aguarde a barra de progresso na parte inferior desaparecer — isso pode levar alguns minutos na primeira vez, pois o Gradle baixa as dependências da libGDX (~50 MB).

---

## 3.7 Executando o Jogo no VS Code

### Opção A – Terminal integrado (recomendado)

1. Abra o terminal integrado: `Ctrl + `` ` `` ` (ou **Terminal → New Terminal**).
2. Certifique-se de que o terminal está na pasta raiz do projeto (deve mostrar `missao-marte>`).
3. Execute:

```bat
.\gradlew.bat lwjgl3:run
```

O Gradle vai compilar o projeto e abrir a janela do jogo.

### Opção B – Painel Gradle do VS Code

1. Clique no ícone do **Gradle** na barra lateral esquerda (elefante roxo).
2. Expanda: `missao-marte → lwjgl3 → Tasks → application`.
3. Clique duas vezes em **run**.

```
GRADLE PROJECTS
└── missao-marte
    ├── core
    └── lwjgl3
        └── Tasks
            └── application
                └── run  ← clique duas vezes aqui
```

### Resultado esperado

Uma janela 800×600 com o jogo aparece na tela.

---

## 3.8 Estrutura do Projeto gerada

```
missao-marte/
├── build.gradle           ← configuração raiz (versões)
├── settings.gradle        ← declara os módulos
├── gradlew.bat            ← wrapper Gradle para Windows
├── gradle/wrapper/
│   └── gradle-wrapper.properties
├── core/
│   ├── build.gradle
│   ├── assets/            ← imagens, sons (vazio por enquanto)
│   └── src/main/java/br/unifor/missaomarte/
│       ├── MissaoMarteGame.java
│       ├── model/
│       │   ├── Nave.java
│       │   ├── Asteroide.java
│       │   └── Estrela.java
│       └── screen/
│           ├── MenuScreen.java
│           ├── GameScreen.java
│           └── GameOverScreen.java
└── lwjgl3/
    ├── build.gradle
    └── src/main/java/br/unifor/missaomarte/
        └── Lwjgl3Launcher.java  ← launcher desktop
```

---

# Parte 5 – Arquitetura do Jogo

## 4.1 O Game Loop da libGDX

A libGDX usa o padrão **Game Loop** para jogos:

```
┌─────────────────────────────────────┐
│               GAME LOOP             │
│                                     │
│  ┌──────────┐     ┌──────────────┐  │
│  │  create  │────▶│    render    │  │◀── repete ~60x por segundo
│  └──────────┘     └──────┬───────┘  │
│                          │          │
│                    ┌─────▼──────┐   │
│                    │   update   │   │
│                    └────────────┘   │
└─────────────────────────────────────┘
```

- **create()** – executado uma única vez ao iniciar.
- **render()** – executado ~60 vezes por segundo.
  - Processa input do usuário.
  - Atualiza posições e estado.
  - Desenha tudo na tela.
- **dispose()** – executado ao fechar o jogo (libera memória).

## 4.2 Conceito de Screen

A libGDX organiza o jogo em **telas** (`Screen`), que funcionam como estados:

```
┌─────────────────────────────────────┐
│         MissaoMarteGame             │
│  (extends Game)                     │
│                                     │
│   setScreen(new MenuScreen())       │
│   setScreen(new GameScreen())       │
│   setScreen(new GameOverScreen())   │
└─────────────────────────────────────┘
```

Cada `Screen` tem seu próprio `render()`, facilitando a separação de responsabilidades (SRP do SOLID!).

## 4.3 Diagrama de Classes

```
ApplicationListener
        ▲
        │ implementa
      Game
        ▲
        │ estende
MissaoMarteGame
        │ usa
        ├──▶ GameScreen (implements Screen)
        │         │ usa
        │         ├──▶ Nave
        │         ├──▶ Asteroide
        │         └──▶ SpriteBatch (libGDX API)
        │
        └──▶ MenuScreen (implements Screen)
```

---

# Parte 6 – Implementação do Jogo

## 5.1 Assets – Recursos Gráficos

Crie a pasta `core/assets/` e adicione as imagens do jogo.

Para este tutorial, usaremos imagens simples. Você pode criá-las no **Paint** ou **Piskel** (pixel art online).

**Tamanhos recomendados:**


| Arquivo          | Tamanho     | Descrição      |
| ------------------ | ------------- | ------------------ |
| `nave.png`       | 64×64 px   | Nave do jogador  |
| `asteroide.png`  | 48×48 px   | Obstáculo       |
| `background.png` | 800×600 px | Fundo do espaço |

> **Dica:** Para testar sem imagens reais, a libGDX permite desenhar **formas geométricas** com `ShapeRenderer`. Veremos isso também.

## 5.2 Classe Principal – MissaoMarteGame.java

```java
package br.unifor.missaomarte;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MissaoMarteGame extends Game {

    // SpriteBatch é o "pincel" da libGDX para desenhar sprites
    // É compartilhado entre todas as telas para economizar memória
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Inicia com a tela de jogo
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        // Libera recursos da memória de vídeo (VRAM)
        batch.dispose();
    }
}
```

**O que este código demonstra sobre APIs:**

- `Game` é uma classe da **API libGDX** — você apenas **estende** ela.
- `SpriteBatch` é uma classe da **API libGDX** — você apenas **instancia** e usa.
- Você não sabe (e não precisa saber) como `SpriteBatch` funciona internamente.

## 5.3 Classe Nave – Nave.java

```java
package br.unifor.missaomarte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Nave {

    private static final float VELOCIDADE = 300f; // pixels por segundo

    private float x;
    private float y;
    private float largura;
    private float altura;
    private Texture textura;

    // Rectangle é da API libGDX: representa uma caixa de colisão
    private Rectangle bounds;

    public Nave(float x, float y) {
        this.x = x;
        this.y = y;
        this.textura = new Texture("nave.png");
        this.largura = 64;
        this.altura = 64;
        this.bounds = new Rectangle(x, y, largura, altura);
    }

    public void moverEsquerda(float delta) {
        // delta = tempo desde o último frame (em segundos)
        // Multiplicar por delta garante velocidade constante independente do FPS
        x -= VELOCIDADE * delta;
    }

    public void moverDireita(float delta) {
        x += VELOCIDADE * delta;
    }

    public void moverCima(float delta) {
        y += VELOCIDADE * delta;
    }

    public void moverBaixo(float delta) {
        y -= VELOCIDADE * delta;
    }

    public void limitarNaTela(float larguraTela, float alturaTela) {
        // Impede a nave de sair da tela
        x = Math.max(0, Math.min(x, larguraTela - largura));
        y = Math.max(0, Math.min(y, alturaTela - altura));
    }

    public void atualizar() {
        // Sincroniza a caixa de colisão com a posição da nave
        bounds.setPosition(x, y);
    }

    public void desenhar(SpriteBatch batch) {
        batch.draw(textura, x, y, largura, altura);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        textura.dispose();
    }
}
```

## 5.4 Classe Asteroide – Asteroide.java

```java
package br.unifor.missaomarte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Asteroide {

    private float x;
    private float y;
    private float velocidade;
    private float largura;
    private float altura;
    private Texture textura;
    private Rectangle bounds;

    public Asteroide(float x, float y, float velocidade) {
        this.x = x;
        this.y = y;
        this.velocidade = velocidade;
        this.textura = new Texture("asteroide.png");
        this.largura = 48;
        this.altura = 48;
        this.bounds = new Rectangle(x, y, largura, altura);
    }

    public void atualizar(float delta) {
        // O asteroide cai de cima para baixo
        y -= velocidade * delta;
        bounds.setPosition(x, y);
    }

    public boolean saiu(float alturaTela) {
        // Verifica se o asteroide saiu da tela pela parte de baixo
        return y + altura < 0;
    }

    public void desenhar(SpriteBatch batch) {
        batch.draw(textura, x, y, largura, altura);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        textura.dispose();
    }
}
```

## 5.5 Tela Principal – GameScreen.java

```java
package br.unifor.missaomarte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

    private final MissaoMarteGame jogo;

    private OrthographicCamera camera;
    private Nave nave;
    private Array<Asteroide> asteroides;
    private Texture background;
    private BitmapFont fonte;

    private int pontuacao;
    private boolean gameOver;
    private long ultimoAsteroide; // timestamp do último asteroide criado

    private static final float INTERVALO_ASTEROIDE = 1.0f; // segundos
    private float tempoDesdeUltimoAsteroide;

    public GameScreen(MissaoMarteGame jogo) {
        this.jogo = jogo;
    }

    @Override
    public void show() {
        // OrthographicCamera: câmera 2D da libGDX
        // Define a "janela de visão" do mundo do jogo
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        // Carrega o fundo
        background = new Texture("background.png");

        // Cria a nave no centro inferior da tela
        nave = new Nave(800 / 2f - 32, 60);

        // Array da libGDX: mais eficiente que ArrayList para jogos
        asteroides = new Array<>();

        // Fonte padrão da libGDX para exibir texto
        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE);

        pontuacao = 0;
        gameOver = false;
        tempoDesdeUltimoAsteroide = 0;

        // Cria o primeiro asteroide
        criarAsteroide();
    }

    @Override
    public void render(float delta) {
        // 1. Limpar a tela (cor de fundo preta)
        ScreenUtils.clear(0, 0, 0.1f, 1);

        // 2. Atualizar a câmera
        camera.update();
        jogo.batch.setProjectionMatrix(camera.combined);

        // 3. Processar input (se não for game over)
        if (!gameOver) {
            processarInput(delta);
            atualizarAsteroides(delta);
            verificarColisoes();
            pontuacao++;
        }

        // 4. Desenhar tudo
        jogo.batch.begin();
            // Fundo
            jogo.batch.draw(background, 0, 0, 800, 600);
            // Nave
            nave.desenhar(jogo.batch);
            // Asteroides
            for (Asteroide a : asteroides) {
                a.desenhar(jogo.batch);
            }
            // HUD – pontuação e mensagens
            fonte.draw(jogo.batch, "Pontos: " + pontuacao, 10, 590);
            if (gameOver) {
                fonte.draw(jogo.batch, "GAME OVER! Pressione R para reiniciar", 200, 300);
            }
        jogo.batch.end();

        // 5. Verificar reinício
        if (gameOver && Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            jogo.setScreen(new GameScreen(jogo));
            dispose();
        }
    }

    private void processarInput(float delta) {
        // Gdx.input é a API de entrada da libGDX
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) {
            nave.moverEsquerda(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            nave.moverDireita(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)    || Gdx.input.isKeyPressed(Input.Keys.W)) {
            nave.moverCima(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            nave.moverBaixo(delta);
        }

        nave.limitarNaTela(800, 600);
        nave.atualizar();
    }

    private void atualizarAsteroides(float delta) {
        // Verifica se é hora de criar um novo asteroide
        tempoDesdeUltimoAsteroide += delta;
        if (tempoDesdeUltimoAsteroide >= INTERVALO_ASTEROIDE) {
            criarAsteroide();
            tempoDesdeUltimoAsteroide = 0;
        }

        // Atualiza e remove asteroides que saíram da tela
        Array<Asteroide> paraRemover = new Array<>();
        for (Asteroide a : asteroides) {
            a.atualizar(delta);
            if (a.saiu(600)) {
                paraRemover.add(a);
            }
        }

        for (Asteroide a : paraRemover) {
            a.dispose();
            asteroides.removeValue(a, true);
        }
    }

    private void criarAsteroide() {
        // MathUtils.random é da libGDX: gerador de números aleatórios
        float x = MathUtils.random(0, 800 - 48);
        float velocidade = MathUtils.random(100, 300);
        asteroides.add(new Asteroide(x, 600, velocidade));
    }

    private void verificarColisoes() {
        Rectangle boundsNave = nave.getBounds();

        for (Asteroide a : asteroides) {
            // Rectangle.overlaps() é a detecção de colisão da libGDX
            if (boundsNave.overlaps(a.getBounds())) {
                gameOver = true;
                return;
            }
        }
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        background.dispose();
        nave.dispose();
        fonte.dispose();
        for (Asteroide a : asteroides) {
            a.dispose();
        }
        asteroides.clear();
    }

    // Métodos obrigatórios da interface Screen (não usados neste exemplo)
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
}
```

## 5.6 Launcher Desktop – Lwjgl3Launcher.java

```java
package br.unifor.missaomarte;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Lwjgl3Launcher {

    public static void main(String[] args) {
        // Configurações da janela desktop
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Missão Marte Unifor");
        config.setWindowedMode(800, 600);
        config.setForegroundFPS(60);
        config.setResizable(false);

        // Cria a aplicação: libGDX cuida do loop de eventos
        new Lwjgl3Application(new MissaoMarteGame(), config);
    }
}
```

---

# Parte 7 – Alternativa sem Imagens: ShapeRenderer

Se você ainda não tem imagens, pode usar `ShapeRenderer` para desenhar formas geométricas:

```java
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

// Na GameScreen:
private ShapeRenderer shapes;

@Override
public void show() {
    shapes = new ShapeRenderer();
    // ...
}

@Override
public void render(float delta) {
    ScreenUtils.clear(0, 0, 0.1f, 1);
    camera.update();

    // Desenhar formas (sem SpriteBatch)
    shapes.setProjectionMatrix(camera.combined);
    shapes.begin(ShapeRenderer.ShapeType.Filled);

    // Fundo estrelas (círculos brancos pequenos)
    shapes.setColor(Color.WHITE);
    // ... código para estrelas

    // Nave (triângulo azul)
    shapes.setColor(Color.CYAN);
    shapes.triangle(
        nave.getX() + 32, nave.getY() + 64,  // topo
        nave.getX(),       nave.getY(),        // base esquerda
        nave.getX() + 64,  nave.getY()         // base direita
    );

    // Asteroide (círculo laranja)
    shapes.setColor(Color.ORANGE);
    for (Asteroide a : asteroides) {
        shapes.circle(a.getX() + 24, a.getY() + 24, 24);
    }

    shapes.end();

    // HUD com texto ainda precisa do SpriteBatch
    jogo.batch.begin();
    fonte.draw(jogo.batch, "Pontos: " + pontuacao, 10, 590);
    jogo.batch.end();
}

@Override
public void dispose() {
    shapes.dispose();
    // ...
}
```

---

# Parte 8 – Configuração do build.gradle

## 7.1 settings.gradle (raiz)

```groovy
rootProject.name = 'missao-marte'

// Lista todos os módulos do projeto
include 'core'
include 'lwjgl3'
```

## 7.2 build.gradle (raiz)

```groovy
// Versão da libGDX usada em todos os módulos
ext {
    gdxVersion = '1.12.1'
    roboVMVersion = '2.3.21'
    box2DLightsVersion = '1.5'
    javaVersion = '17'
}

// Configurações aplicadas a TODOS os módulos
allprojects {
    apply plugin: 'java'

    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion

    repositories {
        mavenCentral()
        maven { url 'https://s01.oss.sonatype.org' }
        google()
    }
}
```

## 7.3 core/build.gradle

```groovy
// O módulo core contém a lógica do jogo
// NÃO tem dependência de plataforma (desktop, android etc.)
dependencies {
    // API principal da libGDX (independente de plataforma)
    implementation "com.badlogicgames.gdx:gdx:$gdxVersion"
}
```

## 7.4 lwjgl3/build.gradle

```groovy
apply plugin: 'application'

// O módulo desktop DEPENDE do core
dependencies {
    implementation project(':core')

    // Backend desktop da libGDX (LWJGL3 = janela, OpenGL no desktop)
    implementation "com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion"
    implementation "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"
}

application {
    mainClass = 'br.unifor.missaomarte.Lwjgl3Launcher'
}

// Configura o caminho dos assets (imagens, sons etc.)
sourceSets.main.resources.srcDirs = ['../core/assets']
```

---

# Parte 9 – Executando e Testando

## 8.1 Executar via Terminal Integrado do VS Code

1. Abra o VS Code na pasta `missao-marte`.
2. Abra o terminal: `Ctrl + `` ` `` ` (ou **Terminal → New Terminal**).
3. Execute:

```bat
.\gradlew.bat lwjgl3:run
```

Na primeira execução o Gradle faz o download das dependências (~50 MB). Aguarde — as execuções seguintes são muito mais rápidas graças ao cache.

## 8.2 Executar via Painel Gradle do VS Code

1. Clique no ícone do **Gradle** (elefante roxo) na barra lateral.
2. Navegue até: `missao-marte → lwjgl3 → Tasks → application → run`.
3. Clique duas vezes em **run**.

## 8.3 Depurar (Debug) no VS Code

1. Pressione `F5` ou vá em **Run → Start Debugging**.
2. Se solicitado, selecione **Java** como ambiente.
3. O VS Code para em breakpoints definidos no código.

> Para definir um breakpoint, clique na margem esquerda do editor ao lado do número da linha.

## 8.4 Controles do Jogo


| Tecla       | Ação                                |
| ------------- | --------------------------------------- |
| `←` ou `A` | Mover esquerda                        |
| `→` ou `D` | Mover direita                         |
| `↑` ou `W` | Mover para cima                       |
| `↓` ou `S` | Mover para baixo                      |
| `P`         | Pausar / Retomar                      |
| `ESC`       | Voltar ao menu                        |
| `ENTER`     | Confirmar / Jogar novamente           |
| `M`         | Menu principal (na tela de Game Over) |

---

# Parte 10 – Exercícios de Fixação

## Exercício 1 – API na Prática ⭐

Abra o arquivo `GameScreen.java` e liste **5 classes da libGDX** que você utilizou. Para cada uma, explique com suas palavras **o que ela faz** e **por que é uma API** (o que ela esconde de você).

## Exercício 2 – Gradle ⭐⭐

1. Adicione a dependência `gdx-freetype` ao `build.gradle` do módulo `core`:

```groovy
implementation "com.badlogicgames.gdx:gdx-freetype:$gdxVersion"
```

2. Execute `gradlew core:dependencies` e observe o download da dependência.
3. Explique a diferença entre `implementation` e `api` como escopos de dependência no Gradle.

## Exercício 3 – Mecânica de Jogo ⭐⭐

Adicione ao jogo:

- **Vidas:** A nave começa com 3 vidas. Cada colisão perde uma vida. O Game Over só ocorre quando as vidas chegam a zero.
- Exiba as vidas na HUD.

## Exercício 4 – Design Patterns ⭐⭐⭐

O jogo atual cria asteroides diretamente com `new Asteroide(...)`. Isso viola o **OCP** (não é possível criar novos tipos de obstáculos facilmente).

Refatore usando o padrão **Factory Method**:

```java
public abstract class ObstaculoFactory {
    public abstract Obstaculo criar(float x, float y);
}

public class AsteroideFactory extends ObstaculoFactory {
    @Override
    public Obstaculo criar(float x, float y) {
        return new Asteroide(x, y, MathUtils.random(100, 300));
    }
}

public class MeteoritoFactory extends ObstaculoFactory {
    @Override
    public Obstaculo criar(float x, float y) {
        return new Meteorito(x, y, 400); // muito rápido!
    }
}
```

## Exercício 5 – Arquitetura ⭐⭐⭐

O `GameScreen` atual tem muitas responsabilidades (SRP violado!). Refatore dividindo em:

- `GameScreen` – apenas renderização e leitura de input.
- `GameLogic` – atualização de asteroides, colisões e pontuação.
- `HUD` – desenho de informações na tela.

---

# Resumo Conceitual

```
┌─────────────────────────────────────────────────────────┐
│                    O QUE APRENDEMOS                     │
├─────────────────────────────────────────────────────────┤
│  API       = Interface pronta para usar, sem ver por    │
│              dentro. libGDX é a API de jogos 2D/3D.     │
├─────────────────────────────────────────────────────────┤
│  Gradle    = Automação de build. Baixa dependências,    │
│              compila e executa com um único comando.    │
├─────────────────────────────────────────────────────────┤
│  libGDX    = API que implementa:                        │
│              - Game Loop (create/render/dispose)        │
│              - Renderização de sprites (SpriteBatch)    │
│              - Leitura de input (Gdx.input)             │
│              - Detecção de colisão (Rectangle.overlaps) │
│              - Câmera 2D (OrthographicCamera)           │
├─────────────────────────────────────────────────────────┤
│  Projeto   = Multi-módulo Gradle:                       │
│  Structure   core/ → lógica independente de plataforma  │
│              lwjgl3/ → launcher desktop                 │
└─────────────────────────────────────────────────────────┘
```

---

# Referências

- [libGDX – Documentação oficial](https://libgdx.com/wiki/)
- [gdx-liftoff – Gerador de projetos](https://github.com/libgdx/gdx-liftoff)
- [Gradle – Guia para iniciantes](https://docs.gradle.org/current/userguide/getting_started_eng.html)
- [libGDX – Game Screen Tutorial](https://libgdx.com/wiki/start/a-simple-game)
- [OpenGL ES para Android](https://developer.android.com/develop/ui/views/graphics/opengl)
