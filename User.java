package Wheels;

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;    // login
    private String password; // senha

    private String plano;
    private String dataCriacao;
    private int viagensHoje;
    private double multaAtual;
    private String proximaCobranca;
    private String bikeAlugada;
    private String horaAluguel;

    private static int userCount = 1;

    // Construtor para novo usuário
    public User(String firstName, String lastName, String email, String password,
                String plano, String dataCriacao, int viagensHoje, double multaAtual,
                String proximaCobranca, String bikeAlugada, String horaAluguel) {
        this.userID = userCount++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

        this.plano = plano;
        this.dataCriacao = dataCriacao;
        this.viagensHoje = viagensHoje;
        this.multaAtual = multaAtual;
        this.proximaCobranca = proximaCobranca;
        this.bikeAlugada = bikeAlugada;
        this.horaAluguel = horaAluguel;
    }

    // Construtor para carregar do CSV (com id)
    public User(int userID, String firstName, String lastName, String email, String password,
                String plano, String dataCriacao, int viagensHoje, double multaAtual,
                String proximaCobranca, String bikeAlugada, String horaAluguel) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

        this.plano = plano;
        this.dataCriacao = dataCriacao;
        this.viagensHoje = viagensHoje;
        this.multaAtual = multaAtual;
        this.proximaCobranca = proximaCobranca;
        this.bikeAlugada = bikeAlugada;
        this.horaAluguel = horaAluguel;
    }

    // Getters e Setters (adicione para todos os novos campos)
    public int getUserID() { return userID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPlano() { return plano; }
    public String getDataCriacao() { return dataCriacao; }
    public int getViagensHoje() { return viagensHoje; }
    public double getMultaAtual() { return multaAtual; }
    public String getProximaCobranca() { return proximaCobranca; }
    public String getBikeAlugada() { return bikeAlugada; }
    public String getHoraAluguel() { return horaAluguel; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPlano(String plano) { this.plano = plano; }
    public void setDataCriacao(String dataCriacao) { this.dataCriacao = dataCriacao; }
    public void setViagensHoje(int viagensHoje) { this.viagensHoje = viagensHoje; }
    public void setMultaAtual(double multaAtual) { this.multaAtual = multaAtual; }
    public void setProximaCobranca(String proximaCobranca) { this.proximaCobranca = proximaCobranca; }
    public void setBikeAlugada(String bikeAlugada) { this.bikeAlugada = bikeAlugada; }
    public void setHoraAluguel(String horaAluguel) { this.horaAluguel = horaAluguel; }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", plano='" + plano + '\'' +
                ", dataCriacao='" + dataCriacao + '\'' +
                ", viagensHoje=" + viagensHoje +
                ", multaAtual=" + multaAtual +
                ", proximaCobranca='" + proximaCobranca + '\'' +
                ", bikeAlugada='" + bikeAlugada + '\'' +
                ", horaAluguel='" + horaAluguel + '\'' +
                '}';
    }
}