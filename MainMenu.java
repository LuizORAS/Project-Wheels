package Wheels;

import java.util.*;

public class MainMenu {
    private final ApiClient apiClient;
    private User user;

    public MainMenu(ApiClient apiClient, User user) {
        this.apiClient = apiClient;
        this.user = user;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Consultar plano atual");
            System.out.println("2. Mudar plano");
            System.out.println("3. Cancelar plano");
            System.out.println("4. Consultar dados do usuário");
            System.out.println("5. Excluir usuário");
            if (user.getBikeAlugada() == null || user.getBikeAlugada().isEmpty()) {
                System.out.println("6. Alugar bicicleta");
            } else {
                System.out.println("6. Devolver bicicleta");
                System.out.println("   [Você possui uma bicicleta alugada: " + user.getBikeAlugada() + "]");
            }
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        consultarPlanoAtual();
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
                        if (user.getBikeAlugada() == null || user.getBikeAlugada().isEmpty()) {
                            alugarBicicleta(scanner);
                        } else {
                            devolverBicicleta(scanner);
                        }
                        break;
                    case "7":
                        System.out.println("Saindo do sistema...");
                        return;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // 1. Consultar plano atual
    private void consultarPlanoAtual() {
        System.out.println("\n--- Consulta de Plano Atual ---");
        System.out.println("Plano: " + user.getPlano());

        switch (user.getPlano()) {
            case FREE:
                System.out.println("Benefícios: Até 1 aluguel por dia, apenas bikes comuns, limite de 30 min por aluguel, sujeito a multa.");
                break;
            case BASIC:
                System.out.println("Benefícios: Até 2 aluguéis por dia, acesso a bikes comuns, limite de 1h por aluguel, sujeito a multa.");
                break;
            case GOLD:
                System.out.println("Benefícios: Até 4 aluguéis por dia, acesso a bikes comuns e comfort, até 2h por aluguel, pode agendar bikes para o dia seguinte, sujeito a multa por atraso.");
                break;
            case DIAMOND:
                System.out.println("Benefícios: Até 6 aluguéis por dia, acesso a bikes comuns, comfort e electric, tempo ilimitado para bikes comuns e comfort, pode usar e-bikes 2 vezes ao dia, limite de 1 hora para e-bike, pode agendar bikes para o dia seguinte, recebe previsão de quando a próxima bike estará disponível, sujeito a multa por atraso de e-bike.");
                break;
            default:
                System.out.println("Benefícios: Plano desconhecido.");
        }
        System.out.println("Data de cadastro: " + user.getDataCriacao());
    }

    // 2. Mudar plano
    private void mudarPlano(Scanner scanner) throws Exception {
        System.out.println("\n--- Mudar Plano ---");
        Plan planoAtual = user.getPlano();

        System.out.println("Planos disponíveis:");
        int idx = 1;
        Plan[] planos = Plan.values();
        Map<Integer, Plan> opcoes = new HashMap<>();
        for (Plan p : planos) {
            if (p != planoAtual) {
                System.out.println(idx + ". " + p);
                opcoes.put(idx, p);
                idx++;
            }
        }
        System.out.println(idx + ". Voltar ao menu anterior");

        int escolha;
        while (true) {
            System.out.print("Escolha o novo plano (ou digite o número para voltar): ");
            String input = scanner.nextLine().trim();
            try {
                escolha = Integer.parseInt(input);
                if (escolha == idx) {
                    System.out.println("Retornando ao menu principal...");
                    return;
                }
                if (!opcoes.containsKey(escolha)) {
                    System.out.println("Opção inválida.");
                    continue;
                }
                Plan novoPlano = opcoes.get(escolha);

                boolean sucesso = Payment.processPlanChange(apiClient, user, novoPlano, scanner);
                if (sucesso) {
                    // Atualiza o usuário localmente, caso a API tenha alterado mais algum campo
                    user = apiClient.getUserByEmail(user.getEmail());
                } else {
                    System.out.println("Operação cancelada ou erro no pagamento.");
                }
                return;
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
            }
        }
    }

    // 3. Cancelar plano
    private void cancelarPlano() throws Exception {
        System.out.println("\n--- Cancelamento de Plano ---");
        if (user.getPlano() == Plan.FREE) {
            System.out.println("Você já está no plano FREE. Não é possível cancelar.");
        } else {
            boolean sucesso = apiClient.cancelUserPlan(user.getEmail());
            if (sucesso) {
                user.setPlano(Plan.FREE);
                System.out.println("Seu plano foi cancelado e você voltou para o plano FREE.");
            } else {
                System.out.println("Erro ao cancelar plano.");
            }
        }
    }

    // 4. Consultar dados do usuário
    private void consultarDadosUsuario() {
        System.out.println("\n--- Dados do Usuário ---");
        System.out.println("ID: " + user.getUserID());
        System.out.println("Nome: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("E-mail: " + user.getEmail());
        System.out.println("Plano: " + user.getPlano());
        System.out.println("Data de cadastro: " + user.getDataCriacao());
        System.out.println("Viagens hoje: " + user.getViagensHoje());
        System.out.println("Multa atual: " + user.getMultaAtual());
        System.out.println("Próxima cobrança: " + user.getProximaCobranca());
        if (user.getBikeAlugada() != null && !user.getBikeAlugada().isEmpty()) {
            System.out.println("Bicicleta alugada: " + user.getBikeAlugada());
            System.out.println("Hora do aluguel: " + user.getHoraAluguel());
        }
    }

    // 5. Excluir usuário
    private void excluirUsuario(Scanner scanner) throws Exception {
        System.out.println("\n--- Excluir Usuário ---");
        System.out.print("Tem certeza? Digite 'SIM' para confirmar: ");
        String confirm = scanner.nextLine().trim();
        if ("SIM".equalsIgnoreCase(confirm)) {
            boolean sucesso = apiClient.deleteUser(user.getEmail());
            if (sucesso) {
                System.out.println("Usuário excluído com sucesso.");
            } else {
                System.out.println("Erro ao excluir usuário.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // 6. Alugar bicicleta
    private void alugarBicicleta(Scanner scanner) throws Exception {
        BikeRentalMenu rentalMenu = new BikeRentalMenu(apiClient, user);
        rentalMenu.show();
        // Atualiza o user local após possíveis mudanças
        this.user = rentalMenu.getUser();
    }

    // 7. Devolver bicicleta
    private void devolverBicicleta(Scanner scanner) throws Exception {
        BikeRentalMenu rentalMenu = new BikeRentalMenu(apiClient, user);
        rentalMenu.show();
        // Atualiza o user local após possíveis mudanças
        this.user = rentalMenu.getUser();
    }
}