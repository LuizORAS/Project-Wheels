package Wheels;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Payment {

    private User user;
    private int paymentId;


    private static int paymentCount = 1;

    // Constructor
    public Payment(User user) {
        this.user = user;
        this.paymentId = paymentCount++;
    }

    // (Mantém métodos antigos se desejar, ou remova se não precisar)

    // Novo método estático para processar troca de plano
    public static boolean processPlanChange(User user, Plan novoPlano, Scanner scanner) {
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

        // Atualiza o plano do usuário
        user.setPlano(novoPlano);

        // Gera recibo
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

    // Getters for Payment ID and User (mantidos se necessários)
    public int getPaymentId() {
        return paymentId;
    }

    public User getUser() {
        return user;
    }
}