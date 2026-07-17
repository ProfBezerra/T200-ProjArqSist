# Questionário de Programação Orientada a Objetos

## Instruções
- Responda cada questão selecionando a alternativa correta.
- Todas as informações necessárias para responder estão incluídas na própria questão.

---

## 1. Conceitos de OO

### 1) Em um sistema desenvolvido em Java, uma classe é:
A) Um molde usado para criar objetos com atributos e comportamentos definidos
B) Um tipo de dado primitivo, como int ou boolean
C) Um comando do terminal usado para executar programas
D) Um arquivo de configuração usado para armazenar valores

### 2) Considere um objeto criado a partir de uma classe. Qual das alternativas melhor descreve esse objeto?
A) Uma instância da classe, com estado próprio e comportamentos definidos
B) Uma variável simples que armazena apenas números
C) Um método estático sem retorno
D) Um pacote sem classes internas

### 3) Em um projeto orientado a objetos, o principal objetivo do encapsulamento é:
A) Esconder detalhes internos de implementação e expor apenas o necessário para uso externo
B) Fazer com que a classe tenha o maior número possível de atributos
C) Impedir o uso de métodos públicos em qualquer situação
D) Substituir o uso de herança por composição em todos os casos

### 4) Em Java, qual modificador normalmente é usado para impedir que um atributo seja acessado diretamente fora da classe?
A) private
B) public
C) protected
D) static

### 5) Em Java, um construtor é:
A) Um método especial chamado automaticamente ao criar um objeto, para inicializar seus valores
B) Um método que sempre retorna um valor booleano
C) Um tipo de interface usada para definir contratos
D) Uma instrução usada para importar bibliotecas externas

---

## 2. Herança, Polimorfismo e Composição

### 6) Considere uma classe Pai e uma classe Filha que herda dela. Qual afirmação é correta?
A) A classe Filha recebe características da classe Pai e pode adicionar ou sobrescrever comportamentos
B) A herança elimina a necessidade de usar polimorfismo
C) A classe Filha não pode ter métodos próprios
D) A herança só pode ser usada com interfaces

### 7) Suponha que em um sistema exista uma classe Passageiro e duas subclasses chamadas Professor e Engenheiro. Nesse cenário, Professor e Engenheiro representam:
A) Classes especializadas que herdam de Passageiro
B) Objetos concretos da classe Nave
C) Tipos de asteroides do jogo
D) Métodos estáticos sem estado

### 8) O polimorfismo em programação orientada a objetos permite que:
A) Objetos de tipos diferentes sejam tratados de forma uniforme quando compartilham uma classe ou interface comum
B) Uma classe herde de várias classes ao mesmo tempo
C) Métodos sejam definidos como privados em todas as classes
D) Pacotes sejam importados automaticamente pelo compilador

### 9) Em Java, a instrução instanceof é usada para:
A) Verificar se um objeto é uma instância de uma determinada classe
B) Criar um novo objeto na memória
C) Definir um pacote de classes
D) Substituir um construtor da classe

### 10) A composição é um conceito em que:
A) Uma classe é formada por outras classes, como uma classe Missao que contém uma classe Nave
B) Uma classe herda todos os atributos de outra classe
C) Uma classe não pode ter atributos nem métodos
D) Um objeto é criado sem nenhum estado interno

---

## 3. Projeto de um Jogo em Console

### 11) Considere um jogo em console no qual existe uma classe Missao e outra classe Passageiro. A relação entre essas classes é um exemplo de:
A) Composição, porque a missão é formada por vários passageiros
B) Herança múltipla, porque uma classe herda de várias outras
C) Encapsulamento incorreto, porque os dados ficam expostos
D) Polimorfismo estático, porque os objetos são tratados de forma fixa

### 12) Em um jogo como esse, qual é o papel principal da classe Missao?
A) Organizar e controlar a interação entre nave, passageiros, asteroides e inimigos
B) Definir apenas a aparência visual do mapa
C) Representar somente uma lista de comandos digitados pelo jogador
D) Armazenar apenas os dados de pontuação do ranking

### 13) Em uma classe Missao, o método verificaColisao() tem como função principal:
A) Detectar se a nave colidiu com asteroides ou inimigos
B) Gerar novos passageiros aleatoriamente
C) Salvar informações do ranking em um arquivo
D) Ler os comandos digitados pelo jogador no teclado

### 14) Em um sistema em que uma classe guarda vários passageiros em uma coleção, o uso de List<Passageiro> indica que:
A) A coleção pode armazenar vários passageiros de forma dinâmica
B) A nave pode transportar apenas um passageiro por vez
C) Os passageiros não podem ser embarcados na nave
D) O jogo não precisa de herança para funcionar

### 15) Em Java, o papel principal dos getters é:
A) Permitir o acesso controlado a valores de atributos privados
B) Criar objetos automaticamente para a classe
C) Substituir a necessidade de construtores
D) Evitar o uso de listas e coleções

---

## 4. Java e Ambiente de Desenvolvimento

### 16) Em uma classe Java, quando escrevemos this.nome = nome, isso significa que:
A) O atributo da classe recebe o valor do parâmetro recebido pelo método ou construtor
B) O método vira estático automaticamente
C) O objeto criado é destruído imediatamente
D) A classe passa a herdar de outra classe

### 17) Para ler dados digitados pelo usuário em um programa Java, o conceito fundamental é:
A) Usar a classe Scanner para capturar a entrada do teclado
B) Usar arquivos XML como entrada principal
C) Usar herança múltipla para ler valores
D) Usar interfaces sem implementação para capturar dados

### 18) Em relação ao ambiente Java, qual afirmação está correta?
A) O JDK contém ferramentas para desenvolver programas Java, enquanto o JRE é usado para executá-los
B) O JDK é usado apenas para executar programas Java
C) O JRE contém o compilador javac
D) O JDK e o JRE são exatamente a mesma coisa

### 19) Em Java, o comando javac é usado para:
A) Compilar arquivos fonte .java em bytecode executável pela JVM
B) Executar programas Java sem necessidade de compilação
C) Criar arquivos Markdown automaticamente
D) Gerar diagramas UML a partir do código

### 20) Ao separar um sistema em classes como Nave, Passageiro e Missao, o principal benefício é:
A) Melhor organização, manutenção e entendimento do código
B) Menor capacidade de reutilização de código
C) Eliminação de qualquer tipo de interação entre objetos
D) Redução do número de arquivos do projeto
