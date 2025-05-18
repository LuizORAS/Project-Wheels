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
            System.out.println("Usuário: " + user.getNome() + " | Plano: " + user.getPlano());
            System.out.println("1. Consultar plano atual e benefícios");
            System.out.println("2. Mudar plano");
            System.out.println("3. Cancelar plano (vira FREE)");
            System.out.println("4. Consultar dados do usuário");
            System.out.println("5. Excluir usuário");
            System.out.println("6. Alugar uma bicicleta");
            System.out.println("7. Devolver bicicleta");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    consultarPlano();
                    break;
                case "2":
                    mudarPlano(scanner);
                    break;
                case "3":
                    cancelarPlano();
                    break;
                case "4":
                    consultarDadosUsuario();
                    break;
                case "5":
                    excluirUsuario(scanner);
                    return;
                case "6":
                    alugarBicicleta(scanner);
                    break;
                case "7":
                    devolverBicicleta(scanner);
                    break;
                case "8":
                    System.out.println("Saindo para o menu de autenticação...");
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private void consultarPlano() {
        // Exibir o plano atual do usuário e seus benefícios
        System.out.println("\n--- PLANO ATUAL ---");
        System.out.println("Plano: " + user.getPlano());
        // TODO: Adicionar exibição dos benefícios do plano
    }

    private void mudarPlano(Scanner scanner) {
        // TODO: Lógica para trocar de plano
        System.out.println("\n--- MUDAR PLANO ---");
        System.out.println("Funcionalidade de troca de plano ainda não implementada.");
    }

    private void cancelarPlano() {
        // TODO: Lógica para cancelar o plano e mudar para FREE
        System.out.println("\n--- CANCELAR PLANO ---");
        System.out.println("Funcionalidade de cancelamento de plano ainda não implementada.");
    }

    private void consultarDadosUsuario() {
        // Exibir dados do usuário
        System.out.println("\n--- DADOS DO USUÁRIO ---");
        System.out.println("Login: " + user.getEmail());
        System.out.println("Data de criação: " + user.getDataCriacao());
        System.out.println("Plano: " + user.getPlano());
        System.out.println("Viagens hoje: " + user.getViagensHoje());
        System.out.println("Valor de multa atual: " + user.getMultaAtual());
        System.out.println("Próxima data de cobrança: " + user.getProximaCobranca());
        // Adicione outros campos conforme o seu User.java
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

    private void alugarBicicleta(Scanner scanner) {
        // TODO: Navegar para o menu de aluguel de bicicletas
        System.out.println("\n--- ALUGAR BICICLETA ---");
        System.out.println("Funcionalidade de aluguel ainda não implementada.");
    }

    private void devolverBicicleta(Scanner scanner) {
        // TODO: Navegar para o menu de devolução de bicicletas
        System.out.println("\n--- DEVOLVER BICICLETA ---");
        System.out.println("Funcionalidade de devolução ainda não implementada.");
    }

}