# Agrupamento dos padrões GRASP

Aqui proponho um agrupamento prático dos princípios GRASP para facilitar o estudo e a aplicação. Os agrupamentos não são exclusivos — servem como guia mental para escolher quais padrões considerar juntos.

- **Atribuição de Responsabilidades**
  - Information Expert: colocar comportamento onde estão os dados. Ex.: `Missao.passageirosRestantes()`, `Missao.resgatar(p)`.
  - Creator: quem deve criar instâncias quando há relação óbvia. Ex.: `FabricaMissao` cria `Missao`; `JogoService` cria `RankingEntry`.
  - Controller: objeto que representa um caso de uso e coordena operações. Ex.: `GameController` orquestra `JogoService`, `IRankingRepository` e `FabricaMissao`.

- **Organização estrutural / design para mudança**
  - Low Coupling: reduzir dependências entre classes. Ex.: `JogoService` depende de `IRankingRepository` (interface), não de `RankingRepositoryArquivo`.
  - High Cohesion: manter responsabilidades relacionadas dentro da mesma classe. Ex.: `Missao` guarda apenas dados do domínio; `JogoService` concentra lógica do jogo; `MapaRenderer` faz só renderização.
  - Protected Variations: isolar pontos que podem variar atrás de abstrações. Ex.: `IRankingRepository` protege `JogoService` de mudanças na persistência.
  - Indirection: inserir intermediários para reduzir acoplamento direto. Ex.: `IRankingRepository` faz indirection entre `JogoService` e o sistema de arquivos ou API externa.

- **Técnicas de implementação / apoio ao design**
  - Polymorphism: usar polimorfismo para variar comportamento sem condicionais. Ex.: `Passageiro.getPontosValor()` em `Professor`/`Engenheiro`/`Astronauta`; `Perigo.getPenalidadePontos()` em `Asteroide`/`Inimigo`.
  - Pure Fabrication: criar classes que não pertencem ao domínio para reduzir acoplamento. Ex.: `MapaRenderer` (renderização) e `IRankingRepository`/`RankingRepositoryArquivo` (persistência).

Sugestões de estudo prático
- Ao analisar um caso de uso: comece pensando em `Controller` (quem orquestra — `GameController`), aplique `Information Expert` para localizar lógica (ex.: `Missao.passageirosRestantes()`), e então verifique `Creator` para responsabilidades de criação (`FabricaMissao`).
- Ao refatorar: use `Low Coupling` e `High Cohesion` como objetivos, e recorra a `Pure Fabrication` (`MapaRenderer`) ou `Indirection` (`IRankingRepository`) para isolar dependências.

Referência rápida: ver `introducao.md` para o exemplo evolutivo da Missão Marte onde estes agrupamentos aparecem em prática.

Mapeamento rápido para SOLID
- **Atribuição de Responsabilidades:** ligado principalmente a `SRP` (definir responsabilidades claras) e `OCP` (organizar para extensão).
- **Organização estrutural / design para mudança:** fortemente relacionado a `DIP` e `ISP` para reduzir acoplamento e criar abstrações estáveis.
- **Técnicas de implementação / apoio:** `Polymorphism` mapeia para `OCP`/`LSP`; `Pure Fabrication` apoia `SRP` e `DIP`.

