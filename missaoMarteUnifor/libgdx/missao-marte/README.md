# Missao Marte

Jogo desenvolvido com **LibGDX** como projeto academico da Unifor.

## Requisitos

- Java 17+ (ou versao compativel com seu ambiente Gradle)
- Gradle Wrapper (ja incluso no projeto)

## Como executar

No Windows (PowerShell/CMD):

```bash
.\gradlew.bat lwjgl3:run
```

No Linux/macOS:

```bash
./gradlew lwjgl3:run
```

## Estrutura do projeto

- `core/`: logica principal do jogo (modelos, telas, audio)
- `lwjgl3/`: launcher para desktop (LWJGL3)
- `core/assets/`: recursos do jogo (sprites, sons, fontes etc.)

## Build

Gerar build do projeto:

```bash
.\gradlew.bat build
```

## Limpeza

Remover artefatos gerados:

```bash
.\gradlew.bat clean
```

## Observacoes

- O projeto usa estrutura padrao multi-modulo do LibGDX.
- Arquivos gerados de build e IDE devem ser ignorados via `.gitignore`.
