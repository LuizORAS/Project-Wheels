package Wheels;

import java.io.*;
import java.util.*;

public class BikeManager {
    private static final String CSV_PATH = "csv/bikes.csv";
    private static final int BIKES_PER_TYPE = 5;
    private static final BikeType[] TYPES = BikeType.values();
    private Map<Integer, Bike> bikes = new HashMap<>();

    public BikeManager() {

        File dir = new File("csv");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File csvFile = new File(CSV_PATH);
        if (!csvFile.exists()) {
            createInitialBikesCSV();
        }
        loadBikesFromCSV();
    }

    // Cria o CSV com 5 bikes de cada tipo, IDs sequenciais de 1 em diante, todas disponíveis
    private void createInitialBikesCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_PATH))) {
            writer.println("id,type,available");
            int id = 1;
            for (BikeType type : TYPES) {
                for (int i = 0; i < BIKES_PER_TYPE; i++) {
                    writer.printf("%d,%s,true%n", id, type.name());
                    id++;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar arquivo de bicicletas: " + e.getMessage());
        }
    }

    // Lê todas as bikes do CSV para a memória
    private void loadBikesFromCSV() {
        bikes.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_PATH))) {
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0]);
                BikeType type = BikeType.valueOf(tokens[1]);
                boolean available = Boolean.parseBoolean(tokens[2]);
                bikes.put(id, new Bike(id, type, available));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de bicicletas: " + e.getMessage());
        }
    }

    // Atualiza o CSV com o estado atual das bikes
    private void saveBikesToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_PATH))) {
            writer.println("id,type,available");
            for (Bike bike : bikes.values()) {
                writer.printf("%d,%s,%b%n", bike.getId(), bike.getType().name(), bike.isAvailable());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo de bicicletas: " + e.getMessage());
        }
    }

    // Lista todas as bikes disponíveis
    public List<Bike> listAvailableBikes(BikeType type) {
        List<Bike> available = new ArrayList<>();
        for (Bike bike : bikes.values()) {
            if (bike.getType() == type && bike.isAvailable()) {
                available.add(bike);
            }
        }
        return available;
    }

    // Aluga uma bike (retorna a bike alugada ou null se nenhuma disponível)
    public Bike rentBike(BikeType type) {
        for (Bike bike : bikes.values()) {
            if (bike.getType() == type && bike.isAvailable()) {
                bike.setAvailable(false);
                saveBikesToCSV();
                return bike;
            }
        }
        return null; // nenhuma disponível
    }

    // Devolve uma bike pelo id
    public boolean returnBike(int bikeId) {
        Bike bike = bikes.get(bikeId);
        if (bike != null && !bike.isAvailable()) {
            bike.setAvailable(true);
            saveBikesToCSV();
            return true;
        }
        return false;
    }

    /*
    * Outros métodos utilitários podem ser
    * adicionados aqui (listar todas, buscar por ID, etc)
    * */
}