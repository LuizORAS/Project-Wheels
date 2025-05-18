package Wheels;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String USERS_CSV = "csv/users.csv";
    private Map<String, User> usersByEmail = new HashMap<>(); // email -> User

    public UserManager() {
        loadUsers();
    }

    public void loadUsers() {
        usersByEmail.clear();
        File file = new File(USERS_CSV);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 5) {
                    int id = Integer.parseInt(tokens[0]);
                    String firstName = tokens[1];
                    String lastName = tokens[2];
                    String email = tokens[3];
                    String password = tokens[4];
                    User u = new User(id, firstName, lastName, email, password);
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
                pw.println("userID,firstName,lastName,email,password");
            }
            pw.printf("%d,%s,%s,%s,%s\n", user.getUserID(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
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

    private void saveAllUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_CSV))) {
            pw.println("userID,firstName,lastName,email,password");
            for (User user : usersByEmail.values()) {
                pw.printf("%d,%s,%s,%s,%s\n", user.getUserID(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }
}