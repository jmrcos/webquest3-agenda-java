import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AgendaApplication {
    public static void main(String[] args) {
        AgendaManager manager = new AgendaManager();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("1. Adicionar Contato");
            System.out.println("2. Buscar Contato");
            System.out.println("3. Remover Contato");
            System.out.println("4. Listar Todos os Contatos");
            System.out.println("5. Salvar em CSV");
            System.out.println("6. Carregar de CSV");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            String opc = sc.nextLine().trim();

            switch (opc) {
                case "1":
                    System.out.print("Nome: ");
                    String nome = sc.nextLine().trim();
                    System.out.print("Telefone: ");
                    String tel = sc.nextLine().trim();
                    System.out.print("E-mail: ");
                    String email = sc.nextLine().trim();
                    try {
                        manager.adicionarContato(new Contato(nome, tel, email));
                        System.out.println("Contato adicionado.");
                    } catch (ContatoExistenteException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.print("Nome para buscar: ");
                    String buscar = sc.nextLine().trim();
                    try {
                        Contato c = manager.buscarContato(buscar);
                        System.out.println(c);
                    } catch (ContatoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.print("Nome para remover: ");
                    String rem = sc.nextLine().trim();
                    try {
                        manager.removerContato(rem);
                        System.out.println("Contato removido.");
                    } catch (ContatoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case "4":
                    List<Contato> todos = manager.listarTodosContatos();
                    if (todos.isEmpty()) {
                        System.out.println("Nenhum contato.");
                    } else {
                        for (Contato ct : todos) {
                            System.out.println(ct);
                        }
                    }
                    break;
                case "5":
                    System.out.print("Nome do arquivo para salvar (ex: contatos.csv): ");
                    String arquivoSalvar = sc.nextLine().trim();
                    try {
                        manager.salvarContatosCSV(arquivoSalvar);
                        System.out.println("Salvo em " + arquivoSalvar);
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar: " + e.getMessage());
                    }
                    break;
                case "6":
                    System.out.print("Nome do arquivo para carregar (ex: contatos.csv): ");
                    String arquivoCarregar = sc.nextLine().trim();
                    try {
                        manager.carregarContatosCSV(arquivoCarregar);
                        System.out.println("Carregado de " + arquivoCarregar);
                    } catch (IOException e) {
                        System.out.println("Erro ao carregar: " + e.getMessage());
                    }
                    break;
                case "7":
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        sc.close();
        System.out.println("Encerrando.");
    }

}