import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AgendaManager {
  private List<Contato> contatos = new ArrayList<>();

  private String normalizeName(String nome) {
    if (nome == null) return "";
    return nome.trim().replaceAll("\\s+", " ").toLowerCase();
  }

  public void adicionarContato(Contato contato) throws ContatoExistenteException {
    String nomeNovoNorm = normalizeName(contato.getNome());
    for (Contato c : contatos) {
      if (normalizeName(c.getNome()).equals(nomeNovoNorm)) {
        throw new ContatoExistenteException("Contato já existe: " + contato.getNome());
      }
    }
    contatos.add(contato);
  }

  public Contato buscarContato(String nome) throws ContatoNaoEncontradoException {
    String nomeNorm = normalizeName(nome);
    for (Contato c : contatos) {
      if (normalizeName(c.getNome()).equals(nomeNorm)) {
        return c;
      }
    }
    throw new ContatoNaoEncontradoException("Contato não encontrado: " + nome);
  }

  public void removerContato(String nome) throws ContatoNaoEncontradoException {
    String nomeNorm = normalizeName(nome);
    Iterator<Contato> it = contatos.iterator();
    while (it.hasNext()) {
      Contato c = it.next();
      if (normalizeName(c.getNome()).equals(nomeNorm)) {
        it.remove();
        return;
      }
    }
    throw new ContatoNaoEncontradoException("Contato não encontrado: " + nome);
  }

  public List<Contato> listarTodosContatos(){
    return new ArrayList<>(contatos);
  }

  public List<Contato> listarContatosOrdenados() {
    List<Contato> copia = new ArrayList<>(contatos);
    Collections.sort(copia, Comparator.comparing(c -> c.getNome().toLowerCase()));
    return copia;
  }

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

  public void carregarContatosCSV(String filename) throws IOException {
    Path p = Paths.get(filename);
    List<String> lines = new ArrayList<>();

    if (Files.exists(p)) {
        try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
            String l;
            while ((l = br.readLine()) != null) {
                lines.add(l);
            }
        }
    } else {
        String resourceName = filename.startsWith("/") ? filename : "/" + filename;
        InputStream is = AgendaManager.class.getResourceAsStream(resourceName);

        if (is == null) {
            throw new IOException("Arquivo não encontrado: " + filename);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String l;
            while ((l = br.readLine()) != null) {
                lines.add(l);
            }
        }
    }

    contatos.clear();

    for (String line : lines) {
        if (line == null) continue;
        line = line.replace("\uFEFF", "").trim();
        if (line.isEmpty()) continue;

        String[] parts = line.split("[,;]");

        if (parts.length == 0) continue;

        if (parts[0].equalsIgnoreCase("nome")) continue;

        if (parts.length >= 3) {
            String nome = parts[0].trim();
            String telefone = parts[1].trim();
            String email = parts[2].trim();

            try {
                adicionarContato(new Contato(nome, telefone, email));
                System.out.println(nome + "; " + telefone + "; " + email);
            } catch (Exception e) {
            }
        }
    }
}

  public void salvarContatosCSV(String filename) throws IOException {
    Path p = Paths.get(filename);
    List<String> out = new ArrayList<>();
    out.add("Nome; Telefone; Email");
    for (Contato c : contatos) {
        String linha = String.format("%s;%s;%s",
            escape(c.getNome()), escape(c.getTelefone()), escape(c.getEmail()));
        out.add(linha);
    }
    Files.write(p, out, StandardCharsets.UTF_8);
  }

  private String escape(String s) {
    if (s == null) return "";
    return s.replace("\\", "\\\\")
            .replace(";", "\\;")
            .replace(",", "\\,");
  }

}
