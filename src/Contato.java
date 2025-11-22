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
    this.telefone = telefone;
    setEmail(email);
  }
  @Override
  public String toString() {
    return "Nome: " + nome + " | Telefone: " + telefone + " | E-mail: " + email;
  }

  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    if (nome == null) throw new IllegalArgumentException("Nome não pode ser nulo.");
    String n = nome.trim().replaceAll("\\s+", " ");
    if (n.isEmpty()) throw new IllegalArgumentException("Nome não pode ser vazio.");
    this.nome = n;
  }
  public String getTelefone() {
    return telefone;
  }
  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    if (email == null) throw new IllegalArgumentException("E-mail não pode ser nulo.");
    String e = email.trim();
    if (!isValidEmail(e)) throw new IllegalArgumentException("E-mail inválido: " + e);
    this.email = e;
  }
  private static boolean isValidEmail(String email) {
    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
  }
}
