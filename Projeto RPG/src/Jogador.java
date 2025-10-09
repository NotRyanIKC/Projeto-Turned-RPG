import java.util.Scanner;

public class Jogador {
    private int id;
    private String nome;
    private String senha;
    private double saldoMoedas;
    ListaPersonagem personagens = new ListaPersonagem();
    private Personagem personagemSelecionado; // 🔹 Novo atributo

    public Jogador(int id, String nome, String senha, double saldoMoedas, ListaPersonagem personagens) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.saldoMoedas = saldoMoedas;
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
    public double getSaldoMoedas() {
        return saldoMoedas;
    }
    public void setSaldoMoedas(double saldoMoedas) {
        this.saldoMoedas = saldoMoedas;
    }

    // 🔹 Getter para o personagem selecionado
    public Personagem getPersonagemSelecionado() {
        return personagemSelecionado;
    }

    // 🔹 Método opcional caso queira alterar via código
    public void setPersonagemSelecionado(Personagem personagem) {
        this.personagemSelecionado = personagem;
    }

    public void cadastrar() {
        // Lógica para cadastrar o jogador
    }

    public void criarPersonagem(Scanner sc) {
        System.out.print("Nome do personagem: ");
        String nomePersonagem = sc.nextLine(); // aceita nomes compostos
        Personagem personagem = new Personagem(
            personagens.getSize() + 1, // id único
            nomePersonagem,
            1,
            100,
            100,
            0
        );
        personagens.adicionar(personagem);
        System.out.println("Personagem criado!");
    }

    
    public void selecionarPersonagem(Scanner sc) {
        if (personagens.getSize() == 0) {
            System.out.println("Nenhum personagem cadastrado.");
            return;
        }

        personagens.listar();
        System.out.print("Digite o ID do personagem que deseja selecionar: ");
        int personagemId = sc.nextInt();
        sc.nextLine();

        Personagem selecionado = personagens.buscarPorId(personagemId);
        if (selecionado != null) {
            personagemSelecionado = selecionado; // salva o personagem
            System.out.println("Personagem selecionado: " + personagemSelecionado.getNome());
        } else {
            System.out.println("Personagem não encontrado.");
        }
    }

    public void curarForaCombate(Personagem personagem, Scanner sc) {
    if (personagem == null) {
        System.out.println("Nenhum personagem selecionado!");
        return;
    }

    if (!personagem.estaVivo()) {
        System.out.println("Seu personagem está morto e precisa de uma cura completa!");
    }

    int vidaFaltando = personagem.getVidaMaxima() - personagem.getVidaAtual();
    if (vidaFaltando == 0) {
        System.out.println("O personagem já está com a vida cheia!");
        return;
    }

    double custoPorPonto = 0.5 ; 
    double custoTotal = vidaFaltando * custoPorPonto;

    System.out.println("Cura completa vai custar " + custoTotal + " moedas. Deseja confirmar? (s/n)");
    String opcao = sc.nextLine();

    if (opcao.equalsIgnoreCase("s")) {
        if (saldoMoedas >= custoTotal) {
            saldoMoedas -= custoTotal;
            personagem.setVidaAtual(personagem.getVidaMaxima());
            System.out.println( personagem.getNome() + " foi totalmente curado!");
            System.out.println("Saldo restante: " + saldoMoedas);
        } else {
            System.out.println("Você não tem moedas suficientes!");
        }
    } else {
        System.out.println("Cura cancelada.");
    }
}

}
