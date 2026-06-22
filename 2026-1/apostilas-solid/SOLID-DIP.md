# Apostila – SOLID: DIP (Inversão de Dependência)

![DIP – Inversão de Dependência](../assets/solid/dip.svg)

**Objetivo:** Depender de abstrações em vez de implementações concretas para reduzir acoplamento.

## Conceito
DIP (Dependency Inversion Principle) afirma que módulos de alto nível não devem depender de módulos de baixo nível; ambos devem depender de abstrações.

## Exemplo (Feira Livre)
- `PedidoService` depende da interface `PedidoRepository` — ver [feira-livre-java/src/feira/PedidoService.java](../feira-livre-java/src/feira/PedidoService.java).
- Implementação concreta: `PedidoRepositoryMemoria` — ver [feira-livre-java/src/feira/PedidoRepositoryMemoria.java](../feira-livre-java/src/feira/PedidoRepositoryMemoria.java).

```java
public class PedidoService {
    private final PedidoRepository repository; // abstração
    public PedidoService(PedidoRepository repository) { this.repository = repository; }
}
```

## Exercícios
- Adicione `PedidoRepositoryArquivo` (ou `PedidoRepositoryCSV`); troque a injeção na `Main` sem editar `PedidoService`.
- Demonstre que trocar infraestrutura é local e não impacta regras de negócio.

## Checklist
- Serviços dependem de interfaces?
- Implementações podem ser trocadas com impacto mínimo?

## Como validar
- Substituir a implementação de repositório não deve exigir alteração no serviço.

## Referências
- Apostila OO (Interfaces, DIP)
- Projeto: `PedidoService.java`, `PedidoRepository.java`
