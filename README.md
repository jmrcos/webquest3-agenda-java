# Agenda de Contatos — Projeto

Este projeto é uma pequena aplicação console em Java que gerencia contatos (nome, telefone, e-mail). Foi desenvolvido como exercício de programação orientada a objetos, interfaces, exceções personalizadas e leitura/escrita CSV.

## Arquivos principais (src/)
- Contato.java  
  Classe que representa um contato com atributos privados (nome, telefone, email), construtor, getters/setters e `toString()`.

- GerenciadorContatos.java  
  Interface que define as operações do gerenciador:
  - adicionarContato(Contato) throws ContatoExistenteException
  - buscarContato(String) throws ContatoNaoEncontradoException
  - removerContato(String) throws ContatoNaoEncontradoException
  - listarTodosContatos()
  - listarContatosOrdenados()
  - buscarPorDominioEmail(String)
  - salvarContatosCSV(String) throws IOException
  - carregarContatosCSV(String) throws IOException

- AgendaManager.java  
  Implementação da interface. Mantém uma `List<Contato>` interna e implementa:
  - verificação de contatos existentes (por nome, case-insensitive)
  - busca e remoção com exceções personalizadas
  - listagem (normal e ordenada)
  - busca por domínio de e-mail (parte após '@')
  - salvar/carregar em CSV (formato `nome;telefone;email`) com escape simples de `;`

- ContatoExistenteException.java / ContatoNaoEncontradoException.java  
  Exceções customizadas (checked) para sinalizar tentativas de adicionar duplicatas ou operações sobre contatos inexistentes.

- AgendaApplication.java  
  Aplicação console com menu interativo via `Scanner`. Opções:
  1. Adicionar Contato
  2. Buscar Contato
  3. Remover Contato
  4. Listar Todos os Contatos
  5. Salvar em CSV
  6. Carregar de CSV
  7. Sair

  (Observação: o menu atual não inclui explicitamente "Listar ordenado" e "Buscar por domínio"; podem ser adicionados facilmente.)

## Formato CSV
- Cada linha: `nome;telefone;email`
- Codificação UTF-8
- O código faz escape simples de `;` substituindo por `\;`
- Ao carregar, linhas malformadas são ignoradas e a lista interna é substituída pelos contatos do arquivo

## Como compilar e executar (Windows)
Abra Prompt/PowerShell no diretório do projeto (`c:\Users\jmarc\Downloads\Programação II - Webquest III`) e execute:
```bat
javac -d bin src\*.java
java -cp bin AgendaApplication
```

## Comportamento e limitações importantes
- Comparações de nome são case-insensitive.
- Exceções são checked — trate ou declare `throws`.
- `carregarContatosCSV` substitui a agenda atual (não mescla). Ajuste se preferir mesclar.
- Validações (nome vazio, formato de e-mail, telefone) não são aplicadas — podem ser acrescentadas.
- Escaping CSV é simples; para casos complexos use uma biblioteca CSV (ex.: OpenCSV).

## Sugestões de melhorias
- Adicionar opções de menu para:
  - listar ordenado (usar `listarContatosOrdenados`)
  - buscar por domínio (usar `buscarPorDominioEmail`)
- Validar entradas (ex.: regex para e-mail)
- Implementar `equals`/`hashCode` em `Contato` se for usar sets ou busca por igualdade
- Implementar mesclagem ao carregar CSV ou pedir confirmação de sobrescrita

## Observações finais
O projeto cobre os requisitos fundamentais: modelagem da classe, interface, exceções personalizadas, persistência CSV e menu interativo. Posso gerar a versão do README em inglês, adicionar as opções faltantes no menu ou implementar validações se desejar.

## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
