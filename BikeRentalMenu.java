package Wheels;

import java.util.List;
import java.util.Scanner;

public class BikeRentalMenu {
    private final User user;
    private final UserManager userManager;
    private final List<Bike> bikes; // Recebe lista de bikes disponível

    public BikeRentalMenu(User user, UserManager userManager, List<Bike> bikes) {
        this.user = user;
        this.userManager = userManager;
        this.bikes = bikes;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- ALUGUEL DE BICICLETA ---");

        // Exemplo de lógica para checar se usuário já tem uma bike alugada
        boolean hasRentedBike = false; // TODO: implementar de acordo com o modelo futuro do User

        if (hasRentedBike) {
            System.out.println("Você já possui uma bicicleta alugada.");
            System.out.println("1. Devolver bicicleta");
            System.out.println("2. Voltar");
            String choice = scanner.nextLine();
            if ("1".equals(choice)) {
                // TODO: lógica de devolução
                System.out.println("[Funcionalidade de devolução a implementar]");
            }
            return;
        }

        // Mostrar bikes disponíveis
        System.out.println("Bicicletas disponíveis para aluguel:");
        for (Bike bike : bikes) {
            if (bike.isAvailable()) {
                System.out.println("ID: " + bike.getId() + " | Tipo: " + bike.getType());
            }
        }
        System.out.print("Digite o ID da bike que deseja alugar (ou 0 para voltar): ");
        int idEscolhido = Integer.parseInt(scanner.nextLine());
        if (idEscolhido == 0) return;

        // Procura e aluga bike
        Bike escolhida = null;
        for (Bike bike : bikes) {
            if (bike.isAvailable() && bike.getId() == idEscolhido) {
                escolhida = bike;
                break;
            }
        }
        if (escolhida == null) {
            System.out.println("Bike não encontrada ou indisponível.");
            return;
        }
        // TODO: associar bike ao usuário, marcar indisponível, salvar no csv etc.
        escolhida.setAvailable(false);
        System.out.println("Bicicleta " + escolhida.getId() + " alugada com sucesso!");

        // TODO: salvar estado atualizado dos alugueis/bikes
    }
}