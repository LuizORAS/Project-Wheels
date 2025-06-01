package Wheels;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AuthMenu {
    private final ApiClient apiClient;

    public AuthMenu(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU DE AUTENTICAÇÃO =====");
            System.out.println("1. Login");
            System.out.println("2. Cadastro");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    User user = login(scanner);
                    if (user != null) {
                        MainMenu mainMenu = new MainMenu(apiClient, user);
                        mainMenu.show();
                    }
                    break;
                case "2":
                    cadastro(scanner);
                    break;
                case "3":
                    System.out.println("Saindo do sistema. Até a próxima!");
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private User login(Scanner scanner) {
        System.out.print("Login (e-mail): ");
        String email = scanner.nextLine().trim();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            User user = apiClient.getUserByEmail(email);
            if (user != null && user.getPassword().equals(senha)) {
                System.out.println("Login realizado com sucesso!");
                return user;
            } else {
                System.out.println("Login ou senha incorretos. Tente novamente.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao tentar logar: " + e.getMessage());
            return null;
        }
    }

    private void cadastro(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine().trim();
        System.out.print("Login (e-mail): ");
        String email = scanner.nextLine().trim();

        try {
            if (apiClient.getUserByEmail(email) != null) {
                System.out.println("Já existe um usuário com esse e-mail. Tente outro.");
                return;
            }
        } catch (Exception e) {
            // Se for erro 404, não encontrou, pode prosseguir
        }

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        // Dados obrigatórios
        Plan plano = Plan.FREE;
        LocalDate dataCriacao = LocalDate.now();
        String dataCriacaoStr = dataCriacao.format(DateTimeFormatter.ISO_LOCAL_DATE);
        int viagensHoje = 0;
        double multaAtual = 0.0;
        LocalDate proximaCobranca = dataCriacao.plusDays(30);
        String proximaCobrancaStr = proximaCobranca.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String bikeAlugada = "";
        String horaAluguel = "";

        User novoUser = new User(0, nome, sobrenome, email, senha, plano, dataCriacaoStr, viagensHoje, multaAtual, proximaCobrancaStr, bikeAlugada, horaAluguel);

        try {
            boolean registrado = apiClient.createUser(novoUser);
            if (registrado) {
                System.out.println("Cadastro realizado com sucesso! Agora faça login.");
            } else {
                System.out.println("Erro ao cadastrar usuário. Tente novamente.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
}