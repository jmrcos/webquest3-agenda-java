# Webquest III - Programação II

Trabalho apresentado ao Prof. Dr. Augusto César Ferreira de Miranda Oliveira da disciplina de Programação II 2025.2, do Curso Licenciatura em Ciências da Computação da Universidade do Estado de Pernambuco - UPE, com objetivo de fins avaliativos.

Equipe
- Alex Wendeu Cavalcante Saraiva
- André Felipe da Silva Pereira
- João Marcos Bezerra da Silva
- José Mateus da Silva Santos

Objetivo
Este projeto é uma aplicação console em Java para gerenciar uma agenda de contatos. Permite cadastrar, buscar, remover, listar e persistir contatos em arquivo CSV. Foi concebido para exercitar orientação a objetos, interfaces, exceções customizadas e operações de I/O.

Funcionalidades
- Adicionar contato (nome, telefone, e‑mail) — evita duplicatas por normalização do nome.
- Buscar contato por nome (case‑insensitive, normalizando espaços).
- Remover contato por nome.
- Listar todos os contatos.
- Listar contatos ordenados alfabeticamente pelo nome.
- Buscar contatos por domínio do e‑mail (ex.: buscar por "gmail.com" ou "@gmail.com").
- Salvar agenda em arquivo CSV (formato nome;telefone;email, UTF‑8).
- Carregar agenda de arquivo CSV (substitui a lista atual).
- Validações: nome não vazio e formato básico de e‑mail são verificados ao criar/alterar um Contato.

Estrutura do projeto (src/)
- Contato.java — classe modelo; valida nome e formato de e‑mail; getters/setters e toString().
- GerenciadorContatos.java — interface com operações esperadas (adicionar, buscar, remover, listar, CSV e buscas extras).
- AgendaManager.java — implementação da interface; mantém a lista de contatos, normaliza nomes para comparação, implementa CSV, ordenação e busca por domínio.
- ContatoExistenteException.java — exceção lançada ao tentar adicionar contato já existente.
- ContatoNaoEncontradoException.java — exceção lançada ao buscar/remover contato inexistente.
- AgendaApplication.java — classe principal com menu interativo via console (Scanner).

Formato CSV
- Cada linha: nome;telefone;email
- Codificação: UTF‑8
- Escape simples para ';' (substituído por "\;")
- Ao carregar, o arquivo substitui a lista atual de contatos (não mescla).

Validações e comparações
- Nome: trim, colapso de múltiplos espaços e não pode ficar vazio.
- E‑mail: validação por expressão regular simples (formato básico).
- Comparações de nome: normalizadas (trim + colapso de espaços + lowercase) para evitar duplicatas por diferença de maiúsculas/minúsculas ou espaços extras.

Saída esperada
- Menu interativo que permite escolher operações (adicionar, buscar, remover, listar, salvar, carregar e sair) e exibe mensagens de sucesso/erro conforme as ações.



