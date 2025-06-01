package Wheels;

public class Main {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        AuthMenu authMenu = new AuthMenu(apiClient);
        authMenu.show();
    }

}
