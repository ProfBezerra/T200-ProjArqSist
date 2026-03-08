# Como compilar e executar o exemplo GRASP (Java)

Este diretório contém um exemplo didático em Java mostrando os padrões GRASP aplicados ao domínio "Feira Livre".
A árvore de fontes está em `src/feira/grasp`.

Pré-requisitos

- JDK 11 ou superior instalado e `javac`/`java` no PATH.

Opção A — (recomendado) com Git Bash / WSL / Linux shell

1. Vá para o diretório do exemplo:

```bash
cd apostilas-grasp
```

2. Compilar todos os .java do exemplo GRASP:

```bash
javac -d out src/feira/grasp/*.java
```

3. Executar a aplicação demo:

```bash
java -cp out feira.grasp.MainGrasp
```

Opção B — PowerShell (Windows)

1. Abra PowerShell e posicione em `apostilas-grasp`:

```powershell
Set-Location -Path apostilas-grasp
```

2. Colete os arquivos .java e compile:

```powershell
$files = Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { $_.FullName }
javac -d out $files
```

3. Execute:

```powershell
java -cp out feira.grasp.MainGrasp
```

Observações

- O `MainGrasp` demonstra criação de `Pedido`, adição de itens, aplicação de desconto e pagamento via `PagamentoFactory` (seleção por `FormaPagamento`).
- O diretório `out` será criado contendo os .class compilados.
Para desenvolvimento contínuo recomendo importar o projeto num IDE (IntelliJ/VS Code com Java extensions) e/ou criar um `pom.xml`/`build.gradle` para facilitar builds.

Problemas comuns

- `javac` não encontrado: instale JDK e adicione ao PATH.
- Erro de `package`: compile a partir da raiz `apostilas-grasp` usando os comandos acima (respeita a estrutura de `package`).

Exemplo adicional: projeto `feira-livre-java` (raiz do workspace)

Se quiser compilar e executar o exemplo `feira-livre-java` que fica na raiz do repositório:

```bash
cd ..\feira-livre-java
javac -d out src/feira/*.java
java -cp out feira.Main
```

No PowerShell (Windows):

```powershell
Set-Location -Path ..\feira-livre-java
$files = Get-ChildItem -Path src\feira -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out feira.Main
```

Se quiser, eu posso gerar um `pom.xml` mínimo (Maven) para tornar a compilação/execução mais simples — quer que eu gere?
