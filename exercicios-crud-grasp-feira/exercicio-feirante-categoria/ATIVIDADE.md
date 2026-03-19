# Atividade Pratica - CRUD com GRASP (Feirante e CategoriaFeirante)

## Objetivo

Implementar um CRUD de Feirante no contexto de uma feira livre, aplicando principios GRASP no desenho e no codigo.

Implementacao obrigatoria: Java em modo texto (console/terminal), sem interface grafica e sem framework web.

Persistencia obrigatoria: os dados de Feirante e CategoriaFeirante devem ser salvos e carregados de arquivos JSON.

## Cenario

Voce esta implementando um modulo de gestao para uma feira livre.
Cada Feirante possui uma CategoriaFeirante associada, usada para classificacao e regras operacionais.

O sistema deve permitir:

1. Cadastrar CategoriaFeirante.
2. Cadastrar Feirante associado(a) a uma CategoriaFeirante.
3. Listar Feirante.
4. Buscar Feirante por id.
5. Atualizar dados de Feirante.
6. Remover Feirante.

## Modo de execucao obrigatorio (texto)

1. A aplicacao deve iniciar por um main Java com menu textual no terminal.
2. Entrada de dados deve ser feita por teclado (ex.: Scanner).
3. Saida deve ser exibida em texto no console.
4. Nao usar GUI (Swing/JavaFX) e nao usar API REST para esta atividade.
5. O Controller deve receber as opcoes do menu e delegar para os servicos.

## Documentacao obrigatoria no codigo

1. Todas as classes criadas para a atividade devem ter comentario de documentacao (JavaDoc) explicando responsabilidade.
2. Metodos publicos devem ter JavaDoc com objetivo, parametros e retorno (quando houver).
3. Regras de negocio importantes devem estar descritas na documentacao dos metodos de dominio/servico.
4. A documentacao sera considerada na nota final.

## Regras de negocio minimas

1. Feirante.nome e obrigatorio e deve ter ao menos 3 caracteres.
2. Feirante.categoriaFeirante e obrigatorio.
3. CategoriaFeirante.nome e obrigatorio e unico no cadastro.
4. Nao permitir remover uma CategoriaFeirante que esteja em uso por algum(a) Feirante.
5. Operacoes invalidas devem retornar mensagem clara no terminal.

## Diagrama de dominio

Arquivo do diagrama: diagrama-dominio.mmd

Visualizacao rapida (Mermaid):

```mermaid
classDiagram
  class CategoriaFeirante {
    +Long id
    +String nome
    +String descricao
  }

  class Feirante {
    +Long id
    +String nome
    +String descricao
    +Boolean ativo
    +CategoriaFeirante categoriaFeirante
  }

  CategoriaFeirante "1" <-- "0..*" Feirante : classifica
```
## Requisitos de arquitetura (GRASP)

1. Information Expert:
- Validacoes e regras de estado de Feirante devem estar no proprio Feirante.
- Regras de consistencia de CategoriaFeirante devem estar no proprio CategoriaFeirante.

2. Creator:
- Quem cria Feirante deve possuir os dados necessarios para isso (ex.: servico de aplicacao recebe DTO e instancia dominio).

3. Controller:
- Um controller recebe operacoes de entrada do menu textual (CLI) e delega para casos de uso.

4. Low Coupling + High Cohesion:
- Classes de dominio sem dependencia direta de detalhes de persistencia.
- Infraestrutura separada por repositorios/interfaces.

5. Pure Fabrication:
- Repositorios e mapeadores podem ser classes fabricadas para manter dominio limpo.

6. Indirection + Protected Variations:
- Servicos devem depender de abstracoes (FeiranteRepository, CategoriaFeiranteRepository).

## Persistencia obrigatoria em JSON

1. O repositorio deve persistir os dados em arquivo JSON local.
2. Ao iniciar a aplicacao, os dados devem ser carregados do JSON (se existir).
3. Apos criar, atualizar ou remover, o JSON deve ser atualizado.

Exemplo simples com Jackson (salvar lista em JSON):

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.nio.file.Path;
import java.util.List;

public class JsonStore {
  private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
  private final Path arquivo = Path.of("feirantes.json");

  public void salvar(List<Feirante> dados) {
    try {
      mapper.writeValue(arquivo.toFile(), dados);
    } catch (Exception e) {
      throw new RuntimeException("Erro ao salvar JSON", e);
    }
  }
}
```

## Casos de uso obrigatorios

1. Criar CategoriaFeirante.
2. Criar Feirante com CategoriaFeirante existente.
3. Listar Feirante com nome da CategoriaFeirante.
4. Atualizar campos de Feirante.
5. Excluir Feirante.
6. Excluir CategoriaFeirante (com validacao de vinculo).

Sugestao de menu textual:

1. Cadastrar CategoriaFeirante
2. Listar CategoriaFeirante
3. Cadastrar Feirante
4. Listar Feirante
5. Buscar Feirante por id
6. Atualizar Feirante
7. Excluir Feirante
8. Excluir CategoriaFeirante
9. Sair

## Criterios de aceitacao

1. CRUD funcional para Feirante e cadastro basico de CategoriaFeirante.
2. Validacoes de dominio implementadas e testaveis.
3. Dependencias invertidas por interface.
4. Controller sem regra de negocio.
5. Execucao completa em modo texto com menu funcional no terminal.
6. Persistencia em JSON funcionando para carga inicial e atualizacao dos dados.
7. Classes e metodos publicos documentados com JavaDoc de forma consistente.

## Rubrica rapida (0 a 10)

1. Funcionalidade do CRUD (0-3)
2. Aplicacao de GRASP no desenho (0-3)
3. Qualidade de codigo, legibilidade e documentacao (JavaDoc) (0-2)
4. Tratamento de validacoes e erros (0-2)

## Desafios opcionais

1. Implementar busca por termo no nome.
2. Criar estrategia de ordenacao sem if-else central.
3. Salvar entidade e subentidade em arquivos JSON separados (feirantes.json e categorias-feirante.json).

## Entrega

1. Codigo-fonte compilando e executando.
2. README curto com instrucoes de execucao.
3. Evidencias de uso (prints ou log de execucao).
4. Explicacao breve (5 a 10 linhas) de como cada principio GRASP foi aplicado.