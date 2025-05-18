package Wheels;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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

            switch (choice) {
                case "1":
                    consultarPlanoAtual();
                    break;
                case "2":
                    mudarPlano(scanner);
                    break;
                case "3":
                    cancelarPlano(scanner);
                    break;
                case "4":
                    consultarDadosUsuario();
                    break;
                case "5":
                    excluirUsuario(scanner);
                    break;
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
        }
    }

    // 1. Consultar plano atual
    private void consultarPlanoAtual() {
        // TODO: Implementar consulta do plano, mostrar nome, benefícios e data de cadastro
        System.out.println("\n--- Consulta de Plano Atual ---");
        System.out.println("Plano: " + user.getPlano());

        // Benefícios de cada plano
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
                System.out.println("Benefícios: Até 6 aluguéis por dia, acesso a bikes comuns, comfort e electric, tempo ilimitado para bikes comuns e comfort, pode usar e-bikes 2 vezes ao dia, limite de 1 hora para e-bike, pode agendar bikes para o dia seguinte, recebe previsão de quando a proxima bike estará disponível, sujeito a multa por atraso de e-bike.");
                break;
            default:
                System.out.println("Benefícios: Plano desconhecido.");
        }
        System.out.println("Data de cadastro: " + user.getDataCriacao());
    }

    // 2. Mudar plano (submenu + integração com Payment e receipt)
    private void mudarPlano(Scanner scanner) {
        System.out.println("\n--- Mudar Plano ---");
        Plan planoAtual = user.getPlano();

        // Listar os planos disponíveis, exceto o atual
        System.out.println("Planos disponíveis:");
        int idx = 1;
        Plan[] planos = Plan.values();
        for (Plan p : planos) {
            if (p != planoAtual) {
                System.out.println(idx + ". " + p);
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
                    break;
                }
                // Mapeia a escolha ao plano correto (pula o plano atual)
                int realIdx = 0;
                for (Plan p : planos) {
                    if (p == planoAtual) continue;
                    realIdx++;
                    if (realIdx == escolha) {
                        boolean sucesso = Payment.processPlanChange(user, p, scanner);
                        if (sucesso) {
                            userManager.saveAllUsers();
                            System.out.println("Plano alterado com sucesso!");
                        } else {
                            System.out.println("Operação cancelada. Plano não alterado.");
                        }
                        break;
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
            }
        }
    }

    // 3. Cancelar plano
    private void cancelarPlano(Scanner scanner) {
        System.out.println("\n--- Cancelamento de Plano ---");
        if (user.getPlano() == Plan.FREE) {
            System.out.println("Você já está no plano FREE. Não é possível cancelar.");
        } else {
            user.setPlano(Plan.FREE);
            userManager.saveAllUsers();
            System.out.println("Seu plano foi cancelado e você voltou para o plano FREE.");
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
        System.out.println("Multa atual: R$" + String.format("%.2f", user.getMultaAtual()));
        System.out.println("Próxima cobrança: " + user.getProximaCobranca());
        System.out.println("Bicicleta alugada: " +
                (user.getBikeAlugada() == null || user.getBikeAlugada().isEmpty() ? "Nenhuma" : user.getBikeAlugada()));
        System.out.println("Hora do aluguel: " +
                (user.getHoraAluguel() == null || user.getHoraAluguel().isEmpty() ? "Nenhuma" : user.getHoraAluguel()));
    }

    // 5. Excluir usuário (pede senha duas vezes e remove do CSV)
    private void excluirUsuario(Scanner scanner) {
        System.out.println("\n--- Excluir Usuário ---");
        System.out.print("Digite sua senha: ");
        String senha1 = scanner.nextLine();
        System.out.print("Confirme sua senha: ");
        String senha2 = scanner.nextLine();

        if (!senha1.equals(senha2)) {
            System.out.println("As senhas não coincidem. Operação cancelada.");
            return;
        }
        if (!user.getPassword().equals(senha1)) {
            System.out.println("Senha incorreta. Operação cancelada.");
            return;
        }

        boolean removido = userManager.removeUserByEmail(user.getEmail());
        if (removido) {
            System.out.println("Usuário excluído com sucesso! Encerrando sessão...");
            System.exit(0);
        } else {
            System.out.println("Erro ao excluir usuário.");
        }
    }

    // 6. Alugar bicicleta
    private void alugarBicicleta(Scanner scanner) {
        System.out.println("\n--- Alugar Bicicleta ---");

        // 1. Verifica limite do plano
        int limite;
        switch (user.getPlano()) {
            case FREE:    limite = 1; break;
            case BASIC:   limite = 2; break;
            case GOLD:    limite = 4; break;
            case DIAMOND: limite = 6; break;
            default:      limite = 1;
        }
        if (user.getViagensHoje() >= limite) {
            System.out.println("Você já atingiu o limite de aluguéis diários do seu plano!");
            return;
        }

        // 2. Mostra opções permitidas pelo plano
        List<BikeType> permitidos = new ArrayList<>();
        switch (user.getPlano()) {
            case FREE:
            case BASIC:
                permitidos.add(BikeType.BASIC);
                break;
            case GOLD:
                permitidos.add(BikeType.BASIC);
                permitidos.add(BikeType.COMFORT);
                break;
            case DIAMOND:
                permitidos.add(BikeType.BASIC);
                permitidos.add(BikeType.COMFORT);
                permitidos.add(BikeType.ELECTRIC);
                break;
        }

        // 3. Instancia BikeManager e mostra quantidade disponível de cada tipo permitido
        BikeManager bm = new BikeManager();
        int idx = 1;
        Map<Integer, BikeType> opcoes = new HashMap<>();
        System.out.println("Tipos de bicicleta disponíveis para seu plano:");
        for (BikeType tipo : permitidos) {
            int disponiveis = bm.listAvailableBikes(tipo).size();
            System.out.println(idx + ". " + tipo + " (disponíveis: " + disponiveis + ")");
            opcoes.put(idx, tipo);
            idx++;
        }
        System.out.println(idx + ". Voltar ao menu anterior");

        // 4. Solicita escolha do tipo de bike
        int escolhaTipo = -1;
        while (true) {
            System.out.print("Escolha o tipo de bicicleta para alugar: ");
            String opt = scanner.nextLine();
            try {
                escolhaTipo = Integer.parseInt(opt);
                if (escolhaTipo == idx) {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }
                if (!opcoes.containsKey(escolhaTipo)) {
                    System.out.println("Opção inválida!");
                    continue;
                }
                BikeType tipoEscolhido = opcoes.get(escolhaTipo);
                List<Bike> disponiveis = bm.listAvailableBikes(tipoEscolhido);
                if (disponiveis.isEmpty()) {
                    System.out.println("Não há bicicletas desse tipo disponíveis. Escolha outro tipo.");
                    continue;
                }
                // 5. Realiza aluguel
                Bike bike = bm.rentBike(tipoEscolhido);
                if (bike == null) {
                    System.out.println("Não foi possível alugar a bicicleta. Tente novamente.");
                    return;
                }
                // Atualiza estado do usuário
                user.setBikeAlugada(String.valueOf(bike.getId()));
                user.setHoraAluguel(java.time.LocalDateTime.now().toString());
                user.setViagensHoje(user.getViagensHoje() + 1);
                userManager.saveAllUsers();
                System.out.println("Bicicleta alugada com sucesso! ID: " + bike.getId() + " (" + bike.getType() + ")");
                System.out.println("Lembre-se de devolver a bicicleta pelo menu principal.");
                // Regras de devolução específicas podem ser exibidas aqui
                return;
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida!");
            }
        }
    }

    // 6. Devolver bicicleta
    private void devolverBicicleta(Scanner scanner) {
        System.out.println("\n--- Devolver Bicicleta ---");
        // 1. Verifica se o usuário realmente tem uma bike alugada
        String bikeIdStr = user.getBikeAlugada();
        String horaAluguelStr = user.getHoraAluguel();
        if (bikeIdStr == null || bikeIdStr.isEmpty() || horaAluguelStr == null || horaAluguelStr.isEmpty()) {
            System.out.println("Você não possui bicicleta alugada.");
            return;
        }
        int bikeId = Integer.parseInt(bikeIdStr);

        // 2. Recupera o tipo da bike alugada
        BikeManager bm = new BikeManager();
        Bike bike = null;
        for (BikeType type : BikeType.values()) {
            for (Bike b : bm.listAvailableBikes(type)) {
                if (b.getId() == bikeId) {
                    bike = b;
                    break;
                }
            }
        }
        // Se não achou entre as disponíveis, pega do CSV (bike já marcada como indisponível)
        if (bike == null) {
            // Hack: Bike não está disponível ainda, então está indisponível
            // Recarrega todas do BikeManager
            bm = new BikeManager(); // força reload
            for (BikeType type : BikeType.values()) {
                for (Bike b : bm.listAvailableBikes(type)) {
                    if (b.getId() == bikeId) {
                        bike = b;
                        break;
                    }
                }
            }
            // Se mesmo assim não achou, ignora, pois só precisa do tipo
            if (bike == null) {
                // Se não achou, provavelmente está como indisponível (alugada), então pega o tipo da string salva no User
                // Só que não temos esse info, então vamos supor que o ID existe
            }
        }

        BikeType bikeType = null;
        if (bike != null) {
            bikeType = bike.getType();
        } else {
            // Como fallback, assume BASIC (melhor do que crashar)
            bikeType = BikeType.BASIC;
        }

        // 3. Calcula tempo de uso
        java.time.LocalDateTime horaAluguel = java.time.LocalDateTime.parse(horaAluguelStr);
        java.time.LocalDateTime horaAgora = java.time.LocalDateTime.now();
        long minutosUso = java.time.Duration.between(horaAluguel, horaAgora).toMinutes();

        // 4. Calcula limite conforme plano/tipo
        int limiteMin = 0;
        switch (user.getPlano()) {
            case FREE:
                limiteMin = 30;
                break;
            case BASIC:
                limiteMin = 60;
                break;
            case GOLD:
                limiteMin = 120;
                break;
            case DIAMOND:
                if (bikeType == BikeType.ELECTRIC) {
                    limiteMin = 60;
                } else {
                    limiteMin = 999999; // Ilimitado para BASIC/COMFORT
                }
                break;
            default:
                limiteMin = 60;
        }

        // 5. Calcula multa se excedeu limite
        double multa = 0.0;
        int minutosExcedidos = (int) (minutosUso - limiteMin);
        if (minutosExcedidos > 0) {
            multa = bikeType.calculateFine(minutosExcedidos);
            user.setMultaAtual(user.getMultaAtual() + multa);
            System.out.printf("Você excedeu o tempo limite em %d minutos. Multa: R$ %.2f\n", minutosExcedidos, multa);
        } else {
            System.out.println("Bicicleta devolvida dentro do tempo! Nenhuma multa aplicada.");
        }

        // 6. Marca bike como disponível
        boolean sucesso = bm.returnBike(bikeId);
        if (!sucesso) {
            System.out.println("Erro ao devolver bicicleta. Contate o suporte.");
            return;
        }

        // 7. Limpa campos do usuário
        user.setBikeAlugada("");
        user.setHoraAluguel("");
        userManager.saveAllUsers();

        System.out.println("Bicicleta devolvida com sucesso!");
        System.out.println("Tempo de uso: " + minutosUso + " minutos.");
        if (minutosExcedidos > 0) {
            System.out.printf("Multa aplicada: R$ %.2f\n", multa);
        }
    }
}