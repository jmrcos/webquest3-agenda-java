// Equipe:
// 1. Alex Wendeu Cavalcante Saraiva
// 2. André Felipe da Silva Pereira
// 3. João Marcos Bezerra da Silva
// 4. José Mateus da Silva Santos

public class Contato {

    private String nome;
    private String telefone;
    private String email;

    public Contato(String nome, String telefone, String email) {
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
    }

    @Override
    public String toString() {
        return String.format(
            "Nome: %s | Telefone: %s | E-mail: %s",
            nome, telefone, email
        );
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone == null ? "" : telefone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail não pode ser vazio.");
        }

        String e = email.trim();

        if (!e.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("E-mail inválido: " + e);
        }

        this.email = e;
    }
}
