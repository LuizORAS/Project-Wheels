package Wheels;

public class Main {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        AuthManager authManager = new AuthManager(apiClient);
        AuthMenu authMenu = new AuthMenu(authManager);
        authMenu.show();
    }

}
