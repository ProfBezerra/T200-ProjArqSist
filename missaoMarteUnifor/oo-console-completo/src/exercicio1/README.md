# Exercício 1: Modificar atributos da nave

## Enunciado

A nave atualmente tem capacidade de 3 passageiros. Mude para 5 e recompile.

## Modificações Realizadas

### 1. Aumentar capacidade da nave
- Linha original: `new Nave("A-1", 3);`
- Linha modificada: `new Nave("A-1", 5);`

### 2. Aumentar número de passageiros no mapa
- Adicionada nova classe: `Astronauta.java`
- Alterado o loop de geração de passageiros de 3 para **5 passageiros**
- Passageiros gerados:
  1. Dr. Silva (Professor)
  2. Eng. Rosa (Engenheiro)
  3. Dr. Lima (Professor)
  4. Eng. Carlos (Engenheiro)
  5. Ast. Maria (Astronauta) ⭐ **Nova classe criada**

### 3. Atualizar visualização no mapa
- Adicionado símbolo 'T' para Astronauta
- Legenda atualizada: `N=Nave, P=Professor, E=Engenheiro, T=Astronauta, A=Asteroide, .=Vazio`

## Arquivos Criados/Modificados

| Arquivo | Status | Modificação |
|---------|--------|-------------|
| `Nave.java` | ✅ Criado | Capacidade = 5 |
| `Passageiro.java` | ✅ Criado | Classe base |
| `Professor.java` | ✅ Criado | Herda de Passageiro |
| `Engenheiro.java` | ✅ Criado | Herda de Passageiro |
| `Astronauta.java` | ✨ **Novo** | Herda de Passageiro |
| `Main.java` | ✅ Modificado | Gera 5 passageiros, atualiza legenda |
| `Missao.java` | ✅ Criado | Orquestrador do jogo |
| `Asteroide.java` | ✅ Criado | Obstáculos do mapa |

## Resultado Esperado

✅ A nave pode embarcar **5 passageiros** em vez de 3  
✅ **5 passageiros** aparecem no mapa (2 Professores, 2 Engenheiros, 1 Astronauta)  
✅ Novo tipo de passageiro (Astronauta) implementado  

## Como Compilar e Executar

### Windows (PowerShell)

```powershell
# Compilar
javac -d out src/exercicio1/*.java

# Executar
java -cp out exercicio1.Main
```

### Linux/macOS

```bash
# Compilar
javac -d out src/exercicio1/*.java

# Executar
java -cp out exercicio1.Main
```

## Teste Funcional

Ao jogar você verá:

```
    -5  -4  -3  -2  -1   0   1   2   3   4   5
    __  __  __  __  __  __  __  __  __  __  __
 -5| .   .   .   .   .   N   .   .   .   .   .
 -4| .   P   .   .   .   .   .   .   .   .   .
 -3| .   .   E   .   .   .   .   .   .   .   .
 -2| .   .   .   T   .   .   .   .   .   .   .
 -1| .   .   .   .   P   .   .   .   .   .   .
  0| .   .   .   .   .   .   .   .   .   .   .
  1| .   .   .   .   .   .   .   .   E   .   .
  2| .   .   .   .   .   .   .   .   .   A   .
  3| .   .   .   .   .   .   .   .   .   .   .
  4| .   .   .   .   .   .   .   .   .   .   A
  5| .   .   .   .   .   .   .   .   .   .   .

Legenda: N=Nave, P=Professor, E=Engenheiro, T=Astronauta, A=Asteroide, .=Vazio
```

- **N** = Sua nave (início em 0,0)
- **P** = Professores (2 no mapa)
- **E** = Engenheiros (2 no mapa)
- **T** = Astronauta (novo!)
- **A** = Asteroides (obstáculos)
- **.** = Espaço vazio

## Pontuação

- Pontos iniciais: **20**
- Cada movimento: **-1 ponto**
- Cada embarque bem-sucedido: **+10 pontos**
- Missão completa: Todos os 5 passageiros embarcados ✅

---

**Disciplina:** Programação Orientada a Objetos  
**Tutorial:** Missão Marte Unifor  
**Nível:** Iniciante  
**Conceitos:** Classes, Herança, Encapsulamento, Composição
