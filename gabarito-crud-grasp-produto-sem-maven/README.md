# Gabarito CRUD GRASP (sem Maven)

Exemplo em Java puro (sem Maven e sem bibliotecas externas), com:

- menu textual
- CRUD de Produto e TipoProduto
- persistencia em JSON (manual)

## Requisitos

- JDK 11+ no PATH (`javac` e `java`)

## Compilar

No terminal, dentro desta pasta:

**Linux / macOS (bash):**
```bash
javac -d out src/feira/graspcrud/Main.java src/feira/graspcrud/controller/*.java src/feira/graspcrud/domain/*.java src/feira/graspcrud/dto/*.java src/feira/graspcrud/exception/*.java src/feira/graspcrud/repository/*.java src/feira/graspcrud/repository/json/*.java src/feira/graspcrud/service/*.java src/feira/graspcrud/util/*.java
```

**Windows (Prompt de Comando):**
```cmd
javac -d out src\feira\graspcrud\Main.java src\feira\graspcrud\controller\*.java src\feira\graspcrud\domain\*.java src\feira\graspcrud\dto\*.java src\feira\graspcrud\exception\*.java src\feira\graspcrud\repository\*.java src\feira\graspcrud\repository\json\*.java src\feira\graspcrud\service\*.java src\feira\graspcrud\util\*.java
```

**Windows (PowerShell):**
```powershell
javac -d out (Get-ChildItem -Recurse -Filter "*.java" src).FullName
```

## Executar

```bash
java -cp out feira.graspcrud.Main
```

Arquivos gerados:

- `data/tipos-produto.json`
- `data/produtos.json`

## Estrutura do projeto e padrões GRASP

| Pacote | Classe | Papel GRASP |
|---|---|---|
| `feira.graspcrud` | `Main` | Bootstrap / Criador das dependencias |
| `controller` | `ProdutoController` | **Controller** — recebe entrada do usuario e delega |
| `domain` | `Produto`, `TipoProduto` | **Information Expert** — validam suas proprias regras |
| `dto` | `ProdutoRequest`, `TipoProdutoRequest` | Objetos de transferencia de dados (entrada) |
| `exception` | `RegraNegocioException` | Excecao de dominio |
| `repository` | `ProdutoRepository`, `TipoProdutoRepository` | **Protected Variations** — interfaces de persistencia |
| `repository.json` | `ProdutoRepositoryJson`, `TipoProdutoRepositoryJson` | **Pure Fabrication** + **Indirection** — implementacao JSON |
| `service` | `ProdutoService`, `TipoProdutoService` | Casos de uso — **Low Coupling** via interfaces |
| `util` | `JsonMini` | **Pure Fabrication** — leitura/escrita JSON sem bibliotecas |
