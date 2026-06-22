# Agrupamento dos padrões GRASP

Aqui proponho um agrupamento prático dos princípios GRASP para facilitar o estudo e a aplicação. Os agrupamentos não são exclusivos — servem como guia mental para escolher quais padrões considerar juntos.

- **Atribuição de Responsabilidades**
  - Information Expert: colocar comportamento onde estão os dados.
  - Creator: quem deve criar instâncias quando há relação óbvia.
  - Controller: objeto que representa um caso de uso e coordena operações.

- **Organização estrutural / design para mudança**
  - Low Coupling: reduzir dependências entre classes.
  - High Cohesion: manter responsabilidades relacionadas dentro da mesma classe.
  - Protected Variations: isolar pontos que podem variar atrás de abstrações.
  - Indirection: inserir intermediários para reduzir acoplamento direto.

- **Técnicas de implementação / apoio ao design**
  - Polymorphism: usar polimorfismo para variar comportamento sem condicionais.
  - Pure Fabrication: criar classes que não pertencem ao domínio para reduzir acoplamento (ex.: repositórios, adaptadores).

Sugestões de estudo prático
- Ao analisar um caso de uso: comece pensando em `Controller` (quem orquestra), aplique `Information Expert` para localizar lógica (ex.: cálculo), e então verifique `Creator` para responsabilidades de criação.
- Ao refatorar: use `Low Coupling` e `High Cohesion` como objetivos, e recorra a `Pure Fabrication` ou `Indirection` para isolar dependências.

Referência rápida: ver `introducao.md` para o exemplo evolutivo da Feira Livre onde estes agrupamentos aparecem em prática.

Mapeamento rápido para SOLID
- **Atribuição de Responsabilidades:** ligado principalmente a `SRP` (definir responsabilidades claras) e `OCP` (organizar para extensão).
- **Organização estrutural / design para mudança:** fortemente relacionado a `DIP` e `ISP` para reduzir acoplamento e criar abstrações estáveis.
- **Técnicas de implementação / apoio:** `Polymorphism` mapeia para `OCP`/`LSP`; `Pure Fabrication` apoia `SRP` e `DIP`.
