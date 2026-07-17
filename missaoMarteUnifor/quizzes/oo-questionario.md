# Questionário de Programação Orientada a Objetos

## Instruções
- Responda cada questão selecionando a alternativa correta.
- O conteúdo é baseado no tutorial da Missão Marte Unifor e nos conceitos de OO vistos no módulo.

---

## 1. Conceitos de OO

### 1) Em programação orientada a objetos, uma classe é:
A) Um molde para criar objetos
B) Um tipo de dado primitivo
C) Um comando de terminal
D) Um arquivo de configuração

### 2) Qual das alternativas descreve melhor um objeto?
A) Uma instância de uma classe com estado e comportamento
B) Uma variável que armazena apenas números
C) Um método estático sem retorno
D) Um pacote Java sem classes

### 3) O principal objetivo do encapsulamento é:
A) Ocultar detalhes internos de implementação e expor apenas o necessário
B) Aumentar o número de atributos por classe
C) Evitar o uso de métodos públicos
D) Substituir herança por composição

### 4) Em Java, qual modificador normalmente é usado para proteger atributos de acesso direto fora da classe?
A) private
B) public
C) protected
D) static

### 5) O que é um construtor em Java?
A) Um método especial usado para inicializar um objeto no momento da criação
B) Um método que sempre retorna um boolean
C) Um tipo de interface
D) Uma instrução para importar pacotes

---

## 2. Herança, Polimorfismo e Composição

### 6) Em relação à herança, qual afirmação está correta?
A) Uma classe filha herda características de uma classe pai
B) A herança elimina completamente a necessidade de polimorfismo
C) Uma classe filha não pode ter métodos próprios
D) Herança é usada apenas para interfaces

### 7) No tutorial, Professor e Engenheiro representam exemplos de:
A) Classes especializadas que herdam de Passageiro
B) Objetos de nave
C) Tipos de asteroides
D) Métodos estáticos

### 8) O polimorfismo permite que:
A) Objetos de tipos diferentes sejam tratados de forma uniforme quando compartilham uma interface ou classe base
B) Uma classe herde de múltiplas classes ao mesmo tempo
C) Métodos sejam privados
D) Pacotes sejam importados automaticamente

### 9) O uso de instanceof em Java serve para:
A) Verificar se um objeto é uma instância de uma determinada classe
B) Criar um objeto novo
C) Definir um pacote
D) Substituir um construtor

### 10) A composição é um conceito em que:
A) Uma classe é formada por outras classes, como uma Missao contendo uma Nave
B) Uma classe herda todos os atributos de outra
C) Uma classe não pode ter atributos
D) Um objeto é criado sem estado

---

## 3. Projeto Missão Marte Unifor

### 11) No projeto da Missão Marte, a relação entre Missao e Passageiro é um exemplo de:
A) Composição
B) Herança múltipla
C) Encapsulamento incorreto
D) Polimorfismo estático

### 12) Qual é o papel principal da classe Missao no jogo?
A) Organizar e controlar a interação entre nave, passageiros e obstáculos
B) Definir apenas a aparência do mapa
C) Representar apenas uma lista de comandos
D) Guardar apenas dados de ranking

### 13) O método verificaColisao() na classe Missao tem como função principal:
A) Detectar se a nave colidiu com asteroides ou inimigos
B) Gerar novos passageiros aleatoriamente
C) Salvar o ranking em arquivo
D) Ler dados do teclado

### 14) No jogo, o uso de List<Passageiro> indica que:
A) A missão pode armazenar vários passageiros em uma coleção dinâmica
B) A nave pode ter apenas um passageiro
C) Os passageiros não podem ser embarcados
D) O jogo não precisa de herança

### 15) Qual alternativa descreve corretamente o papel dos getters em Java?
A) Permitem acessar valores de atributos privados de forma controlada
B) Substituem a necessidade de construtores
C) Criam novos objetos automaticamente
D) Evitam o uso de listas

---

## 4. Java e Ambiente de Desenvolvimento

### 16) Em uma classe Java, o comando this.nome = nome indica que:
A) O atributo da classe recebe o valor do parâmetro do construtor ou método
B) O método é estático
C) O objeto é destruído
D) A classe herda de outra

### 17) No tutorial, qual conceito é fundamental para que o jogador possa interagir com o jogo por meio do teclado?
A) Uso de Scanner para leitura de entrada
B) Uso de arquivos XML
C) Uso de herança múltipla
D) Uso de interfaces sem implementação

### 18) Em relação ao ambiente Java, qual afirmação está correta?
A) JDK contém ferramentas para desenvolvimento, enquanto JRE é usado para executar programas Java
B) JDK é usado apenas para executar programas
C) JRE contém o compilador javac
D) JDK e JRE são a mesma coisa

### 19) Em Java, o comando javac é usado para:
A) Compilar arquivos fonte .java em bytecode executável pela JVM
B) Executar programas diretamente sem compilação
C) Criar arquivos de texto em Markdown
D) Gerar diagramas UML automaticamente

### 20) Considerando o tutorial, qual é o principal benefício de separar o código em classes como Nave, Passageiro e Missao?
A) Melhor organização, manutenção e entendimento do sistema
B) Menor capacidade de reutilização
C) Eliminação de qualquer tipo de interação entre objetos
D) Redução do número de arquivos do projeto
