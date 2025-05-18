package Wheels;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Menu de autenticação (login/cadastro/saída)
public class AuthMenu {
    private final UserManager userManager;
    private final AuthManager authManager;

    public AuthMenu(UserManager userManager, AuthManager authManager) {
        this.userManager = userManager;
        this.authManager = authManager;
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
                        // Após login bem-sucedido, vai para o menu principal
                        MainMenu mainMenu = new MainMenu(user, userManager, authManager);
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

        User user = authManager.login(email, senha);
        if (user == null) {
            System.out.println("Login ou senha incorretos. Tente novamente.");
            return null;
        }
        System.out.println("Login realizado com sucesso!");
        return user;
    }

    private void cadastro(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine().trim();
        System.out.print("Login (e-mail): ");
        String email = scanner.nextLine().trim();

        if (userManager.emailExists(email)) {
            System.out.println("Já existe um usuário com esse e-mail. Tente outro.");
            return;
        }

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        // Dados adicionais obrigatórios para o funcionamento do sistema
        String plano = "FREE"; // padrão no cadastro
        LocalDate dataCriacao = LocalDate.now();
        String dataCriacaoStr = dataCriacao.format(DateTimeFormatter.ISO_LOCAL_DATE);
        int viagensHoje = 0;
        double multaAtual = 0.0;
        LocalDate proximaCobranca = dataCriacao.plusDays(30);
        String proximaCobrancaStr = proximaCobranca.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String bikeAlugada = ""; // nenhuma inicialmente
        String horaAluguel = "";

        // Registra usuário com todos os campos necessários (ajuste o método se necessário)
        boolean registrado = authManager.register(
                nome,
                sobrenome,
                email,
                senha,
                plano,
                dataCriacaoStr,
                viagensHoje,
                multaAtual,
                proximaCobrancaStr,
                bikeAlugada,
                horaAluguel
        );

        if (registrado) {
            System.out.println("Cadastro realizado com sucesso! Agora faça login.");
        } else {
            System.out.println("Erro ao cadastrar usuário. Tente novamente.");
        }
    }
}