package Wheels;

import java.util.Scanner;

public class IssueBikeUI {
    private User user;             // Usuário autenticado
    private UserManager userManager; // Permite atualizar/excluir usuário

    public IssueBikeUI(User user, UserManager userManager) {
        this.user = user;
        this.userManager = userManager;
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("Bem-vindo, " + user.getFirstName() + " " + user.getLastName());
            System.out.println("1. Ver meus dados");
            System.out.println("2. Alterar senha");
            System.out.println("3. Excluir minha conta");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showUserInfo();
                    break;
                case "2":
                    changePassword(scanner);
                    break;
                case "3":
                    deleteAccount(scanner);
                    running = false;
                    break;
                case "4":
                    running = false;
                    System.out.println("Saindo do sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void showUserInfo() {
        System.out.println("ID: " + user.getUserID());
        System.out.println("Nome: " + user.getFirstName());
        System.out.println("Sobrenome: " + user.getLastName());
        System.out.println("Email: " + user.getEmail());
    }

    private void changePassword(Scanner scanner) {
        System.out.print("Digite a nova senha: ");
        String newPassword = scanner.nextLine();
        user.setPassword(newPassword);
        UserManager.updateUser(user); // Você deve implementar updateUser em UserManager
        System.out.println("Senha alterada com sucesso!");
    }

    private void deleteAccount(Scanner scanner) {
        System.out.print("Tem certeza que deseja excluir sua conta? (s/n): ");
        String conf = scanner.nextLine().trim().toLowerCase();
        if (conf.equals("s")) {
            UserManager.deleteUser(user); // Você deve implementar deleteUser em UserManager
            System.out.println("Conta excluída com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}