package Wheels;

import java.util.List;
import java.util.Scanner;

public class BikeRentalMenu {
    private final ApiClient apiClient;
    private User user;

    public BikeRentalMenu(ApiClient apiClient, User user) {
        this.apiClient = apiClient;
        this.user = user;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- ALUGUEL DE BICICLETA ---");

        if (user.getBikeAlugada() != null && !user.getBikeAlugada().isEmpty()) {
            System.out.println("Você já possui uma bicicleta alugada.");
            System.out.println("1. Devolver bicicleta");
            System.out.println("2. Voltar");
            String choice = scanner.nextLine();
            if ("1".equals(choice)) {
                devolverBike(scanner);
            }
            return;
        }

        // Mostrar opções permitidas pelo plano do usuário
        List<BikeType> permitidos = new java.util.ArrayList<>();
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

        int idx = 1;
        java.util.Map<Integer, BikeType> opcoes = new java.util.HashMap<>();
        System.out.println("Tipos de bicicleta disponíveis para seu plano:");
        for (BikeType tipo : permitidos) {
            try {
                List<Bike> disponiveis = apiClient.getAvailableBikes(tipo);
                System.out.println(idx + ". " + tipo + " (disponíveis: " + disponiveis.size() + ")");
                opcoes.put(idx, tipo);
                idx++;
            } catch (Exception e) {
                System.out.println("Erro ao buscar bikes: " + e.getMessage());
            }
        }
        System.out.println(idx + ". Voltar ao menu anterior");

        int escolhaTipo;
        while (true) {
            System.out.print("Escolha o tipo de bicicleta para alugar: ");
            String opt = scanner.nextLine();
            try {
                escolhaTipo = Integer.parseInt(opt);
                if (escolhaTipo == idx) {
                    System.out.println("Voltando ao menu...");
                    return;
                }
                if (!opcoes.containsKey(escolhaTipo)) {
                    System.out.println("Opção inválida!");
                    continue;
                }
                BikeType tipoEscolhido = opcoes.get(escolhaTipo);
                List<Bike> disponiveis = apiClient.getAvailableBikes(tipoEscolhido);

                if (disponiveis.isEmpty()) {
                    System.out.println("Não há bicicletas desse tipo disponíveis. Escolha outro tipo.");
                    continue;
                }

                Bike bikeSelecionada = disponiveis.get(0);
                System.out.println("Será selecionada automaticamente a primeira bicicleta disponível:");
                System.out.println("ID: " + bikeSelecionada.getId() + " | Tipo: " + bikeSelecionada.getType());

                // Solicita senha para confirmação
                System.out.print("Digite sua senha para confirmar o aluguel: ");
                String senhaDigitada = scanner.nextLine();
                if (!user.getPassword().equals(senhaDigitada)) {
                    System.out.println("Senha incorreta. Operação cancelada.");
                    return;
                }

                // Tenta alugar a bike pela API
                boolean sucesso = apiClient.rentBike(bikeSelecionada.getId(), user.getEmail());
                if (sucesso) {
                    user = apiClient.getUserByEmail(user.getEmail()); // Atualiza estado
                    System.out.println("Bicicleta " + bikeSelecionada.getId() + " alugada com sucesso!");
                } else {
                    System.out.println("Não foi possível alugar a bicicleta (indisponível ou erro).");
                }
                return;
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida!");
            } catch (Exception e) {
                System.out.println("Erro ao alugar bicicleta: " + e.getMessage());
                return;
            }
        }
    }

    private void devolverBike(Scanner scanner) {
        String bikeIdStr = user.getBikeAlugada();
        if (bikeIdStr == null || bikeIdStr.isEmpty()) {
            System.out.println("Você não possui bicicleta alugada.");
            return;
        }
        int bikeId = Integer.parseInt(bikeIdStr);

        try {
            boolean sucesso = apiClient.returnBike(bikeId, user.getEmail());
            if (sucesso) {
                user = apiClient.getUserByEmail(user.getEmail());
                System.out.println("Bicicleta devolvida com sucesso!");
                if (user.getMultaAtual() > 0) {
                    System.out.printf("Atenção: Você possui multa acumulada de R$ %.2f\n", user.getMultaAtual());
                }
            } else {
                System.out.println("Erro ao devolver bicicleta.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao devolver bicicleta: " + e.getMessage());
        }
    }

    public User getUser() {
        return user;
    }
}