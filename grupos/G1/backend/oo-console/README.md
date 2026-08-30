Missão Marte — Exemplo OO (console)
=====================================

Este é um exemplo minimalista para aplicar conceitos de Orientação a Objetos no projeto "Missão Marte Unifor".

Conteúdo:

- `src/missao` — código fonte Java (classes: `Nave`, `Passageiro`, `Professor`, `Engenheiro`, `Asteroide`, `Missao`, `Main`).

Compilar e executar (a partir da raiz do repositório):

```bash
javac -g -d out src/missao/*.java
java -cp out missao.Main
```

Geração de documentação (Javadoc):

```bash
"C:\Program Files\Java\jdk-21\bin\javadoc.exe" -d docs -encoding UTF-8 -charset UTF-8 -sourcepath src missao
```

- Os arquivos HTML serão gerados em `docs/` (abra `docs/index.html`).

Depuração (opções):

- Console com `jdb` (depurador CLI):

```powershell
& 'C:\Program Files\Java\jdk-21\bin\jdb.exe' -classpath out missao.Main
# ou execute com caminho completo para jdb.exe se não estiver no PATH
```

- VS Code: instale o `Extension Pack for Java`, abra o projeto e use Run and Debug. Um `launch.json` de exemplo está descrito em `VSCODE-JAVA-DEBUG.md`.

Descrição rápida do jogo em console:

- Comandos: `w` (up), `s` (down), `a` (left), `d` (right), `c` (embarcar se houver passageiro na mesma posição), `q` (sair).
- Objetivo: embarcar todos os passageiros sem colidir com asteroides.

Observações e recomendações:

- Compile sempre com `-g` para obter informações de depuração (linhas/variáveis).
- O ranking é salvo em `ranking.json` no diretório de trabalho. Se quiser ignorar esse arquivo no Git, adicione-o ao `.gitignore` e remova do índice com:

```bash
git rm --cached ranking.json
git commit -m "Remove runtime ranking from tracking"
```

Para detalhes sobre como debugar com VS Code e configurações recomendadas, veja `VSCODE-JAVA-DEBUG.md`.

Para documentação do código e recursos úteis, veja os links abaixo:

- Javadoc (HTML gerado): [docs/index.html](docs/index.html)
- Documentação explicativa: [DOCUMENTACAO-CODIGO.md](DOCUMENTACAO-CODIGO.md)
- Guia de depuração no VS Code: [VSCODE-JAVA-DEBUG.md](VSCODE-JAVA-DEBUG.md)
- Código-fonte: [src/missao](src/missao)

Use este projeto como ponto de partida para exercícios de refatoração (SOLID), testes e aplicação de padrões.
