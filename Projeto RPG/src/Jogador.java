import java.util.Scanner;

public class Jogador {
    private int id;
    private String nome;
    private String senha;
    private double saldoCristais;
    ListaPersonagem personagens = new ListaPersonagem();
       

    public Jogador(int id, String nome, String senha, double saldoCristais, ListaPersonagem personagens) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.saldoCristais = saldoCristais;
        this.personagens = personagens;
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

    

    public void criarPersonagem(Scanner sc) {
        System.out.print("Nome do personagem: ");
        String nomePersonagem = sc.next();
        Personagem personagem = new Personagem(
            personagens.getSize() + 1, // id único
            nomePersonagem,
            1, // nível inicial
            100, // vida máxima
            100, // vida atual
            0, // experiência inicial
            new ListaHabilidade()
        );
        personagens.adicionar(personagem);
        System.out.println("Personagem criado!");
    }

    public Personagem selecionarPersonagem(Scanner sc) {
        if (personagens.getSize() == 0) {
            System.out.println("Nenhum personagem cadastrado.");
            return null;
        }
        personagens.listar();
        System.out.print("Digite o ID do personagem que deseja selecionar: ");
        int personagemId = sc.nextInt();
        sc.nextLine(); // Consome o '\n'
        Personagem selecionado = personagens.buscarPorId(personagemId);
        if (selecionado != null) {
            System.out.println("Personagem selecionado: " + selecionado.getNome());
            return selecionado;
        }
        System.out.println("Personagem não encontrado.");
        return null;
    }


}
