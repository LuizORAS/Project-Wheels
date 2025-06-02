package Wheels;

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;    // login
    private String password; // senha

    private Plan plano;
    private String dataCriacao;
    private int viagensHoje;
    private double multaAtual;
    private String proximaCobranca;
    private String bikeAlugada;
    private String horaAluguel;

    public User() {}

    // Construtor para novo usuário
    public User(String firstName, String lastName, String email, String password,
                Plan plano, String dataCriacao, int viagensHoje, double multaAtual,
                String proximaCobranca, String bikeAlugada, String horaAluguel) {

        this.userID = 0;
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
                Plan plano, String dataCriacao, int viagensHoje, double multaAtual,
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

    // Getters usados
    public int getUserID() { return userID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Plan getPlano() { return plano; }
    public String getDataCriacao() { return dataCriacao; }
    public int getViagensHoje() { return viagensHoje; }
    public double getMultaAtual() { return multaAtual; }
    public String getProximaCobranca() { return proximaCobranca; }
    public String getBikeAlugada() { return bikeAlugada; }
    public String getHoraAluguel() { return horaAluguel; }

    // Setter usado
    public void setPlano(Plan plano) { this.plano = plano; }

}