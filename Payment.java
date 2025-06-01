package Wheels;
import java.util.Scanner;

public class Payment {

    /**
     * Processa um pagamento de troca de plano:
     * - Solicita dados do cartão
     * - Realiza o pagamento (simulado)
     * - Troca o plano via ApiClient
     * - Gera recibo local
     * Retorna true se sucesso, false se cancelado.
     */
    public static boolean processPlanChange(ApiClient apiClient, User user, Plan novoPlano, Scanner scanner) {
        System.out.println("\n--- Pagamento para troca de plano ---");

        // Solicita e valida número do cartão
        String card;
        while (true) {
            System.out.print("Digite os 16 dígitos do cartão: ");
            card = scanner.nextLine().replaceAll("\\s+", "");
            if (card.equalsIgnoreCase("voltar")) return false;
            if (card.matches("\\d{16}"))
                break;
            System.out.println("Cartão inválido! Digite 16 dígitos numéricos ou 'voltar' para retornar.");
        }

        // Solicita e valida data de validade
        String validade;
        while (true) {
            System.out.print("Digite a data de validade (MM/AA): ");
            validade = scanner.nextLine().trim();
            if (validade.equalsIgnoreCase("voltar")) return false;
            if (validade.matches("\\d{2}/\\d{2}"))
                break;
            System.out.println("Data de validade inválida! Use o formato MM/AA ou 'voltar' para retornar.");
        }

        // Solicita e valida CVC
        String cvc;
        while (true) {
            System.out.print("Digite o CVC (3 dígitos): ");
            cvc = scanner.nextLine().trim();
            if (cvc.equalsIgnoreCase("voltar")) return false;
            if (cvc.matches("\\d{3}"))
                break;
            System.out.println("CVC inválido! Digite 3 dígitos numéricos ou 'voltar' para retornar.");
        }

        // Faz a troca de plano via ApiClient
        boolean sucessoAPI = false;
        try {
            sucessoAPI = apiClient.changeUserPlan(user.getEmail(), novoPlano);
        } catch (Exception e) {
            System.out.println("Erro ao trocar plano na API: " + e.getMessage());
        }
        if (!sucessoAPI) {
            System.out.println("Não foi possível alterar o plano na API. Operação cancelada.");
            return false;
        }
        user.setPlano(novoPlano);

        System.out.println("Plano alterado com sucesso para " + novoPlano + "!");
        return true;
    }

}