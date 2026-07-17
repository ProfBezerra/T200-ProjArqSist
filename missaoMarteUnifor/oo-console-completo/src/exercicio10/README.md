# Exercício 10: Desafio Final — Mini-jogo Completo

## 📋 Enunciado

Com base no exercício 9, desenvolva a versão final do jogo "Missão Marte Unifor" agregando os seguintes requisitos avançados:

- **Menu inicial interativo** com opções de iniciar missão, visualizar o ranking, resetar histórico ou sair.
- **Utilização de Enum** para representação dos níveis de dificuldade.
- **Estatísticas de fim de partida** contendo o tempo total da missão (em segundos), movimentos efetuados e recorde atual.
- **Reset do ranking** em tempo de execução.
- **Refatoração da classe `Main`** em métodos especializados e menores para melhor legibilidade e organização do fluxo de jogo.

## 🎯 Objetivo

Consolidar o aprendizado dos pilares de Orientação a Objetos, demonstrando como estruturas de controle avançadas (como enums e modularização) tornam o código sustentável, além de prover uma interface de usuário de console mais completa e robusta.

## 🔧 Modificações Realizadas

### 1. Menu Principal Interativo
Implementação de um loop de escolha no método `main` permitindo ao usuário navegar de forma clara:
```text
--- MENU PRINCIPAL ---
1. Iniciar Nova Missão
2. Visualizar Ranking Top 5
3. Resetar Histórico de Ranking
4. Sair do Jogo
----------------------
```

### 2. Enum `Dificuldade` para Segurança de Tipos (Type-Safety)
Substituição das strings brutas por um tipo enumerado formal `Dificuldade` (com valores `FACIL`, `MEDIO`, `DIFICIL`) para lidar com as configurações do mapa e de pontuação de forma segura.

### 3. Computação de Estatísticas de Partida
Cálculo da duração da missão comparando timestamps de início e fim da partida por meio de `System.currentTimeMillis()`. O resumo final exibe:
- Pontuação final obtida.
- Quantidade total de movimentos no grid marciano.
- Duração da missão em segundos.
- Feedback caso a pontuação seja o novo recorde absoluto.

### 4. Resetar Ranking
Criação de funcionalidade para deletar o arquivo persistido `ranking.json` do disco, limpando os registros locais instantaneamente.

### 5. Separação de Concerns
A classe `Main` foi limpa e modularizada em métodos menores:
- `jogarPartida`: cuida do loop ativo de movimentos e ações dentro do mapa.
- `exibirEstatisticas`: formata as métricas de tempo e pontuação.
- `resetarRanking`: manipula a exclusão do arquivo JSON.
- `criarNovaMissao` e `desenharMapa`: delegados para configuração e renderização.

## 📁 Estrutura do Projeto

```text
src/exercicio10/
├── Passageiro.java
├── Professor.java
├── Engenheiro.java
├── Astronauta.java
├── Nave.java
├── Asteroide.java
├── Inimigo.java
├── Missao.java
├── Dificuldade.java
├── Main.java
└── README.md
```

## 📚 Conceitos de OO Demonstrados

✅ **Encapsulamento e Abstração** de estado de jogo e dados do piloto.  
✅ **Polimorfismo** no cálculo de pontuação dos diferentes passageiros (`getPontuacao`).  
✅ **Composição e Delegação** de responsabilidades entre as classes `Missao`, `Nave` e perigos.  
✅ **Enumerações (Enums)** para tipagem forte de dados.  
✅ **Modularidade** através da quebra do código procedural em funções estáticas coesas.

## 🚀 Como Compilar e Executar

### Compilação

Execute a partir da pasta raiz do projeto `oo-console-completo`:

```bash
javac -d out src/exercicio10/*.java
```

### Execução

```bash
java -cp out exercicio10.Main
```

## ✅ Resultado Esperado

- ✓ O jogo apresenta um menu inicial e aceita comandos numéricos estáveis.
- ✓ O tempo total da missão é calculado e impresso com sucesso ao final.
- ✓ A limpeza de ranking funciona removendo o arquivo persistido.
- ✓ O compilador não gera avisos de erro de tipos de dificuldade.
