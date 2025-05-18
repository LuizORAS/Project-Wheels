package Wheels;

public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        AuthManager authManager = new AuthManager(userManager);

        AuthMenu authMenu = new AuthMenu(userManager, authManager);
        authMenu.show();
    }

}
