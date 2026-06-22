# Diretrizes para Documentação com o C4 Model

## Nível 1 – Contexto
- Escopo do sistema, atores e relações externas.
- Objetivo: comunicar rapidamente o "o quê" e "quem" interage.

## Nível 2 – Container
- Containers principais (SPA frontend, backend, banco de dados, serviços externos).
- Para cada container: responsabilidades, tecnologias, comunicação (REST/eventos), portas/protocolos.

## Nível 3 – Componente
- Principais componentes dentro do backend (ex.: controllers, services, repositories, adapters).
- Contratos e dependências entre componentes.

## Convenções e Ferramentas
- Ferramentas: PlantUML ou Structurizr, exportar para PNG/PDF.
- Nomenclatura consistente, legendas e notas sobre decisões relevantes.

## Entregas por Marco (sugerido)
- Checkpoint 1: Diagrama C4 Context + modelo de domínio (UML) resumido.
- Checkpoint 3: Diagrama C4 Container + ADRs iniciais.
- AV3: Diagrama C4 Componente + ADRs finais e justificativas.

## Checklist Rápido
- O diagrama responde claramente "quem", "o quê" e "como"?
- Containers têm responsabilidades e fronteiras bem definidas?
- Componentes mostram dependências e contratos essenciais?
- Há referência às decisões (ADRs) e trade-offs?
