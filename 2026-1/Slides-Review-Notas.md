# Revisão dos Slides – Parte Teórica

Objetivo: manter o conteúdo e fortalecer clareza, engajamento e alinhamento às entregas do semestre.

## Forças do material
- Sequência lógica: UNIDADES e progressão (SOLID/GRASP → POA → Arquitetura → Integração).
- Contextualização consistente com o tema "Feira Livre Digital".
- Atividades de discussão e exercícios pontuais já presentes.

## Melhorias sugeridas (sem alterar conteúdo)
1. Objetivos de aprendizagem por unidade
   - Adicionar, no início de cada unidade, um slide curto "Ao final desta unidade, você será capaz de..." com 3–5 bullets mensuráveis.
   - Exemplos: "Aplicar SRP e OCP em cenários reais", "Modelar responsabilidades usando GRASP", "Comparar monolito modular e microsserviços com critérios de decisão".

2. Conexão com marcos e avaliação
   - Inserir um rodapé discreto em slides-chave com "Checkpoint 1/2/3" quando for conteúdo diretamente cobrado.
   - Incluir um slide de encerramento por unidade: "Resumo + Próxima Entrega".

3. Visualização e exemplos
   - Em slides de SOLID/GRASP/Patterns, incluir um bloco "Exemplo Feira" padronizado com 1 diagrama simples (ASCII/PlantUML) para fixação visual.
   - Em Arquitetura, inserir placeholders para C4 (Context/Container/Component) ao final.

4. Interatividade leve
   - Alternar perguntas abertas com mini-tarefas de 3–5 minutos (Think–Pair–Share, identificação de smells em pseudo-código, mapeamento de responsabilidades).
   - Indicar ícones/sinais: "Discussão", "Exercício", "Demonstração" para orientar o ritmo.

5. Notas do apresentador
   - Acrescentar seções ocultas (ou blocos em itálico) com pontos-chave, exemplos e alertas de armadilhas (ex.: "herança por conveniência", "uso excessivo de Singleton").

6. Acessibilidade e consistência
   - Garantir títulos H1/H2 consistentes, contraste, e texto grande para conceitos centrais.
   - Padronizar estrutura por slide: **Contexto** → **Conceito** → **Exemplo** → **Pergunta/Exercício**.

## Microedições propostas (exemplos)
- Slide 10 – SOLID Integrado: adicionar uma tabela de conflitos comuns (SRP vs. OCP) e como resolver com composição e interfaces.
- Slide 16 – Low Coupling / High Cohesion: incluir um pequeno diagrama de dependências (módulos de domínio) e uma regra prática (n ≤ 3 dependências diretas por componente).
- Slide 22 – Strategy: ilustrar com políticas de preço (promoção, sazonal) e como injetar via configuração.
- Slide 31 – Monolito Modular: ressaltar limites de módulos e política de dependências (camada superior não chama inferior diretamente sem serviço/porta).
- Slide 33 – Serverless: sugerir 1 função candidata (ex.: cálculo de métricas diárias da feira) e discutir custo vs. previsibilidade.

## Integração com entregas (slides extras opcionais)
- "Checklist de Checkpoint 1": modelo de domínio, justificativas SOLID/GRASP, exemplos mínimos.
- "Checklist de Checkpoint 3": C4 Context/Container, ADRs iniciais e riscos.
- "Guia de AV3": resumo da rúbrica (documento arquitetural, protótipo, padrões, POA, defesa técnica).

## Template rápido de slide (copiar/colar)
```
# <Título do Slide>

**Contexto:** <por que este tema importa>

**Conceito:** <definição objetiva>

**Exemplo (Feira):** <caso curto>

**Pergunta/Exercício (3–5 min):** <atividade>
```

## Próximos passos
- Posso aplicar estes ajustes diretamente em `slides.md` mantendo o conteúdo, apenas adicionando objetivos, rodapés de checkpoints e templates de exemplo.
- Se preferir Marp (exportável para PPTX/PDF), adapto `slides.md` com frontmatter e separadores, mantendo a mesma estrutura.
