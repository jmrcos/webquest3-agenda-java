// Equipe:
// 1. Alex Wendeu Cavalcante Saraiva
// 2. André Felipe da Silva Pereira
// 3. João Marcos Bezerra da Silva
// 4. José Mateus da Silva Santos

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class AgendaManager implements GerenciadorContatos {
    private Map<String, Contato> contatos = new HashMap<>();
    private String normalizar(String nome) {
        return nome == null ? "" : nome.trim().toLowerCase();
    }

    @Override
    public void adicionarContato(Contato contato) throws ContatoExistenteException {
        String chave = normalizar(contato.getNome());

        if (contatos.containsKey(chave)) {
            throw new ContatoExistenteException("Contato já existe: " + contato.getNome());
        }

        contatos.put(chave, contato);
    }

    @Override
    public Contato buscarContato(String nome) throws ContatoNaoEncontradoException {
        Contato c = contatos.get(normalizar(nome));
        if (c == null) {
            throw new ContatoNaoEncontradoException("Contato não encontrado: " + nome);
        }
        return c;
    }

    @Override
    public void removerContato(String nome) throws ContatoNaoEncontradoException {
        String chave = normalizar(nome);
        if (!contatos.containsKey(chave)) {
            throw new ContatoNaoEncontradoException("Contato não encontrado: " + nome);
        }
        contatos.remove(chave);
    }

    @Override
    public List<Contato> listarTodosContatos() {
        return new ArrayList<>(contatos.values());
    }

    @Override
    public List<Contato> listarContatosOrdenados() {
        List<Contato> lista = listarTodosContatos();
        lista.sort(Comparator.comparing(c -> c.getNome().toLowerCase()));
        return lista;
    }

    @Override
    public List<Contato> buscarPorDominioEmail(String dominio) {
        if (dominio == null) return Collections.emptyList();

        String dom = dominio.toLowerCase().replace("@", "");

        List<Contato> result = new ArrayList<>();

        for (Contato c : contatos.values()) {
            String email = c.getEmail().toLowerCase();
            int arroba = email.lastIndexOf('@');

            if (arroba != -1) {
                String d = email.substring(arroba + 1);
                if (d.equals(dom)) result.add(c);
            }
        }

        return result;
    }

    @Override
    public void salvarContatosCSV(String nomeArquivo) throws IOException {
        Path caminho = Paths.get(nomeArquivo);

        try (BufferedWriter bw = Files.newBufferedWriter(caminho, StandardCharsets.UTF_8)) {
            bw.write("nome;telefone;email");
            bw.newLine();

            for (Contato c : contatos.values()) {
                bw.write(
                    String.join(";",
                        c.getNome(),
                        c.getTelefone(),
                        c.getEmail()
                    )
                );
                bw.newLine();
            }
        }
    }

    @Override
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
}
