public class Jogador {
    private int id;
    private String nome;
    private String senha;
    private double saldoCristais;

    public Jogador(int id, String nome, String senha, double saldoCristais) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.saldoCristais = saldoCristais;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public double getSaldoCristais() {
        return saldoCristais;
    }
    public void setSaldoCristais(double saldoCristais) {
        this.saldoCristais = saldoCristais;
    }


    public void cadastrar() {
        // Lógica para cadastrar o jogador
    }

    

    public void criarPersonagem() {
        // Lógica para criar um personagem
    }

    public void selecionarPersonagem() {
        // Lógica para selecionar um personagem
    }

    
}
