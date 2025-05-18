package Wheels;

import java.util.Scanner;

public class MainMenu {
    private final User user;
    private final UserManager userManager;
    private final AuthManager authManager;

    public MainMenu(User user, UserManager userManager, AuthManager authManager) {
        this.user = user;
        this.userManager = userManager;
        this.authManager = authManager;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("Usuário: " + user.getFirstName() + " " + user.getLastName());
            System.out.println("E-mail: " + user.getEmail());
            System.out.println("1. Consultar dados do usuário");
            System.out.println("2. Excluir usuário");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    consultarDadosUsuario();
                    break;
                case "2":
                    excluirUsuario(scanner);
                    return;
                case "3":
                    System.out.println("Saindo para o menu de autenticação...");
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private void consultarDadosUsuario() {
        System.out.println("\n--- DADOS DO USUÁRIO ---");
        System.out.println("ID: " + user.getUserID());
        System.out.println("Nome: " + user.getFirstName());
        System.out.println("Sobrenome: " + user.getLastName());
        System.out.println("E-mail: " + user.getEmail());
        // Não exibir senha por segurança
    }

    private void excluirUsuario(Scanner scanner) {
        System.out.println("\n--- EXCLUIR USUÁRIO ---");
        System.out.print("Digite sua senha para confirmar: ");
        String senha1 = scanner.nextLine();
        System.out.print("Digite novamente sua senha: ");
        String senha2 = scanner.nextLine();

        if (!senha1.equals(senha2)) {
            System.out.println("As senhas não coincidem. Cancelando exclusão.");
            return;
        }
        if (!user.getPassword().equals(senha1)) {
            System.out.println("Senha incorreta. Cancelando exclusão.");
            return;
        }

        boolean ok = userManager.removeUserByEmail(user.getEmail());
        if (ok) {
            System.out.println("Usuário excluído com sucesso. Encerrando sessão...");
        } else {
            System.out.println("Falha ao excluir usuário. Erro interno.");
        }
    }
}