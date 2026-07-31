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
- Configurar um projeto Java **multi-módulo** com Gradle.
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

| API | O que oferece |
|-----|--------------|
| `java.util.ArrayList` | Lista dinâmica de objetos |
| `java.util.Scanner` | Leitura de dados do console |
| `java.io.File` | Acesso ao sistema de arquivos |
| `javax.swing.*` | Interface gráfica desktop |
| **libGDX** | Engine de jogos 2D/3D |
| **Spring Boot** | Backend REST em Java |

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

| Item | Detalhe |
|------|---------|
| **Repositório** | https://github.com/gradle/gradle |
| **Licença** | Apache License 2.0 (open source) |
| **Linguagem** | Java, Groovy, Kotlin |
| **Website** | https://gradle.org |
| **Empresa** | Gradle Inc. (San Francisco, EUA) |

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

| Versão | Ano | Destaques |
|--------|-----|-----------|
| **1.0** | 2012 | Primeira versão estável. Suporte a Java, Groovy, Scala. |
| **2.0** | 2014 | Daemon habilitado por padrão. Melhora de performance. |
| **2.14** | 2016 | Build scan (diagnóstico visual do build). |
| **3.0** | 2016 | Composite builds (projetos que dependem de outros projetos locais). |
| **4.0** | 2017 | Nova API de Tasks com `Provider<T>` e `Property<T>` (lazy evaluation). |
| **4.6** | 2018 | Suporte nativo a testes JUnit 5. |
| **5.0** | 2018 | **Kotlin DSL estável.** Alinhamento de versões de dependências. Dependency constraints. |
| **6.0** | 2019 | Verificação de checksum de dependências. Gradle Module Metadata. |
| **6.8** | 2021 | Configuration cache (experimental). Suporte a Java 16. |
| **7.0** | 2021 | Compilação incremental do Java aprimorada. Suporte a Java 16. |
| **7.4** | 2022 | Toolchain Java estável. Builds reproduzíveis. |
| **8.0** | 2023 | **Configuration Cache estável.** Isolamento de projetos. |
| **8.4** | 2023 | Gradle Develocity integrado. |
| **8.7** | 2024 | Melhorias de performance. Build toolchain aprimorado. |
| **8.10** | 2024 | Suporte aprimorado a Java 21+. |

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

| Característica | Maven | Gradle |
|----------------|-------|--------|
| Configuração | XML (`pom.xml`) — verboso | Groovy/Kotlin DSL — conciso |
| Flexibilidade | Baixa (estrutura rígida) | Alta (lógica condicional, scripts) |
| Performance | Mais lento | Mais rápido (cache incremental) |
| Uso em Android | Não | **Padrão oficial** |
| Uso em jogos | Raro | Padrão (libGDX) |
| Curva de aprendizado | Moderada | Moderada |
| Suporte a multi-módulo | Sim | Sim (mais flexível) |
| Cache de build | Não | **Sim (Build Cache)** |

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

| | Groovy DSL | Kotlin DSL |
|--|------------|------------|
| **Arquivo** | `build.gradle` | `build.gradle.kts` |
| **Linguagem** | Groovy (dinâmica) | Kotlin (estática) |
| **Autocompletar no VS Code** | Parcial | Completo |
| **Velocidade de parse** | Mais rápido | Um pouco mais lento |
| **Verificação em tempo real** | Não | Sim (erros detectados na edição) |
| **Popularidade** | Padrão histórico | Crescente (tendência atual) |

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

| Escopo | Usado em | Exportado para quem depende? | Exemplo de uso |
|--------|----------|------------------------------|----------------|
| `implementation` | compilação + execução | ❌ Não | Dependências internas |
| `api` | compilação + execução | ✅ Sim | Dependências expostas na API pública |
| `runtimeOnly` | apenas execução | ❌ Não | Drivers JDBC, natives |
| `testImplementation` | apenas testes | ❌ Não | JUnit, Mockito |
| `compileOnly` | apenas compilação | ❌ Não | Annotations, Lombok |

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

> **Nota:** O `gradlew.bat` é o **Gradle Wrapper** — um script incluído no projeto que baixa automaticamente a versão correta do Gradle. Você **não precisa instalar o Gradle manualmente** para usar o wrapper. Veja a Parte 3 para configurar o ambiente completo.

---

# Parte 3 – Configurando o Ambiente no Visual Studio Code

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

| Campo | Valor |
|-------|-------|
| **Nome** | Extension Pack for Java |
| **Publicador** | Microsoft |
| **ID** | `vscjava.vscode-java-pack` |

Esta extensão instala automaticamente:
- Language Support for Java (compilação, autocompletar)
- Debugger for Java (depuração)
- Test Runner for Java
- Maven for Java
- Project Manager for Java

### Gradle for Java (obrigatório)

| Campo | Valor |
|-------|-------|
| **Nome** | Gradle for Java |
| **Publicador** | Microsoft |
| **ID** | `vscjava.vscode-gradle` |

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

# Parte 4 – Arquitetura do Jogo

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

# Parte 5 – Implementação do Jogo

## 5.1 Assets – Recursos Gráficos

Crie a pasta `core/assets/` e adicione as imagens do jogo.

Para este tutorial, usaremos imagens simples. Você pode criá-las no **Paint** ou **Piskel** (pixel art online).

**Tamanhos recomendados:**

| Arquivo | Tamanho | Descrição |
|---------|---------|-----------|
| `nave.png` | 64×64 px | Nave do jogador |
| `asteroide.png` | 48×48 px | Obstáculo |
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

# Parte 6 – Alternativa sem Imagens: ShapeRenderer

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

# Parte 7 – Configuração do build.gradle

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

# Parte 8 – Executando e Testando

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

| Tecla | Ação |
|-------|------|
| `←` ou `A` | Mover esquerda |
| `→` ou `D` | Mover direita |
| `↑` ou `W` | Mover para cima |
| `↓` ou `S` | Mover para baixo |
| `P` | Pausar / Retomar |
| `ESC` | Voltar ao menu |
| `ENTER` | Confirmar / Jogar novamente |
| `M` | Menu principal (na tela de Game Over) |

---

# Parte 9 – Exercícios de Fixação

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
