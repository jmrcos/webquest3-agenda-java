// Equipe:
// 1. Alex Wendeu Cavalcante Saraiva
// 2. André Felipe da Silva Pereira
// 3. João Marcos Bezerra da Silva
// 4. José Mateus da Silva Santos

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AgendaApplication {

    private static final Scanner sc = new Scanner(System.in);
    private static final AgendaManager manager = new AgendaManager();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("====== AGENDA ======");
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
                case "1" -> adicionarContato();
                case "2" -> buscarContato();
                case "3" -> removerContato();
                case "4" -> listarContatos();
                case "5" -> salvarCSV();
                case "6" -> carregarCSV();
                case "7" -> running = false;
                default -> System.out.println("Opção inválida.");
            }
        }

        System.out.println("Encerrando...");
    }
    
    private static void adicionarContato() {
        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();

        System.out.print("Telefone: ");
        String tel = sc.nextLine().trim();

        System.out.print("E-mail: ");
        String email = sc.nextLine().trim();

        try {
            manager.adicionarContato(new Contato(nome, tel, email));
            System.out.println("Contato adicionado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void buscarContato() {
        System.out.print("Nome para buscar: ");
        String nome = sc.nextLine().trim();

        try {
            System.out.println(manager.buscarContato(nome));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void removerContato() {
        System.out.print("Nome para remover: ");
        String nome = sc.nextLine().trim();

        try {
            manager.removerContato(nome);
            System.out.println("Contato removido.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarContatos() {
        List<Contato> lista = manager.listarTodosContatos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
            return;
        }

        for (Contato c : lista) {
            System.out.println(c);
        }
    }

    private static void salvarCSV() {
        try {
            manager.salvarContatosCSV("contatos.csv");
            System.out.println("Arquivo salvo: contatos.csv");
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private static void carregarCSV() {
        try {
            manager.carregarContatosCSV("contatos.csv");
            System.out.println("Arquivo carregado: contatos.csv");
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
    }
}
