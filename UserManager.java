package Wheels;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String USERS_CSV = "csv/users.csv";
    private Map<String, User> usersByEmail = new HashMap<>(); // email -> User

    public UserManager() {
        ensureCsvDirectory();
        loadUsers();
    }

    private void ensureCsvDirectory() {
        File dir = new File("csv");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void loadUsers() {
        usersByEmail.clear();
        File file = new File(USERS_CSV);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                // 12 campos
                if (tokens.length >= 12) {
                    int id = Integer.parseInt(tokens[0]);
                    String firstName = tokens[1];
                    String lastName = tokens[2];
                    String email = tokens[3];
                    String password = tokens[4];
                    Plan plano = Plan.valueOf(tokens[5]);
                    String dataCriacao = tokens[6];
                    int viagensHoje = Integer.parseInt(tokens[7]);
                    double multaAtual = Double.parseDouble(tokens[8]);
                    String proximaCobranca = tokens[9];
                    String bikeAlugada = tokens[10];
                    String horaAluguel = tokens[11];
                    User u = new User(id, firstName, lastName, email, password, plano, dataCriacao, viagensHoje, multaAtual, proximaCobranca, bikeAlugada, horaAluguel);
                    usersByEmail.put(email, u);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler users.csv: " + e.getMessage());
        }
    }

    public boolean emailExists(String email) {
        return usersByEmail.containsKey(email);
    }

    public User getUserByEmail(String email) {
        return usersByEmail.get(email);
    }

    public void addUser(User user) {
        usersByEmail.put(user.getEmail(), user);
        saveUser(user);
    }

    private void saveUser(User user) {
        boolean fileExists = new File(USERS_CSV).exists();
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_CSV, true))) {
            if (!fileExists) {
                pw.println("userID,firstName,lastName,email,password,plano,dataCriacao,viagensHoje,multaAtual,proximaCobranca,bikeAlugada,horaAluguel");
            }
            pw.printf("%d,%s,%s,%s,%s,%s,%s,%d,%.2f,%s,%s,%s%n",
                    user.getUserID(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPlano().name(),
                    user.getDataCriacao(),
                    user.getViagensHoje(),
                    user.getMultaAtual(),
                    user.getProximaCobranca(),
                    user.getBikeAlugada(),
                    user.getHoraAluguel()
            );
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public Collection<User> getAllUsers() {
        return usersByEmail.values();
    }

    public boolean removeUserByEmail(String email) {
        if (!usersByEmail.containsKey(email)) return false;
        usersByEmail.remove(email);
        saveAllUsers();
        return true;
    }

    public void saveAllUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_CSV))) {
            pw.println("userID,firstName,lastName,email,password,plano,dataCriacao,viagensHoje,multaAtual,proximaCobranca,bikeAlugada,horaAluguel");
            for (User user : usersByEmail.values()) {
                pw.printf("%d,%s,%s,%s,%s,%s,%s,%d,%.2f,%s,%s,%s%n",
                        user.getUserID(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getPlano(),
                        user.getDataCriacao(),
                        user.getViagensHoje(),
                        user.getMultaAtual(),
                        user.getProximaCobranca(),
                        user.getBikeAlugada(),
                        user.getHoraAluguel()
                );
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }
}