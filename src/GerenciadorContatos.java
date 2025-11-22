// Equipe:
// 1. Alex Wendeu Cavalcante Saraiva
// 2. André Felipe da Silva Pereira
// 3. João Marcos Bezerra da Silva
// 4. José Mateus da Silva Santos

import java.util.List;

public interface GerenciadorContatos {
  void adicionarContato(Contato contato) throws ContatoExistenteException;
  Contato buscarContato(String nome) throws ContatoNaoEncontradoException;
  void removerContato(String nome) throws ContatoNaoEncontradoException;
  List<Contato> listarTodosContatos();
  
  List<Contato> listarContatosOrdenados();
  List<Contato> buscarPorDominioEmail(String dominio);

  void salvarContatosCSV(String nomeArquivo) throws java.io.IOException;
  void carregarContatosCSV(String nomeArquivo) throws java.io.IOException;
}
