package Wheels;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Payment process for plan changes and receipt generation.
 * Agora, integra com a API REST via ApiClient na troca de plano!
 */
public class Payment {

    private User user;
    private int paymentId;
    private static int paymentCount = 1;

    // Constructor (mantido para compatibilidade, se necessário)
    public Payment(User user) {
        this.user = user;
        this.paymentId = paymentCount++;
    }

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

        // Gera recibo local
        try {
            File dir = new File("receipts");
            if (!dir.exists()) dir.mkdirs();
            String fileName = "receipts/receipt_" + user.getUserID() + "_" + System.currentTimeMillis() + ".txt";
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            writer.println("Nome do usuário: " + user.getFirstName() + " " + user.getLastName());
            writer.println("Plano comprado: " + novoPlano);
            writer.println("Data da compra: " + LocalDateTime.now());
            writer.close();
            System.out.println("Recibo gerado em: " + fileName);
        } catch (IOException e) {
            System.out.println("Erro ao gerar recibo: " + e.getMessage());
        }

        System.out.println("Plano alterado com sucesso para " + novoPlano + "!");
        return true;
    }

    // Getters para Payment ID e User (mantidos se necessários)
    public int getPaymentId() {
        return paymentId;
    }
    public User getUser() {
        return user;
    }
}