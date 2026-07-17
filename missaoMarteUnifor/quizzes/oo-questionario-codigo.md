# Questionário de Programação Orientada a Objetos - Interpretação de Código

## Instruções
- Leia cada trecho de código Java e escolha a alternativa correta.
- Todas as informações necessárias para responder estão contidas no próprio enunciado.

---

## 1) Interpretação de classe e objeto

Considere o seguinte código em Java:

```java
public class Nave {
    private int x;

    public Nave(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }
}
```

Qual é o valor retornado por `new Nave(5).getX()`?

A) 5
B) 0
C) Erro de compilação
D) null

---

## 2) Comportamento de construtor

Considere:

```java
public class Pessoa {
    private String nome;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
```

Se for criada a instância `new Pessoa("Ana")`, qual será o valor de `getNome()`?

A) Ana
B) nome
C) null
D) Erro em tempo de execução

---

## 3) Encapsulamento

Considere este trecho:

```java
public class Conta {
    private double saldo;

    public Conta(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }
}
```

O que ocorre ao tentar acessar `conta.saldo` diretamente de outra classe?

A) O código não compila, porque o atributo é privado
B) O código compila e retorna o saldo
C) O código compila, mas o saldo fica vazio
D) O código gera um erro de execução

---

## 4) Herança

Considere:

```java
class Animal {
    public void emitirSom() {
        System.out.println("Som");
    }
}

class Cachorro extends Animal {
    public void emitirSom() {
        System.out.println("Au au");
    }
}
```

Se o objeto `Animal a = new Cachorro();` for criado, qual será o resultado de `a.emitirSom()`?

A) Au au
B) Som
C) Erro de compilação
D) Nenhuma saída

---

## 5) Polimorfismo

Considere:

```java
public class Professor extends Pessoa {
    public Professor(String nome) {
        super(nome);
    }
}
```

Se `Pessoa p = new Professor("Carlos");`, qual é a afirmação correta?

A) `p` é um objeto da classe Professor tratado como Pessoa
B) `p` não pode ser criado porque Professor não herda de Pessoa
C) `p` é do tipo `Professor` apenas
D) `p` não tem acesso aos atributos de Pessoa

---

## 6) Composição

Considere:

```java
public class Motor {
    public void ligar() {
        System.out.println("Motor ligado");
    }
}

public class Carro {
    private Motor motor = new Motor();

    public void ligarCarro() {
        motor.ligar();
    }
}
```

Qual relação existe entre `Carro` e `Motor`?

A) Composição, porque o carro possui um motor
B) Herança, porque o carro é um tipo de motor
C) Associação sem dependência
D) Nenhuma relação

---

## 7) Uso de listas

Considere:

```java
List<String> nomes = new ArrayList<>();
nomes.add("Ana");
nomes.add("Bruno");
System.out.println(nomes.size());
```

Qual será a saída?

A) 2
B) 1
C) Ana
D) Bruno

---

## 8) Estrutura condicional

Considere:

```java
int idade = 18;
if (idade >= 18) {
    System.out.println("Maior de idade");
} else {
    System.out.println("Menor de idade");
}
```

Qual será a saída?

A) Maior de idade
B) Menor de idade
C) Nenhuma saída
D) Erro de compilação

---

## 9) Laço de repetição

Considere:

```java
for (int i = 0; i < 3; i++) {
    System.out.println(i);
}
```

Quais valores serão impressos?

A) 0, 1 e 2
B) 1, 2 e 3
C) 0, 1, 2 e 3
D) Nenhum valor

---

## 10) Método e retorno

Considere:

```java
public int somar(int a, int b) {
    return a + b;
}
```

Se a chamada for `somar(4, 6)`, qual valor será retornado?

A) 10
B) 4
C) 6
D) 46
