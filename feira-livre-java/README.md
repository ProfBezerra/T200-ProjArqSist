# Feira Livre (Console Java)

Exemplo simples para reforçar conceitos de OO:
- Encapsulamento: validações em `Produto` e `PedidoItem`.
- Herança e Polimorfismo: `ProdutoOrganico` sobrescreve `getPreco()`.
- Composição: `Pedido` possui `PedidoItem`.
- DIP (Inversão de Dependência): `PedidoService` depende da interface `PedidoRepository`.
- Coesão e baixo acoplamento: responsabilidades separadas por classe.

## Estrutura
```
feira-livre-java/
  src/feira/
    Main.java
    Produto.java
    ProdutoOrganico.java
    Pedido.java
    PedidoItem.java
    PedidoRepository.java
    PedidoRepositoryMemoria.java
```

## Requisitos
- JDK 8 ou superior instalado.
- `javac` e `java` disponíveis no PATH do sistema.

Para verificar:
```powershell
java -version
javac -version
```

## Compilar e Executar (Windows)

### PowerShell
```powershell
# Ir para a pasta do projeto
cd "c:\Users\S704595683\Documents\Ensino\Unifor\T200 - 2025.1Proj.Arq. Sist\feira-livre-java"

# Compilar para a pasta out
javac -d out src/feira/*.java

# Executar
java -cp out feira.Main
```

### CMD (Prompt de Comando)
```bat
cd "c:\Users\S704595683\Documents\Ensino\Unifor\T200 - 2025.1Proj.Arq. Sist\feira-livre-java"
javac -d out src\feira\*.java
java -cp out feira.Main
```

## Uso
- Escolha `1` para adicionar produto.
- Informe nome, preço base, quantidade e se é orgânico.
- Orgânico aplica 10% de desconto via polimorfismo.
- Escolha `2` para finalizar e salvar (em memória) e ver total.
- Escolha `3` para listar os produtos cadastrados (nome, preço aplicado, quantidade e subtotal).
- `0` para sair.

## Observações Didáticas
- Preferir composição à herança. Aqui a herança é usada apenas para demonstrar polimorfismo.
- Em projetos reais, descontos/tributos podem ser estratégias (interfaces) ao invés de sobrecarga simples.

## Conceitos por Classe
- `Produto`: encapsulamento com validação de nome e preço.
- `ProdutoOrganico`: herança e polimorfismo (sobrescreve `getPreco`).
- `PedidoItem`: composição de `Produto` com quantidade; regra de subtotal.
- `Pedido`: agrega itens e calcula `total`; expõe lista imutável (coesão).
- `PedidoRepository`/`PedidoRepositoryMemoria`: contrato e implementação (DIP).
- `PedidoService`: orquestra regras de finalização e persiste via interface (baixo acoplamento).

## Características de OO (apostila) demonstradas no exemplo
- **Encapsulamento:** validações internas em `Produto` (preço e nome) e `PedidoItem` — ver [feira-livre-java/src/feira/Produto.java](src/feira/Produto.java) e [feira-livre-java/src/feira/PedidoItem.java](src/feira/PedidoItem.java). Os detalhes ficam ocultos e expostos via métodos públicos controlados.
- **Abstração:** `Pedido.total()` abstrai o cálculo do total — ver [feira-livre-java/src/feira/Pedido.java](src/feira/Pedido.java). Quem usa o pedido não precisa conhecer como os subtotais são somados.
- **Herança:** `ProdutoOrganico` especializa `Produto` — ver [feira-livre-java/src/feira/ProdutoOrganico.java](src/feira/ProdutoOrganico.java). Relação “é um”.
- **Polimorfismo:** sobreposição de `getPreco()` em `ProdutoOrganico` altera comportamento em tempo de execução, mantendo a referência do tipo `Produto`. O desconto de 10% aparece quando você marca “orgânico” no console.
- **Composição:** `Pedido` “tem” muitos `PedidoItem` — ver [feira-livre-java/src/feira/Pedido.java](src/feira/Pedido.java). Preferimos composição para flexibilidade.
- **Interfaces (contratos):** `PedidoRepository` define contrato de persistência — ver [feira-livre-java/src/feira/PedidoRepository.java](src/feira/PedidoRepository.java). Facilita substituição de implementação.
- **DIP (Inversão de Dependência):** `PedidoService` depende da interface `PedidoRepository`, não de uma classe concreta — ver [feira-livre-java/src/feira/PedidoService.java](src/feira/PedidoService.java). Baixo acoplamento.
- **Coesão:** cada classe tem uma responsabilidade clara (produto, item, pedido, serviço, persistência). Isso torna o sistema mais legível e fácil de manter.
- **Baixo acoplamento:** `PedidoService` não conhece detalhes de armazenamento; `Main` apenas orquestra, sem acessar estados internos diretamente — ver [feira-livre-java/src/feira/PedidoRepositoryMemoria.java](src/feira/PedidoRepositoryMemoria.java) como uma das possíveis implementações.

## Demonstração dos conceitos em execução
- Ao adicionar um produto orgânico, o polimorfismo aplica 10% de desconto no preço exibido.
- Ao finalizar, `PedidoService` usa `PedidoRepository` (interface) para salvar em memória, ilustrando DIP e baixo acoplamento.

---

## Mini Sistema: Feira Livre (Console)

Vamos montar um sistema mínimo, mas bem modelado e alinhado à apostila.

### Entidades
- `Produto` — preço e nome, com validação (encapsulamento) — ver [src/feira/Produto.java](src/feira/Produto.java)
- `ProdutoOrganico` — especializa `Produto` com desconto (herança/polimorfismo) — ver [src/feira/ProdutoOrganico.java](src/feira/ProdutoOrganico.java)
- `PedidoItem` — compõe `Produto` + quantidade e calcula subtotal (composição/coesão) — ver [src/feira/PedidoItem.java](src/feira/PedidoItem.java)
- `Pedido` — agrega itens e calcula total (abstração de regra de negócio) — ver [src/feira/Pedido.java](src/feira/Pedido.java)

### Serviço
- `PedidoService` — orquestra finalização e persiste via contrato (`PedidoRepository`) — ver [src/feira/PedidoService.java](src/feira/PedidoService.java)

### Classe principal
- `Main` — interface de texto para interação — ver [src/feira/Main.java](src/feira/Main.java)

### Arquitetura (conceitual)
`Main → PedidoService → Pedido → PedidoItem → Produto`

Baixo acoplamento, alta coesão, OO puro.

### Snippets principais
1) Produto (encapsulamento)
```java
public class Produto {
  private String nome;
  private double preco;
  // getters/setters com validação
}
```

2) PedidoItem (coesão)
```java
public class PedidoItem {
  private Produto produto;
  private int quantidade;
  public double subtotal() { return produto.getPreco() * quantidade; }
}
```

3) Pedido (abstração/composição)
```java
public class Pedido {
  public double total() { /* soma subtotais */ }
}
```

4) PedidoService (DIP)
```java
public class PedidoService {
  private final PedidoRepository repository; // depende de interface
  public double finalizar(Pedido pedido) { /* valida, persiste, retorna total */ }
}
```

5) Main (console)
```java
public class Main {
  public static void main(String[] args) {
    // Scanner lê entradas; cria Produto/ProdutoOrganico, adiciona ao Pedido
    // Chama PedidoService.finalizar(pedido)
  }
}
```

### Como executar (Windows)
Veja os comandos na seção acima de "Compilar e Executar". Em resumo:
```powershell
cd "c:\Users\S704595683\Documents\Ensino\Unifor\T200 - 2025.1Proj.Arq. Sist\feira-livre-java"
javac -d out src/feira/*.java
java -cp out feira.Main
```

### O que este exemplo ensina
- **Orientação a Objetos:** classes, objetos, encapsulamento, herança/polimorfismo, composição.
- **Alta coesão:** cada classe foca em uma responsabilidade clara.
- **Baixo acoplamento:** serviço depende de contrato; `Main` não conhece detalhes de cálculo/persistência.
- **Arquitetura conceitual em camadas:** `Main` (interface), `PedidoService` (serviço), `Pedido/Produto` (domínio), `PedidoRepositoryMemoria` (infra).

## Sessão de Exemplo
```
=== Feira Livre (console) ===

1) Adicionar produto  2) Finalizar pedido  0) Sair
Escolha: 1
Nome do produto: Tomate
Preço base (ex: 10.50): 10
Quantidade: 2
É orgânico? (s/n): s
Adicionado: Tomate x2  | Preço unitário aplicado: R$ 9,00

1) Adicionar produto  2) Finalizar pedido  0) Sair
Escolha: 2
Total do pedido: R$ 18,00
Pedido salvo (memória).
Encerrado.
```

## Problemas Comuns
- `javac` não encontrado: adicione o diretório do JDK ao PATH ou use o Terminal do JDK.
- Erros de digitação em preço/quantidade: o app valida e informa o erro.
