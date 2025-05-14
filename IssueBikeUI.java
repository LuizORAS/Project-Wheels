package Wheels;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IssueBikeUI {
    private List<User> users; // Lista de usuários cadastrados

    public IssueBikeUI() {
        users = new ArrayList<>();
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar Novo Usuário");
            System.out.println("2. Informações Sobre Planos");
            System.out.println("3. Inscrever-se em um Plano");
            System.out.println("4. Cancelar um Plano");
            System.out.println("5. Excluir Cadastro de Usuário");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (choice) {
                case 1:
                    createUser(scanner);
                    break;
                case 2:
                    showPlanInfo(scanner);
                    break;
                case 3:
                    associatePlan(scanner);
                    break;
                case 4:
                    cancelPlan(scanner);
                    break;
                case 5:
                    deleteUser(scanner);
                    break;
                case 6:
                    running = false;
                    System.out.println("Encerrando programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    private void createUser(Scanner scanner) {
        System.out.print("Digite o nome do usuário: ");
        String name = scanner.nextLine();

        System.out.print("Digite o CPF do usuário: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o email do usuário: ");
        String email = scanner.nextLine();

        System.out.print("Digite o telefone do usuário: ");
        String cellphone = scanner.nextLine();

        System.out.println("Escolha um plano (FREE, BASIC, GOLD, DIAMOND): ");
        String planInput = scanner.nextLine().toUpperCase();

        User.Plan plan;
        try {
            plan = User.Plan.valueOf(planInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Plano inválido. Usuário será cadastrado com plano FREE.");
            plan = User.Plan.FREE;
        }

        User user = new User(name, cpf, email, cellphone, plan);
        users.add(user);
        saveUserToLog(user);

        System.out.println("Usuário cadastrado com sucesso!");
    }

    private void showPlanInfo(Scanner scanner) {
        boolean inSubMenu = true;

        while (inSubMenu) {
            System.out.println("\n--- INFORMAÇÕES SOBRE PLANOS ---");
            System.out.println("1. Detalhes do Plano Free");
            System.out.println("2. Detalhes do Plano Basic");
            System.out.println("3. Detalhes do Plano Gold");
            System.out.println("4. Detalhes do Plano Diamond");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (choice) {
                case 1:
                    System.out.println("Plano Free: Gratuito. Permite acesso limitado ao sistema.");
                    break;
                case 2:
                    System.out.println("Plano Basic: $20/mês. Benefícios básicos com acesso padrão.");
                    break;
                case 3:
                    System.out.println("Plano Gold: $50/mês. Benefícios adicionais e maior flexibilidade.");
                    break;
                case 4:
                    System.out.println("Plano Diamond: $100/mês. Todos os benefícios premium.");
                    break;
                case 5:
                    inSubMenu = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void associatePlan(Scanner scanner) {
        System.out.print("Digite o CPF do usuário: ");
        String cpf = scanner.nextLine();

        User user = findUserByCpf(cpf);
        if (user == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.println("Escolha um plano (BASIC, GOLD, DIAMOND): ");
        String planInput = scanner.nextLine().toUpperCase();

        User.Plan plan;
        try {
            plan = User.Plan.valueOf(planInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Plano inválido. Operação cancelada.");
            return;
        }

        user.setSubscriptionPlan(plan);
        saveUserToLog(user);

        if (plan != User.Plan.FREE) {
            generateReceipt(user);
        }

        System.out.println("Plano associado com sucesso!");
    }

    private void cancelPlan(Scanner scanner) {
        System.out.print("Digite o CPF do usuário: ");
        String cpf = scanner.nextLine();

        User user = findUserByCpf(cpf);
        if (user == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        user.setSubscriptionPlan(User.Plan.FREE);
        saveUserToLog(user);
        System.out.println("Plano cancelado com sucesso! O usuário foi movido para o plano Free.");
    }

    private void deleteUser(Scanner scanner) {
        System.out.print("Digite o CPF do usuário a ser excluído: ");
        String cpf = scanner.nextLine();

        User user = findUserByCpf(cpf);
        if (user == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        users.remove(user);
        System.out.println("Usuário excluído com sucesso.");
    }

    private User findUserByCpf(String cpf) {
        for (User user : users) {
            if (user.getCpf().equals(cpf)) {
                return user;
            }
        }
        return null;
    }

    private void saveUserToLog(User user) {
        try (FileWriter writer = new FileWriter("users_log.csv", true)) {
            writer.write(user.getName() + "," + user.getCpf() + "," + user.getEmail() + "," +
                    user.getCellphone() + "," + user.getSubscriptionPlan() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar log do usuário: " + e.getMessage());
        }
    }

    private void generateReceipt(User user) {
        try (FileWriter writer = new FileWriter(user.getName() + "_receipt.txt")) {
            writer.write("---- Recibo ----\n");
            writer.write("Nome: " + user.getName() + "\n");
            writer.write("Plano: " + user.getSubscriptionPlan() + "\n");

            double cost = 0;
            switch (user.getSubscriptionPlan()) {
                case BASIC:
                    cost = 20.0;
                    break;
                case GOLD:
                    cost = 50.0;
                    break;
                case DIAMOND:
                    cost = 100.0;
                    break;
            }

            writer.write("Valor: $" + cost + "\n");
            writer.write("----------------\n");
        } catch (IOException e) {
            System.out.println("Erro ao gerar recibo: " + e.getMessage());
        }
    }
}