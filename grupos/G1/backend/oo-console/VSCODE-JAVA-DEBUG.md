# Guia: Desenvolver e Debugar com VS Code — Missão Marte Unifor

## Requisitos

- JDK: instalar JDK 17+ (JDK 21 testado).
- VS Code: instalar o editor.
- Extensões recomendadas: `Extension Pack for Java` (Microsoft), `Debugger for Java`.

## Estrutura do projeto (referência)

- Código-fonte: src/missao
- Classe principal: src/missao/Main.java

## Compilar e executar (terminal)

- Compilar com informação de debug:

```bash
javac -g -d out src/missao/*.java
```

- Executar:

```bash
java -cp out missao.Main
```

## Debug no terminal (jdb)

1. Compile com `-g` (ver item acima).
2. Inicie o depurador:

```bash
jdb -classpath out missao.Main
```

3. Comandos úteis no `jdb`:

- `stop at missao.Main.main` — parar na entrada `main`.
- `run` — iniciar execução.
- `step` / `next` — avançar instruções.
- `locals` — listar variáveis locais.
- `print <expr>` — imprimir expressão/variável (ex: `print score`).
- `cont` — continuar até próximo breakpoint.
- `quit` — sair.

Exemplo de fluxo:

```text
stop at missao.Main.main
run
step
print nave.getX()
```

Observação: para apps interativos no console, usar `jdb` pode exigir foco no terminal e entradas manuais (Scanner).

## Debug no VS Code

1. Abra a pasta do projeto no VS Code.
2. Instale o `Extension Pack for Java`.
3. Abra `src/missao/Main.java` e coloque breakpoints clicando na margem esquerda.
4. Use a paleta **Run and Debug** → **Run (Java)** ou crie a configuração de launch.

Exemplo de `.vscode/launch.json` (copie para `.vscode/launch.json`):

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Launch Main",
      "request": "launch",
      "mainClass": "missao.Main",
      "projectName": ""
    }
  ]
}
```

- Se o VS Code não localizar automaticamente classes, defina `classPaths`:

```json
"classPaths": ["${workspaceFolder}/out"]
```

- Para executar com código compilado manualmente:

```bash
javac -g -d out src/missao/*.java
```

## Onde colocar breakpoints recomendados

- Antes do `switch` que lê o comando do usuário em `src/missao/Main.java`.
- Dentro de `desenharMapa(...)` para inspecionar estado do mapa.
- Em `Missao.embarcarPassageiroNaPosicao()` / `Nave.embarcar()` (arquivos em `src/missao`).

## Dicas práticas

- Para depuração local rápida, adicione `System.out.println(...)` em pontos críticos (movimento, colisão, boarding).
- Compile sempre com `-g` para ter variáveis e linhas mapeadas ao depurador.
- Se o app espera entrada (Scanner), use o terminal integrado do VS Code (não o console de depuração se ele não suportar entrada).

## Ignorar artefatos e arquivo de runtime

- Recomenda-se `.gitignore` com: `out/`, `*.class`, `.vscode/`, `ranking.json`, `.idea/`, `*.iml`.
- Se `ranking.json` já está comitado, rode:

```bash
git rm --cached ranking.json
git commit -m "Remove runtime ranking from tracking"
```

## Passos rápidos para começar

```bash
javac -g -d out src/missao/*.java
java -cp out missao.Main
```

Abra o projeto no VS Code, defina breakpoints e use Run and Debug → `Launch Main`.

---

Arquivo gerado pelo assistente — adaptar conforme preferir.
