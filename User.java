package Wheels;

public class User {
    // Enum to represent subscription plans
    public enum Plan {
        FREE, BASIC, GOLD, DIAMOND;
    }

    // Member variables
    private String name;
    private int userID;
    private String cpf;
    private String email;
    private String cellphone;
    private Plan subscriptionPlan;

    // Static counter for generating unique IDs
    private static int userCount = 1;

    // Constructor
    public User(String name, String cpf, String email, String cellphone, Plan plan) {
        if (!isValidCPF(cpf)) {
            throw new IllegalArgumentException("Invalid CPF format.");
        }

        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.cellphone = cellphone;
        this.subscriptionPlan = plan;
        this.userID = userCount++;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getUserID() {
        return userID;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public Plan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    // Setters
    public void setSubscriptionPlan(Plan plan) {
        this.subscriptionPlan = plan;
    }

    // Static method to validate CPF (basic simulation)
    private static boolean isValidCPF(String cpf) {
        // Simulating a basic CPF validation (only format check for now)
        return cpf != null && cpf.matches("\\d{11}");
    }

    // ToString override for better object representation
    @Override
    public String toString() {
        return "User{" +
               "name='" + name + '\'' +
               ", userID=" + userID +
               ", cpf='" + cpf + '\'' +
               ", email='" + email + '\'' +
               ", cellphone='" + cellphone + '\'' +
               ", subscriptionPlan=" + subscriptionPlan +
               '}';
    }
}