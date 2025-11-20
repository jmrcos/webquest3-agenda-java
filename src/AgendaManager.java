import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AgendaManager implements GerenciadorContatos {
  private List<Contato> contatos = new ArrayList<>();

  @Override
  public void adicionarContato(Contato contato) throws ContatoExistenteException {
    for (Contato c : contatos) {
      if (c.getNome().equalsIgnoreCase(contato.getNome())) {
        throw new ContatoExistenteException("Contato já existe: " + contato.getNome());
      }
    }
    contatos.add(contato);
  }

  @Override
  public Contato buscarContato(String nome) throws ContatoNaoEncontradoException {
    for (Contato c : contatos) {
      if (c.getNome().equalsIgnoreCase(nome)) {
        return c;
      }
    }
    throw new ContatoNaoEncontradoException("Contato não encontrado: " + nome);
  }

  @Override
  public void removerContato(String nome) throws ContatoNaoEncontradoException {
    Iterator<Contato> it = contatos.iterator();
    while (it.hasNext()) {
      Contato c = it.next();
      if (c.getNome().equalsIgnoreCase(nome)) {
        it.remove();
        return;
      }
    }
    throw new ContatoNaoEncontradoException("Contato não encontrado: " + nome);
  }

  @Override
  public List<Contato> listarTodosContatos(){
    return new ArrayList<>(contatos);
  }

  @Override
  public List<Contato> listarContatosOrdenados() {
    List<Contato> copia = new ArrayList<>(contatos);
    Collections.sort(copia, Comparator.comparing(c -> c.getNome().toLowerCase()));
    return copia;
  }

  @Override
  public List<Contato> buscarPorDominioEmail(String dominio) {
    if (dominio == null) return Collections.emptyList();
    String d = dominio.toLowerCase();
    if (d.startsWith("@")) d = d.substring(1);
    List<Contato> resultado = new ArrayList<>();
    for (Contato c : contatos) {
      String email = c.getEmail();
      if (email != null) {
        int at = email.lastIndexOf('@');
        if (at >= 0) {
          String dom = email.substring(at + 1).toLowerCase();
          if (dom.equals(d)) {
            resultado.add(c);
          }
        }
      }
    }
    return resultado;
  }

  @Override
  public void salvarContatosCSV(String nomeArquivo) throws IOException {
    Path p = Path.of(nomeArquivo);
    try (BufferedWriter writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8)) {
      for (Contato c : contatos) {
        // formato: nome;telefone;email
        String line = String.format("%s;%s;%s", escape(c.getNome()), escape(c.getTelefone()), escape(c.getEmail()));
        writer.write(line);
        writer.newLine();
      }
    }
  }

  @Override
  public void carregarContatosCSV(String nomeArquivo) throws IOException {
    Path p = Path.of(nomeArquivo);
    List<Contato> carregados = new ArrayList<>();
    if (!Files.exists(p)) {
      // arquivo não existe -> nada a carregar
      return;
    }
    try (BufferedReader reader = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) continue;
        String[] parts = line.split(";", -1);
        if (parts.length < 3) continue; // ignora linha malformada
        String nome = unescape(parts[0].trim());
        String telefone = unescape(parts[1].trim());
        String email = unescape(parts[2].trim());
        carregados.add(new Contato(nome, telefone, email));
      }
    }
    // substituir contatos atuais pelos carregados (evita duplicatas e conflitos)
    this.contatos = new ArrayList<>(carregados);
  }

  // helpers simples para escapar ';' se necessário (aqui só substitui ocorrências simples)
  private String escape(String s) {
    if (s == null) return "";
    return s.replace(";", "\\;");
  }
  private String unescape(String s) {
    if (s == null) return "";
    return s.replace("\\;", ";");
  }

}
