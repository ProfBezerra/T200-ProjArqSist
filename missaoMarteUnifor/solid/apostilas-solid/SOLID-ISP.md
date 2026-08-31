# Apostila – SOLID: ISP (Segregação de Interface)

![ISP – Segregação de Interface](../assets/solid/isp.svg)

**Objetivo:** Evitar interfaces inchadas; preferir contratos pequenos e coesos.

## Conceito

ISP (Interface Segregation Principle) diz que uma classe não deve depender de métodos que ela não usa.

Quando a interface cresce demais, ela obriga implementações a fornecer operações que não fazem sentido para o cliente. Isso gera acoplamento desnecessário, classes mais difíceis de manter e código “vazio” para cumprir um contrato que não era necessário.

## Exemplo no tutorial

No projeto, a abstração `RankingRepository` define apenas o que o sistema precisa para manipular o ranking:

```java
public interface RankingRepository {
    void salvar(String nome, int pontuacao);
    List<RankingEntry> listar();
    void limpar();
}
```

A classe `JogoService` usa o repositório somente para persistência da pontuação. Ele não precisa conhecer os detalhes da estrutura interna do arquivo nem da lógica de apresentação.

```java
public class JogoService {
    private final RankingRepository rankingRepository;

    public JogoService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public void registrarPontuacao(String nome, int pontos) {
        rankingRepository.salvar(nome, pontos);
    }
}
```

Isso é um contrato simples e fiel ao caso de uso do sistema.

## Anti-exemplo a evitar

```java
public interface PersistenciaJogo {
    void salvarRanking();
    void desenharMapa();
    void iniciarPartida();
    void finalizarPartida();
    List<RankingEntry> listarRanking();
}
```

Esse contrato mistura persistência, renderização e fluxo do jogo. Dessa forma, qualquer implementação seria forçada a implementar coisas que não lhe pertencem.

## Extensões possíveis

Se o sistema crescer, vale separar por intenção:

```java
public interface RankingEscritaRepository {
    void salvar(String nome, int pontuacao);
}

public interface RankingLeituraRepository {
    List<RankingEntry> listar();
}
```

Assim, cada cliente usa apenas a parte que precisa.

## Exercícios

1. Verifique se a interface do tutorial é pequena e específica.
2. Pense em como dividir `RankingRepository` em leitura e escrita, se o sistema crescer.
3. Compare manutenção de interfaces grandes e interfaces focadas.

## Checklist

- A interface não obriga implementações a métodos que não usam?
- Os contratos são curtos e representam um caso de uso específico?
- O cliente depende apenas do que realmente precisa?

## Como validar

- Se uma funcionalidade exigir apenas leitura, não é necessário obrigar a implementação a fornecer métodos de escrita.
- Isso mostra que o contrato está bem segregado.

## Referências

- Apostila OO (Interfaces, Baixo acoplamento)
- Projeto do tutorial: src/tutorial-exercicio10
