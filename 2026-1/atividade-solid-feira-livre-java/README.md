# Atividade Prática SOLID - Feira Livre (Projeto Isolado)

Este projeto é separado de `feira-livre-java` para evitar confusão em sala.

## Objetivo didático

Refatorar um sistema com problemas de design, aplicando SOLID por etapas:

1. SRP
2. OCP
3. ISP
4. DIP
5. LSP

## Estrutura do projeto

```text
atividade-solid-feira-livre-java/
  src/feira/problemasolid/   (código com problemas)
  src/feira/solucao/         (código que os alunos vão criar)
  ATIVIDADE.md               (roteiro principal)
```

## Roteiro oficial

Siga o arquivo `ATIVIDADE.md`.
Ele está organizado por princípio, com classes existentes, classes a criar, execução e validação por etapa.

## Execução do código original (antes da refatoração)

No PowerShell, dentro da pasta do projeto:

```powershell
javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out feira.problemasolid.AtividadeMain
```

## Execução esperada após a refatoração

Depois de concluir as etapas do roteiro:

```powershell
javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out feira.solucao.SolucaoMain
```

## Sugestão de condução em sala

1. Rodar o sistema original e discutir sintomas do design.
2. Resolver uma etapa por vez (SRP -> OCP -> ISP -> DIP -> LSP).
3. Compilar e executar ao final de cada etapa.
4. Validar no final com o checklist do `ATIVIDADE.md`.
