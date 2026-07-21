# Tutorial SOLID — Missão Marte Unifor

Este módulo reúne um tutorial prático para converter o projeto de Orientação a Objetos da Missão Marte em uma versão que siga os princípios do SOLID.

## Conteúdo

- Tutorial completo em [src/tutorial-solid/README.md](src/tutorial-solid/README.md)
- Projeto exemplo em Java em [src/tutorial-solid/src/solidtutorial](src/tutorial-solid/src/solidtutorial)

## Objetivo didático

Os alunos acompanham uma refatoração guiada em cinco etapas:

1. SRP — Single Responsibility Principle
2. OCP — Open/Closed Principle
3. LSP — Liskov Substitution Principle
4. ISP — Interface Segregation Principle
5. DIP — Dependency Inversion Principle

## Como usar

A partir da pasta da disciplina, compile e execute o exemplo:

```bash
mkdir -p out
find missaoMarteUnifor/solid/src/tutorial-solid/src/solidtutorial -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out solidtutorial.Main
```
