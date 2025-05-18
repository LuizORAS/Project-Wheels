package Wheels;

public class AuthManager {
    private UserManager userManager;

    public AuthManager(UserManager userManager) {
        this.userManager = userManager;
    }

    // Autenticação simples: retorna o usuário autenticado, ou null se falhar
    public User login(String email, String password) {
        User u = userManager.getUserByEmail(email);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    // Cadastro atualizado: retorna true se sucesso, false se email já existe
    public boolean register(
            String firstName,
            String lastName,
            String email,
            String password,
            Plan plano,
            String dataCriacao,
            int viagensHoje,
            double multaAtual,
            String proximaCobranca,
            String bikeAlugada,
            String horaAluguel
    ) {
        if (userManager.emailExists(email)) return false;
        User user = new User(firstName, lastName, email, password, plano, dataCriacao, viagensHoje, multaAtual, proximaCobranca, bikeAlugada, horaAluguel);
        userManager.addUser(user);
        return true;
    }
}