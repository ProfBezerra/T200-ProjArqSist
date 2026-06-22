# Gabarito - Atividade SOLID (Feira Livre)

Projeto de referência para correção da atividade prática.

## O que foi aplicado

- SRP: responsabilidades separadas por serviço/classe.
- OCP: descontos e pagamentos extensíveis por estratégia.
- LSP: cálculo de prazo de entrega via contrato único sem quebra de substituição.
- ISP: interfaces pequenas e focadas.
- DIP: serviços de alto nível dependem de abstrações e recebem dependências por construtor.

## Estrutura

```text
atividade-solid-feira-livre-java-gabarito/
  src/feira/gabarito/
    AppMain.java
    domain/
    desconto/
    pagamento/
    entrega/
    notificacao/
    cupom/
    relatorio/
    repository/
    service/
```

## Execução

No PowerShell, dentro desta pasta:

```powershell
javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out feira.gabarito.AppMain
```
