package Wheels;

public class AuthManager {
    private final ApiClient apiClient;

    public AuthManager(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Realiza login, retornando o usuário autenticado ou null se falhar.
     */
    public User login(String email, String password) {
        try {
            User user = apiClient.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    /**
     * Realiza cadastro, retorna true se sucesso, false se email já existe ou erro.
     */
    public boolean register(User user) {
        try {
            // Verifica se já existe
            if (apiClient.getUserByEmail(user.getEmail()) != null) {
                return false;
            }
            return apiClient.createUser(user);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }
    public ApiClient getApiClient() {
        return apiClient;
    }
}