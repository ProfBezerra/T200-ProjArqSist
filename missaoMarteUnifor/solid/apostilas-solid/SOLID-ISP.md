# Apostila – SOLID: ISP (Segregação de Interface)

![ISP – Segregação de Interface](../assets/solid/isp.svg)

**Objetivo:** Evitar interfaces inchadas; preferir contratos pequenos e coesos.

## Conceito
ISP (Interface Segregation Principle) sugere dividir interfaces para que implementadores não sejam forçados a métodos desnecessários.

## Exemplo (Missão Marte Unifor)
No tutorial, a abstração `RankingRepository` define apenas o que o sistema realmente precisa para manipular o ranking.

```java
public interface RankingRepository {
    void salvar(String nome, int pontuacao);
    List<RankingEntry> listar();
    void limpar();
}
```

## Extensões possíveis
- Se o sistema futuramente precisar separar leitura e escrita, podemos criar interfaces menores, como:
  - `RankingEscritaRepository`
  - `RankingLeituraRepository`

## Exercícios
- Veja se a interface do tutorial é pequena e específica.
- Pense em como seria dividir ainda mais a responsabilidade se o sistema crescesse.

## Checklist
- A interface não obriga implementações a métodos que não usam.
- Os contratos são curtos e representam um caso de uso específico.

## Como validar
- Se uma nova funcionalidade exigir apenas leitura, não é necessário obrigar a implementação a fornecer métodos de escrita.

## Referências
- Apostila OO (Interfaces, Baixo acoplamento)
- Projeto do tutorial: src/tutorial-exercicio10
